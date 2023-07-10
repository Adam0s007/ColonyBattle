package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;

public class Warrior extends Person {
    public Warrior(int health, int energy, Vector2d position, Colony colony,int landAppropriation,int id) {
        super(health, energy, 20, position, colony, landAppropriation,id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
    }

    @Override
    public Character getInitial() {
        return 'A'; // Dla Wojownika
    }



    // Implementacja metod...

}
