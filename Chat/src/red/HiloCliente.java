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
                //Mensajes enviados por un usuario
                case "MSG":
                    
                    switch (datosMensaje[2]) {
                        case "PUB":
                            enviarMsgPublico(datosMensaje[3]);
                            break;
                    }
                    
                    
                    break;
                    
                //Comandos enviados desde la aplicación cliente    
                case "CMD":
                    //Evaluación de los comandos
                    switch (datosMensaje[2]) {
                        case "LOGIN":
                            String nombre = datosMensaje[3];
                            loginUsuario(nombre);
                            break;
                        case "LOGOUT":
                            logoutUsuario();
                            break;
                        default:
                            throw new AssertionError();
                    }
                    break;
                
                //
                default:
                    throw new AssertionError();
            }
        }
    }
    
    /**
     * Agrega un usuario a la lista de usuarios y lo identifica en el sistema
     * @param nombreUsuario 
     */
    private void loginUsuario(String nombreUsuario){
        //Chequeamos si ya hay un usuario con ese nombre en el servidor
        if (ServidorMulti.nombresDeUsuario.contains(nombreUsuario)) {
            //Si existe un usuario, bloqueamos su ingreso
            salida.println("Servidor::MSG_EST::ERROR::Este nombre de usuario ya esta en uso");
        } else {
            //De lo contrario, añadimos su nombre a la conexión y a la lista de usuarios
            this.usuario = new Usuario(nombreUsuario);
            ServidorMulti.nombresDeUsuario.add(nombreUsuario);
            
            //Le enviamos datos identificatorios y un mensaje de bienvenida
            salida.println("Servidor::MSG_EST::LOGIN_OK::"+this.id);
            salida.println("Servidor::MSG_SRV::Bienvenido "+nombreUsuario);
            
            //Anunciamos a los otros usuarios que el usuario se ha conectado
            enviarMsgPublico( this.usuario.getNombre() + 
                 " ha entrado al servidor" );
            
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
        enviarMsgPublico( this.usuario.getNombre() + 
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
     */
    public void enviarMsgServicio(String mensaje, String tipo){
        
        //Enviamos el mensaje a todos los usuarios mediante un forEach
        for (HiloCliente cliente : ServidorMulti.clientes) {
                cliente.salida.println(
                        "Servidor::MSG_SRV::"
                        +tipo+"::"
                        +mensaje);
        }
    }
    
    
    
    public void actualizarListaUsuarios(){
        String resultado = "";
        
        for (HiloCliente cliente : ServidorMulti.clientes) {
            resultado = resultado+cliente.usuario.getNombre()+"-@-";
        }
        
        enviarMsgServicio(resultado, "USR_LST");
        
    }
    //private void enviarMensajeAUsuario(String mensaje)
    
}
