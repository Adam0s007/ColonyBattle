package com.example.colonybattle.board.boardlocks;

import com.example.colonybattle.board.position.Point2d;
import com.example.colonybattle.models.person.helpers.BoardRef;

public class PosLock {
    BoardRef boardRef;
    public PosLock(BoardRef boardRef){
        this.boardRef = boardRef;
    }
    public boolean aquirePositionLock(Point2d position){
        boolean succeeded = false;
           if(boardRef.getBoard() != null)
                succeeded = boardRef.getBoard().getLockManager().tryAcquireLock(position);
        return succeeded;
    }
    public void releasePositionLock(Point2d position){
        if(boardRef.getBoard() != null)
            boardRef.getBoard().getLockManager().releaseLock(position);
    }
}
