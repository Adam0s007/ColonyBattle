package com.example.colonybattle.models.person.characters;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.abilities.Magic;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.Attack;
import com.example.colonybattle.models.person.actions.movement.DefenderMovementStrategy;
import com.example.colonybattle.models.person.actions.movement.WizardMovementStrategy;
import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Wizard extends Person implements Magic {
    private  final int MIN_PROTECTION_ENERGY = 4;
    //Random value between 0 and 4:
    private final int INITIAL_DELAY;
    private final int HEALING_PERIOD = 10;
    private final int WAND_PERIOD = 4;
    private final int MIN_WAIT = 800;
    private final int MAX_WAIT = 1000;
    public Wizard(PersonType type, Point2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        super.movement = new WizardMovementStrategy(this);
        attackPerformer = new Attack(this,movement);
        status.setType(type);
        INITIAL_DELAY = ThreadLocalRandom.current().nextInt(0, 4);
    }

    @Override
    public Character getInitial() {
        return 'W';
    }
    @Override
    public void wand(Point2d vec) {
        Person person = vec.getPerson();
        if(person != null){//zabiera mu energie
            double random = ThreadLocalRandom.current().nextDouble(); // Generate a random number between 0 and 1
            if (random > 0.4) {
                person.cellHelper.energyEmitionColor();
                this.attackPerformer.attackAndPossiblyKill(person);
                this.getStatus().addEnergy(1);
            } else {
                person.cellHelper.energyEmitionColor();
                person.getStatus().addEnergy(-2);
                this.getStatus().addEnergy(2);
            }
        }
    }
    // Implementacja pozostałych metod...
    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }
    @Override
    public void defend(int damage) {
        if (defendLock.tryLock()) {
            try {
                if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
                    double random = ThreadLocalRandom.current().nextDouble();

                    if (random <= 0.6) {
                        status.addEnergy(-MIN_PROTECTION_ENERGY);
                    } else {
                        int energy = (int) Math.ceil(damage * 0.4);
                        int health = (int) Math.ceil(damage * 0.6);
                        status.addEnergy(energy);
                        status.addHealth(-health);
                        if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                    }
                } else {
                    double damageReduction = 0.5;
                    int reducedDamage = (int) Math.ceil(damage * damageReduction);
                    status.addHealth(-reducedDamage);
                    if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                }
            } finally {
                defendLock.unlock();
            }
        } else {
            // Unable to lock, so we do nothing. You can decide what to do in this case.
            //System.out.println("Unable to lock, skipping defense.");
        }
    }
    public  void healFriends(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //System.out.println("ja sie wywoluję!");
        Runnable task = () -> {
            if (!super.running) { // if running is false, shut down the executor service
                executorService.shutdown();
            } else {
                this.colony.getPeople().stream()
                        .filter(person -> person != this && person.getStatus().getHealth() < person.getType().getHealth())
                        .findFirst()
                        .ifPresent(person -> {
                            if (this.status.getEnergy() > 0) {
                                person.healMe(2);
                                this.status.addEnergy(-1);
                            }
                        });

            }
        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, HEALING_PERIOD, TimeUnit.SECONDS);
    }
    //Wizard szuka wroga i szansa ze zabierze mu energie wynosi 80% a ze zycie 20% - uzywa scheduleAtFixedRate
    @Override
    public void performAbsorption(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("");
        Runnable task = () -> {
            if (!super.running) { // if running is false, shut down the executor service
                executorService.shutdown();
            } else {
                Point2d closestPersonPosition = findClosestPosition();
                //System.out.println("Wizard closest enemy position: " + closestPersonPosition);
                if (closestPersonPosition != null) {
                    Person person = closestPersonPosition.getPerson();
                    if (person != null)
                        this.wand(closestPersonPosition);
                }
            }
        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, WAND_PERIOD, TimeUnit.SECONDS);
    }
    @Override
    public void attack(Person person) {
        if (attackLock.tryLock()) {
            try {
                int strength = status.getStrength();
                int energy = status.getEnergy();
                if(energy < this.MIN_PROTECTION_ENERGY) {
                    person.defend(1);
                    if(person.getStatus().getHealth() <= 0)  person.cellHelper.deathColor();
                    return;
                }
                int damage = (int) Math.ceil((0.2*strength) * ((energy / 10.0)));
                person.defend(damage);
            } finally {
                attackLock.unlock();
            }
        } else {
            // Unable to lock, so we do nothing. You can decide what to do in this case.
          //  System.out.println("Unable to lock, skipping attack.");
        }
    }
    @Override
    public void die(){
        //System.out.println("Wizard died");
        super.die();
    }
    @Override
    public Point2d findClosestPosition() {
        Point2d closestPersonPosition = null;
        List<Colony> colonies = this.boardRef.getAllColonies();
        Optional<Person> closestPerson = colonies.stream()
                .filter(colony -> !colony.equals(this.getColony())) // filter out this person's colony
                .flatMap(colony -> colony.getPeople().stream())     // get stream of people from other colonies
                .min(Comparator.comparing(person -> this.position.distanceTo(person.getPosition()))); // find person with minimum distance
        if (closestPerson.isPresent()) {
            closestPersonPosition = closestPerson.get().getPosition();
        }
        return closestPersonPosition;
    }
  
    @Override
    public long waitingTiming() {
        long timeEnd = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT);
        return timeEnd;
    }
}
