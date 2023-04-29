/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

//import chat.Sala;
import herramientas.Texto;
import herramientas.Tiempo;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author luiss
 */
public class ServidorMulti implements Closeable{
    
    //Conexiones para el servidor
    private ServerSocket socketServidor;
    private Socket socketCliente;
    
    //Estado del servidor
    boolean estado = true;
    
    //
    //private BufferedReader entrada;
    //private PrintWriter salida;
    
    //Herramientas
    Texto texto = new Texto();
    Tiempo tiempo = new Tiempo();
    
    //Listados dinámicos de clientes conectados y usuarios en el sistema.
    static ArrayList<HiloCliente> clientes = new ArrayList<>();
    static ArrayList<String> nombresDeUsuario = new ArrayList<>();
    
    
    //Auxiliar temporal. A revisión más adelante.
    //Sala sala = new Sala("Común");
    
    /**
     * Permite iniciar el servidor en el puerto indicado
     * @param puerto el puerto para abrir e iniciar el servidor
     */
    public void iniciar(int puerto){
        System.out.println("Iniciando el servidor en el puerto "+ puerto);
        try {

            //Se abre el socket del servidor en el puerto indicado
            socketServidor = new ServerSocket(puerto);
            //Se lo configura para permitir múltiples conexiones
            socketServidor.setReuseAddress(true);
            socketServidor.setSoTimeout(5000);
            
            System.out.println(tiempo.marcaTiempo() + "Servidor iniciado");
            System.out.println(tiempo.marcaTiempo() + "Esperando clientes...");
            
            //Sub-hilo: Control del servicio
            Thread control = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Scanner teclado = new Scanner(System.in);
                        System.out.println("Escriba 'CERRAR' para cerrar el servidor...");
                        while(true){
                            String respuesta = teclado.nextLine();

                            if (respuesta.equalsIgnoreCase("cerrar")) {
                                estado = false;
                                cerrar();
                                break;
                            }
                            else{
                                System.out.println(tiempo.marcaTiempo() + 
                                        respuesta + " no es un comando reconocido");
                            }
                        }
                        teclado.close();
                    }
                }
            );
            
            
            //Sub-Hilo: Servicio
            Thread servicio = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (estado) {
                            
                            try {
                                //Se espera a que un cliente se conecte al servidor...
                                // Esta conexión se "recicla" para aceptar nuevas conexiones
                                socketCliente = socketServidor.accept();
                                
                                //...y cuando sucede se anuncian sus datos
                                System.out.println(tiempo.marcaTiempo()+"Se ha conectado un usuario desde la IP: "+
                                socketCliente.getInetAddress().getHostAddress());

                                //Entrada de datos desde el cliente conectado
                                InputStreamReader lectorDeStream = new InputStreamReader(socketCliente.getInputStream());
                                BufferedReader entrada = new BufferedReader(lectorDeStream);

                                //Salida de datos hacia el cliente conectado          
                                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

                                //Creamos un hilo para el cliente que se conecta. Y lo conectamos a 
                                // un objeto Thread para su ejecución.
                                HiloCliente cliente = new HiloCliente(socketCliente, entrada, salida);
                                Thread hilo = new Thread(cliente);

                                //Añadimos el cliente al listado de clientes e iniciamos el hilo.
                                clientes.add(cliente);
                                hilo.start();
                                
                                
                            } catch (SocketTimeoutException e) {
                                //System.out.println("Sin solicitudes... reseteando conexión");
                                //socketCliente = null;                             
                            } catch (IOException e){
                                System.out.println("ERROR al iniciar el servidor: "+e.toString());
                            }
                        }
                        //Si se cierra el bucle, el servidor cierra el socket y sus conexiones.
                        cerrar();
                    }
                }
            );
            
            
            control.start();
            servicio.start();
                    
        } 
        catch (IOException e) {
            System.out.println("ERROR al iniciar el servidor: "+e.toString());
        }
    }
    
    
    /**
     * Cierra el servidor y sus conexiones.
     */
     public void cerrar() {
        try {
            
            System.out.println(tiempo.marcaTiempo()+"Cerrando el Servidor...");
            //Cerramos los flujos de entrada y salida
            //entrada.close();
            //salida.close();
            //Cerramos el socket del cliente y el socket del servidor
            
            if (socketCliente!=null) {
                socketCliente.close();
            }
            
            System.out.println("Cerrando conexiones...");
            socketServidor.close();
         } 
        catch (Exception e) {
             System.out.println("ERROR al cerrar el servidor: "+e.toString());
         }
    }
     
     
    /**
     * Permite iniciar el servidor de forma automática.
     * @param args 
     */
    public static void main(String[] args) {    
        int puerto = 6666;         
        ServidorMulti servidor = new ServidorMulti();
        servidor.iniciar(puerto);
    }

    @Override
    public void close() throws IOException {
        cerrar();
    }
    
    

}
