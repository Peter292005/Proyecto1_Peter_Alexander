/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import javax.swing.JOptionPane;



/**
 * 
 * Clase que representa una Lista Simplemente Enlazada.
 * Permite agregar, eliminar y buscar elementos.
 * @author PeterNaddaf
 */
public class ListaSimple {
    
    private Nodo pFirts;
    private int size;

    /**
     * Constructor de la clase ListaSimple.
     * Inicializa la lista vacía.
     */
    public ListaSimple() {
        this.pFirts = null;
        this.size = 0;
    }

    /**
     * Obtiene el primer nodo de la lista.
     * @return Nodo que representa el primer elemento de la lista.
     */
    public Nodo getpFirts() {
        return pFirts;
    }

    /**
     * Define el primer nodo de la lista.
     * @param pFirts Nodo a asignar como primer elemento.
     */
    public void setpFirts(Nodo pFirts) {
        this.pFirts = pFirts;
    }

    /**
     * Obtiene el tamaño de la lista.
     * @return int con el número de elementos en la lista.
     */
    public int getSize() {
        return size;
    }

    /**
     * Establece el tamaño de la lista.
     * @param size Número de elementos en la lista.
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Verifica si la lista está vacía.
     * @return true si la lista está vacía, false si tiene elementos.
     */
    public boolean esVacia() {
        return this.pFirts == null;
    }

    /**
     * Inserta un nuevo elemento al final de la lista.
     * @param dato Objeto a agregar en la lista.
     */
    public void insertarFinal(Object dato) {
        Nodo pNew = new Nodo();
        pNew.setElemento(dato);
        if (this.esVacia()) {
            this.setpFirts(pNew);
        } else {
            Nodo aux = this.pFirts;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(pNew);
        }
        size++;
    }

    /**
     * Elimina el último elemento de la lista.
     */
    public void eliminarFinal() {
        if (!this.esVacia()) {
            if (this.getSize() == 1) {
                this.setpFirts(null);
            } else {
                Nodo aux = this.pFirts;
                while (aux.getSiguiente().getSiguiente() != null) {
                    aux = aux.getSiguiente();
                }
                aux.setSiguiente(null);
            }
        }
    }

    /**
     * Busca un elemento en la lista.
     * @param ref Objeto a buscar en la lista.
     * @return true si el elemento se encuentra, false si no.
     */
    public boolean buscar(Object ref) {
        if (!this.esVacia()) {
            Nodo aux = this.pFirts;
            while (aux != null) {
                if(aux.getElemento() == ref){
                    return true;
                }
                aux = aux.getSiguiente();
            }
        }
        return false;
    }
    
    /**
     * Transforma la lista en una cadena de texto con los elementos en orden.
     * @return String con todos los elementos de la lista enlazados por " -> ".
     */
    public String transformar(){
        if (!this.esVacia()) {
            String listaStr = "";
            Nodo aux = this.pFirts;
            while (aux != null) {
                Vertice vertice = (Vertice) aux.getElemento();
                listaStr += vertice.getParada().getNombre();
                if (aux.getSiguiente() != null) {
                    listaStr += " -> ";
                }
                aux = aux.getSiguiente();
            }
            return listaStr;
        } else {
            return "La lista está vacía";
        }
    }

    /**
     * Obtiene el valor en una posición específica de la lista.
     * @param posicion Posición del elemento en la lista.
     * @return Objeto en la posición dada o null si está fuera de rango.
     */
    public Object getValor(int posicion) {
        if (posicion >= 0 && posicion < size) {
            Nodo aux = this.pFirts;
            for (int i = 0; i < posicion; i++) {
                aux = aux.getSiguiente();
            }
            return aux.getElemento();
        }
        return null;
    }

    /**
     * Convierte la lista en una representación en cadena de texto.
     * @return String con todos los elementos en la lista separados por líneas.
     */
    @Override
    public String toString() {
        if (!this.esVacia()) {
            String listaStr = "";
            Nodo aux = this.pFirts;
            while (aux != null) {
                listaStr += aux.getElemento().toString();
                if (aux.getSiguiente() != null) {
                    listaStr += "\n";
                }
                aux = aux.getSiguiente();
            }
            return listaStr;
        } else {
            return "La lista está vacía";
        }
    }

    /**
     * Muestra la lista en un cuadro de diálogo.
     */
    public void mostrar(){
        JOptionPane.showMessageDialog(null, this.toString());
    }

    /**
     * Destruye la lista, dejándola vacía.
     */
    public void destruir(){
        this.pFirts = null;
        this.size = 0;
    }
}