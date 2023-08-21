package com.example.colonybattle.models.person.type;

public enum PersonType {
    FARMER(8, 8, 5,20,5),
    DEFENDER(20, 12, 10,20,3),
    WARRIOR(12, 15, 20,20,4),
    WIZARD(14, 20, 12,20,4);

    private final int health;
    private final int energy;
    private final int strength;

    private final int protection_energy;
    private final int landAppropriation;

    PersonType(int health, int energy, int strength,int landAppropriation,int protection_energy){
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.protection_energy = protection_energy;
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

    public int getProtection_energy() {return protection_energy;}

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

