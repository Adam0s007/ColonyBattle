package com.example.colonybattle;

import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.Farmer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Engine {

    public static void main(String[] args) throws InterruptedException {
        // Stworzenie 10 osób
        Set<Person> people = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            Vector2d startPosition = new Vector2d(i, i); // Tu możemy dostosować początkowe pozycje osób
            Colony testColony = new Colony(); // Zakładamy, że mamy konstruktor bezparametrowy dla Colony
            people.add(new Farmer(100, 100, 10, startPosition, testColony, i));
        }
        // Dodanie osób do kolonii
        Colony colony = new Colony(people,null,0,ColonyColor.COLONY1);
        colony.addPeople(people);
        // Dodanie kolonii do listy wszystkich kolonii
        List<Colony> allColonies = new ArrayList<>();
        allColonies.add(colony);
        // Stworzenie planszy
        Board board = new Board(allColonies);
        // Uruchomienie wszystkich osób (wątków)
        board.start();
        // Odczekanie minuty
        //Thread.sleep(30000);
        //co 2 sekundy niech zostanie printowany board (wypisywany na ekran) - możesz to zrobić w osobnym wątku
        while(true) {
            board.printBoard();
            Thread.sleep(2000);
        }



        // Zatrzymanie wszystkich osób (wątków)
        //board.stop();
    }
}

