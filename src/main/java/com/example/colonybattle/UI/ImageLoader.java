package com.example.colonybattle.UI;


import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {

    private Map<PersonType, ImageIcon> imageMap = new HashMap<>();

    public ImageLoader() {
        for (PersonType type : PersonType.values()) {
            imageMap.put(type, new ImageIcon(  type.toString() + ".png"));
        }
    }

    public ImageIcon getImageForType(PersonType type) {
        return imageMap.get(type);
    }
}

