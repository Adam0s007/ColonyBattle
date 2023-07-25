package com.example.colonybattle.models.person.actions.attack;

import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.actions.movement.Movement;

public class WarriorAttackStrategy extends PersonAttackStrategy{

    public WarriorAttackStrategy(Person attacker, Movement movement) {
        super(attacker, movement);
    }


    @Override
    public void attack(Person person) {
        if (attackLock.tryLock()) {
            try {
                int strength = attacker.getStatus().getStrength();
                int energy = attacker.getStatus().getEnergy();
                if(energy < attacker.getType().getProtection_energy()) {
                    person.defend(attacker,2);
                    if(person.getStatus().getHealth() <= 0)  person.cellHelper.deathColor();
                }else{
                    int damage = (int) Math.ceil((0.2*strength) * ((energy / 10.0)));
                    person.defend(attacker,damage);
                }

            } finally {
                attackLock.unlock();
            }
        } else {
            //System.out.println("Unable to lock, skipping attack.");
        }
    }
}
