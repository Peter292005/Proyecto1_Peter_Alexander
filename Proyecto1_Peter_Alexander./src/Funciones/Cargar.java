/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;
import EstructurasDatos.ListaSimple;
import EstructurasDatos.Vertice;
import MainClass.Parada;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
/**
 * Clase que carga y procesa datos de transporte desde archivos JSON.
 * Permite buscar y cargar archivos, procesar líneas de transporte y estaciones.
 * 
 * @author PeterNaddaf
 */
public class Cargar {
    private ListaSimple vertices = new ListaSimple(); // Lista de vértices

    /**
     * Obtiene la lista de vértices.
     * @return ListaSimple con los vértices.
     */
    public ListaSimple getVertices() {
        return vertices;
    }

    /**
     * Establece la lista de vértices.
     * @param vertices ListaSimple a asignar.
     */
    public void setVertices(ListaSimple vertices) {
        this.vertices = vertices;
    }

    /**
     * Abre un archivo JSON seleccionado por el usuario y muestra su contenido.
     * @param ruta JTextField donde se mostrará la ruta del archivo.
     * @param contenido JTextArea donde se mostrará el contenido del archivo.
     * @param cargar JFrame para la ventana de selección de archivo.
     */
    public void buscarArchivo(JTextField ruta, JTextArea contenido, JFrame cargar) {
        JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos JSON (*.json)", "json");
        fc.setFileFilter(filtro);

        int seleccion = fc.showOpenDialog(cargar);

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fc.getSelectedFile();
            ruta.setText(fichero.getAbsolutePath());
            try (FileReader fr = new FileReader(fichero)) {
                StringBuilder cadena = new StringBuilder();
                int valor = fr.read();
                while (valor != -1) {
                    cadena.append((char) valor);
                    valor = fr.read();
                }
                contenido.setText(cadena.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(cargar, "No se ha seleccionado ningún archivo.");
        }
    }
    
    /**
     * Carga y procesa datos de transporte desde un archivo JSON.
     * @param rutaArchivo Ruta del archivo JSON a cargar.
     */
    public void cargarJSON(String rutaArchivo) {
        try {
            Gson gson = new Gson();
            JsonObject redTransporteData = gson.fromJson(new FileReader(rutaArchivo), JsonObject.class);
            ListaSimple nombresRedes = obtenerClaves(redTransporteData);

            for (int i = 0; i < nombresRedes.getSize(); i++) {
                String nombreRed = (String) nombresRedes.getValor(i);
                JsonElement redElement = redTransporteData.get(nombreRed);

                if (redElement.isJsonObject()) {
                    JsonObject lineasObject = redElement.getAsJsonObject();
                    procesarLinea(lineasObject);
                } else if (redElement.isJsonArray()) {
                    JsonArray lineasArray = redElement.getAsJsonArray();
                    for (JsonElement elementoLinea : lineasArray) {
                        if (elementoLinea.isJsonObject()) {
                            JsonObject lineaObject = elementoLinea.getAsJsonObject();
                            procesarLinea(lineaObject);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene las claves de un objeto JSON.
     * @param jsonObject Objeto JSON.
     * @return ListaSimple con las claves del objeto.
     */
    private ListaSimple obtenerClaves(JsonObject jsonObject) {
        ListaSimple listaClaves = new ListaSimple();
        for (String key : jsonObject.keySet()) {
            listaClaves.insertarFinal(key);
        }
        return listaClaves;
    }

    /**
     * Procesa una línea de transporte y sus estaciones, creando vértices y adyacencias.
     * @param lineasObject Objeto JSON que representa una línea de transporte.
     */
    private void procesarLinea(JsonObject lineasObject) {
        ListaSimple nombresLineas = obtenerClaves(lineasObject);

        for (int i = 0; i < nombresLineas.getSize(); i++) {
            String nombreLinea = (String) nombresLineas.getValor(i);
            JsonArray estacionesArray = lineasObject.getAsJsonArray(nombreLinea);

            Vertice verticeAnterior = null;
            Vertice verticeActual;

            for (JsonElement estacionElement : estacionesArray) {
                if (estacionElement.isJsonPrimitive()) {
                    String nombreEstacion = estacionElement.getAsString();
                    verticeActual = obtenerOcrearVertice(nombreEstacion);

                    if (verticeAnterior != null) {
                        verticeAnterior.getListaAdyacencia().insertarFinal(verticeActual);
                        verticeActual.getListaAdyacencia().insertarFinal(verticeAnterior);
                    }
                    verticeAnterior = verticeActual;

                } else if (estacionElement.isJsonObject()) {
                    JsonObject conexionPeatonal = estacionElement.getAsJsonObject();
                    ListaSimple clavesPeatonales = obtenerClaves(conexionPeatonal);

                    for (int j = 0; j < clavesPeatonales.getSize(); j++) {
                        String parada1 = (String) clavesPeatonales.getValor(j);
                        String parada2 = conexionPeatonal.get(parada1).getAsString();
                        verticeAnterior = this.crearPasoPeatonal(parada1, parada2, verticeAnterior);
                    }
                }
            }
        }
    }

    /**
     * Crea o actualiza una conexión peatonal entre dos estaciones.
     * @param estacion1 Nombre de la primera estación.
     * @param estacion2 Nombre de la segunda estación.
     * @param verticeAnterior Vértice anterior en la línea, para mantener adyacencias.
     * @return El vértice de la primera estación, actualizado.
     */
    private Vertice crearPasoPeatonal(String estacion1, String estacion2, Vertice verticeAnterior) {
        Vertice v1 = obtenerOcrearVertice(estacion1);
        Vertice v2 = obtenerOcrearVertice(estacion2);

        v1.getParada().setPasoPeatonal(v2.getParada());
        v2.getParada().setPasoPeatonal(v1.getParada());

        if (verticeAnterior != null) {
            verticeAnterior.getListaAdyacencia().insertarFinal(v1);
            v1.getListaAdyacencia().insertarFinal(verticeAnterior);
        }
        verticeAnterior = v1;
        return verticeAnterior;
    }

    /**
     * Obtiene un vértice existente o crea uno nuevo si no se encuentra.
     * @param nombreParada Nombre de la parada o estación.
     * @return Vértice correspondiente a la parada.
     */
    private Vertice obtenerOcrearVertice(String nombreParada) {
        for (int i = 0; i < vertices.getSize(); i++) {
            Vertice verticeActual = (Vertice) vertices.getValor(i);
            if (verticeActual.getParada().getNombre().equalsIgnoreCase(nombreParada)) {
                return verticeActual;
            }
        }

        Parada nuevaParada = new Parada(nombreParada);
        Vertice verticeNuevo = new Vertice(nuevaParada);
        vertices.insertarFinal(verticeNuevo);
        return verticeNuevo;
    }
}