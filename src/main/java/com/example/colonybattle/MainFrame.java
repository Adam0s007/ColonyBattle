package com.example.colonybattle;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Przykład z obrazkiem i etykietą");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Wczytaj obrazek
        ImageIcon imageIcon = new ImageIcon("heads/warrior.png");
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Skaluj obrazek do żądanej wielkości

        // Dodaj etykietę z napisem
        JLabel label = new JLabel("Przykładowy napis");
        label.setHorizontalAlignment(JLabel.CENTER); // Wyśrodkuj napis poziomo

        // Ustaw układ GridBagLayout
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        panel.add(new JLabel(new ImageIcon(scaledImage)), constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.1;
        panel.add(label, constraints);

        add(panel);
        pack();
        setLocationRelativeTo(null); // Wyśrodkuj okno na ekranie
    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}

