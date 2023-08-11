package com.example.colonybattle.models.person.messages;
import com.example.colonybattle.board.position.Point2d;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DestinationMessage {
    Point2d destination;
}
