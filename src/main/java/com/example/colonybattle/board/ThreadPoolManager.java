package com.example.colonybattle.board;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private ExecutorService executorService;

    public void start(int totalPeopleCount) {
        this.executorService = Executors.newFixedThreadPool(totalPeopleCount);
    }

    public void stop() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error waiting for ExecutorService shutdown");
        }
    }

    public void submitTask(Runnable task) {
        executorService.submit(task);
    }
}

