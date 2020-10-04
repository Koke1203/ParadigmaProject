/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javafx.scene.layout.Pane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author groya
 */
public class FormulaJInternalFrame extends JInternalFrame {

    private static int contFramesAbiertos = 1, x = 30, y = 30;

    private JTextField txtExpresion;
    private JButton btnVerificar;
    private JTextArea txtArea;

    public FormulaJInternalFrame() {
        //Resizable, closable, maximizable, iconifiable
        super("Formula " + (contFramesAbiertos++), true, true, true, true);

        ajustarComponentes(getContentPane());

        setFrameIcon(new ImageIcon(getClass().getResource("../Recursos/icon.png")));
        //Eje x, eje y, ancho, altura
        setBounds(x, y, 400, 300);
        x *= contFramesAbiertos;
        y *= contFramesAbiertos;
        setVisible(true);
    }

    private void ajustarComponentes(Container container) {
        JPanel pane = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(txtExpresion = new JTextField("", 50), c);
        c.weightx = 0.25;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(btnVerificar = new JButton("Verificar"), c);

        c.anchor = GridBagConstraints.PAGE_END;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;

        JPanel paneCanonica = new JPanel(new BorderLayout());
        paneCanonica.setBorder(BorderFactory.createTitledBorder("Forma Canónica"));

        txtArea = new JTextArea(5, 30);
        txtArea.setEditable(false);
        paneCanonica.add(BorderLayout.CENTER, txtArea);

        pane.add(paneCanonica, c);

        container.add(pane);

    }

}
