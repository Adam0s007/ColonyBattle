package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;


import javax.swing.*;
import java.awt.*;

public class PersonPanel extends JPanel {
    Person person;
    JLabel imageLabel;
    JLabel idLabel;
    JLabel lifeLabel;
    JLabel energyLabel;
    JLabel positionLabel;

    public PersonPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        this.add(imagePanel);

        idLabel = new JLabel();
        this.add(idLabel);

        lifeLabel = new JLabel();
        this.add(lifeLabel);

        energyLabel = new JLabel();
        this.add(energyLabel);

        positionLabel = new JLabel();
        this.add(positionLabel);

        updatePersonData();
    }

    public void updatePersonData() {
        if(person == null) {
            idLabel.setText("Click on a person to see details");
            return;
        }

        imageLabel.setIcon(person.getImage());
        idLabel.setText("ID: " + person.toString());
        if(person.getStatus().getHealth() <= 0) {
            lifeLabel.setText("Person is Dead.");
            positionLabel.setText("Died at: (" + person.getPosition());
            return;
        }
        int currentHealth = person.getStatus().getHealth();
        int maxHealth = person.getType().getHealth();
        lifeLabel.setText("Life: " + currentHealth + " / " + maxHealth);

        int currentEnergy = person.getStatus().getEnergy();
        int maxEnergy = person.getType().getEnergy();
        energyLabel.setText("Energy: " + currentEnergy + " / " + maxEnergy);

        Point2d position = person.getPosition();
        positionLabel.setText("Now at: (" + position.getX() + ", " + position.getY() + ")");
    }

    public void setPerson(Person person) {
        this.person = person;
        updatePersonData();
    }
    public Person getPerson() {
        return this.person;
    }
}
