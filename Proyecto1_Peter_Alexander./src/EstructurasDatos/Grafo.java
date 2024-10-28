/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import javax.swing.JOptionPane;

/**
 * 
 * Clase que representa un Grafo con una lista de vértices.
 * Permite agregar vértices y conexiones entre ellos.
 * @author PeterNaddaf
 */
public class Grafo {
    private ListaSimple vertices;

    /**
     * Constructor de la clase Grafo.
     * Inicializa la lista de vértices vacía.
     */
    public Grafo() {
        this.vertices = new ListaSimple();
    }

    /**
     * Obtiene la lista de vértices del grafo.
     * @return ListaSimple que contiene los vértices.
     */
    public ListaSimple getVertices() {
        return vertices;
    }

    /**
     * Establece la lista de vértices del grafo.
     * @param vertices ListaSimple con los vértices a asignar.
     */
    public void setVertices(ListaSimple vertices) {
        this.vertices = vertices;
    }

    /**
     * Retorna el número de vértices en el grafo.
     * @return int con el número de vértices.
     */
    public int numeroVetices() {
        return this.vertices.getSize();
    }

    /**
     * Verifica si el grafo está vacío.
     * @return true si el grafo no tiene vértices, false si tiene al menos uno.
     */
    public boolean grafoVacio() {
        return this.vertices.esVacia();
    }

    /**
     * Busca un vértice en el grafo.
     * @param vertice Vértice a buscar.
     * @return Vertice si se encuentra, null si no está.
     */
    public Vertice buscarVertice(Vertice vertice) {
        if (!this.grafoVacio()) {
            Nodo aux = this.vertices.getpFirts();
            while (aux != null) {
                Vertice verticeActual = (Vertice) aux.getElemento();
                if (verticeActual.getParada().getNombre().equalsIgnoreCase(vertice.getParada().getNombre())) {
                    return verticeActual;
                }
                aux = aux.getSiguiente();
            }
            return null;
        }
        return null;
    }

    /**
     * Busca un vértice en el grafo por su nombre.
     * @param nombre Nombre del vértice a buscar.
     * @return Vertice si se encuentra, null si no está.
     */
    public Vertice buscarVerticePorNombre(String nombre) {
        if (!this.grafoVacio()) {
            Nodo aux = this.vertices.getpFirts();
            while (aux != null) {
                Vertice verticeActual = (Vertice) aux.getElemento();
                if (verticeActual.getParada().getNombre().equalsIgnoreCase(nombre)) {
                    return verticeActual;
                }
                aux = aux.getSiguiente();
            }
            return null;
        }
        return null;
    }

    /**
     * Inserta un nuevo vértice en el grafo si no existe ya.
     * @param vertice Vértice a agregar.
     */
    public void insertarVertice(Vertice vertice) {
        if (this.buscarVertice(vertice) == null) {
            vertice.setNumVertice(this.numeroVetices());
            this.vertices.insertarFinal(vertice);
        } else {
            JOptionPane.showMessageDialog(null, "El vértice ya existe");
        }
    }

    /**
     * Conecta dos vértices en el grafo, haciendo que cada uno sea adyacente al otro.
     * @param vertice1 Primer vértice a conectar.
     * @param vertice2 Segundo vértice a conectar.
     */
    public void conectarVertices(Vertice vertice1, Vertice vertice2) {
        if (this.buscarVertice(vertice1) != null && this.buscarVertice(vertice2) != null) {
            Vertice verticeInicio = this.buscarVertice(vertice1);
            Vertice verticeLlegada = this.buscarVertice(vertice2);

            verticeInicio.añadirAdyacencia(verticeLlegada);
            verticeLlegada.añadirAdyacencia(verticeInicio);
        } else {
            JOptionPane.showMessageDialog(null, "Alguno de los vértices no está dentro del grafo.");
        }
    }

    /**
     * Muestra la representación en cadena de todos los vértices del grafo.
     * @return String con los vértices o null si el grafo está vacío.
     */
    public String mostrarVertice() {
        if (!this.grafoVacio()) {
            return this.vertices.transformar();
        }
        return null;
    }

    /**
     * Muestra todos los vértices del grafo en un cuadro de diálogo.
     */
    public void mostrar() {
        this.vertices.mostrar();
    }
    
    /**
     * Destruye el grafo, eliminando todos sus vértices.
     */
    public void destruir(){
        this.vertices.destruir();
    }
}