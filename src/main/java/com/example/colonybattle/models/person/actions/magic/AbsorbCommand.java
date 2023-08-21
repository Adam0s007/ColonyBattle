package com.example.colonybattle.models.person.actions.magic;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.Person;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AbsorbCommand implements MagicCommand {
    private final Person myPerson;
    public final int WAND_PERIOD = 3;
    public final int INITIAL_DELAY;
    public AbsorbCommand(Person myPerson) {
        this.myPerson = myPerson;
        INITIAL_DELAY = ThreadLocalRandom.current().nextInt(0, 4);
    }
    @Override
    public void execute() {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        Runnable task = () -> {
            if (!myPerson.isRunning()) { // if running is false, shut down the executor service
                executorService.shutdown();
            } else {
                Point2d closestPersonPosition = myPerson.findClosestPosition();
                //System.out.println("Wizard closest enemy position: " + closestPersonPosition);
                if (closestPersonPosition != null) {
                    Person person = closestPersonPosition.getPerson();
                    if (person != null)
                        wand(closestPersonPosition);
                }
            }
        };
        executorService.scheduleAtFixedRate(task, INITIAL_DELAY, WAND_PERIOD, TimeUnit.SECONDS);
    }

    public void wand(Point2d vec) {
        Person person = vec.getPerson();
        if(person != null){//zabiera mu energie
            double random = ThreadLocalRandom.current().nextDouble(); // Generate a random number between 0 and 1
            if (random > 0.4) {
                person.cellHelper.energyEmitionColor();
                myPerson.getAttackPerformer().attackAndPossiblyKill(person);
                myPerson.getStatus().addEnergy(1);
            } else {
                person.cellHelper.energyEmitionColor();
                person.getStatus().addEnergy(-2);
                myPerson.getStatus().addEnergy(2);
            }
        }
    }



}
