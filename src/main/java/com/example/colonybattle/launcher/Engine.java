package com.example.colonybattle.launcher;

import com.example.colonybattle.board.Board;
import com.example.colonybattle.ui.frame.MyFrame;
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
        List<Colony> allColonies = new ArrayList<>();
        frame = new MyFrame(Board.SIZE,allColonies);

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

        board.initFields();//dodajemy wszystkie pola osob z wszystkich kolonii do hashSetu fields i dodajemy wszystkie pozostale pola do tego hashsetu
        frame.getInfoPanel().getColonyPanel().init(); //inicjalizujemy panel kolonii
        frame.getGridPanel().setPositionReferences(board.getFields());//dodajemy wszystkie pola do gridPanelu
        board.start();//uruchamiamy wszystkie watki osob
        frame.getInfoPanel().getSpawnPanel().runTimer();//uruchamiamy timer od spawnu osob

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

