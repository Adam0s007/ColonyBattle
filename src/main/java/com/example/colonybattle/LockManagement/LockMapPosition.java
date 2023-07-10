package com.example.colonybattle.LockManagement;
import java.util.concurrent.Semaphore;
import com.example.colonybattle.Vector2d;
public class LockMapPosition {
    private final Semaphore[][] lockGrid;

    public LockMapPosition(int size) {
        lockGrid = new Semaphore[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                lockGrid[i][j] = new Semaphore(2);//dwie osoby mogą wejść na jedno pole
            }
        }
    }

    public void acquireLock(Vector2d position) throws InterruptedException {
        lockGrid[position.getX()][position.getY()].acquire();
    }

    public void releaseLock(Vector2d position) {
        lockGrid[position.getX()][position.getY()].release();
    }
}
