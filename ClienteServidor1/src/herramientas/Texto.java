/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;

/**
 *
 * @author sebastian
 */
public class Texto {
    
    public String[] analizarSolicitud( String solicitud ){
        String[] resultado = new String[1];
        resultado = solicitud.split("::");
        
        if (resultado.length < 2) {
            String[] error = {"ERROR","Solicitud no válida"};
            resultado = error;
        }
        
        return resultado;
    }
    
}
