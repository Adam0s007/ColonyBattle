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

    protected Vector2d position;

    protected Colony colony;
    protected int landAppropriation;
    private volatile boolean running = true;



    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Person))
            return false;
        Person that = (Person) other;
        return this.id == that.id;
    }
    //toString niech wypisze "osoba o id ..." i tyle
    @Override
    public String toString() {
        return "Person[" + "id=" + id + ']';
    }


    public Person(int health, int energy, int strength, Vector2d position, Colony colony, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.id = id;
        changePosConnections(null,position);
        connectColony(colony);
        //gui changes
        newCellAt(this.position);
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
        newCellAt(this.position);
    }
    public Board getBoard() {
        return this.colony.getBoard();
    }

    //funkcja sprawdzająca, czy w Bardzie w field znajduje się dany Vector2d
    public boolean isFieldOccupied(Vector2d field){
        return this.getBoard().isFieldOccupied(field.toString());
    }
    public Vector2d getVectorFromBoard(Vector2d field){
        return this.getBoard().getVector2d(field.toString());
    }


    public void setPosition(Vector2d position) {
        this.position = position;
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




    private void move(Vector2d newPosition) {
        Vector2d oldPosition = this.position;
        changePosConnections(oldPosition,newPosition);

        //gui changes
        resetCell(oldPosition);
        newCellAt(newPosition);
    }


    private void changePosConnections(Vector2d oldPosition,Vector2d newPosition){
        if(oldPosition != null && oldPosition.containPerson(this))
            oldPosition.popPerson(this);
        newPosition.addPerson(this);
        this.position = newPosition;
    }


    public void resetCell(Vector2d position){
        Engine.getFrame().setInitColor(position);
        Engine.getFrame().setLifeAtPosition(position, 0);
        Engine.getFrame().setInitial(position, ' ');
    }

    public void newCellAt(Vector2d newPosition){
        Color color = this.colony.getColor().getColor(); // Zakładamy, że Colony ma metodę getColor() zwracającą kolor kolonii
        Engine.getFrame().setColorAtPosition(newPosition, ColorConverter.convertColor(color));
        Engine.getFrame().setLifeAtPosition(newPosition, health); // Ustawiamy aktualną ilość życia osoby
        Engine.getFrame().setInitial(position, this.getInitial());
    }


    public void walk() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();

        Vector2d newPosition = position.addVector(directionVector);//tworzy nowy Vector
        if(isFieldOccupied(newPosition))
            newPosition = this.getVectorFromBoard(newPosition);
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
            getBoard().getLockManager().acquireLock(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void releasePositionLock(Vector2d position){
        getBoard().getLockManager().releaseLock(position);
    }
    @Override
    public void run(){
        aquirePositionLock(position);
        while(running){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(800, 1500));
                walk();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    public void die(){
        this.changePosConnections(this.position,null);
        this.disconnectColony();
        resetCell(this.position);
        releasePositionLock(this.position);
        this.stop();
    }
    public  void  attack(){};
    public void regenerate(){};
    public  void giveBirth(){};

    public abstract Character getInitial(); // Nowa metoda zwracająca inicjały osoby

    public Vector2d getPosition() {
        return position;
    }

    public void stop() {
        resetCell(this.position);
        running = false;
    }


    public Colony getColony() {
        return colony;
    }


    public void setColony(Colony colony) {
        this.colony = colony;
    }

}
