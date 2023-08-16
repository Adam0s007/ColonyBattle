package com.example.colonybattle.ui.infopanel.person;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
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
    private JLabel ageLabel;
    private JLabel strengthLabel;
    private JLabel hintLabel;
    private int size = 17;

    public PersonPanel() {
        setPanelLayout();
        addImagePanel();
        idLabel = addLabel(Font.PLAIN,size);
        ageLabel = addLabel(Font.PLAIN,size);
        lifeLabel = addLabel(Font.PLAIN,size);
        energyLabel = addLabel(Font.PLAIN,size);
        strengthLabel = addLabel(Font.PLAIN,size);
        positionLabel = addLabel(Font.PLAIN,size);
        colonyLabel = addLabel(Font.PLAIN,size);
        killsLabel = addLabel(Font.PLAIN,size);
        hintLabel = addLabel(Font.BOLD,12);
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


    private JLabel addLabel(int font, int size) {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", font, size));
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
        idLabel.setText(person.toString());

        BigDecimal age = person.getStatus().getAge();
        ageLabel.setText("Age: " + age);

        if(person.getStatus().getHealth() <= 0) {
            updateDeadPersonData();
        } else {
            updateAlivePersonData();
        }

        int strength = person.getStatus().getStrength();
        strengthLabel.setText("Strength: " + strength);

        String colonyName = person.getColony().toString();
        colonyLabel.setText("Colony: " + colonyName);

        int kills = person.getKills().getKills();
        this.killsLabel.setText("Killed: " + kills);
        if(person.getStatus().getHealth() > 0)
            this.hintLabel.setText("Right-click on the map to make the focused character go there");
        else
            this.hintLabel.setText("");
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

