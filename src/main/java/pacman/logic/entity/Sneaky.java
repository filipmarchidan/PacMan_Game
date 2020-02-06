package pacman.logic.entity;

import java.util.List;

import pacman.graphics.sprite.SneakySprite;
import pacman.graphics.sprite.Sprite;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

public class Sneaky extends Ghost {

    private static final Sprite<Ghost> sprite = new SneakySprite();
    private static Square HOME_CORNER;

    /**
     * Creating Sneaky.
     * @param board the board
     * @param square Sneaky's square
     */
    public Sneaky(Board board, Square square) {
        super(board, square, sprite);
        direction = Direction.RIGHT;
        HOME_CORNER = board.getSquare(board.getWidth(), board.getHeight());//NOPMD
        // needed to initialize it here with board as parameter
    }

    /**
     * Sneaky is always around its home area.
     * @param options list of squares the ghost can choose from.
     * @return Sneaky's target.
     */
    @Override
    protected Square chaseTarget(List<Square> options) {
        return scatterTarget(options);
    }



}
