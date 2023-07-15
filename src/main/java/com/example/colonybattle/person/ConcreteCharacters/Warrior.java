package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Warrior extends Person {

    private final int MIN_PROTECTION_ENERGY = 4;
    public Warrior(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);

    }

    @Override
    public Character getInitial() {
        return 'A'; // Dla Wojownika
    }



    // Implementacja metod...
    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }

    @Override
    public void defend(int damage) {
        if (status.getEnergy() >= MIN_PROTECTION_ENERGY) { // Minimalna wartość energii wymagana do obrony
            double random = ThreadLocalRandom.current().nextDouble();  // Generowanie losowej liczby z zakresu 0-1

            if (random <= 0.45) {
                // Obrona się powiodła - nie traci życia, ale traci MIN_PROTECTION_ENERGY
                status.addEnergy(-MIN_PROTECTION_ENERGY);
            } else {
                // Obrona się nie powiodła - traci 1 serce
                status.addHealth(-1);
            }
        } else {
            // Brak wystarczającej ilości energii do obrony
            double damageReduction = 0.6; // 40% redukcji obrażeń, gdy brak energii
            int reducedDamage = (int) Math.ceil(damage * damageReduction);
            status.addHealth(-reducedDamage);
        }
    }


    //szuka najbliższej osoby z pobliskiej kolonii
    @Override
    public Vector2d findClosestPerson() {
        Vector2d closestPersonPosition = null;
        List<Colony> colonies = this.boardRef.getAllColonies();
        Optional<Person> closestPerson = colonies.stream()
                .filter(colony -> !colony.equals(this.getColony())) // filter out this person's colony
                .flatMap(colony -> colony.getPeople().stream())     // get stream of people from other colonies
                .min(Comparator.comparing(person -> this.position.distanceTo(person.getPosition()))); // find person with minimum distance

        if (closestPerson.isPresent()) {
            closestPersonPosition = closestPerson.get().getPosition();
        }
        return closestPersonPosition;
    }
    @Override
    public void attack(Person person) {
        int strength = status.getStrength();
        int energy = status.getEnergy();
        if(energy < this.MIN_PROTECTION_ENERGY) {
            person.defend(1);
        }
        int damage = (int) Math.ceil((0.2*strength) * ((energy / 10.0)));
        person.defend(damage);
    }



}
