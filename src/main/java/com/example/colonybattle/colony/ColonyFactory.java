package com.example.colonybattle.colony;

import com.example.colonybattle.colors.ColonyColor;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.PersonFactory;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.config.PeopleNumber;
import com.example.colonybattle.models.person.type.PersonType;


public class ColonyFactory {

    private Set<Point2d> usedPositions;
    private PeopleNumber peopleNumber;
    public ColonyFactory() {
        usedPositions = new HashSet<>();
        peopleNumber = PeopleNumber.getInstance();
    }
    //make function creating proper color type of ColonyColor based on enum ColonyType
    public ColonyColor getColonyColor(ColonyType type){
        switch (type) {
            case VOLCANIC_NATION:
                return ColonyColor.VOLCANIC_NATION;
            case ICE_NATION:
                return ColonyColor.ICE_NATION;
            case JUNGLE_NATION:
                return ColonyColor.JUNGLE_NATION;
            case DESERT_NATION:
                return ColonyColor.DESERT_NATION;
            default:
                throw new IllegalArgumentException("Invalid colony type");
        }
    }


    public Colony createColony(ColonyType type,Board board){
        // Tworzymy puste zbiory ludzi i pól
        PersonFactory personFactory = new PersonFactory();
        Set<Person> people = ConcurrentHashMap.newKeySet();
        Set<Point2d> fields =ConcurrentHashMap.newKeySet();
        ColonyColor color = getColonyColor(type);
        Colony colony = new Colony(type, people, fields, 0, color,board,personFactory);
        Point2d startPos;
        Point2d endPos;

        switch (type) {
            case VOLCANIC_NATION:
                startPos = new Point2d(0, 0);
                endPos = new Point2d(Board.SIZE / 2, Board.SIZE / 2);
                break;
            case ICE_NATION:
                startPos = new Point2d(Board.SIZE / 2, 0);
                endPos = new Point2d(Board.SIZE, Board.SIZE / 2);
                break;
            case JUNGLE_NATION:
                startPos = new Point2d(0, Board.SIZE / 2);
                endPos = new Point2d(Board.SIZE / 2, Board.SIZE);
                break;
            case DESERT_NATION:
                startPos = new Point2d(Board.SIZE / 2, Board.SIZE / 2);
                endPos = new Point2d(Board.SIZE, Board.SIZE);
                break;
            default:
                throw new IllegalArgumentException("Invalid colony type");
        }

        Map<PersonType, Integer> personTypeToNumberMap = Map.of(
                PersonType.FARMER, peopleNumber.getFarmerNumber(),
                PersonType.DEFENDER, peopleNumber.getDefenderNumber(),
                PersonType.WARRIOR, peopleNumber.getWarriorNumber(),
                PersonType.WIZARD, peopleNumber.getWizardNumber()
        );
        personTypeToNumberMap.forEach((personType, number) -> {
            IntStream.range(0, number).forEach(i -> {
                Point2d position = getRandomPositionWithin(startPos, endPos);

                personFactory.createPerson(personType, position, colony);
            });
        });
        return colony;
    }

    private Point2d getRandomPositionWithin(Point2d startPos, Point2d endPos) {
        Point2d newPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(startPos.getX(), endPos.getX());
            int y = ThreadLocalRandom.current().nextInt(startPos.getY(), endPos.getY());
            newPosition = new Point2d(x, y);
        } while (usedPositions.contains(newPosition));
        usedPositions.add(newPosition);
        return newPosition;
    }
}
