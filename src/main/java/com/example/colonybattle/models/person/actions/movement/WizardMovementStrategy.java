package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.characters.Farmer;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.utils.Calculator;

public class WizardMovementStrategy extends MovementStrategy{
    public WizardMovementStrategy(Person person) {
        super(person);
    }

    @Override
    protected boolean shouldReturnNewFieldPos(Point2d closestPersonPos){
        return (person.getStatus().getHealth() > 4 || Calculator.calculateDistance(person.getPosition(), closestPersonPos) >= 6);
    }
    @Override
    protected Point2d calculateDirectionVecPerson(Point2d closestPersonPos) {
        Point2d directionVecPerson = Calculator.calculateDirection(person.getPosition(), closestPersonPos);
        return new Point2d(-directionVecPerson.getX(), -directionVecPerson.getY());
    }
}
