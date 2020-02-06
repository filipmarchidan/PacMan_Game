package pacman.logic.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import pacman.logic.Direction;
import pacman.logic.Player;
import pacman.logic.entity.Blinky;
import pacman.logic.entity.Entity;
import pacman.logic.entity.PacMan;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class GameTest {

    @Nested
    class TestSuite1 {

        private Game game;

        /**
         * setting up the testing environment.
         */
        @BeforeEach
        public void init() {
            Board board = new MapParser("").parseMapFromString("*P+..*");
            Level level = new LevelFactory().createLevel(board);
            List<Level> levels = new ArrayList<>();
            levels.add(level);
            game = new Game(new Player(), levels);
        }

        @Test
        @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // known bug of pmd with foreach loops.
        public void testUpdate() {
            for (Entity entity : game.getLevel().getBoard().getEntities()) {
                entity.setAlive(false);
            }
            game.setState(GameState.RUNNING);
            game.update(0);
            Iterator<Entity> iterator = game.getLevel().getBoard().getEntities().iterator();
            assertTrue(iterator.hasNext());
            iterator.next();
            assertFalse(iterator.hasNext());
        }

        @Test
        @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
        public void testLoss() {
            game.setState(GameState.RUNNING);
            game.getPlayer().loseLife();
            game.getPlayer().loseLife();
            game.getPlayer().loseLife();
            game.update(1);
            assertEquals(GameState.LOST, game.getState().getValue());
        }

        @Test
        public void eatsBigPellet() {
            PacMan pacMan = game.getLevel().getPacMan();
            pacMan.setDirection(Direction.RIGHT);
            game.setState(GameState.RUNNING);
            game.update(0.5);
            assertTrue(pacMan.isPumped());

            game.update(PacMan.pumpedTime - Double.MIN_VALUE);
            assertTrue(pacMan.isPumped());

            game.update(Double.MIN_VALUE);
            assertFalse(pacMan.isPumped());

            assertSame(GameState.RUNNING, game.getState().getValue());
        }
    }

    @Nested
    class TestSuite2 {

        private Game game;
        private Blinky blinky;

        /**
         * Rpeated test as it showed non determinism.
         */
        @RepeatedTest(20)
        public void testPacManEatsGhosts() {
            Board board = new MapParser("").parseMapFromString("*P.+....#");
            Level level = new LevelFactory().createLevel(board);
            List<Level> levels = new ArrayList<>();
            levels.add(level);
            blinky = new Blinky(board, board.getSquare(7, 0));
            game = new Game(new Player(), levels);
            PacMan pacMan = game.getLevel().getPacMan();

            pacMan.setDirection(Direction.RIGHT);
            blinky.setDirection(Direction.LEFT);
            game.setState(GameState.RUNNING);
            game.update(0.5);
            assertEquals(board.getSquare(6,0),blinky.getSquare());
            game.update(0.5);
            assertEquals(board.getSquare(5, 0), blinky.getSquare());
            assertTrue(game.isRunning());
            assertTrue(pacMan.isAlive());
            assertTrue(pacMan.isPumped());
            assertTrue(blinky.isScared());
            game.update(0.5);
            assertTrue(pacMan.isAlive());
            assertTrue(blinky.isEaten());
        }
    }

}
