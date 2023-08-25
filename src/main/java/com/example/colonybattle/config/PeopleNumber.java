package com.example.colonybattle.config;


import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PeopleNumber {

    private static PeopleNumber instance;
    private int farmerNumber = 1;
    private int defenderNumber = 1;
    private int warriorNumber = 1;
    private int wizardNumber = 1;
    private PeopleNumber() {}
    public static PeopleNumber getInstance() {
        if (instance == null) {
            instance = new PeopleNumber();
        }
        return instance;
    }
}

