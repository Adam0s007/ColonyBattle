package com.example.colonybattle.models.person.actions.defense;

import com.example.colonybattle.models.person.Person;

import java.util.concurrent.locks.ReentrantLock;

abstract public class PersonDefendStrategy implements DefendStrategy {
    protected Person person;
    protected final ReentrantLock defendLock = new ReentrantLock();

    public PersonDefendStrategy(Person person) {
        this.person = person;
    }
    abstract public void defend(Person person, int damage);
}
