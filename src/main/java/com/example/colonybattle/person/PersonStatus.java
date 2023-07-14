package com.example.colonybattle.person;

import com.example.colonybattle.colony.Colony;

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

    PersonStatus(int health, int energy, int strength, int landAppropriation,int id) {
        this.health = health;
        this.energy = energy;
        this.strength = strength;
        this.landAppropriation = landAppropriation;
        this.id = id;
    }

    public int getHealth(){
        try{
            healthSemaphore.acquire();
            int value = this.health;
            healthSemaphore.release();
            return value;

        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }
        return -1;
    }

    public int getEnergy(){
        try{
            energySemaphore.acquire();
            int value = this.energy;
            energySemaphore.release();
            return value;

        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }
        return -1;
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
        try{
            idSemaphore.acquire();
            int value = this.id;
            idSemaphore.release();
            return value;

        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }
        return -1;
    }

    public void setHealth(int health){
        try{

            healthSemaphore.acquire();
            if(health < 0){
                this.health = 0;
            } else {
                this.health = health;
            }
            healthSemaphore.release();

        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }


    }

    public void setEnergy(int energy){
        try{
            energySemaphore.acquire();
            if(energy < 0){
                this.energy = 0;
            } else {
                this.energy = energy;
            }
            energySemaphore.release();
        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }


    }

    public void addEnergy(int energy){
        try{
            energySemaphore.acquire();
            if(this.energy + energy < 0){
                this.energy = 0;
            } else if(this.energy + energy <= type.getEnergy()){
                this.energy += energy;
            }
            energySemaphore.release();
        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }


    }

    public void addHealth(int health){
        try{
            healthSemaphore.acquire();
            if(this.health + health < 0){
                this.health = 0;
            } else if (this.health + health <= type.getHealth()){
                this.health += health;
            }
            healthSemaphore.release();
        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }


    }

    public void setId(int id){
        try{
            idSemaphore.acquire();
            this.id = id;
            idSemaphore.release();
        } catch (InterruptedException e){
            System.out.println("Interrupted");
        }
    }
}
