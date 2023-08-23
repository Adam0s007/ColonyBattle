package com.example.colonybattle;
import com.example.colonybattle.launcher.Engine;
import com.example.colonybattle.utils.ThreadUtils;
import com.example.colonybattle.ui.frame.StartWindow;
import javax.swing.SwingUtilities;
public class HelloApplication{
        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                new StartWindow();
            });
        }

}