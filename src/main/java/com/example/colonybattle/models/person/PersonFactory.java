package com.example.colonybattle.models.person;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.characters.*;
import com.example.colonybattle.models.person.id.IdAllocator;
import com.example.colonybattle.config.PeopleNumber;
import com.example.colonybattle.models.person.type.PersonType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PersonFactory {
    private IdAllocator idAllocator;
    private Map<PersonType, Integer> personCountMap;
    private PeopleNumber peopleConfig;

    public PersonFactory() {
        this.idAllocator = IdAllocator.getInstance();
        this.peopleConfig = PeopleNumber.getInstance();
        initPersonCountMap();
    }

    private void initPersonCountMap() {
        personCountMap = new ConcurrentHashMap<>();
        Arrays.stream(PersonType.values())
                .forEach(type -> personCountMap.put(type, 0));
    }

    public Person createPerson(PersonType type, Point2d pos, Colony colony) {
        Person person;
        int newId = idAllocator.giveId();
        switch (type) {
            case FARMER:
                person = new Farmer(type, pos, colony, newId);
                break;
            case DEFENDER:
                person = new Defender(type, pos, colony, newId);
                break;
            case WARRIOR:
                person = new Warrior(type, pos, colony, newId);
                break;
            case WIZARD:
                person = new Wizard(type, pos, colony, newId);
                break;
            default:
                throw new IllegalArgumentException("Wrong person type");
        }
        incrementPersonCount(type);
        return person;
    }

    private synchronized void incrementPersonCount(PersonType type) {
        personCountMap.put(type, personCountMap.get(type) + 1);
    }

    public synchronized boolean isFull() {
        return Arrays.stream(PersonType.values())
                .allMatch(type -> personCountMap.get(type) >= getPeopleNumberForType(type));
    }

    public Person generateRandom(Colony colony, Point2d pos) {
        personCounterExecutor(colony);
        if (isFull()) return null;
        List<PersonType> types = Arrays.asList(PersonType.values());
        Collections.shuffle(types);
        return types.stream()
                .filter(type -> personCountMap.get(type) < getPeopleNumberForType(type))
                .findFirst()
                .map(type -> createPerson(type, pos, colony))
                .orElse(null);
    }

    // Pomocnicza metoda, która zwraca liczbę osób danego typu na podstawie konfiguracji
    private int getPeopleNumberForType(PersonType type) {
        switch (type) {
            case FARMER:
                return peopleConfig.getFarmerNumber();
            case DEFENDER:
                return peopleConfig.getDefenderNumber();
            case WARRIOR:
                return peopleConfig.getWarriorNumber();
            case WIZARD:
                return peopleConfig.getWizardNumber();
            default:
                throw new IllegalArgumentException("Nieznany typ osoby: " + type);
        }
    }

    public void personCounterExecutor(Colony colony) {
        Map<PersonType, Integer> countMap = getCountMapFromColony(colony);
        updatePersonCountMap(countMap);
    }

    private Map<PersonType, Integer> getCountMapFromColony(Colony colony) {
        return colony.getPeopleManager().getPeople().stream()
                .collect(Collectors.groupingBy(Person::getType, Collectors.summingInt(p -> 1)));
    }

    private void updatePersonCountMap(Map<PersonType, Integer> countMap) {
        countMap.forEach(personCountMap::put);
    }

    public synchronized void removePerson(Person person) {
        if (personCountMap.get(person.getType()) > 0) {
            personCountMap.put(person.getType(), personCountMap.get(person.getType()) - 1);
        }
    }
}
