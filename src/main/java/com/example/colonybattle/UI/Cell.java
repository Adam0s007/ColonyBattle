package com.example.colonybattle.UI;

import javax.swing.*;
import javax.swing.border.Border;

import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.Colors.ConsoleColor;
import com.example.colonybattle.Vector2d;
import java.awt.*;


public class Cell extends JLabel {
    private Vector2d position;
    private String life = "";

    private Border border;
    private ImageIcon image= null;

    public final Color INITIAL_BACKGROUND = new Color(15, 23, 51);

    public Cell(int x, int y) {
        this.position = new Vector2d(x, y);
        this.setOpaque(true);
        initColor();
        this.setText(life);
        // Tworzenie etykiety z napisem
        //lifeLabel = new JLabel(life);


        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        //font names:

        this.setFont(new Font("Arial", Font.BOLD, 16));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setIconTextGap(-20);
        // Tworzenie etykiety z początkową wartością//przezroczysty kolor tla:
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        // Tworzenie etykiety z ikoną


    }
    public Vector2d getPosition() {
        return position;
    }

    public void initColor() {

       // this.setBackground(new Color(36, 255, 103));
        updateBackground(this.position);
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        this.setBorder(border);
    }

    public void updateLife(int life) {
        if (life == 0) {
            this.life = "";
        } else {
            this.life = Integer.toString(life);
        }
        this.setText(this.life);
    }


    public void updateInitial(Character initial) {
        //TODO
    }

    public void setImageIcon(ImageIcon icon) {
        if (icon == null) {
            System.out.println("Image is null");
            this.image = null;
            this.setIcon(image);
            return;
        }
        Image originalImage = icon.getImage();
        Image scaledImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        this.image = scaledIcon;
        this.setIcon(image);
    }

    public void removeImageIcon() {
        image = null;
        this.setIcon(image);
    }

    public void updateColors(Color color) {
        border = BorderFactory.createLineBorder(color, 3);
        this.setBorder(border);
        this.setForeground(Color.WHITE);
        updateBackground(this.position);
    }

    public void updateBackground(Vector2d position) {
        Color color = position.getColonyColor();
        if(color == null) color = INITIAL_BACKGROUND;
        this.setBackground(color.darker());
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

}

