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
public class Canonica {
    
    public Canonica(){}
    
    
    //metodo para sacar la canonica disyuntiva
    public String calculaDisyuntiva(String[][] tabla, String variables[]) {
        int contVariables = variables.length - 2;
        String expresion_final = "";
        for (int j = 0; j < tabla.length; j++) {
            if (tabla[j][contVariables].equals("true")) {
                //   System.out.print("Pos: " + j);
                for (int i = 0; i < contVariables + 1; i++) {
                    if (tabla[j][i].equals("true")) {
                        expresion_final += variables[i];
                    } else {
                        expresion_final += "¬" + variables[i];
                    }
                    if (i == contVariables) {
                        expresion_final += " + ";
                    }
                }
            }
        }
        //para que ignore el ultimo +
        expresion_final = expresion_final.substring(0, expresion_final.length() - 1);
        //System.out.print("Resultado final: "+expresion_final);
        return expresion_final;
    }
    
    
    //metodo para sacar la canonica conjuntiva
    public String calculaConjuntiva(String[][] tabla, int contVariables, String variables[]) {
        String expresion_final = "";
        for (int j = 0; j < tabla.length; j++) {
            if (tabla[j][contVariables].equals("false")) {
                for (int i = 0; i < contVariables + 1; i++) {
                    if (i == 0) {
                        expresion_final += "(";
                    }
                    
                    if (tabla[j][i].equals("true")) {
                        if (i == contVariables) {
                            expresion_final += variables[i];
                            expresion_final += " ) ";
                        } else {
                            expresion_final += variables[i] + " + ";
                        }
                    } else {
                        if (i == contVariables) {
                            expresion_final += "¬" + variables[i];
                            expresion_final += ")";
                        } else {
                            expresion_final += "¬" + variables[i] + " + ";
                        }
                    }
                }
            }
        }
        //para que ignore el ultimo +
        expresion_final = expresion_final.substring(0, expresion_final.length() - 1);
      //  System.out.print("Resultado final: " + expresion_final + "\n");
        return expresion_final;
    }
    
}
