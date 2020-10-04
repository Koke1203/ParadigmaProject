/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author groya
 */
public class JDPEditorFormulas extends JFrame {

    private CustomDesktopPane desktopPane;
    private Container contentPane;

    public JDPEditorFormulas() {
        desktopPane = new CustomDesktopPane();
        contentPane = getContentPane();

        contentPane.add(desktopPane, BorderLayout.CENTER);
        desktopPane.display(desktopPane);

        setTitle("Editor de Formulas");
        setSize(1200, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
