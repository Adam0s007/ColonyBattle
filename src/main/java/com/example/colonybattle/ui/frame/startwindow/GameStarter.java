package com.example.colonybattle.ui.frame.startwindow;

import com.example.colonybattle.launcher.Engine;

import javax.swing.*;
import javax.swing.event.ChangeListener;

class GameStarter {

    private final SpinnerController spinnerController;

    public GameStarter(SpinnerController spinnerController) {
        this.spinnerController = spinnerController;
    }

    public void startGame() {
        spinnerController.updatePeopleNumbers();
        spinnerController.updateConfigurations(); // Aktualizacja nowych konfiguracji
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
