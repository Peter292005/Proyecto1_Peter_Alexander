/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

/**
 *
 * @author mateusnaddaf
 */
public class Helpers {
    public int esNumero(String numero) {
        try {
            return Integer.parseInt(numero); 
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    public boolean estaRango(int min, int max, int num){
        return num >= min && num<= max;
    }
}
