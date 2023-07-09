package com.example.colonybattle.person;

import com.example.colonybattle.Board;
import com.example.colonybattle.Colony;
import com.example.colonybattle.Direction;
import com.example.colonybattle.Vector2d;
import javafx.scene.shape.Rectangle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.scene.paint.Color;


public abstract class Person implements Runnable{
    protected int health;
    protected int energy;
    protected int strength;
    protected int id;

    private Rectangle rectangle;

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

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
        this.colony = colony;
        this.landAppropriation = landAppropriation;
        this.id = id;
    }
    // konstruktor z domyslnymi parametrami i randomowym polozeniem
    public Person(){
        this.health = 100;
        this.energy = 100;
        this.strength = 10;
        this.position = new Vector2d(ThreadLocalRandom.current().nextInt(0, Board.SIZE), ThreadLocalRandom.current().nextInt(0, Board.SIZE));
        this.colony = null;
        this.landAppropriation = 1;
    }

    public void attack(){};

    public void walk() {
        Direction[] directions = Direction.values();
        Direction randomDirection = directions[ThreadLocalRandom.current().nextInt(directions.length)];
        Vector2d directionVector = randomDirection.getVector();
        Vector2d newPosition = position.addVector(directionVector);

        // Sprawdzenie czy nowa pozycja jest w granicach planszy
        if (newPosition.getX() >= 0 && newPosition.getX() < Board.SIZE &&
                newPosition.getY() >= 0 && newPosition.getY() < Board.SIZE) {
            // Jeżeli tak, aktualizacja pozycji
            // Jeżeli tak, aktualizacja pozycji
            this.position = newPosition;
        }
        // Jeżeli nowa pozycja jest poza granicami planszy, person nie porusza się
       // System.out.println("Person " + id + " is walking to " + position);
    }
    @Override
    public void run(){
        while(running){
            //randomowo dlugo czeka (od sekundy do 3 sekund) i wykonuje metodę walk()
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 3000));
                walk();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    public void die(){};
    public void regenerate(){};
    public  void giveBirth(){};

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

//    protected void updateRectanglePosition() {
//        if (rectangle != null) {
//            rectangle.setFill(colony.getColonyColor().getColor());
//            rectangle.setTranslateX(position.getX() * 20); // Przesunięcie prostokąta na odpowiednią pozycję X
//            rectangle.setTranslateY(position.getY() * 20); // Przesunięcie prostokąta na odpowiednią pozycję Y
//        }
//    }
}
