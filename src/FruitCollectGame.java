import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * The backend implementation of a FruiCollect! game.
 * 
 * @author yuyingf
 *
 */
public class FruitCollectGame {

    private Player player;
    private OrchardBoard orchard;
    private Map<String, Integer> collected;
    private int gen;
    private boolean ended;
    
    public static final int DEFAULT_ORCHARD_WIDTH = 15;
    public static final int DEFAULT_ORCHARD_HEIGHT = 15;
    public static final int DEFAULT_SECONDS_PER_GEN = 5;
    public static final int MAX_MATURE_PER_GEN = 3;
    public static final int DEFAULT_MAX_GEN = 30;
    public static final int DEFAULT_POND_PERCENT = 10;
    public static final int MAX_POND_PERCENT = 70;
    public static final int DEFAULT_INITIAL_PLAYER_X = 7;
    public static final int DEFAULT_INITIAL_PLAYER_Y = 7;
    public static final int MIN_GOAL_PER_TYPE = 10;
    
    /**
     * Constructs a {@code FruitCollectGame} given a specific initial orchard setup and
     * an initial position of the player.
     * 
     * @param orchard a 2-D array of {@code OrchardObject} representing the initial setup of
     * the orchard
     * @param playerX x-coordinate of the player's starting position
     * @param playerY y-coordinate of the player's starting position
     * @throws IllegalArgumentException if the orchard is null or empty, if the player position
     * is not in the orchard, or if the player is initialized in a pond.
     */
    public FruitCollectGame(OrchardObject[][] orchard, int playerX, int playerY) {
        this.orchard = new OrchardBoard(orchard);
        if (playerY < 0 || playerY >= orchard.length
                        || playerX < 0 || playerX >= orchard[0].length) {
            throw new IllegalArgumentException("invalid player position");
        }
        player = new Player(playerX, playerY);
        if (orchard[playerY][playerX] instanceof Pond) {
            throw new IllegalArgumentException("player initialized in a pond");
        }
        collected = new HashMap<String, Integer>();
        for (int row = 0; row < orchard.length; row++) {
            for (int col = 0; col < orchard[0].length; col++) {
                OrchardObject obj = orchard[row][col];
                if (obj instanceof FruitTree && !collected.containsKey(obj.toString())) {
                    collected.put(obj.toString(), 0);
                }
            }
        }
        gen = 0;
        ended = false;
    }
    
