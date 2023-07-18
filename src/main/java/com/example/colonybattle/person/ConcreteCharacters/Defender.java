package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Defender extends Person {

    private final int MIN_PROTECTION_ENERGY = 3;
    private final int MIN_WAIT = 1200;
    private final int MAX_WAIT = 2500;
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
    public  synchronized void defend(int damage) {
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
            return;
        }
        int damage = (int) Math.ceil((0.1*strength) * ((energy / 10.0)));
        person.defend(damage);
    }
    //szuka najblizszej osoby ze swojej kolonii (do bronienia)
    @Override
    public Vector2d findClosestPosition() {
        Vector2d closestPersonPosition = null;
        List<Colony> colonies = this.boardRef.getAllColonies();
        Optional<Person> closestPerson = colonies.stream()
                .filter(colony -> colony.equals(this.getColony())) // filter out this person's colony
                .flatMap(colony -> colony.getPeople().stream())     // get stream of people from other colonies
                .filter(person -> person.getType() != PersonType.DEFENDER)            // filter out this person
                .min(Comparator.comparing(person -> this.position.distanceTo(person.getPosition()))); // find person with minimum distance
        if (closestPerson.isPresent()) {
            closestPersonPosition = closestPerson.get().getPosition();
        }
        //System.out.println(this.colony.getType()+"Wywoluje sie!!");
        return closestPersonPosition;
    }
    @Override
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }

    @Override
    public void die() {
        //System.out.println("Defender died at position: "+this.getPosition().toString()+ " with id: "+this.getStatus().getId()+" and colony: "+this.getColony().getType()+"\n");
        super.die();

    }

}
