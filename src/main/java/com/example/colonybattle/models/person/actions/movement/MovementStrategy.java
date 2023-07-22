package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.boardlocks.PosLock;
import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.characters.Farmer;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.models.person.helpers.BoardRef;
import com.example.colonybattle.models.person.helpers.CellHelper;
import com.example.colonybattle.models.person.helpers.ConnectionHelper;
import com.example.colonybattle.models.person.type.PersonType;
import com.example.colonybattle.utils.Calculator;
import com.example.colonybattle.utils.ThreadUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public abstract class MovementStrategy implements Movement {
    protected final Person person;

    public MovementStrategy(Person person) {
        this.person = person;
    }

    private void move(Point2d newPosition) {
        Point2d oldPosition = person.getPosition();
        person.getConnectionHelper().changePosConnections(oldPosition,newPosition);
        if(person.getCellHelper() == null) return;
        person.getCellHelper().resetCell(oldPosition);
        person.getCellHelper().newCellAt(newPosition);
    }

    public void walk() {
        Point2d newPosition = newPoint();
        Point2d oldPosition = person.getPosition();
        if (isValidMove(newPosition)) {
            if (!person.getPosLock().aquirePositionLock(newPosition)) {
                attemptAlternateMoves(oldPosition);
            } else {
                this.move(newPosition);
                person.getPosLock().releasePositionLock(oldPosition);
            }
        }
    }

    private boolean isValidMove(Point2d newPosition) {
        return newPosition != null && newPosition.properCoordinates(Board.SIZE) && !person.getPosition().equals(newPosition);
    }
    private void attemptAlternateMoves(Point2d oldPosition) {
        for(int i = 0; i < person.MAX_DEPTH; i++) {
            Point2d alternatePosition = generateRandomPosition(person.getPosition());
            if (isValidMove(alternatePosition) && person.getPosLock().aquirePositionLock(alternatePosition)) {
                this.move(alternatePosition);
                person.getPosLock().releasePositionLock(oldPosition);
                break;
            }
        }
    }

    public Point2d newPoint() {//here it should be abstract
        Point2d directionVecField = calculateDirectionVecField();
        Point2d newFieldPos = calculateNewFieldPos(directionVecField);
        Point2d closestPersonPos = person.findClosestPosition();
        if (closestPersonPos == null) return newFieldPos;
        Point2d directionVecPerson = calculateDirectionVecPerson(closestPersonPos);
        if (shouldReturnNewFieldPos(closestPersonPos)) return newFieldPos;
        return calculateNewPosition(person.getPosition(), directionVecPerson);
    }

    protected Point2d calculateDirectionVecField() {
        Point2d vecField = closestField(person);
        if (vecField != null){
            return Calculator.calculateDirection(person.getPosition(), vecField);
        }
        return null;
    }

    protected Point2d calculateNewFieldPos(Point2d directionVecField) {
        if (directionVecField != null) {
            return calculateNewPosition(person.getPosition(), directionVecField);
        }
        return null;
    }

    protected abstract Point2d calculateDirectionVecPerson(Point2d closestPersonPos);

    protected abstract  boolean shouldReturnNewFieldPos(Point2d closestPersonPos);

    protected Stream<Point2d> streamFilteredFields() {
        Point2d personPosition = person.getPosition();
        Map<String, Point2d> fields = person.getBoardRef().getBoard().getFields();
        return fields.values().stream()
                .filter(vector -> !vector.equals(personPosition));
    }


    public Point2d closestField(Person person) {
        Stream<Point2d> fieldsStream = streamFilteredFields();
        fieldsStream = fieldsStream.filter(vector -> vector.getMembership() != null && !vector.getMembership().equals(person.getColony()));
        Point2d newPosition = fieldsStream.min(Comparator.comparing(vector -> vector.distanceTo(person.getPosition())))
                .orElse(null);
        if(newPosition == null) newPosition = generateRandomPosition(person.getPosition());
        return newPosition;
    }


    @Override
    public Point2d calculateNewPosition(Point2d position, Point2d directionVector) {
        Point2d newPosition = position.addVector(directionVector);
        if(!newPosition.properCoordinates(Board.SIZE)) newPosition = generateRandomPosition(position);
        if (person.getBoardRef().isFieldOccupied(newPosition))
            newPosition = person.getBoardRef().getVectorFromBoard(newPosition);
        return newPosition;
    }

    public Point2d generateRandomPosition(Point2d position) {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        while(!position
                .addVector(randomDirection.getVector())
                .properCoordinates(Board.SIZE)){
            randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
            ThreadUtils.getInstance().pause(200);
        }
        Point2d directionVector = randomDirection.getVector();
        return calculateNewPosition(position, directionVector);
    }


}

