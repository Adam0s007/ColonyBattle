package com.example.colonybattle;

import com.example.colonybattle.Colors.ColonyColor;
import com.example.colonybattle.UI.MyFrame;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.colony.ColonyFactory;
import com.example.colonybattle.colony.ColonyType;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.Farmer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Engine {
    private static MyFrame frame;
    public static MyFrame getFrame() {
        return frame;
    }
    public static void main(String[] args) throws InterruptedException {

        frame = new MyFrame(20);

        ColonyFactory colonyFactory = new ColonyFactory();

        Colony colony1 = colonyFactory.createColony(ColonyType.COLONY1);
        Colony colony2 = colonyFactory.createColony(ColonyType.COLONY2);
        Colony colony3 = colonyFactory.createColony(ColonyType.COLONY3);
        Colony colony4 = colonyFactory.createColony(ColonyType.COLONY4);

        List<Colony> allColonies = new ArrayList<>();
        allColonies.add(colony1);
        allColonies.add(colony2);
        allColonies.add(colony3);
        allColonies.add(colony4);

        Board board = new Board(allColonies);

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

