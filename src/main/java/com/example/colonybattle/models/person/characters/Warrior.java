package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.Attack;
import com.example.colonybattle.models.person.actions.movement.DefenderMovementStrategy;
import com.example.colonybattle.models.person.actions.movement.WarriorMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Warrior extends Person {

    private final int MIN_PROTECTION_ENERGY = 4;
    private final int MIN_WAIT = 800;
    private final int MAX_WAIT = 1400;

    public Warrior(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new WarriorMovementStrategy(this);
        status.setType(type);
        attackPerformer = new Attack(this,movement);

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
        if (defendLock.tryLock()) {
            try {
                if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
                    double random = ThreadLocalRandom.current().nextDouble();

                    if (random <= 0.45) {
                        status.addEnergy(-MIN_PROTECTION_ENERGY);
                    } else {
                        status.addHealth(-1);
                        if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                    }
                } else {
                    double damageReduction = 0.6;
                    int reducedDamage = (int) Math.ceil(damage * damageReduction);
                    status.addHealth(-reducedDamage);
                    if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                }
            } finally {
                defendLock.unlock();
            }
        } else {
            //System.out.println("Unable to lock, skipping defense.");
        }
    }


    //szuka najbliższej osoby z pobliskiej kolonii
    @Override
    public Point2d findClosestPosition() {
        Point2d closestPersonPosition = null;
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
        if (attackLock.tryLock()) {
            try {
                int strength = status.getStrength();
                int energy = status.getEnergy();
                if(energy < this.MIN_PROTECTION_ENERGY) {
                    person.defend(2);
                    if(person.getStatus().getHealth() <= 0)  person.cellHelper.deathColor();
                    return;
                }
                int damage = (int) Math.ceil((0.2*strength) * ((energy / 10.0)));
                person.defend(damage);
            } finally {
                attackLock.unlock();
            }
        } else {
            //System.out.println("Unable to lock, skipping attack.");
        }
    }

    @Override
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }


}
