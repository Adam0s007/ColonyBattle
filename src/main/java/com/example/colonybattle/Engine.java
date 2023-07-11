package com.example.colonybattle;

import com.example.colonybattle.UI.MyFrame;
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

        frame = new MyFrame(20);
        List<Colony> allColonies = new ArrayList<>();
        Board board = new Board(allColonies);

        ColonyFactory colonyFactory = new ColonyFactory();

        Colony colony1 = colonyFactory.createColony(ColonyType.COLONY1,board);
        Colony colony2 = colonyFactory.createColony(ColonyType.COLONY2,board);
        Colony colony3 = colonyFactory.createColony(ColonyType.COLONY3,board);
        Colony colony4 = colonyFactory.createColony(ColonyType.COLONY4,board);


        allColonies.add(colony1);
        allColonies.add(colony2);
        allColonies.add(colony3);
        allColonies.add(colony4);


        board.initFields();
        board.start();

        int iter = 0;
        while(iter < 3) {
            //board.printBoard();
            Thread.sleep(2000);
            iter++;
        }

        // Zatrzymanie wszystkich osób (wątków)
        board.stop();
        frame.dispose();
    }
}

