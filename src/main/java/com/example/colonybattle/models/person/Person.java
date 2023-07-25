package com.example.colonybattle.models.person;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.abilities.Magic;
import com.example.colonybattle.models.person.achievements.Kills;
import com.example.colonybattle.models.person.actions.defense.DefendStrategy;
import com.example.colonybattle.models.person.actions.movement.Movement;
import com.example.colonybattle.models.person.messages.Message;
import com.example.colonybattle.ui.image.ImageLoader;
import com.example.colonybattle.ui.image.ImageLoaderInterface;
import com.example.colonybattle.board.boardlocks.PosLock;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.actions.attack.Attack;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.models.person.helpers.BoardRef;
import com.example.colonybattle.models.person.helpers.CellHelper;
import com.example.colonybattle.models.person.helpers.ConnectionHelper;
import com.example.colonybattle.models.person.status.PersonStatus;
import com.example.colonybattle.models.person.type.PersonType;
import com.example.colonybattle.utils.ThreadUtils;

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
    protected ImageLoaderInterface imageLoader;
    public final Semaphore dyingSemaphore;
    public final int MAX_DEPTH = 5;
    protected Movement movement;
    public final BlockingQueue<Message> queue;
    protected Kills kills;
    protected DefendStrategy defendStrategy;

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
        dyingSemaphore = new Semaphore(1);;
        queue = new LinkedBlockingQueue<>();
        this.kills = new Kills();

    }

    public void initGUI(){
        this.cellHelper = new CellHelper(this,boardRef);
        cellHelper.newCellAt(this.position);
        cellHelper.spawningColor();
    }

    public void setPosition(Point2d position) {
        this.position = position;
    }


    private void checkWizardingQualifications(){
        if(this instanceof Wizard){
            Magic wizard = (Wizard) this;
            wizard.healFriends();
            wizard.performAbsorption();
        }
    }
    @Override
    public void run(){
        initGUI();
        if(!isNew) posLock.aquirePositionLock(position);
        checkWizardingQualifications();
        while(running){
            PersonWaiting();
            receivingMessage();
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
            attackNearby();
            ThreadUtils.getInstance().pause((int)(passingTime));
        }
    }
    public void PersonWaiting(){
        long timeEnd = waitingTiming();
        //funckja AttackingTime powinna sie tutaj wykonywac rownolegle w osobnym wątku
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<?> future = executor.submit(() -> AttackingTime(timeEnd));
        try {
            ThreadUtils.getInstance().pause(timeEnd);
            future.get();
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
                person.defend(this,2);
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
    public abstract  void defend(Person person,int attackStrength);
    public abstract Point2d findClosestPosition();
    public abstract long waitingTiming();

    public BoardRef getBoardRef() {
        return boardRef;
    }
    public boolean isRunning() {
        return running;
    }
    public CellHelper getCellHelper() {
        return cellHelper;
    }
    public ConnectionHelper getConnectionHelper() {
        return connectionHelper;
    }

    public PosLock getPosLock() {
        return posLock;
    }

    public void walk(){
        this.movement.walk();
    }

    public boolean CheckingKill(){
        if(this.getStatus().getHealth() <= 0){
            this.cellHelper.deathColor();
            return true;
        }
        return false;
    }

    public void sendingMessage(Person person, Message message){
        person.queue.add(message);
    }
    //method receivingMessgae() will be take all messages from BlockingQueue
    public void receivingMessage() {
        while (!queue.isEmpty()) {
            Message message = queue.poll();
            if (!kills.hasKilled(message.getPerson())) {
                kills.addKill(message.getPerson());
                addPoints(30);
                System.out.println(message.getPerson() + " was killed by " + this);
            }
        }
    }
    public void  addPoints(int points){
        if(this.colony != null)
            this.colony.addPoints(points);
    }

}
