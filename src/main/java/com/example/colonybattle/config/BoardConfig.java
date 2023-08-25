package com.example.colonybattle.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardConfig {
    private static BoardConfig instance;
    private int boardSize = 25;
    private int obstaclesAmount = 10;
    private BoardConfig() {}
    public static BoardConfig getInstance() {
        if (instance == null) {
            instance = new BoardConfig();
        }
        return instance;
    }
}
