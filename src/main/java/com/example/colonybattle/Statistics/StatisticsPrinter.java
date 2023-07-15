package com.example.colonybattle.Statistics;

import com.example.colonybattle.Colors.ConsoleColor;
import com.example.colonybattle.colony.Colony;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StatisticsPrinter {

    private final ScheduledExecutorService scheduler;

    public StatisticsPrinter() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void startPrintingStatistics(List<Colony> allColonies) {
        final Runnable statsPrinter = () -> {
            allColonies.stream().forEach(colony -> {
                System.out.println(colony.getType() + ": " + colony.getPeopleCount());
                colony.getPeople().stream()
                        .forEach(person -> System.out.println(person.getColony().getColor().getColor() + " " + person.getPosition() + ConsoleColor.RESET));
            });
        };

        // Here we schedule the task to run every 5 seconds.
        // This will start 2 second after the method is called, and then repeat every 5 seconds.
        scheduler.scheduleAtFixedRate(statsPrinter, 2, 5, TimeUnit.SECONDS);
    }

    // Method to stop the scheduler when you no longer need the statistics
    public void stopPrintingStatistics() {
        scheduler.shutdown();
        try {
            // Wait for existing tasks to complete
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Scheduler did not terminate in the allotted time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

