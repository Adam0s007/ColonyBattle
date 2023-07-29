package com.example.colonybattle.ui.infopanel;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.ui.infopanel.colony.ColonyPanel;
import com.example.colonybattle.ui.infopanel.colony.SpawnPanel;
import com.example.colonybattle.ui.infopanel.person.PersonPanel;
import com.example.colonybattle.ui.infopanel.colony.ranking.RankingPanel;
import javax.swing.*;
import java.awt.*;

import java.util.List;

public class InfoPanel extends JPanel {
    public final PersonPanel personPanel;
    public final ColonyPanel colonyPanel;

    public final SpawnPanel spawnPanel;

    // Nowe panele
    public final RankingPanel rankingPanel;

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
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 2.0 / 6; // Panel zajmuje 2/3 przestrzeni
        gbc.gridy = 1;
        this.add(colonyPanel, gbc);


        // Stworzenie panelu odliczajacego czas do nastepnego spawnu
        this.spawnPanel = new SpawnPanel(allColonies);
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 1.0 / 6;
        gbc.gridy = 2;
        this.add(spawnPanel, gbc);


        // Stworzenie panelu z rankingiem
        this.rankingPanel = new RankingPanel(allColonies);
        gbc.weightx = 1.0; // Panel zajmuje całą przestrzeń w poziomie
        gbc.weighty = 1.0 / 6;
        gbc.gridy = 3;
        this.add(rankingPanel, gbc);

        // Stworzenie panelu z rankingiem





    }

    public PersonPanel getPersonPanel() {
        return personPanel;
    }

    public ColonyPanel getColonyPanel() {
        return colonyPanel;
    }
    public SpawnPanel getSpawnPanel() {return spawnPanel;}
    public RankingPanel getRankingPanel() {return rankingPanel;}
}

