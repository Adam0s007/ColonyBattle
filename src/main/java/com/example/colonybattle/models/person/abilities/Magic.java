package com.example.colonybattle.models.person.abilities;

import com.example.colonybattle.board.position.Point2d;

public interface Magic {
    void wand(Point2d vec);

    void performAbsorption();
    void healFriends();
}
