package com.example.colonybattle.person;

import com.example.colonybattle.Board;
import com.example.colonybattle.Vector2d;

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
        return getBoard().isFieldOccupied(field.toString());
    }
    public Vector2d getVectorFromBoard(Vector2d field){
        return getBoard().getVector2d(field.toString());
    }

    void addVectorToBoard(Vector2d vector){
        getBoard().getFields().put(vector.toString(),vector);
    }
}
