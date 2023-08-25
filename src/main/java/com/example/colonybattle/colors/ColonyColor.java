package com.example.colonybattle.colors;

public enum ColonyColor {
    VOLCANIC_NATION(ConsoleColor.RED),
    ICE_NATION(ConsoleColor.BRIGHT_BLUE),
    JUNGLE_NATION(ConsoleColor.GREEN),
    DESERT_NATION(ConsoleColor.YELLOW),
    DEFAULT_COLOR(ConsoleColor.BLUE);
    ;
    private final ConsoleColor consoleColor;
    ColonyColor(ConsoleColor consoleColor) {
        this.consoleColor = consoleColor;
    }
    public ConsoleColor getColor() {
        return consoleColor;
    }
}

