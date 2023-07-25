package com.example.colonybattle.models.person.actions.attack;

import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.movement.Movement;

import java.util.concurrent.locks.ReentrantLock;

public class PersonAttackStrategy implements AttackStrategy {
    protected final Person attacker;
    private final Movement movement;
    private final Point2d[] offsets;
    protected final ReentrantLock attackLock = new ReentrantLock();

    public PersonAttackStrategy(Person attacker, Movement movement) {
        this.attacker = attacker;
        this.movement = movement;
        offsets = Direction.getVectors();
    }

    @Override
    public void executeNearbyAttack() {
        for (Point2d offset : offsets) {
            executeAttackInDirection(offset);
        }
    }


    private void executeAttackInDirection(Point2d offset) {
        Point2d position = attacker.getPosition();
        Point2d targetPos = movement.calculateNewPosition(position,offset);
        Person person = targetPos.getPerson();
        if(person != null){
            if(person.getColony().getType() != attacker.getColony().getType()){
                attackAndPossiblyKill(person);
            }
        }
    }

    public void attackAndPossiblyKill(Person person) {
        attacker.attack(person);
        if(person != null && person.isRunning() && person.getStatus().getHealth() <= 0){
            //System.out.println(person.getColony());
            if(person.getColony() != null)
                person.getColony().removePerson(person);
        }
    }
    @Override
    public void attack(Person person) {
        if (attackLock.tryLock()) {
            try {
                person.defend(attacker,2);
                attacker.getStatus().addEnergy(-1);
            } finally {
                attackLock.unlock();
            }
        } else {
            System.out.println("Unable to lock, skipping attack.");
        }
    }



}

