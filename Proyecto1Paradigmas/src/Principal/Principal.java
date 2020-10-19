/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Logica.Simplificacion;
import Vista.Vista;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author groya
 */
public class Principal {

    public static void main(String[] args) {
        /*try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println(e);
        }
        new ControladorVista();*/
        
        Simplificacion sim = new Simplificacion();
        
        System.out.println(sim.aplicaDistributiva("(p∨q)∧(p∨l)"));
        //¬(abbbbba¬c
    }
        
}
