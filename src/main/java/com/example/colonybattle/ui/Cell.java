package com.example.colonybattle.ui;

import javax.swing.*;
import javax.swing.border.Border;

import com.example.colonybattle.board.position.Point2d;
import java.awt.*;


public class Cell extends JLabel {
    private Point2d position;
    private String life = "";

    private Border border;
    private ImageIcon image= null;
    private int xHealthBar = 0; // Pozycja X prostokąta
    private int yHealthBar = 0; // Pozycja Y prostokąta
    private int widthHealthBar = 0; // Szerokość prostokąta
    private final int heightHealthBar = 4; // Wysokość prostokąta

    private final int MAX_HEALTH = 20; // Maksymalna wartość życia
    private Color colorHealthBar = new Color(255, 194, 189); // Kolor prostokąta

    public final Color INITIAL_BACKGROUND = new Color(15, 23, 51);

    public Cell(int x, int y) {
        this.position = new Point2d(x, y);
        this.setOpaque(true);
        initColor();
        this.setText(life);
        // Tworzenie etykiety z napisem
        //lifeLabel = new JLabel(life);


        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        //font names:

        this.setFont(new Font("Arial", Font.BOLD, 13));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setIconTextGap(-20);
        // Tworzenie etykiety z początkową wartością//przezroczysty kolor tla:
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        // Tworzenie etykiety z ikoną
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(colorHealthBar);
        g.fillRect(xHealthBar, yHealthBar, widthHealthBar, heightHealthBar);
    }
    // Metoda do aktualizacji pozycji i rozmiaru prostokąta
    public void setHealthBar(int x, int y, int width) {
        this.xHealthBar = x;
        this.yHealthBar = y;
        this.widthHealthBar = width;
    }

    public void updateHealthBar() {
        if(this.position.getPerson() != null){
            int labelWidth = this.getWidth();
            double healthPerPixel = MAX_HEALTH / (double) labelWidth;
            int currentHealth = this.position.getPerson().getStatus().getHealth();
            int healthBarWidth = (int) (currentHealth / healthPerPixel);
            setHealthBar(3, 3, healthBarWidth);
        }
        else setHealthBar(0,0, 0);
    }

    // Metoda do aktualizacji koloru prostokąta
    public void setRectangleColor(Color color) {
        this.colorHealthBar = color;
    }



    public Point2d getPosition() {
        return position;
    }

    public void initColor() {

       // this.setBackground(new Color(36, 255, 103));
        updateBackground(this.position);
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        this.setBorder(border);
    }

    public void updateLife(int life) {
        updateHealthBar();
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

    public void updateBackground(Point2d position) {
        Color color = position.getColonyColor();
        if(color == null) color = INITIAL_BACKGROUND;
        this.setBackground(color.darker());
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }

}

