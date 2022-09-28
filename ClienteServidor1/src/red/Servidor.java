/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import java.net.*;
import java.io.*;
/**
 *
 * @author sebastian
 */
public class Servidor {
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    private BufferedReader entrada;
    private PrintWriter salida;
    
    public void iniciar(int puerto){
        System.out.println("Iniciando el servidor en el puerto "+ puerto);
        try {
            socketServidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado");
            
            socketCliente = socketServidor.accept();
            System.out.println("Se ha conectado un cliente desde " + 
                    socketCliente.getInetAddress().getCanonicalHostName());
            
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
            entrada = new BufferedReader(lectorDeStream);
            
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            String mensaje = "";
            
            while ( ! mensaje.equalsIgnoreCase("salir") ) {                
                mensaje = entrada.readLine();
                salida.println("OK");
                System.out.println( "Se recibió: " + mensaje );
            }
            
            System.out.println("Cerrando el servidor...");
            entrada.close();
            salida.close();
            socketCliente.close();
            socketServidor.close();
            System.out.println("Servidor cerrado");
            
        } 
        catch (IOException e) {
            System.out.println("ERROR al iniciar el servidor: "+e.toString());
        }
    }
    
    
    public static void main(String[] args) {
        Servidor srv = new Servidor();
        srv.iniciar(6666);
    }
}
