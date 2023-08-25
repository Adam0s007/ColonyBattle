package com.example.colonybattle.models.person.actions.magic;

import com.example.colonybattle.models.person.Person;
import com.example.colonybattle.models.person.characters.Wizard;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HealCommand implements MagicCommand{
    private final Person myPerson;

    public final int HEALING_PERIOD = 4;
    public final int INITIAL_DELAY;
    public HealCommand(Person myPerson) {
        this.myPerson = myPerson;
        INITIAL_DELAY = ThreadLocalRandom.current().nextInt(0, 4);
    }

    @Override
    public void execute() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //System.out.println("ja sie wywoluję!");
        Runnable task = () -> {
            if (!myPerson.isRunning())
                executorService.shutdown();
            else {
                myPerson.getColony().getPeopleManager().getPeople().stream()
                        .filter(person -> person != myPerson && person.getStatus().getHealth() < person.getType().getHealth())
                        .findFirst()
                        .ifPresent(person -> {
                            if (myPerson.getStatus().getEnergy() > 0) {
                                person.healMe(2);
                                myPerson.getStatus().addEnergy(-1);
                            }
                        });
            }

        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, HEALING_PERIOD, TimeUnit.SECONDS);

    }
}
