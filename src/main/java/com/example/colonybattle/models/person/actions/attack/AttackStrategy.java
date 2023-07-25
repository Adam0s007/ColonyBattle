package com.example.colonybattle.models.person.actions.attack;

import com.example.colonybattle.models.person.Person;

public interface AttackStrategy {
    void executeNearbyAttack();
    void attack(Person person);
}
