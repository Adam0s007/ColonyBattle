package com.example.colonybattle;

public enum Color {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    BLUE("\033[0;34m"),
    YELLOW("\033[0;33m"),
    RESET("\033[0m"); // używane do resetowania koloru do domyślnego

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
