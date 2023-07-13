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
        this.setSize(800, 800);

        this.setLayout(new GridLayout(gridSize, gridSize,0,0));
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

    // Metoda do ustawiania liczby żyć na danej pozycji
    public void setLifeAtPosition(Vector2d position, int life) {
        grid[position.getX()][position.getY()].updateLife(life);
    }

    public void setInitial(Vector2d position,Character initial){
        grid[position.getX()][position.getY()].updateInitial(initial);
    }

    // Metoda do ustawiania obrazka na danej pozycji
    public void setImageAtPosition(Vector2d position, ImageIcon icon) {
        grid[position.getX()][position.getY()].setImageIcon(icon);
    }

    // Metoda do usuwania obrazka z danej pozycji
    public void removeImageAtPosition(Vector2d position) {
        grid[position.getX()][position.getY()].removeImageIcon();
    }

}