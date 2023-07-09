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

    public void stop() {
        executorService.shutdown();
    }


}
