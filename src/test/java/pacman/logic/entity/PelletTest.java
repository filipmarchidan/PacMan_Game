package pacman.logic.entity;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@SuppressWarnings("PMD.DataflowAnomalyAnalysis")
//tests have some variables that fall into the PMD bugs spectrum
class PelletTest {

    private Board board; //NOPMD no need for getters/setters,
    // it's just for keeping track of it while testing

    @BeforeEach
    public void init() {
        board = MapParser.parseMapFromString("##*P#");
    }

    @Test
    public void createPellet() {
        Pellet pellet = null;
        for (Entity e: board.getEntities()) {
            if (e instanceof Pellet) {
                pellet = (Pellet) e;
            }
            System.out.println(e.getX());
        }
        Assert.assertNotNull(pellet);
    }

}