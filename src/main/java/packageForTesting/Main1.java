package packageForTesting;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Main1 {
    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("src/main/resources/heads/wizard.png");
        Border border = BorderFactory.createLineBorder(Color.GREEN, 3);

        JLabel label = new JLabel();
        label.setText("Hello");

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setForeground(new Color(0x00FF00));
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setIcon(image);
        label.setIconTextGap(-20);
        label.setBorder(border);

        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(label);

        int width = frame.getWidth();
        int height = frame.getHeight();
        //image resized to fit label



        frame.getContentPane().setBackground(new Color(0x123456));
    }
}
