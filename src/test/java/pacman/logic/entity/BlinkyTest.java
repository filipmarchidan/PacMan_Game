package pacman.logic.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class BlinkyTest {

    private final String map = "P.*#";
    private Board board;
    private Blinky blinky;

    /**
     * Test to see the square picked by blinky in chase mode for specific diretions of pacman.
     *
     * @param pacDirection possible directions for pacman.
     */
    @ParameterizedTest
    @EnumSource(Direction.class)
    public void chaseTargetTest(Direction pacDirection) {
        board = MapParser.parseMapFromString(map);
        board.pacman.setDirection(pacDirection);
        blinky = new Blinky(board, board.getSquare(1, 0));
        assertEquals(blinky.chaseTarget(blinky.getOptions()), board.getSquare(0, 0));
    }

    @Test
    public void chaseTargetTestWithoutPacMan() {
        board = MapParser.parseMapFromString(map);
        board.removeEntity(board.pacman);
        blinky = new Blinky(board, board.getSquare(1, 0));
        assertNull(blinky.chaseTarget(blinky.getOptions()));
    }

}
