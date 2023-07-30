package com.example.colonybattle.time;

import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.models.person.Person;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameTimer {
    private int time = 0;
    private List<Colony> allColonies;
    private ScheduledExecutorService executor;

    public GameTimer(List<Colony> allColonies) {
        this.allColonies = allColonies;
        this.executor = Executors.newScheduledThreadPool(1);
    }

    public void start() {
        executor.scheduleAtFixedRate(this::tickAndIncreaseAge, 0, 1, TimeUnit.SECONDS);
    }

    public void stop() {
        executor.shutdown();
    }

    private void tickAndIncreaseAge() {
        tick();
        increaseAge();
    }

    private void tick() {
        time++;
    }

    private void increaseAge() {
        allColonies.stream()
                .flatMap(colony -> colony.getPeople().stream())
                .forEach(Person::increaseAge);
    }

    public int getTime() {
        return time;
    }
}
