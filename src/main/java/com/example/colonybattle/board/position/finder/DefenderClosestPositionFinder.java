package com.example.colonybattle.board.position.finder;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.type.PersonType;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefenderClosestPositionFinder implements ClosestPositionFinder{

    @Override
    public Point2d findClosestPosition(Person myPerson) {
        Point2d closestPersonPosition = null;
        List<Colony> colonies = myPerson.getBoardRef().getAllColonies();
        Optional<Person> closestPerson = colonies.stream()
                .filter(colony -> colony.equals(myPerson.getColony())) // filter out this person's colony
                .flatMap(colony -> colony.getPeople().stream())     // get stream of people from other colonies
                .filter(person -> person.getType() != PersonType.DEFENDER)            // filter out this person
                .min(Comparator.comparing(person -> myPerson.getPosition().distanceTo(person.getPosition()))); // find person with minimum distance
        if (closestPerson.isPresent()) {
            closestPersonPosition = closestPerson.get().getPosition();
        }
        //System.out.println(this.colony.getType()+"Wywoluje sie!!");
        return closestPersonPosition;
    }
}
