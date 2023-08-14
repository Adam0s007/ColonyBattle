package com.example.colonybattle.models.person;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.board.boardlocks.PosLock;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.abilities.Magic;
import com.example.colonybattle.models.person.achievements.Kills;
import com.example.colonybattle.models.person.actions.attack.PersonAttackStrategy;
import com.example.colonybattle.models.person.actions.defense.DefendStrategy;
import com.example.colonybattle.models.person.actions.movement.Movement;
import com.example.colonybattle.models.person.characters.Wizard;
import com.example.colonybattle.models.person.helpers.BoardRef;
import com.example.colonybattle.models.person.helpers.CellHelper;
import com.example.colonybattle.models.person.helpers.ConnectionHelper;
import com.example.colonybattle.models.person.messages.DestinationMessage;
import com.example.colonybattle.models.person.messages.Message;
import com.example.colonybattle.models.person.status.PersonStatus;
import com.example.colonybattle.models.person.type.PersonType;
import com.example.colonybattle.ui.image.ImageLoader;
import com.example.colonybattle.ui.image.ImageLoaderInterface;
import com.example.colonybattle.utils.ThreadUtils;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

@Getter
@Setter
public abstract class Person implements Runnable {
    protected Point2d position;
    protected Colony colony;
    protected volatile boolean running = true;
    protected PersonStatus status;
    public CellHelper cellHelper;
    protected BoardRef boardRef;
    protected ConnectionHelper connectionHelper;
    protected PosLock posLock;
    public boolean isNew = false;
    protected PersonAttackStrategy attackPerformer;
    protected ImageLoaderInterface imageLoader;
    public final int MAX_DEPTH = 5;
    protected Movement movement;
    public final BlockingQueue<Message> queue;
    public final BlockingQueue<DestinationMessage> destinationMessages;
    protected Kills kills;
    protected DefendStrategy defendStrategy;
    private boolean isFocused = false;
    private Color focusColor = new Color(0, 255, 175, 255);
    public Person(int health, int energy, int strength, Point2d position, Colony colony, int landAppropriation, int id) {
        this.imageLoader = ImageLoader.getInstance();
        this.status = new PersonStatus(health, energy, strength, landAppropriation, id, this.cellHelper);
        this.boardRef = new BoardRef(this);
        this.connectionHelper = new ConnectionHelper(this);
        connectionHelper.changePosConnections(null, position);
        connectionHelper.connectColony(colony);
        posLock = new PosLock(boardRef);
        queue = new LinkedBlockingQueue<>();
        destinationMessages = new LinkedBlockingQueue<>();
        this.kills = new Kills();
    }
    public abstract Character getInitial();
    public abstract ImageIcon getImage();
    public abstract void defend(Person person, int attackStrength);
    public abstract Point2d findClosestPosition();
    public abstract long waitingTiming();
    @Override
    public int hashCode() {
        return this.status.getId();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Person)) return false;
        Person that = (Person) other;
        return this.status.getId() == that.status.getId();
    }
    @Override
    public String toString() {
        return "Person[" + "id=" + this.status.getId() + ']';
    }
    @Override
    public void run() {
        initGUI();
        if (!isNew) posLock.aquirePositionLock(position);
        updateColonyFrame();
        while (running) {
            this.getBoardRef().updateRankingPanel();
            PersonWaiting();
            receivingMessage();
            if (this.getStatus().getHealth() <= 0) {
                die();
                return;
            }
            walk();
        }
    }
    public void initGUI(){
        this.cellHelper = new CellHelper(this,boardRef);
        cellHelper.newCellAt(this.position);
        cellHelper.spawningColor();
    }
    public void die(){
        setFocused(false); // nie wazne czy byl czy nie byl, focus sie skonczyl
        Point2d oldPosition = new Point2d(this.position.getX(),this.position.getY());
        connectionHelper.changePosConnections(this.position,null);
        cellHelper.resetCell(oldPosition);
        posLock.releasePositionLock(oldPosition);
        this.running = false;
        this.colony.personFactory.removePerson(this);
        connectionHelper.disconnectColony();
        updateColonyFrame();
    }
    public void stop() {
        running = false;
    }
    public void AttackingTime(long timeEnd) {
        this.attackPerformer.AttackingTime(timeEnd);
    }
    public void attack(Person person) {
        this.attackPerformer.attack(person);
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
    public PersonType getType() {
        return status.getType();
    }
    public void healMe(int heal){
        int oldHealth = this.status.addHealth(heal);
        if(oldHealth < this.getType().getHealth())
            this.cellHelper.healingColor();
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

    public void setNewTarget(DestinationMessage message){
        this.destinationMessages.add(message);
    }
    public void receivingMessage() {
        while (!queue.isEmpty()) {
            Message message = queue.poll();
            if (!kills.hasKilled(message.getPerson())) {
                kills.addKill(message.getPerson());
                addPoints(30);
                System.out.println(message.getPerson() + " was killed by " + this);
                message.getPerson().getBoardRef().removePersonFromFrame();//making sure that person is removed from frame
            }
        }
    }
    public void  addPoints(int points){
        if(this.colony != null)
            this.colony.addPoints(points);
        this.updateColonyPoints();
    }

    public void updateColonyFrame(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boardRef.updateColonyFrame();
            }
        });
    }
    public void updateColonyPoints(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                boardRef.updateColonyPoints();
            }
        });
    }
    public boolean isDead(){
        return this.status.getHealth() <= 0;
    }
    public void increaseAge(){
        this.status.incrementAge();
    }
}
