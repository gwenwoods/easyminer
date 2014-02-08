package org.easyminer.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EasyMinerUI extends JApplet {

    JPanel topPanel = new JPanel();
    JInternalFrame minerFrame = new JInternalFrame();

    @Override
    public void init() {
        super.init();
        setSize(1024, 512);
        getContentPane().setSize(1024, 512);
        topPanel.setBackground(Color.gray);
        topPanel.setSize(400, 200);
        
        minerFrame.setBackground(Color.blue);
        minerFrame.setSize(960, 480);
    //    minerFrame.show();
        getContentPane().add(minerFrame,BorderLayout.NORTH);
      //  getContentPane().add(topPanel, BorderLayout.WEST);
        
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.println("createGUI didn't successfully complete");
        }
    }

    private void createGUI() {
        JLabel label = new JLabel("You are successfully running a Swing applet!");
        label.setHorizontalAlignment(JLabel.CENTER);
        // label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        // getContentPane().add(label, BorderLayout.CENTER);
    }

    @Override
    public void paint(Graphics g) {

        // Draw a rectangle width=250, height=100

        g.drawRect(40, 20, 800, 400);

        // Set the color to blue
        g.setColor(Color.blue);

        // Write the message to the web page
        g.drawString("Look at me, I'm a Java Applet!", 10, 50);
    }
}
