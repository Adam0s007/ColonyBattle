package com.example.colonybattle.Colors;

public enum Color {
    RED("\033[0;31m"),
    GREEN("\033[0;32m"),
    BLUE("\033[0;34m"),
    YELLOW("\033[0;33m"),
    PURPLE("\033[0;35m"), // fioletowy
    CYAN("\033[0;36m"), // turkusowy
    WHITE("\033[0;37m"), // biały
    BLACK("\033[0;30m"), // czarny
    BRIGHT_RED("\033[0;91m"), // jasnoczerwony
    BRIGHT_GREEN("\033[0;92m"), // jasnozielony
    BRIGHT_BLUE("\033[0;94m"), // jasnoniebieski
    BRIGHT_YELLOW("\033[0;93m"), // jasnożółty
    BRIGHT_PURPLE("\033[0;95m"), // jasnofioletowy
    BRIGHT_CYAN("\033[0;96m"), // jasnoturkusowy
    BRIGHT_WHITE("\033[0;97m"), // jasnobiały
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

