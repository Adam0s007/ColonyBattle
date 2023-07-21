package com.example.colonybattle.utils;

public class InitialConventer {

    private static InitialConventer instance = null;

    private InitialConventer() {
        // Prywatny konstruktor uniemożliwia bezpośrednie tworzenie obiektów
    }
    public static InitialConventer getInstance() {
        if (instance == null) {
            instance = new InitialConventer();
        }
        return instance;
    }
    public String convertInitial(Character initial) {
        if (initial == null) {
            return null;
        }

        switch (Character.toUpperCase(initial)) {
            case 'A':
                return "Warrior";
            case 'W':
                return "Wizard";
            case 'D':
                return "Defender";
            case 'F':
                return "Farmer";
            default:
                return null;
        }
    }
}
