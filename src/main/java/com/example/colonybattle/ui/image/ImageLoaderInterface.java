package com.example.colonybattle.ui.image;

import javax.swing.ImageIcon;

public interface ImageLoaderInterface<T> {
    ImageIcon getImageForType(T type);
}

