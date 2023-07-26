package com.example.colonybattle.ui;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonPanel extends JPanel {
    private Person person;
    private JLabel imageLabel;
    private JLabel idLabel;
    private JLabel lifeLabel;
    private JLabel energyLabel;
    private JLabel positionLabel;
    private JLabel colonyLabel;
    private JLabel killsLabel;

    public PersonPanel() {
        setPanelLayout();
        addImagePanel();
        idLabel = addLabel();
        lifeLabel = addLabel();
        energyLabel = addLabel();
        positionLabel = addLabel();
        colonyLabel = addLabel();
        killsLabel = addLabel();
        updatePersonData();
    }

    private void setPanelLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.WHITE);
    }

    private void addImagePanel() {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        this.add(imagePanel);
    }

    private JLabel addLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.BLACK);
        this.add(label);
        return label;
    }

    public void updatePersonData() {
        if(person == null) {
            idLabel.setText("Click on a person to see details");
            return;
        }

        imageLabel.setIcon(scaleImageIcon(person.getImage(), 100, 100));
        idLabel.setText("ID: " + person.toString());

        if(person.getStatus().getHealth() <= 0) {
            updateDeadPersonData();
        } else {
            updateAlivePersonData();
        }

        String colonyName = person.getColony().toString();
        colonyLabel.setText("Colony: " + colonyName);

        int kills = person.getKills().getKills();
        this.killsLabel.setText("Killed: " + kills);
    }

    private ImageIcon scaleImageIcon(ImageIcon originalIcon, int width, int height) {
        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(scaledImage);
    }

    private void updateDeadPersonData() {
        lifeLabel.setText("Person is Dead.");
        positionLabel.setText("Died at: (" + person.getPosition() + ")");
    }

    private void updateAlivePersonData() {
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

