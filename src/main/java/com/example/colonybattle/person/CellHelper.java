package com.example.colonybattle.person;

import com.example.colonybattle.Colors.ConsoleColor;
import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.Engine;
import com.example.colonybattle.Vector2d;

public class CellHelper {


    Person person;
    CellHelper(Person person){
        this.person = person;

    }
    public void updateLife(int life){
        Engine.getFrame().setLifeAtPosition(person.position, person.status.getHealth());
    }

    public void updateLifeAndEnergy(int life,int energy){
        Engine.getFrame().setLifeAtPosition(person.position, person.status.getHealth());

    }
    public void deathColor(){
        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(ConsoleColor.PURPLE));
    }


    public void resetCell(Vector2d position){
        Engine.getFrame().setInitColor(position);
        Engine.getFrame().setLifeAtPosition(position, 0);
        Engine.getFrame().setInitial(position, ' ');
        removeImageFromCell(position);
        //Engine.getFrame().setInitColor(position);
    }

    public void newCellAt(Vector2d newPosition){
        ConsoleColor consoleColor = person.colony.getColor().getColor(); // Zakładamy, że Colony ma metodę getColor() zwracającą kolor kolonii
        Engine.getFrame().setColorAtPosition(newPosition, ColorConverter.convertColor(consoleColor));
        Engine.getFrame().setLifeAtPosition(newPosition, person.status.health); // Ustawiamy aktualną ilość życia osoby
        Engine.getFrame().setInitial(person.position, person.getInitial());
        addImageToCell(newPosition);
    }

    public void addImageToCell(Vector2d position){
        Engine.getFrame().setImageAtPosition(position, person.getImage());
    }

    public void removeImageFromCell(Vector2d position){
        Engine.getFrame().removeImageAtPosition(position);
    }

    public void healingColor(){

        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(ConsoleColor.BLUE));
        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(person.colony.getColor().getColor()));
    }
    public void energyEmitionColor(){
        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(ConsoleColor.BRIGHT_WHITE));
        try{
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(person.colony.getColor().getColor()));
    }
}
