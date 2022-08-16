import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 * A pond object that drowns the player if stepped on. It is represented
 * as a filled light blue rectangle on the game board.
 * 
 * @author yuyingf
 *
 */
public class Pond implements OrchardObject {
    
    /**
     * All ponds are equal.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pond;
    }
    
    /**
     * Always returns 0 since all ponds are equal.
     */
    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * A {@code String} representing the pond.
     */
    @Override
    public String toString() {
        return "Pond";
    }

    /**
     * Kills the player.
     */
    @Override
    public boolean meetPlayer(Map<String, Integer> collected) {
        return true;
    }

    /**
     * Draws a filled light blue rectangle with the given dimension
     * at the position specified by (px,py).
     */
    @Override
    public void draw(Graphics g, int px, int py, int width, int height) {
        g.setColor(new Color(51,204,255));
        g.fillRect(px, py, width, height);
    }
    
    /**
     * @return a clone of the {@code Pond} as an {@code OrchardObject}
     */
    @Override
    public OrchardObject clone() {
        return new Pond();
    }

}
