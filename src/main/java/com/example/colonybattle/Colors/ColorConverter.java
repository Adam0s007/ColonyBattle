package com.example.colonybattle.Colors;


public class ColorConverter {
    public static java.awt.Color convertColor(Color color){
        switch (color){
            case RED:
            case BRIGHT_RED:
                return java.awt.Color.RED;
            case BLUE:
            case BRIGHT_BLUE:
                return java.awt.Color.BLUE;
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
}
