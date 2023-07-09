package com.example.colonybattle.person;

import com.example.colonybattle.Board;
import com.example.colonybattle.Colony;
import com.example.colonybattle.Direction;
import com.example.colonybattle.Vector2d;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Person implements Runnable{
    protected int health;
    protected int energy;
    protected int strength;
    protected Vector2d position;
    protected Colony colony;
    protected int landAppropriation;

    public Person(int health, int energy, int strength, Vector2d position, Colony colony, int landAppropriation) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.position = position;
        this.colony = colony;
        this.landAppropriation = landAppropriation;
    }
    // konstruktor z domyslnymi parametrami i randomowym polozeniem
    public Person(){
        this.health = 100;
        this.energy = 100;
        this.strength = 10;
        this.position = new Vector2d(ThreadLocalRandom.current().nextInt(0, Board.SIZE), ThreadLocalRandom.current().nextInt(0, Board.SIZE));
        this.colony = null;
        this.landAppropriation = 1;
    }

    public void attack(){};

    public void walk() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();
        Vector2d newPosition = position.addVector(directionVector);

        // Sprawdzenie czy nowa pozycja jest w granicach planszy
        if (newPosition.getX() >= 0 && newPosition.getX() < Board.SIZE &&
                newPosition.getY() >= 0 && newPosition.getY() < Board.SIZE) {
            // Jeżeli tak, aktualizacja pozycji
            position = newPosition;
        }
        // Jeżeli nowa pozycja jest poza granicami planszy, person nie porusza się
    }
    @Override
    public void run(){

    };
    public void die(){};
    public void regenerate(){};
    public  void giveBirth(){};

    public Vector2d getPosition() {
        return position;
    }
}
