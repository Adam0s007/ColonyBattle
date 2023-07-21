package com.example.colonybattle.ui;

import javax.swing.*;
import javax.swing.border.Border;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.utils.InitialConventer;

import java.awt.*;


public class Cell extends JLabel {
    private Point2d position;
    private String life = "";

    private Border border;
    private ImageIcon image= null;
    private final int xHealthBar = 3; // Pozycja X prostokąta
    private final int yHealthBar = 3; // Pozycja Y prostokąta
    private int widthHealthBar = 0; // Szerokość prostokąta
    private final int heightHealthBar = 4; // Wysokość prostokąta

    private final int MAX_HEALTH = 20; // Maksymalna wartość życia
    private Color colorHealthBar = new Color(255, 194, 189); // Kolor prostokąta

    private final int xEnergyBar = 3; // Pozycja X prostokąta
    private final int yEnergyBar = 6;
    // Szerokość prostokąta dla paska energii
    private int widthEnergyBar = 0;
    // Maksymalna wartość energii
    private final int MAX_ENERGY = 20;
    // Kolor prostokąta dla paska energii
    private Color colorEnergyBar = new Color(255, 255, 189);



    public final Color INITIAL_BACKGROUND = new Color(15, 23, 51);

    public Cell(int x, int y) {
        this.setLayout(new BorderLayout()); // Wyłącza zarządcę układu

        this.position = new Point2d(x, y);
        this.setOpaque(true);
        initColor();
        this.setText(life);
        // Tworzenie etykiety z napisem
        //lifeLabel = new JLabel(life);


        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.BOTTOM);
        //font names:

        this.setFont(new Font("Sans Serif", Font.BOLD, 7));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setIconTextGap(-15);
        //this.setBounds(0, this.getHeight() - 20, this.getWidth(), 20);
        // Tworzenie etykiety z początkową wartością//przezroczysty kolor tla:
        border = BorderFactory.createLineBorder(new Color(0,0,0,0), 2);
        // Tworzenie etykiety z ikoną
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(colorHealthBar);
        g.fillRect(xHealthBar, yHealthBar, widthHealthBar, heightHealthBar);

        g.setColor(colorEnergyBar);
        g.fillRect(xHealthBar, yEnergyBar, widthEnergyBar, heightHealthBar);
    }
    // Metoda do aktualizacji pozycji i rozmiaru prostokąta
    public void setHealthBar(int width) {
        this.widthHealthBar = width;
    }

    public void updateHealthBar() {
        if(this.position.getPerson() != null){
            int labelWidth = this.getWidth();
            double healthPerPixel = MAX_HEALTH / (double) labelWidth;
            int currentHealth = this.position.getPerson().getStatus().getHealth();
            int healthBarWidth = (int) (currentHealth / healthPerPixel);
            setHealthBar(healthBarWidth);
        }
        else setHealthBar(0);
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
    public void updateEnergy(int energy){
        updateEnergyBar();
    }

    // Metoda do aktualizacji pozycji i rozmiaru paska energii
    public void setEnergyBar(int width) {
        this.widthEnergyBar = width;
    }

    public void updateEnergyBar() {
        if(this.position.getPerson() != null){
            int labelWidth = this.getWidth();
            double energyPerPixel = MAX_ENERGY / (double) labelWidth;
            int currentEnergy = this.position.getPerson().getStatus().getEnergy();
            int energyBarWidth = (int) (currentEnergy / energyPerPixel);
            setEnergyBar(energyBarWidth);
        }
        else setEnergyBar(0);
    }


    public void updateInitial(Character initial) {
        if(this.position.getPerson() == null){
            this.setText("");
            return;
        }
        String convertedInitial = InitialConventer.getInstance().convertInitial(initial);
        if(convertedInitial != null) {
            this.setText(convertedInitial);
        }
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

