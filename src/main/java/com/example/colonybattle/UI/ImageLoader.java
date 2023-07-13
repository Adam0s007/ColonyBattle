package com.example.colonybattle.UI;


import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader {
    private static ImageLoader instance = null;

    private Map<PersonType, ImageIcon> imageMap = new HashMap<>();

    private ImageLoader() {
        for (PersonType type : PersonType.values()) {
            imageMap.put(type, new ImageIcon("heads/"+type.toString() + ".png"));
        }
    }

    public static ImageLoader getInstance() {
        if (instance == null) {
            instance = new ImageLoader();
        }
        return instance;
    }

    public ImageIcon getImageForType(PersonType type) {
        return imageMap.get(type);
    }
}


