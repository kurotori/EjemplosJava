/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

/**
 *
 * @author sebastian
 */
public class Mensaje {
    
    private String tipo;
    private String datos;
    
    public Mensaje(String tipo, String datos){
        this.tipo = tipo;
        this.datos = datos;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return the datos
     */
    public String getDatos() {
        return datos;
    }
    
    
}
