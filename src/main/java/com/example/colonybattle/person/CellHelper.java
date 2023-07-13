package com.example.colonybattle.person;

import com.example.colonybattle.Colors.Color;
import com.example.colonybattle.Colors.ColorConverter;
import com.example.colonybattle.Engine;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.Colony;
public class CellHelper {


    Person person;
    CellHelper(Person person){
        this.person = person;

    }
    void updateLife(int life){
        Engine.getFrame().setLifeAtPosition(person.position, person.status.getHealth());
    }

    void deathColor(){
        Engine.getFrame().setColorAtPosition(person.position, ColorConverter.convertColor(Color.PURPLE));
    }

    void resetCell(Vector2d position){
        Engine.getFrame().setInitColor(position);
        Engine.getFrame().setLifeAtPosition(position, 0);
        Engine.getFrame().setInitial(position, ' ');
        removeImageFromCell(position);
    }

    void newCellAt(Vector2d newPosition){
        Color color = person.colony.getColor().getColor(); // Zakładamy, że Colony ma metodę getColor() zwracającą kolor kolonii
        Engine.getFrame().setColorAtPosition(newPosition, ColorConverter.convertColor(color));
        Engine.getFrame().setLifeAtPosition(newPosition, person.status.health); // Ustawiamy aktualną ilość życia osoby
        Engine.getFrame().setInitial(person.position, person.getInitial());
        addImageToCell(newPosition);
    }

    void addImageToCell(Vector2d position){
        Engine.getFrame().setImageAtPosition(position, person.getImage());
    }

    void removeImageFromCell(Vector2d position){
        Engine.getFrame().removeImageAtPosition(position);
    }
}
