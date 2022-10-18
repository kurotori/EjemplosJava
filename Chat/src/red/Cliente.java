/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import chat.Usuario;
import herramientas.Tiempo;
import java.net.*;
import java.io.*;

/**
 *
 * @author sebastian
 */
public class Cliente {
    // Socket para la conexi[on con el servidor
    private Socket socketCliente;
    // Objeto para enviar datos hacia el servidor
    private PrintWriter salida;
    // Objeto para recibir datos desde el servidor
    private BufferedReader entrada;
    
    Tiempo tiempo = new Tiempo();
    

    /**
     * Inicia la conexión con el servidor en la <b>ip</b> indicada.
     * @param ip IP del servidor
     * @param port puerto utilizado por el servidor
     */
    public void iniciarConexion(String ip, int port) {
        try {
            //Iniciamos la conexión con el servidor
            socketCliente = new Socket(ip, port);
            
            //Vinculamos la salida del cliente con el flujo de salida del socket
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            //Creamos un lector de flujo de datos y lo vinculamos al flujo de 
            // datos que llegan del servidor...
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());               
            // ...y lo vinculamos a la entrada.
            entrada = new BufferedReader(lectorDeStream);
        } 
        catch (IOException e) {
            System.out.println("ERROR al iniciar conexión cliente:" + e.toString());
        }
        
    }
    
    
    /**
     * Cierra la conexión iniciada con un servidor
     */
    public void cerrarConexion() {
        try {
            entrada.close();
            salida.close();
            socketCliente.close();
        } catch (IOException e) {
            System.out.println("ERROR al cerrar la conexión del cliente:" + e.toString());
        }
    }
    
    
    
    /**
     * Envía una solicitud identificado al usuario
     * @param usuario 
     * @param datos 
     * @param tipo
     */
    public String enviarSolicitud(Usuario usuario, String tipo, String datos) {
        String respuesta="";
        try {
            salida.println(usuario.getNombre()+"::"+tipo+"::"+datos);
            respuesta = recibirMensaje();//entrada.readLine();
            System.out.println(tiempo.marcaTiempo() +  respuesta);
        }
        catch (Exception e) {
            System.out.println("ERROR al enviar solicitud desde el cliente:" + e.toString());
        }
        
        return respuesta;
    }
    
    
    /**
     * Espera un mensaje desde el servidor
     * @return un String con el mensaje
     */
    public String recibirMensaje(){
        String resp="";
        try {
            resp = entrada.readLine();
        } catch (IOException e) {
            System.out.println("ERROR al recibir mensaje desde el servidor:" + e.toString());
        }
        return resp;
    }
    

    /**
     * Envia una solicitud para iniciar sesión en el servidor
     * @param usuario
     * @return 
     */
    public String loginUsuario(Usuario usuario){
        String respuesta = enviarSolicitud(usuario,"CMD::LOGIN",usuario.getNombre());
        //String respuesta = recibirMensaje();
        return respuesta;
    }
    
    
            
    
}
