package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.board.position.finder.WarriorClosestPositionFinder;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.attack.PersonAttackStrategy;
import com.example.colonybattle.models.person.actions.attack.WizardAttackStrategy;
import com.example.colonybattle.models.person.actions.defense.WarriorDefendStrategy;
import com.example.colonybattle.models.person.actions.movement.WarriorMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class Warrior extends Person {

    private final int MIN_WAIT = 800;
    private final int MAX_WAIT = 1400;

    public Warrior(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new WarriorMovementStrategy(this);
        status.setType(type);
        this.attackPerformer = new WizardAttackStrategy(this,movement);
        this.defendStrategy = new WarriorDefendStrategy(this);
        this.closestPositionFinder = new WarriorClosestPositionFinder();
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
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }

    @Override
    public String toString() {
        return this.getType().toString() + "[" + this.getStatus().getId()+"]";
    }


}
