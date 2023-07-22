package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.utils.Calculator;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

public class FarmerMovementStrategy extends MovementStrategy{

    public FarmerMovementStrategy(Person person) {
        super(person);
    }
    @Override
    public Point2d closestField(Person person) {
        Stream<Point2d> fieldsStream = streamFilteredFields();
        fieldsStream = fieldsStream.filter(vector -> vector.getMembership() == null || !vector.getMembership().equals(person.getColony()));
        Point2d newPosition = fieldsStream.min(Comparator.comparing(vector -> vector.distanceTo(person.getPosition())))
                .orElse(null);
        if(newPosition == null) newPosition = generateRandomPosition(person.getPosition());
        return newPosition;
    }

    @Override
    protected boolean shouldReturnNewFieldPos(Point2d closestPersonPos){
        return (person.getStatus().getHealth() > 4 || Calculator.calculateDistance(person.getPosition(), closestPersonPos) >= 6);
    }
    @Override
    protected Point2d calculateDirectionVecPerson(Point2d closestPersonPos) {//here it should be abstract
        Point2d directionVecPerson = Calculator.calculateDirection(person.getPosition(), closestPersonPos);
        return new Point2d(-directionVecPerson.getX(), -directionVecPerson.getY());
    }
}
