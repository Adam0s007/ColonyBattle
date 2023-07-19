package com.example.colonybattle.ui.image;

import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.ImageIcon;

public interface ImageLoaderInterface {
    ImageIcon getImageForType(PersonType type);
}

