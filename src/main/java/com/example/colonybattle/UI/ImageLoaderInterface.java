package com.example.colonybattle.UI;

import com.example.colonybattle.person.PersonType;

import javax.swing.ImageIcon;

public interface ImageLoaderInterface {
    ImageIcon getImageForType(PersonType type);
}

