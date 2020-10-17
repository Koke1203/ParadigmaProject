/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Logica;
import java.util.Stack;

/**
 *
 * @author Joan Corea
 */
public class Expresion {
    
    public Expresion(){}
    
    
    
    
    //METODO GLOBAL PARA LA EVALUACION
    public String resultadoEvaluacion(String[][] tabla, String expresion){
        String resultado_final = "";
        //verifico si en la expresion se encuentra un implica 
        if(expresion.contains("->")){
            int pos = expresion.indexOf("->");
            if(expresion.charAt(pos-1)=='<'){  //es doble implicacion
                resultado_final = casoImplicacion(tabla,expresion);
            }else{ //es simple implicacion
                resultado_final = casoDobleImplicacion(tabla,expresion);
            }
        }else{ // no tiene implicacion
            for (int i = 0; i < tabla.length; i++) {
                resultado_final += evaluarExpresion(i, tabla, expresion);
            }
        }
        return resultado_final;
    }
    
    
    
    
    //VALIDACION
    public String dividirExpresionOperandos(String formula) {
        //cuando se presione el boton verificar se llama a este metodo
        //para pintar en la tabla los operandos (encabezado)
        //(p * (q + r)) -> s, se tiene que tomar en cuenta que ¬x != x
        String operandos = "";
        //recorremos todo la cadena caracter por caracter y vamos preguntando si es una
        //letra del abecedario
        for (int i = 0; i < formula.length(); i++) {
            if (Character.isLetter(formula.charAt(i))) {
                if (i > 1) { //si solo hay un digito no es necesario ir a revisar si está repetido
                    if (esDuplicado(operandos, formula.charAt(i))) {
                        operandos += formula.charAt(i);
                    }
                } else {
                    operandos += formula.charAt(i);
                }
            }
        }
        return operandos;
    }

