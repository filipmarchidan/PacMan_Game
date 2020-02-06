package pacman.logic.level;

import org.jetbrains.annotations.NotNull;
import pacman.logic.entity.PacMan;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class Level {

    private Board board;
    private PacMan pacMan;

    protected Level(@NotNull Board board, @NotNull PacMan pacMan) {
        this.board = board;
        this.pacMan = pacMan;
    }

    public Board getBoard() {
        return board;
    }

    public PacMan getPacMan() {
        return pacMan;
    }

    /**
     * Checks if the level was won.
     * @return False iff there are pellets left in the level
     */
    public boolean levelWon() {
        return board.getPellets().size() == 0 && pacMan.isAlive();
    }

    /**
     * Checks if PacMan was hit and therefore should lose a life.
     * @return whether PacMan was hit
     */
    public boolean wasPacManHit() {
        return !pacMan.isAlive();
    }

    /**
     * Revives the player.
     */
    public void revivePlayer() {
        pacMan.setAlive(true);
    }

}
