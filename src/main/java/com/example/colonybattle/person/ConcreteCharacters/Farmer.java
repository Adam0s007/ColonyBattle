package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Farmer extends Person {

    private final int MIN_PROTECTION_ENERGY = 5;
    public Farmer(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);

    }
    @Override
    public Character getInitial() {
        return 'F'; // Dla Obrońcy
    }

    // Implementacja metod...
    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }

    @Override
    public void defend(int damage) {
        if (status.getEnergy() >= MIN_PROTECTION_ENERGY) { // Minimalna wartość energii wymagana do obrony
            double random = ThreadLocalRandom.current().nextDouble(); // Generowanie losowej liczby z zakresu 0-1

            if (random <= 0.1) {
                // Obrona się powiodła - nie traci życia, ale traci 5 punkt energii
                status.addEnergy(-MIN_PROTECTION_ENERGY);
            } else {
                // Obrona się nie powiodła - traci 1 serce i 1 punkt energii
                status.addEnergy(-1);
                status.addHealth(-1);
            }
        } else {
            // Brak wystarczającej ilości energii do obrony
            double damageReduction = 0.2; // 20% redukcji obrażeń, gdy brak energii
            int reducedDamage = (int) Math.ceil(damage * damageReduction);
            status.addHealth(-reducedDamage);
        }
    }


}
