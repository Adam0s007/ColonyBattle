package com.example.colonybattle.ui.image;



import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ImageLoader<T> implements ImageLoaderInterface<T> {
    protected static ImageLoader instance = null;
    protected final String path;
    protected Map<T, ImageIcon> imageMap = new HashMap<>();

    protected ImageLoader(String path) {
        this.path = path;
    }

    protected void loadImages(Class<T> enumClass) {
        for (T type : enumClass.getEnumConstants()) {
            imageMap.put(type, new ImageIcon(path + type.toString().toLowerCase() + ".png"));
        }
    }

    @Override
    public ImageIcon getImageForType(T type) {
        return imageMap.get(type);
    }
}
