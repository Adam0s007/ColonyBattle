package com.example.colonybattle.person;
import com.example.colonybattle.*;
import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.UI.ImageLoaderInterface;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.ConcreteCharacters.Defender;
import com.example.colonybattle.person.ConcreteCharacters.Farmer;
import com.example.colonybattle.person.ConcreteCharacters.Warrior;
import com.example.colonybattle.person.ConcreteCharacters.Wizard;

import javax.swing.*;
import java.util.concurrent.*;


public abstract class Person implements Runnable{

    protected Vector2d position;

    protected Colony colony;
    protected volatile boolean running = true;
    protected PersonStatus status;
    public CellHelper cellHelper;
    protected BoardRef boardRef;
    protected ConnectionHelper connectionHelper;
    protected PosLock posLock;
    protected Attack attackPerformer;
    protected ImageLoaderInterface imageLoader;
    private int depth = 0;
    //semafor for dying
    private final Semaphore dyingSemaphore;
    private final int MAX_DEPTH = 3;

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
        return "Person[" + "id=" + this.status.getId() + ']';
    }
    public Person(int health, int energy, int strength, Vector2d position, Colony colony, int landAppropriation,int id) {
        this.imageLoader = ImageLoader.getInstance();
        this.status = new PersonStatus(health,energy,strength,landAppropriation,id);
        this.boardRef = new BoardRef(this);
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.changePosConnections(null,position);
        this.position.changeMembershipForcefully(this);
        connectionHelper.connectColony(colony);
        posLock = new PosLock(boardRef);
        attackPerformer = new Attack(this,boardRef);
        dyingSemaphore = new Semaphore(1);
    }

    public void initGUI(){
        this.cellHelper = new CellHelper(this);
        cellHelper.newCellAt(this.position);
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    private void move(Vector2d newPosition) {
        Vector2d oldPosition = this.position;
        connectionHelper.changePosConnections(oldPosition,newPosition);
        if(cellHelper == null) return;
        cellHelper.resetCell(oldPosition);
        cellHelper.newCellAt(newPosition);
    }

    public void walk() {
        Vector2d newPosition = newPoint();
        if (newPosition != null && newPosition.properCoordinates(Board.SIZE) && this.position.equals(newPosition) == false) {
            Vector2d oldPosition = position;
            this.boardRef.addVectorToBoard(newPosition);
            if(!posLock.aquirePositionLock(newPosition)){
                depth++;
                walk();
            }
            else{
                this.move(newPosition);
                posLock.releasePositionLock(oldPosition);
            }
        }
    }
    private void checkWizardingQualifications(){
        if(this instanceof Wizard){
            Magic wizard = (Wizard) this;
            wizard.healFriends();
            wizard.performAbsorption();
        }
    }
    private Vector2d newPoint() {
        Vector2d randomPos = boardRef.generateRandomPosition(position);
        if (depth == MAX_DEPTH) {
            depth = 0;
            return randomPos;
        }
        Vector2d closestEnemy = null;
        if (this instanceof Warrior || this instanceof Farmer || this instanceof Defender) {
            closestEnemy = ((Person) this).findClosestPosition();
            if (closestEnemy == null) return randomPos;
        }
        if (closestEnemy == null) return randomPos;
        Vector2d directionVec = Calculator.calculateDirection(position, closestEnemy);
        if (this instanceof Farmer && (this.getStatus().getHealth() > 4 || Calculator.calculateDistance(position,closestEnemy) >= 6)) return randomPos;
        if (this instanceof Farmer)
            directionVec = new Vector2d(-directionVec.getX(), -directionVec.getY());
        return boardRef.calculateNewPosition(position, directionVec);
    }
    @Override
    public void run(){
        initGUI();
        posLock.aquirePositionLock(position);
        checkWizardingQualifications();
        while(running){
            PersonWaiting();
            if(this.getStatus().getHealth() <= 0)
                if(dyingSemaphore.tryAcquire())
                    die();
            walk();
        }
    };
    public void die(){
        Vector2d oldPosition = new Vector2d(this.position.getX(),this.position.getY());
        connectionHelper.changePosConnections(this.position,null);
        System.out.println(this.boardRef.getBoard() == null);
        cellHelper.resetCell(oldPosition);
        posLock.releasePositionLock(oldPosition);
        this.running = false;
        dyingSemaphore.release();
        connectionHelper.disconnectColony();
    }
    public void stop() {
        running = false;
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
        long timeEnd = waitingTiming();
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
    public abstract Character getInitial(); // Nowa metoda zwracająca inicjały osoby
    public Vector2d getPosition() {
        return position;
    }

    public Colony getColony() {
        return colony;
    }
    public void setColony(Colony colony) {
        this.colony = colony;
    }
    public PersonType getType() {
        return status.getType();
    }
    public abstract ImageIcon getImage();

    public void attack(Person person) {
        person.defend(1);
        this.status.addEnergy(-1);
    }
    public void healMe(int heal){
        int oldHealth = this.status.addHealth(heal);
        this.cellHelper.updateLife(this.getStatus().getHealth());
        if(oldHealth < this.getType().getHealth())
            this.cellHelper.healingColor();
    }
    public abstract void defend(int attackStrength);
    public abstract Vector2d findClosestPosition();
    public abstract long waitingTiming();
}
