package pacman.logic.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.MapParser;

public class PinkyTest {

    private final String map = ".........\n" //NOPMD needed for Pinky test
            + ".........\n"
            + ".........\n"
            + ".........\n"
            + "....Pp...\n"
            + ".........\n"
            + ".........\n"
            + ".........\n"
            + ".........\n";

    /**
     * test to see pinky's next target considering pinky's current direction.
     *
     * @param pacmanDirection the direction Pinky is looking towards.
     * @param x               x-coordinate.
     * @param y               y-coordinate.
     */
    @ParameterizedTest
    @CsvSource({"UP, 4, 0", "RIGHT, 8, 4", "DOWN, 4, 8", "LEFT, 0, 4"})
    public void chaseTargetTest(Direction pacmanDirection, int x, int y) {
        Board board = MapParser.parseMapFromString(map);
        board.pacman.setDirection(pacmanDirection);
        Pinky p = (Pinky) board.getGhosts().iterator().next();
        assertEquals(board.getSquare(x, y), p.chaseTarget(p.getOptions()));
    }

    @Test
    public void chaseTargetTestWithoutPacMan() {
        Board board = MapParser.parseMapFromString(map);
        board.removeEntity(board.pacman);
        Pinky p = (Pinky) board.getGhosts().iterator().next();
        assertNull(p.chaseTarget(p.getOptions()));
    }
}
