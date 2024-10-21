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
public class ListaSimple {
    

    private Nodo pFirts;
    private int size;

    public ListaSimple() {
        this.pFirts = null;
        this.size = 0;
    }

    public Nodo getpFirts() {
        return pFirts;
    }

    public void setpFirts(Nodo pFirts) {
        this.pFirts = pFirts;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean esVacia() {
        return this.pFirts == null;
    }

    public void insertarFinal(Object dato) {
        Nodo pNew = new Nodo();
        pNew.setElemento(dato);
        if (this.esVacia()) {
            this.setpFirts(pNew);
        } else {
            Nodo aux = this.pFirts;
            while (aux.getSiguiente() != null) {
                aux = aux.getSiguiente();
            }
            aux.setSiguiente(pNew);
        }
        size++;
    }

    public void eliminarFinal() {
        if (!this.esVacia()) {
            if (this.getSize() == 1) {
                this.setpFirts(null);
            } else {
                Nodo aux = this.pFirts;
                while (aux.getSiguiente().getSiguiente() != null) {
                    aux = aux.getSiguiente();
                }
                aux.setSiguiente(null);
            }
        }
    }

    public boolean buscar(Object ref) {
        if (!this.esVacia()) {
            if (this.getSize() == 1) {
               return this.pFirts.getElemento() == ref;
            } else {
                Nodo aux = this.pFirts;
                while (aux != null) {
                    if(aux.getElemento() == ref){
                        return true;
                    }
                    aux = aux.getSiguiente();
                }
                return false;
            }
        }

        return false;
    }
    
    public String transformar(){
        if (!this.esVacia()) {
            String listaStr = "";
            if (this.getSize() == 1) {
               Vertice vertice = (Vertice) this.pFirts.getElemento();
               listaStr += vertice.getParada().getNombre();
            } else {
                Nodo aux = this.pFirts;
                while (aux != null) {
                    Vertice vertice = (Vertice) aux.getElemento();
                    if(aux.getSiguiente() == null){
                        listaStr += vertice.getParada().getNombre();
                        break;
                    }
                    listaStr += vertice.getParada().getNombre() + " -> ";
                    aux = aux.getSiguiente();
                }
            }
            return listaStr;
        }else{
            return "La lista esta vacia";
        }
    }

    @Override
    public String toString() {
       if (!this.esVacia()) {
            String listaStr = "";
            if (this.getSize() == 1) {
               listaStr += this.pFirts.getElemento().toString();
            } else {
                Nodo aux = this.pFirts;
                while (aux != null) {
                    if(aux.getSiguiente() == null){
                        listaStr += aux.getElemento().toString();
                        break;
                    }
                    listaStr += aux.getElemento().toString() + "\n";
                    aux = aux.getSiguiente();
                }
            }
            return listaStr;
        }else{
            return "La lista esta vacia";
        }
    
    }
    
    public void mostrar(){
        JOptionPane.showMessageDialog(null, this.toString());
    }
        

}


