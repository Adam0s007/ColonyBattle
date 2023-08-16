package com.example.colonybattle.ui.frame;

import com.example.colonybattle.colony.Colony;

import com.example.colonybattle.ui.grid.GridPanel;
import com.example.colonybattle.ui.infopanel.InfoPanel;
import com.example.colonybattle.ui.infopanel.person.PersonPanel;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Getter
public class MyFrame extends JFrame {

    private final GridPanel gridPanel;
    private final InfoPanel infoPanel;

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
        this.infoPanel = new InfoPanel(personPanel,allColonies,gridPanel);
        infoPanel.setPreferredSize(new Dimension(500, 800));
        this.add(infoPanel, BorderLayout.EAST);
        this.setVisible(true);


    }

    // Metoda do pobierania komórki na danej pozycji

}