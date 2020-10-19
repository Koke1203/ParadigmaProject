/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simplificacion {

    public Simplificacion() {
    }
    
    public String aplicaLeyes(String expresion_general){
        String resultado = expresion_general;
        
        resultado = aplicaDobleNegacion(resultado);
        resultado = aplicaDeMorgan(resultado);
        resultado = aplicaDobleNegacion(resultado);
        resultado = aplicaIdempotencia(resultado);
        resultado = aplicaDistributiva(resultado);
        resultado = aplicaAbsorcion(resultado); 
        return resultado;
    }

    //Distributiva
    public String aplicaDistributiva(String expresion_general) {
        ArrayList<String> expresion = new ArrayList<String>();
        String regex = "[(]¬*[a-z][∧∨]¬*[a-z][)][∧∨][(]¬*[a-z][∧∨]¬*[a-z][)]";
        int l = 0;
        String replaceFirst = expresion_general;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expresion_general);
        String aux = "";

        // revisa las ocurrencias dentro del string
        while (matcher.find()) {
            expresion.add(matcher.group());
        }

        for (int i = 0; i < expresion.size(); i++) {
            //recorremos la expresion
            String auxiliar[] = new String[expresion.get(i).length()];
            //recorremos la expresion individualmente 
            for (int j = 0; j < expresion.get(i).length() - 1; j++) {
                if (expresion.get(i).charAt(j) == '¬') {
                    auxiliar[l++] = "¬" + expresion.get(i).charAt(j + 1);
                    j++;
                } else {
                    auxiliar[l++] = expresion.get(i).charAt(j) + "";
                }
            }

            aux = expresion.get(i);
            if (expresion.get(i).matches(regex)) {
                if (auxiliar[2].equals(auxiliar[8]) && !auxiliar[2].equals(auxiliar[5])) {
                    if (auxiliar[1].equals(auxiliar[7]) || auxiliar[1].equals(auxiliar[9])) {
                        if (auxiliar[1].equals(auxiliar[7])) {
                            expresion.set(i, "(" + auxiliar[1] + auxiliar[2] + "(" + auxiliar[3]
                                    + auxiliar[5] + auxiliar[9] + "))");
                        } else {
                            expresion.set(i, "(" + auxiliar[1] + auxiliar[2] + "(" + auxiliar[3]
                                    + auxiliar[5] + auxiliar[7] + "))");
                        }
                    } else if (auxiliar[3].equals(auxiliar[7]) || auxiliar[3].equals(auxiliar[9])) {
                        if (auxiliar[3].equals(auxiliar[7])) {
                            expresion.set(i, "(" + auxiliar[3] + auxiliar[2] + "(" + auxiliar[1]
                                    + auxiliar[5] + auxiliar[9] + "))");
                        } else {
                            expresion.set(i, "(" + auxiliar[3] + auxiliar[2] + "(" + auxiliar[1]
                                    + auxiliar[5] + auxiliar[7] + "))");
                        }
                    }
                } else {
                    System.out.println("F");
                }
            }

            replaceFirst = replaceFirst.replace(aux, expresion.get(i));
            l = 0; //reiniciamos el valor para las demas vueltas
        }

        return replaceFirst;
    }

    //Absorción
    public String aplicaAbsorcion(String expresion_general) {
        String exp_simp = expresion_general;
        //Expresion regular para buscar match
        String regex = "¬*[a-z][∧∨][(]¬*[a-z][∧∨]¬*[a-z][)]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(exp_simp);
        //Se almacenan en un array los match
        ArrayList<String> subExps_match = new ArrayList<>();
        while (matcher.find()) {
            subExps_match.add(matcher.group());
        }

        int i = 0;
        String sub_expr = "";
        String resultado_exp;
        //Recorrer el array de epresiones que hacen match
        for (i = 0; i < subExps_match.size(); i++) {
            sub_expr = subExps_match.get(i);
            resultado_exp = esAbsorcion(sub_expr);
            if (!resultado_exp.equals(sub_expr)) {
                exp_simp = exp_simp.replace(sub_expr, resultado_exp);
            }
        }

        return exp_simp;

    }

    public String esAbsorcion(String match_absorcion) {
        String resultado;
        boolean negativa = false;
        String primer_variable;
        //Verifico si es negativa para obtener la primer variable
        if (match_absorcion.charAt(0) == '¬') {
            primer_variable = match_absorcion.substring(0, 2);
            negativa = true;
        } else {
            primer_variable = match_absorcion.substring(0, 1);
        }

        boolean primVar_valida = false;
        String oper_bool;
        String exp_parentesis;
        boolean dif_simbolo = true;
        if (negativa) {
            //Si es negativa y alguna de las dos variabes dentro de los parentesis son iguales
            if ((primer_variable.equals(match_absorcion.substring(4, 6)))) {
                primVar_valida = true;
            } else if ((primer_variable.equals(match_absorcion.substring(match_absorcion.length() - 3, match_absorcion.length() - 1)))) {
                primVar_valida = true;
            }
            //Comparación del primero operador 'y' o 'o' con lo que estad dentro del parentesis que sean diferentes
            oper_bool = match_absorcion.substring(2, 3);
            exp_parentesis = match_absorcion.substring(3);
            if (exp_parentesis.contains(oper_bool)) {
                dif_simbolo = false;
            }

            //Caso de no ser negativa
        } else {
            if ((primer_variable.equals(match_absorcion.substring(3, 4)))) {
                primVar_valida = true;
            } else if ((primer_variable.equals(match_absorcion.substring(match_absorcion.length() - 2, match_absorcion.length() - 1)))) {
                primVar_valida = true;
            }
            //Comparación del primero operador 'y' o 'o' con lo que estad dentro del parentesis que sean diferentes
            oper_bool = match_absorcion.substring(1, 2);
            exp_parentesis = match_absorcion.substring(2);
            if (exp_parentesis.contains(oper_bool)) {
                dif_simbolo = false;
            }
        }

        if (primVar_valida && dif_simbolo) {
            resultado = primer_variable;
        } else {
            resultado = match_absorcion;
        }
        return resultado;
    }

    //Idempotencia
    public String aplicaIdempotencia(String expresion_general) {
        String resultado = expresion_general;
        String regex = "[(]*¬*[a-z][)]*[∧∨][(]*¬*[a-z][)]*";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(expresion_general);
        ArrayList<String> expresion = new ArrayList<String>();
        String aux1 = "";
        String aux2 = "";
        String auxiliar = "";

        // revisa las ocurrencias dentro del string
        while (matcher.find()) {
            auxiliar = matcher.group();
            auxiliar = auxiliar.replace("(", "");
            auxiliar = auxiliar.replace(")", "");
            if (auxiliar.substring(0, ((int) auxiliar.length() / 2)).equals(auxiliar.substring(((int) auxiliar.length() / 2) + 1))) {
                expresion.add(matcher.group());
            } else {
             //agarramos la ultima posicion que es una variables y la comparamos con la cadena restante 
                //q∨p∨p = q∨p = end:2 substring(end,length) = pvp = p
                auxiliar = resultado.substring(matcher.end() - 1, resultado.length());
                Matcher matcher1 = pattern.matcher(auxiliar);
                while (matcher1.find()) {
                    auxiliar = matcher1.group();
                    auxiliar = auxiliar.replace("(", "");
                    auxiliar = auxiliar.replace(")", "");
                    if (auxiliar.substring(0, ((int) auxiliar.length() / 2)).equals(auxiliar.substring(((int) auxiliar.length() / 2) + 1))) {
                        expresion.add(matcher1.group());
                    }
                }
            }
            
        }

        auxiliar = "";
        for (int i = 0; i < expresion.size(); i++) {
            if (expresion.get(i) != null) {
                auxiliar = expresion.get(i);
                expresion.set(i, expresion.get(i).replace("(", ""));
                expresion.set(i, expresion.get(i).replace(")", ""));
                while (expresion.get(i).matches(regex)) {
                    aux1 = expresion.get(i).substring(0, ((int) expresion.get(i).length() / 2));
                    aux2 = expresion.get(i).substring(((int) expresion.get(i).length() / 2) + 1);
                    if (aux1.equals(aux2)) {
                        resultado = resultado.replace(auxiliar, aux1);
                        expresion.set(i, resultado);
                    } else {
                        break;
                    }
                }
            }
        }

        //
        if (expresion.isEmpty()) {
            return resultado;
        } else {
            return aplicaIdempotencia(resultado);
        }
    }

    //Doble negacion
    public String aplicaDobleNegacion(String expresion_general) {
        String resultado = expresion_general;
        int cont_negativos = 0;
        String auxiliar = "";
        //vamos recorriendo la expresion y verificando donde hay ¬
        for (int i = 0; i < resultado.length(); i++) {
            //si sale un negativo recorremos todos los negativos que estan continuos a el
            while (expresion_general.charAt(i) == '¬') {
                auxiliar += expresion_general.charAt(i);
                cont_negativos++;
                i++;
            }

            if (cont_negativos > 0) {
                if (cont_negativos % 2 == 0) {
                    resultado = resultado.replace(auxiliar, "");
                } else {
                    resultado = resultado.replace(auxiliar, "¬");
                }
            }
            auxiliar = "";
            cont_negativos = 0;
        }

        return resultado;
    }

    //De Morgan
    //-(-r^-qv-s) = -(-r^-q);   "-(())"
    public String aplicaDeMorgan(String expresion_general) {
        String resultado = expresion_general;
        int index = 0;
        int cont_aper = 0;
        int cont_cla = 0;
        for (int k = 0; k < resultado.length() - 1; k++) {

            if (resultado.charAt(k) == '¬' && resultado.charAt(k + 1) == '(') {
                index = resultado.indexOf("¬(");
                cont_aper++;
                resultado = resultado.substring(0, index)
                        + resultado.substring(index + 1, resultado.length());
                index += 1;
                for (int i = index; i < resultado.length(); i++) {
                    while (cont_aper != cont_cla) {
                        if (resultado.charAt(i) == '(') {
                            cont_aper++;
                        } else if (resultado.charAt(i) == ')') {
                            cont_cla++;
                        }

                        //vamos poniendo los negativos
                        if (Character.isLetter(resultado.charAt(i))) {
                            resultado = addChar(resultado, '¬', i);
                            i++;
                            //resultado = resultado.substring(0, i) + "¬" + resultado.substring(i, resultado.length());
                        } else if (isOperator(resultado.charAt(i))) {
                            if (resultado.charAt(i) == '∧') {
                                resultado = replaceChar(resultado, '∨', i);
                                //resultado = resultado.substring(0, i - 1) + "∨" + resultado.substring(i + 1, resultado.length());
                            } else {
                                resultado = replaceChar(resultado, '∧', i);
                                //resultado = resultado.substring(0, i) + "∧" + resultado.substring(i + 1, resultado.length());
                            }
                        }
                        k++;
                        i++;
                    }
                    i = resultado.length();
                }
            }

        }

        return resultado;
    }

    public String replaceChar(String str, char ch, int position) {
        char[] charArray = str.toCharArray();
        charArray[position] = ch;
        return new String(charArray);
    }

    public String addChar(String str, char ch, int position) {
        int len = str.length();
        char[] updatedArr = new char[len + 1];
        str.getChars(0, position, updatedArr, 0);
        updatedArr[position] = ch;
        str.getChars(position, len, updatedArr, position + 1);
        return new String(updatedArr);
    }

    public boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '∨' || c == '∧') {
            return true;
        } else {
            return false;
        }
    }

}
