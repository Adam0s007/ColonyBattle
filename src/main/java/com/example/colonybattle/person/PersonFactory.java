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

    public Person createPerson(String type, Vector2d pos, Colony colony){
        switch (type.toLowerCase()) {
            case "farmer":
                return new Farmer(8, 8, 5, pos, colony, incrementId());

            case "defender":
                return new Defender(14, 12, pos, colony, 10, incrementId());

            case "warrior":
                return new Warrior(12, 15, pos, colony, 8, incrementId());

            case "wizard":
                return new Wizard(14, 15, pos, colony, 14, incrementId());

            default:
                System.out.println("Wrong person type");
                return null;
        }
    }
}

