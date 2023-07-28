package com.example.colonybattle.models.person.messages;
import com.example.colonybattle.board.position.Point2d;
public class DestinationMessage {
    Point2d destination;
    public DestinationMessage(Point2d destination) {
        this.destination = destination;
    }

    public Point2d getDestination() {
        return destination;
    }
}
