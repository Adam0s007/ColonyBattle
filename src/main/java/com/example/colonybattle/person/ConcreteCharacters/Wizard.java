package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Magic;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Wizard extends Person implements Magic {

    private  final int MIN_PROTECTION_ENERGY = 4;
    public Wizard(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);

    }

    @Override
    public Character getInitial() {
        return 'W';
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
    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }

    @Override
    public void defend(int damage) {
        if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
            double random = ThreadLocalRandom.current().nextDouble(); // Generate a random number between 0 and 1

            if (random <= 0.6) {
                status.addEnergy(-MIN_PROTECTION_ENERGY);//traci tylko 1 punkt energii
            } else {
                //wizard potrafi przeksztalcic pewną część damage na energie ale wtedy pozostala czesc damage trafia w jego zdrowie
                int energy = (int) Math.ceil(damage * 0.4);
                int health = (int) Math.ceil(damage * 0.6);
                status.addEnergy(energy);
                status.addHealth(-health);
            }
        } else {
            double damageReduction = 0.5; // 50% damage reduction when energy < PROTECTION_ENERGY
            int reducedDamage = (int) Math.ceil(damage * damageReduction);
            status.addHealth(-reducedDamage);
        }
    }
}
