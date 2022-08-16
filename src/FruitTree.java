import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

/**
 * A fruit tree object
 * 
 * @author yuyingf
 *
 */
public class FruitTree implements OrchardObject {
    
    private final String type;
    private final Color color;
    private boolean mature;
    private boolean badFruit;

    /**
     * Constructs a {@code FruitTree} given a fruit type and a color.
     * A tree is not mature and doesn't bear bad fruit upon initiation.
     * 
     * @param type the type of fruit the tree produces
     * @param color the color of the fruit the tree produces
     */
    public FruitTree(String type, Color color) {
        this.type = type;
        this.color = color;
        this.mature = false;
        this.badFruit = false;
    }
    
    // A helper constructor for the clone method
    private FruitTree(String type, Color color, boolean mature, boolean badFruit) {
        this.type = type;
        this.color = color;
        this.mature = mature;
        this.badFruit = badFruit;
    }

    /**
     * @return the type of fruit the tree produces
     */
    public String getType() {
        return type;
    }

    /**
     * @return the color of the fruit the tree produces, which is also the color of the tree when
     * drawn on an orchard board
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * @return {@code true} if the tree is mature and {@code false} otherwise
     */
    public boolean isMature() {
        return mature;
    }
    
    /**
     * @return {@code true} if the tree is mature and bears bad fruit; {@code false} otherwise
     */
    public boolean isBadFruit() {
        return mature && badFruit;
    }
    
    /**
     * Makes the tree mature.
     */
    public void mature() {
        mature = true;
    }
    
    /**
     * Makes the fruit on the tree turn bad. Does nothing if the tree is not mature.
     */
    public void turnBad() {
        if (mature) {
            badFruit = true;
        }
    }
    
    /**
     * Compares if this tree is equal to that object using type, color,
     * maturity and bad fruit status.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FruitTree)) {
            return false;
        }
        FruitTree other = (FruitTree) obj;
        if (badFruit != other.isBadFruit()) {
            return false;
        }
        if (color == null) {
            if (other.getColor() != null) {
                return false;
            }
        } else if (!color.equals(other.getColor())) {
            return false;
        }
        if (mature != other.isMature()) {
            return false;
        }
        if (type == null) {
            if (other.getType() != null) {
                return false;
            }
        } else if (!type.equals(other.getType())) {
            return false;
        }
        return true;
    }
    
    /**
     * A hashcode representation of a {@code FruitTree} computed using
     * its type, color, maturity, and whether it bears bad fruit.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (badFruit ? 1231 : 1237);
        result = prime * result + ((color == null) ? 0 : color.hashCode());
        result = prime * result + (mature ? 1231 : 1237);
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * Returns a {@code String} representation of the tree based on its type.
     */
    @Override
    public String toString() {
        return type;
    }

    /**
     * If the tree is mature and has good fruit, updates the map of collected fruits,
     * sets mature to false and returns false.<br>
     * If the tree is mature and has bad fruit, does nothing to the map and returns true.<br>
     * If the tree is not mature, does nothing and returns false.
     * 
     * @throws IllegalArgumentException if the tree is mature and has good fruit (i.e.) ready
     * to be collected, but the map of collected fruits is null.
     */
    @Override
    public boolean meetPlayer(Map<String, Integer> collected) {
        if (isBadFruit()) {
            return true;
        }
        if (isMature()) {
            if (collected == null) {
                throw new IllegalArgumentException("map of collected fruits is null");
            }
            if (collected.containsKey(type)) {
                collected.put(type, collected.get(type) + 1);
            } else {
                collected.put(type, 1);
            }
            mature = false;
        }
        return false;
    }

    /**
     * Draws the tree with the given dimension at the location (px,py).<p>
     * An immature tree is an empty circle of its color.<br>
     * A mature tree bearing good fruit is a filled circle of its color.<br>
     * A mature tree bearing bad fruit if a filled circle of black.
     */
    @Override
    public void draw(Graphics g, int px, int py, int width, int height) {
        if (isBadFruit()) {
            g.setColor(Color.BLACK);
        } else {
            g.setColor(getColor());
        }
        if (isMature()) {
            g.fillOval(px, py, width, height);
        } else {
            g.drawOval(px, py, width, height);
        }
    }
    
    /**
     * @return a clone of the {@code FruitTree} as an {@code OrchardObject}
     */
    @Override
    public OrchardObject clone() {
        return new FruitTree(getType(), getColor(), isMature(), isBadFruit());
    }

}
