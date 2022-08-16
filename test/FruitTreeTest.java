import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/** 
 *  Tests methods in {@code FruitTree}.
 */

public class FruitTreeTest {
    
    @Test
    public void testMeetPlayerImmatureInMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        Map<String, Integer> collected = new HashMap<String, Integer>();
        collected.put("Apple", 0);
        assertFalse(tree.meetPlayer(collected));
        assertEquals(0, collected.get("Apple"));
    }
    
    @Test
    public void testMeetPlayerGoodFruitInMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        Map<String, Integer> collected = new HashMap<String, Integer>();
        collected.put("Apple", 0);
        assertFalse(tree.meetPlayer(collected));
        assertEquals(1, collected.get("Apple"));
        assertFalse(tree.isMature());
        
        tree.mature();
        assertFalse(tree.meetPlayer(collected));
        assertEquals(2, collected.get("Apple"));
        assertFalse(tree.isMature());
    }
    
    @Test
    public void testMeetPlayerBadFruitInMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        tree.turnBad();
        Map<String, Integer> collected = new HashMap<String, Integer>();
        collected.put("Apple", 0);
        assertTrue(tree.meetPlayer(collected));
        assertEquals(0, collected.get("Apple"));
    }
    
    @Test
    public void testMeetPlayerImmatureEmptyMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        Map<String, Integer> collected = new HashMap<String, Integer>();
        assertFalse(tree.meetPlayer(collected));
        assertEquals(0, collected.size());
    }
    
    @Test
    public void testMeetPlayerGoodFruitEmptyMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        Map<String, Integer> collected = new HashMap<String, Integer>();
        assertFalse(tree.meetPlayer(collected));
        assertEquals(1, collected.size());
        assertEquals(1, collected.get("Apple"));
    }
    
    @Test
    public void testMeetPlayerBadFruitEmptyMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        tree.turnBad();
        Map<String, Integer> collected = new HashMap<String, Integer>();
        assertTrue(tree.meetPlayer(collected));
        assertEquals(0, collected.size());
    }

    @Test
    public void testMeetPlayerImmatureNullMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        assertFalse(tree.meetPlayer(null));
    }
    
    @Test
    public void testMeetPlayerGoodFruitNullMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        assertThrows(IllegalArgumentException.class, () -> {
            tree.meetPlayer(null);
        });
    }
    
    @Test
    public void testMeetPlayerBadFruitNullMap() {
        FruitTree tree = new FruitTree("Apple", Color.RED);
        tree.mature();
        tree.turnBad();
        assertTrue(tree.meetPlayer(null));
    }
    
    @Test
    public void testClone() {
        FruitTree tree1 = new FruitTree("Apple", Color.RED);
        OrchardObject tree2 = tree1.clone();
        assertTrue(tree2 instanceof FruitTree);
        assertTrue(tree1 != tree2 && tree1.equals(tree2));
        
        tree1.mature();
        tree2 = tree1.clone();
        assertTrue(tree2 instanceof FruitTree);
        assertTrue(tree1 != tree2 && tree1.equals(tree2));
        
        tree1.turnBad();
        tree2 = tree1.clone();
        assertTrue(tree2 instanceof FruitTree);
        assertTrue(tree1 != tree2 && tree1.equals(tree2));
    }

}
