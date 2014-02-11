package org.ninjacat.easyminer.ui.transformations;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public final class TransformationsPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    public TransformationsPanel() {

        // -----------------------------------
        // Transformation Panel parameters

        // super(gridLayout);
        setSize(1024, 640);
        setBackground(Color.cyan);

        TitledBorder titleBorder = BorderFactory.createTitledBorder("Transformations");
        Font titleFont = new Font("Verdana", 0, 16);
        titleBorder.setTitleFont(titleFont);
        setBorder(titleBorder);

        setVisible(true);

        // ------------------------------------------
        JButton changeColorButton = new JButton("Change Color");
        changeColorButton.addActionListener(this);
        changeColorButton.setSize(120, 40);
        changeColorButton.setLocation(400, 20);

        add(changeColorButton);
    }

    public void actionPerformed(ActionEvent evt) {

        if (Color.GREEN == getBackground()) {
            setBackground(Color.CYAN);
        } else {
            setBackground(Color.GREEN);
        }
    }
}
