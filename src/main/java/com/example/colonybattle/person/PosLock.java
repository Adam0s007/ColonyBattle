package com.example.colonybattle.person;

import com.example.colonybattle.Vector2d;

public class PosLock {
    BoardRef boardRef;
    PosLock(BoardRef boardRef){
        this.boardRef = boardRef;
    }
    protected boolean aquirePositionLock(Vector2d position){
        boolean succeeded = false;
           if(boardRef.getBoard() != null)
                succeeded = boardRef.getBoard().getLockManager().tryAcquireLock(position);
        return succeeded;
    }
    protected void releasePositionLock(Vector2d position){
        if(boardRef.getBoard() != null)
            boardRef.getBoard().getLockManager().releaseLock(position);
    }
}
