package com.example.colonybattle.colony;

import lombok.Getter;

import java.util.concurrent.Semaphore;


@Getter
public class PointsManager {
    private int points;
    private final Semaphore pointSemaphore = new Semaphore(1);
    public PointsManager(int points) {
        this.points = points;

    }
    public PointsManager() {
        this.points = 0;
    }

    public void addPoints(int points) {
        try {
            pointSemaphore.acquire();
            this.points = Math.max(points + this.points, 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pointSemaphore.release();
        }
    }

}

