package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Direction;
import com.example.colonybattle.board.position.Vector2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardRef {
    Person person;

    public BoardRef(Person person){
        this.person = person;
    }

    public Board getBoard() {
        return person.getColony().getBoard();
    }

    //funkcja sprawdzająca, czy w Bardzie w field znajduje się dany Vector2d
    public boolean isFieldOccupied(Vector2d field){
        return getBoard() != null ? getBoard().isFieldOccupied(field.toString()) : false;
    }
    public Vector2d getVectorFromBoard(Vector2d field){
        return getBoard() != null ? getBoard().getVector2d(field.toString()) : null;
    }

    void addVectorToBoard(Vector2d vector){
        if(getBoard() != null){
            if(getBoard().getFields().containsKey(vector.toString()))
                return;
            getBoard().getLockManager().initializeLock(vector);
            getBoard().getFields().put(vector.toString(),vector);
        }
    }

    public Vector2d calculateNewPosition(Vector2d position, Vector2d directionVector) {
        Vector2d newPosition = position.addVector(directionVector);
        if(!newPosition.properCoordinates(Board.SIZE)) newPosition = generateRandomPosition(position);
        if (this.isFieldOccupied(newPosition))
            newPosition = this.getVectorFromBoard(newPosition);
        return newPosition;
    }

    public Vector2d generateRandomPosition(Vector2d position) {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        while(!position
                .addVector(randomDirection.getVector())
                .properCoordinates(Board.SIZE)){
            randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
            try{
                Thread.sleep(200);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
        Vector2d directionVector = randomDirection.getVector();
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
}
