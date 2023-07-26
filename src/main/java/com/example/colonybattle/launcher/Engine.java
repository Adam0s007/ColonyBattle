package com.example.colonybattle.launcher;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.ui.MyFrame;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.colony.ColonyFactory;
import com.example.colonybattle.colony.ColonyType;

import java.util.ArrayList;
import java.util.List;

public class Engine {
    private static MyFrame frame;
    public static MyFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) throws InterruptedException {

        frame = new MyFrame(Board.SIZE);
        List<Colony> allColonies = new ArrayList<>();
        Board board = new Board(allColonies);
        board.initFrame(frame);
        ColonyFactory colonyFactory = new ColonyFactory();

        Colony colony1 = colonyFactory.createColony(ColonyType.VOLCANIC_NATION,board);
        Colony colony2 = colonyFactory.createColony(ColonyType.ICE_NATION,board);
        Colony colony3 = colonyFactory.createColony(ColonyType.JUNGLE_NATION,board);
        Colony colony4 = colonyFactory.createColony(ColonyType.DESERT_NATION,board);

        allColonies.add(colony1);
        allColonies.add(colony2);
        allColonies.add(colony3);
        allColonies.add(colony4);

        board.initFields();
        frame.getGridPanel().setPositionReferences(board.getFields());
        board.start();

        synchronized (EndgameMonitor.monitor) {
            while (!board.isOnlyOneColonyLeft()) {
                try {
                    EndgameMonitor.monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        // Zatrzymanie wszystkich osób (wątków)
        board.stop();
        frame.dispose();
        System.exit(0);
    }
}

