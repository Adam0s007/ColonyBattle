package com.example.colonybattle.person;

public enum PeopleNumber {
    FARMER_NUMBER(3),
    DEFENDER_NUMBER(2),
    WARRIOR_NUMBER(4),
    WIZARD_NUMBER(1);

    private final int number;

    PeopleNumber(int number){
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
