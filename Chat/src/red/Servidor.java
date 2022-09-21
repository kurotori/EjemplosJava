/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import herramientas.Texto;
import herramientas.Tiempo;
import java.net.*;
import java.io.*;

/**
 *
 * @author luiss
 */
public class Servidor{
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    private BufferedReader entrada;
    private PrintWriter salida;
    
    Texto texto = new Texto();
    Tiempo tiempo = new Tiempo();
    
    
    public void iniciar(int puerto){
        System.out.println("Iniciando el servidor en el puerto "+ puerto);
        try {
            //Se inicializa el socket del servidor
            socketServidor = new ServerSocket(puerto);
            System.out.println(tiempo.marcaTiempo()+"Servidor iniciado");
            
            //Se habilita la conexión de un cliente y se espera a que se realice
            // la conexión
            socketCliente = socketServidor.accept();
            System.out.println(tiempo.marcaTiempo()+"Se ha conectado un usuario desde la IP: "+
                    socketCliente.getInetAddress().getHostAddress());
            
            //Entrada de datos desde el cliente conectado
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
            entrada = new BufferedReader(lectorDeStream);
            
            //Salida de datos hacia el cliente conectado          
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            //Preparamos un String para los datos de entrada desde el cliente
            String mensaje = "";
            
            //Abrimos un bucle que se cierra con un mensaje específico
            while (!mensaje.equalsIgnoreCase("salir")) {                
                mensaje = entrada.readLine();
                System.out.println( 
                        tiempo.marcaTiempo() + 
                        socketCliente.getInetAddress().getHostAddress() + 
                        ":" + mensaje );
            }
        } 
        catch (IOException e) {
            System.out.println("ERROR al iniciar el servidor: "+e.toString());
        }
    }
    
    
     public void cerrar() {
        try {
            entrada.close();
            salida.close();
            socketCliente.close();
            socketServidor.close();
         } catch (IOException e) {
             System.out.println("ERROR al cerrar el servidor: "+e.toString());
         }
        
    }
    
     /**
      * Permite iniciar el servid    * @param args 
      */
     public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar(6666);
    }
}
