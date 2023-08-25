package com.example.colonybattle.ui.frame.startwindow;

import javax.swing.*;

class FrameSetup {

    private final JFrame frame;

    public FrameSetup(JFrame frame) {
        this.frame = frame;
    }

    public void setupFrame() {
        frame.setTitle("Colony Battle - Start");
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

