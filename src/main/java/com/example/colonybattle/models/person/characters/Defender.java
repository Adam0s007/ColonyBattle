package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.board.position.finder.DefenderClosestPositionFinder;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.attack.DefenderAttackStrategy;
import com.example.colonybattle.models.person.actions.attack.PersonAttackStrategy;
import com.example.colonybattle.models.person.actions.defense.DefenderDefendStrategy;
import com.example.colonybattle.models.person.actions.movement.DefenderMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Defender extends Person {

    private final int MIN_PROTECTION_ENERGY = 3;
    private final int MIN_WAIT = 1200;
    private final int MAX_WAIT = 2500;
    public Defender(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new DefenderMovementStrategy(this);
        status.setType(type);
        this.attackPerformer = new DefenderAttackStrategy(this,movement);
        this.defendStrategy = new DefenderDefendStrategy(this);
        this.closestPositionFinder = new DefenderClosestPositionFinder();

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
    public void defend(Person person,int damage) {
        this.defendStrategy.defend(person,damage);
    }
    @Override
    public void attack(Person person) {
        this.attackPerformer.attack(person);
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
    //override to string (PersonType + id)
    @Override
    public String toString() {
        return this.getType().toString() + "[" + this.getStatus().getId()+"]";
    }


}
