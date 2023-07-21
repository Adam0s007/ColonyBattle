package com.example.colonybattle.models.person;
import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.abilities.Magic;
import com.example.colonybattle.ui.image.ImageLoader;
import com.example.colonybattle.ui.image.ImageLoaderInterface;
import com.example.colonybattle.board.boardlocks.PosLock;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.actions.Attack;
import com.example.colonybattle.models.person.characters.Farmer;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.models.person.helpers.BoardRef;
import com.example.colonybattle.models.person.helpers.CellHelper;
import com.example.colonybattle.models.person.helpers.ConnectionHelper;
import com.example.colonybattle.models.person.status.PersonStatus;
import com.example.colonybattle.models.person.type.PersonType;
import com.example.colonybattle.utils.Calculator;

import javax.swing.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;


public abstract class Person implements Runnable{

    protected Point2d position;

    protected Colony colony;
    protected volatile boolean running = true;
    protected PersonStatus status;
    public CellHelper cellHelper;
    protected BoardRef boardRef;
    protected ConnectionHelper connectionHelper;
    protected PosLock posLock;
    public boolean isNew = false;
    protected Attack attackPerformer;
    protected final ReentrantLock attackLock = new ReentrantLock();
    protected final ReentrantLock defendLock = new ReentrantLock();
    protected ImageLoaderInterface imageLoader;
    private int depth = 0;
    //semafor for dying
    public final Semaphore dyingSemaphore;
    private final int MAX_DEPTH = 5;

    @Override
    public int hashCode() {
        return this.status.getId();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Person))
            return false;
        Person that = (Person) other;
        return this.status.getId() == that.status.getId();
    }
    @Override
    public String toString() {
        return "Person[" + "id=" + this.status.getId() + ']';
    }
    public Person(int health, int energy, int strength, Point2d position, Colony colony, int landAppropriation, int id) {
        this.imageLoader = ImageLoader.getInstance();
        this.status = new PersonStatus(health,energy,strength,landAppropriation,id,this.cellHelper);
        this.boardRef = new BoardRef(this);
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.changePosConnections(null,position);
        connectionHelper.connectColony(colony);
        this.position.changeMembershipForcefully(this);
        posLock = new PosLock(boardRef);
        attackPerformer = new Attack(this,boardRef);
        dyingSemaphore = new Semaphore(1);
    }

    public void initGUI(){
        this.cellHelper = new CellHelper(this,boardRef);
        cellHelper.newCellAt(this.position);
        cellHelper.spawningColor();
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }

    private void move(Point2d newPosition) {
        Point2d oldPosition = this.position;
        connectionHelper.changePosConnections(oldPosition,newPosition);
        if(cellHelper == null) return;
        cellHelper.resetCell(oldPosition);
        cellHelper.newCellAt(newPosition);
    }

    public void walk() {
        Point2d newPosition = newPoint();
        Point2d oldPosition = this.position;
        if (isValidMove(newPosition)) {
            if (!posLock.aquirePositionLock(newPosition)) {
                attemptAlternateMoves(oldPosition);
            } else {
                this.move(newPosition);
                posLock.releasePositionLock(oldPosition);
            }
        }
    }

    private boolean isValidMove(Point2d newPosition) {
        return newPosition != null && newPosition.properCoordinates(Board.SIZE) && !this.position.equals(newPosition);
    }
    private void attemptAlternateMoves(Point2d oldPosition) {
        for(int i = 0; i < MAX_DEPTH; i++) {
            Point2d alternatePosition = boardRef.generateRandomPosition(position);
            if (isValidMove(alternatePosition) && posLock.aquirePositionLock(alternatePosition)) {
                this.move(alternatePosition);
                posLock.releasePositionLock(oldPosition);
                break;
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
    private Point2d newPoint() {
        Point2d vecField = boardRef.closestField(this);
        Point2d directionVecField = null;
        Point2d newFieldPos = null;
        if(vecField != null){;
             directionVecField = Calculator.calculateDirection(position, vecField);
            newFieldPos = boardRef.calculateNewPosition(position, directionVecField);
        }

        Point2d closestPersonPos = null;
        closestPersonPos = this.findClosestPosition();
        if (closestPersonPos == null) return newFieldPos;

        Point2d directionVecPerson = Calculator.calculateDirection(position, closestPersonPos);
        if ((this instanceof Farmer || this instanceof  Wizard) && (this.getStatus().getHealth() > 4 || Calculator.calculateDistance(position,closestPersonPos) >= 6)) return newFieldPos;
        if (this instanceof Farmer || this instanceof  Wizard)
            directionVecPerson = new Point2d(-directionVecPerson.getX(), -directionVecPerson.getY());
        return boardRef.calculateNewPosition(position, directionVecPerson);
    }
    @Override
    public void run(){
        initGUI();
        if(!isNew) posLock.aquirePositionLock(position);
        checkWizardingQualifications();
        while(running){
            PersonWaiting();
            if(this.getStatus().getHealth() <= 0){
                if(dyingSemaphore.tryAcquire()) {
                    die();
                }
                return;
            }
            walk();
        }
    };
    public void die(){
        Point2d oldPosition = new Point2d(this.position.getX(),this.position.getY());
        connectionHelper.changePosConnections(this.position,null);
        cellHelper.resetCell(oldPosition);
        posLock.releasePositionLock(oldPosition);
        this.running = false;
        this.colony.personFactory.removePerson(this);
        dyingSemaphore.release();
        connectionHelper.disconnectColony();
        //System.out.println(this.boardRef.getBoard() == null);
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
    public Point2d getPosition() {
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
        if (attackLock.tryLock()) {
            try {
                person.defend(2);
                this.status.addEnergy(-1);
            } finally {
                attackLock.unlock();
            }
        } else {
            System.out.println("Unable to lock, skipping attack.");
        }
    }
    public void healMe(int heal){
        int oldHealth = this.status.addHealth(heal);
        if(oldHealth < this.getType().getHealth())
            this.cellHelper.healingColor();
    }
    public abstract  void defend(int attackStrength);
    public abstract Point2d findClosestPosition();
    public abstract long waitingTiming();

    public BoardRef getBoardRef() {
        return boardRef;
    }
    public boolean isRunning() {
        return running;
    }
}
