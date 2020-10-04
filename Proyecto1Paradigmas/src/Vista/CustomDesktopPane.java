/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.awt.Container;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

/**
 *
 * @author groya
 */
public class CustomDesktopPane extends JDesktopPane {

    int numFrames = 3, x = 30, y = 30;

    public void display(CustomDesktopPane dp) {
        for (int i = 0; i < numFrames; ++i) {
            JInternalFrame jiframe = new JInternalFrame("Internal Frame" + i, true, true);

            jiframe.setBounds(x, y, 250, 85);
            Container c1 = jiframe.getContentPane();
            c1.add(new JLabel("I love my country"));
            dp.add(jiframe);
            jiframe.setVisible(true);
            y += 85;
        }

    }
}
