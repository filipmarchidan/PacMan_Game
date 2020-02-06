package pacman.logic.entity;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
//tests have some variables that fall into the PMD bugs spectrum
class WallTest {

    private Board board; //NOPMD no need for getters/setters,
    // it's just for keeping track of it while testing

    @BeforeEach
    public void init() {
        board = new MapParser(".").parseMapFromString("#P");
        Level level = new LevelFactory().createLevel(board);
    }

    @Test
    public void createWall() {
        Wall wall = null;
        for (Entity e: board.getEntities()) {
            if (e instanceof Wall) {
                wall = (Wall) e;
            }
        }
        assertNotNull(wall);
    }
}