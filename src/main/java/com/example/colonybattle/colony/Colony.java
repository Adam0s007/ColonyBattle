package com.example.colonybattle.colony;

import com.example.colonybattle.colors.ColonyColor;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.board.Board;
import com.example.colonybattle.models.person.PersonFactory;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

@Getter
@Setter
public class Colony {
    private ColonyColor color = ColonyColor.DEFAULT_COLOR;
    private ColonyType type;
    public final static int PERIOD_SEC = 30;
    private final Instant creationTime = Instant.now();;
    public final PersonFactory personFactory;
    private Board board;
    private PointsManager pointsManager;
    private PeopleManager peopleManager;
    private FieldsManager fieldsManager;

    public Colony() {
        this.personFactory = new PersonFactory();
        this.pointsManager = new PointsManager();
        this.peopleManager = new PeopleManager();
        this.fieldsManager = new FieldsManager();
    }
    public Colony(ColonyType type, Set<Person> people, Set<Point2d> fields, int points, ColonyColor color, Board board, PersonFactory personFactory) {
        this.type = type;
        this.color = color;
        this.board = board;
        this.personFactory = personFactory;
        this.pointsManager = new PointsManager(points);
        this.peopleManager = new PeopleManager(people);
        this.fieldsManager = new FieldsManager(fields);
    }
    public Duration getLifetime() {
        return Duration.between(creationTime, Instant.now());
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
    public void spawnPerson() {

            Point2d freeField;
            synchronized(this) { // Obtain lock on this Colony
                freeField = this.getFieldsManager().getFreeField();
                if(freeField != null && getBoard().getLockManager().tryAcquireLock(freeField)){
                        Person newPerson = personFactory.generateRandom(this,freeField); // Create a new Person using the factory
                        System.out.println("new person: " + newPerson);
                        if(newPerson != null){
                            newPerson.isNew = true;
                            this.getBoard().startPerson(newPerson); // Submit the new Person to the ExecutorService
                        }else{
                            getBoard().getLockManager().releaseLock(freeField);
                        }

                }
            }
    }
}
