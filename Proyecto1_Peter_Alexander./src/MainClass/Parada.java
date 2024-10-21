/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainClass;

import javax.swing.JOptionPane;

/**
 *
 * @author mateusnaddaf
 */
public class Parada {
    private String nombre;
    private boolean sucursal;
    private Parada pasoPeatonal;

    public Parada(String nombre) {
        this.nombre = nombre;
        this.sucursal = false;
        this.pasoPeatonal = null;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSucursal() {
        return sucursal;
    }

    public void setSucursal(boolean sucursal) {
        this.sucursal = sucursal;
    }

    public Parada getPasoPeatonal() {
        return pasoPeatonal;
    }

    public void setPasoPeatonal(Parada pasoPeatonal) {
        this.pasoPeatonal = pasoPeatonal;
    }
    
    public boolean tienePasoPeatonal(){
        return this.pasoPeatonal != null;
    }
    
    public String mostrarSucursal(){
        if(this.sucursal){
            return "si";
        }
        
        return "no";
    }
    
    public String mostrarPasoPeatonal(){
        if(this.tienePasoPeatonal()){
            return this.pasoPeatonal.getNombre();
        }
        
        return "no tiene paso peatonal";
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Sucursal: " + this.mostrarSucursal() + ", Paso Peatonal: " + this.mostrarPasoPeatonal();
    }
    
    public void mostrar(){
        JOptionPane.showMessageDialog(null, this.toString());
    }
    
}


