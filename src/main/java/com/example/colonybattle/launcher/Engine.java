package com.example.colonybattle.launcher;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.time.GameTimer;
import com.example.colonybattle.ui.frame.MyFrame;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.colony.ColonyFactory;
import com.example.colonybattle.colony.ColonyType;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    public void run() {
        MyFrame frame; // Lokalna deklaracja
        List<Colony> allColonies = new ArrayList<>();
        frame = new MyFrame(Board.SIZE, allColonies);
        Board board = new Board(allColonies);
        GameTimer gameTimer = new GameTimer(allColonies);

        initializeGame(board, allColonies, frame);
        gameLoop(board, gameTimer);
        shutdownGame(board, gameTimer, frame);
    }

    private void initializeGame(Board board, List<Colony> allColonies, MyFrame frame) {
        board.initFrame(frame);
        createColonies(allColonies, board);

        // Code executed after adding all colonies to the list allColonies
        board.initFields();
        frame.getInfoPanel().getColonyPanel().init();
        frame.getGridPanel().setPositionReferences(board.getFields());
        board.start();
        frame.getInfoPanel().getSpawnPanel().runTimer();
    }

    private void createColonies(List<Colony> allColonies, Board board) {
        ColonyFactory colonyFactory = new ColonyFactory();
        for (ColonyType type : ColonyType.values()) {
            Colony colony = colonyFactory.createColony(type, board);
            allColonies.add(colony);
        }
    }

    private void gameLoop(Board board, GameTimer gameTimer) {
        gameTimer.start();
        synchronized (EndgameMonitor.monitor) {
            while (!board.isOnlyOneColonyLeft()) {
                try {
                    EndgameMonitor.monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void shutdownGame(Board board, GameTimer gameTimer, MyFrame frame) {
        gameTimer.stop();
        board.stop();
        frame.dispose();
        System.exit(0);
    }
}
