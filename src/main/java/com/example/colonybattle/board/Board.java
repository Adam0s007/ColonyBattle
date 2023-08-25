package com.example.colonybattle.board;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.board.boardlocks.LockMapPosition;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.obstacle.ObstacleType;
import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.ui.frame.MyFrame;
import com.example.colonybattle.config.BoardConfig;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Getter
@Setter
public class Board {
    public static final int SIZE = BoardConfig.getInstance().getBoardSize();
    public static final int OBSTACLE_COUNT = BoardConfig.getInstance().getObstaclesAmount();
    private Map<String, Point2d> fields = new ConcurrentHashMap<>();
    private Map<String, Point2d> obstacleFields = new ConcurrentHashMap<>();
    private final LockMapPosition lockManager = new LockMapPosition();
    private List<Colony> allColonies;
    private ThreadPoolManager threadPoolManager = new ThreadPoolManager();
    private MyFrame frame = null;

    public Board(List<Colony> allColonies) {
        this.allColonies = allColonies;
    }
    public void start() {
        int totalPeopleCount = allColonies.stream().mapToInt((colony) -> colony.getPeopleManager().getTotalPeopleCount()).sum();
        threadPoolManager.start(totalPeopleCount);
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeopleManager().getPeople()) {
                threadPoolManager.submitTask(person);
            }
        }
        //statisticsPrinter.startPrintingStatistics(allColonies); - for testing only
    }
    public void initFields() {
        allColonies.stream()
                .flatMap(colony -> colony.getPeopleManager().getPeople().stream())
                .forEach(person -> initFieldAndLock(person.getPosition()));
        IntStream
                .range(0, SIZE)
                .boxed()
                .flatMap(i -> IntStream.range(0, SIZE).mapToObj(j -> new Point2d(i, j)))
                .filter(position -> !fields.containsKey(position.toString()))
                .forEach(this::initFieldAndLock);
        initObstacleFields();
    }
    public void initObstacleFields() {
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
    private void initFieldAndLock(Point2d position) {
        fields.put(position.toString(), position);
        lockManager.initializeLock(position);
    }
    public boolean isFieldOccupied(String stringPos) {
        return fields.containsKey(stringPos);
    }
    public Point2d getPoint2d(String stringPos) {
        return fields.get(stringPos);
    }
    public void startPerson(Person person) {
        threadPoolManager.submitTask(person);
    }
    public void stopPeople() {
        for (Colony colony : allColonies) {
            for (Person person : colony.getPeopleManager().getPeople()) {
                person.stop();
            }
        }
    }
    public void stop() {
        this.stopPeople();
        threadPoolManager.stop();
        //this.statisticsPrinter.stopPrintingStatistics(); - for testing only
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
        Iterator<Colony> iterator = allColonies.iterator();
        while (iterator.hasNext()) {
            Colony colony = iterator.next();
            if (colony.getPeopleManager().getPeopleCount() == 0) {
                System.out.println("Colony of type " + colony.getType() + " has been defeated!");
                iterator.remove();
            }
        }
    }
    public boolean isOnlyOneColonyLeft() {
        return allColonies.size() == 1;
    }
    public boolean isColonyEmpty(Colony colony) {
        return colony.getPeopleManager().getPeopleCount() == 0;
    }
}
