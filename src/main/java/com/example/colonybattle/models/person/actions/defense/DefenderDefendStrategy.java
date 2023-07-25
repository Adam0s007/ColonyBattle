package com.example.colonybattle.models.person.actions.defense;

import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.messages.Message;

import java.util.concurrent.ThreadLocalRandom;

public class DefenderDefendStrategy extends PersonDefendStrategy{

    private final int MIN_PROTECTION_ENERGY = 3;

    public DefenderDefendStrategy(Person person) {
        super(person);
    }

    @Override
    public void defend(Person attacker, int damage) {
        if (defendLock.tryLock()) {
            try {
                int oldHealth = person.getStatus().getHealth();
                if (person.getStatus().getEnergy() >= MIN_PROTECTION_ENERGY) {
                    double random = ThreadLocalRandom.current().nextDouble();

                    if (random <= 0.8) {
                        person.getStatus().addEnergy(-1);
                    } else {
                        person.getStatus().addEnergy(-1);
                        person.getStatus().addHealth(-1);
                        // if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                    }
                } else {
                    double damageReduction = 0.6;
                    int reducedDamage = (int) Math.ceil(damage * damageReduction);
                    person.getStatus().addHealth(-reducedDamage);
                    // if(this.getStatus().getHealth() <= 0)  this.cellHelper.deathColor();
                }
                if(person.CheckingKill() && oldHealth > person.getStatus().getHealth()) person.sendingMessage(attacker,new Message("killed",person));
            } finally {
                defendLock.unlock();
            }
        }
    }
}
