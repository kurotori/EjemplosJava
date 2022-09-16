/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;

/**
 *
 * @author luiss
 */
public class Texto {
    
    
    /**
     * Interpreta un mensaje enviado por el usuario al servidor
     * @param mensaje
     * @return 
     */
    public String[] analizarMensaje(String mensaje){
        
        String[] resultado = mensaje.split("::");
        
        if (resultado.length < 2) {
            String[] r = {"ERROR","Mensaje no válido"};
            resultado = r;
        } 

        return resultado;
    }
    
}
