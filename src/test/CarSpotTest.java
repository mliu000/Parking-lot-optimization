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
        assertEquals(123, carSpot.getParkingSpotId());

        checkParametersOfCarSpot(carSpot, false, "", "", 0);
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
            checkParametersOfCarSpot(carSpot, true, carPlate, "", 0);

            carSpot.unoccupy();
            checkParametersOfCarSpot(carSpot, false, "", "", 0);
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
        checkParametersOfCarSpot(carSpot, true, "Y00000", "", 1);

        // Occupy second motorcycle
        carSpot.occupyWithMotorcycle("Y12345");
        checkParametersOfCarSpot(carSpot, true, "Y00000", "Y12345", 2);
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
        checkParametersOfCarSpot(carSpot, true, "Y34245", "Y12345", 2);
    }

    // Initializes fully parked spot with 2 motorcycles, removes motorcycle in slot 1
    @Test
    public void removeMotorcycleFromSlot1FullSpot() {
        // Initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");

        // Remove motorcycle in slot 1
        carSpot.unoccupyMotorcycle("Y00000");
        checkParametersOfCarSpot(carSpot, true, "", "Y12345", 1);
    }

    // Initializes fully parked spot with 2 motorcycles, removes motorcycle in slot 2
    @Test
    public void removeMotorcycleFromSlot2FullSpot() {
        // Initialization
        carSpot.occupyWithMotorcycle("Y00000");
        carSpot.occupyWithMotorcycle("Y12345");

        // Remove motorcycle in slot 2
        carSpot.unoccupyMotorcycle("Y12345");
        checkParametersOfCarSpot(carSpot, true, "Y00000", "", 1);
    }

    // Sets up case where you have motorcycle in spot 1, then try to remove it.
    @Test
    public void removeHalfFullSpotMotorcycleSlot1() {
        // Set up/initialization
        carSpot.occupyWithMotorcycle("Y00000");

        // Remove the motorcycle
        carSpot.unoccupyMotorcycle("Y00000");
        checkParametersOfCarSpot(carSpot, false, "", "", 0);
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
        checkParametersOfCarSpot(carSpot, false, "", "", 0);
    }

    ///// HELPER METHOD ///// 
    
    // Tests the parameters of the carspot
    public void checkParametersOfCarSpot(CarSpot spot, boolean a, String b, String c, int d) {
        assertEquals(a, spot.getOccupiedStatus());
        assertEquals(b, spot.getLicensePlate());
        assertEquals(c, spot.getLicensePlate2());
        assertEquals(d, spot.getMotorcycleCount());
    }
}
