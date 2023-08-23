package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.launcher.Engine;
import com.example.colonybattle.models.person.type.PeopleNumber;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
class GameStarter {

    private final SpinnerController spinnerController;

    public GameStarter(SpinnerController spinnerController) {
        this.spinnerController = spinnerController;
    }

    public void startGame() {
        spinnerController.updatePeopleNumbers();
        new Engine().run();
    }

    public ChangeListener getSpinnerValueAdjuster() {
        return e -> {
            JSpinner sourceSpinner = (JSpinner) e.getSource();
            if (spinnerController.getTotalSpinnerValue() > ComponentFactory.MAX_TOTAL_CHARACTERS) {
                sourceSpinner.setValue((int) sourceSpinner.getValue() - 1);
            }
        };
    }
}
