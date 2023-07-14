package com.example.colonybattle.person;

import com.example.colonybattle.Direction;
import com.example.colonybattle.person.BoardRef;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonStatus;
import com.example.colonybattle.Vector2d;

import java.util.Set;

public class Attack {
    private final Person attacker;
    private final BoardRef boardRef;

    private final Vector2d[] offsets;

    public Attack(Person attacker, BoardRef boardRef) {
        this.attacker = attacker;
        this.boardRef = boardRef;
        offsets = Direction.getVectors();
    }

    public void executeNearbyAttack() {
        for (Vector2d offset : offsets) {
            executeAttackInDirection(offset);
        }
    }


    private void executeAttackInDirection(Vector2d offset) {
        Vector2d position = attacker.getPosition();
        Vector2d targetPos = boardRef.calculateNewPosition(position,offset);
        Person person = targetPos.getPerson();
        if(person != null){
            if(person.getColony().getType() != attacker.getColony().getType()){
                attackAndPossiblyKill(person);
            }
        }
    }


    private void attackAndPossiblyKill(Person person) {
        attacker.attack(person);
        if (person.getStatus().getHealth() <= 0) {
            person.cellHelper.deathColor();
        }
    }
}

