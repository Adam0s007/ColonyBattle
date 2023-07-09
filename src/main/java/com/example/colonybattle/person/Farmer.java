package com.example.colonybattle.person;

import com.example.colonybattle.Colony;
import com.example.colonybattle.Vector2d;

public class Farmer extends Person {
    public Farmer(int health, int energy, int strength, Vector2d position, Colony colony) {
        super(health, energy, strength, position, colony, 10);  // Wartość 10 to przykładowa wartość landAppropriation dla Farmer
    }

    // Implementacja metod...
}
