package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.helpers.BoardRef;
import com.example.colonybattle.models.person.messages.DestinationMessage;
import com.example.colonybattle.utils.Calculator;
import com.example.colonybattle.utils.ThreadUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public abstract class MovementStrategy implements Movement {
    protected final Person person;

    protected final BoardRef boardRef;
    public int directionIndex = 0;
    protected Point2d potentialTarget = null;
    public MovementStrategy(Person person) {
        this.person = person;
        this.boardRef = person.getBoardRef();
    }

    private void move(Point2d newPosition) {
        Point2d oldPosition = person.getPosition();
        person.getConnectionHelper().changePosConnections(oldPosition,newPosition);
        if(person.getCellHelper() == null) return;
        person.getCellHelper().resetCell(oldPosition);
        person.getCellHelper().newCellAt(newPosition);
    }

    public void walk() {
        processMessage();
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
        return newPosition != null &&
                newPosition.properCoordinates(Board.SIZE) &&
                !person.getPosition().equals(newPosition) &&
                person.getBoardRef().isFieldAccessible(newPosition);
    }
    private void attemptAlternateMoves(Point2d oldPosition) {
        while(true) {
            Point2d alternatePosition = generateNextPosition(person.getPosition());
            if (isValidMove(alternatePosition) && person.getPosLock().aquirePositionLock(alternatePosition)) {
                this.move(alternatePosition);
                person.getPosLock().releasePositionLock(oldPosition);
                break;
            }
        }
    }

    public Point2d newPoint() {//here it should be abstract

        Point2d closestPersonPos = null;
        closestPersonPos = potentialTargetVec();
        if(closestPersonPos != null) return closestPersonPos;
        Point2d directionVecField = calculateDirectionVecField();
        Point2d newFieldPos = calculateNewFieldPos(directionVecField);
        closestPersonPos = person.findClosestPosition();
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

    public Point2d potentialTargetVec(){
        if(this.potentialTarget != null && potentialTarget.equals(person.getPosition())) {
            this.potentialTarget = null;
        } else if(this.potentialTarget != null){
            Point2d dir = Calculator.calculateDirection(person.getPosition(), this.potentialTarget);
            return calculateNewPosition(person.getPosition(), dir);
        }
        return null;
    }

    public Point2d closestField(Person person) {
        Stream<Point2d> fieldsStream = streamFilteredFields();
        fieldsStream = fieldsStream.filter(vector -> vector.getMembership() != null && !vector.getMembership().equals(person.getColony()));
        Point2d newPosition = fieldsStream.min(Comparator.comparing(vector -> vector.distanceTo(person.getPosition())))
                .orElse(null);
        if(newPosition == null) newPosition = generateNextPosition(person.getPosition());
        return newPosition;
    }


    @Override
    public Point2d calculateNewPosition(Point2d position, Point2d directionVector) {
        Point2d newPosition = position.addVector(directionVector);
        if(!isValidMove(newPosition)) newPosition = generateNextPosition(position);
        if (person.getBoardRef().isFieldOccupied(newPosition))
            newPosition = person.getBoardRef().getVectorFromBoard(newPosition);
        return newPosition;
    }

    public Point2d generateNextPosition(Point2d position) {
        Direction[] directions = Direction.values();
        Direction randomDirection;
        do {
            randomDirection = directions[directionIndex++ % directions.length];
            if (directionIndex % directions.length == 0)
                ThreadUtils.getInstance().pause(200);
        } while(!position
                .addVector(randomDirection.getVector())
                .properCoordinates(Board.SIZE));

        Point2d directionVector = randomDirection.getVector();
        return calculateNewPosition(position, directionVector);
    }
    public void processMessage() {
        Point2d potentialTarget = person.getMessageHandler().processMessage();
        if(potentialTarget != null) {
            this.potentialTarget = potentialTarget;
        }
    }


}

