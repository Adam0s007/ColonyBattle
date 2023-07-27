package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class MyFrame extends JFrame {

    public final GridPanel gridPanel;
    public final InfoPanel infoPanel;

    public MyFrame(int gridSize,List<Colony> allColonies) {
        // ... konstruktor ...
        this.setTitle("Colony Battle");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1300, 800);

        this.setLayout(new BorderLayout());

        PersonPanel personPanel = new PersonPanel();
        gridPanel = new GridPanel(gridSize,personPanel);
        this.add(gridPanel, BorderLayout.CENTER);
        this.infoPanel = new InfoPanel(personPanel,allColonies);
        infoPanel.setPreferredSize(new Dimension(500, 800));
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