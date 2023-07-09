package com.example.colonybattle.person;

import com.example.colonybattle.Colony;
import com.example.colonybattle.Vector2d;

public abstract class Person {
    protected int health;
    protected int energy;
    protected int strength;
    protected Vector2d position;
    protected Colony colony;
    protected int landAppropriation;

    public void attack(){};
    public void run(){};
    public void die(){};
    public void regenerate(){};
    public  void giveBirth(){};
}
