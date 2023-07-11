package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;

public class PosLock {
    BoardRef boardRef;
    PosLock(BoardRef boardRef){
        this.boardRef = boardRef;
    }
    protected void aquirePositionLock(Vector2d position){
        try {
            boardRef.getBoard().getLockManager().acquireLock(position);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    protected void releasePositionLock(Vector2d position){
        boardRef.getBoard().getLockManager().releaseLock(position);
    }
}
