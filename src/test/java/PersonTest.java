import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.Vector2d;
import com.example.colonybattle.person.ConcreteCharacters.Farmer;
import com.example.colonybattle.person.Person;
import com.example.colonybattle.person.PersonType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    //przetestujmy metodę walk()
    @Test
    public void testWalk() {
        Vector2d startPosition = new Vector2d(10, 10); // Umieszczamy naszą osobę na środku mapy o rozmiarze 20x20
        Colony testColony = null;
        Person person = new Farmer(PersonType.FARMER, startPosition, testColony,-1);

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
