import com.example.colonybattle.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    //make tests for Vector2d class
    @Test
    void add() {
        Vector2d vector2d = new Vector2d(1, 1);
        Vector2d vector2d2 = new Vector2d(1, 1);
        assertEquals(new Vector2d(2, 2), vector2d.addVector(vector2d2));
        assertEquals(new Vector2d(2, 2), vector2d2.addVector(vector2d));
    }

    @Test
    void subtract() {
        Vector2d vector2d = new Vector2d(1, 1);
        Vector2d vector2d2 = new Vector2d(1, 1);
        assertEquals(new Vector2d(0, 0), vector2d.subtractVector(vector2d2));
        assertEquals(new Vector2d(0, 0), vector2d2.subtractVector(vector2d));
    }

    @Test
    void opposite() {
        Vector2d vector2d = new Vector2d(1, 1);
        assertEquals(new Vector2d(-1, -1), vector2d.oppositeVector());
    }
}


