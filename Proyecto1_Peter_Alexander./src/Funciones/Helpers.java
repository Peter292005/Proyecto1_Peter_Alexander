/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Funciones;

/**
 * Clase de métodos auxiliares para verificar y manejar números.
 * 
 * Proporciona métodos para convertir cadenas a números enteros y para verificar
 * si un número está dentro de un rango específico.
 * 
 * @author PeterNaddaf
 */
public class Helpers {

    /**
     * Verifica si una cadena representa un número entero.
     * @param numero Cadena a verificar.
     * @return El número entero si la conversión es exitosa, -1 si no es un número.
     */
    public int esNumero(String numero) {
        try {
            return Integer.parseInt(numero); 
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * Verifica si un número está dentro de un rango específico.
     * @param min Límite inferior del rango.
     * @param max Límite superior del rango.
     * @param num Número a verificar.
     * @return true si el número está en el rango, false si no.
     */
    public boolean estaRango(int min, int max, int num) {
        return num >= min && num <= max;
    }
}