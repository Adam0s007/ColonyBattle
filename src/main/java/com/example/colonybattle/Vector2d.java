package com.example.colonybattle;

import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.Person;

import java.awt.*;

public class Vector2d {
    private int x;
    private int y;
    private Colony membership = null;//okresla przynaleznosc do kolonii
    private final int INIT_APPROPRIATION; //okresla stopien przynaleznosci do kolonii
    private int currentAppropriation = 0;
    private Person person;

    public Vector2d(int x, int y, int INIT_APPROPRIATION) {
        this.x = x;
        this.y = y;
        this.INIT_APPROPRIATION = INIT_APPROPRIATION;
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = null;
    }
    public Vector2d(int x, int y, int INIT_APPROPRIATION, Person person) {//wiemy ze osoba bedzie miec unikalną pozycje na poczatku
        this.x = x;
        this.y = y;
        this.INIT_APPROPRIATION = INIT_APPROPRIATION;
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = person;
        if(person != null)
            changeMembership(person);
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.membership = null;
        this.INIT_APPROPRIATION = 10;
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = null;
    }


    public Vector2d addVector(Vector2d vector) {
        return new Vector2d(this.x + vector.x, this.y + vector.y, this.INIT_APPROPRIATION);
    }
    // odejmij vector
    public Vector2d subtractVector(Vector2d vector) {
        return new Vector2d(this.x - vector.x, this.y - vector.y, this.INIT_APPROPRIATION);
    }

    public Vector2d oppositeVector() {
        return new Vector2d(-this.x, -this.y, this.INIT_APPROPRIATION);
    }

    // Override hashcode oraz equals
    @Override
    public int hashCode() {
        return this.x*31 + this.y;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Colony getMembership() {
        return membership;
    }


    public int getINIT_APPROPRIATION() {
        return INIT_APPROPRIATION;
    }



    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
        changeMembership(person);
    }
    public void addPerson(Person person) {
        this.setPerson(person);
    }

    public Person popPerson(Person person) {
        if(this.person.equals(person)) {
            Person oldPerson = this.person;
            this.person = null;
            return oldPerson;
        }
        return null;
    }
    public Boolean containPerson(Person person) {
        return this.person.equals(person);
    }

    public boolean properCoordinates(int size) {
        return this.x >= 0 && this.x < size && this.y >= 0 && this.y < size;
    }

    public double distanceTo(Vector2d other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public void changeMembership(Person person) {
        int newAppropr = this.currentAppropriation - person.getStatus().getLandAppropriation();
        this.currentAppropriation = Math.max(0,newAppropr);
        if(this.currentAppropriation == 0) {
            this.membership = person.getColony();
            this.currentAppropriation = this.INIT_APPROPRIATION;
        }
    }
    public void changeMembershipForcefully(Person person){
        this.membership = person.getColony();
        this.currentAppropriation = this.INIT_APPROPRIATION;
    }

    public Color getColonyColor(){
        if(this.membership == null)
            return Color.BLACK;
        return ColorConverter.convertColor(this.membership.getColor().getColor());
    }



}

