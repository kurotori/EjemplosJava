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
    
    
    /**
     * Permite iniciar el servidor en el puerto indicado
     * @param puerto el puerto para abrir e iniciar el servidor
     */
    public void iniciar(int puerto){
        System.out.println("Iniciando el servidor en el puerto "+ puerto);
        try {
            //Se abre el socket del servidor en el puerto indicado
            socketServidor = new ServerSocket(puerto);
            System.out.println(tiempo.marcaTiempo()+"Servidor iniciado");
            
            //Se espera a que un cliente se conecte al servidor...
            socketCliente = socketServidor.accept();
            //...y cuando sucede se anuncian sus datos
            System.out.println(tiempo.marcaTiempo()+"Se ha conectado un usuario desde la IP: "+
                    socketCliente.getInetAddress().getHostAddress());
            
            //Entrada de datos desde el cliente conectado
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
            entrada = new BufferedReader(lectorDeStream);
            
            //Salida de datos hacia el cliente conectado          
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            //Variable para almacenar los datos enviados desde el cliente
            String mensaje = "";
            
            //Bucle de conexión. 
            while (!mensaje.equalsIgnoreCase("salir")) {                
                mensaje = entrada.readLine();
                System.out.println( 
                        tiempo.marcaTiempo() + 
                        socketCliente.getInetAddress().getHostAddress() + 
                        ":" + mensaje );
            }
            
            //Si se cierra el bucle, el servidor cierra el socket y sus conexiones.
            cerrar();
                    
                   
            
        } 
        catch (IOException e) {
            System.out.println("ERROR al iniciar el servidor: "+e.toString());
        }
    }
    
    
    //
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
    
     
     public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.iniciar(6666);
    }
}
