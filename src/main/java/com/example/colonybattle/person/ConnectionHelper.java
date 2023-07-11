package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.Colony;

public class ConnectionHelper {

    Person person;

    ConnectionHelper(Person person){
        this.person = person;
    }
    public void connectColony(Colony colony){
        this.person.colony = colony;
        if(this.person.colony != null)
            this.person.colony.addPerson(this.person);
    }
    public void disconnectColony(){
        if(this.person.colony != null)
            this.person.colony.removePerson(this.person);
        this.person.colony = null;
    }

    public void changePosConnections(Vector2d oldPosition, Vector2d newPosition){
        if(oldPosition != null && oldPosition.containPerson(this.person))
            oldPosition.popPerson(this.person);
        newPosition.addPerson(this.person);
        this.person.position = newPosition;
    }

}
