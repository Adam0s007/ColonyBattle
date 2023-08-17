package com.example.colonybattle.models.obstacle;

public enum ObstacleType {
    STONE("stone");

    private final String name;

    ObstacleType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
