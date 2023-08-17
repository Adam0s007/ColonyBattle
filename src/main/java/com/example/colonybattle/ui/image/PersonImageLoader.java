package com.example.colonybattle.ui.image;


import com.example.colonybattle.models.person.type.PersonType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PersonImageLoader implements ImageLoaderInterface<PersonType> {
    private static PersonImageLoader instance = null;
    public final String path = "src/main/resources/heads/";
    private Map<PersonType, ImageIcon> imageMap = new HashMap<>();

    private PersonImageLoader() {
        for (PersonType type : PersonType.values()) {
            imageMap.put(type, new ImageIcon(path + type.toString().toLowerCase() + ".png"));
        }
    }

    public static PersonImageLoader getInstance() {
        if (instance == null) {
            instance = new PersonImageLoader();
        }
        return instance;
    }

    public ImageIcon getImageForType(PersonType type) {
        return imageMap.get(type);
    }
}


