package com.example.colonybattle;

import com.example.colonybattle.Colors.Color;
import com.example.colonybattle.LockManagement.LockMapPosition;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.person.Person;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Board {
    public static final int SIZE = 20;

    private Map<String,Vector2d> fields = new HashMap<>(); //zawiera pola, ktora byly odwiedzone, bądź aktualnie są okupowane
    private  final LockMapPosition lockManager = new LockMapPosition(Board.SIZE);
    private List<Colony> allColonies;
    private ExecutorService executorService;
    public Board(List<Colony> allColonies) {
        this.allColonies = allColonies;
    }

    public void start() {
        int totalPeopleCount = allColonies.stream().mapToInt(Colony::getTotalPeopleCount).sum();
        this.executorService = Executors.newFixedThreadPool(totalPeopleCount);

        for (Colony colony : allColonies) {
            for (Person person : colony.getPeople()) {
                executorService.submit(person);
            }
        }
    }
    //dodajmy wszystkie pola osob z wszystkich kolonii do hashSetu fields
    public void initFields() {
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeople()) {
                fields.put(person.getPosition().toString(),person.getPosition());
            }
        }
    }
    //funckja sprawdzająca czy dany wektor Vector2d znajduje się w fields
    public boolean isFieldOccupied(String stringPos) {
        return fields.containsKey(stringPos);
    }
    public Vector2d getVector2d(String stringPos) {
        return fields.get(stringPos);
    }

    public void startPerson(Person person) {//zakladamy ze osoba jest juz zwiazana z kolonią
        executorService.submit(person);
    }

    public void removePerson(Person person) { // disconnects person from colony and removes it from board
        person.die();
    }

    public void stopPeople() {
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeople()) {
                person.stop();
            }
        }
    }


    public void stop() {
        this.stopPeople();
        executorService.shutdown();
    }

    public void printBoard() {
        // Wyczyszczenie okna konsoli
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Person person = getPersonAtPosition(i, j); // Metoda do implementacji: zwraca osobę na danej pozycji lub null, jeśli nie ma tam osoby
                if (person != null) {
                    System.out.print(person.getColony().getColonyColor().getColor() + "x" + Color.RESET+" ");
                } else {
                    System.out.print(Color.YELLOW+"o "+Color.RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    private Person getPersonAtPosition(int i, int j) {
        Vector2d position = new Vector2d(i, j);

        //wiemy że getPeople zwraca hashset,
        for (Colony colony : allColonies) {
            Person person = colony.containsPerson(position);
            if (person != null) {
                return person;
            }
        }
        return null;
    }


    public LockMapPosition getLockManager() {
        return lockManager;
    }


}
