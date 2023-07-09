package com.example.colonybattle.Colors;

public class ColorConverter {
    public static java.awt.Color convertColor(Color color){
        switch (color){
            case RED:
                return java.awt.Color.RED;
            case BLUE:
                return java.awt.Color.BLUE;
            case GREEN:
                return java.awt.Color.GREEN;
            case YELLOW:
                return java.awt.Color.YELLOW;
            default:
                return java.awt.Color.WHITE;
        }
    }
}
