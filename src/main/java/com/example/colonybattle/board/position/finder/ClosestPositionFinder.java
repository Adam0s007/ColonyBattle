package com.example.colonybattle.board.position.finder;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;

public interface ClosestPositionFinder {
    Point2d findClosestPosition(Person person);
}
