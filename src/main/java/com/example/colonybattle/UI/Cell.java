package com.example.colonybattle.UI;

import javax.swing.*;
import javax.swing.border.Border;
import com.example.colonybattle.Vector2d;
import java.awt.*;

public class Cell extends JPanel {
    private Vector2d position;
    private JLabel lifeLabel;
    private JLabel initialLabel;

    private JLabel imageLabel;

    public Cell(int x, int y) {
        super();
        this.position = new Vector2d(x, y);
        this.setOpaque(true);
        initColor();
        this.setLayout(new GridBagLayout()); // Ustawiamy layout na GridBagLayout

        this.initialLabel = new JLabel("", SwingConstants.CENTER);
        this.lifeLabel = new JLabel("", SwingConstants.CENTER);

        initialLabel.setFont(new Font(initialLabel.getFont().getName(), Font.BOLD, 14));
        lifeLabel.setFont(new Font(lifeLabel.getFont().getName(), Font.BOLD, 14));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.bottom = 2;
        this.add(initialLabel, constraints);

        constraints.gridy = 1;
        this.add(lifeLabel, constraints);

        Border border = BorderFactory.createLineBorder(new Color(0, 184, 70));
        this.setBorder(border);

        imageLabel = new JLabel();
        //GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets.bottom = 2;
        this.add(imageLabel, constraints);
    }

    public Vector2d getPosition() {
        return position;
    }

    public void initColor() {
        this.setBackground(new Color(36, 255, 103));
    }

    public void updateLife(int life) {
        lifeLabel.setText(Integer.toString(life));
        if (life == 0) {
            lifeLabel.setText("");
        }
    }

    public void updateInitial(Character initial) {
        initialLabel.setText(initial.toString());
        if (initial == ' ') {
            initialLabel.setText("");
        }
    }

    public void setImageIcon(ImageIcon icon) {
        imageLabel.setIcon(icon);
    }

    public void removeImageIcon() {
        imageLabel.setIcon(null);
    }
}