    //Se verifica que la expresion sea correcta
    //Balance de parentesis
    //operandor: operando,operador,operando || operando,operador,parentesis || parentesis,operador,operando
    public boolean esExpresion(String formula) {
        boolean retorno = true;
        //contadores
        int apertura_parentesis = 0;
        int clausura_parentesis = 0;

        //Balance de parentesis
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == '(') {
                apertura_parentesis++;
            } else if (formula.charAt(i) == ')') {
                clausura_parentesis++;
            }
        }

        //si los parentesis estan balanceados proceda
        if (apertura_parentesis == clausura_parentesis && formula.length() > 2) {
            //reglas operador
            for (int i = 0; i < formula.length() - 2; i++) {
                //operador: operando,operador,operando || operando,operador,parentesis || parentesis,operador,operando
                if ((Character.isLetter(formula.charAt(i)) && esOperador(formula.charAt(i + 1)) && Character.isLetter(formula.charAt(i + 2)))
                        || (esOperador(formula.charAt(i)) && Character.isLetter(formula.charAt(i + 1)) && esOperador(formula.charAt(i)))
                        || (("" + formula.charAt(i)).equals("(") && Character.isLetter(formula.charAt(i + 1)) && esOperador(formula.charAt(i + 2)))
                        || (esOperador(formula.charAt(i)) && Character.isLetter(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals(")"))
                        || (Character.isLetter(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals(")") && ("" + formula.charAt(i + 2)).equals(")"))
                        || (Character.isLetter(formula.charAt(i)) && esOperador(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals("("))
                        || (esOperador(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals("(") && Character.isLetter(formula.charAt(i + 2)))
                        || (Character.isLetter(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals("-") && ("" + formula.charAt(i + 2)).equals(">"))
                        || (("" + formula.charAt(i)).equals(")") && ("" + formula.charAt(i + 1)).equals("-") && ("" + formula.charAt(i + 2)).equals(">"))
                        || (("" + formula.charAt(i)).equals("-") && ("" + formula.charAt(i + 1)).equals(">") && Character.isLetter(formula.charAt(i + 2)))
                        || (("" + formula.charAt(i)).equals("-") && ("" + formula.charAt(i + 1)).equals(">") && ("" + formula.charAt(i + 2)).equals("("))
                        || (("" + formula.charAt(i)).equals(")") && ("" + formula.charAt(i + 1)).equals(")") && ("" + formula.charAt(i + 2)).equals("-"))
                        || (("" + formula.charAt(i)).equals("(") && ("" + formula.charAt(i + 1)).equals("¬") && Character.isLetter(formula.charAt(i + 2)))
                        || (("" + formula.charAt(i)).equals("¬") && Character.isLetter(formula.charAt(i + 1)) && esOperador(formula.charAt(i + 2)))
                        || (("" + formula.charAt(i)).equals("¬") && (("" + formula.charAt(i + 1)).equals("(")) && Character.isLetter(formula.charAt(i + 2)))
                        || (Character.isLetter(formula.charAt(i)) && (("" + formula.charAt(i + 1)).equals(")")) && ("" + formula.charAt(i + 2)).equals("-"))
                        || (("" + formula.charAt(i)).equals(">") && (("" + formula.charAt(i + 1)).equals("(")) && Character.isLetter(formula.charAt(i + 2)))
                        || ((("" + formula.charAt(i)).equals(")")) && esOperador(formula.charAt(i + 1)) && Character.isLetter(formula.charAt(i + 2)))
                        || (Character.isLetter(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals(")") && esOperador(formula.charAt(i + 2)))
                        || (("" + formula.charAt(i)).equals(")") && ("" + formula.charAt(i + 1)).equals(")") && esOperador(formula.charAt(i + 2)))
                        || (("" + formula.charAt(i)).equals(")") && esOperador(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals("("))
                        || (Character.isLetter(formula.charAt(i)) && esOperador(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals("¬"))
                        || (esOperador(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals("¬") && Character.isLetter(formula.charAt(i + 2)))
                        || (esOperador(formula.charAt(i)) && ("" + formula.charAt(i + 1)).equals("(") && ("" + formula.charAt(i + 2)).equals("¬"))
                        || (("" + formula.charAt(i)).equals(">") && ("" + formula.charAt(i + 1)).equals("(") && ("" + formula.charAt(i + 2)).equals("¬"))
                        || (("" + formula.charAt(i)).equals("¬") && Character.isLetter(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals(")"))
                        || (("" + formula.charAt(i)).equals(">") && Character.isLetter(formula.charAt(i + 1)) && esOperador(formula.charAt(i + 2)))) {
                    retorno = true;
                    /*  */
                } else {
                    retorno = false;
                    return retorno;
                }
            }
            //no puede terminar con un operador
            if (esOperador(formula.charAt(formula.length() - 1)) || esOperador(formula.charAt(0))) {
                retorno = false;
            }

        } else {
            retorno = false;
        }

        return retorno;
    }

    public boolean esOperador(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '∨' || c == '∧') {
            return true;
        } else {
            return false;
        }
    }

    //funcion que evita que hayan duplicados en el encabezado de la tabla
    public boolean esDuplicado(String formula, char operando) {
        boolean retorno = true;
        for (int i = 0; i < formula.length(); i++) {
            if (formula.charAt(i) == operando) {
                retorno = false;
                return retorno;
            }
        }
        return retorno;
    }
    
    
    
    
    //CASO DE IMPLICACION
    public String casoImplicacion(String[][] tabla,String expresion){
        String aux1="";
        String aux2="";
        String resultado_final="";
        String resultado_aux1 = "";
        String resultado_aux2 = "";
        int pos = expresion.indexOf("->");
        
        aux1 = expresion.substring(0, pos);
        aux2 = expresion.substring(pos+2,expresion.length()-1);
        
        for (int i = 0; i < tabla.length; i++) {
            resultado_aux1 += evaluarExpresion(i, tabla, aux1);
            resultado_aux2 += evaluarExpresion(i, tabla, aux2);
        }
        
        String vector1[] = resultado_aux1.split(",");
        String vector2[] = resultado_aux2.split(",");
        
        for(int i=0; i<vector1.length; i++){
            if(vector1[i].equals("true") && vector2[i].equals("false")){
                resultado_final+="false,";
            }else{
                resultado_final+="true,";
            }
        }
        
        return resultado_final;
    }
    
    
    
    
    //CASO DE DOBLE IMPLICACION 
    public String casoDobleImplicacion(String[][] tabla,String expresion){
        String aux1="";
        String aux2="";
        String resultado_final="";
        String resultado_aux1 = "";
        String resultado_aux2 = "";
        int pos = expresion.indexOf("<->");
        
        aux1 = expresion.substring(0, pos);
        aux2 = expresion.substring(pos+3,expresion.length()-1);

        for (int i = 0; i < tabla.length; i++) {
            resultado_aux1 += evaluarExpresion(i, tabla, aux1);
            resultado_aux2 += evaluarExpresion(i, tabla, aux2);
        }
        
        String vector1[] = resultado_aux1.split(",");
        String vector2[] = resultado_aux2.split(",");
        
        for(int i=0; i<vector1.length; i++){
            if((vector1[i].equals("true") && vector2[i].equals("true")) 
                    || (vector1[i].equals("false") && vector2[i].equals("false"))){
                resultado_final+="true,";
            }else{
                resultado_final+="false,";
            }
        }
        
        return resultado_final;
    }
    
    
    
    
    //PARA EVALUAR LA EXPRESION 
    
    public String evaluarExpresion(int pos,String[][] tabla,String expresion) {
        Stack<String> operadores = new Stack<String>();
        Stack<String> variables = new Stack<String>();
        int j=0;
        char[] encabezado = new char[3];
        encabezado[0]='p';
        encabezado[1]='q';
        encabezado[2]='r';
        //encabezado[3]='m';
        String resultado="";
        for (int i = 0; i < expresion.length(); i++) {
            resultado = "";
            while (expresion.charAt(i) != ')') {
                if (Character.isLetter(expresion.charAt(i))) {
                    variables.push(expresion.charAt(i)+"");
                } else {
                    operadores.push(expresion.charAt(i)+"");
                }
                i++;
            }
            i++;
            resultado += evaluarPilas(operadores, variables,tabla[pos],encabezado)+",";
        }
        //System.out.println(resultado);
        return resultado;
    }
    
    public String evaluarPilas(Stack<String> operadores, Stack<String> variables, String[] fila, char[] encabezado) {
        //Stack
        char operador;
        String var1;
        String var2;
        String valor_variable1="";
        String valor_variable2="";
        String valor_final = "";
        char vector[] = divisionOperadoresLogicos(operadores);
        
        for (int i = 0; i < vector.length; i++) {
            operador = vector[i];
            var1 = variables.pop();
            var2 = variables.pop();
            
            //validamos la primera variable
            if(var1.equals("1")){
                valor_variable1 = "true";
            }else if(var1.equals("0")){
                valor_variable1 = "false";
            }else{
                valor_variable1 = fila[retornaPosicionVariable(var1,encabezado)];
            }
            //validamos la segunda variable
            if(var2.equals("1")){
                valor_variable2 = "true";
            }else if(var2.equals("0")){
                valor_variable2 = "false";
            }else{
                valor_variable2 = fila[retornaPosicionVariable(var2,encabezado)];
            }
            
            if(operador=='∧'){
                if(valor_variable1.equals("true") && valor_variable2.equals("true")){
                    valor_final="true";
                    variables.push("1");
                }else{
                    valor_final="false";
                    variables.push("0");
                }
            }else{
                if(valor_variable1.equals("false")&&valor_variable2.equals("false")){
                    valor_final="false";
                    variables.push("0");
                }else{
                    valor_final="true";
                    variables.push("1");
                }
            }
        }
        return valor_final;
    }
    
    public char[] divisionOperadoresLogicos(Stack<String> operadores) {
        //cantidad de operadores
        char vector[] = new char[cantidadOperadores(operadores)]; 
        String var;
        int cont=0;
        while (!operadores.isEmpty()) {
            var = (String) operadores.pop();
            if (var.equals("∧") || var.equals("∨")) {
                vector[cont++]=var.charAt(0);
            }
        }
        return vector;
    }
    
    public int cantidadOperadores(Stack<String> operadores){
        int cont=0;
        Object[] arr = operadores.toArray();
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals("∧") || arr[i].equals("∨")){
                cont++;
            }
        }
        return cont;
    }
    
    public int retornaPosicionVariable(String var, char[] encabezado){
        int pos = 0;
        for(int i=0; i<encabezado.length; i++){
            if((encabezado[i]+"").equals(var)){
                return pos=i;
            }
        }
        return pos;
    }
    
}
