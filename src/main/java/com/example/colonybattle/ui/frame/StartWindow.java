package com.example.colonybattle.ui.frame;


import com.example.colonybattle.launcher.Engine;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartWindow extends JFrame {

    public StartWindow() {
        setTitle("Colony Battle - Start");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton startButton = new JButton("Start");
        startButton.setBounds(100, 50, 100, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zamknięcie okna startowego
                dispose();
                // Uruchomienie silnika gry
                Engine engine = new Engine();
                engine.run();


            }
        });

        add(startButton);

        setLocationRelativeTo(null); // Ustawienie okna na środku ekranu
        setVisible(true);
    }
}

