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
 *
 * @author mateusnaddaf
 */
public class RedEstacionesVisualizador extends JFrame {
    private Grafo redEstaciones;
    private Viewer visualizador;
    private ViewPanel panelGrafico;

    public RedEstacionesVisualizador(Grafo redEstaciones) {
        this.redEstaciones = redEstaciones;
        configurarVentana();
        inicializarVisualizador();
        agregarBotonVolver();
    }

    // Configura la ventana principal donde se visualizará el grafo
    private void configurarVentana() {
        setTitle("Mapa de la Red de Estaciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    // Inicializa el visualizador de la red de estaciones
    private void inicializarVisualizador() {
        Graph grafoVisual = new SingleGraph("Estaciones");
        construirRed(grafoVisual);

        // Mostrar el grafo sin crear una nueva ventana
        visualizador = grafoVisual.display(false);
        visualizador.enableAutoLayout();  // Permitir que el layout se ajuste automáticamente

        // Creamos el panel de visualización si no existe ya
        if (panelGrafico == null) {
            panelGrafico = (ViewPanel) visualizador.getDefaultView();  // Sin ventana adicional
            add(panelGrafico, BorderLayout.CENTER);  // Añadir el panel al JFrame
        }
    }

    // Construye el grafo visual, añadiendo los nodos y conexiones
    private void construirRed(Graph grafoVisual) {
        // Agregar los nodos correspondientes a cada vértice
        for (int i = 0; i < redEstaciones.getVertices().getSize(); i++) {
            Vertice vertice = (Vertice) redEstaciones.getVertices().getValor(i);
            Node nodo = grafoVisual.addNode(vertice.getParada().getNombre());
            nodo.setAttribute("ui.label", vertice.getParada().getNombre());

            // Asignar colores: marrón si no tiene sucursal, azul si tiene sucursal
            String colorNodo = vertice.getParada().isSucursal() ? "blue" : "brown";
            nodo.setAttribute("ui.style", "fill-color: " + colorNodo + ";");
        }

        agregarConexiones(grafoVisual);

        // Ajustar los estilos generales del grafo
        grafoVisual.setAttribute("ui.stylesheet",
                "node { text-size: 12px; size: 30px; text-alignment: center; }"
                + "edge { size: 2px; }"
        );
    }

    // Añade las conexiones (rutas) entre las estaciones
    private void agregarConexiones(Graph grafoVisual) {
        for (int i = 0; i < redEstaciones.getVertices().getSize(); i++) {
            Vertice vertice = (Vertice) redEstaciones.getVertices().getValor(i);
            ListaSimple adyacentes = vertice.getListaAdyacencia();

            // Añadir conexiones entre los vértices adyacentes
            for (int j = 0; j < adyacentes.getSize(); j++) {
                Vertice verticeAdyacente = (Vertice) adyacentes.getValor(j);
                String idConexion = vertice.getParada().getNombre() + "-" + verticeAdyacente.getParada().getNombre();

                if (grafoVisual.getEdge(idConexion) == null && grafoVisual.getEdge(verticeAdyacente.getParada().getNombre() + "-" + vertice.getParada().getNombre()) == null) {
                    grafoVisual.addEdge(idConexion, vertice.getParada().getNombre(), verticeAdyacente.getParada().getNombre());
                }
            }

            // Agregar rutas peatonales si existen
            agregarRutaPeatonal(grafoVisual, vertice);
        }
    }

    // Crea rutas peatonales (si existen) y las agrega al grafo
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

    // Agrega un botón que permite volver al menú principal
    private void agregarBotonVolver() {
        JButton botonVolver = new JButton("Volver");
        botonVolver.addActionListener(e -> {
            cerrarVisualizador();
            this.dispose();
            Menu menuPrincipal = new Menu(); 
            
        });
        add(botonVolver, BorderLayout.SOUTH);
    }

    // Cierra el visor y elimina el panel de visualización
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


