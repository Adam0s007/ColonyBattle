package com.example.colonybattle.UI;

import javax.swing.*;
import javax.swing.border.Border;
import com.example.colonybattle.Vector2d;
import java.awt.*;


public class Cell extends JPanel {
    private Vector2d position;
    private String life = "";
    private String initial = "";
    private ImageIcon image = null;
    private JLabel lifeLabel;
    private JLabel initialLabel;
    private JLabel imageLabel;

    public Cell(int x, int y) {
        super(new GridLayout(2, 1));
        this.position = new Vector2d(x, y);
        this.setOpaque(true);
        initColor();

        // Tworzenie etykiety z napisem
        lifeLabel = new JLabel(life);
        lifeLabel.setHorizontalAlignment(JLabel.CENTER);
        lifeLabel.setVerticalAlignment(JLabel.CENTER);

        // Tworzenie etykiety z początkową wartością
        initialLabel = new JLabel(initial);
        initialLabel.setHorizontalAlignment(JLabel.CENTER);
        initialLabel.setVerticalAlignment(JLabel.CENTER);

        // Tworzenie etykiety z ikoną
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        // Dodawanie etykiet do panelu
        add(lifeLabel);
        add(initialLabel);
        add(imageLabel);
    }

    public Vector2d getPosition() {
        return position;
    }

    public void initColor() {
        this.setBackground(new Color(36, 255, 103));
    }

    public void updateLife(int life) {
        if (life == 0) {
            this.life = "";
        } else {
            this.life = Integer.toString(life);
        }
        lifeLabel.setText(this.life);
    }

    public void updateInitial(Character initial) {
        if (initial == ' ') {
            this.initial = "";
        } else {
            this.initial = initial.toString();
        }
        initialLabel.setText(this.initial);
    }

    public void setImageIcon(ImageIcon icon) {
        if (icon == null) {
            System.out.println("Image is null");
            image = null;
            imageLabel.setIcon(null);
            return;
        }
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        image = scaledIcon;
        imageLabel.setIcon(scaledIcon);
    }

    public void removeImageIcon() {
        image = null;
        imageLabel.setIcon(null);
    }
}

