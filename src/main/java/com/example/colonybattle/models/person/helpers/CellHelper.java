package com.example.colonybattle.models.person.helpers;

import com.example.colonybattle.colors.ConsoleColor;
import com.example.colonybattle.colors.ColorConverter;
import com.example.colonybattle.launcher.Engine;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;

import java.awt.*;

public class CellHelper {


    Person person;
    public CellHelper(Person person){
        this.person = person;

    }
    public void updateLife(int life){
        Engine.getFrame().setLifeAtPosition(person.getPosition(), person.getStatus().getHealth());
    }

    public void updateLifeAndEnergy(int life,int energy){
        Engine.getFrame().setLifeAtPosition(person.getPosition(), person.getStatus().getHealth());

    }
    public void deathColor(){
        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(ConsoleColor.PURPLE));
    }

    public Color getDeathColor(){
        return ColorConverter.convertColor(ConsoleColor.PURPLE);
    }


    public void resetCell(Point2d position){
        Engine.getFrame().setInitColor(position);
        Engine.getFrame().setLifeAtPosition(position, 0);
        Engine.getFrame().setInitial(position, ' ');
        //Engine.getFrame().updateBackgroundAtPosition(position);
        removeImageFromCell(position);
        //Engine.getFrame().setInitColor(position);
    }

    public void newCellAt(Point2d newPosition){
        ConsoleColor consoleColor = person.getColony().getColor().getColor(); // Zakładamy, że Colony ma metodę getColor() zwracającą kolor kolonii
        Engine.getFrame().setColorAtPosition(newPosition, ColorConverter.convertColor(consoleColor));
        Engine.getFrame().setLifeAtPosition(newPosition, person.getStatus().getHealth()); // Ustawiamy aktualną ilość życia osoby
        Engine.getFrame().setInitial(person.getPosition(), person.getInitial());
        //Engine.getFrame().updateBackgroundAtPosition(newPosition);
        addImageToCell(newPosition);
    }

    public void addImageToCell(Point2d position){
        Engine.getFrame().setImageAtPosition(position, person.getImage());
    }

    public void removeImageFromCell(Point2d position){
        Engine.getFrame().removeImageAtPosition(position);
    }

    public void healingColor(){

        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(ConsoleColor.BLUE));
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(person.getColony().getColor().getColor()));
    }
    public void energyEmitionColor(){
        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(ConsoleColor.BRIGHT_WHITE));
        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(person.getColony().getColor().getColor()));
    }

    public void spawningColor(){
        Color color = new Color(255,140,0);
        Color oldColor = Engine.getFrame().getColorAtPosition(person.getPosition());
        Engine.getFrame().setColorAtPosition(person.getPosition(), color);

        Engine.getFrame().setColorAtPosition(person.getPosition(), ColorConverter.convertColor(person.getColony().getColor().getColor()));
        Engine.getFrame().setBackground(person.getPosition(), oldColor);
    }


}
