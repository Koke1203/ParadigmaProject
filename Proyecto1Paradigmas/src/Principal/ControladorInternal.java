/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Datos.Archivo;
import Vista.FormulaJInternalFrame;
import Vista.Vista;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Joan Corea
 */
public class ControladorInternal implements ActionListener {

    public FormulaJInternalFrame internal = new FormulaJInternalFrame();

    public ControladorInternal(boolean nuevo) {
        if (nuevo) {
            //cada vez que se abre un txt se genera un internal frame nuevo
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);
            File archivo = fc.getSelectedFile();
            String formula = "";
            try {
                Archivo archivo_clase = new Archivo();
                formula = archivo_clase.leerArchivo(archivo);
                internal.txtExpresion.setText(formula);
                System.out.println("Archivo leido correctamente");
            } catch (Exception ee) {
                System.out.println("Problemas al leer el archivo");
            }
        }
        internal.btnVerificar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == internal.btnVerificar) {
            if (!internal.txtExpresion.getText().isEmpty()) {
                escuchaVerificar(internal.txtExpresion.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Debe digitar una expresion válida");
            }
            System.out.println("Escuchando boton verificar");
        }
    }

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

    public void escuchaVerificar(String formula) {
        //se llama al metodo para obtener los operandos
        if (esExpresion(formula.replaceAll("\\s", ""))) {
            String formula_division = dividirExpresionOperandos(formula);

            int aux = formula_division.length() + 1;
            String variables[] = new String[aux];

            variables[aux - 1] = "f(";
            for (int i = 0; i < formula_division.length(); i++) {
                variables[i] = formula_division.charAt(i) + "";
                //ir concantenando la ultima columna, donde se evalua la expresion
                if (i == aux - 1) {
                    variables[aux - 1] += ")";
                } else {
                    if (i == aux - 2) {
                        variables[aux - 1] += formula_division.charAt(i);
                    } else {
                        variables[aux - 1] += formula_division.charAt(i) + ", ";
                    }
                }
            }

            variables[aux - 1] += ")";
            String tablaVerdad[][] = obtenerTablaDeVerdad(variables.length - 1);

            //Encabezado de la tabla
            internal.tbVerdad.setModel(
                    new javax.swing.table.DefaultTableModel(
                            tablaVerdad,
                            variables
                    ));

        } else {
            JOptionPane.showMessageDialog(null, "Debe digitar una expresion válida");
        }
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
                        || (("" + formula.charAt(i)).equals("¬") && Character.isLetter(formula.charAt(i + 1)) && ("" + formula.charAt(i + 2)).equals(")"))) {
                    retorno = true;
                    /* */
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

    private String[][] obtenerTablaDeVerdad(int n) {
        int filas = (int) Math.pow(2, n);
        String tablaVerdad[][];
        tablaVerdad = new String[filas][n];

        for (int i = 0; i < filas; i++) {
            for (int j = n - 1; j >= 0; j--) {
                tablaVerdad[i][j] = (((i / (int) Math.pow(2, j)) % 2) == 1) + "";
            }
        }
        return tablaVerdad;
    }

}