    /**
     * Randomly constructs a game with the orchard dimensions specified.
     * Fruit trees will only be of types specified in the map, and
     * are assigned randomly with equal chance. Upon initiation, the game
     * is at generation 0 and no tree is mature. The percent of pond in
     * the orchard is as specified. The player's starting position will
     * never be a pond.
     * 
     * @param width the width of the orchard
     * @param height the height of the orchard
     * @param fruits a map of possible type of fruits with their colors
     * @param pondPercent expected percent of ponds in the orchard (between 0 and 70, inclusive)
     * @param playerX x-coordinate of the player's starting position
     * @param playerY y-coordinate of the player's starting position
     */
    public FruitCollectGame(int width, int height, Map<String, Color> fruits, int pondPercent,
                    int playerX, int playerY) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("invalid dimentions");
        }
        if (fruits == null || fruits.isEmpty()) {
            throw new IllegalArgumentException("empty list of fruits");
        }
        if (pondPercent < 0 || pondPercent > MAX_POND_PERCENT) {
            throw new IllegalArgumentException("invalid pondPercent: " + pondPercent
                            + " (must be 0-" + MAX_POND_PERCENT + ")");
        }
        if (playerX < 0 || playerX >= width || playerY < 0 || playerY >= height) {
            throw new IllegalArgumentException("invalid player position");
        }
        
        player = new Player(playerX, playerY);
        gen = 0;
        ended = false;
        
        ArrayList<String> fruitTypes = new ArrayList<String>();
        collected = new HashMap<String, Integer>();
        for (String fruit : fruits.keySet()) {
            fruitTypes.add(fruit);
            collected.put(fruit, 0);
        }
        
        int numTypes = fruitTypes.size();
        OrchardObject[][] randomSetup = new OrchardObject[height][width];
        Random r = new Random();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (r.nextDouble() * 100 < pondPercent) {
                    randomSetup[row][col] = new Pond();
                } else {
                    String fruit = fruitTypes.get(r.nextInt(numTypes));
                    randomSetup[row][col] = new FruitTree(fruit, fruits.get(fruit));
                }
            }
        }
        randomSetup[playerY][playerX] = new FruitTree(fruitTypes.get(0),
                        fruits.get((fruitTypes).get(0)));
        orchard = new OrchardBoard(randomSetup);
    }
    
    /**
     * Randomly constructs a game with the default orchard dimensions 15x15
     * and the default pond percent of 10%. Fruit trees will only be of types specified
     * in the map, and are assigned randomly with equal chance. Upon initial setup, the
     * game is at generation 0 and no tree is mature. The percent of pond in the orchard
     * is as specified.<br><br>
     * The player will start at the default position (7,7), which is near the center.
     * The player's starting position will never be a pond.
     * 
     * @param fruits a map of possible type of fruits with their colors
     */
    public FruitCollectGame(Map<String, Color> fruits) {
        this(DEFAULT_ORCHARD_WIDTH, DEFAULT_ORCHARD_HEIGHT, fruits, DEFAULT_POND_PERCENT,
                        DEFAULT_INITIAL_PLAYER_X, DEFAULT_INITIAL_PLAYER_Y);
    }
    
    /**
     * @return the width of the game board
     */
    public int getWidth() {
        return orchard.getWidth();
    }
    
    /**
     * @return the height of the game board
     */
    public int getHeight() {
        return orchard.getHeight();
    }
    
    /**
     * @param x the x-coordinate of the location specified
     * @param y the y-coordinate of the location specified
     * @return {@code true} if the location specified is on this game board
     * and {@code false} otherwise
     */
    public boolean onBoard(int x, int y) {
        return orchard.onBoard(x, y);
    }
    
    /**
     * @param x the x-coordinate of the location specified
     * @param y the y-coordinate of the location specified
     * @return {@code true} if there is an {@code OrchardObject} at the location specified
     * and {@code false} otherwise
     */
    public boolean hasObject(int x, int y) {
        return orchard.hasObject(x, y);
    }
    
    /**
     * @return {@code true} if the game has ended and {@code false} otherwise
     */
    public boolean hasEnded() {
        return ended;
    }
    
    /**
     * Ends the game.
     */
    public void endGame() {
        ended = true;
    }
    
    /**
     * @return a set of all fruits being collected
     */
    public Set<String> getFruitSet() {
        Set<String> fruits = new HashSet<String>();
        for (String fruit : collected.keySet()) {
            fruits.add(fruit);
        }
        return fruits;
    }
    
    /**
     * @param fruit the fruit of interest
     * @return {@code true} if the fruit is being collected and {@code false} otherwise
     */
    public boolean isCollecting(String fruit) {
        return collected.containsKey(fruit);
    }
    
    /**
     * @param fruit the fruit of interest
     * @return the current number of this type of fruit collected; -1 if the fruit is not
     * being collected in this game
     */
    public int numCollected(String fruit) {
        if (isCollecting(fruit)) {
            return collected.get(fruit);
        } else {
            return -1;
        }
    }
    
    /**
     * @return {@code true} if the collection goal is accomplished and {@code false} otherwise
     */
    public boolean goalAccomplished() {
        for (String fruit : collected.keySet()) {
            if (collected.get(fruit) < MIN_GOAL_PER_TYPE) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Draws the object at position (x,y) in the orchard at (px,py) on the canvas.
     * Does nothing if the position given contains a null reference.
     * 
     * @param x the orchard x-coordinate of the object to draw
     * @param y the orchard y-coordinate of the object to draw
     * @param g the {@code Graphics} context used for drawing the object
     * @param px the pixel x-coordinate where the object should be drawn
     * @param py the pixel y-coordinate where the object should be drawn
     * @param width the width of the object in pixels
     * @param height the height of the object in pixels
     */
    public void drawObject(int x, int y, Graphics g, int px, int py, int width, int height) {
        if (onBoard(x, y)) {
            OrchardObject obj = orchard.getObject(x, y);
            if (obj != null) {
                obj.draw(g, px, py, width, height);
            }
        } else {
            throw new IllegalArgumentException("invalid position");
        }
    }
    
    /**
     * @return the current x-coordinate of the player
     */
    public int getPlayerX() {
        return player.getX();
    }
    
    /**
     * @return the current y-coordinate of the player
     */
    public int getPlayerY() {
        return player.getY();
    }
    
    /**
     * Moves the player to (x,y) and updates the game state according to the object encountered.
     * Does nothing if the position specified is not on the board.
     * 
     * @param x the x-coordinate to move the player to
     * @param y the y-coordinate to move the player to
     */
    public void move(int x, int y) {
        if (onBoard(x, y)) {
            player.move(x, y);
            ended = orchard.getObject(x, y).meetPlayer(collected);
            if (goalAccomplished()) {
                endGame();
            }
        }
    }
    
    /**
     * @return the current generation number
     */
    public int getGen() {
        return gen;
    }

    /**
     * Turns any good fruit in the last gen bad; then randomly makes up to 3 trees mature.
     * Will not turn the tree where the player is at mature.
     */
    public void nextGen() {
        int width = getWidth();
        int height = getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                OrchardObject obj = orchard.getObject(x, y);
                if (obj instanceof FruitTree) {
                    ((FruitTree) obj).turnBad();
                }
            }
        }
        Random r = new Random();
        for (int i = 0; i < MAX_MATURE_PER_GEN; i++) {
            int randomX = r.nextInt(width);
            int randomY = r.nextInt(height);
            if (randomX != getPlayerX() && randomY != getPlayerY()) {
                OrchardObject obj = orchard.getObject(randomX, randomY);
                if (obj instanceof FruitTree) {
                    ((FruitTree) obj).mature();
                }
            }
        }
        gen++;
    }
    
}
