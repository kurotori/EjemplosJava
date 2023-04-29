/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jconga.red;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luiss
 */
public class Cliente {
    private Socket socketCliente;
    private PrintWriter salida;
    private BufferedReader entrada;
    private Herramientas h = new Herramientas();
    
        
    public void escanearRed(){
      
    }
    
    
    public void iniciarConexion(String ip, int port) throws IOException {
        try {
            socketCliente = new Socket(ip, port);
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());               
            entrada = new BufferedReader(lectorDeStream);
            
        } 
        catch (IOException e) {
            System.out.println("ERROR: Sin Conexión en "+ip+" - "+e.toString());
        }
        
    }

    public String sendMessage(String msg) throws IOException {
        salida.println(msg);
        String resp = entrada.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {
        entrada.close();
        salida.close();
        socketCliente.close();
    }
}
