/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import javax.swing.JOptionPane;

/**
 * Clase que representa una Parada en una red de estaciones.
 * Cada parada tiene un nombre, un indicador de si es una sucursal, y un posible paso peatonal hacia otra parada.
 * 
 * Proporciona métodos para obtener y definir estos atributos, así como para mostrar información sobre la parada.
 * 
 * @author PeterNaddaf
 */
public class Parada {
    private String nombre;
    private boolean sucursal;
    private Parada pasoPeatonal;

    /**
     * Constructor que inicializa la parada con un nombre y sin sucursal ni paso peatonal.
     * @param nombre Nombre de la parada.
     */
    public Parada(String nombre) {
        this.nombre = nombre;
        this.sucursal = false;
        this.pasoPeatonal = null;
    }

    /**
     * Obtiene el nombre de la parada.
     * @return Nombre de la parada.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre de la parada.
     * @param nombre Nombre a asignar a la parada.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Verifica si la parada es una sucursal.
     * @return true si la parada es una sucursal, false si no.
     */
    public boolean isSucursal() {
        return sucursal;
    }

    /**
     * Define si la parada es una sucursal.
     * @param sucursal true para definir la parada como sucursal, false para quitar la sucursalidad.
     */
    public void setSucursal(boolean sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * Obtiene el paso peatonal asociado a esta parada, si existe.
     * @return Parada que representa el paso peatonal o null si no tiene.
     */
    public Parada getPasoPeatonal() {
        return pasoPeatonal;
    }

    /**
     * Define un paso peatonal hacia otra parada.
     * @param pasoPeatonal Parada a la cual se enlaza como paso peatonal.
     */
    public void setPasoPeatonal(Parada pasoPeatonal) {
        this.pasoPeatonal = pasoPeatonal;
    }

    /**
     * Verifica si la parada tiene un paso peatonal.
     * @return true si la parada tiene paso peatonal, false si no.
     */
    public boolean tienePasoPeatonal() {
        return this.pasoPeatonal != null;
    }

    /**
     * Muestra si la parada es una sucursal.
     * @return "si" si es sucursal, "no" si no lo es.
     */
    public String mostrarSucursal() {
        return this.sucursal ? "si" : "no";
    }

    /**
     * Muestra el nombre del paso peatonal si existe, o indica que no tiene.
     * @return Nombre del paso peatonal o "no tiene paso peatonal" si no tiene.
     */
    public String mostrarPasoPeatonal() {
        return this.tienePasoPeatonal() ? this.pasoPeatonal.getNombre() : "no tiene paso peatonal";
    }

    /**
     * Convierte la parada a una representación en cadena de texto.
     * @return String con el nombre, si es sucursal, y el paso peatonal de la parada.
     */
    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Sucursal: " + this.mostrarSucursal() + ", Paso Peatonal: " + this.mostrarPasoPeatonal();
    }

    /**
     * Muestra un cuadro de diálogo con la información de la parada.
     */
    public void mostrar() {
        JOptionPane.showMessageDialog(null, this.toString());
    }
}


