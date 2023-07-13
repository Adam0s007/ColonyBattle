package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;

public class PosLock {
    BoardRef boardRef;
    PosLock(BoardRef boardRef){
        this.boardRef = boardRef;
    }
    protected boolean aquirePositionLock(Vector2d position){
        boolean succeeded = false;
        try {
           succeeded = boardRef.getBoard().getLockManager().tryAcquireLock(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return succeeded;
    }
    protected void releasePositionLock(Vector2d position){
        boardRef.getBoard().getLockManager().releaseLock(position);
    }
}