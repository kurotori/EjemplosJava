
package edu.utu.java_01_estructurabasica;

/***
 * NOTA: No se supone que este código sea compilable (de hecho, no lo es), 
 * su único propósito es demostrar la sintaxis de Java.
 * @author Luis Sebastián de los Ángeles
 */


/**
 * Estructuras de Control: Permiten que el programa controle el flujo de la ejecución
 * @author Luis Sebastián de los Ángeles
 */
public class EstructurasDeControl {
 
    public void EstructurasControl(){
    
        /***
        * Estructura Condicional (IF): Permite que el programa controle el flujo
        * de la ejecución dependiendo del valor (verdadero o falso) que tenga el 
        * chequeo de una condición. 
        * Las condiciones por lo general toman la forma de comparaciones.
        */
        
        if ( condicion ) //La condición entre paréntesis es la que controla la ejecución de la estructura
        {
            // Si la condición devuelve el valor VERDADERO, se ejecutan las sentencias
            //de este bloque.
        }
        else{
            // Si la condición devuelve el valor FALSO, se ejecutan las sentencias
            //de este bloque.
        }
        
        
        /**
         * BUCLES.
         */
         
        /**
         * Bucle FOR: Las sentencias contenidas en su bloque se ejecutan de forma
         * repetitiva una cantidad de veces determinada, que se establece en la 
         * declaración del FOR. Esta cantidad se determina mediante una variable 
         * interna de tipo int que sirve de contador. Una condición, que se verifica con cada ejecución
         * del bucle, chequea que el contador no supere el límite establecido. 
         * Finalmente, tras la ejecución de las sentencias del bloque, se ejecuta
         * la última sentencia de la definición del FOR, que establece cómo se 
         * debe modificar el contador tras cada ejecución.
         */
        
        for (int contador = 0;  //Esta es la variable que registra el avance del bucle.
             contador < 10;     //Esta es la condición que controla el valor límite que puede alcanzar el contador.
             contador++         //Esta sentencia establece la forma cómo debe avanzar el contador.
                ) 
        {
            //Las sentencias de este bloque se ejecutan de forma repetitiva.
        }
        
        
        /**
         * Bucle WHILE: Las sentencias contenidas en el bloque de esta sentencia
         * se ejecutan de forma repetitiva mientras la condición de control del
         * bucle devuelva VERDADERO al chequearla. El bucle WHILE chequea si su
         * condición se cumple (si devuelve VERDADERO) antes de ejecutar los contenidos
         * de su bloque. Si la condición se cumple, se ejecuta el contenido del bloque.
         * De lo contrario el bucle se cierra.
         * NOTA: una condición mal definida puede provocar que el bucle se transforme en
         * un bucle infinito.
         */
        
        while ( condición ) //La condición entre paréntesis es la que controla la ejecución del bucle
        {            
            //Las sentencias de este bloque se ejecutan de forma repetitiva.
        }
        
    }
    
}
