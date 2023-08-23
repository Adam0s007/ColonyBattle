package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.launcher.Engine;
import com.example.colonybattle.models.person.type.PeopleNumber;

import javax.swing.*;
import java.awt.*;
class SpinnerController {

    private final ComponentFactory componentFactory;

    public SpinnerController(JFrame frame, ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
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
