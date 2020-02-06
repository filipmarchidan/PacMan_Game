package pacman.graphics;

import javafx.scene.paint.Color;

/**
 * Represents the style in which the game is rendered.
 */
public enum Style {

    /**
     * Classic style: like the original PacMan.
     */
    CLASSIC("#FFFF00", "#9DDE04", "#1818FF", "#000000",
            "#FF0000", "#FF99CC",
            "#33FFFF", "#FFCC33", "#F8B090",
            "#E08F02", "#DEDEFF", "#B39500");

    private Color pacmanColor;
    private Color pumpedColor;
    private Color wallColor;
    private Color backgroundColor;
    private Color blinkyColor;
    private Color pinkyColor;
    private Color drunkyColor;
    private Color sneakyColor;
    private Color pelletColor;
    private Color powerPelletColor;
    private Color textColor;
    private Color drinkColor;

    Style(String pacmanColor, String pumpedColor, String wallColor,
          String backgroundColor, String blinkyColor,
          String pinkyColor, String drunkyColor, String sneakyColor, String pelletColor,
          String powerPelletColor, String textColor, String drinkColor) {
        this.pacmanColor = Color.web(pacmanColor);
        this.pumpedColor = Color.web(pumpedColor);
        this.wallColor = Color.web(wallColor);
        this.backgroundColor = Color.web(backgroundColor);
        this.blinkyColor = Color.web(blinkyColor);
        this.pinkyColor = Color.web(pinkyColor);
        this.drunkyColor = Color.web(drunkyColor);
        this.sneakyColor = Color.web(sneakyColor);
        this.pelletColor = Color.web(pelletColor);
        this.powerPelletColor = Color.web(powerPelletColor);
        this.textColor = Color.web(textColor);
        this.drinkColor = Color.web(drinkColor);
    }

    /**
     * Gets the color of PacMan.
     *
     * @return The color PacMan has
     */
    public Color getPacmanColor() {
        return pacmanColor;
    }

    /**
     * Gets the color of the walls.
     *
     * @return The color the walls have
     */
    public Color getWallColor() {
        return wallColor;
    }

    /**
     * Gets the color of the background.
     *
     * @return The color the background has
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Gets the color of Blinky.
     *
     * @return The color Blinky has
     */
    public Color getBlinkyColor() {
        return blinkyColor;
    }

    /**
     * Gets the color of Pinky.
     *
     * @return The color Pinky has
     */
    public Color getPinkyColor() {
        return pinkyColor;
    }

    /**
     * Gets the color of Drunky.
     * @return The color Drunky has
     */
    public Color getDrunkyColor() {
        return drunkyColor;
    }

    /**
     * Gets the color of Sneaky.
     * @return The color Sneaky has
     */
    public Color getSneakyColor() {
        return sneakyColor;
    }

    /**
     * Gets the color of the pellets.
     *
     * @return The color the pellets have
     */
    public Color getPelletColor() {
        return pelletColor;
    }

    /**
     * Gets the color of the text.
     *
     * @return the color the text has
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Gets the color of power pellets.
     *
     * @return the color of power pellets
     */
    public Color getPowerPelletColor() {
        return powerPelletColor;
    }

    /**
     * Gets the color of pumped packman.
     *
     * @return the color of pumped packman
     */
    public Color getPumpedColor() {
        return pumpedColor;
    }

    /**
     * Gets the drink color.
     *
     * @return the drink color
     */
    public Color getDrinkColor() {
        return drinkColor;
    }
}
