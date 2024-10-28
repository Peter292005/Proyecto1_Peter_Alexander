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
 * Clase que agrupa métodos de utilidades para gestionar un grafo de estaciones de transporte.
 * Permite cargar vértices, verificar sucursales, gestionar conexiones y listar paradas.
 * 
 * @author PeterNaddaf
 */
public class Funcion {

    /**
     * Carga una lista de vértices en un grafo.
     * @param vertices Lista de vértices a cargar.
     * @param grafo Grafo donde se insertarán los vértices.
     */
    public void cargarGrafo(ListaSimple vertices, Grafo grafo) {
        if (!vertices.esVacia()) {
            for (int i = 0; i < vertices.getSize(); i++) {
                Vertice verticeActual = (Vertice) vertices.getValor(i);
                grafo.insertarVertice(verticeActual);
            }
        }
    }

    /**
     * Agrega múltiples vértices a un grafo.
     * @param grafo Grafo donde se agregarán los vértices.
     * @param vertices Lista de vértices a agregar.
     */
    public void agregarVertices(Grafo grafo, ListaSimple vertices) {
        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeAct = (Vertice) vertices.getValor(i);
            grafo.insertarVertice(verticeAct);
        }
    }

    /**
     * Verifica si el grafo tiene al menos una sucursal.
     * @param grafo Grafo a verificar.
     * @return true si hay sucursales, false si no.
     */
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

    /**
     * Genera una lista de nombres de paradas, filtrando por tipo de parada.
     * @param grafo Grafo que contiene las paradas.
     * @param codigo Código que indica el tipo de paradas:
     *               0 - sin sucursales, 1 - solo sucursales, otro valor - todas.
     * @return ListaSimple de nombres de paradas según el código.
     */
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

        if(codigo == 0) return nombresSinSucursal;
        if(codigo == 1) return nombresConSucursal;
        else return nombres;
    }

    /**
     * Gestiona el estado de una sucursal en el grafo, permitiendo agregar o removerla.
     * @param grafo Grafo que contiene la parada.
     * @param nombreParada Nombre de la parada a gestionar.
     * @param codigo Código que indica la acción: 0 - remover, 1 - agregar.
     */
    public void gestionarSucursal(Grafo grafo, String nombreParada, int codigo) {
        for (int i = 0; i < grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) grafo.getVertices().getValor(i);
            Parada paradaActual = verticeActual.getParada();

            if (paradaActual.getNombre().equalsIgnoreCase(nombreParada)) {
                if (codigo == 0) {
                    paradaActual.setSucursal(false);
                    JOptionPane.showMessageDialog(null, "Sucursal removida con éxito");
                    break;
                } else {
                    paradaActual.setSucursal(true);
                    JOptionPane.showMessageDialog(null, "Sucursal agregada con éxito");
                    break;
                }
            }
        }
    }

    /**
     * Conecta una lista de paradas en orden, creando adyacencias entre vértices consecutivos.
     * @param linea Lista de nombres de paradas en la línea.
     * @param verticesNuevos Lista de vértices donde se crearán o buscarán las paradas.
     */
    public void conectarLinea(ListaSimple linea, ListaSimple verticesNuevos) {
        for (int i = 0; i < linea.getSize() - 1; i++) {
            String nombreParadaAct = (String) linea.getValor(i);
            String nombreParadaFuturo = (String) linea.getValor(i + 1); 
            
            Vertice verticeNuevo = this.obtenerOcrearVertice(nombreParadaAct, verticesNuevos);
            Vertice verticeFuturo = this.obtenerOcrearVertice(nombreParadaFuturo, verticesNuevos);
            
            verticeFuturo.getListaAdyacencia().insertarFinal(verticeNuevo);
            verticeNuevo.getListaAdyacencia().insertarFinal(verticeFuturo);
        }
    }

    /**
     * Busca un vértice por nombre en la lista o crea uno nuevo si no existe.
     * @param nombreEstacion Nombre de la estación.
     * @param vertices Lista de vértices donde se buscará o insertará el nuevo vértice.
     * @return El vértice correspondiente a la estación.
     */
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
    
    /**
     * Genera una lista de nombres de los vértices en la lista de entrada.
     * @param vertices Lista de vértices de la cual obtener nombres.
     * @return ListaSimple con los nombres de las paradas en los vértices.
     */
    public ListaSimple nombresVertices(ListaSimple vertices) {
        ListaSimple nombres = new ListaSimple();
        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeActual = (Vertice) vertices.getValor(i);
            nombres.insertarFinal(verticeActual.getParada().getNombre());
        }
        return nombres;
    }
}
