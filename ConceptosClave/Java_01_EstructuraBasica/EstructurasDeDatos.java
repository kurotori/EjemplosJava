

package edu.utu.java_01_estructurabasica;

/**
 * Las Estrucuras de Datos permiten almacenar y manejar datos durante la ejecución
 * de los algoritmos del programa. Toda estructura de datos debe tener un nombre
 * para identificarla y volver a acceder a los datos de las mismas.
 * @author Luis Sebastián De los Ángeles
 */
public class EstructurasDeDatos {
    
    /**
     * VARIABLES: 
     * son las estructuras de almacenamiento de datos básicas, permiten
     * almacenar un dato con un tipo de datos definido. Para declarar una variable
     * se debe indicar para la misma un tipo de datos, un nombre y un valor inicial.
     * NOTA: Por convención, los nombres de variable deben escribirse con la letra 
     * inicial en minúscula.
     */
    
    TipoDeDatos nombreDeVariable = valorInicial;
    
    /**
     * NOTA: Este tipo de declaración sólo es válido para los tipos de datos 
     * denominados PRIMITIVOS; para más información acerca de estos tipos de 
     * datos, ver el documento en este link: 
     * https://tinyurl.com/y49h38uh
    */
    
    nombreDeVariable = nuevoValor;
    
    /**
     * Para modificar el valor almacenado en una variable, basta con igualar la
     * variable al nuevo valor que se le quiere asignar.
     */
    
    
    otraVariable = nombreDeVariable + 25;  //Se utilizó el valor de la variable para 
                                           //determinar el valor de otra variable
    System.out.println( nombreDeVariable );   //Se muestra el valor de la variable
                                              //en pantalla.
    
    /**
     * Para acceder a los datos de una variable (para usarlos para generar otros
     * datos, o para mostrar estos datos en pantalla, por ejemplo), basta con
     * incluir el nombre de la variable en donde irían los datos de la misma y
     * el programa realizará la substitución durante la ejecución.
     */
    
    
    
    
    /**
     * LISTAS, VECTORES o ARRAYS: son estructuras semejantes a las variables, pero
     * permiten almacenar varios datos del mismo tipo al mismo tiempo en una sola 
     * estructura.
     * La cantidad de datos que se pueden almacenar en una misma lista depende
     * de la cantidad de posiciones que esta tenga. Esta cantidad es denominada
     * LONGITUD, y se determina en el momento de la declaración de la estructura.
     * Una vez declarada la lista, su longitud no puede modificarse.
     * Para declarar una lista, se declara el tipo de datos, pero se le agregan
     * paréntesis rectos ( '[]' ) al nombre del tipo de datos. Luego se indica
     * el nombre de la lista. Para inicializar la lista, se la iguala al comando
     * de construcción ('new') y se vuelve a indicar el tipo de datos seguido de
     * paréntesis rectos, pero esta vez incluímos entre los paréntesis la longitud
     * de la lista.
     */
    
    TipoDeDatos[] nombreDeLista = new TipoDeDatos[10];
    
    /**
     * Para acceder a los datos de una lista, se utiliza el nombre de la lista y
     * la posición del dato en la lista. Esta posición es un número entero. Las
     * posiciones en una lista se comienzan a contar en 0, por lo que el número
     * que identifica a la última posición de la lista siempre es igual a la
     * longitud de la lista menos 1.
     */
    
    nombreDeLista[2] = nuevoValor;  //Se almacenó un valor en la posición 3 de la lista
    otraVariable = nombreDeLista[4]; //Se utiliza el valor de una posición de la lista
                                     //para determinar el valor de una variable.
    System.out.println(nombreDeLista[6]); //Se muestra el valor de una posición de
                                          //la lista en pantalla.
   
    
}
