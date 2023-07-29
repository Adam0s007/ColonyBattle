package com.example.colonybattle.ui.infopanel.colony;

import com.example.colonybattle.colony.Colony;

import javax.swing.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SpawnPanel extends JPanel{
    private final JLabel countdownLabel;
    private int countdown;
    private Timer timer;
    private final int spawnPeriod = Colony.PERIOD_SEC;
    private final List<Colony> allColonies;
    public SpawnPanel(List<Colony> allColonies) {
        this.countdown = Colony.PERIOD_SEC;
        this.countdownLabel = new JLabel(String.valueOf(countdown));
        this.allColonies = allColonies;
        this.add(countdownLabel);
    }
    public void runTimer() {
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownLabel.setText("next spawn: "+ countdown);
                if (countdown <= 0) {
                    allColonies.forEach(colony -> colony.spawnPerson());
                    resetTimer();
                }
                countdown--;
            }
        });
        this.timer.start();
    }

    public void resetTimer() {
        this.countdown = spawnPeriod;
        this.countdownLabel.setText("next spawn: "+ countdown);
    }
}

