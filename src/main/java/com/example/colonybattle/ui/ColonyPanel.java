package com.example.colonybattle.ui;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ColonyPanel extends JPanel {

    public final List<Colony> allColonies;

    public ColonyPanel(List<Colony> allColonies){
        this.allColonies = allColonies;
        // Ustawienie układu na GridLayout z jednym wierszem i tyle kolumn ile jest kolonii
        this.setLayout(new GridLayout(1, allColonies.size()));
    }

    public void updateColonies(){
        this.removeAll();
        allColonies.forEach(this::addColonyPanel);
        this.revalidate();
        this.repaint();
    }

    private void addColonyPanel(Colony colony){
        JPanel colonyPanel = new JPanel();
        colonyPanel.setLayout(new BoxLayout(colonyPanel, BoxLayout.Y_AXIS));

        Font font = new Font("Arial", Font.BOLD, 10); // Ustalamy rodzaj czcionki

        JLabel colonyLabel = new JLabel(colony.getType() + ": " + colony.getPeopleCount());
        colonyLabel.setFont(font); // Ustawiamy czcionkę dla etykiety

        JLabel pointsLabel = new JLabel("Points: " + colony.getPoints());
        pointsLabel.setFont(font); // Ustawiamy czcionkę dla etykiety

        colonyPanel.add(colonyLabel);
        colonyPanel.add(pointsLabel);

        colony.getPeople().forEach(person -> colonyPanel.add(createPersonLabel(person, font))); // Przekazujemy czcionkę jako argument

        this.add(colonyPanel);
    }

    private JLabel createPersonLabel(Person person, Font font){ // Dodajemy argument dla czcionki
        JLabel personLabel = new JLabel("" + person.getPosition());
        personLabel.setFont(font); // Ustawiamy czcionkę dla etykiety
        return personLabel;
    }
}


