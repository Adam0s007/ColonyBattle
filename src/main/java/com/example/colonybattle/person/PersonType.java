package com.example.colonybattle.person;

public enum PersonType {
    FARMER(8, 8, 5,20),
    DEFENDER(20, 12, 10,0),
    WARRIOR(12, 15, 20,0),
    WIZARD(14, 20, 14,0);

    private final int health;
    private final int energy;
    private final int strength;

    private final int landAppropriation;

    PersonType(int health, int energy, int strength,int landAppropriation){
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;

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

    public int getLandAppropriation() {
        return landAppropriation;
    }


    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

