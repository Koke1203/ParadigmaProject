/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author Joan Corea
 */
public class Tabla {
    
    public Tabla(){}
    
    public String[][] obtenerTablaDeVerdad(int n) {
        int filas = (int) Math.pow(2, n);
        String tablaVerdad[][];
        tablaVerdad = new String[filas][n+1];
        
        for (int i = 0; i < filas; i++) {
            for (int j = n - 1; j >= 0; j--) {
                tablaVerdad[i][j] = (((i / (int) Math.pow(2, j)) % 2) == 0) + "";
            }
        }
        return tablaVerdad;
    }
    
}
