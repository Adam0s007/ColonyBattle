package com.example.colonybattle;

import com.example.colonybattle.person.Person;

import java.util.Set;

public class Colony {
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

    // Konstruktor, gettery, settery
}