/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Principal.ControladorInternal;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

/**
 *
 * @author groya
 */
public class Vista extends JFrame {

    //Menú de opciones
    private JMenuBar menuPrincipal;
    private JMenu menuArchivo;
    private JMenu menuEdicion;
    private JMenu menuVista;
    private JMenu menuHerramientas;
    private JMenu menuVentana;
    private JMenu menuAyuda;

    //Herramientas
    public JToolBar toolBar;
    public JButton btnNuevo;
    public JButton btnAbrir;
    public JButton btnGuardar;
    public JButton btnImprimir;
    public JButton btnBuscar;

    //JDesktopPane
    public JDesktopPane desktopPane;

    public Vista() {
        super("Editor de fórmulas");
        configurarVista();
    }

    private void configurarVista() {
        ajustarComponentes(getContentPane());

        setIconImage(getIconImage());
        setResizable(true);
        setMinimumSize(new Dimension(1200, 900));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
    }

    private void ajustarComponentes(Container container) {
        container.setLayout(new BorderLayout());

        ajustarMenus();

        ajustarBarraHerramientas();
        container.add(BorderLayout.NORTH, toolBar);

        desktopPane = new JDesktopPane();

        desktopPane.add(new ControladorInternal(false).internal);
        desktopPane.add(new ControladorInternal(false).internal);
        desktopPane.add(new ControladorInternal(false).internal);
        container.add(BorderLayout.CENTER, desktopPane);
    }

    private void ajustarBarraHerramientas() {
        toolBar = new JToolBar();
        btnNuevo = new JButton();
        btnAbrir = new JButton();
        btnGuardar = new JButton();
        btnImprimir = new JButton();
        btnBuscar = new JButton();
        try {
            btnNuevo.setIcon(new ImageIcon(getClass().getResource("../Recursos/nuevo.png")));
            btnAbrir.setIcon(new ImageIcon(getClass().getResource("../Recursos/abrir.png")));
            btnGuardar.setIcon(new ImageIcon(getClass().getResource("../Recursos/guardar.png")));
            btnImprimir.setIcon(new ImageIcon(getClass().getResource("../Recursos/imprimir.png")));
            btnBuscar.setIcon(new ImageIcon(getClass().getResource("../Recursos/buscar.png")));
        } catch (Exception ex) {

        }

        toolBar.add(btnNuevo);
        toolBar.add(btnAbrir);
        toolBar.add(btnGuardar);
        toolBar.add(btnImprimir);
        toolBar.add(btnBuscar);
    }

    private void ajustarMenus() {
        menuPrincipal = new JMenuBar();
        menuPrincipal.add(menuArchivo = new JMenu("Archivo"));
        menuPrincipal.add(menuEdicion = new JMenu("Edición"));
        menuPrincipal.add(menuVista = new JMenu("Vista"));
        menuPrincipal.add(menuHerramientas = new JMenu("Herramientas"));
        menuPrincipal.add(menuVentana = new JMenu("Ventana"));
        menuPrincipal.add(menuAyuda = new JMenu("Ayuda"));

        setJMenuBar(menuPrincipal);
    }

    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Recursos/icon.png"));
        return retValue;
    }

}
