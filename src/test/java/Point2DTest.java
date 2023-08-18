import com.example.colonybattle.board.position.Point2d;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class Point2DTest {
    //make tests for Vector2d class
    @Test
    void add() {
        Point2d point2D = new Point2d(1, 1);
        Point2d point2D2 = new Point2d(1, 1);
        assertEquals(new Point2d(2, 2), point2D.addVector(point2D2));
        assertEquals(new Point2d(2, 2), point2D2.addVector(point2D));
    }

    @Test
    void subtract() {
        Point2d point2D = new Point2d(1, 1);
        Point2d point2D2 = new Point2d(1, 1);
        assertEquals(new Point2d(0, 0), point2D.subtractVector(point2D2));
        assertEquals(new Point2d(0, 0), point2D2.subtractVector(point2D));
    }

    @Test
    void opposite() {
        Point2d point2D = new Point2d(1, 1);
        assertEquals(new Point2d(-1, -1), point2D.oppositeVector());
    }

    @Test
    void equality() {
        Point2d vector1 = new Point2d(2, 1);
        Point2d vector2 = new Point2d(1, 2);
        assertNotEquals(vector1,vector2);
        Set<Point2d> set= new HashSet<>();
        set.add(vector1);
        assertFalse(set.contains(vector2));
        Point2d vector3 = new Point2d(2, 1);
        assertEquals(vector1,vector3);
    }
}


