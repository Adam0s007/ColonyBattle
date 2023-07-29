package com.example.colonybattle.ui.infopanel.colony;

import com.example.colonybattle.colony.Colony;

import javax.swing.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpawnPanel extends JPanel implements TimerObserver {
    private final JLabel countdownLabel;
    private int countdown;
    private Timer timer;
    private final int spawnPeriod = Colony.PERIOD_SEC;
    public SpawnPanel() {
        this.countdown = Colony.PERIOD_SEC;
        this.countdownLabel = new JLabel(String.valueOf(countdown));

        // Set up timer to fire every second (1000 ms)


        this.add(countdownLabel);
    }
    public void runTimer() {
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdown--;
                countdownLabel.setText("next spawn: "+ countdown);
            }
        });

        this.timer.start();
    }

    @Override
    public void updateTime(int seconds) {
        // Update countdown label here...
        resetTimer();

    }

    public void resetTimer() {
        this.countdown = spawnPeriod;
        this.countdownLabel.setText("next spawn: "+ countdown);
    }
}

