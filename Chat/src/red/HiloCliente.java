/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import chat.Usuario;
import java.io.BufferedReader;
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
    
    public HiloCliente(Socket socketCliente, 
                       BufferedReader entrada, 
                       PrintWriter salida){
        
        this.socketCliente = socketCliente;
        this.entrada = entrada;
        this.salida = salida;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
