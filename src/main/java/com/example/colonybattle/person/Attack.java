package com.example.colonybattle.person;

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
        offsets = getOffsets();
    }

    public void executeNearbyAttack() {
        for (Vector2d offset : offsets) {
            executeAttackInDirection(offset);
        }
    }

    private Vector2d[] getOffsets() {
        return new Vector2d[] {
                new Vector2d(-1, -1), new Vector2d(-1, 0), new Vector2d(-1, 1),
                new Vector2d(0, -1), new Vector2d(0, 1),
                new Vector2d(1, -1), new Vector2d(1, 0), new Vector2d(1, 1),
        };
    }

    private void executeAttackInDirection(Vector2d offset) {
        Vector2d targetPos = attacker.getPosition().addVector(offset);
        if (!boardRef.isFieldOccupied(targetPos)) {
            return;
        }
        targetPos = boardRef.getVectorFromBoard(targetPos);
        Set<Person> people = targetPos.getPeople();
        attackEnemiesInSet(people);
    }

    private void attackEnemiesInSet(Set<Person> people) {
        for (Person person : people) {
            if (person.getColony().getType() != attacker.getColony().getType()) {
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

