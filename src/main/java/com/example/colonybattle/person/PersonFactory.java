package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.ConcreteCharacters.Defender;
import com.example.colonybattle.person.ConcreteCharacters.Farmer;
import com.example.colonybattle.person.ConcreteCharacters.Warrior;
import com.example.colonybattle.person.ConcreteCharacters.Wizard;

public class PersonFactory {
    private static int available_id = 0;

    public static int incrementId(){
        return available_id++;
    }

    public Person createPerson(PersonType type, Vector2d pos, Colony colony){
        switch (type) {
            case FARMER:
                return new Farmer(type, pos, colony, incrementId());
            case DEFENDER:
                return new Defender(type, pos, colony, incrementId());
            case WARRIOR:
                return new Warrior(type, pos, colony, incrementId());
            case WIZARD:
                return new Wizard(type, pos, colony, incrementId());
            default:
                System.out.println("Wrong person type");
                return null;
        }
    }
}

