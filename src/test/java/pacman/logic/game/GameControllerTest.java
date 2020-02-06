package pacman.logic.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import pacman.graphics.BoardCanvas;
import pacman.logic.Player;
import pacman.logic.level.Board;
import pacman.logic.level.Level;
import pacman.logic.level.LevelFactory;
import pacman.logic.level.MapParser;

@RunWith(PowerMockRunner.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GameControllerTest {

    @Test
    @Order(1)
    public void testCreateController() {
        assertNotNull(GameController.getInstance());
    }

    @Test
    @Order(2)
    public void testOneInstance() {
        assertEquals(GameController.getInstance(), GameController.getInstance());
    }

    @Test
    @Order(3)
    public void testPauseNotStarted() {
        assertThrows(IllegalStateException.class, () -> GameController.getInstance().pause());
    }

    @Test
    @Order(4)
    public void testUnpauseNotStarted() {
        GameController.getInstance().getGame().setState(GameState.READY);
        assertThrows(IllegalStateException.class, () -> GameController.getInstance().unpause());
    }

    @Test
    @Order(5)
    public void testUnpauseWorkingOk() {
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        GameController.getInstance().pause();
        GameController.getInstance().unpause();
        assertTrue(GameController.getInstance().getGame().isRunning());
    }

    @Test
    @Order(6)
    public void testStartAndClose() {
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        assertTrue(GameController.getInstance().getGame().isRunning());
        GameController.getInstance().pause();
        assertFalse(GameController.getInstance().getGame().isRunning());
    }

    @Test
    @Order(7)
    public void testStopException() {
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        assertTrue(GameController.getInstance().getGame().isRunning());
        assertThrows(IllegalStateException.class, () -> GameController.getInstance().stop());
    }

    @Test
    @Order(8)
    public void testStopWorkingOk() {
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        assertTrue(GameController.getInstance().getGame().isRunning());
        GameController.getInstance().pause();
        GameController.getInstance().stop();
        assertFalse(GameController.getInstance().getGame().isRunning());
    }

    @Test()
    @Order(9)
    public void testGameWon() {
        Board bd = new MapParser(".").parseMapFromString("##P..+#");
        Level lvl = new LevelFactory().createLevel(bd);
        List<Level> lv = new ArrayList<>();
        lv.add(lvl);
        BoardCanvas cnv = mock(BoardCanvas.class);
        doNothing().when(cnv).draw(anyDouble());
        GameController.getInstance().setGame(new Game(new Player(), lv));
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        GameController.getInstance().update(3);
        //GameController.getInstance().getGame().changeMaxLvl(0);
        assertTrue(GameController.getInstance().getGame().won());
    }

    @Test
    @Order(10)
    public void testNextLevel() {
        Board bd = new MapParser(".").parseMapFromString("##P..+#");
        Board bd2 = MapParser.parseMapFromString("##P..**+#");
        Level lvl1 = new LevelFactory().createLevel(bd);
        Level lvl2 = new LevelFactory().createLevel(bd2);
        List<Level> lv = new ArrayList<>();
        lv.add(lvl1);
        lv.add(lvl2);
        BoardCanvas cnv = mock(BoardCanvas.class);
        doNothing().when(cnv).draw(anyDouble());
        GameController.getInstance().setGame(new Game(new Player(), lv));
        GameController.getInstance().setTimer(prepareTimer());
        GameController.getInstance().start();
        GameController.getInstance().update(3);
        assertEquals(GameController.getInstance().getGame().getLevel(), lvl1);
        GameController.getInstance().nextLevel();
        assertEquals(GameController.getInstance().getGame().getLevel(), lvl2);
    }

    /**
     * prepares the mock for the animationtimer.
     * @return that animationtimer mocked.
     */
    public AnimationTimer prepareTimer() {
        AnimationTimer at = mock(AnimationTimer.class);
        doNothing().when(at).start();
        doNothing().when(at).stop();
        return at;
    }

}
