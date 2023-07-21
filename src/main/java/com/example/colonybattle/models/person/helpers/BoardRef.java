package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.type.PersonType;
import com.example.colonybattle.ui.MyFrame;
import com.example.colonybattle.utils.ThreadUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class BoardRef {
    Person person;

    public BoardRef(Person person){
        this.person = person;
    }

    public Board getBoard() {
        return person.getColony().getBoard();
    }

    //funkcja sprawdzająca, czy w Bardzie w field znajduje się dany Vector2d
    public boolean isFieldOccupied(Point2d field){
        return getBoard() != null ? getBoard().isFieldOccupied(field.toString()) : false;
    }
    public Point2d getVectorFromBoard(Point2d field){
        return getBoard() != null ? getBoard().getVector2d(field.toString()) : null;
    }

    void addVectorToBoard(Point2d vector){
        if(getBoard() != null){
            if(getBoard().getFields().containsKey(vector.toString()))
                return;
            getBoard().getLockManager().initializeLock(vector);
            getBoard().getFields().put(vector.toString(),vector);
        }
    }

    public Point2d calculateNewPosition(Point2d position, Point2d directionVector) {
        Point2d newPosition = position.addVector(directionVector);
        if(!newPosition.properCoordinates(Board.SIZE)) newPosition = generateRandomPosition(position);
        if (this.isFieldOccupied(newPosition))
            newPosition = this.getVectorFromBoard(newPosition);
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


    //function returning all Colonies from com.example.colonyBattle/Board
    public List<Colony> getAllColonies() {
        return getBoard() != null ? getBoard().getAllColonies() : null;
    }
    // reference to the getLongestSurvivingColony method
    public void getLongestSurvivingColony() {
        if(getBoard() != null)
            getBoard().getLongestSurvivingColony();
    }

    // reference to the defeatedColony method
    public void removeDefeatedColony() {
        if (getBoard() != null) {
            getBoard().removeDefeatedColony();
        }
    }

    // reference to the isOnlyOneColonyLeft method
    public boolean isOnlyOneColonyLeft() {
        return getBoard() != null && getBoard().isOnlyOneColonyLeft();
    }
    public boolean isColonyEmpty(Colony colony) {
        return getBoard() != null ? getBoard().isColonyEmpty(colony) : true;
    }
    public Point2d closestField(Person person) {
        Colony personColony = person.getColony();
        Point2d personPosition = person.getPosition();
        Map<String, Point2d> fields = getBoard().getFields();
        Stream<Point2d> fieldsStream = fields.values().stream()
                .filter(vector -> !vector.equals(personPosition));
        if (person.getType() == PersonType.FARMER) {
            fieldsStream = fieldsStream.filter(vector -> vector.getMembership() == null || !vector.getMembership().equals(personColony));
        } else {
            fieldsStream = fieldsStream.filter(vector -> vector.getMembership() != null && !vector.getMembership().equals(personColony));
        }
        Point2d newPosition = fieldsStream.min(Comparator.comparing(vector -> vector.distanceTo(personPosition)))
                .orElse(null);
        if(newPosition == null) newPosition = this.generateRandomPosition(personPosition);
        return newPosition;
    }

    public MyFrame getFrame(){
        return this.getBoard().getFrame();
    }

}
