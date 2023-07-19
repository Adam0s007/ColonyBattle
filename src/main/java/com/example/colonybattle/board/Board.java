package com.example.colonybattle.board;

import com.example.colonybattle.board.position.Vector2d;
import com.example.colonybattle.colors.ConsoleColor;
import com.example.colonybattle.board.boardlocks.LockMapPosition;
import com.example.colonybattle.statistics.StatisticsPrinter;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Board {
    public static final int SIZE = 20;
    private Map<String, Vector2d> fields = new ConcurrentHashMap<>(); //zawiera pola, ktora byly odwiedzone, bądź aktualnie są okupowane
    private  final LockMapPosition lockManager = new LockMapPosition();
    private List<Colony> allColonies;
    public ExecutorService executorService;
    private StatisticsPrinter statisticsPrinter;
    public Board(List<Colony> allColonies) {
        this.allColonies = allColonies;
        this.statisticsPrinter = new StatisticsPrinter();
    }

    public Map<String, Vector2d> getFields() {
        return fields;
    }
    public void start() {
        int totalPeopleCount = allColonies.stream().mapToInt(Colony::getTotalPeopleCount).sum();
        this.executorService = Executors.newFixedThreadPool(100);
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeople()) {
                executorService.submit(person);
            }
        }
        statisticsPrinter.startPrintingStatistics(allColonies);
    }
    //dodajmy wszystkie pola osob z wszystkich kolonii do hashSetu fields
    public void initFields() {
        allColonies.stream()
                .flatMap(colony -> colony.getPeople().stream())
                .map(Person::getPosition)
                .forEach(this::initFieldAndLock);

        IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE).mapToObj(j -> new Vector2d(i, j)))
                .filter(position -> !fields.containsKey(position.toString()))
                .forEach(this::initFieldAndLock);
    }
    private void initFieldAndLock(Vector2d position) {
        fields.put(position.toString(), position);
        lockManager.initializeLock(position);
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
            colony.shutdown();
        }
    }
    public void stop() {
        this.stopPeople();
        executorService.shutdown();
        try{
            this.statisticsPrinter.stopPrintingStatistics();
            executorService.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    public void printBoard() {
        // Wyczyszczenie okna konsoli
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Person person = getPersonAtPosition(i, j); // Metoda do implementacji: zwraca osobę na danej pozycji lub null, jeśli nie ma tam osoby
                if (person != null) {
                    System.out.print(person.getColony().getColonyColor().getColor() + "x" + ConsoleColor.RESET+" ");
                } else {
                    System.out.print(ConsoleColor.YELLOW+"o "+ ConsoleColor.RESET);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    private Person getPersonAtPosition(int i, int j) {
        Vector2d position = new Vector2d(i, j);

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
    public List<Colony> getAllColonies() {
        return allColonies;
    }
    public void getLongestSurvivingColony() {
        Colony longestSurvivingColony = allColonies.stream()
                .max(Comparator.comparing(colony -> colony.getLifetime().toSeconds()))
                .orElse(null);

        if (longestSurvivingColony != null) {
            long lifetimeInSeconds = longestSurvivingColony.getLifetime().toSeconds();
            System.out.println("The longest surviving colony is of type " + longestSurvivingColony.getType() +
                    " with a lifetime of " + lifetimeInSeconds + " seconds, ");
        } else {
            System.out.println("No more colonies survived.");
        }
    }


    public void removeDefeatedColony() {
        // Utworzenie iteratora, aby umożliwić bezpieczne usuwanie elementów podczas iteracji
        Iterator<Colony> iterator = allColonies.iterator();
        while(iterator.hasNext()) {
            Colony colony = iterator.next();
            if(colony.getPeopleCount() == 0) {  // załóżmy, że getPeopleCount() zwraca liczbę ludzi w kolonii
                System.out.println("Colony of type " + colony.getType() + " has been defeated!");
                // Usunięcie kolonii z listy
                iterator.remove();
            }
        }
    }
    public boolean isOnlyOneColonyLeft() {
        return allColonies.size() == 1;
    }
    public boolean isColonyEmpty(Colony colony) {
        return colony.getPeopleCount() == 0;
    }
}