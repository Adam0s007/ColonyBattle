package com.example.colonybattle.person;
import com.example.colonybattle.*;
import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.UI.ImageLoaderInterface;
import com.example.colonybattle.colony.Colony;

import javax.swing.*;
import java.util.Set;
import java.util.concurrent.*;


public abstract class Person implements Runnable{

    protected Vector2d position;

    protected Colony colony;
    private volatile boolean running = true;
    protected PersonStatus status;
    protected CellHelper cellHelper;
    protected BoardRef boardRef;
    protected ConnectionHelper connectionHelper;
    protected PosLock posLock;
    protected Attack attackPerformer;

    protected PersonType type;
    protected ImageIcon image;
    protected ImageLoaderInterface imageLoader;

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
        this.imageLoader = ImageLoader.getInstance();
        this.status = new PersonStatus(health,energy,strength,landAppropriation,id);
        this.boardRef = new BoardRef(this);
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.changePosConnections(null,position);
        connectionHelper.connectColony(colony);
        posLock = new PosLock(boardRef);
        attackPerformer = new Attack(this,boardRef);
    }

    public void initGUI(){
        this.cellHelper = new CellHelper(this);
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
        attackPerformer = new Attack(this,boardRef);
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
        Vector2d newPosition = position.generateRandomPosition(position, boardRef);
        if (newPosition != null && newPosition.properCoordinates(Board.SIZE) && this.position.equals(newPosition) == false) {
            Vector2d oldPosition = position;
            this.boardRef.addVectorToBoard(newPosition);
            if(!posLock.aquirePositionLock(newPosition)){
                walk();
            }
            else{
                this.move(newPosition);
                posLock.releasePositionLock(oldPosition);
            }
        }
    }

    @Override
    public void run(){
        initGUI();
        posLock.aquirePositionLock(position);
        while(running){
            PersonWaiting();
            if(this.getStatus().getHealth() <= 0)
                die();
            walk();
            //regenerate();
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
    public void attack(Person person) {
        if (this.getStatus().getEnergy() > 0) {
            person.getStatus().addHealth(-1);
            person.cellHelper.updateLife(person.getStatus().getHealth());

        }
    }


    public void AttackingTime(long timeEnd) {
        int maxIter = ThreadLocalRandom.current().nextInt(1, 3);
        int currIter = 0;
        //podziel timeEnd przez 5 , zrzutuj na long
        long passingTime = (int) (Math.abs(timeEnd) / maxIter);
        while (currIter++ < maxIter) {
            try {
                    attackNearby();
                Thread.sleep((int)(passingTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void PersonWaiting(){
        long timeEnd = ThreadLocalRandom.current().nextInt(800, 1500);
        //funckja AttackingTime powinna sie tutaj wykonywac rownolegle w osobnym wątku
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<?> future = executor.submit(() -> AttackingTime(timeEnd));
        try {

            Thread.sleep(timeEnd);
            future.get();
            // Czekaj, aż wątek z AttackingTime się skończy

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    public void attackNearby() {
        attackPerformer.executeNearbyAttack();
    }

    public PersonStatus getStatus(){
        return this.status;
    }

    public void regenerate(){
        this.getStatus().addEnergy(2);
        this.getStatus().addHealth(1);

    };
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

    public abstract ImageIcon getImage();
}
