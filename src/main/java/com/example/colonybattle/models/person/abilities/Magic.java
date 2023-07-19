package com.example.colonybattle.models.person.abilities;

import com.example.colonybattle.board.position.Vector2d;

public interface Magic {
    void wand(Vector2d vec);

    void performAbsorption();
    void healFriends();
}
