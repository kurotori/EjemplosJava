/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import herramientas.Texto;
import java.net.*;
import java.io.*;

/**
 *
 * @author luiss
 */
public class Servidor {
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    private BufferedReader entrada;
    private PrintWriter salida;
    
    Texto texto = new Texto();
    
    
    public void iniciar(int puerto){
        try {
            socketServidor = new ServerSocket(puerto);
            socketCliente = socketServidor.accept();
            
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
            entrada = new BufferedReader(lectorDeStream);
            
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            String mensaje = entrada.readLine();
            System.out.println( texto.analizarMensaje(mensaje) );
            
        } 
        catch (Exception e) {
            
        }
    }
    
    
    
    
}
