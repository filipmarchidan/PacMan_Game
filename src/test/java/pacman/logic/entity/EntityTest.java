package pacman.logic.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class EntityTest {

    private PacMan pacMan;
    private Entity entity;

    /**
     * setting up the testing environment.
     */
    @BeforeEach
    public void init() {
        Board board = new MapParser("").parseMapFromString("*P");
        Level level = new LevelFactory().createLevel(board);
        Iterator<Entity> iter = board.getEntities().iterator();
        entity = iter.next();
        if (!(entity instanceof Pellet)) {
            entity = iter.next();
        }
        pacMan = level.getPacMan();
    }

    @Test
    public void testUpdate() {
        pacMan.setDirection(Direction.RIGHT);
        pacMan.update(0.1);
        assertEquals(1.7, pacMan.getX(), 0.001);
    }

    @Test
    public void testNoCollision() {
        pacMan.setDirection(Direction.LEFT);
        assertFalse(entity.collide(pacMan));
        assertEquals(0, pacMan.checkCollision().size());
    }

    @Test
    public void testCollision() {
        pacMan.setDirection(Direction.LEFT);
        pacMan.update(0.5);
        assertTrue(entity.collide(pacMan));
        assertEquals(1, pacMan.checkCollision().size());
    }

    @Test
    public void testDistanceX() {
        assertEquals(0.1, entity.distanceX(0.6), 0.001);
    }

    @Test
    public void testDistanceXWrap() {
        assertEquals(0.6, entity.distanceX(1.9), 0.001);
    }

    @Test
    public void testSquare() {
        assertEquals(entity.getBoard().getSquare(0, 0), entity.getSquare());
    }

}
