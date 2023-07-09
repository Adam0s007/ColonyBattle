package com.example.colonybattle.UI;

import com.example.colonybattle.Vector2d;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    private Cell[][] grid;

    public MyFrame(int gridSize) {
        // ... konstruktor ...
        this.setTitle("Colony Battle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(600, 600);

        this.setLayout(new GridLayout(gridSize, gridSize));
        grid = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = new Cell(i, j);
                this.add(grid[i][j]);
            }
        }
        this.setVisible(true);
    }

    // Metoda do pobierania komórki na danej pozycji
    public Cell getCellAtPosition(Vector2d position) {
        return grid[position.getX()][position.getY()];
    }
    // Metoda do ustawiania koloru komórki na podstawie pozycji
    public void setColorAtPosition(Vector2d position,Color color) {
        grid[position.getX()][position.getY()].setBackground(color);
    }

    public void setInitColor(Vector2d position){
        grid[position.getX()][position.getY()].initColor();
    }

}