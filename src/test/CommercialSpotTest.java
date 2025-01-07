package test;

import model.CommericalSpot;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Tests the functionality of the commercial parking spot class in JUnit 4. 
*/
public class CommercialSpotTest {

    public CommericalSpot commericalSpot;

    // Creates a new commercial spot.
    @Before
    public void createCommercialSpot() {
        commericalSpot = new CommericalSpot(100, 10);
    }

    // Tests the constructor by testing it's distance.
    @Test
    public void constructorTest() {
        assertEquals(10, commericalSpot.getDistance(), 0.001); // Due to double rounding errors
        assertFalse(commericalSpot.getOccupiedStatus());
        assertEquals(100, commericalSpot.getId());
        assertEquals("", commericalSpot.getLicensePlate());
    }

    // Test: Changes the id of the spot and checks for output
    @Test
    public void changeIdTest() {
        commericalSpot.changeId(101);
        assertEquals(101, commericalSpot.getId());
    }

    // Tests: Occupy spot with commercial vehicle, then unoccupy it (x3)
    @Test
    public void occupyAndUnoccupyTest() {
        List<String> plates = new ArrayList<>();
        plates.add("AB1234");
        plates.add("BC1234");
        plates.add("CG3241");

        for (String plate: plates) {
            commericalSpot.occupy(plate);
            assertEquals(plate, commericalSpot.getLicensePlate());
            assertTrue(commericalSpot.getOccupiedStatus());

            commericalSpot.unoccupy();
            assertEquals("", commericalSpot.getLicensePlate());
            assertFalse(commericalSpot.getOccupiedStatus());
        }

    }

}
