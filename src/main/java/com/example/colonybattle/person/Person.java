package com.example.colonybattle.person;

import com.example.colonybattle.*;

import com.example.colonybattle.Colors.Color;
import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.LockManagement.LockMapPosition;
import com.example.colonybattle.UI.Cell;
import com.example.colonybattle.colony.Colony;

import java.util.concurrent.ThreadLocalRandom;


public abstract class Person implements Runnable{

    protected Vector2d position;

    protected Colony colony;
    private volatile boolean running = true;
    protected PersonStatus status;
    protected CellHelper cellHelper;
    protected BoardRef boardRef;
    @Override
    public int hashCode() {
        return this.status.id;
    }
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Person))
            return false;
        Person that = (Person) other;
        return this.status.id == that.status.id;
    }
    @Override
    public String toString() {
        return "Person[" + "id=" + this.status + ']';
    }
    public Person(int health, int energy, int strength, Vector2d position, Colony colony, int landAppropriation,int id) {
        this.status = new PersonStatus(health,energy,strength,landAppropriation,id);
        this.cellHelper = new CellHelper(this);
        this.boardRef = new BoardRef(this);
        changePosConnections(null,position);
        connectColony(colony);
        cellHelper.newCellAt(this.position);
    }
    public Person(){
        this.position = new Vector2d(ThreadLocalRandom.current().nextInt(0, Board.SIZE), ThreadLocalRandom.current().nextInt(0, Board.SIZE));
        this.status = new PersonStatus(20,20,20,20,-1);
        this.cellHelper = new CellHelper(this);
        this.boardRef = new BoardRef(this);
        connectColony(null);
        cellHelper.newCellAt(this.position);
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
        cellHelper.resetCell(oldPosition);
        cellHelper.newCellAt(newPosition);
    }
    private void changePosConnections(Vector2d oldPosition,Vector2d newPosition){
        if(oldPosition != null && oldPosition.containPerson(this))
            oldPosition.popPerson(this);
        newPosition.addPerson(this);
        this.position = newPosition;
    }
    public void walk() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();

        Vector2d newPosition = position.addVector(directionVector);//tworzy nowy Vector
        if(boardRef.isFieldOccupied(newPosition))
            newPosition = boardRef.getVectorFromBoard(newPosition);
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
            boardRef.getBoard().getLockManager().acquireLock(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void releasePositionLock(Vector2d position){
        boardRef.getBoard().getLockManager().releaseLock(position);
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
        cellHelper.resetCell(this.position);
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
        cellHelper.resetCell(this.position);
        running = false;
    }
    public Colony getColony() {
        return colony;
    }
    public void setColony(Colony colony) {
        this.colony = colony;
    }

}
