package com.example.colonybattle.person;

import com.example.colonybattle.Board;

import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.Colony;

import java.util.concurrent.ThreadLocalRandom;

public class PersonFactory {
    private static int available_id = 0;

    public static int incrementId(){
        return available_id++;
    }

    public Person createPerson(PersonType type, Vector2d pos, Colony colony){
        switch (type) {
            case FARMER:
                return new Farmer(type.getHealth(), type.getEnergy(), type.getStrength(), pos, colony, incrementId());
            case DEFENDER:
                return new Defender(type.getHealth(), type.getEnergy(), pos, colony, type.getStrength(), incrementId());
            case WARRIOR:
                return new Warrior(type.getHealth(), type.getEnergy(), pos, colony, type.getStrength(), incrementId());
            case WIZARD:
                return new Wizard(type.getHealth(), type.getEnergy(), pos, colony, type.getStrength(), incrementId());
            default:
                System.out.println("Wrong person type");
                return null;
        }
    }
}

