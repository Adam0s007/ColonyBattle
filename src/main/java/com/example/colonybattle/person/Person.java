package com.example.colonybattle.person;

import com.example.colonybattle.*;

import com.example.colonybattle.Colors.Color;
import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.LockManagement.LockMapPosition;
import com.example.colonybattle.UI.Cell;
import com.example.colonybattle.colony.Colony;

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
            PersonWaiting();
            if(this.getStatus().getHealth() <= 0){
                die();
            }
            walk();
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
    public void attack(Person person) {
        // Sprawdź, czy osoba ma wystarczającą energię do ataku
        if (this.getStatus().getEnergy() > 0) {
            // Zmniejsz zdrowie ofiary o wartość siły atakującego
            person.getStatus().addHealth(-2);
            // Zmniejsz energię atakującego
            this.getStatus().addEnergy(-1);

            person.cellHelper.updateLife(person.getStatus().getHealth());
        }
    }
    public void regenerate(){
        int energy = this.getStatus().getEnergy();
        this.getStatus().setEnergy(energy + 1);
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

    public void AttackingTime(long timeEnd) {

        int maxIter = ThreadLocalRandom.current().nextInt(1, 5);
        int currIter = 0;
        //podziel timeEnd przez 5 , zrzutuj na long
        long passingTime = (int) (timeEnd / maxIter);
        while (currIter++ < maxIter) {
            try {
                Thread.sleep(passingTime);
                attackNearby();
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

        //czekajmy aż wątek z AttackingTime się skończy

    }

    public void attackNearby() {
        Vector2d[] offsets = {
                new Vector2d(-1, -1), new Vector2d(-1, 0), new Vector2d(-1, 1),
                new Vector2d(0, -1),                    new Vector2d(0, 1),
                new Vector2d(1, -1), new Vector2d(1, 0), new Vector2d(1, 1),
        };
        //System.out.println("Atakujemy");
        for (Vector2d offset : offsets) {
            //edge cases for offset
            if(offset.getX() < 0 || offset.getX() >= Board.SIZE || offset.getY() < 0 || offset.getY() >= Board.SIZE)
                continue;
            Vector2d targetPos = position.addVector(offset);
            if(boardRef.isFieldOccupied(targetPos))
                targetPos = boardRef.getVectorFromBoard(targetPos);
            else continue;
            //this.posLock.aquirePositionLock(targetPos);
             //return set of people from targetPos
            Set<Person> people = targetPos.getPeople();
            //iterujemy po osobach w peopleArray, jesli sa z innej kolonii to je atakujemy
            for (Person person : people) {

                if (person.getColony().getType() != this.getColony().getType()) {
                    System.out.println("Nastepuje atak");
                    this.attack(person);

                }
            }
            //this.posLock.releasePositionLock(targetPos);
        }
    }

    public PersonStatus getStatus(){
        return this.status;
    }



}
