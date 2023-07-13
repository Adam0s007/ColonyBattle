package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;

public class PersonStatus {
    volatile int  health;
    volatile int energy;
    final int strength;
    volatile int id;
    final int landAppropriation;

    PersonStatus(int health, int energy, int strength, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setHealth(int health) {
        if(health < 0){
            this.health = 0;
            return;
        }
        this.health = health;
    }

    public void setEnergy(int energy) {
        if(energy < 0){
            this.energy = 0;
            return;
        }
        this.energy = energy;
    }
    public void addEnergy(int energy){
        if(this.energy + energy < 0){
            this.energy = 0;
            return;
        }
        this.energy += energy;
    }
    public void addHealth(int health){
        if(this.health + health < 0){
            this.health = 0;
            return;
        }
        this.health += health;
    }

    public void setId(int id) {
        this.id = id;
    }
}
