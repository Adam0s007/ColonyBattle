package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;

public class Defender extends Person{
    public Defender(int energy, int strength, Vector2d position, Colony colony,int landAppropriation,int id) {
        super(20, energy, strength, position, colony, landAppropriation,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Defender
    }

    // Implementacja metod...
}
