package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Magic;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Wizard extends Person implements Magic {

    private  final int MIN_PROTECTION_ENERGY = 4;
    //Random value between 0 and 4:
    private final int INITIAL_DELAY;
    private final int PERIOD = 6;
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
    public void wand() {
        // Implementacja...
    }

    @Override
    public void healMyself() {
        // Implementacja...
    }



    // Implementacja pozostałych metod...
    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }

    @Override
    public void defend(int damage) {
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
    //
    public void healFriends(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("ja sie wywoluję!");
        Runnable task = () -> {
            if (!super.running) { // if running is false, shut down the executor service
                executorService.shutdown();
            } else {
                    this.colony.getPeople().forEach(person -> {
                        if(person != this){
                            if(this.status.getEnergy() > 0){
                                person.healMe(100);
                                this.status.addEnergy(-1);
                            }
                        }
                    });
            }
        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }


    public void attack(Person person) {
        person.defend(4);
    }

    @Override
    public void die(){
        System.out.println("Wizard died");
        super.die();

    }
}
