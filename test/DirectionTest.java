import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void testDirections() {
        Direction d1 = Direction.one;
        Direction d2 = Direction.two;
        Direction d3 = Direction.three;
        Direction d4 = Direction.four;

        // Test equality
        assertEquals(d1, Direction.one);
        assertEquals(d2, Direction.two);
        assertEquals(d3, Direction.three);
        assertEquals(d4, Direction.four);

        // Test inequality
        assertNotEquals(d1, d2);
        assertNotEquals(d1, d3);
        assertNotEquals(d1, d4);
        assertNotEquals(d2, d3);
        assertNotEquals(d2, d4);
        assertNotEquals(d3, d4);
    }
}
