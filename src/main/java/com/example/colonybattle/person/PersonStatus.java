package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;

public class PersonStatus {
    int health;
    int energy;
    int strength;
    int id;
    int landAppropriation;

    PersonStatus(int health, int energy, int strength, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.id = id;
    }

}
