package pacman.logic.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
class PowerPelletTest {

    private Board board; //NOPMD test variable, no need for getter/setter

    @BeforeEach
    public void setUp() {
        board = new MapParser(".").parseMapFromString("#P.+");
        Level level = new LevelFactory().createLevel(board);
    }

    @Test
    public void createPowerPellet() {
        PowerPellet powerPellet = null;
        for (Entity e: board.getEntities()) {
            if (e instanceof PowerPellet) {
                powerPellet = (PowerPellet) e;
            }
        }
        Assert.assertNotNull(powerPellet);
    }
}