package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.launcher.EndgameMonitor;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

public class ConnectionHelper {

    Person person;

    public ConnectionHelper(Person person){
        this.person = person;
    }
    public void connectColony(Colony colony){
        this.person.setColony(colony);
        if(this.person.getColony() != null)
            this.person.getColony().addPerson(this.person);
    }
    public void disconnectColony(){
        synchronized (EndgameMonitor.monitor) {
            if(this.person.getColony() != null){
                this.person.getColony().getPeopleManager().removePerson(this.person);
                // Check if this person's colony is empty
                if (this.person.getBoardRef().isColonyEmpty(this.person.getColony())) {
                    this.person.getBoardRef().removeDefeatedColony();
                    if(this.person.getBoardRef().isOnlyOneColonyLeft()){
                        this.person.getBoardRef().getLongestSurvivingColony();
                    }
                }
                EndgameMonitor.monitor.notifyAll();
            }
        }
    }


    public void changePosConnections(Point2d oldPosition, Point2d newPosition){
        if(oldPosition != null && oldPosition.containPerson(this.person))
            oldPosition.popPerson(this.person);
        if(newPosition != null)
            newPosition.addPerson(this.person);
        this.person.setPosition(newPosition);
    }

}
