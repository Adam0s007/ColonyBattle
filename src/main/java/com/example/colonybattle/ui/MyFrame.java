package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyFrame extends JFrame {
    private Cell[][] grid;
    public final GridPanel gridPanel;

    public MyFrame(int gridSize) {
        // ... konstruktor ...
        this.setTitle("Colony Battle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);

        this.setLayout(new BorderLayout());
        gridPanel = new GridPanel(gridSize);
        this.add(gridPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }

    // Metoda do pobierania komórki na danej pozycji
   public GridPanel getGridPanel(){
        return this.gridPanel;
   }
}