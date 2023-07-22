package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.characters.Farmer;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.utils.Calculator;

public class WarriorMovementStrategy extends MovementStrategy{
    public WarriorMovementStrategy(Person person) {
        super(person);
    }

    @Override
    protected boolean shouldReturnNewFieldPos(Point2d closestPersonPos){
        return false;
    }
    @Override
    protected Point2d calculateDirectionVecPerson(Point2d closestPersonPos) {//here it should be abstract
        return  Calculator.calculateDirection(person.getPosition(), closestPersonPos);
    }
}

