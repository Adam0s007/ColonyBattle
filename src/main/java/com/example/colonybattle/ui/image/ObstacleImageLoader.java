package com.example.colonybattle.ui.image;

import com.example.colonybattle.models.obstacle.ObstacleType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ObstacleImageLoader implements ImageLoaderInterface<ObstacleType> {
    private static ObstacleImageLoader instance = null;
    public final String path = "src/main/resources/obstacles/";
    private Map<ObstacleType, ImageIcon> imageMap = new HashMap<>();

    private ObstacleImageLoader() {
        for (ObstacleType type : ObstacleType.values()) {
            imageMap.put(type, new ImageIcon(path + type.toString().toLowerCase() + ".png"));
        }
    }

    public static ObstacleImageLoader getInstance() {
        if (instance == null) {
            instance = new ObstacleImageLoader();
        }
        return instance;
    }

    @Override
    public ImageIcon getImageForType(ObstacleType type) {
        return imageMap.get(type);
    }
}
