/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

/**
 *
 * @author mateusnaddaf
 */
public class Nodo {
    private Nodo siguiente;
    private Object elemento;

    public Nodo() {
        this.siguiente = null;
        this.elemento = null;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public Object getElemento() {
        return elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }

    @Override
    public String toString() {
        return "Elemento: " + elemento;
    }
}


