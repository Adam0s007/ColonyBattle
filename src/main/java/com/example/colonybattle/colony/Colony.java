package com.example.colonybattle.colony;

import com.example.colonybattle.Colors.ColonyColor;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;

import java.util.HashSet;
import java.util.Set;

public class Colony {

    private ColonyColor color;
    private ColonyType type;

    public ColonyColor getColor() {
        return color;
    }

    public void setColor(ColonyColor color) {
        this.color = color;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public void setPeople(Set<Person> people) {
        this.people = people;
    }

    public Set<Vector2d> getFields() {
        return fields;
    }

    public void setFields(Set<Vector2d> fields) {
        this.fields = fields;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private Set<Person> people;
    private Set<Vector2d> fields;
    private int points;

    public int getTotalPeopleCount() {
        return people.size();
    }

    public synchronized void addPeople(Set<Person> people) {

        this.people.addAll(people);
        //kazda osoba musi miec wskaznik do tej kolonii
        for (Person person : people) {
            person.setColony(this);
        }
    }
    public synchronized void addPerson(Person person) {
        this.people.add(person);
        //person.setColony(this); - to jest robione w klasie Person
    }

    public synchronized void removePerson(Person person) {
        //person.setColony(null); - to jest robione w klasie Person
        this.people.remove(person);
    }

    public Colony() {
        this.people = new HashSet<>();
        this.fields = new HashSet<>();
        this.points = 0;
    }
    public Colony(ColonyType type,Set<Person> people, Set<Vector2d> fields, int points, ColonyColor color) {
        this.type = type;
        this.people = people;
        this.fields = fields;
        this.points = points;
        this.color = color;

    }

    public ColonyType getType() {
        return type;
    }

    public Person containsPerson(Vector2d position) {
        for (Person person : people) {
            if (person.getPosition().equals(position)) {
                return person;
            }
        }
        return null;
    }
    //getColonyColor

    public ColonyColor getColonyColor() {
        return color;
    }
}