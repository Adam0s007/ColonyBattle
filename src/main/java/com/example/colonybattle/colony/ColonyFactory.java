package com.example.colonybattle.colony;

import com.example.colonybattle.colors.ColonyColor;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.PersonFactory;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.board.position.Vector2d;
import com.example.colonybattle.models.person.type.PersonType;


public class ColonyFactory {

    private Set<Vector2d> usedPositions;
    public ColonyFactory() {
        usedPositions = new HashSet<>();
    }
    //make function creating proper color type of ColonyColor based on enum ColonyType
    public ColonyColor getColonyColor(ColonyType type){
        switch (type) {
            case COLONY1:
                return ColonyColor.COLONY1;
            case COLONY2:
                return ColonyColor.COLONY2;
            case COLONY3:
                return ColonyColor.COLONY3;
            case COLONY4:
                return ColonyColor.COLONY4;
            default:
                throw new IllegalArgumentException("Invalid colony type");
        }
    }


    public Colony createColony(ColonyType type,Board board){
        // Tworzymy puste zbiory ludzi i pól
        PersonFactory personFactory = new PersonFactory();
        Set<Person> people = ConcurrentHashMap.newKeySet();
        Set<Vector2d> fields =ConcurrentHashMap.newKeySet();
        ColonyColor color = getColonyColor(type);
        Colony colony = new Colony(type, people, fields, 0, color,board,personFactory);
        Vector2d startPos;
        Vector2d endPos;

        switch (type) {
            case COLONY1:
                startPos = new Vector2d(0, 0);
                endPos = new Vector2d(Board.SIZE / 2, Board.SIZE / 2);
                break;
            case COLONY2:
                startPos = new Vector2d(Board.SIZE / 2, 0);
                endPos = new Vector2d(Board.SIZE, Board.SIZE / 2);
                break;
            case COLONY3:
                startPos = new Vector2d(0, Board.SIZE / 2);
                endPos = new Vector2d(Board.SIZE / 2, Board.SIZE);
                break;
            case COLONY4:
                startPos = new Vector2d(Board.SIZE / 2, Board.SIZE / 2);
                endPos = new Vector2d(Board.SIZE, Board.SIZE);
                break;
            default:
                throw new IllegalArgumentException("Invalid colony type");
        }

        // Dodajemy rolników (farmers)
        for (int i = 0; i < 3; i++) {
            Vector2d position = getRandomPositionWithin(startPos, endPos);
            colony.addField(position);
            personFactory.createPerson(PersonType.FARMER, position, colony);
        }

        // Dodajemy obrońców (defenders)
        for (int i = 0; i < 2; i++) {
            Vector2d position = getRandomPositionWithin(startPos, endPos);
            personFactory.createPerson(PersonType.DEFENDER, position, colony);

        }

        // Dodajemy maga (wizard)
        Vector2d wizardPosition = getRandomPositionWithin(startPos, endPos);
        personFactory.createPerson(PersonType.WIZARD, wizardPosition, colony);

        // Dodajemy wojowników (warriors)
        for (int i = 0; i < 4; i++) {
            Vector2d position = getRandomPositionWithin(startPos, endPos);
            personFactory.createPerson(PersonType.WARRIOR, position, colony);
        }

        return colony;
    }

    private Vector2d getRandomPositionWithin(Vector2d startPos, Vector2d endPos) {
        Vector2d newPosition;
        do {
            int x = ThreadLocalRandom.current().nextInt(startPos.getX(), endPos.getX());
            int y = ThreadLocalRandom.current().nextInt(startPos.getY(), endPos.getY());
            newPosition = new Vector2d(x, y);
        } while (usedPositions.contains(newPosition));
        usedPositions.add(newPosition);
        return newPosition;
    }
}
