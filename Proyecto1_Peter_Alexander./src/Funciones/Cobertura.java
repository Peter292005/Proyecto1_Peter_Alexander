/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import EstructurasDatos.Cola;
import EstructurasDatos.Grafo;
import EstructurasDatos.ListaSimple;
import EstructurasDatos.Vertice;
import javax.swing.JOptionPane;

/**
 * Clase que maneja la cobertura de estaciones en un grafo de transporte.
 * Permite realizar búsquedas de estaciones dentro de un rango y verificar la cobertura completa.
 * También sugiere nuevas ubicaciones de sucursales para maximizar la cobertura.
 * 
 * @author PeterNaddaf
 */
public class Cobertura {
    private Grafo grafo;
    private int t;  // Límite máximo de distancia

    /**
     * Constructor que inicializa el grafo y el límite máximo de distancia.
     * @param grafo Grafo de estaciones de transporte.
     * @param t Límite de distancia para la cobertura.
     */
    public Cobertura(Grafo grafo, int t) {
        this.grafo = grafo;
        this.t = t;
    }

    /**
     * Realiza una búsqueda en anchura (BFS) desde un vértice inicial hasta una distancia máxima.
     * @param verticeInicial Vértice desde el cual comenzar la búsqueda.
     */
    public void busquedaBFS(Vertice verticeInicial) {
        if (verticeInicial == null) {
            JOptionPane.showMessageDialog(null, "El vértice inicial no puede ser nulo.");
            return;
        }

        String recorrido = "Partiendo desde: " + verticeInicial.getParada().getNombre() + "\n";
        Cola cola = new Cola();
        Cola distancias = new Cola();
        ListaSimple visitados = new ListaSimple();

        cola.enColar(verticeInicial);
        distancias.enColar(0);
        visitados.insertarFinal(verticeInicial);

        while (!cola.colaVacia()) {
            Vertice verticeActual = (Vertice) cola.desenColar();
            int distanciaActual = (int) distancias.desenColar();

            if (distanciaActual > t) {
                continue;
            }

            recorrido += "Vértice: " + verticeActual.getParada().getNombre() + ", Distancia: " + distanciaActual + "\n";

            if (verticeActual.getParada().getPasoPeatonal() != null) {
                Vertice verticePeatonal = grafo.buscarVerticePorNombre(verticeActual.getParada().getPasoPeatonal().getNombre());
                if (!visitados.buscar(verticePeatonal)) {
                    cola.enColar(verticePeatonal);
                    distancias.enColar(distanciaActual);
                    visitados.insertarFinal(verticePeatonal);
                }
            }

            ListaSimple adyacentes = verticeActual.getListaAdyacencia();
            for (int i = 0; i < adyacentes.getSize(); i++) {
                Vertice adyacente = (Vertice) adyacentes.getValor(i);
                if (!visitados.buscar(adyacente)) {
                    cola.enColar(adyacente);
                    distancias.enColar(distanciaActual + 1);
                    visitados.insertarFinal(adyacente);
                }
            }
        }

        JOptionPane.showMessageDialog(null, recorrido);
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) desde un vértice inicial hasta una distancia máxima.
     * @param verticeInicial Vértice desde el cual comenzar la búsqueda.
     */
    public void busquedaDFS(Vertice verticeInicial) {
        if (verticeInicial == null) {
            JOptionPane.showMessageDialog(null, "El vértice inicial no puede ser nulo.");
            return;
        }

        ListaSimple visitados = new ListaSimple();
        StringBuilder resultado = new StringBuilder();
        resultado.append("Partiendo desde: ").append(verticeInicial.getParada().getNombre()).append("\n");

        dfsRecursivo(verticeInicial, visitados, 0, resultado);

        resultado.append("Cobertura DFS completada.\n");
        JOptionPane.showMessageDialog(null, resultado.toString());
    }

    /**
     * Método recursivo que aplica DFS a un vértice y mantiene un registro de la distancia y los vértices visitados.
     * @param vertice Vértice actual.
     * @param visitados Lista de vértices visitados.
     * @param distanciaActual Distancia actual desde el vértice inicial.
     * @param resultado Registro del recorrido en profundidad.
     */
    private void dfsRecursivo(Vertice vertice, ListaSimple visitados, int distanciaActual, StringBuilder resultado) {
        visitados.insertarFinal(vertice);

        resultado.append("Vértice: ").append(vertice.getParada().getNombre())
                .append(", Distancia: ").append(distanciaActual).append("\n");

        if (distanciaActual >= t) {
            return;
        }

        if (vertice.getParada().getPasoPeatonal() != null) {
            Vertice verticePeatonal = grafo.buscarVerticePorNombre(vertice.getParada().getPasoPeatonal().getNombre());
            if (!visitados.buscar(verticePeatonal)) {
                dfsRecursivo(verticePeatonal, visitados, distanciaActual, resultado);
            }
        }

        ListaSimple adyacentes = vertice.getListaAdyacencia();
        for (int i = 0; i < adyacentes.getSize(); i++) {
            Vertice adyacente = (Vertice) adyacentes.getValor(i);
            if (!visitados.buscar(adyacente)) {
                dfsRecursivo(adyacente, visitados, distanciaActual + 1, resultado);
            }
        }
    }

