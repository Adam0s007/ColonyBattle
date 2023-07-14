package com.example.colonybattle.Colors;

public enum ColonyColor {
    COLONY1(ConsoleColor.RED),
    COLONY2(ConsoleColor.BRIGHT_BLUE),
    COLONY3(ConsoleColor.GREEN),
    COLONY4(ConsoleColor.YELLOW);

    private final ConsoleColor consoleColor;

    ColonyColor(ConsoleColor consoleColor) {
        this.consoleColor = consoleColor;
    }

    public ConsoleColor getColor() {
        return consoleColor;
    }
}

