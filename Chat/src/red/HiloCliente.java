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
                "Cliente: "+socketCliente.getInetAddress().getCanonicalHostName()
               +"ID: "+id );
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
                if ( mensaje == null ) {
                    System.out.println("Nulo");
                }
                //Respondemos al cliente con una confirmación
                //salida.println("Servidor::MSG_EST::OK");

                //Análisis del mensaje
                String[] datosMensaje = texto.analizarSolicitud(mensaje);
                procesarMensaje(datosMensaje);
                
                //Mostramos un registro del mensaje recibido
                System.out.println( 
                        tiempo.marcaTiempo() + 
                        socketCliente.getInetAddress().getHostAddress() + 
                        ":" + mensaje );
                }
            }
            catch(IOException e){
                System.out.println("ERROR: mensaje nulo");
                System.out.println(e.getMessage());
                mensaje = "salir";
            }
            catch (NullPointerException e) {
                System.out.println("ERROR: mensaje nulo");
                System.out.println(e.getMessage());
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
                    for (HiloCliente cliente : ServidorMulti.clientes) {
                        cliente.salida.println(datosMensaje[2]);
                    }
                    break;
                    
                //Comandos enviados desde la aplicación cliente    
                case "CMD":
                    //Evaluación de los comandos
                    switch (datosMensaje[2]) {
                        case "LOGIN":
                            String nombre = datosMensaje[3];
                            System.out.println("el nombre recibido es " + nombre);
                            loginUsuario(nombre);
                            break;
                        case "LOGOUT":
                            
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
            salida.println("Servidor::MSG_EST::ERROR::Este nombre de usuario ya esta en uso");
        } else {
            this.usuario = new Usuario(nombreUsuario);
            ServidorMulti.nombresDeUsuario.add(nombreUsuario);
            salida.println("Servidor::MSG_EST::LOGIN_OK::"+this.id);
            salida.println("Servidor::MSG_SRV::Bienvenido "+nombreUsuario);
        }
        
        
        
        
        System.out.println(
                tiempo.marcaTiempo() + 
                "El usuario " + this.usuario.getNombre() + 
                 " ha iniciado sesión desde el cliente " + this.id);
    }
    
}
