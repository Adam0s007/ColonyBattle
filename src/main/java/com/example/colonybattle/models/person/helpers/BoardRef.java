package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.ui.frame.MyFrame;

import javax.swing.*;
import java.util.List;

public class BoardRef {
    Person person;

    public BoardRef(Person person){
        this.person = person;
    }

    public Board getBoard() {
        if(person.getColony() == null)
            return null;
        return person.getColony().getBoard();
    }

    //funkcja sprawdzająca, czy w Bardzie w field znajduje się dany Vector2d
    public boolean isFieldOccupied(Point2d field){
        return getBoard() != null ? getBoard().isFieldOccupied(field.toString()) : false;
    }
    public Point2d getVectorFromBoard(Point2d field){
        return getBoard() != null ? getBoard().getPoint2d(field.toString()) : null;
    }

    void addVectorToBoard(Point2d vector){
        if(getBoard() != null){
            if(getBoard().getFields().containsKey(vector.toString()))
                return;
            getBoard().getLockManager().initializeLock(vector);
            getBoard().getFields().put(vector.toString(),vector);
        }
    }

    public boolean isFieldAccessible(Point2d vector){
        if(!getBoard().getObstacleFields().containsKey(vector.toString()))
            return true;
        return false;
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
        getFrame().getInfoPanel().getColonyPanel().update(this.person);
    }
    public void updateColonyPoints(){
        getFrame().getInfoPanel().getColonyPanel().updateColonyPoints(this.person);
    }

    public void removePersonFromFrame(){
        getFrame().getInfoPanel().getColonyPanel().removePersonLabel(this.person);
    }

    public void updateRankingPanel() {
        SwingUtilities.invokeLater(() -> this.getBoard().getFrame().getInfoPanel().getRankingPanel().update());
    }


}
