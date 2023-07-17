package com.example.colonybattle.colony;

import com.example.colonybattle.Colors.ColonyColor;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.Board;
import com.example.colonybattle.person.PersonFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Colony {

    private ColonyColor color;
    private ColonyType type;
    private Set<Person> people;
    private Map<String,Vector2d> fields;
    private int points;
    private PersonFactory personFactory;

    private Board board;
    private final Instant creationTime;

    public Colony() {
        this.people = ConcurrentHashMap.newKeySet();
        this.fields = new ConcurrentHashMap<>();
        this.points = 0;
        this.creationTime = Instant.now();
    }

    public Colony(ColonyType type,Set<Person> people, Map<String,Vector2d> fields, int points, ColonyColor color, Board board,PersonFactory personFactory) {
        this.type = type;
        this.people = ConcurrentHashMap.newKeySet();
        this.fields = fields;
        this.people.addAll(people);
        this.points = points;
        this.color = color;
        this.board = board;
        this.personFactory = personFactory;
        this.creationTime = Instant.now();

    }
    public ColonyColor getColor() {
        return color;
    }
    public void setColor(ColonyColor color) {
        this.color = color;
    }
    public Set<Person> getPeople() {
        return people;
    }
    public Map<String,Vector2d> getFields() {
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

    public int getPeopleCount() {
        return people.size();
    }
    public Duration getLifetime() {
        return Duration.between(creationTime, Instant.now());
    }

    public void addField(String name, Vector2d position) {
        fields.put(name,position);
    }
    public void removeField(String name) {
        fields.remove(name);
    }
    public boolean includesField(String name) {
        return fields.containsKey(name);
    }


    public Vector2d getFreeField(){
        return this.fields.values().stream()
                .filter(field -> field.getPerson() == null)
                .findAny().orElse(null);
    }
    public void spawnPerson(){
        new Thread(() -> {
            while(true) {
                Vector2d freeField;
                synchronized(this) { // Obtain lock on this Colony
                    while((freeField = getFreeField()) == null) {
                        try {
                            wait(); // This will wait until notifyAll() is called in Vector2d
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Person newPerson = personFactory.generateRandom(this,freeField); // Create a new Person using the factory
                // Set the new person's position to the freeField
                // ...
                try {
                    Thread.sleep(10000); // Wait for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


}
