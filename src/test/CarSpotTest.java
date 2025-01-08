package test;

import org.junit.Test;

import model.CarSpot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Tests the functionality of the car parking spot class in JUnit 4. We will test the functionality of
occupying with car, as well as with motorcycle
*/
public class CarSpotTest {

    public CarSpot carSpot;

    // Creates a new car spot
    @Before
    public void createCarSpot() {
        carSpot = new CarSpot(123, 52.3);
    }

    ///// CAR OCCUPATION TESTS /////

    // Tests the constructor to make sure the fields are initialized correctly
    @Test
    public void testConstructor() {
        assertEquals(52.3, carSpot.getDistance(), 0.001);
        assertEquals(123, carSpot.getId());
        assertFalse(carSpot.getOccupiedStatus());
        assertTrue(carSpot.getLicensePlate().equals("") && 
            carSpot.getLicensePlate2().equals(""));
        assertEquals(0, carSpot.getMotorcycleCount());
    }

    // Test: Occupies and unoccupies a spot with a car 3 times
    @Test
    public void occupyAndUnoccupyCarTest() {
        List<String> carPlates = new ArrayList<>();
        carPlates.add("ABC123");
        carPlates.add("123ABC");
        carPlates.add("AB123C");
        for (String carPlate: carPlates) {
            carSpot.occupy(carPlate);
            assertTrue(carSpot.getOccupiedStatus());
            assertEquals(carPlate, carSpot.getLicensePlate());
            assertEquals("", carSpot.getLicensePlate2());
            assertEquals(0, carSpot.getMotorcycleCount());

            carSpot.unoccupy();
            assertFalse(carSpot.getOccupiedStatus());
            assertEquals("", carSpot.getLicensePlate());
            assertEquals("", carSpot.getLicensePlate2());
            assertEquals(0, carSpot.getMotorcycleCount());
        }
    }

    ///// MOTORCYCLE OCCUPATION TESTS /////

    /*
     * Occupies motorcycle in an empty spot, checks for parameters.
     * Then, occupies second slot with another motorcycle (where 1 motorcycle is in the first slot)
     */
    @Test
    public void occupyEmptyStallWithMotorcycle() {
        // Occupy first motorcycle
        carSpot.occupyWithMotorcycle("Y00000");
        assertTrue(carSpot.getOccupiedStatus());
        assertEquals("Y00000", carSpot.getLicensePlate());
        assertEquals("", carSpot.getLicensePlate2());
        assertEquals(1, carSpot.getMotorcycleCount());

        // Occupy second motorcycle
        carSpot.occupyWithMotorcycle("Y12345");
        assertTrue(carSpot.getOccupiedStatus());
        assertEquals("Y00000", carSpot.getLicensePlate());
        assertEquals("Y12345", carSpot.getLicensePlate2());
        assertEquals(2, carSpot.getMotorcycleCount());
    }

    /*
     * Initializes half parked spot with motorcycle already parked in slot 2, and tries to add
     * another motorcycle to spot 1. (similar to the second part of the above test method)
    */
    @Test
    public void occupyHalfFullStallWithMotorcycleInSlot2() {
        // Initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");
        carSpot.unoccupyMotorcycle("Y00000");

        // Occupy another motorcycle
        carSpot.occupyWithMotorcycle("Y34245");

        assertTrue(carSpot.getOccupiedStatus());
        assertEquals("Y34245", carSpot.getLicensePlate());
        assertEquals("Y12345", carSpot.getLicensePlate2());
        assertEquals(2, carSpot.getMotorcycleCount());
    }

    // Initializes fully parked spot with 2 motorcycles, removes motorcycle in slot 1
    @Test
    public void removeMotorcycleFromSlot1FullSpot() {
        // Initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");

        // Remove motorcycle in slot 1
        carSpot.unoccupyMotorcycle("Y00000");

        assertTrue(carSpot.getOccupiedStatus());
        assertEquals("", carSpot.getLicensePlate());
        assertEquals("Y12345", carSpot.getLicensePlate2());
        assertEquals(1, carSpot.getMotorcycleCount());
    }

    // Initializes fully parked spot with 2 motorcycles, removes motorcycle in slot 2
    @Test
    public void removeMotorcycleFromSlot2FullSpot() {
        // Initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");

        // Remove motorcycle in slot 2
        carSpot.unoccupyMotorcycle("Y12345");

        assertTrue(carSpot.getOccupiedStatus());
        assertEquals("Y00000", carSpot.getLicensePlate());
        assertEquals("", carSpot.getLicensePlate2());
        assertEquals(1, carSpot.getMotorcycleCount());
    }

    // Sets up case where you have motorcycle in spot 1, then try to remove it.
    @Test
    public void removeHalfFullSpotMotorcycleSlot1() {
        // Set up/initialization
        carSpot.occupyWithMotorcycle("Y00000");

        // Remove the motorcycle
        carSpot.unoccupyMotorcycle("Y00000");

        assertFalse(carSpot.getOccupiedStatus());
        assertEquals("", carSpot.getLicensePlate());
        assertEquals("", carSpot.getLicensePlate2());
        assertEquals(0, carSpot.getMotorcycleCount());
    }

    // Sets up case where you have motorcycle in spot 2, then try to remove it.
    @Test
    public void removeHalfFullSpotMotorcycleSlot2() {
        // Set up/initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");
        carSpot.unoccupyMotorcycle("Y00000");

        // Remove the motorcycle
        carSpot.unoccupyMotorcycle("Y12345");

        assertFalse(carSpot.getOccupiedStatus());
        assertEquals("", carSpot.getLicensePlate());
        assertEquals("", carSpot.getLicensePlate2());
        assertEquals(0, carSpot.getMotorcycleCount());
    }


}
