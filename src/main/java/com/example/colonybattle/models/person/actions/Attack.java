package com.example.colonybattle.models.person.actions;

import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.helpers.BoardRef;

public class Attack {
    private final Person attacker;
    private final BoardRef boardRef;

    private final Point2d[] offsets;

    public Attack(Person attacker, BoardRef boardRef) {
        this.attacker = attacker;
        this.boardRef = boardRef;
        offsets = Direction.getVectors();
    }

    public void executeNearbyAttack() {
        for (Point2d offset : offsets) {
            executeAttackInDirection(offset);
        }
    }


    private void executeAttackInDirection(Point2d offset) {
        Point2d position = attacker.getPosition();
        Point2d targetPos = boardRef.calculateNewPosition(position,offset);
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
}

