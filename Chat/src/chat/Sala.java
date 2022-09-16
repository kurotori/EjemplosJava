/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.util.ArrayList;

/**
 *
 * @author sebastian
 */
public class Sala {
    public ArrayList<Usuario> usuarios =  new ArrayList<>();
    
    public void agregarUsuario(Usuario usuario){
        usuarios.add(usuario);
        System.out.println("Se ha agregado al usuario "+ usuario.getNombre());
    }
}
