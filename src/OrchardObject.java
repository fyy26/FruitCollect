import java.awt.Graphics;
import java.util.Map;

/**
 * An object in the orchard.
 * 
 * @author yuyingf
 *
 */
public interface OrchardObject {
    
    /**
     * Specifies how an object should react when it meets the player.
     * 
     * @param collected a map of the fruits collected and their quantities
     * @return whether the object is a "bomb" that kills the player when encountered;
     * {@code true} if the object kills the player and {@code false} otherwise
     */
    public boolean meetPlayer(Map<String, Integer> collected);
    
    /**
     * Draws the object with the given specifications.
     * 
     * @param g the <code>Graphics</code> context used for drawing the object
     * @param px the x position of the upper left corner of the rectangle circumscribing the
     * object in terms of pixels 
     * @param py the y position of the upper left corner of the rectangle circumscribing the
     * object in terms of pixels 
     * @param width the width of the rectangle circumscribing the object in terms of pixels
     * @param height the height of the rectangle circumscribing the object in terms of pixels
     */
    public void draw(Graphics g, int px, int py, int width, int height);
    
    /**
     * @return a clone of the object
     */
    public OrchardObject clone();

}
