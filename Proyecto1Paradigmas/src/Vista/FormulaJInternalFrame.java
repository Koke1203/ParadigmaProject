/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javafx.scene.layout.Pane;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author groya
 */
public class FormulaJInternalFrame extends JInternalFrame {

    private static int contFramesAbiertos = 1, x = 10, y = 10;

    public JTextField txtExpresion;
    public JButton btnVerificar;
    public JTextArea txtArea;
    public JTable tbVerdad;
    public DefaultTableModel modelo;
    public JScrollPane jScrollPane1;

    public FormulaJInternalFrame() {
        //Resizable, closable, maximizable, iconifiable
        super("Formula " + (contFramesAbiertos++), true, true, true, true);

        ajustarComponentes(getContentPane());

        setFrameIcon(new ImageIcon(getClass().getResource("../Recursos/icon.png")));
        //Eje x, eje y, ancho, altura
        setBounds(x, y, 670, 470);
        x += x + 5;
        y += y + 5;
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
        c.weightx = 0.25;
        c.gridx = 0;
        c.gridy = 2;

        JPanel paneCanonica = new JPanel(new BorderLayout());
        paneCanonica.setBorder(BorderFactory.createTitledBorder("Forma Canónica"));
        
        txtArea = new JTextArea(5, 30);
        txtArea.setEditable(false);
        paneCanonica.add(BorderLayout.CENTER, txtArea);
        
        pane.add(paneCanonica, c);
        
        
        jScrollPane1 = new JScrollPane();
        tbVerdad = new javax.swing.JTable();
        
        jScrollPane1.setViewportView(tbVerdad);
        pane.add(jScrollPane1,c);

        container.add(pane);
    }
    
}
