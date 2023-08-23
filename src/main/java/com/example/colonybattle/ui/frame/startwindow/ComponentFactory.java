package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.launcher.Engine;
import com.example.colonybattle.models.person.type.PeopleNumber;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
@Getter
class ComponentFactory {


    public static final int MAX_TOTAL_CHARACTERS = 12;

    private final JFrame frame;
    private JSpinner farmerSpinner;
    private JSpinner defenderSpinner;
    private JSpinner warriorSpinner;
    private JSpinner wizardSpinner;

    public ComponentFactory(JFrame frame) {
        this.frame = frame;
    }

    public void initializeComponents(GameStarter gameStarter) {
        addHeaderLabel();

        ChangeListener listener = gameStarter.getSpinnerValueAdjuster();

        farmerSpinner = addSpinnerWithLabel("Farmers:", 20, 3, listener);
        defenderSpinner = addSpinnerWithLabel("Defenders:", 60, 2, listener);
        warriorSpinner = addSpinnerWithLabel("Warriors:", 100, 4, listener);
        wizardSpinner = addSpinnerWithLabel("Wizards:", 140, 1, listener);

        JButton startButton = new JButton("Start");
        startButton.setBounds(150, 200, 100, 50);
        startButton.addActionListener(e -> gameStarter.startGame());
        frame.add(startButton);
    }
    private void addHeaderLabel() {
        JLabel header = new JLabel("Initial Characters per Colony");
        header.setBounds(50, 0, 300, 20);
        frame.add(header);
    }
    private JSpinner addSpinnerWithLabel(String labelText, int yPosition, int initialValue, ChangeListener listener) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, yPosition, 80, 25);
        frame.add(label);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, 1, MAX_TOTAL_CHARACTERS, 1));
        spinner.setBounds(150, yPosition, 50, 25);
        spinner.addChangeListener(listener);
        frame.add(spinner);

        return spinner;
    }
}
