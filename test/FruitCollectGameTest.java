import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests methods in {@code FruitCollectGame}.
 * 
 * @author yuyingf
 *
 */
public class FruitCollectGameTest {
    
    private OrchardObject[][] setup;
    
    @BeforeEach
    public void setUp() {
        setup = new OrchardObject[2][3];
        setup[0][0] = new Pond();
        setup[0][1] = new FruitTree("Peach", Color.PINK);
        setup[0][2] = new FruitTree("Peach", Color.PINK);
        setup[1][0] = new FruitTree("Banana", Color.YELLOW);
        setup[1][1] = new Pond();
        setup[1][2] = new FruitTree("Apple", Color.RED);
    }
    
    // The array part of the first constructor is handled by OrchardBoard and has been tested
    // in OrchardBoardTest.
    
    @Test
    public void testConstructorWithSetupGood() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        
        assertEquals(1, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertFalse(game.hasEnded());
        assertEquals(0, game.getGen());
        Set<String> fruits = game.getFruitSet();
        assertEquals(3, fruits.size());
        assertTrue(fruits.contains("Peach"));
        assertTrue(fruits.contains("Banana"));
        assertTrue(fruits.contains("Apple"));
    }
    
    @Test
    public void testConstructorWithSetupPlayerNotOnBoard() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(setup, 5, 0);
        });
    }
    
    @Test
    public void testConstructorWithSetupPlayerInPond() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(setup, 0, 0);
        });
    }
    
    @Test
    public void testConstructorRandomGood() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        FruitCollectGame game = new FruitCollectGame(2, 3, fruits, 15, 1, 0);
        
        assertEquals(1, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertFalse(game.hasEnded());
        assertEquals(0, game.getGen());
        Set<String> fruitTypes = fruits.keySet();
        for (String fruit : game.getFruitSet()) {
            assertTrue(fruitTypes.contains(fruit));
        }
        // test if a pond is placed at the player's initial position
        game.move(1, 0);
        assertFalse(game.hasEnded());
    }
    
    @Test
    public void testConstructorRandomInvalidWidth() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(-5, 2, fruits, 15, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomInvalidHeight() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(5, -2, fruits, 15, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomNullMap() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(2, 3, null, 15, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomEmptyMap() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(2, 3, fruits, 15, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomPondPercentNegative() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(2, 3, fruits, -15, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomPondPercentTooBig() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(2, 3, fruits, FruitCollectGame.MAX_POND_PERCENT + 1, 1, 0);
        });
    }
    
    @Test
    public void testConstructorRandomPondPlayerNotOnBoard() {
        Map<String, Color> fruits = new HashMap<String, Color>();
        fruits.put("Apple", Color.RED);
        fruits.put("Banana", Color.YELLOW);
        fruits.put("Peach", Color.PINK);
        assertThrows(IllegalArgumentException.class, () -> {
            new FruitCollectGame(2, 3, fruits, 15, -1, 0);
        });
    }
    
    @Test
    public void testGetFruitSet() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        Set<String> fruits = game.getFruitSet();
        assertEquals(3, fruits.size());
        assertTrue(fruits.contains("Peach"));
        assertTrue(fruits.contains("Banana"));
        assertTrue(fruits.contains("Apple"));
        // test encapsulation
        fruits.remove("Apple");
        assertEquals(3, game.getFruitSet().size());
        assertTrue(game.getFruitSet().contains("Apple"));
        assertTrue(game.isCollecting("Apple"));
        fruits.add("Blueberry");
        assertFalse(game.getFruitSet().contains("Blueberry"));
        assertFalse(game.isCollecting("Blueberry"));
    }
    
    @Test
    public void testIsCollecting() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        assertTrue(game.isCollecting("Apple"));
        assertTrue(game.isCollecting("Banana"));
        assertTrue(game.isCollecting("Peach"));
        assertFalse(game.isCollecting("Blueberry"));
    }
    
    @Test
    public void testNumCollected() {
        ((FruitTree) setup[1][2]).mature();  // Apple
        ((FruitTree) setup[0][2]).mature();  // Peach
        ((FruitTree) setup[1][0]).mature();  // Banana
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertEquals(-1, game.numCollected("Blueberry"));
        
        game.move(2, 1);  // Apple
        assertEquals(1, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        
        game.move(2, 0);  // Peach
        assertEquals(1, game.numCollected("Apple"));
        assertEquals(1, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        
        game.move(0, 1);  // Banana
        assertEquals(1, game.numCollected("Apple"));
        assertEquals(1, game.numCollected("Peach"));
        assertEquals(1, game.numCollected("Banana"));
    }
    
    @Test
    public void testGoalAccomplished() {
        OrchardObject[][] setupBig = new OrchardObject[3][12];
        for (int i = 0; i < 11; i++) {
            FruitTree apple = new FruitTree("Apple", Color.RED);
            apple.mature();
            setupBig[0][i] = apple;
            FruitTree banana = new FruitTree("Banana", Color.YELLOW);
            banana.mature();
            setupBig[1][i] = banana;
            FruitTree peach = new FruitTree("Peach", Color.PINK);
            peach.mature();
            setupBig[2][i] = peach;
        }
        
        // Collect one of each fruit in each iteration.
        // Goal should be accomplished after 30 moves.
        FruitCollectGame game = new FruitCollectGame(setupBig, 11, 1);
        int moves = 0;
        boolean done = false;
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 3; col++) {
                game.move(row, col);
                moves++;
                done = game.goalAccomplished();
                if (done) {
                    break;
                }
            }
            if (done) {
                break;
            }
        }
        assertEquals(30, moves);
        
        // Collect all 11 fruit of one type in each iteration.
        // Goal should be accomplished after 22 + 10 = 32 moves.
        game = new FruitCollectGame(setupBig, 11, 1);
        moves = 0;
        done = false;
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 11; row++) {
                game.move(row, col);
                moves++;
                done = game.goalAccomplished();
                if (done) {
                    break;
                }
            }
            if (done) {
                break;
            }
        }
        assertEquals(32, moves);
    }
    
    @Test
    public void testMovePond() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        game.move(0, 0);
        assertEquals(0, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertTrue(game.hasEnded());
    }
    
    @Test
    public void testMoveImmatureTree() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        game.move(2, 0);
        assertEquals(2, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertFalse(game.hasEnded());
    }
    
    @Test
    public void testMoveGoodFruit() {
        ((FruitTree) setup[0][2]).mature();
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        game.move(2, 0);
        assertEquals(2, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(1, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertFalse(game.hasEnded());
        
        // The tree at (2,0) should now be immature
        game.move(2, 0);
        assertEquals(2, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(1, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertFalse(game.hasEnded());
    }
    
    @Test
    public void testMoveBadFruit() {
        ((FruitTree) setup[0][2]).mature();
        ((FruitTree) setup[0][2]).turnBad();
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        game.move(2, 0);
        assertEquals(2, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertTrue(game.hasEnded());
    }
    
    @Test
    public void testMoveOffBoard() {
        FruitCollectGame game = new FruitCollectGame(setup, 1, 0);
        game.move(-1, 0);
        assertEquals(1, game.getPlayerX());
        assertEquals(0, game.getPlayerY());
        assertEquals(0, game.numCollected("Apple"));
        assertEquals(0, game.numCollected("Peach"));
        assertEquals(0, game.numCollected("Banana"));
        assertFalse(game.hasEnded());
    }
    
}
