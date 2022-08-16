import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

/**
 * Tests methods in {@code OrchardBoard}.
 * 
 * @author yuyingf
 *
 */
public class OrchardBoardTest {
    
    @Test
    public void testConstructorValidMulti() {
        OrchardObject[][] setup = new OrchardObject[2][3];
        setup[0][0] = new Pond();
        setup[1][2] = new FruitTree("Apple", Color.RED);
        OrchardBoard board = new OrchardBoard(setup);
        assertEquals(3, board.getWidth());
        assertEquals(2, board.getHeight());
        assertTrue(board.hasObject(0, 0));
        assertTrue(board.hasObject(2, 1));
        assertFalse(board.hasObject(0, 1));
    }
    
    @Test
    public void testConstructorValidSingleRow() {
        OrchardObject[][] setup = new OrchardObject[1][3];
        setup[0][0] = new Pond();
        OrchardBoard board = new OrchardBoard(setup);
        assertEquals(3, board.getWidth());
        assertEquals(1, board.getHeight());
        assertTrue(board.hasObject(0, 0));
        assertFalse(board.hasObject(2, 0));
    }
    
    @Test
    public void testConstructorValidSingleCol() {
        OrchardObject[][] setup = new OrchardObject[3][1];
        setup[1][0] = new Pond();
        OrchardBoard board = new OrchardBoard(setup);
        assertEquals(1, board.getWidth());
        assertEquals(3, board.getHeight());
        assertTrue(board.hasObject(0, 1));
        assertFalse(board.hasObject(0, 0));
    }
    
    @Test
    public void testConstructorValidSingleton() {
        OrchardObject[][] setup = new OrchardObject[1][1];
        setup[0][0] = new Pond();
        OrchardBoard board = new OrchardBoard(setup);
        assertEquals(1, board.getWidth());
        assertEquals(1, board.getHeight());
        assertTrue(board.hasObject(0, 0));
    }

    @Test
    public void testConstructorEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrchardBoard(new OrchardObject[][] {});
        });
    }
    
    @Test
    public void testConstructorNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrchardBoard(null);
        });
    }
    
    @Test
    public void testOnBoard() {
        OrchardBoard board = new OrchardBoard(new OrchardObject[3][3]);
        
        // Center
        assertTrue(board.onBoard(1,1));
        // Edge
        assertTrue(board.onBoard(1, 0));
        assertTrue(board.onBoard(0, 1));
        assertTrue(board.onBoard(2, 1));
        assertTrue(board.onBoard(1, 2));
        // Corner
        assertTrue(board.onBoard(0, 0));
        assertTrue(board.onBoard(2, 0));
        assertTrue(board.onBoard(0, 2));
        assertTrue(board.onBoard(2, 2));
        // Off too small (negative)
        assertFalse(board.onBoard(-1, 0));
        assertFalse(board.onBoard(0, -2));
        assertFalse(board.onBoard(-1, -2));
        // Off too big
        assertFalse(board.onBoard(4, 0));
        assertFalse(board.onBoard(0, 4));
        assertFalse(board.onBoard(5, 4));
    }
    
    @Test
    public void testGetObject() {
        OrchardObject[][] setup = new OrchardObject[2][3];
        Pond pond = new Pond();
        setup[0][0] = pond;
        FruitTree tree = new FruitTree("Apple", Color.RED);
        setup[1][2] = tree;
        OrchardBoard board = new OrchardBoard(setup);
        
        // On board, has object
        assertEquals(pond, board.getObject(0, 0));
        assertEquals(tree, board.getObject(2, 1));
        
        // On board, has no object
        assertNull(board.getObject(0, 1));
        
        // Not on board
        assertNull(board.getObject(-1, 1));
        assertNull(board.getObject(5, 4));
    }
    
    @Test
    public void testHasObject() {
        OrchardObject[][] setup = new OrchardObject[2][3];
        setup[0][0] = new Pond();
        setup[1][2] = new FruitTree("Apple", Color.RED);
        OrchardBoard board = new OrchardBoard(setup);
        
        // On board, has object
        assertTrue(board.hasObject(0, 0));
        assertTrue(board.hasObject(2, 1));
        
        // On board, has no object
        assertFalse(board.hasObject(0, 1));
        
        // Not on board
        assertFalse(board.hasObject(-1, 1));
        assertFalse(board.hasObject(5, 4));
    }
    
    @Test
    public void testEncapsulation() {
        OrchardObject[][] setup = new OrchardObject[2][3];
        setup[0][0] = new Pond();
        OrchardBoard board = new OrchardBoard(setup);
        
        setup[0][0] = new FruitTree("Apple", Color.RED);
        assertNotEquals(board.getObject(0,0), setup[0][0]);
        assertEquals(new Pond(), board.getObject(0, 0));
    }
    
    @Test
    public void testModificable() {
        OrchardObject[][] setup = new OrchardObject[2][3];
        setup[0][0] = new FruitTree("Apple", Color.RED);
        OrchardBoard board = new OrchardBoard(setup);
        
        FruitTree tree = (FruitTree) board.getObject(0, 0);
        assertFalse(tree.isMature());
        assertEquals(tree.isMature(), ((FruitTree) board.getObject(0, 0)).isMature());
        tree.mature();
        assertTrue(tree.isMature());
        assertEquals(tree.isMature(), ((FruitTree) board.getObject(0, 0)).isMature());
    }

}
