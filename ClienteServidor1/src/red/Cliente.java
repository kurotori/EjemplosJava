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
public class Cliente {
    private Socket socketCliente;
    private PrintWriter salida;
    private BufferedReader entrada;

    public void iniciarConexion(String ip, int port,String nombre) {
        try {
            socketCliente = new Socket(ip, port);
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());               
            entrada = new BufferedReader(lectorDeStream);
            
            enviarMensaje("LOGIN::"+nombre);
        } catch (IOException e) {
            System.out.println("ERROR al iniciar la conexión cliente:" + e.toString());
        }
        
        
    }
    
    
    public String enviarMensaje(String mensaje) {
        String resp="";
        try {
            salida.println(mensaje);
            resp = entrada.readLine();
            System.out.println("El servidor dice: "+resp);
        } catch (Exception e) {
            System.out.println("ERROR al enviar mensaje desde el cliente:" + e.toString());
        }
        
        return resp;
    }

    
    public void cerrarConexion() {
        try {
            entrada.close();
            salida.close();
            socketCliente.close();
        } catch (IOException e) {
            System.out.println("ERROR al cerrar la conexión del cliente:" + e.toString());
        }
    }
}
