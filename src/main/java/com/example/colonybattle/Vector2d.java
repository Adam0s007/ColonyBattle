package com.example.colonybattle;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.Person;

import java.util.HashSet;
import java.util.Set;

public class Vector2d {
    private int x;
    private int y;
    private Colony membership;//okresla przynaleznosc do kolonii
    private int appropriationRate; //okresla stopien przynaleznosci do kolonii
    private Set<Person> people = new HashSet<>(); //wskazanie na osoby na tym polu



    public Vector2d(int x, int y, Colony membership, int appropriationRate) {
        this.x = x;
        this.y = y;
        this.membership = membership;
        this.appropriationRate = appropriationRate;
        this.people = new HashSet<>();
    }
    public Vector2d(int x, int y, Colony membership, int appropriationRate,Person people) {//wiemy ze osoba bedzie miec unikalną pozycje na poczatku
        this.x = x;
        this.y = y;
        this.membership = membership;
        this.appropriationRate = appropriationRate;
        this.people.add(people);
    }
    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.membership = null;
        this.appropriationRate = 10;
        this.people = new HashSet<>();
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

    public Set<Person> getPeople() {
        return people;
    }
    public void setPeople(Set<Person> people) {
        this.people = people;
    }
    public void addPerson(Person person) {
        this.people.add(person);
    }

    public Person popPerson(Person person) {
        this.people.remove(person);
        return person;
    }
    public Boolean containPerson(Person person) {
        return this.people.contains(person);
    }
    //funkcja sprawdzająca, czy osoby na tym polu są z dwóch roznych kolonii
    public Boolean isConflict() {
        if (this.people.size() > 1) {
            Person[] peopleArray = this.people.toArray(new Person[0]);
            return peopleArray[0].getColony().getType() != peopleArray[1].getColony().getType();
        }
        return false;
    }


}

