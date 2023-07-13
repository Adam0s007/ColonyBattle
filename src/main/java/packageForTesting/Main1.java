package packageForTesting;

import javax.swing.*;
import java.awt.*;

public class Main1 {
    public static void main(String[] args) {
        ImageIcon image = new ImageIcon("heads/wizard.png");
        JLabel label = new JLabel();
        label.setText("Hello");

        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(0x00FF00));
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.add(label);

        int width = frame.getWidth();
        int height = frame.getHeight();
        //image resized to fit label
        image = new ImageIcon(image.getImage().getScaledInstance(width/2, height/2, Image.SCALE_DEFAULT));
        label.setIcon(image);
        label.setIconTextGap(0);
        label.setBounds(width/4, height/4, width/2, height/2);
        frame.getContentPane().setBackground(new Color(0x123456));
    }
}
