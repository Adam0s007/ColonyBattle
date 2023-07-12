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
    protected ConnectionHelper connectionHelper;
    protected PosLock posLock;
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
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.changePosConnections(null,position);
        connectionHelper.connectColony(colony);
        posLock = new PosLock(boardRef);
        cellHelper.newCellAt(this.position);
    }
    public Person(){
        this.position = new Vector2d(ThreadLocalRandom.current().nextInt(0, Board.SIZE), ThreadLocalRandom.current().nextInt(0, Board.SIZE));
        this.status = new PersonStatus(20,20,20,20,-1);
        this.cellHelper = new CellHelper(this);
        this.boardRef = new BoardRef(this);
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.connectColony(null);
        posLock = new PosLock(boardRef);
        cellHelper.newCellAt(this.position);
    }
    public void setPosition(Vector2d position) {
        this.position = position;
    }

    private void move(Vector2d newPosition) {
        Vector2d oldPosition = this.position;
        connectionHelper.changePosConnections(oldPosition,newPosition);
        cellHelper.resetCell(oldPosition);
        cellHelper.newCellAt(newPosition);
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
            posLock.aquirePositionLock(newPosition);
            this.move(newPosition);
            posLock.releasePositionLock(oldPosition);
        }

    }

    @Override
    public void run(){
        posLock.aquirePositionLock(position);
        while(running){
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(800, 1500));
                walk();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // jesli wylosuje liczbe 3 z zakresu od 1 do 5 to die()
//            if (ThreadLocalRandom.current().nextInt(1, 5) == 3) {
//                die();
//            }

        }
    };
    public void die(){
        Vector2d oldPosition = new Vector2d(this.position.getX(),this.position.getY());
        connectionHelper.changePosConnections(this.position,null);
        connectionHelper.disconnectColony();
        cellHelper.resetCell(oldPosition);
        posLock.releasePositionLock(oldPosition);
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
        running = false;
    }
    public Colony getColony() {
        return colony;
    }
    public void setColony(Colony colony) {
        this.colony = colony;
    }

}
