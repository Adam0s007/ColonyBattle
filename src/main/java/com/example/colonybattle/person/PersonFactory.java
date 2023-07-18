package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.ConcreteCharacters.Defender;
import com.example.colonybattle.person.ConcreteCharacters.Farmer;
import com.example.colonybattle.person.ConcreteCharacters.Warrior;
import com.example.colonybattle.person.ConcreteCharacters.Wizard;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PersonFactory {
    private static int available_id = 0;

    private IdAllocator idAllocator;
    private Map<PersonType, Integer> personCountMap;

    private int newId(){
        return idAllocator.giveId();
    }
    public PersonFactory() {
        this.idAllocator = IdAllocator.getInstance();
        personCountMap = new ConcurrentHashMap<>();
        for (PersonType type : PersonType.values()) {
            personCountMap.put(type, 0);
        }
    }

    public Person createPerson(PersonType type, Vector2d pos, Colony colony){
            switch (type) {
                case FARMER:
                    return new Farmer(type, pos, colony, newId());
                case DEFENDER:
                    return new Defender(type, pos, colony, newId());
                case WARRIOR:
                    return new Warrior(type, pos, colony, newId());
                case WIZARD:
                    return new Wizard(type, pos, colony, newId());
                default:
                    System.out.println("Wrong person type");
                    return null;
            }
    }

    public boolean isFull(){
        if(personCountMap.get(PersonType.FARMER) == PeopleNumber.FARMER_NUMBER.getNumber() &&
                personCountMap.get(PersonType.DEFENDER) == PeopleNumber.DEFENDER_NUMBER.getNumber() &&
                personCountMap.get(PersonType.WARRIOR) == PeopleNumber.WARRIOR_NUMBER.getNumber() &&
                personCountMap.get(PersonType.WIZARD) == PeopleNumber.WIZARD_NUMBER.getNumber()){
            return true;
        }
        return false;
    }
    public Person generateRandom(Colony colony, Vector2d pos) {
        personCounterExecutor(colony);
        if (isFull()) return null;
        List<PersonType> types = new ArrayList<>(Arrays.asList(PersonType.values()));
        Collections.shuffle(types); // mieszamy typy aby losowość była bardziej naturalna
        return types.stream()
                .filter(type -> personCountMap.get(type) < PeopleNumber.valueOf(type.toString().toUpperCase() + "_NUMBER").getNumber())
                .findFirst()
                .map(type -> {
                    personCountMap.put(type, personCountMap.get(type) + 1);
                    return createPerson(type, pos, colony);
                })
                .orElse(null);
    }

    public void personCounterExecutor(Colony colony){
        // Initialize the count map
        Map<PersonType, Integer> countMap = new HashMap<>();
        for (PersonType type : PersonType.values()) {
            countMap.put(type, 0);
        }

        // Iterate through the people in the colony and count the number of each type
        for (Person person : colony.getPeople()) {
            countMap.computeIfPresent(person.getType(), (type, count) -> count + 1);
        }

        // Update the overall person count map with the count from this colony
        for (PersonType type : PersonType.values()) {
            personCountMap.put(type, countMap.get(type));
        }
    }

    public void removePerson(Person person){
        if(personCountMap.get(person.getType()) > 0)
        personCountMap.put(person.getType(), personCountMap.get(person.getType()) - 1);
    }

}

