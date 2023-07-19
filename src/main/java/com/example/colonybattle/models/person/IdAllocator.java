package com.example.colonybattle.models.person;

import java.util.concurrent.atomic.AtomicInteger;

public class IdAllocator {

    private static IdAllocator instance = null;
    private AtomicInteger currentId;

    private IdAllocator() {
        currentId = new AtomicInteger(0);
    }

    public static synchronized IdAllocator getInstance() {
        if(instance == null) {
            instance = new IdAllocator();
        }
        return instance;
    }

    public int giveId() {
        return currentId.getAndIncrement();
    }
}
