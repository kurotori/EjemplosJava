/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import chat.Usuario;
import herramientas.Texto;
import herramientas.Tiempo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.UUID;

/**
 *
 * @author luiss
 */
public class HiloCliente implements Runnable {
    
    private final Socket socketCliente;
    private BufferedReader entrada;
    private PrintWriter salida;
    private UUID id;
    private Usuario usuario;
    
    Texto texto = new Texto();
    Tiempo tiempo = new Tiempo();
    
    
    
    
    /**
     * Método constructor del cliente
     * @param socketCliente
     * @param entrada
     * @param salida 
     */
    public HiloCliente(Socket socketCliente, 
                       BufferedReader entrada, 
                       PrintWriter salida){
        
        this.socketCliente = socketCliente;
        this.entrada = entrada;
        this.salida = salida;
        //Se genera una id mediante la clase 
        this.id = UUID.randomUUID();
        
        //Auxiliar: mostrar la id generada por el cliente conectado
        System.out.println(
                "Hilo Cliente para: "+socketCliente.getInetAddress().getCanonicalHostName()
               +" ID: "+id );
    }

    /**
     * Permite iniciar los procesos del hilo
     */
    @Override
    public void run() {
        //Preparamos un String para los datos de entrada desde el cliente
        String mensaje = "";
            
        //Abrimos un bucle que se cierra con un mensaje específico, dentro de un 
        try {
            while ( ! mensaje.equalsIgnoreCase("salir")) {                
                
                //Esperamos un mensaje del cliente y lo almacenamos en la variable 'mensaje'
                mensaje = entrada.readLine();
                
                //Un mensaje nulo (diferente de un mensaje vacío) se recibe cuando el cliente se
                // desconecta forzosamente. En este caso se cierra la conexión del cliente.
                if ( mensaje == null ) {
                    System.out.println(tiempo.marcaTiempo()+
                            "EL cliente "+this.id + " se ha desconectado.");
                    System.out.println(tiempo.marcaTiempo() + "Cerrando sesión de " + this.id);
                    logoutUsuario();
                    break;
                }
                //Respondemos al cliente con una confirmación
                //salida.println("Servidor::MSG_EST::OK");

                //Análisis del mensaje
                String[] datosMensaje = texto.analizarSolicitud(mensaje);
                procesarMensaje(datosMensaje);
                
                //Mostramos un registro del mensaje recibido
                System.out.println( 
                        tiempo.marcaTiempo() + "Reg "+
                        socketCliente.getInetAddress().getHostAddress() + 
                        ":" + mensaje );
                }
            }
            catch(IOException e){
                System.out.println("ERROR: mensaje nulo");
                System.out.println(e.getMessage());
                logoutUsuario();
                mensaje = "salir";
            }
            catch (NullPointerException e) {
                System.out.println("ERROR: mensaje nulo");
                System.out.println(e.getMessage());
                logoutUsuario();
                mensaje = "salir";
            }
        
    }
    
    
        /**
     * Permite procesar un mensaje enviado desde la aplicación cliente según su tipo,
 tras pasar por el método analizarSolicitud() de la clase Texto.
     * @param datosMensaje 
     */
    private void procesarMensaje(String[] datosMensaje){
        if (datosMensaje[0].equals("ERROR ")) {
            System.out.println(tiempo.marcaTiempo() + datosMensaje[0] + datosMensaje[1]);
        } 
        else {
            switch (datosMensaje[1]) {
                //Mensajes enviados por un usuario desde la aplicación cliente
                /**
                 * nombre_usuario::MSG::PUB::mensaje
                 * nombre_usuario::MSG::USR::nombre_usuario_dest::mensaje
                 */
                case "MSG":
                    switch (datosMensaje[2]) {
                        //Mensajes públicos
                        case "PUB":
                            enviarMsgPublico(datosMensaje[3]);
                            break;
                        //Mensajes a usuarios específicos
                        case "USR":
                            
                            break;
                    }
                    
                    break;
                    
                //Comandos enviados desde la aplicación cliente
                /**
                 * nombre_usuario::CMD::LOGIN::nombre_usuario
                 * nombre_usuario::CMD::LOGOUT
                 * nombre_usuario::CMD::
                 */
                case "CMD":
                    //Evaluación de los comandos
                    switch (datosMensaje[2]) {
                        //Solicitud de inicio de sesión 
                        case "LOGIN":
                            String nombre = datosMensaje[3];
                            loginUsuario(nombre);
                            break;
                        //Solicitud de cierre de sesión
                        case "LOGOUT":
                            logoutUsuario();
                            break;
                        default:
                            throw new AssertionError();
                    }
                    break;
                    
                default:
                    throw new AssertionError();
            }
        }
    }
    
    /**
     * Chequea si un usuario ya existe en el sistema.
     * @param nombre
     * @return true si el usuario existe, false si no existe
     */
    private boolean usuarioExiste(String nombre){
        boolean existe = false;
        
        for (HiloCliente cliente : ServidorMulti.clientes) {
            if (cliente.usuario.getNombre().equals(nombre)) {
                existe=true;
                break;
            }
        }
        
        return existe;
    }
    
