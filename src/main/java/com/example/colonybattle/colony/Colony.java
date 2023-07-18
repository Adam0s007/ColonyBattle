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
import java.util.concurrent.*;

public class Colony {

    private ColonyColor color;
    private ColonyType type;
    private Set<Person> people;
    private Set<Vector2d> fields;
    private final int PERIOD_SEC = 60;
    private int points;
    public final PersonFactory personFactory;
    private Board board;
    private final Instant creationTime;
    private final ScheduledExecutorService executorService;
    public Colony() {
        this.people = ConcurrentHashMap.newKeySet();
        this.fields =ConcurrentHashMap.newKeySet();
        this.points = 0;
        this.creationTime = Instant.now();
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.personFactory = new PersonFactory();

    }

    public Colony(ColonyType type,Set<Person> people, Set<Vector2d> fields, int points, ColonyColor color, Board board,PersonFactory personFactory) {
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
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        spawnPerson();


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

    public void addField(Vector2d position) {
        fields.add(position);
    }
    public void removeField(Vector2d position) {
        fields.remove(position);
    }


    public Vector2d getFreeField(){
        return this.fields.stream()
                .filter(field -> field.getPerson() == null)
                .findAny().orElse(null);
    }

    public void spawnPerson() {
        Runnable task = () -> {
            Vector2d freeField;
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
        };
        executorService.scheduleAtFixedRate(task, 0, PERIOD_SEC, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Executor service did not terminate in the allotted time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
