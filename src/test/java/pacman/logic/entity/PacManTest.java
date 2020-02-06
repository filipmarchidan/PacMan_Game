package pacman.logic.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class PacManTest {

    private PacMan pacMan;
    private Entity pellet;
    private Entity wall;

    /**
     * setting up the testing environment.
     */
    @BeforeEach
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // known bug of pmd with foreach loops.
    public void init() {
        Board board = new MapParser("").parseMapFromString("*P#");
        Level level = new LevelFactory().createLevel(board);
        for (Entity entity : board.getEntities()) {
            if (entity instanceof Pellet) {
                pellet = entity;
            }
            if (entity instanceof Wall) {
                wall = entity;
            }
        }
        pacMan = level.getPacMan();
    }

    @Test
    public void testCollidePellet() {
        pacMan.setDirection(Direction.LEFT);
        pacMan.update(0.5);
        assertTrue(pacMan.collide(pellet));
    }

    @Test
    public void testCollideWall() {
        pacMan.setX(pacMan.getX() + 0.2);
        assertTrue(pacMan.collide(wall));
    }

    @Test
    public void testChangeDirection() {
        pacMan.setNextDirection(Direction.UP);
        pacMan.update(0);
        assertEquals(Direction.UP, pacMan.getDirection());
    }

    @Test
    public void testImmunity() {
        pacMan.setImmune();
        pacMan.update(1.5);
        assertTrue(pacMan.isImmune());
        pacMan.update(1.5);
        assertFalse(pacMan.isImmune());
    }

}
