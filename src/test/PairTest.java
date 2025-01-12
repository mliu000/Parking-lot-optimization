package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utility.Pair;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Tests the pair custom defined data structure
*/
public class PairTest {

    public Pair<Integer, String> pair1; 
    public Pair<Boolean, Integer> pair2;

    // Create sample pairs
    @Before
    public void createPairs() {
        pair1 = new Pair<>(1, "1");
        pair2 = new Pair<>(true, 1);
    }

    // Check the constructor
    @Test
    public void constructorTest() {
        assertEquals(Integer.valueOf(1), pair1.getFirst());
        assertEquals("1", pair1.getSecond());

        assertEquals(true, pair2.getFirst());
        assertEquals(Integer.valueOf(1), pair2.getSecond());
    }

    // Change values test
    @Test
    public void changeValuesTest() {
        // Set the values
        pair1.setFirst(0);
        pair1.setSecond("0");
        pair2.setFirst(false);
        pair2.setSecond(0);

        // Check the values
        assertEquals(Integer.valueOf(0), pair1.getFirst());
        assertEquals("0", pair1.getSecond());
        assertEquals(false, pair2.getFirst());
        assertEquals(Integer.valueOf(0), pair2.getSecond());
    }

}
