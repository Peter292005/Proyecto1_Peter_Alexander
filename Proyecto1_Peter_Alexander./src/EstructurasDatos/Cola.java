/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import javax.swing.JOptionPane;

/**
 *
 * @author mateusnaddaf
 */
public class Cola {
    private NodoCola cabeza;
    private NodoCola cola;
    private int size;
    
    public Cola() {
        this.cabeza = this.cola = null;
        this.size = 0;
    }
    
    public NodoCola getCabeza() {
        return cabeza;
    }
    
    public void setCabeza(NodoCola cabeza) {
        this.cabeza = cabeza;
    }
    
    public NodoCola getCola() {
        return cola;
    }
    
    public void setCola(NodoCola cola) {
        this.cola = cola;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean colaVacia() {
        return this.cabeza == null;
    }
    
    public void enColar(Object dato) {
        NodoCola nuevo = new NodoCola();
        nuevo.setDato(dato);
        if (this.colaVacia()) {
            this.setCabeza(nuevo);
            this.setCola(nuevo);
        } else {
            this.getCola().setSiguiente(nuevo);
            this.setCola(nuevo);
        }
        size++;
    }
    
    public Object desenColar(){
        if(!this.colaVacia()){
            Object dato = this.cabeza.getDato();
            this.setCabeza(this.cabeza.getSiguiente());
            size--;
            return dato;
        }
        
        return null;
    }

    @Override
    public String toString() {
        if(!this.colaVacia()){
          String colaStr = "Cola:\n";
          if(this.size == 1){
              colaStr += this.cabeza.getDato().toString();
              return colaStr;
          }else{
              NodoCola aux = this.cabeza;
              while(aux != null){
                  if(aux.getSiguiente() == null){
                      colaStr += aux.getDato().toString();
                      break;
                  } 
                  colaStr += aux.getDato().toString() + " -> ";
                  aux = aux.getSiguiente();
              }
              
              return colaStr;
          }
        }
        
        return null;
    }
    
    
    
    public void mostrar(){
       if(this.toString() == null){
           JOptionPane.showMessageDialog(null, "La cola esta vacia.");
       }else{
           JOptionPane.showMessageDialog(null, this.toString());
       }
    }
}


