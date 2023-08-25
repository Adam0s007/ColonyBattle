package com.example.colonybattle.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpawningTime {
    private static SpawningTime instance;

    private int spawningTime = 30;
    private SpawningTime() {}
    public static SpawningTime getInstance() {
        if (instance == null) {
            instance = new SpawningTime();
        }
        return instance;
    }
}
