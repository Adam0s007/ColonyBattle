package com.example.colonybattle.person;

import com.example.colonybattle.*;

import com.example.colonybattle.Colors.Color;
import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.LockManagement.LockMapPosition;
import com.example.colonybattle.colony.Colony;

import java.util.concurrent.ThreadLocalRandom;


public abstract class Person implements Runnable{
    protected int health;
    protected int energy;
    protected int strength;
    protected int id;

    private static final LockMapPosition lockManager = new LockMapPosition(Board.SIZE);


    public void setPosition(Vector2d position) {
        this.position = position;
    }

    protected Vector2d position;
    protected Colony colony;
    protected int landAppropriation;
    private volatile boolean running = true;

    public Person(int health, int energy, int strength, Vector2d position, Colony colony, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.position = position;
        connectColony(colony);
        this.landAppropriation = landAppropriation;
        this.id = id;
        newColorAt(this.position);
    }

    // konstruktor z domyslnymi parametrami i randomowym polozeniem
    public Person(){
        this.health = 20;
        this.energy = 20;
        this.strength = 20;
        this.position = new Vector2d(ThreadLocalRandom.current().nextInt(0, Board.SIZE), ThreadLocalRandom.current().nextInt(0, Board.SIZE));
        this.landAppropriation = 20;
        this.id = -1;
        connectColony(null);
        newColorAt(this.position);
    }

    public void connectColony(Colony colony){
        this.colony = colony;
        if(this.colony != null)
            this.colony.addPerson(this);
    }
    public void disconnectColony(){
        if(this.colony != null)
            this.colony.removePerson(this);
        this.colony = null;
    }

    public  void  attack(){};


    private void move(Vector2d newPosition) {
        // Aktualizujemy referencję do komórki
        resetColor(this.position);
        // Aktualizujemy pozycję
        this.position = newPosition;
        // Aktualizujemy kolor komórki na planszy
        newColorAt(newPosition);
    }

    public void resetColor(Vector2d position){
        Engine.getFrame().setInitColor(position);
    }

    public void newColorAt(Vector2d newPosition){
        Color color = this.colony.getColor().getColor(); // Zakładamy, że Colony ma metodę getColor() zwracającą kolor kolonii
        Engine.getFrame().setColorAtPosition(newPosition, ColorConverter.convertColor(color));
    }


    public void walk() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();
        Vector2d newPosition = position.addVector(directionVector);

        // Sprawdzenie czy nowa pozycja jest w granicach planszy
        if (newPosition != null && newPosition.getX() >= 0 && newPosition.getX() < Board.SIZE &&
                newPosition.getY() >= 0 && newPosition.getY() < Board.SIZE && this.position.equals(newPosition) == false) {
            Vector2d oldPosition = this.position;
            aquirePositionLock(newPosition);
            this.move(newPosition);
            releasePositionLock(oldPosition);
        }

    }
    protected void aquirePositionLock(Vector2d position){
        try {
            lockManager.acquireLock(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void releasePositionLock(Vector2d position){
        lockManager.releaseLock(position);
    }
    @Override
    public void run(){
        aquirePositionLock(position);
        while(running){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(200, 300));
                walk();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    public void die(){
        resetColor(this.position);
        this.disconnectColony();
        this.stop();
    }
    public void regenerate(){};
    public  void giveBirth(){};

    public Vector2d getPosition() {
        return position;
    }

    public void stop() {
        resetColor(this.position);
        running = false;
    }


    public Colony getColony() {
        return colony;
    }


    public void setColony(Colony colony) {
        this.colony = colony;
    }

}
