package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.config.BoardConfig;
import com.example.colonybattle.config.PeopleNumber;
import com.example.colonybattle.config.SpawningTime;
import com.example.colonybattle.config.WizardMagic;

import javax.swing.*;

class SpinnerController {

    private final ComponentFactory componentFactory;

    public SpinnerController(JFrame frame, ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
    }

    public void updateConfigurations() {
        BoardConfig.getInstance().setBoardSize((int) componentFactory.getBoardSizeSpinner().getValue());
        BoardConfig.getInstance().setObstaclesAmount((int) componentFactory.getObstaclesAmountSpinner().getValue());
        SpawningTime.getInstance().setSpawningTime((int) componentFactory.getSpawningTimeSpinner().getValue());
        WizardMagic.getInstance().setMagicEnabled(componentFactory.getMagicEnabledCheckBox().isSelected());
    }

    public void updatePeopleNumbers() {
        PeopleNumber people = PeopleNumber.getInstance();
        people.setFarmerNumber((int) componentFactory.getFarmerSpinner().getValue());
        people.setDefenderNumber((int) componentFactory.getDefenderSpinner().getValue());
        people.setWarriorNumber((int) componentFactory.getWarriorSpinner().getValue());
        people.setWizardNumber((int) componentFactory.getWizardSpinner().getValue());
    }

    public int getTotalSpinnerValue() {
        return (int) componentFactory.getFarmerSpinner().getValue() +
                (int) componentFactory.getDefenderSpinner().getValue() +
                (int) componentFactory.getWarriorSpinner().getValue() +
                (int) componentFactory.getWizardSpinner().getValue();
    }
}
