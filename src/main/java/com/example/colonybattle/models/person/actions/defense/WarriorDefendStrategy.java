package com.example.colonybattle.models.person.actions.defense;

import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.messages.Message;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

public class WarriorDefendStrategy extends PersonDefendStrategy{


    public WarriorDefendStrategy(Person person) {
        super(person);
    }
    @Override
    public void defend(Person attacker, int damage) {
        if (defendLock.tryLock()) {
            try {
                int oldHealth = person.getStatus().getHealth();
                if (person.getStatus().getEnergy() >= person.getType().getProtection_energy()) {
                    double random = ThreadLocalRandom.current().nextDouble();

                    if (random <= 0.45) {
                        person.getStatus().addEnergy(-person.getType().getProtection_energy());
                    } else {
                        person.getStatus().addHealth(-1);
                        //if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                    }
                } else {
                    double damageReduction = 0.6;
                    int reducedDamage = (int) Math.ceil(damage * damageReduction);
                    person.getStatus().addHealth(-reducedDamage);
                    //if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                }
                if(person.CheckingKill() && oldHealth > person.getStatus().getHealth()) person.sendingMessage(attacker,new Message("killed",person));
            } finally {
                defendLock.unlock();
            }
        } else {
            //System.out.println("Unable to lock, skipping defense.");
        }
    }
}
