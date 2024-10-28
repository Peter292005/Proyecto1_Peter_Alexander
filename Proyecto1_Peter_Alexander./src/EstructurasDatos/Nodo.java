/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

/**
 * Clase que representa un Nodo en una lista enlazada.
 * Cada nodo contiene un elemento y una referencia al siguiente nodo.
 * 
 * @author PeterNaddaf
 */
public class Nodo {
    private Nodo siguiente;
    private Object elemento;

    /**
     * Constructor de la clase Nodo.
     * Inicializa el nodo con el siguiente nodo en null y el elemento en null.
     */
    public Nodo() {
        this.siguiente = null;
        this.elemento = null;
    }

    /**
     * Obtiene el siguiente nodo en la lista.
     * @return Nodo siguiente en la lista.
     */
    public Nodo getSiguiente() {
        return siguiente;
    }

    /**
     * Define el siguiente nodo en la lista.
     * @param siguiente Nodo a asignar como siguiente.
     */
    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Obtiene el elemento contenido en el nodo.
     * @return Objeto que representa el elemento del nodo.
     */
    public Object getElemento() {
        return elemento;
    }

    /**
     * Define el elemento contenido en el nodo.
     * @param elemento Objeto a asignar como elemento del nodo.
     */
    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    /**
     * Devuelve una representación en cadena del nodo.
     * @return String que muestra el elemento contenido en el nodo.
     */
    @Override
    public String toString() {
        return "Elemento: " + elemento;
    }
}


