/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

import red.Servidor;
import ventanas.Inicio;

/**
 *
 * @author sebastian
 */
public class Chat {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if ( args.length > 0 ) {
            switch (args[0]) {
                case "-s":
                    Servidor servidor = new Servidor();
                    servidor.iniciar(6666);
                    break;
                default:
                    throw new AssertionError();
            }
        } else {
            Inicio inicio = new Inicio();
            inicio.setVisible(true);
        }
    }
    
}
