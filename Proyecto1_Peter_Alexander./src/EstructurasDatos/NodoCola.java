/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

/**
 * Clase que representa un Nodo en una cola.
 * Cada nodo contiene un dato y una referencia al siguiente nodo en la cola.
 * 
 * @author PeterNaddaf
 */
public class NodoCola {
    private NodoCola siguiente;
    private Object dato;

    /**
     * Constructor de la clase NodoCola.
     * Inicializa el nodo con el siguiente nodo en null y el dato en null.
     */
    public NodoCola() {
        this.siguiente = null;
        this.dato = null;
    }

    /**
     * Obtiene el siguiente nodo en la cola.
     * @return NodoCola siguiente en la cola.
     */
    public NodoCola getSiguiente() {
        return siguiente;
    }

    /**
     * Define el siguiente nodo en la cola.
     * @param siguiente NodoCola a asignar como siguiente.
     */
    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Obtiene el dato contenido en el nodo.
     * @return Objeto que representa el dato del nodo.
     */
    public Object getDato() {
        return dato;
    }

    /**
     * Define el dato contenido en el nodo.
     * @param dato Objeto a asignar como dato del nodo.
     */
    public void setDato(Object dato) {
        this.dato = dato;
    }
}