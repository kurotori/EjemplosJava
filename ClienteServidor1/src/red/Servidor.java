/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import chat.Usuario;
import herramientas.Texto;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author sebastian
 */
public class Servidor {
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    private BufferedReader entrada;
    private PrintWriter salida;
    
    private Texto texto = new Texto();
    
    ArrayList<Usuario> usuarios = new ArrayList<>();
    /**
     * Inicia el servidor en el puerto indicado
     * @param puerto el puerto del sistema utilizado por el servidor
     */
    public void iniciar(int puerto){
        System.out.println("Iniciando el servidor en el puerto "+ puerto+"...");
        try {
            socketServidor = new ServerSocket(puerto);
            System.out.println("Servidor Iniciado");
            System.out.println("Esperando clientes...");
            
            socketCliente = socketServidor.accept();
            System.out.println("Se ha conectado un cliente desde " + 
                    socketCliente.getInetAddress().getCanonicalHostName());
            
            InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
            entrada = new BufferedReader(lectorDeStream);
            
            salida = new PrintWriter(socketCliente.getOutputStream(), true);
            
            String solicitud = "";
            
            while ( ! solicitud.equalsIgnoreCase("salir") ) {                
                
                solicitud = entrada.readLine();
                
                String[] datosSolicitud = texto.analizarSolicitud(solicitud);
                ejecutarSolicitud(datosSolicitud);
                
                salida.println( datosSolicitud[0] );
                System.out.println("Se recibió: " + datosSolicitud[1] );
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
    
    /**
     * Inicia el servidor automáticamente
     * @param args 
     */
    public static void main(String[] args) {
        Servidor srv = new Servidor();
        srv.iniciar(6666);
    }
    
    private void ejecutarSolicitud(String[] datosSolicitud){
        switch(datosSolicitud[0]){
            case "LOGIN":
                Usuario user = new Usuario(datosSolicitud[1]);
                usuarios.add(user);
                System.out.println(user.getNombre() + " ha iniciado sesión");
                salida.println("Bienvenido al servidor");
                break;
            case "ERROR":
                break;
            case "MSG":
                break;
        }
    }
    
}
