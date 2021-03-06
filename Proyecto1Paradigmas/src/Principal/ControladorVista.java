/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import Vista.Vista;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Joan Corea
 */
public class ControladorVista implements ActionListener {

    Vista vista = new Vista();

    public ControladorVista() {
        
        vista.btnAbrir.addActionListener(this);
        
        vista.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAbrir) {
            ControladorInternal con = new ControladorInternal(true);
            vista.desktopPane.add(con.internal);
            vista.getContentPane().add(BorderLayout.CENTER, vista.desktopPane);
        }
    }

}
