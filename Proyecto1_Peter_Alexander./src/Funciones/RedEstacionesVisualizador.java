/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

import EstructurasDatos.Grafo;
import EstructurasDatos.ListaSimple;
import EstructurasDatos.Vertice;
import Interfaz.Menu;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.swing_viewer.ViewPanel;


/**
 * Clase para visualizar la red de estaciones en una interfaz gráfica.
 * Muestra el grafo de estaciones con opciones de colores y conexiones entre estaciones.
 * También permite agregar rutas peatonales y un botón para volver al menú principal.
 * 
 * Esta clase extiende JFrame.
 * 
 * @author PeterNaddaf
 */
public class RedEstacionesVisualizador extends JFrame {
    private Grafo redEstaciones;
    private Viewer visualizador;
    private ViewPanel panelGrafico;

    /**
     * Constructor que inicializa el visualizador de la red de estaciones.
     * @param redEstaciones Grafo que representa la red de estaciones.
     */
    public RedEstacionesVisualizador(Grafo redEstaciones) {
        this.redEstaciones = redEstaciones;
        configurarVentana();
        inicializarVisualizador();
        agregarBotonVolver();
    }

    /**
     * Configura las propiedades de la ventana principal donde se visualizará el grafo.
     */
    private void configurarVentana() {
        setTitle("Mapa de la Red de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa el visualizador del grafo y configura el panel gráfico.
     */
    private void inicializarVisualizador() {
        Graph grafoVisual = new SingleGraph("Estaciones");
        construirRed(grafoVisual);

        visualizador = grafoVisual.display(false);
        visualizador.enableAutoLayout();

        if (panelGrafico == null) {
            panelGrafico = (ViewPanel) visualizador.getDefaultView();
            add(panelGrafico, BorderLayout.CENTER);
        }
    }

    /**
     * Construye el grafo visual agregando los nodos y conexiones entre estaciones.
     * @param grafoVisual Grafo visual donde se agregan los nodos y conexiones.
     */
    private void construirRed(Graph grafoVisual) {
        for (int i = 0; i < redEstaciones.getVertices().getSize(); i++) {
            Vertice vertice = (Vertice) redEstaciones.getVertices().getValor(i);
            Node nodo = grafoVisual.addNode(vertice.getParada().getNombre());
            nodo.setAttribute("ui.label", vertice.getParada().getNombre());

            String colorNodo = vertice.getParada().isSucursal() ? "blue" : "brown";
            nodo.setAttribute("ui.style", "fill-color: " + colorNodo + ";");
        }

        agregarConexiones(grafoVisual);

        grafoVisual.setAttribute("ui.stylesheet",
                "node { text-size: 12px; size: 30px; text-alignment: center; }"
                + "edge { size: 2px; }"
        );
    }

    /**
     * Agrega conexiones entre estaciones, incluyendo rutas adyacentes y peatonales.
     * @param grafoVisual Grafo visual donde se agregarán las conexiones.
     */
    private void agregarConexiones(Graph grafoVisual) {
        for (int i = 0; i < redEstaciones.getVertices().getSize(); i++) {
            Vertice vertice = (Vertice) redEstaciones.getVertices().getValor(i);
            ListaSimple adyacentes = vertice.getListaAdyacencia();

            for (int j = 0; j < adyacentes.getSize(); j++) {
                Vertice verticeAdyacente = (Vertice) adyacentes.getValor(j);
                String idConexion = vertice.getParada().getNombre() + "-" + verticeAdyacente.getParada().getNombre();

                if (grafoVisual.getEdge(idConexion) == null && grafoVisual.getEdge(verticeAdyacente.getParada().getNombre() + "-" + vertice.getParada().getNombre()) == null) {
                    grafoVisual.addEdge(idConexion, vertice.getParada().getNombre(), verticeAdyacente.getParada().getNombre());
                }
            }
            agregarRutaPeatonal(grafoVisual, vertice);
        }
    }

    /**
     * Agrega rutas peatonales entre estaciones, si existen, y configura su estilo.
     * @param grafoVisual Grafo visual donde se agregarán las rutas peatonales.
     * @param vertice Vértice que representa la estación con posible ruta peatonal.
     */
    private void agregarRutaPeatonal(Graph grafoVisual, Vertice vertice) {
        if (vertice.getParada().tienePasoPeatonal()) {
            String nombrePasoPeatonal = vertice.getParada().getPasoPeatonal().getNombre();
            Vertice verticePeatonal = redEstaciones.buscarVerticePorNombre(nombrePasoPeatonal);
            String idPeatonal = vertice.getParada().getNombre() + "-" + verticePeatonal.getParada().getNombre() + "-peatonal";

            if (grafoVisual.getEdge(idPeatonal) == null) {
                Edge aristaPeatonal = grafoVisual.addEdge(idPeatonal, vertice.getParada().getNombre(), verticePeatonal.getParada().getNombre(), true);
                aristaPeatonal.setAttribute("ui.style", "stroke-mode: dots; stroke-color: blue;");
            }
        }
    }

    /**
     * Agrega un botón para volver al menú principal, cerrando el visualizador al presionarlo.
     */
    private void agregarBotonVolver() {
        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> {
            cerrarVisualizador();
            this.dispose();
            Menu menuPrincipal = new Menu(); 
        });
        add(botonVolver, BorderLayout.SOUTH);
    }

    /**
     * Cierra el visualizador y elimina el panel gráfico de la ventana.
     */
    private void cerrarVisualizador() {
        if (visualizador != null) {
            visualizador.disableAutoLayout();
            visualizador.close();
        }
        if (panelGrafico != null) {
            remove(panelGrafico);
            panelGrafico = null;
        }
    }
}