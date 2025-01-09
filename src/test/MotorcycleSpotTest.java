package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.MotorcycleSpot;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Tests the functionality of the commercial parking spot class in JUnit 4. 
Same as the commerical spot test class because the classes are essentially the same
*/
public class MotorcycleSpotTest {

    public MotorcycleSpot motorcycleSpot;

    // Creates a new commercial spot.
    @Before
    public void createCommercialSpot() {
        motorcycleSpot = new MotorcycleSpot(100, 10);
    }

    // Tests the constructor by testing it's distance.
    @Test
    public void constructorTest() {
        assertEquals(10, motorcycleSpot.getDistance(), 0.001); // Due to double rounding errors
        assertFalse(motorcycleSpot.getOccupiedStatus());
        assertEquals(100, motorcycleSpot.getId());
        assertEquals("", motorcycleSpot.getLicensePlate());
    }

    // Tests: Occupy spot with motorcycle, then unoccupy it (x3)
    @Test
    public void occupyAndUnoccupyTest() {
        List<String> plates = new ArrayList<>();
        plates.add("Y00000");
        plates.add("Y00001");
        plates.add("Y12345");

        for (String plate: plates) {
            motorcycleSpot.occupy(plate);
            assertEquals(plate, motorcycleSpot.getLicensePlate());
            assertTrue(motorcycleSpot.getOccupiedStatus());

            motorcycleSpot.unoccupy();
            assertEquals("", motorcycleSpot.getLicensePlate());
            assertFalse(motorcycleSpot.getOccupiedStatus());
        }

    }
}
