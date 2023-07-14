package com.example.colonybattle.colony;

import com.example.colonybattle.Colors.ColonyColor;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.Board;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Colony {

    private ColonyColor color;
    private ColonyType type;
    private Set<Person> people;
    private Set<Vector2d> fields;
    private int points;

    private Board board;

    public ColonyColor getColor() {
        return color;
    }

    public void setColor(ColonyColor color) {
        this.color = color;
    }

    public Set<Person> getPeople() {
        return people;
    }

    public Set<Vector2d> getFields() {
        return fields;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getTotalPeopleCount() {
        return people.size();
    }

    public void addPeople(Set<Person> people) {
        this.people.addAll(people);
        for (Person person : people) {
            person.setColony(this);
        }
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }

    public Colony() {
        this.people = ConcurrentHashMap.newKeySet();
        this.fields = ConcurrentHashMap.newKeySet();
        this.points = 0;
    }

    public Colony(ColonyType type,Set<Person> people, Set<Vector2d> fields, int points, ColonyColor color, Board board) {
        this.type = type;
        this.people = ConcurrentHashMap.newKeySet();
        this.fields = ConcurrentHashMap.newKeySet();
        this.people.addAll(people);
        this.fields.addAll(fields);
        this.points = points;
        this.color = color;
        this.board = board;
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

    public ColonyColor getColonyColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Colony[" +
                "type=" + type +
                ']';
    }
    //equal function override
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Colony)) return false;
        Colony colony = (Colony) o;
        return type == colony.type;
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
