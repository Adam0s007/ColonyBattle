package com.example.colonybattle.ui;

import javax.swing.*;
import java.awt.*;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public final PersonPanel personPanel;
    public final ColonyPanel colonyPanel;
    public InfoPanel(PersonPanel personPanel) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Stworzenie pierwszego panelu
        this.personPanel = personPanel;

        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 1.0 / 3; // Panel zajmuje 1/3 przestrzeni
        gbc.fill = GridBagConstraints.BOTH; // Panel rozciąga się w obu kierunkach (wertykalnie i horyzontalnie)
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(personPanel, gbc);

        // Stworzenie drugiego panelu
        this.colonyPanel = new ColonyPanel();
        colonyPanel.setBackground(Color.RED);
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 2.0 / 3; // Panel zajmuje 2/3 przestrzeni
        gbc.gridy = 1;
        this.add(colonyPanel, gbc);
    }

    public PersonPanel getPersonPanel() {
        return personPanel;
    }

    public ColonyPanel getColonyPanel() {
        return colonyPanel;
    }

}

