package pacman.logic.level;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;
import pacman.logic.entity.Blinky;
import pacman.logic.entity.Bottle;
import pacman.logic.entity.Drunky;
import pacman.logic.entity.Entity;
import pacman.logic.entity.PacMan;
import pacman.logic.entity.Pellet;
import pacman.logic.entity.Pinky;
import pacman.logic.entity.PowerPellet;
import pacman.logic.entity.Sneaky;
import pacman.logic.entity.Wall;

/**
 * Parses text to maps ({@link Board}s).
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize") // Class is not a bean.
public class MapParser {

    private File levelDirectory;
    private static Map<Character, Class<? extends Entity>> entityChars;

    static {
        entityChars = new HashMap<>();
        entityChars.put('#', Wall.class);
        entityChars.put('*', Pellet.class);
        entityChars.put('+', PowerPellet.class);
        entityChars.put('B', Blinky.class);
        entityChars.put('b', Bottle.class);
        entityChars.put('P', PacMan.class);
        entityChars.put('p', Pinky.class);
        entityChars.put('D', Drunky.class);
        entityChars.put('S', Sneaky.class);
    }

    /**
     * Creates a map parser that loads a file, reads it and generates a board out of it.
     *
     * @param levelDirectory The directory to read levels from.
     */
    public MapParser(String levelDirectory) {
        try {
            this.levelDirectory = new File(getClass().getResource(levelDirectory).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Platform.exit();
        }

        if (!this.levelDirectory.exists()) {
            throw new IllegalArgumentException("Level Directory does not exist");
        }
        if (!this.levelDirectory.isDirectory()) {
            throw new IllegalArgumentException("Level Directory should be a directory");
        }
    }

    /**
     * Reads the file and parses the map from the contents.
     *
     * @param levelName The name of the level
     * @return A board parsed from the file
     */
    public Board parseMap(@NotNull String levelName) {
        try (Scanner scanner = new Scanner(new File(levelDirectory.getPath() + "/" + levelName
                + ".txt"))) {
            return parseMap(scanner);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            return null;
        }
    }

    /**
     * Reads the scanner and parses the map from the contents.
     *
     * @param scanner The scanner to read from
     * @return A board parsed from the scanner
     */
    @NotNull
    public static Board parseMap(@NotNull Scanner scanner) {
        List<String> lines = new ArrayList<>();
        while (scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        checkMapCorrectness(lines);

        int width = lines.get(0).length();
        int height = lines.size();
        char[][] map = new char[height][]; // NOPMD redefinition necessary
        for (int i = 0; i < lines.size(); i++) {
            map[i] = lines.get(i).toCharArray(); // NOPMD redefinition necessary
        }

        Board board = new Board(width, height);
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                parseSquare(board, map[j][i], i, j);
            }
        }

        return board;
    }

    /**
     * Parses a map from the given string.
     *
     * @param mapString The string to read from
     * @return A board parsed from the string
     */
    @NotNull
    public static Board parseMapFromString(@NotNull String mapString) {
        return parseMap(new Scanner(mapString));
    }

    /**
     * Parses the square and creates the entities it needs.
     *
     * @param board      the board
     * @param squareChar the squares char
     * @param x          the x coordinate
     * @param y          the y coordinate
     */
    @SuppressWarnings("PMD.DataflowAnomalyAnalysis") // false warnings.
    private static void parseSquare(@NotNull Board board, char squareChar, int x, int y) {
        Square square = new Square(board, x, y); // NOPMD variable is used
        Class<? extends Entity> squareClass = entityChars.get(squareChar);//
        if (squareChar == '.') { //NOPMD literal makes sense
            return;
        } else if (squareClass == null) {
            throw new IllegalArgumentException("Invalid character");
        } else {
            try {
                squareClass.getConstructor(Board.class, Square.class).newInstance(board, square);
            } catch (Exception e) {
                throw new IllegalStateException("Class has no appropriate constructor.");
            }
        }
    }

    /**
     * .
     * Check for validity of map, if it has lines of different lengths or if the file doesn't
     * contain any proper lines
     *
     * @param mapText the lines read from the file
     */
    public static void checkMapCorrectness(List<String> mapText) {
        if (mapText == null || mapText.isEmpty()) {
            throw new IllegalArgumentException("Invalid map format: "
                    + "No existing rows inside the map file");
        }
        int width = mapText.get(0).length();
        if (width == 0) {
            throw new IllegalArgumentException("Invalid map format: "
                    + "Input lines have no characters");
        }
        for (int i = 0; i < mapText.size(); i++) {
            String line = mapText.get(i);
            if (line.length() != width) {
                throw new IllegalArgumentException("Invalid map format: "
                        + "Input lines are not of equal width");
            }
        }
    }

}
