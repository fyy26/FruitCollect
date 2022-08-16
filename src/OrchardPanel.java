import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * A {@code JPanel} that paints the orchard board of a {@code FruitCollectGame}. It is
 * divided into many rectangular grids, and each object in the orchard is drawn in one grid.
 * 
 * @author yuyingf
 *
 */
public class OrchardPanel extends JPanel {
    
    private FruitCollectGame game;
    private int gridWidth;
    private int gridHeight;

    /**
     * Constructs a {@code OrchardPanel} with a given {@code FruitCollectGame} and
     * given grid dimensions.
     * 
     * @param game the {@code FruitCollectGame} this panel paints
     * @param gridWidth the width of a grid
     * @param gridHeight the height of a grid
     */
    public OrchardPanel(FruitCollectGame game, int gridWidth, int gridHeight) {
        this.game = game;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        setPreferredSize(new Dimension(game.getWidth() * gridWidth, game.getHeight() * gridHeight));
    }
    
    /**
     * Paints the current state of the {@code FruitCollectGame} on this panel.
     * The player is a small green filled circle, and each game object is painted
     * in its respective way.
     */
    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                game.drawObject(x, y, g, x * gridWidth, y * gridHeight, gridWidth, gridHeight);
            }
        }
        g.setColor(Color.GREEN);
        g.fillOval(game.getPlayerX() * gridWidth + (int)(gridWidth * 0.2),
                        game.getPlayerY() * gridHeight + (int)(gridHeight * 0.2),
                        (int)(gridWidth * 0.6), (int)(gridHeight * 0.6));
    }
    
    /**
     * @return the width of a grid
     */
    public int getGridWidth() {
        return gridWidth;
    }

    /**
     * @param gridWidth the new grid width
     */
    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    /**
     * @return the height of a grid
     */
    public int getGridHeight() {
        return gridHeight;
    }

    /**
     * @param gridHeight the new grid height
     */
    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }
    
}
