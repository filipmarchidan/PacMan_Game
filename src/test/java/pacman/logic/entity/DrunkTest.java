package pacman.logic.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.MapParser;

@SuppressWarnings({"PMD.DataflowAnomalyAnalysis", "PMD.BeanMembersShouldSerialize"})
//tests have some variables that fall into the PMD bugs spectrum (for loop & bean).
class DrunkTest {

    private Board board;
    private PacMan pacMan;

    private void init(String map) {
        board = MapParser.parseMapFromString(map);
        pacMan = board.getPacman();
    }

    @ParameterizedTest
    @CsvSource({"....P.,0", "..bP.,1", ".bbP.,2"})
    public void createBottle(String map, int expected) {
        init(map);
        int bottles = 0;
        for (Entity e : board.getEntities()) {
            if (e instanceof Bottle) {
                bottles++;
            }
        }
        Assertions.assertEquals(expected, bottles);
    }

    @Test
    public void becomesDrunk() {
        init("#bP");
        assertFalse(pacMan.isDrunk());

        pacMan.setNextDirection(Direction.LEFT);
        pacMan.update(0.5);
        Assertions.assertTrue(pacMan.isDrunk());
    }

    @Test
    public void becomesUnDrunk() {
        init("#.Pb..................");
        pacMan.setNextDirection(Direction.RIGHT);
        pacMan.update(0.5);
        Assertions.assertTrue(pacMan.isDrunk());
        //! Dont run into walls when updating with > 1 second !!
        pacMan.update(PacMan.drunkTime - 1e-10);
        Assertions.assertTrue(pacMan.isDrunk());

        pacMan.update(2e-10);
        assertFalse(pacMan.isDrunk());
    }

    /**
     * Tests keybindings, both original and drunk ones.
     * This because the keybindings were specifically moved here for drunk behavior.
     * This in contrast with it being part of the graphics module, which is untested.
     *
     * @param keyCode   the key that would be pressed.
     * @param direction the direction that pacman would take.
     */
    @CsvSource({"A,LEFT", "LEFT,LEFT", "W,UP", "UP,UP",
            "S,DOWN", "DOWN,DOWN", "D,RIGHT", "RIGHT,RIGHT"})
    @ParameterizedTest
    public void testKeyBindingsUnDrunk(KeyCode keyCode, Direction direction) {
        init("P");
        assertFalse(pacMan.isDrunk());
        assertEquals(direction, Direction.keyToDirection(keyCode, pacMan));

        pacMan.setDrunk();
        assertTrue(pacMan.isDrunk());
        assertEquals(direction.getInverse(), Direction.keyToDirection(keyCode, pacMan));
    }

    @EnumSource(KeyCode.class)
    @ParameterizedTest
    public void testAllOtherKeys(KeyCode keyCode) {
        init("P");
        if (!keyCode.isArrowKey() && keyCode != KeyCode.A && keyCode != KeyCode.W
                && keyCode != KeyCode.S && keyCode != KeyCode.D) {
            assertNull(Direction.keyToDirection(keyCode, pacMan));
        }
    }
}