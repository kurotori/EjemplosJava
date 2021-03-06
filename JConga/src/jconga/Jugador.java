/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jconga;

import baraja.Baraja;
import baraja.Carta;
import herramientas.Azar;
import java.util.ArrayList;
/**
 *
 * @author luiss
 */
public class Jugador {
    
    
    private String nombre="";
    private int ID = 0;
    public ArrayList<Carta> mano = new ArrayList<>();
    private ArrayList<JuegoDeCartas> juegos = new ArrayList<JuegoDeCartas>();

    private Azar utilesAzar = new Azar();
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    
    
    public void Barajar(Baraja baraja){
       
        for (int i = 0; i < 50; i++) {
            int posAzar = utilesAzar.NumeroAlAzar(50);
            Carta carta1 = baraja.cartas.get(i);
            Carta carta2 = baraja.cartas.get(posAzar);
            baraja.cartas.set(i, carta2);
            baraja.cartas.set(posAzar, carta1);
        }
    }
    
    
    public void Repartir(ArrayList<Jugador> jugadores, Baraja baraja){
        for (int c = 0; c < 7; c++) {
            for(Jugador jugador:jugadores){
                Carta carta = baraja.cartas.get(0);
                baraja.cartas.remove(0);
                jugador.mano.add(carta);
            }
        }
        
        Carta carta = baraja.cartas.get(0);
        baraja.cartas.remove(0);
        jugadores.get(1).mano.add(carta);
        
        System.out.println("j0:"+jugadores.get(0).mano.size());
        System.out.println("j1:"+jugadores.get(1).mano.size());
    }
    
    private void AgregarCartaAMano(Carta carta){
        this.mano.add(carta);
    }
    
    public void LevantarCarta(Baraja baraja){
        Carta carta = baraja.cartas.remove(0);
        AgregarCartaAMano(carta);
    }
    
    public void LevantarCarta(ArrayList<Carta> mazo){
        Carta carta = mazo.remove(0);
        AgregarCartaAMano(carta);
    }
    
   public void TirarCarta(Carta carta, ArrayList<Carta> mazo){
       
       mazo.add(0,carta);
       mano.remove(carta);
   }
}
