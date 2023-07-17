package com.example.colonybattle.person;

public class IdAllocator {

    private static IdAllocator instance = null;
    private int currentId;

    private IdAllocator() {
        currentId = 0;
    }

    public static synchronized IdAllocator getInstance() {
        if(instance == null) {
            instance = new IdAllocator();
        }
        return instance;
    }

    public synchronized int giveId() {
        return currentId++;
    }
}

