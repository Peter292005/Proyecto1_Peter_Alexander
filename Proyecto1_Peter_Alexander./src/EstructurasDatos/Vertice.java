/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import MainClass.Parada;
import javax.swing.JOptionPane;

/**
 *
 * @author PeterNaddaf
 */
public class Vertice {
    private Parada parada;
    private int numVertice;
    private ListaSimple listaAdyacencia;

    public Vertice(Parada parada) {
        this.parada = parada;
        this.numVertice = -1;
        this.listaAdyacencia = new ListaSimple();
    }

    public Parada getParada() {
        return parada;
    }

    public void setParada(Parada parada) {
        this.parada = parada;
    }

    public int getNumVertice() {
        return numVertice;
    }

    public void setNumVertice(int numVertice) {
        this.numVertice = numVertice;
    }

    public ListaSimple getListaAdyacencia() {
        return listaAdyacencia;
    }

    public void setListaAdyacencia(ListaSimple listaAdyacencia) {
        this.listaAdyacencia = listaAdyacencia;
    }
    
    public void añadirAdyacencia(Vertice vertice){
        if(!this.listaAdyacencia.buscar(vertice)){
            this.listaAdyacencia.insertarFinal(vertice);
        }
    }
    
    public String mostrarListaAdyacencia(){
        if(!this.listaAdyacencia.esVacia()){
            return listaAdyacencia.transformar();
        }
        return null;
    }

    @Override
    public String toString() {
        if(this.mostrarListaAdyacencia() != null){
            return numVertice + ", " + parada.getNombre() + " -> " + this.mostrarListaAdyacencia();
        } 
        return numVertice + ", " + parada.getNombre();
    }
    
    public void mostrar(){
        JOptionPane.showMessageDialog(null, this.toString());
    }
    
}


