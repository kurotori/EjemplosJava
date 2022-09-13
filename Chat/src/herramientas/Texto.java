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
    
    public String analizarMensaje(String mensaje){
        String resultado="";
        
        String[] datosMensaje = mensaje.split("::");
        
        resultado = datosMensaje[0]+" dice: "+datosMensaje[1];
        
        return resultado;
    }
    
}
