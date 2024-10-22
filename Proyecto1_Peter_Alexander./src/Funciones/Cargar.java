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
 *
 * @author mateusnaddaf
 */
public class Cargar {
    private ListaSimple vertices = new ListaSimple(); // Lista de vértices

    public ListaSimple getVertices() {
        return vertices;
    }

    public void setVertices(ListaSimple vertices) {
        this.vertices = vertices;
    }

    public void buscarArchivo(JTextField ruta, JTextArea contenido, JFrame cargar) {

        // Esto va dentro del constructor de tu JFrame o en el evento de un botón
        JFileChooser fc = new JFileChooser();

        // Creo el filtro para archivos .json
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos JSON (*.json)", "json");

        // Le indico el filtro
        fc.setFileFilter(filtro);

        // Abrimos la ventana, guardamos la opción seleccionada por el usuario
        int seleccion = fc.showOpenDialog(cargar);

        // Si el usuario presiona aceptar
        if (seleccion == JFileChooser.APPROVE_OPTION) {

            // Selecciono el fichero
            File fichero = fc.getSelectedFile();

            ruta.setText(fichero.getAbsolutePath());
            try (FileReader fr = new FileReader(fichero)) {
                StringBuilder cadena = new StringBuilder();
                int valor = fr.read();

                while (valor != -1) {
                    cadena.append((char) valor);
                    valor = fr.read();
                }

                // Modifico el valor del JTextArea para mostrar el contenido del archivo
                contenido.setText(cadena.toString());

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } else {
            // Si el usuario no seleccionó ningún archivo, muestra un mensaje
            JOptionPane.showMessageDialog(cargar, "No se ha seleccionado ningún archivo.");
        }
    }
    
    public void cargarJSON(String rutaArchivo) {
        try {
            Gson gson = new Gson();
            JsonObject redTransporteData = gson.fromJson(new FileReader(rutaArchivo), JsonObject.class);

            // Obtener los nombres de las redes de transporte (claves principales)
            ListaSimple nombresRedes = obtenerClaves(redTransporteData);

            // Iterar sobre cada red de transporte
            for (int i = 0; i < nombresRedes.getSize(); i++) {
                String nombreRed = (String) nombresRedes.getValor(i);
                JsonElement redElement = redTransporteData.get(nombreRed);

                // Verificamos si es un objeto o un arreglo y lo manejamos adecuadamente
                if (redElement.isJsonObject()) {
                    JsonObject lineasObject = redElement.getAsJsonObject();
                    procesarLinea(lineasObject);
                } else if (redElement.isJsonArray()) {
                    JsonArray lineasArray = redElement.getAsJsonArray();
                    for (JsonElement elementoLinea : lineasArray) {
                        if (elementoLinea.isJsonObject()) {
                            JsonObject lineaObject = elementoLinea.getAsJsonObject();
                            procesarLinea(lineaObject);  // Procesar cada objeto de línea
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ListaSimple obtenerClaves(JsonObject jsonObject) {
        ListaSimple listaClaves = new ListaSimple();
        for (String key : jsonObject.keySet()) {
            listaClaves.insertarFinal(key);
        }
        return listaClaves;
    }

    // Método auxiliar para procesar las líneas y estaciones
    private void procesarLinea(JsonObject lineasObject) {
        // Obtener las claves de las líneas
        ListaSimple nombresLineas = obtenerClaves(lineasObject);

        for (int i = 0; i < nombresLineas.getSize(); i++) {
            String nombreLinea = (String) nombresLineas.getValor(i);
            JsonArray estacionesArray = lineasObject.getAsJsonArray(nombreLinea);

            Vertice verticeAnterior = null;  // Para enlazar adyacencias entre estaciones consecutivas
            Vertice verticeActual;

            // Procesar cada estación de la línea
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
                    // Caso de una conexión peatonal (ejemplo: {"Capitolio":"El Silencio"})
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

    private Vertice crearPasoPeatonal(String estacion1, String estacion2, Vertice verticeAnterior) {
        Vertice v1 = obtenerOcrearVertice(estacion1);
        Vertice v2 = obtenerOcrearVertice(estacion2);

        
        v1.getParada().setPasoPeatonal(v2.getParada());
        v2.getParada().setPasoPeatonal(v1.getParada());

        // Establecer también la adyacencia lógica entre las estaciones de la misma línea
        if (verticeAnterior != null) {
            verticeAnterior.getListaAdyacencia().insertarFinal(v1);
            v1.getListaAdyacencia().insertarFinal(verticeAnterior);
        }
        verticeAnterior = v1;
        return verticeAnterior;
    }

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


