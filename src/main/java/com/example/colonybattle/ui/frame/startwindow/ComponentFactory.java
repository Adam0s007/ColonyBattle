package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.config.BoardConfig;
import com.example.colonybattle.config.SpawningTime;
import com.example.colonybattle.config.WizardMagic;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.ChangeListener;

@Getter
class ComponentFactory {


    public static final int MAX_TOTAL_CHARACTERS = 12;

    private final JFrame frame;
    private JSpinner farmerSpinner;
    private JSpinner defenderSpinner;
    private JSpinner warriorSpinner;
    private JSpinner wizardSpinner;
    private JSpinner boardSizeSpinner;
    private JSpinner obstaclesAmountSpinner;
    private JSpinner spawningTimeSpinner;
    private JCheckBox magicEnabledCheckBox;

    public ComponentFactory(JFrame frame) {
        this.frame = frame;
    }

    public void initializeComponents(GameStarter gameStarter) {
        addHeaderLabel();

        ChangeListener listener = gameStarter.getSpinnerValueAdjuster();

        farmerSpinner = addSpinnerWithLabel("Farmers:", 20, 3,1 ,MAX_TOTAL_CHARACTERS,1,listener);
        defenderSpinner = addSpinnerWithLabel("Defenders:", 40, 2, 1,MAX_TOTAL_CHARACTERS,1,listener);
        warriorSpinner = addSpinnerWithLabel("Warriors:", 60, 4, 1,MAX_TOTAL_CHARACTERS,1,listener);
        wizardSpinner = addSpinnerWithLabel("Wizards:", 80, 1, 1,MAX_TOTAL_CHARACTERS,1,listener);

        boardSizeSpinner = addSpinnerWithLabel("Board Size:", 100, BoardConfig.getInstance().getBoardSize(), 15, 25, 1, null);
        obstaclesAmountSpinner = addSpinnerWithLabel("Max Obstacles:", 120, BoardConfig.getInstance().getObstaclesAmount(), 0, 10, 1, null);
        spawningTimeSpinner = addSpinnerWithLabel("Spawning Time:", 140, SpawningTime.getInstance().getSpawningTime(), 15, 60, 15, null);

        magicEnabledCheckBox = new JCheckBox("Enable Magic:", WizardMagic.getInstance().isMagicEnabled());
        magicEnabledCheckBox.setBounds(50, 160, 150, 25);
        frame.add(magicEnabledCheckBox);

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
    private JSpinner addSpinnerWithLabel(String labelText, int yPosition, int initialValue, int min, int max, int step, ChangeListener listener) {
        JLabel label = new JLabel(labelText);
        label.setBounds(50, yPosition, 120, 25);
        frame.add(label);

        JSpinner spinner = new JSpinner(new SpinnerNumberModel(initialValue, min, max, step));
        spinner.setBounds(150, yPosition, 60, 25);
        if (listener != null) {
            spinner.addChangeListener(listener);
        }
        frame.add(spinner);

        return spinner;
    }
}
