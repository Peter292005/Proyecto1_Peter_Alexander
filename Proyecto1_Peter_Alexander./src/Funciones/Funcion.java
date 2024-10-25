/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import EstructurasDatos.Grafo;
import EstructurasDatos.ListaSimple;
import EstructurasDatos.Vertice;
import MainClass.Parada;
import javax.swing.JOptionPane;

/**
 *
 * @author mateusnaddaf
 */
public class Funcion {
    public void cargarGrafo(ListaSimple vertices, Grafo grafo) {
        if (!vertices.esVacia()) {
            for (int i = 0; i < vertices.getSize(); i++) {
                Vertice verticeActual = (Vertice) vertices.getValor(i);
                grafo.insertarVertice(verticeActual);
            }
        }
    }
    public void agregarVertices(Grafo grafo, ListaSimple vertices) {
        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeAct = (Vertice) vertices.getValor(i);
            grafo.insertarVertice(verticeAct);
        }
    }

    public boolean haySucursales(Grafo grafo) {
        for (int i = 0; i < grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) grafo.getVertices().getValor(i);
            Parada paradaActual = verticeActual.getParada();

            if (paradaActual.isSucursal()) {
                return true;
            }
        }

        return false;
    }

    public ListaSimple listaParadas(Grafo grafo, int codigo) {
        ListaSimple nombresSinSucursal = new ListaSimple();
        ListaSimple nombresConSucursal = new ListaSimple();
        ListaSimple nombres = new ListaSimple();
        for (int i = 0; i < grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) grafo.getVertices().getValor(i);
            Parada paradaActual = verticeActual.getParada();

            if (paradaActual.isSucursal()) {
                nombresConSucursal.insertarFinal(paradaActual.getNombre());
            } else {
                nombresSinSucursal.insertarFinal(paradaActual.getNombre());
            }

            nombres.insertarFinal(paradaActual.getNombre());
        }

//        return switch (codigo) {
//            case 0 ->
//                nombresSinSucursal;
//            case 1 ->
//                nombresConSucursal;
//            default ->
//                nombres;
//        };
        if(codigo == 0)
            return nombresSinSucursal;
        if(codigo == 1)
            return nombresConSucursal;
        else
            return nombres;
        

    }

    public void gestionarSucursal(Grafo grafo, String nombreParada, int codigo) {
        for (int i = 0; i < grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) grafo.getVertices().getValor(i);
            Parada paradaActual = verticeActual.getParada();

            if (paradaActual.getNombre().equalsIgnoreCase(nombreParada)) {
                if (codigo == 0) {
                    paradaActual.setSucursal(false);
                    JOptionPane.showMessageDialog(null, "Sucursal removida con exito");
                    break;
                } else {
                    paradaActual.setSucursal(true);
                    JOptionPane.showMessageDialog(null, "Sucursal agregada con exito");
                    break;
                }
            }
        }
    }

    public void conectarLinea(ListaSimple linea, ListaSimple verticesNuevos) {
        for (int i = 0; i < linea.getSize()-1; i++) {
            String nombreParadaAct = (String) linea.getValor(i);
            String nombreParadaFuturo = (String) linea.getValor(i + 1); 
            
            Vertice verticeNuevo = this.obtenerOcrearVertice(nombreParadaAct, verticesNuevos);
            Vertice verticeFuturo = this.obtenerOcrearVertice(nombreParadaFuturo, verticesNuevos);
            
            verticeFuturo.getListaAdyacencia().insertarFinal(verticeNuevo);
            verticeNuevo.getListaAdyacencia().insertarFinal(verticeFuturo);
        }
    }

    private Vertice obtenerOcrearVertice(String nombreEstacion, ListaSimple vertices) {

        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeActual = (Vertice) vertices.getValor(i);
            if (verticeActual.getParada().getNombre().equalsIgnoreCase(nombreEstacion)) {
                return verticeActual;
            }
        }

        Parada nuevaEstacion = new Parada(nombreEstacion);
        Vertice verticeNuevo = new Vertice(nuevaEstacion);
        vertices.insertarFinal(verticeNuevo);
        return verticeNuevo;
    }
    
    public ListaSimple nombresVertices(ListaSimple vertices){
        ListaSimple nombres = new ListaSimple();
        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeActual = (Vertice) vertices.getValor(i);
            nombres.insertarFinal(verticeActual.getParada().getNombre());
        }
        
        return nombres;
    }
}
