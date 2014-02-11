package org.ninjacat.easyminer.ui.transformations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public final class TransformationsFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    JButton linkFileButton = new JButton("Link Data File");
    JButton chooseWorkingDirButton = new JButton("Choose Working Dir");
    JButton createTransformedField = new JButton("Transform Fields");

    public TransformationsFrame() {

        // -----------------------------------
        // Transformation Frame parameters
        setTitle("Ninja Cat - EasyMiner - Transformations");
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // -----------------------------------
        // Link file button
        JButton startButton = new JButton("Link Data File");
        add(startButton);// Add the button to the JFrame.
        startButton.addActionListener(this);// Reads the action.
        startButton.setSize(120, 40);
        startButton.setLocation(40, 20);
    }

    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource() == linkFileButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                System.out.println(selectedFile.getAbsolutePath());
            }
        } else if (evt.getSource() == chooseWorkingDirButton) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                System.out.println(selectedFile.getAbsolutePath());
            }
        }
    }

}
