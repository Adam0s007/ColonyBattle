package com.example.colonybattle.UI;

import javax.swing.*;
import javax.swing.border.Border;
import com.example.colonybattle.Vector2d;
import java.awt.*;

public class Cell extends JPanel {
    private Vector2d position;

    public Cell(int x, int y) {
        super();
        this.position = new Vector2d(x, y);
        this.setOpaque(true); // Musimy to ustawić, aby tło JLabel było widoczne
        initColor();  // Domyślny kolor

        // Tworzenie obramowania
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        this.setBorder(border);
    }

    public Vector2d getPosition() {
        return position;
    }

    public void initColor(){
        this.setBackground(new Color(36,255,103));
    }
}