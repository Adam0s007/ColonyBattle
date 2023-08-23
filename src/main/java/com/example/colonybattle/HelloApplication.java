package com.example.colonybattle;
import com.example.colonybattle.ui.frame.startwindow.StartWindow;
import javax.swing.SwingUtilities;
public class HelloApplication{
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new StartWindow();
            });
        }

}