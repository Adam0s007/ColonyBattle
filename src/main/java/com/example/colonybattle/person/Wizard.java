package com.example.colonybattle.person;

import com.example.colonybattle.Magic;

public class Wizard extends Person implements Magic {
    public Wizard() {
        this.energy = 10;  // Wartość przykładowa
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
