package com.example.colonybattle;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.BoardRef;
import com.example.colonybattle.person.Person;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Vector2d {
    private int x;
    private int y;
    private Colony membership;//okresla przynaleznosc do kolonii
    private int appropriationRate; //okresla stopien przynaleznosci do kolonii
    private Person person;



    public Vector2d(int x, int y, Colony membership, int appropriationRate) {
        this.x = x;
        this.y = y;
        this.membership = membership;
        this.appropriationRate = appropriationRate;
        this.person = null;
    }
    public Vector2d(int x, int y, Colony membership, int appropriationRate,Person person) {//wiemy ze osoba bedzie miec unikalną pozycje na poczatku
        this.x = x;
        this.y = y;
        this.membership = membership;
        this.appropriationRate = appropriationRate;
        this.person = person;
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.membership = null;
        this.appropriationRate = 10;
        this.person = null;
    }

    public Vector2d newVector(int x, int y) {
        return new Vector2d(x, y, this.membership, this.appropriationRate);
    }

    public Vector2d addVector(Vector2d vector) {
        return new Vector2d(this.x + vector.x, this.y + vector.y, this.membership, this.appropriationRate);
    }
    // odejmij vector
    public Vector2d subtractVector(Vector2d vector) {
        return new Vector2d(this.x - vector.x, this.y - vector.y, this.membership, this.appropriationRate);
    }

    public Vector2d oppositeVector() {
        return new Vector2d(-this.x, -this.y, this.membership, this.appropriationRate);
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

    public void setMembership(Colony membership) {
        this.membership = membership;
    }

    public int getAppropriationRate() {
        return appropriationRate;
    }

    public void setAppropriationRate(int appropriationRate) {
        this.appropriationRate = appropriationRate;
    }

    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
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




}

