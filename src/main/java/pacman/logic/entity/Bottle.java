package pacman.logic.entity;

import org.jetbrains.annotations.NotNull;
import pacman.graphics.sprite.BottleSprite;
import pacman.graphics.sprite.Sprite;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

public class Bottle extends Entity {

    private static final Sprite<Bottle> SPRITE = new BottleSprite();

    public Bottle(@NotNull Board board, Square square) {
        super(board, square, SPRITE);
    }

    @Override
    public void update(double dtSmall) {
    }

    @Override
    protected boolean isWithinBound(double dx, double dy) {
        return dx * dx + dy * dy < 0.25;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void collideWithPacMan(PacMan pacMan) {
        pacMan.setDrunk();
        setAlive(false);
    }
}
