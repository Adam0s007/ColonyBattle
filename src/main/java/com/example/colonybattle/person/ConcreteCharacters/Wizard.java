package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Magic;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

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
    private final int PERIOD = 3;

    private final int MIN_WAIT = 1000;
    private final int MAX_WAIT = 2000;
    public Wizard(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);
        INITIAL_DELAY = ThreadLocalRandom.current().nextInt(0, 4);
    }

    @Override
    public Character getInitial() {
        return 'W';
    }
    @Override
    public void wand(Vector2d vec) {
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
    public synchronized void defend(int damage) {
        if (status.getEnergy() >= MIN_PROTECTION_ENERGY) {
            double random = ThreadLocalRandom.current().nextDouble(); // Generate a random number between 0 and 1

            if (random <= 0.6) {
                status.addEnergy(-MIN_PROTECTION_ENERGY);//traci tylko 1 punkt energii
            } else {
                //wizard potrafi przeksztalcic pewną część damage na energie ale wtedy pozostala czesc damage trafia w jego zdrowie
                int energy = (int) Math.ceil(damage * 0.4);
                int health = (int) Math.ceil(damage * 0.6);
                status.addEnergy(energy);
                status.addHealth(-health);
            }
        } else {
            double damageReduction = 0.5; // 50% damage reduction when energy < PROTECTION_ENERGY
            int reducedDamage = (int) Math.ceil(damage * damageReduction);
            status.addHealth(-reducedDamage);
        }
    }
    public void healFriends(){
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
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
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
                Vector2d closestPersonPosition = findClosestPosition();
                //System.out.println("Wizard closest enemy position: " + closestPersonPosition);
                if (closestPersonPosition != null) {
                    Person person = closestPersonPosition.getPerson();
                    if (person != null)
                        this.wand(closestPersonPosition);
                }
            }
        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, 5, TimeUnit.SECONDS);
    }
    @Override
    public void attack(Person person) {
        int strength = status.getStrength();
        int energy = status.getEnergy();
        if(energy < this.MIN_PROTECTION_ENERGY) {
            person.defend(1);
        }
        int damage = (int) Math.ceil((0.2*strength) * ((energy / 10.0)));
        person.defend(damage);
    }
    @Override
    public void die(){
        //System.out.println("Wizard died");
        super.die();
    }
    @Override
    public Vector2d findClosestPosition() {
        Vector2d closestPersonPosition = null;
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
