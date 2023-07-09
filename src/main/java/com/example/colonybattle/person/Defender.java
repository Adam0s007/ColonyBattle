package com.example.colonybattle.person;

import com.example.colonybattle.Colony;
import com.example.colonybattle.Vector2d;

public class Defender extends Person{
    public Defender(int health, int energy, int strength, Vector2d position, Colony colony,int id) {
        super(health, energy, strength, position, colony, 10,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Defender
    }

    // Implementacja metod...
}
