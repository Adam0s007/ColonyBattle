package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.attack.Attack;
import com.example.colonybattle.models.person.actions.defense.FarmerDefendStrategy;
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
        this.defendStrategy = new FarmerDefendStrategy(this);

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
    public void defend(Person person,int damage) {
        this.defendStrategy.defend(person,damage);
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

    @Override
    public String toString() {
        return this.getType().toString() + "[" + this.getStatus().getId()+"]";
    }


}
