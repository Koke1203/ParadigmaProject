/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 *
 * @autor Joan Corea
 */
public class Archivo {

    FileWriter archivo;
    PrintWriter pw;
    File archivoLeido;
    FileReader fr;
    BufferedReader br;

    public Archivo() {
    }

    public String leerArchivo(File archivo) {
        String informacion = "";
        String linea = "";
        try {
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);
            while ((linea = br.readLine()) != null) {
                informacion += linea ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return informacion;
    }

}
