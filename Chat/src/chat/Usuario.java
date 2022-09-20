/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.net.Socket;

/**
 *
 * @author sebastian
 */
public class Usuario {
    private String nombre;
    private Socket conexion;
    
    /**
     * Construye un objeto de clase Usuario para su uso en el Servidor
     * @param nombre
     * @param conexion 
     */
    public Usuario(String nombre, Socket conexion){
        this.nombre = nombre;
        this.conexion = conexion;
    }
    
    
    /**
     * Construye un objeto de clase Usuario para su uso en el Cliente
     * @param nombre 
     */
    public Usuario(String nombre){
        this.nombre = nombre;
        this.conexion = null;
    }
    
    
    public String getNombre() {
        return nombre;
    }
    
   
}
