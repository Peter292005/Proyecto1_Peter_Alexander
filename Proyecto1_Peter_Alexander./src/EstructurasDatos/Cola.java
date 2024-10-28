/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EstructurasDatos;

import javax.swing.JOptionPane;

/**
 * 
 * Clase que representa una Cola (estructura de datos FIFO - First In, First Out).
 * Permite agregar elementos al final de la cola y sacarlos desde el inicio.
 * @author PeterNaddaf
 */
public class Cola {
    private NodoCola cabeza;
    private NodoCola cola;
    private int size;
    
    /**
     * Constructor de la clase Cola.
     * Inicializa la cola vacía, con cabeza y cola en null y tamaño en 0.
     */
    public Cola() {
        this.cabeza = this.cola = null;
        this.size = 0;
    }
    
    /**
     * Obtiene el primer nodo de la cola.
     * @return NodoCola que representa la cabeza de la cola.
     */
    public NodoCola getCabeza() {
        return cabeza;
    }
    
    /**
     * Define el primer nodo de la cola.
     * @param cabeza NodoCola a asignar como cabeza.
     */
    public void setCabeza(NodoCola cabeza) {
        this.cabeza = cabeza;
    }
    
    /**
     * Obtiene el último nodo de la cola.
     * @return NodoCola que representa la cola.
     */
    public NodoCola getCola() {
        return cola;
    }
    
    /**
     * Define el último nodo de la cola.
     * @param cola NodoCola a asignar como cola.
     */
    public void setCola(NodoCola cola) {
        this.cola = cola;
    }
    
    /**
     * Obtiene el tamaño de la cola.
     * @return int con el número de elementos en la cola.
     */
    public int getSize() {
        return size;
    }
    
    /**
     * Establece el tamaño de la cola.
     * @param size Número de elementos en la cola.
     */
    public void setSize(int size) {
        this.size = size;
    }
    
    /**
     * Verifica si la cola está vacía.
     * @return true si la cola está vacía, false si tiene elementos.
     */
    public boolean colaVacia() {
        return this.cabeza == null;
    }
    
    /**
     * Agrega un nuevo elemento al final de la cola.
     * @param dato Objeto a agregar en la cola.
     */
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
    
    /**
     * Elimina y retorna el primer elemento de la cola.
     * @return Objeto que estaba en la cabeza de la cola o null si está vacía.
     */
    public Object desenColar(){
        if(!this.colaVacia()){
            Object dato = this.cabeza.getDato();
            this.setCabeza(this.cabeza.getSiguiente());
            size--;
            return dato;
        }
        return null;
    }

    /**
     * Devuelve una representación en cadena de la cola.
     * @return String que muestra todos los elementos de la cola en orden.
     */
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
    
    /**
     * Muestra la representación de la cola en un cuadro de diálogo.
     * Si la cola está vacía, indica que está vacía.
     */
    public void mostrar(){
       if(this.toString() == null){
           JOptionPane.showMessageDialog(null, "La cola está vacía.");
       }else{
           JOptionPane.showMessageDialog(null, this.toString());
       }
    }
}