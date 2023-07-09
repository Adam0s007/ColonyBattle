package com.example.colonybattle;

import com.example.colonybattle.Colors.ColonyColor;
import com.example.colonybattle.UI.MyFrame;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.Farmer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Engine {
    private static MyFrame frame;
    public static MyFrame getFrame() {
        return frame;
    }
    public static void main(String[] args) throws InterruptedException {
        // Stworzenie 10 osób
        Set<Person> people = new HashSet<>();


        frame = new MyFrame(20);

        for (int i = 0; i < 10; i++) {
            Vector2d startPosition = new Vector2d(i, i); // Tu możemy dostosować początkowe pozycje osób
            Colony testColony = new Colony(); // Zakładamy, że mamy konstruktor bezparametrowy dla Colony
            people.add(new Farmer(100, 100, 10, startPosition, testColony, i));
        }
        // Dodanie osób do kolonii
        Colony colony = new Colony(people,null,0, ColonyColor.COLONY1);
        colony.addPeople(people);
        List<Colony> allColonies = new ArrayList<>();
        allColonies.add(colony);


        Board board = new Board(allColonies);

        board.start();

        int iter = 0;
        while(iter < 20) {
            //board.printBoard();
            Thread.sleep(2000);
            iter++;
        }

        // Zatrzymanie wszystkich osób (wątków)
        board.stop();
        frame.dispose();
    }
}

