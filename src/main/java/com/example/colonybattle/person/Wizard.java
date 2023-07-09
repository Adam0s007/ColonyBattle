package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Magic;
import com.example.colonybattle.Vector2d;

public class Wizard extends Person implements Magic {
    public Wizard(int health, int strength, Vector2d position, Colony colony,int landAppropriation,int id) {
        super(health, 20, strength, position, colony, landAppropriation,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Wizard
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
