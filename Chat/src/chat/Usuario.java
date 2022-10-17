/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

/**
 *
 * @author sebastian
 */
public class Usuario {
    private String nombre;
    private boolean activo;
    
    
    /**
     * Construye un objeto de clase Usuario para su uso en el Cliente
     * @param nombre 
     */
    public Usuario(String nombre){
        this.nombre = nombre;
        this.activo = true;
    }
    
    
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the conectado
     */
    public boolean esActivo() {
        return activo;
    }
    
    public void cambiarEstado(){
        this.activo = ! this.activo;
    } 
}
