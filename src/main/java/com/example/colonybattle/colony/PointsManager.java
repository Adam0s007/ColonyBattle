package com.example.colonybattle.colony;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class PointsManager {
    private final AtomicInteger points;

    public PointsManager(int points) {
        this.points = new AtomicInteger(points);
    }

    public PointsManager() {
        this.points = new AtomicInteger(0);
    }

    public void addPoints(int points) {
        this.points.updateAndGet(current -> Math.max(current + points, 0));
    }

    public int getPoints() {
        return points.get();
    }
}
