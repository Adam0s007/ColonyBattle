package com.example.colonybattle;

import com.example.colonybattle.person.Person;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Board {
    public static final int SIZE = 20;
    private List<Colony> allColonies;
    private ExecutorService executorService;
    public Board(List<Colony> allColonies) {
        this.allColonies = allColonies;
        int totalPeopleCount = allColonies.stream().mapToInt(Colony::getTotalPeopleCount).sum();
        this.executorService = Executors.newFixedThreadPool(totalPeopleCount);
    }

    public void start() {
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeople()) {
                executorService.submit(person);
            }
        }
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
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Person person = getPersonAtPosition(i, j); // Metoda do implementacji: zwraca osobę na danej pozycji lub null, jeśli nie ma tam osoby
                if (person != null) {
                    System.out.print(person.getColony().getColonyColor().getColor() + "P" + Color.RESET + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
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


}
