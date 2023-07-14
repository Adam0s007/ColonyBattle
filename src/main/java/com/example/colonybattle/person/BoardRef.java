package com.example.colonybattle.person;

import com.example.colonybattle.Board;
import com.example.colonybattle.Direction;
import com.example.colonybattle.Vector2d;

import java.util.concurrent.ThreadLocalRandom;

public final class BoardRef {
    Person person;

    BoardRef(Person person){
        this.person = person;
    }

    Board getBoard() {
        return person.colony.getBoard();
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
        if (this.isFieldOccupied(newPosition)) {
            newPosition = this.getVectorFromBoard(newPosition);
        }
        return newPosition;
    }

    public Vector2d generateRandomPosition(Vector2d position) {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();
        return calculateNewPosition(position, directionVector);
    }
}
