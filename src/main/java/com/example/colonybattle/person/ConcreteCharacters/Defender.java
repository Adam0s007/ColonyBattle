package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.UI.ImageLoader;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;

import javax.swing.*;

public class Defender extends Person {

    public Defender(PersonType type, Vector2d position, Colony colony, int id) {
        super(type.getHealth(), type.getEnergy(), type.getStrength(), position, colony, type.getLandAppropriation(),id);  // Wartość 10 to przykładowa wartość landAppropriation dla Warrior
        status.setType(type);

    }
    @Override
    public Character getInitial() {
        return 'D'; // Dla Obrońcy
    }
    // Implementacja metod...

    @Override
    public ImageIcon getImage() {
        return imageLoader.getImageForType(getType());
    }
}
