package com.example.colonybattle.colony;

import com.example.colonybattle.colors.ColonyColor;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.board.Board;
import com.example.colonybattle.models.person.PersonFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class Colony {
    private ColonyColor color;
    private ColonyType type;
    private Set<Person> people;
    private Set<Point2d> fields;
    public final static int PERIOD_SEC = 30;
    private int points;
    public final PersonFactory personFactory;
    private Board board;
    private final Instant creationTime;
    public final Semaphore pointSemaphore;
    public Colony() {
        this.people = ConcurrentHashMap.newKeySet();
        this.fields =ConcurrentHashMap.newKeySet();
        this.points = 0;
        this.creationTime = Instant.now();
        this.personFactory = new PersonFactory();
        this.pointSemaphore = new Semaphore(1);

    }
    public Colony(ColonyType type, Set<Person> people, Set<Point2d> fields, int points, ColonyColor color, Board board, PersonFactory personFactory) {
        this.type = type;
        this.people = ConcurrentHashMap.newKeySet();
        this.fields =  ConcurrentHashMap.newKeySet();;
        this.people.addAll(people);
        this.fields.addAll(fields);
        this.points = points;
        this.color = color;
        this.board = board;
        this.personFactory = personFactory;
        this.creationTime = Instant.now();
        this.pointSemaphore = new Semaphore(1);
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
    public Set<Point2d> getFields() {
        return fields;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        try {
            this.pointSemaphore.acquire();
            this.points = Math.max(points,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.pointSemaphore.release();
        }

    }
    public void addPoints(int points) {
        try {
            this.pointSemaphore.acquire();
            this.points = Math.max(points+this.points,0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            this.pointSemaphore.release();
        }
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
    public Person containsPerson(Point2d position) {
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
        return  ""+type;
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
    public void addField(Point2d position) {
        fields.add(position);
    }
    public void removeField(Point2d position) {
        fields.remove(position);
    }
    public Point2d getFreeField(){
        return this.fields.stream()
                .filter(field -> field.getPerson() == null)
                .findAny().orElse(null);
    }
    public void spawnPerson() {

            Point2d freeField;
            synchronized(this) { // Obtain lock on this Colony
                freeField = getFreeField();
                if(freeField != null && getBoard().getLockManager().tryAcquireLock(freeField)){
                        Person newPerson = personFactory.generateRandom(this,freeField); // Create a new Person using the factory
                        System.out.println("new person: " + newPerson);
                        if(newPerson != null){
                            newPerson.isNew = true;
                            this.getBoard().executorService.submit(newPerson); // Submit the new Person to the ExecutorService
                        }else{
                            getBoard().getLockManager().releaseLock(freeField);
                        }

                } // Try to acquire lock on the free field (if it is not locked by another thread
            }
    }
}
