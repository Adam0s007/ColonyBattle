package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MyFrame extends JFrame {

    public final GridPanel gridPanel;
    public final InfoPanel infoPanel;

    public MyFrame(int gridSize) {
        // ... konstruktor ...
        this.setTitle("Colony Battle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200, 800);

        this.setLayout(new BorderLayout());

        PersonPanel personPanel = new PersonPanel();
        gridPanel = new GridPanel(gridSize,personPanel);
        this.add(gridPanel, BorderLayout.CENTER);
        this.infoPanel = new InfoPanel(personPanel);
        infoPanel.setPreferredSize(new Dimension(400, 800));
        this.add(infoPanel, BorderLayout.EAST);
        this.setVisible(true);
    }

    // Metoda do pobierania komórki na danej pozycji
   public GridPanel getGridPanel(){
        return this.gridPanel;
   }

    public InfoPanel getInfoPanel(){
          return this.infoPanel;
    }
}