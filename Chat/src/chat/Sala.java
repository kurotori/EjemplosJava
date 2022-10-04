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
    private String nombre;
    private ArrayList<Usuario> usuarios =  new ArrayList<>();
    
    public Sala(String nombre){
        this.nombre = nombre;
    }
    
    
    public void agregarUsuario(Usuario usuario){
        usuarios.add(usuario);
        System.out.println("Se ha agregado al usuario "+ usuario.getNombre());
        listarUsuarios();
    }
    
    public void listarUsuarios(){
        System.out.println("Sala: "+ this.nombre + " Usuarios Conectados: "+ usuarios.size());
        
        for (Usuario usuario : usuarios) {
            System.out.println(usuarios.indexOf(usuario) +" - " + usuario.getNombre());
        }
    }
}
