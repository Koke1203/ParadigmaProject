/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simplificacion {

    public Simplificacion() {
    }

    //Distributiva
    public String esDistributiva(String expresion_general) {
        String expresion[] = new String[2];
        String regex = "[(]¬*[a-z][∧∨]¬*[a-z][)][∧∨][(]¬*[a-z][∧∨]¬*[a-z][)]";
        int k = 0;
        int l = 0;
        String replaceFirst = expresion_general;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expresion_general);
        char[] var_negativas = new char[3];
        boolean actualiza = true;
        String auxiliar = "";
        char[] var_repetidas = new char[2];
        int q=0;
        // revisa las ocurrencias dentro del string
        while (matcher.find()) {
            expresion[k++] = matcher.group();
        }

        replaceFirst = replaceFirst.replaceAll("¬", "");
        System.out.println("ReplaceFirst: " + replaceFirst);

        for (int i = 0; i < expresion.length; i++) {
            //recorremos la expresion
            for (int j = 0; j < expresion[i].length() - 1; j++) {
                if (expresion[i].charAt(j) == '¬') {
                    var_negativas[l++] = expresion[i].charAt(j + 1);
                }
            }
            
            expresion[i] = expresion[i].replaceAll("¬", "");
            auxiliar = expresion[i];
            if (expresion[i].matches(regex)) {
                if (expresion[i].charAt(2) == expresion[i].charAt(8) && expresion[i].charAt(2) != expresion[i].charAt(5)) {
                    if (expresion[i].charAt(1) == expresion[i].charAt(7) || expresion[i].charAt(1) == expresion[i].charAt(9)) {
                        if (expresion[i].charAt(1) == expresion[i].charAt(7)) {
                            //validar que la letra que se repite aparece dos veces en el vector de variables
                            //negativas
                            var_repetidas[q] = expresion[i].charAt(1);
                            if (cantRepetida(var_negativas, expresion[i].charAt(1)) == 0 || cantRepetida(var_negativas, expresion[i].charAt(1)) > 1) {
                                expresion[i] = "(" + expresion[i].charAt(1) + expresion[i].charAt(2) + "(" + expresion[i].charAt(3)
                                        + expresion[i].charAt(5) + expresion[i].charAt(9) + "))";
                            } else {
                                actualiza = false;
                            }
                        } else {
                            var_repetidas[q] = expresion[i].charAt(1);
                            if (cantRepetida(var_negativas, expresion[i].charAt(1)) == 0 || cantRepetida(var_negativas, expresion[i].charAt(1)) > 1) {
                                expresion[i] = "(" + expresion[i].charAt(1) + expresion[i].charAt(2) + "(" + expresion[i].charAt(3)
                                        + expresion[i].charAt(5) + expresion[i].charAt(7) + "))";
                            } else {
                                actualiza = false;
                            }
                        }
                    } else if (expresion[i].charAt(3) == expresion[i].charAt(7) || expresion[i].charAt(3) == expresion[i].charAt(9)) {
                        if (expresion[i].charAt(3) == expresion[i].charAt(7)) {
                            //si valida igual
                            var_repetidas[q] = expresion[i].charAt(3);
                            if (cantRepetida(var_negativas, expresion[i].charAt(3)) == 0 || cantRepetida(var_negativas, expresion[i].charAt(3)) > 1) {
                                expresion[i] = "(" + expresion[i].charAt(3) + expresion[i].charAt(2) + "(" + expresion[i].charAt(1)
                                        + expresion[i].charAt(5) + expresion[i].charAt(9) + "))";
                            } else {
                                actualiza = false;
                            }
                        } else {
                            var_repetidas[q] = expresion[i].charAt(3);
                            if (cantRepetida(var_negativas, expresion[i].charAt(3)) == 0 || cantRepetida(var_negativas, expresion[i].charAt(3)) > 1) {
                                expresion[i] = "(" + expresion[i].charAt(3) + expresion[i].charAt(2) + "(" + expresion[i].charAt(1)
                                        + expresion[i].charAt(5) + expresion[i].charAt(7) + "))";
                            } else {
                                actualiza = false;
                            }
                        }
                    }
                } else {
                    System.out.println("F");
                }
            }
            
            if (actualiza) {
                replaceFirst = replaceFirst.replace(auxiliar, expresion[i]);
            }
            
            actualiza = true;
        }
        
        //ponemos los negativos
        for (int i = 0; i < var_negativas.length; i++) {
            replaceFirst = replaceFirst.replaceAll(var_negativas[i] + "", "¬" + var_negativas[i]); 
        }
        
        return replaceFirst;
    }

    public int cantRepetida(char[] negativas, char var) {
        int cont = 0;
        for (int i = 0; i < negativas.length; i++) {
            if (negativas[i] == var) {
                cont++;
            }
        }
        return cont;
    }

    //Absorción
//Idempotencia
//Doble negacion
}
