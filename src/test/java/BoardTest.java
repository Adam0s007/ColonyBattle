import com.example.colonybattle.board.Board;
import com.example.colonybattle.colony.Colony;
import com.example.colonybattle.board.position.Vector2d;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BoardTest {

    private Board board;
    private List<Colony> colonies;

    @BeforeEach
    void setUp() {
        colonies = new ArrayList<>();
        Colony colony1 = new Colony();
        Colony colony2 = new Colony();
        colonies.add(colony1);
        colonies.add(colony2);
        board = new Board(colonies);
    }

    @Test
    void testGetFields() {
        Map<String, Vector2d> fields = board.getFields();
        assertNotNull(fields);
        assertTrue(fields.isEmpty());
    }

//
//    @Test
//    void testInitFields() {
//        Person person1 = new Person();
//        Person person2 = new Person();
//        Vector2d position1 = new Vector2d(0, 0);
//        Vector2d position2 = new Vector2d(1, 1);
//
//        person1.setPosition(position1);
//        person2.setPosition(position2);
//
//        colonies.get(0).addPerson(person1);
//        colonies.get(1).addPerson(person2);
//
//        board.initFields();
//
//        Map<String, Vector2d> fields = board.getFields();
//        assertEquals(2, fields.size());
//        assertEquals(position1, fields.get("(0,0)"));
//        assertEquals(position2, fields.get("(1,1)"));
//    }
//
//    @Test
//    void testIsFieldOccupied() {
//        Vector2d position = new Vector2d(0, 0);
//        board.getFields().put(position.toString(), position);
//
//        assertTrue(board.isFieldOccupied("(0,0)"));
//        assertFalse(board.isFieldOccupied("(1,1)"));
//    }
//
//    @Test
//    void testGetVector2d() {
//        Vector2d position = new Vector2d(0, 0);
//        board.getFields().put("(0,0)", position);
//
//        assertEquals(position, board.getVector2d("(0,0)"));
//        assertNull(board.getVector2d("(1,1)"));
//    }
//
//    @Test
//    void testRemovePerson() {
//        Person person = new Person();
//        board.removePerson(person);
//
//        assertFalse(person.isRunning());
//    }
//
//    @Test
//    void testStopPeople() {
//        Person person1 = new Person();
//        Person person2 = new Person();
//        Set<Person> people1 = new HashSet<>();
//        Set<Person> people2 = new HashSet<>();
//        people1.add(person1);
//        people2.add(person2);
//
//        colonies.get(0).setPeople(people1);
//        colonies.get(1).setPeople(people2);
//
//        board.stopPeople();
//
//        assertFalse(person1.isRunning());
//        assertFalse(person2.isRunning());
//    }
//
//    @Test
//    void testStop() {
//        board.stop();
//
//        assertFalse(board.executorService.isShutdown());
//        assertTrue(board.executorService.isTerminated());
//    }
//
//    // Należy pamiętać, że metoda printBoard() zawiera operacje wyjścia na konsolę
//    // i nie może być łatwo przetestowana przy użyciu JUnit.
//    // Można jednak przetestować, czy metoda nie zgłasza wyjątku.
    @Test
    void testPrintBoard() {
        assertDoesNotThrow(() -> board.printBoard());
    }

    // testGetLockManager() - nie wymaga testowania, ponieważ jest prostą metodą dostępu
}
