package com.example.colonybattle.board.boardlocks;

import com.example.colonybattle.board.position.Point2d;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class LockMapPosition {
    private final Map<Point2d, Semaphore> lockMap = new ConcurrentHashMap<>();

    public LockMapPosition(){};
    public void initializeLock(Point2d position) {
        lockMap.putIfAbsent(position, new Semaphore(1));
    }

    public boolean tryAcquireLock(Point2d position){
        try{
            Semaphore semaphore = lockMap.get(position);
            return semaphore != null && semaphore.tryAcquire();
        } catch (Exception e){
            return false;
        }
    }

    public void releaseLock(Point2d position) {
        Semaphore semaphore = lockMap.get(position);
        if (semaphore != null) {
            semaphore.release();
        }
    }
}
