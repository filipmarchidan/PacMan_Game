package pacman.graphics.sprite;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import pacman.logic.entity.Ghost;

@SuppressWarnings("PMD.BeanMembersShouldSerialize") //No beans here
public abstract class GhostSprite extends Sprite<Ghost> {

    Image image;
    Image scared;
    Image eyes;

    protected void drawHome(@NotNull Ghost ghost, @NotNull GraphicsContext g, Color color,
                            Color background) {
        double x = ghost.getHomeX() - ghost.getX();
        double y = ghost.getHomeY() - ghost.getY();
        g.setFill(color.interpolate(background, 0.8));

        double[] triangleX = {x - 0.4, x, x + 0.4};
        double[] triangleY = {y - 0.1, y - 0.4, y - 0.1};
        g.fillPolygon(triangleX, triangleY, 3);

        g.fillRect(x - 0.25, y - 0.15, 0.5, 0.4);
    }

    protected void setImages(Image first, Image second, Image third) {
        image = first;
        scared = second;
        eyes = third;
    }

    protected void drawImage(@NotNull Ghost ghost, @NotNull GraphicsContext g) {
        if (ghost.isScared()) {
            g.drawImage(scared, -0.5, -0.5, 1, 1);
        } else if (ghost.isEaten()) {
            g.drawImage(eyes, -0.5, -0.5, 1, 1);
        } else {
            g.drawImage(image, -0.5, -0.5, 1, 1);
        }
    }

}
