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
 *
 * @author mateusnaddaf
 */
public class Cobertura {
    private Grafo grafo;
    private int t;  // Límite máximo de distancia

    // Constructor que inicializa el grafo y el parámetro t
    public Cobertura(Grafo grafo, int t) {
        this.grafo = grafo;
        this.t = t;
    }

    // BFS (búsqueda en anchura)
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

            // Revisar paso peatonal (distancia = 0)
            if (verticeActual.getParada().getPasoPeatonal() != null) {
                Vertice verticePeatonal = grafo.buscarVerticePorNombre(verticeActual.getParada().getPasoPeatonal().getNombre());
                if (!visitados.buscar(verticePeatonal)) {
                    cola.enColar(verticePeatonal);
                    distancias.enColar(distanciaActual);  // No se incrementa la distancia para paso peatonal
                    visitados.insertarFinal(verticePeatonal);
                }
            }

            // Revisar adyacencias
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

    // DFS (búsqueda en profundidad)
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

    private void dfsRecursivo(Vertice vertice, ListaSimple visitados, int distanciaActual, StringBuilder resultado) {
        visitados.insertarFinal(vertice);

        resultado.append("Vértice: ").append(vertice.getParada().getNombre())
                .append(", Distancia: ").append(distanciaActual).append("\n");

        // Detener si se excede la distancia t
        if (distanciaActual >= t) {
            return;
        }

        // Revisar paso peatonal (distancia = 0)
        if (vertice.getParada().getPasoPeatonal() != null) {
            Vertice verticePeatonal = grafo.buscarVerticePorNombre(vertice.getParada().getPasoPeatonal().getNombre());
            if (!visitados.buscar(verticePeatonal)) {
                dfsRecursivo(verticePeatonal, visitados, distanciaActual, resultado);  // No se incrementa la distancia
            }
        }

        // Revisar adyacencias
        ListaSimple adyacentes = vertice.getListaAdyacencia();
        for (int i = 0; i < adyacentes.getSize(); i++) {
            Vertice adyacente = (Vertice) adyacentes.getValor(i);
            if (!visitados.buscar(adyacente)) {
                dfsRecursivo(adyacente, visitados, distanciaActual + 1, resultado);
            }
        }
    }

    public void verificarCoberturaCompleta() {
        if (this.grafo.grafoVacio()) {
            System.out.println("El grafo está vacío. No hay estaciones para verificar.");
            return;
        }

        // Lista para almacenar las estaciones cubiertas y las no cubiertas
        ListaSimple estacionesCubiertas = new ListaSimple();
        ListaSimple estacionesPendientes = new ListaSimple();

        // Paso 1: Marcar cobertura de todas las sucursales
        for (int i = 0; i < this.grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) this.grafo.getVertices().getValor(i);
            if (verticeActual.getParada().isSucursal()) {
                aplicarCobertura(verticeActual, estacionesCubiertas, t);
            }
        }

        // Paso 2: Identificar las estaciones no cubiertas
        for (int i = 0; i < this.grafo.getVertices().getSize(); i++) {
            Vertice verticeActual = (Vertice) this.grafo.getVertices().getValor(i);
            if (!estacionesCubiertas.buscar(verticeActual)) {
                estacionesPendientes.insertarFinal(verticeActual);
            }
        }

        // Evaluamos los resultados
        if (estacionesPendientes.getSize() == 0) {
            JOptionPane.showMessageDialog(null, "¡Cobertura completa! Todas las estaciones están cubiertas.");
        } else {
            JOptionPane.showMessageDialog(null, "Existen estaciones no cubiertas.");
            proponerNuevaSucursal(estacionesPendientes, estacionesCubiertas, t);
        }
    }

// Aplica cobertura recursiva a partir de una estación, considerando su rango y el paso peatonal
    private void aplicarCobertura(Vertice vertice, ListaSimple estacionesCubiertas, int distanciaMaxima) {
        expandirCobertura(vertice, estacionesCubiertas, 0, distanciaMaxima);
    }

// Expande la cobertura marcando estaciones y adyacentes hasta una distancia máxima
    private void expandirCobertura(Vertice verticeActual, ListaSimple estacionesCubiertas, int distanciaActual, int distanciaMaxima) {
        // Si la distancia es mayor al rango de cobertura o ya está cubierta, detenemos
        if (distanciaActual > distanciaMaxima || estacionesCubiertas.buscar(verticeActual)) {
            return;
        }

        // Marcamos la estación como cubierta
        estacionesCubiertas.insertarFinal(verticeActual);

        // Revisamos adyacencias
        ListaSimple adyacentes = verticeActual.getListaAdyacencia();
        for (int i = 0; i < adyacentes.getSize(); i++) {
            Vertice adyacente = (Vertice) adyacentes.getValor(i);
            expandirCobertura(adyacente, estacionesCubiertas, distanciaActual + 1, distanciaMaxima);
        }

        // También cubrimos el paso peatonal si existe (sin aumentar la distancia)
        if (verticeActual.getParada().tienePasoPeatonal()) {
            Vertice verticePeatonal = grafo.buscarVerticePorNombre(verticeActual.getParada().getPasoPeatonal().getNombre());
            expandirCobertura(verticePeatonal, estacionesCubiertas, distanciaActual, distanciaMaxima);
        }
    }

// Sugiere dónde colocar una nueva sucursal para cubrir más estaciones
    private void proponerNuevaSucursal(ListaSimple estacionesPendientes, ListaSimple estacionesCubiertas, int rangoCobertura) {
        Vertice mejorOpcion = null;
        int maxCoberturaAdicional = 0;

        // Iterar sobre las estaciones pendientes para simular la cobertura
        for (int i = 0; i < estacionesPendientes.getSize(); i++) {
            Vertice estacionPendiente = (Vertice) estacionesPendientes.getValor(i);
            ListaSimple coberturaTemporal = new ListaSimple();

            // Simulamos la cobertura que ofrecería esta estación
            aplicarCobertura(estacionPendiente, coberturaTemporal, rangoCobertura);

            // Contamos cuántas nuevas estaciones cubriría
            int coberturaAdicional = contarCoberturaNueva(coberturaTemporal, estacionesCubiertas);

            // Si encontramos una mejor opción, la guardamos
            if (coberturaAdicional > maxCoberturaAdicional) {
                mejorOpcion = estacionPendiente;
                maxCoberturaAdicional = coberturaAdicional;
            }
        }

        // Proporcionamos una sugerencia al usuario
        if (mejorOpcion != null) {
            JOptionPane.showMessageDialog(null, "Sugerencia: Colocar una sucursal en la estación "
                    + mejorOpcion.getParada().getNombre() + " para cubrir " + maxCoberturaAdicional + " estaciones adicionales.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una estación adecuada para aumentar la cobertura.");
        }
    }

// Método para contar cuántas estaciones adicionales serían cubiertas
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


