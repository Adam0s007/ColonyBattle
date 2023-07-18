package com.example.colonybattle.LockManagement;

import com.example.colonybattle.Vector2d;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class LockMapPosition {
    private final Map<Vector2d, Semaphore> lockMap = new ConcurrentHashMap<>();

    public LockMapPosition(){};
    public void initializeLock(Vector2d position) {
        lockMap.putIfAbsent(position, new Semaphore(1));
    }

    public boolean tryAcquireLock(Vector2d position){
        try{
            Semaphore semaphore = lockMap.get(position);
            return semaphore != null && semaphore.tryAcquire();
        } catch (Exception e){
            return false;
        }
    }

    public void releaseLock(Vector2d position) {
        Semaphore semaphore = lockMap.get(position);
        if (semaphore != null) {
            semaphore.release();
        }
    }
}
