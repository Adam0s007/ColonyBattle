package com.example.colonybattle.person.ConcreteCharacters;

import com.example.colonybattle.person.PersonType;

public class TypePriority {
    private static TypePriority instance;

    private TypePriority() {
    }

    public static TypePriority getInstance() {
        if (instance == null) {
            instance = new TypePriority();
        }
        return instance;
    }

    public int getTypePriority(PersonType type) {
        switch (type) {
            case WIZARD:
                return 1; // Highest priority
            case FARMER:
                return 2;
            case DEFENDER:
                return 3;
            default:
                return Integer.MAX_VALUE; // Lowest priority for all other types
        }
    }
}

