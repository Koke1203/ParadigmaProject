/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Datos.Archivo;
import Logica.Canonica;
//import Logica.Expresion;
import Logica.Tabla;
import Vista.FormulaJInternalFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Joan Corea
 */
public class ControladorInternal implements ActionListener {
    
    public FormulaJInternalFrame internal = new FormulaJInternalFrame();
    public boolean archivo_abierto = false;  //para saber si ya abrimos un archivo
    
    JFileChooser seleccionar = new JFileChooser();
    File archivo;
    Archivo archivo_clase = new Archivo();
    String variables[];
    Expresion objeto_expresion;
    Canonica canonica;
    Tabla tabla;
    
    public ControladorInternal(boolean nuevo) {
        objeto_expresion = new Expresion();
        tabla = new Tabla();
        canonica = new Canonica();
        if (nuevo) {
            //cada vez que se abre un txt se genera un internal frame nuevo
            JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);
            File archivo_nuevo = fc.getSelectedFile();
            String formula = "";
            try {
                Archivo archivo_clase = new Archivo();
                formula = archivo_clase.leerArchivo(archivo_nuevo);
                internal.txtExpresion.setText(formula);
                //se actualiza porque ya se abrió un archivo
                archivo = archivo_nuevo;
                seleccionar = fc;
                archivo_abierto = true;
                //
                System.out.println("Archivo leido correctamente");
            } catch (Exception ee) {
                System.out.println("Problemas al leer el archivo");
            }
        }
        internal.btnVerificar.addActionListener(this);
        internal.btnGuardar.addActionListener(this);
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
        } else if (e.getSource() == internal.btnGuardar) {
            if (!internal.txtExpresion.getText().isEmpty()) {
                escuchaGuardarArchivo();
            } else {
                JOptionPane.showMessageDialog(null, "No hay información para guardar");
            }
        }
    }
    
    private void escuchaGuardarArchivo() {
        String documento = "";
        if (archivo_abierto) {
            documento = internal.txtExpresion.getText();
            archivo_clase.guardarArchivo(archivo, documento);
        } else {
            if (seleccionar.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION) {
                archivo = seleccionar.getSelectedFile();
                if (!archivo.getName().endsWith("txt")) {
                    archivo = new File(archivo.toString() + ".txt");
                }
                documento = internal.txtExpresion.getText();
                archivo_clase.guardarArchivo(archivo, documento);
                System.out.println("Archivo Guardado");
                archivo_abierto = true;
            } else {
                JOptionPane.showMessageDialog(null, "No se guardó");
            }
        }
    }
    
    public void escuchaVerificar(String formula) {
        //se llama al metodo para obtener los operandos
        if (objeto_expresion.esExpresion(formula.replaceAll("\\s", ""))) {
            //JOptionPane.showMessageDialog(null, "TODO BIEN");
            String resultado_evaluacion="";
            String formula_division = objeto_expresion.dividirExpresionOperandos(formula);
            
            int aux = formula_division.length() + 1;
            variables = new String[aux];
            
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
            
            System.out.println(variables.length-1);
            String tablaVerdad[][] = tabla.obtenerTablaDeVerdad(variables.length-1);
            
            //modificar tabla de verdad y ponerle la ultima columna como el resultado 
            //que nos devuelve el metodo resultadoEvaluacion de la clase Expresion 
            //recibe por parametro la tabla y una expresion
            //El resultado viene separado por ','s (comas)
            resultado_evaluacion = objeto_expresion.resultadoEvaluacion(tablaVerdad, formula);
            //concatenar este resultado como un vector a la ultima columna de la tabla
            
            System.out.println(tablaVerdad.length);
            
            String columna_final[] = resultado_evaluacion.split(",");
            for(int i=0; i<tablaVerdad.length; i++){
                tablaVerdad[i][variables.length-1] = columna_final[i];
            }
            //---------------------------------------------------------------------
            
            //Encabezado de la tabla
            internal.tbVerdad.setModel(
                    new javax.swing.table.DefaultTableModel(
                            tablaVerdad,
                            variables
                    ));
            
            //canonica.calculaDisyuntiva(tablaVerdad, variables)
            //System.out.println(canonica.calculaDisyuntiva(tablaVerdad, variables));
            //canonica.calculaConjuntiva(tablaVerdad, variables);
        } else {
            JOptionPane.showMessageDialog(null, "Debe digitar una expresion válida");
        }
    }
    
}
