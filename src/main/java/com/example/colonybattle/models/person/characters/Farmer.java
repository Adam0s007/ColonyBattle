package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.Attack;
import com.example.colonybattle.models.person.actions.movement.DefenderMovementStrategy;
import com.example.colonybattle.models.person.actions.movement.FarmerMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Farmer extends Person {

    private final int MIN_PROTECTION_ENERGY = 5;
    private final int MIN_WAIT = 600;
    private final int MAX_WAIT = 900;
    public Farmer(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new FarmerMovementStrategy(this);
        status.setType(type);
        attackPerformer = new Attack(this,movement);

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
        if (defendLock.tryLock()) {
            try {
                if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
                    double random = ThreadLocalRandom.current().nextDouble();

                    if (random <= 0.1) {
                        status.addEnergy(-MIN_PROTECTION_ENERGY);
                    } else {
                        status.addEnergy(-1);
                        status.addHealth(-1);
                        if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                    }
                } else {
                    double damageReduction = 0.2;
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

    //szuka najbliższej osoby z pobliskiej kolonii, ktora nie jest Farmerem (przed innymi ucieka)
    @Override
    public Point2d findClosestPosition() {
        Point2d closestPersonPosition = null;
        List<Colony> colonies = this.boardRef.getAllColonies();
        Optional<Person> closestPerson = colonies.stream()
                .filter(colony -> !colony.equals(this.getColony())) // filter out this person's colony
                .flatMap(colony -> colony.getPeople().stream())     // get stream of people from other colonies
                .filter(person -> person.getType() != PersonType.FARMER)
                .min(Comparator.comparing(person -> this.position.distanceTo(person.getPosition()))); // find person with minimum distance

        if (closestPerson.isPresent()) {
            closestPersonPosition = closestPerson.get().getPosition();
        }
        return closestPersonPosition;
    }

    @Override
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }


}
