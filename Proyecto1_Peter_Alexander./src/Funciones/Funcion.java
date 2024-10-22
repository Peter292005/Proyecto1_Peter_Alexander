/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import EstructurasDatos.Grafo;
import EstructurasDatos.ListaSimple;
import EstructurasDatos.Vertice;

/**
 *
 * @author mateusnaddaf
 */
public class Funcion {
    public void cargarGrafo(ListaSimple vertices, Grafo grafo){
        if(!vertices.esVacia()){
            for (int i = 0; i < vertices.getSize(); i++) {
                Vertice verticeActual = (Vertice) vertices.getValor(i);
                grafo.insertarVertice(verticeActual);
            }
        }
    }
}
