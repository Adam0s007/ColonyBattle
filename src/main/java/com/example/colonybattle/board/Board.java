package com.example.colonybattle.board;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.colors.ConsoleColor;
import com.example.colonybattle.board.boardlocks.LockMapPosition;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.obstacle.ObstacleType;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.ui.frame.MyFrame;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Getter
public class Board {
    public static final int SIZE = 15;
    public static final int OBSTACLE_COUNT = 10;
    private Map<String, Point2d> fields = new ConcurrentHashMap<>(); //zawiera pola, ktora byly odwiedzone, bądź aktualnie są okupowane
    private Map<String,Point2d> obstacleFields = new ConcurrentHashMap<>(); //zawiera pola, ktore sa zajete przez przeszkody
    private  final LockMapPosition lockManager = new LockMapPosition();
    private List<Colony> allColonies;
    public ExecutorService executorService;
    private  MyFrame frame = null;
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
        //statisticsPrinter.startPrintingStatistics(allColonies); - for testing only
    }
    //dodajmy wszystkie pola osob z wszystkich kolonii do hashSetu fields
    public void initFields() {
        allColonies.stream()
                .flatMap(colony -> colony.getPeople().stream())
                .map(Person::getPosition)
                .forEach(this::initFieldAndLock);

        IntStream.range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE).mapToObj(j -> new Point2d(i, j)))
                .filter(position -> !fields.containsKey(position.toString()))
                .forEach(this::initFieldAndLock);
    initObstacleFields();
    }
    public void initObstacleFields(){//randomowe pola, na ktorych nie ma osob
        Random random = new Random();
        IntStream.range(0, OBSTACLE_COUNT)
                .boxed()
                .map(i -> fields.get(new Point2d(random.nextInt(SIZE), random.nextInt(SIZE)).toString()))
                .filter(position -> fields.get(position.toString()).getPerson() == null)
                .forEach(position -> {
                    obstacleFields.put(position.toString(), position);
                    position.setObstacleType(ObstacleType.STONE);
                });
    }
    public void initFrame(MyFrame frame) {
        this.frame = frame;
    }
    private void initFieldAndLock(Point2d position) {
        fields.put(position.toString(), position);
        lockManager.initializeLock(position);
    }


    //funckja sprawdzająca czy dany wektor Vector2d znajduje się w fields
    public boolean isFieldOccupied(String stringPos) {
        return fields.containsKey(stringPos);
    }
    public Point2d getPoint2d(String stringPos) {
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
        try{
            //this.statisticsPrinter.stopPrintingStatistics(); - for testing only
            executorService.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

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

    public MyFrame getFrame() {
        return frame;
    }

    public Map<String, Point2d> getObstacleFields() {
        return obstacleFields;
    }
}
