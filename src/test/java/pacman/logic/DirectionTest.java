package pacman.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pacman.logic.Direction.DOWN;
import static pacman.logic.Direction.LEFT;
import static pacman.logic.Direction.RIGHT;
import static pacman.logic.Direction.UP;

import java.util.Map;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;

public class DirectionTest {

    private static final Map<Direction, Direction> INVERSES = Map.of(UP, DOWN, DOWN, UP, LEFT,
            RIGHT, RIGHT, LEFT);

    @ParameterizedTest
    @EnumSource(Direction.class)
    public void testInverse(Direction dir) {
        assertEquals(INVERSES.get(dir), dir.getInverse());
    }

    @ParameterizedTest
    @CsvSource({"1,0,RIGHT", "-1,0,LEFT", "0,1,DOWN", "0,-1,UP"})
    public void testFromCoords(int x, int y, Direction dir) {
        assertEquals(dir, Direction.getDirection(x, y));
    }

    @ParameterizedTest
    @CsvSource({"1,1","0,0","2,1","1,2"})
    public void testWrongCoords(int x, int y) {
        assertThrows(IllegalArgumentException.class, () -> Direction.getDirection(x, y));
    }

}
