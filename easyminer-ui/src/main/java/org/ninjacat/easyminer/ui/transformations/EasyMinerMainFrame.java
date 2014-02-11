package org.ninjacat.easyminer.ui.transformations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public final class EasyMinerMainFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    Map<String, String> paraMap = new HashMap<String, String>();

    JSplitPane mainSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    JPanel topPanel = new JPanel(new GridLayout(1, 4));
    JPanel xformPanel = new TransformationsPanel();
    JPanel dummyPanel = new JPanel();

    // ----------------------
    // buttons in topPanel
    JButton chooseWorkingDirButton = new JButton("Choose Working Dir");
    JButton linkFileButton = new JButton("Link Data File");
    JButton toXformButton = new JButton("Transformations");
    JButton helloButton = new JButton("Hello");

    public EasyMinerMainFrame() {

        setLayout(new BorderLayout());
        add(mainSplit, BorderLayout.CENTER);

        // -----------------------------------
        // Transformation Frame parameters
        setTitle("Ninja Cat - EasyMiner");
        setSize(1024, 768);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // -------------------------------------
        // main split pane
        topPanel.setSize(1024, 128);
        topPanel.setBackground(Color.DARK_GRAY);
        topPanel.add(chooseWorkingDirButton);
        topPanel.add(linkFileButton);
        topPanel.add(toXformButton);
        topPanel.add(helloButton);

        mainSplit.setTopComponent(topPanel);
        mainSplit.setBottomComponent(dummyPanel);
        mainSplit.setEnabled(false);

        // -----------------------------------
        // Choose working dir button
        chooseWorkingDirButton.addActionListener(this);// Reads the action.
        // chooseWorkingDirButton.setSize(160, 40);
        // chooseWorkingDirButton.setLocation(40, 20);

        // -----------------------------------
        // Link file button
        linkFileButton.addActionListener(this);// Reads the action.
        // linkFileButton.setSize(120, 40);
        // linkFileButton.setLocation(200, 20);

        // -----------------------------------
        // to xform button
        toXformButton.addActionListener(this);// Reads the action.
        helloButton.addActionListener(this);// Reads the action.

    }

    public void actionPerformed(ActionEvent evt) {

        if (evt.getSource() == chooseWorkingDirButton) {
            mainSplit.setBottomComponent(dummyPanel);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                System.out.println(selectedFile.getAbsolutePath());
            }

        } else if (evt.getSource() == linkFileButton) {
            mainSplit.setBottomComponent(dummyPanel);

            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println(selectedFile.getName());
                System.out.println(selectedFile.getAbsolutePath());
            }

        } else if (evt.getSource() == toXformButton) {
            mainSplit.setBottomComponent(xformPanel);

        }
    }
}
