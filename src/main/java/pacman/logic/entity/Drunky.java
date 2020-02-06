package pacman.logic.entity;

import java.util.List;

import pacman.graphics.sprite.DrunkySprite;
import pacman.graphics.sprite.Sprite;
import pacman.logic.Direction;
import pacman.logic.level.Board;
import pacman.logic.level.Square;

public class Drunky extends Ghost {

    private static final Sprite<Ghost> sprite = new DrunkySprite();
    private static Square HOME_CORNER;

    /**
     * Creating Drunky.
     *
     * @param board  the board
     * @param square Drunky's square
     */
    public Drunky(Board board, Square square) {
        super(board, square, sprite);
        direction = Direction.LEFT;
        HOME_CORNER = board.getSquare(0, board.getHeight());//NOPMD
        // needed to initialize it here with board as parameter
    }

    /**
     * {@inheritDoc}
     * Drunky is drunk so in chase mode he uses frightened mode.
     */
    @Override
    protected Square chaseTarget(List<Square> options) {
        return frightenedTarget(options);
    }


}
