/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import javax.swing.JOptionPane;

/**
 *
 * @author mateusnaddaf
 */
public class Grafo {
    private ListaSimple vertices;

    public Grafo() {
        this.vertices = new ListaSimple();
    }

    public ListaSimple getVertices() {
        return vertices;
    }

    public void setVertices(ListaSimple vertices) {
        this.vertices = vertices;
    }

    public int numeroVetices() {
        return this.vertices.getSize();
    }

    public boolean grafoVacio() {
        return this.vertices.esVacia();
    }

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

    public void insertarVertice(Vertice vertice) {
        if (this.buscarVertice(vertice) == null) {
            vertice.setNumVertice(this.numeroVetices());
            this.vertices.insertarFinal(vertice);
            JOptionPane.showMessageDialog(null, "Vertice insertado con exito.");
        }else{
           JOptionPane.showMessageDialog(null, "El vertice ya xiste");
        }
        
    }

    public void conectarVertices(Vertice vertice1, Vertice vertice2) {
        if(this.buscarVertice(vertice1) != null && this.buscarVertice(vertice2) != null){
            Vertice verticeInicio = this.buscarVertice(vertice1) ;
            Vertice verticeLlegada = this.buscarVertice(vertice2);
            
            verticeInicio.añadirAdyacencia(verticeLlegada);
            verticeLlegada.añadirAdyacencia(verticeInicio);
        }else{
            JOptionPane.showMessageDialog(null, "Alguno de los vertices no esta dentro del grafo.");
        }
    }

    public String mostrarVertice() {
        if (!this.grafoVacio()) {
            return this.vertices.transformar();
        }

        return null;
    }

    public void mostrar() {
        this.vertices.mostrar();
    }
}


