import java.util.Arrays;

/**
 * Represents the orchard and the objects in it as a 2-D game board.
 * 
 * @author yuyingf
 *
 */
public class OrchardBoard {

    private OrchardObject[][] orchard;
    private int width;
    private int height;
    
    /**
     * Constructs an {@code OrchardBoard} given an initial setup.
     * 
     * @param orchard 2-D array representing the initial setup of the orchard
     */
    public OrchardBoard(OrchardObject[][] orchard) {
        if (orchard == null) {
            throw new IllegalArgumentException("orchard is null");
        }
        if (orchard.length > 0 && orchard[0].length > 0) {
            this.width = orchard[0].length; 
            this.height = orchard.length;
            
            OrchardObject[][] clone = new OrchardObject[height][width];
            for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (orchard[row][col] != null) {
                        clone[row][col] = orchard[row][col].clone();
                    }
                }
            }
            this.orchard = clone;
        } else {
            throw new IllegalArgumentException("empty orchard");
        }
    }

    /**
     * @return the width of the orchard board
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the orchard borad
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * @return a {@code String} representing the orchard in 2-D
     */
    public String toString() {
        String out = "";
        for (int row = 0; row < orchard.length; row++) {
            if (row != orchard.length - 1) {
                out += Arrays.toString(orchard[row]) + "\n";
            } else {
                out += Arrays.toString(orchard[row]);
            }
        }
        return out;
    }
    
    /**
     * @param x the x-coordinate of the location specified
     * @param y the y-coordinate of the location specified
     * @return {@code true} if the location specified is on this orchard board
     * and {@code false} otherwise
     */
    public boolean onBoard(int x, int y) {
        return 0 <= x && x < width && 0 <= y && y < height;
    }
    
    /**
     * @param x the x-coordinate of the location specified
     * @param y the y-coordinate of the location specified
     * @return the OrchardObject at the location specified,
     * null if the location is empty or not on board
     */
    public OrchardObject getObject(int x, int y) {
        if (onBoard(x, y)) {
            return orchard[y][x];
        } else {
            return null;
        }
    }
    
    /**
     * @param x the x-coordinate of the location specified
     * @param y the y-coordinate of the location specified
     * @return {@code true} if there is an {@code OrchardObject} at the location specified
     * and {@code false} otherwise
     */
    public boolean hasObject(int x, int y) {
        return onBoard(x, y) && getObject(x, y) != null;
    }
    
}
