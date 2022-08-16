/**
 * A movable player on a 2-D game board.
 * It is assumed that the top left corner of the board is position (0,0).
 * 
 * @author yuyingf
 *
 */
public class Player {
    
    private int x;
    private int y;

    /**
     * Constructs a {@code Player} at the position specified by (x,y) on the game board.
     */
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return the current x (horizontal) coordinate of the player
     */
    public int getX() {
        return x;
    }

    /**
     * @return the current y (vertical) coordinate of the player
     */
    public int getY() {
        return y;
    }
    
    /**
     * Moves the player to position (x,y) on the game board. 
     * 
     * @param x - the x (horizontal) coordinate to move to
     * @param y - the y (vertical) coordinate to move to
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
