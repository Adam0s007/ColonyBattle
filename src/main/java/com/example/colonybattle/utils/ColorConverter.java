package com.example.colonybattle.utils;


import com.example.colonybattle.colors.ConsoleColor;

import java.awt.*;

public class ColorConverter {
    public static java.awt.Color convertColor(ConsoleColor consoleColor){
        switch (consoleColor){
            case RED:
            case BRIGHT_RED:
                return java.awt.Color.RED;
            case BLUE:
                return java.awt.Color.BLUE;
            case BRIGHT_BLUE:
                return new java.awt.Color(126, 194, 252);
            case GREEN:
            case BRIGHT_GREEN:
                return java.awt.Color.GREEN;
            case YELLOW:
            case BRIGHT_YELLOW:
                return java.awt.Color.YELLOW;
            case PURPLE:
            case BRIGHT_PURPLE:
                return java.awt.Color.MAGENTA; // W AWT nie ma koloru PURPLE, więc używamy MAGENTA zamiast tego
            case CYAN:
            case BRIGHT_CYAN:
                return java.awt.Color.CYAN;
            case BLACK:
                return java.awt.Color.BLACK;
            case WHITE:
            case BRIGHT_WHITE:
                return java.awt.Color.WHITE;
            default:
                return java.awt.Color.WHITE;
        }
    }
    public static Color getContrastColor(Color color) {
        // Obliczanie jasności koloru (algorytm YIQ)
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;

        // Wybieranie odpowiedniego koloru czcionki na podstawie jasności tła
        return y >= 128 ? Color.BLACK : Color.WHITE;
    }

}
