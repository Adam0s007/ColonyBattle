package com.example.colonybattle.utils;


public class ThreadUtils {
    private static ThreadUtils instance = null;

    private ThreadUtils() {}

    public static ThreadUtils getInstance() {
        if (instance == null) {
            instance = new ThreadUtils();
        }
        return instance;
    }

    public <T extends Number> void pause(T millis) {
        try {
            Thread.sleep(millis.longValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