    /**
     * Verifica si todas las estaciones están cubiertas.
     * Si existen estaciones no cubiertas, sugiere una nueva ubicación de sucursal.
     */
    public void verificarCoberturaCompleta() {
        if (this.grafo.grafoVacio()) {
            System.out.println("El grafo está vacío. No hay estaciones para verificar.");
            return;
        }

        ListaSimple estacionesCubiertas = new ListaSimple();
        ListaSimple estacionesPendientes = new ListaSimple();

        for (int i = 0; i < this.grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) this.grafo.getVertices().getValor(i);
            if (verticeActual.getParada().isSucursal()) {
                aplicarCobertura(verticeActual, estacionesCubiertas, t);
            }
        }

        for (int i = 0; i < this.grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) this.grafo.getVertices().getValor(i);
            if (!estacionesCubiertas.buscar(verticeActual)) {
                estacionesPendientes.insertarFinal(verticeActual);
            }
        }

        if (estacionesPendientes.getSize() == 0) {
            JOptionPane.showMessageDialog(null, "¡Cobertura completa! Todas las estaciones están cubiertas.");
        } else {
            JOptionPane.showMessageDialog(null, "Existen estaciones no cubiertas.");
            proponerNuevaSucursal(estacionesPendientes, estacionesCubiertas, t);
        }
    }

    /**
     * Aplica cobertura a partir de una estación hasta una distancia máxima.
     * @param vertice Vértice de inicio.
     * @param estacionesCubiertas Lista de estaciones cubiertas.
     * @param distanciaMaxima Distancia máxima para la cobertura.
     */
    private void aplicarCobertura(Vertice vertice, ListaSimple estacionesCubiertas, int distanciaMaxima) {
        expandirCobertura(vertice, estacionesCubiertas, 0, distanciaMaxima);
    }

    /**
     * Expande la cobertura de estaciones y adyacentes hasta una distancia máxima.
     * @param verticeActual Vértice actual.
     * @param estacionesCubiertas Lista de estaciones cubiertas.
     * @param distanciaActual Distancia desde el vértice inicial.
     * @param distanciaMaxima Distancia máxima de cobertura.
     */
    private void expandirCobertura(Vertice verticeActual, ListaSimple estacionesCubiertas, int distanciaActual, int distanciaMaxima) {
        if (distanciaActual > distanciaMaxima || estacionesCubiertas.buscar(verticeActual)) {
            return;
        }

        estacionesCubiertas.insertarFinal(verticeActual);

        ListaSimple adyacentes = verticeActual.getListaAdyacencia();
        for (int i = 0; i < adyacentes.getSize(); i++) {
            Vertice adyacente = (Vertice) adyacentes.getValor(i);
            expandirCobertura(adyacente, estacionesCubiertas, distanciaActual + 1, distanciaMaxima);
        }

        if (verticeActual.getParada().tienePasoPeatonal()) {
            Vertice verticePeatonal = grafo.buscarVerticePorNombre(verticeActual.getParada().getPasoPeatonal().getNombre());
            expandirCobertura(verticePeatonal, estacionesCubiertas, distanciaActual, distanciaMaxima);
        }
    }

    /**
     * Sugiere una nueva ubicación de sucursal para maximizar la cobertura.
     * @param estacionesPendientes Lista de estaciones no cubiertas.
     * @param estacionesCubiertas Lista de estaciones cubiertas.
     * @param rangoCobertura Rango de cobertura de la sucursal.
     */
    private void proponerNuevaSucursal(ListaSimple estacionesPendientes, ListaSimple estacionesCubiertas, int rangoCobertura) {
        Vertice mejorOpcion = null;
        int maxCoberturaAdicional = 0;

        for (int i = 0; i < estacionesPendientes.getSize(); i++) {
            Vertice estacionPendiente = (Vertice) estacionesPendientes.getValor(i);
            ListaSimple coberturaTemporal = new ListaSimple();

            aplicarCobertura(estacionPendiente, coberturaTemporal, rangoCobertura);
            int coberturaAdicional = contarCoberturaNueva(coberturaTemporal, estacionesCubiertas);

            if (coberturaAdicional > maxCoberturaAdicional) {
                mejorOpcion = estacionPendiente;
                maxCoberturaAdicional = coberturaAdicional;
            }
        }

        if (mejorOpcion != null) {
            JOptionPane.showMessageDialog(null, "Sugerencia: Colocar una sucursal en la estación "
                    + mejorOpcion.getParada().getNombre() + " para cubrir " + maxCoberturaAdicional + " estaciones adicionales.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una estación adecuada para aumentar la cobertura.");
        }
    }

    /**
     * Cuenta cuántas estaciones nuevas serían cubiertas por una cobertura simulada.
     * @param coberturaSimulada Lista de estaciones en la cobertura simulada.
     * @param estacionesCubiertas Lista de estaciones ya cubiertas.
     * @return Número de estaciones adicionales cubiertas.
     */
    private int contarCoberturaNueva(ListaSimple coberturaSimulada, ListaSimple estacionesCubiertas) {
        int contador = 0;

        for (int i = 0; i < coberturaSimulada.getSize(); i++) {
            Vertice estacion = (Vertice) coberturaSimulada.getValor(i);
            if (!estacionesCubiertas.buscar(estacion)) {
                contador++;
            }
        }

        return contador;
    }

}