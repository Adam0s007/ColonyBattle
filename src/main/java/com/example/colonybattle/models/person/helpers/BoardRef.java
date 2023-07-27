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


    public MyFrame getFrame(){
        return this.getBoard().getFrame();
    }

    public void updateColonyFrame(){
        getFrame().getInfoPanel().getColonyPanel().updateColonies();
    }

}
