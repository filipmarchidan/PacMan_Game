package pacman.logic.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.MapParser;
import pacman.logic.level.Square;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class GhostTest {

    private final String map = "P.*#";
    private Board board;
    private Ghost ghost;

    @Test
    public void updatePacmanDies() {
        board = MapParser.parseMapFromString(map);
        ghost = new Blinky(board, board.getSquare(1, 0));
        board.pacman.setAlive(true);
        board.pacman.setDirection(Direction.RIGHT);
        board.pacman.update(0.5);
        assertFalse(board.pacman.isAlive());
    }

    @Test
    public void updatePacmanAlive() {
        board = MapParser.parseMapFromString(map);
        ghost = new Blinky(board, board.getSquare(2, 0));
        board.pacman.setAlive(true);
        board.pacman.setDirection(Direction.RIGHT);
        board.pacman.update(0.5);
        assertTrue(board.pacman.isAlive());
    }

    @Test
    public void ghostDoesntMove() {
        board = MapParser.parseMapFromString(map);
        ghost = new Blinky(board, board.getSquare(1, 0));
        ghost.update(0.0);
        assertSame(ghost.oldSquare, ghost.square);
    }

    @Test
    public void noTarget() {
        String mapp = "..*#";
        board = MapParser.parseMapFromString(mapp);
        ghost = new Blinky(board, board.getSquare(1, 0));
        ghost.update(0.5);
        assertEquals(ghost.oldSquare, ghost.square);
    }

    @Test
    public void noOptions() {
        String map = ".";
        board = MapParser.parseMapFromString(map);
        ghost = new Blinky(board, board.getSquare(0, 0));
        ghost.update(0);
        assertEquals(ghost.oldSquare, ghost.square);
    }

    /**
     * Test to get the square options the ghost can move to.
     * The neighbors don't consist of solids(walls).
     */
    @Test
    public void getOptionsNoWalls() {
        String mapp = "P...*#";
        board = MapParser.parseMapFromString(mapp);
        ghost = new Blinky(board, board.getSquare(2, 0));
        List<Square> options = new ArrayList<>();
        options.add(board.getSquare(1, 0));
        options.add(board.getSquare(3, 0));
        assertEquals(2, ghost.getOptions().size());
        assertEquals(options, ghost.getOptions());
    }

    @Test
    public void getOptionsWithWalls() {
        String mapp = ".#.\n"
                + "P.#\n"
                + "...";
        board = MapParser.parseMapFromString(mapp);
        ghost = new Blinky(board, board.getSquare(1, 1));
        ghost.setDirection(Direction.RIGHT);
        List<Square> expected = new ArrayList<>();
        expected.add(board.getSquare(1, 2));
        expected.add(board.getSquare(0, 1));
        assertTrue(ghost.getOptions().containsAll(expected));
        assertEquals(2, ghost.getOptions().size());
    }

    @Test
    public void closestNeighborThrowsException() {
        String mapp = "P.";
        board = MapParser.parseMapFromString(mapp);
        ghost = new Blinky(board, board.getSquare(1, 0));
        List<Square> expected = new ArrayList<>(); //NOPMD variable needed for testing purposes
        assertThrows(IllegalArgumentException.class, () ->
                ghost.closestNeighbour(board.pacman.square, expected));
    }

    @Test
    public void closestNeighbor() {
        String mapp = "P..*#";
        board = MapParser.parseMapFromString(mapp);
        ghost = new Blinky(board, board.getSquare(2, 0));
        List<Square> options = new ArrayList<>();
        options.add(board.getSquare(1, 0));
        options.add(board.getSquare(3, 0));
        assertEquals(board.getSquare(1, 0), ghost.closestNeighbour(board.pacman.square, options));
    }

    @Test
    public void testSwitchScatterChaseModes() {
        board = MapParser.parseMapFromString(map);
        ghost = new Blinky(board, board.getSquare(2, 0));
        assertSame(ghost.mode, Ghost.Mode.CHASE);
        ghost.update(20);
        assertSame(Ghost.Mode.SCATTER, ghost.mode);
    }

    @Test
    public void testScatterBehavior() {
        String maP = "P.....*#";
        board = MapParser.parseMapFromString(maP);
        ghost = new Blinky(board, board.getSquare(3, 0));
        assertSame(ghost.mode, Ghost.Mode.CHASE);
        assertSame(Direction.LEFT, ghost.getDirection());
        ghost.update(19.0);
        ghost.update(0.0);
        assertSame(Direction.RIGHT, ghost.getDirection());
        assertSame(Ghost.Mode.SCATTER, ghost.mode);
        ghost.update(2.0);
        ghost.update(8.0);
        assertSame(Ghost.Mode.CHASE, ghost.mode);
        ghost.update(0.0);
        assertSame(Direction.LEFT, ghost.getDirection());
    }

    @Test
    public void testFrightenedBehavior() {
        String maP = "P.....*#";
        board = MapParser.parseMapFromString(maP);
        ghost = new Blinky(board, board.getSquare(2, 0));
        ghost.setScared(true);
        board.pacman.setDirection(Direction.RIGHT);
        ghost.update(0.8);
        assertTrue(board.pacman.isAlive());
    }

    /**
     * Repeated test as an insurance of determinism.
     * This as some runs seemed to have unexpected results.
     */
    @RepeatedTest(10)
    public void testEatenBehavior() {
        String maP = "......*P#";
        board = MapParser.parseMapFromString(maP);
        ghost = new Blinky(board, board.getSquare(3, 0));
        ghost.update(1);
        assertSame(board.getSquare(1, 0), ghost.getSquare());
        assertSame(Direction.LEFT, ghost.getDirection());
        ghost.setEaten();
        assertTrue(ghost.isEaten());
        assertSame(Direction.RIGHT, ghost.getDirection());
        ghost.update(0.25);
        assertSame(board.getSquare(2, 0), ghost.getSquare());
        ghost.update(0.25);
        assertSame(Direction.RIGHT, ghost.getDirection());
        assertSame(board.getSquare(3, 0), ghost.getSquare());
        assertFalse(ghost.isEaten());
        assertSame(Ghost.Mode.CHASE, ghost.mode);
    }

}
