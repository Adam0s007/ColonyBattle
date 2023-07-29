package com.example.colonybattle.ui.infopanel;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.ui.infopanel.colony.ColonyPanel;
import com.example.colonybattle.ui.infopanel.colony.SpawnPanel;
import com.example.colonybattle.ui.infopanel.person.PersonPanel;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class InfoPanel extends JPanel {
    public final PersonPanel personPanel;
    public final ColonyPanel colonyPanel;

    public final SpawnPanel spawnPanel;
    public InfoPanel(PersonPanel personPanel, List<Colony> allColonies) {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Stworzenie pierwszego panelu
        this.personPanel = personPanel;

        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 1.0 / 6; // Panel zajmuje 1/3 przestrzeni
        gbc.fill = GridBagConstraints.BOTH; // Panel rozciąga się w obu kierunkach (wertykalnie i horyzontalnie)
        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(personPanel, gbc);

        // Stworzenie drugiego panelu
        this.colonyPanel = new ColonyPanel(allColonies);
        colonyPanel.setBackground(Color.RED);
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 3.0 / 6; // Panel zajmuje 2/3 przestrzeni
        gbc.gridy = 1;
        this.add(colonyPanel, gbc);

        // Stworzenie panelu odliczajacego czas do nastepnego spawnu
        // Stworzenie panelu odliczajacego czas do nastepnego spawnu
        this.spawnPanel = new SpawnPanel();
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 1.0 / 6;
        gbc.gridy = 2;
        this.add(spawnPanel, gbc);
    }

    public PersonPanel getPersonPanel() {
        return personPanel;
    }

    public ColonyPanel getColonyPanel() {
        return colonyPanel;
    }
    public SpawnPanel getSpawnPanel() {return spawnPanel;}
}

