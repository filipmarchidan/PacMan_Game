package pacman.logic.level;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.Direction;
import pacman.logic.entity.Entity;
import pacman.logic.entity.PacMan;
import pacman.logic.entity.Pellet;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
//tests have some variables that fall into the PMD bugs spectrum
class BoardTest {

    Board board; //NOPMD
    Level level; //NOPMD
    Entity entity; //NOPMD
    //no need for getters/setters,
    // it's just for keeping track of it while testing

    @BeforeEach
    public void init() {
        board = MapParser.parseMapFromString(".#P*#");
        level = new LevelFactory().createLevel(board);
    }

    @Test
    void getSquare() {
        Square square = board.getSquare(1, 0);
        assertNotNull(square);
    }

    @Test
    void getEntities() {
        assertEquals(4, StreamSupport.stream(board.getEntities().spliterator(),
                false).count());
    }

    @Test
    void getSquares() {
        assertEquals(5, StreamSupport.stream(board.getSquares().spliterator(),
                false).count());
    }

    @Test
    void removeEntity() {
        for (Entity e : board.getEntities()) {
            if (e instanceof PacMan) {
                entity = e;
            }
        }
        Square sq = entity.getSquare();
        board.removeEntity(entity);
        assertEquals(0, StreamSupport.stream(sq.getEntities().spliterator(),
                false).count());
        assertTrue(StreamSupport.stream(board.getEntities().spliterator(),
                false).noneMatch(a -> a instanceof PacMan));
    }

    @Test
    void checkLevelWon() {
        for (Entity e : board.getEntities()) {
            if (e instanceof Pellet) {
                e.setAlive(false);
            }
        }
        assertTrue(StreamSupport.stream(board.getEntities().spliterator(), false)
                .noneMatch(e -> e instanceof Pellet && e.isAlive()));
        board.getSquare(0, 0).addEntity(new Pellet(board, null));
        assertFalse(StreamSupport.stream(board.getEntities().spliterator(), false)
                .noneMatch(e -> e instanceof Pellet && e.isAlive()));
    }

    @Test
    void computeScore() {
        board.pacman.setDirection(Direction.RIGHT);
        board.pacman.update(0.5);
        assertEquals(10, board.getTickScore());
    }

    @Test
    void removeDeadEntities() {
        Pellet p = (Pellet) board.getSquare(3, 0).getEntities().iterator().next();
        p.setAlive(false);
        assertTrue(StreamSupport.stream(board.getEntities().spliterator(),
                false).anyMatch(e -> e instanceof Pellet));
        board.removeDeadEntities();
        assertFalse(StreamSupport.stream(board.getEntities().spliterator(),
                false).anyMatch(e -> e instanceof Pellet));
    }
}