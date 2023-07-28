package com.example.colonybattle.ui;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ColonyPanel extends JPanel {

    public final List<Colony> allColonies;
    private final Map<Colony, JPanel> colonyPanels = new ConcurrentHashMap<>();
    private final Map<Person, JLabel> personLabels = new ConcurrentHashMap<>();
    private final Font font = new Font("Arial", Font.BOLD, 10); // Definiujemy czcionkę tylko raz

    public ColonyPanel(List<Colony> allColonies){
        this.allColonies = allColonies;
        this.setLayout(new GridLayout(1, allColonies.size()));

    }

    public void init(){
        for (Colony colony : allColonies) {
            addColonyPanel(colony);
        }
    }

    public void updateColonies(){
        for (Colony colony : allColonies) {
            updateColonyPanel(colony);
        }
    }

    private void addColonyPanel(Colony colony){
        JPanel colonyPanel = new JPanel();
        colonyPanel.setLayout(new BoxLayout(colonyPanel, BoxLayout.Y_AXIS));

        JLabel colonyLabel = new JLabel(colony.getType() + ": " + colony.getPeopleCount());
        colonyLabel.setFont(font);

        JLabel pointsLabel = new JLabel("Points: " + colony.getPoints());
        pointsLabel.setFont(font);

        colonyPanel.add(colonyLabel);
        colonyPanel.add(pointsLabel);

        for (Person person : colony.getPeople()) {
            JLabel personLabel = createPersonLabel(person, font);
            colonyPanel.add(personLabel);
            personLabels.put(person, personLabel);
        }

        this.add(colonyPanel);
        colonyPanels.put(colony, colonyPanel);
    }

    public void updateColonyPanel(Colony colony){
        JPanel colonyPanel = colonyPanels.get(colony);
        if(colonyPanel == null)return; // Check if colonyPanel is null, not colony
        ((JLabel) colonyPanel.getComponent(0)).setText(colony.getType() + ": " + colony.getPeopleCount());
        ((JLabel) colonyPanel.getComponent(1)).setText("Points: " + colony.getPoints());
    }

    private void removeColonyPanel(Colony colony){
        JPanel colonyPanel = colonyPanels.get(colony);
        if(colonyPanel != null) {
            this.remove(colonyPanel);
            colonyPanels.remove(colony);
        }
    }

    private JLabel createPersonLabel(Person person, Font font){
        JLabel personLabel = new JLabel("" + person.getPosition());
        personLabel.setFont(font);
        JPanel colonyPanel = colonyPanels.get(person.getColony());
        if(colonyPanel != null){
            colonyPanel.add(personLabel);
            personLabels.put(person, personLabel);
        }
        return personLabel;
    }

    public void removePersonLabel(Person person){
        JLabel personLabel = personLabels.get(person);
        if(personLabel != null) {
            personLabels.remove(person);
            JPanel colonyPanel = colonyPanels.get(person.getColony());
            if(colonyPanel != null) {
                colonyPanel.remove(personLabel);
                colonyPanel.revalidate();  // Re-check the layout
                colonyPanel.repaint();  // Redraw the panel
                if(colonyPanel.getComponentCount() == 2)
                    removeColonyPanel(person.getColony());
            }
        }
    }

    public void update(Person person){
        if(person.isDead()) {
            removePersonLabel(person);
        }else{
            JLabel personLabel = personLabels.get(person);
            if(personLabel != null)
                personLabel.setText("" + person.getPosition());
            else {
                createPersonLabel(person, font); // Don't forget to add this newly created personLabel into your colonyPanel
            }
        }
        JPanel colonyPanel = colonyPanels.get(person.getColony());
        if(colonyPanel != null)
            updateColonyPanel(person.getColony());
    }



}


