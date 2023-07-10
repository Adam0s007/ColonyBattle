package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;

public class Farmer extends Person {
    public Farmer(int health, int energy, int strength, Vector2d position, Colony colony,int id) {
        super(health, energy, strength, position, colony, 20,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Farmer
    }

    @Override
    public Character getInitial() {
        return 'F'; // Dla Obrońcy
    }

    // Implementacja metod...
}
