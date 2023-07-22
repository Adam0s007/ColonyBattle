package com.example.colonybattle.models.person.actions.movement;

import com.example.colonybattle.board.position.Point2d;

public interface Movement {
    void walk();
    Point2d calculateNewPosition(Point2d position, Point2d directionVector);
}
