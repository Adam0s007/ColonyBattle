package com.example.colonybattle.Colors;

public enum ColonyColor {
    COLONY1(Color.RED),
    COLONY2(Color.BLUE),
    COLONY3(Color.GREEN),
    COLONY4(Color.YELLOW);

    private final Color color;

    ColonyColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

