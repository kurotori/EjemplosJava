/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package herramientas;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 *Herramientas para el manejo de archivos
 * @author luiss
 */
public class Archivo {
    
    private String sop;
    
    public Archivo(){
        //Detección de SO

        this.sop = System.getProperty("os.name");
    }
    
    
    
    /**
     * Permite crear un archivo en la ruta y con el nombre indicado, si ya
     * existe no lo sobreescribe.
     * 
     * @param ruta ruta en la cuál se creará el archivo.
     * @param nombre nombre del archivo a crear.
     * @return 
     */
    public String CrearArchivo(String ruta, String nombre){
        String resultado = "";

        try{
            File archivo = new File(ruta+nombre);
            
            if( ! archivo.exists()){
                
                archivo.createNewFile();
                resultado="N;";
                System.out.println("Archivo creado: " + archivo.getName() );
                
            }
            else{
                resultado="S;";
                System.out.println("Archivo " + archivo.getName() + " ya existe");
                
            }
            resultado += archivo.getCanonicalPath();
            
        }
        catch(IOException error){
            resultado = "E;ERROR CrearArchivo: "+ error.getMessage();
        }

        return resultado;
    }
    
    /**
     * Permite leer el contenido de un archivo y pasarlo a una
     * variable de tipo String.
     * @param archivo La ruta completa hacia el archivo a leer.
     * @return El contenido del archivo.
     */
    public String LeerArchivo(Path archivo){
        String resultado = "";
        File arch = archivo.toFile();
        
        if(arch.exists()){
            try {
                resultado = Files.readString(archivo);
            } catch (IOException error) {
                resultado = "ERROR: " + error.getMessage();
            }
        }
        else{
            resultado = "ERROR: El archivo " + archivo.toString() + " no existe";
        }
        
        
        return resultado;
    }
    
    /**
     * Permite guardar un dato guardado en un string en un archivo.
     * @param archivo Ruta al archivo
     * @param dato String a guardar
     */
    public void StringAArchivo(Path archivo, String dato){
        
        File arch = archivo.toFile();
        
        if(arch.exists()){
           try {
                FileWriter escritor = new FileWriter(arch,false);
                escritor.write(dato);
                escritor.close();
           } 
           catch (IOException e) {
               System.out.println(
                       "ERROR StringAArchivo: "
                       + e.getMessage()
                       + "\n" + e.toString() );
           } 
        }
        
        
        
    }
}
