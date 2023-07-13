package com.example.colonybattle.UI;


import com.example.colonybattle.person.PersonType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageLoader implements ImageLoaderInterface {
    private static ImageLoader instance = null;
    public final String path = "src/main/resources/heads/";
    private Map<PersonType, ImageIcon> imageMap = new HashMap<>();

    private ImageLoader() {
        for (PersonType type : PersonType.values()) {
            imageMap.put(type, new ImageIcon(path + type.toString().toLowerCase() + ".png"));
       //path from src to resources folder:
         //src\main\java\com\example\colonybattle\UI\heads
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


