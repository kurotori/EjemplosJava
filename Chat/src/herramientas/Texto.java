/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

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
    public String[] analizarSolicitud(String mensaje){
        String[] resultado = {""};
        try {
            resultado = mensaje.split("::");
        } catch (Exception e) {
            String[] r ={"ERROR","Mensaje vacío"};
            resultado = r;
        }
         
        
        if (resultado.length < 2) {
            String[] r = {"ERROR","Mensaje no válido"};
            resultado = r;
        }
        else{
            
        }

        return resultado;
    }
    
    
    public String[] analizarListaUsuarios(String dato){
        String[] resultado = new String[1];
        
        try {
            resultado = dato.split("-@-");
        } catch (Exception e) {
            String[] r ={"ERROR","Mensaje vacío"};
            resultado = r;
        }
        
        return resultado;
    }
    
    
    /**
     * Devuelve configuraciones de texto útiles para mostrar
     * @return 
     */
    public SimpleAttributeSet[] atributosDeTexto(){
        SimpleAttributeSet[] atributos = new SimpleAttributeSet[10];
        
        SimpleAttributeSet negrita = new SimpleAttributeSet();
        StyleConstants.setBold(negrita, true);
        atributos[0] = negrita;
        
        SimpleAttributeSet cursiva = new SimpleAttributeSet();
        StyleConstants.setItalic(cursiva, true);
        atributos[1] = cursiva;
        
        SimpleAttributeSet derecha = new SimpleAttributeSet();
        StyleConstants.setAlignment(derecha, StyleConstants.ALIGN_RIGHT);
        atributos[2] = derecha;
        
        SimpleAttributeSet negritaDerecha = new SimpleAttributeSet();
        StyleConstants.setBold(negritaDerecha, true);
        StyleConstants.setAlignment(negritaDerecha, StyleConstants.ALIGN_RIGHT);
        atributos[3] = negritaDerecha;
        
        return atributos;
        
    }
    
    
}
