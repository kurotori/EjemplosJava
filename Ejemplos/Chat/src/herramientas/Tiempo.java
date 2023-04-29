/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author sebastian
 */
public class Tiempo {
    
    /**
     * Devuelve la marca de tiempo del sistema de acuerdo al reloj del mismo.
     * @return la marca de tiempo del sistema
     */
    public String marcaTiempo(){
        String resultado="";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("(dd-MM-yyyy_HH-mm-ss)");
        LocalDateTime tiempo = LocalDateTime.now();
        resultado = tiempo.format(formateador) + "\t";
        return resultado;
    }
    
}
