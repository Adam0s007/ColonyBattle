package com.example.colonybattle.models.person.status;

import com.example.colonybattle.models.person.type.PersonType;

import java.util.concurrent.Semaphore;

public class PersonStatus {
    volatile int health;
    volatile int energy;
    final int strength;
    volatile int id;
    final int landAppropriation;
    PersonType type;

    private final Semaphore healthSemaphore = new Semaphore(1);
    private final Semaphore energySemaphore = new Semaphore(1);
    private final Semaphore idSemaphore = new Semaphore(1);

    public PersonStatus(int health, int energy, int strength, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.id = id;
    }

    public int getHealth(){
        if (healthSemaphore.tryAcquire()) {
            int value = this.health;
            healthSemaphore.release();
            return value;
        }
        return this.health; // default to current health
    }

    public int getEnergy(){
        if (energySemaphore.tryAcquire()) {
            int value = this.energy;
            energySemaphore.release();
            return value;
        }
        return this.energy; // default to current energy
    }

    public int getStrength() {
        return strength;
    }

    public int getLandAppropriation() {
        return landAppropriation;
    }

    public void setType(PersonType type){
        this.type = type;
    }

    public PersonType getType(){
        return this.type;
    }

    public int getId(){
        if (idSemaphore.tryAcquire()) {
            int value = this.id;
            idSemaphore.release();
            return value;
        }
        return this.id; // default to current id
    }

    public void setHealth(int health){
        if (healthSemaphore.tryAcquire()) {
            this.health = Math.max(0, health);
            healthSemaphore.release();
        }
    }

    public void setEnergy(int energy){
        if (energySemaphore.tryAcquire()) {
            this.energy = Math.max(0, energy);
            energySemaphore.release();
        }
    }

    public void addEnergy(int energy){
        if (energySemaphore.tryAcquire()) {
            this.energy = Math.max(0, Math.min(this.energy + energy, type.getEnergy()));
            energySemaphore.release();
        }
    }

    public int addHealth(int health){
        int oldHealth = this.health;
        if (healthSemaphore.tryAcquire()) {
            this.health = Math.max(0, Math.min(this.health + health, type.getHealth()));
            healthSemaphore.release();
        }
        return oldHealth;
    }

    public void setId(int id){
        if (idSemaphore.tryAcquire()) {
            this.id = id;
            idSemaphore.release();
        }
    }
}
