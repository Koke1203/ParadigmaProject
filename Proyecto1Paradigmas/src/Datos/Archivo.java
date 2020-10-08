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
import java.io.FileOutputStream;

/**
 *
 * @autor Joan Corea
 */
public class Archivo {

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
                informacion += linea;
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

    public void guardarArchivo(File archivo, String documento) {
        FileOutputStream salida;
        try {
            salida = new FileOutputStream(archivo);
            byte[] bytxt = documento.getBytes();
            salida.write(bytxt);
        } catch (Exception ee) {
        }
    }
    
}
