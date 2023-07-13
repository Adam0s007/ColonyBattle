import com.example.colonybattle.Board;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.colony.ColonyFactory;
import com.example.colonybattle.colony.ColonyType;
import com.example.colonybattle.person.ConcreteCharacters.Farmer;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    //przetestujmy metodę walk()
    @Test
    public void testWalk() {
        List<Colony> allColonies = new ArrayList<>();
        Board board = new Board(allColonies);
        ColonyFactory colonyFactory = new ColonyFactory();
        Colony colony1 = colonyFactory.createColony(ColonyType.COLONY1,board);
        allColonies.add(colony1);
        board.initFields();
        Person person = colony1.getPeople().stream().filter(p -> p.getType() == PersonType.FARMER).findFirst().get();

        for (int i = 0; i < 100; i++) {  // Wykonujemy 100 kroków
            person.walk();

            Vector2d position = person.getPosition();

            assertTrue(position.getX() >= 0);
            assertTrue(position.getX() < 20);
            assertTrue(position.getY() >= 0);
            assertTrue(position.getY() < 20);
        }
    }

}
