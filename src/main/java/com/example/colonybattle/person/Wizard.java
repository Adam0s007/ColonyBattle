package com.example.colonybattle.person;

import com.example.colonybattle.Colony;
import com.example.colonybattle.Magic;
import com.example.colonybattle.Vector2d;

public class Wizard extends Person implements Magic {
    public Wizard(int health, int energy, int strength, Vector2d position, Colony colony,int id) {
        super(health, energy, strength, position, colony, 10,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Wizard
    }

    @Override
    public void wand() {
        // Implementacja...
    }

    @Override
    public void healMyself() {
        // Implementacja...
    }

    // Implementacja pozostałych metod...
}
