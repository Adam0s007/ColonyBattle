package com.example.colonybattle.board.position;

import com.example.colonybattle.utils.ColorConverter;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.type.PersonType;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Point2d {
    private int x;
    private int y;
    private Colony membership = null;//okresla przynaleznosc do kolonii
    private final int INIT_APPROPRIATION; //okresla stopien przynaleznosci do kolonii
    private int currentAppropriation = 0;
    private Person person;
    public Point2d(int x, int y, int INIT_APPROPRIATION) {
        this.x = x;
        this.y = y;
        this.INIT_APPROPRIATION = INIT_APPROPRIATION;
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = null;
    }
    public Point2d(int x, int y, int INIT_APPROPRIATION, Person person) {//wiemy ze osoba bedzie miec unikalną pozycje na poczatku
        this.x = x;
        this.y = y;
        this.INIT_APPROPRIATION = INIT_APPROPRIATION;
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = person;
        if(person != null)
            changeMembership(person);
    }
    public Point2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.membership = null;
        this.INIT_APPROPRIATION = PersonType.FARMER.getLandAppropriation();
        this.currentAppropriation = INIT_APPROPRIATION;
        this.person = null;
    }
    public Point2d addVector(Point2d vector) {
        return new Point2d(this.x + vector.x, this.y + vector.y, this.INIT_APPROPRIATION);
    }
    public Point2d subtractVector(Point2d vector) {
        return new Point2d(this.x - vector.x, this.y - vector.y, this.INIT_APPROPRIATION);
    }
    public Point2d oppositeVector() {
        return new Point2d(-this.x, -this.y, this.INIT_APPROPRIATION);
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
        if (!(other instanceof Point2d))
            return false;
        Point2d that = (Point2d) other;
        return this.x == that.x && this.y == that.y;
    }
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
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

    public double distanceTo(Point2d other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
    public synchronized void changeMembership(Person person) {
        adjustCurrentAppropriation(person);

        if (this.currentAppropriation == 0) {
            updateMembershipAndField(person, true);
        }
    }
    private void adjustCurrentAppropriation(Person person) {
        if (this.membership != person.getColony()) {
            this.currentAppropriation = Math.max(0, this.currentAppropriation - person.getStatus().getLandAppropriation());
        }
    }
    private void updateMembershipAndField(Person person, boolean rewardPoints) {
        Colony oldColony = this.membership;
        this.membership = (this.person.getType() == PersonType.FARMER) ? person.getColony() : null;

        if (this.membership != null) {
            this.membership.addField(this);
            if (rewardPoints) {
                person.addPoints(1);
            }
        }
        if (oldColony != null) {
            oldColony.removeField(this);
            if (rewardPoints) {
                person.addPoints(1);
            }
        }
    }
    public Color getColonyColor(){
        if(this.membership == null)
            return null;
        return ColorConverter.convertColor(this.membership.getColor().getColor());
    }
}

