package com.example.colonybattle.person;

public enum PersonType {
    FARMER(8, 8, 5),
    DEFENDER(14, 12, 10),
    WARRIOR(12, 15, 8),
    WIZARD(14, 15, 14);

    private final int health;
    private final int energy;
    private final int strength;

    PersonType(int health, int energy, int strength) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public int getStrength() {
        return strength;
    }
}