    /**
     * Agrega un usuario a la lista de usuarios y lo identifica en el sistema
     * @param nombreUsuario 
     */
    private void loginUsuario(String nombreUsuario){
        
        //Chequeamos si ya hay un usuario con ese nombre en el servidor
        if (usuarioExiste(nombreUsuario)) {
            //Si existe un usuario, bloqueamos su ingreso
            salida.println("Servidor::MSG_EST::ERROR::Este nombre de usuario ya esta en uso");
        } else {
            //De lo contrario, anunciamos a los otros usuarios que el usuario se ha conectado
            enviarMsgServidor(nombreUsuario + 
                 " ha entrado al servidor" );
            
            //Y añadimos su nombre a la conexión y a la lista de usuarios
            this.usuario = new Usuario(nombreUsuario);
            //ServidorMulti.nombresDeUsuario.add(nombreUsuario);
            
            //Le enviamos datos identificatorios y un mensaje de bienvenida
            salida.println("Servidor::MSG_EST::LOGIN_OK::"+this.id);
            salida.println("Servidor::MSG_SRV::Bienvenido "+nombreUsuario);
            
            //Actualizamos el listado de usuarios en los clientes conectados
            actualizarListaUsuarios();
            
            //Y generamos un registro en el servidor
            System.out.println(
                tiempo.marcaTiempo() + 
                "El usuario " + this.usuario.getNombre() + 
                 " ha iniciado sesión desde el cliente " + this.id);
        }
                
        
    }
    
    /**
     * Permite cerrar la sesión de un usuario.
     */
    private void logoutUsuario(){
        //Removemos al usuario de las listas de usuarios y de la lista de clientes
        ServidorMulti.nombresDeUsuario.remove(this.usuario.getNombre());
        ServidorMulti.clientes.remove(this);
        
        //Enviamos un mensaje público anunciando que el usuario ha cerrado sesión

        enviarMsgServidor(this.usuario.getNombre() + 
                 " ha salido del servidor" );
        
        //Actualizamos el listado de usuarios en los clientes conectados
        actualizarListaUsuarios();
        //Generamos un registro en el servidor
        System.out.println(tiempo.marcaTiempo()+ 
                "Se ha cerrado la sesión del usuario "+ this.usuario.getNombre());
        try {
            this.entrada.close();
            this.salida.close();
        } catch (Exception e) {
            System.out.println(tiempo.marcaTiempo()+
                    "Se han detectado errores al cerrar el hilo cliente: "+
                    e.getMessage());
        }
    }
    
    /**
     * Envía un mensaje a todos los usuarios conectados al servidor
     * @param mensaje 
     */
    public void enviarMsgPublico(String mensaje){
        //Obtenemos el nombre del usuario remitente
        String nombreUsuario = this.usuario.getNombre();
        
        //Enviamos el mensaje a todos los usuarios mediante un forEach
        for (HiloCliente cliente : ServidorMulti.clientes) {
            //Con un if, chequeamos para no enviar el mensaje al remitente
            if ( ! cliente.usuario.getNombre().equals(nombreUsuario)) {
                    cliente.salida.println(
                            "Servidor::MSG_PUB::"
                            +this.usuario.getNombre()+"::"
                            +mensaje);
            }
        }
    }
    
    /**
     * Envía un mensaje de servicio a todos los usuarios conectados al servidor
     * @param mensaje 
     * @param tipo 
     */
    public void enviarMsgEstado(String mensaje, String tipo){
        
        //Enviamos el mensaje a todos los usuarios mediante un forEach
        for (HiloCliente cliente : ServidorMulti.clientes) {
                cliente.salida.println(
                        "Servidor::MSG_EST::"
                        +tipo+"::"
                        +mensaje);
        }
    }
    
    /**
     * Permite enviar un mensaje público del servidor
     * @param mensaje 
     */
    public void enviarMsgServidor(String mensaje){
        for (HiloCliente cliente : ServidorMulti.clientes) {
            cliente.salida.println(
                    "Servidor::MSG_SRV::"
                    +mensaje);
        }
    }
    
    
    /**
     * Permite enviar un mensaje privado a un usuario específico
     * @param mensaje
     * @param usuario 
     */
    public void enviarMsgPrivado(String mensaje, String usuario){
        
    }
    
    
    /**
     * Permite enviar un listado actualizado de usuarios a los clientes conectados
     */
    public void actualizarListaUsuarios(){
        String resultado = "";
        
        for (HiloCliente cliente : ServidorMulti.clientes) {
            resultado = resultado+cliente.usuario.getNombre()+"-@-";
        }
        
        enviarMsgEstado(resultado, "USR_LST");
        
    }
    //private void enviarMensajeAUsuario(String mensaje)
    
}
