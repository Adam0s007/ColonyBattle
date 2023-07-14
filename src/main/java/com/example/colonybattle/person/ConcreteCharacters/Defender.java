package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Defender extends Person {

    private final int MIN_PROTECTION_ENERGY = 3;
    public Defender(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);

    }
    @Override
    public Character getInitial() {
        return 'D'; // Dla Obrońcy
    }
    // Implementacja metod...

    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }

    @Override
    public void defend(int damage) {
        if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
            double random = ThreadLocalRandom.current().nextDouble(); // Generate a random number between 0 and 1

            if (random <= 0.8) {
                status.addEnergy(-1);//traci tylko 1 punkt energii
            } else {
                status.addEnergy(-1);
                status.addHealth(-1);
            }
        } else {
            double damageReduction = 0.6; // 60% damage reduction when energy < PROTECTION_ENERGY
            int reducedDamage = (int) Math.ceil(damage * damageReduction);
            status.addHealth(-reducedDamage);
        }
    }
    @Override
    public void attack(Person person) {
        int strength = status.getStrength();
        int energy = status.getEnergy();
        if(energy < this.MIN_PROTECTION_ENERGY) {
            person.defend(1);
        }
        int damage = (int) Math.ceil((0.1*strength) * ((energy / 10.0)));
        person.defend(damage);
    }

}
