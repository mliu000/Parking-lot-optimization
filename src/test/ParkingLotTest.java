package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import model.CarSpot;
import model.ParkingLot;
import model.ParkingSpot;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Tests the functionality of a sample parking lot with 4 commerial spots, 5 motorcycle spots, 
as well as 6 car spots, all with different id's and distances
*/
public class ParkingLotTest {

    // Parking Lot
    public ParkingLot parkingLot;

    // Construct new parking lot with instantiated parking spots
    @Before
    public void constructParkingLotAndSpots() {
        parkingLot = new ParkingLot("Lot 1");

        // Adds the car spots
        int[] carSpotIds = {11, 12, 13, 14, 15, 16};
        int[] carSpotDists = {100, 101, 108, 106, 109, 104};
        for (int i = 0; i < 6; i++) {
            parkingLot.addParkingSpot(carSpotIds[i], carSpotDists[i], 0);
        }

        // Adds the motorcycle spots
        int [] motorcycleSpotIds = {20, 21, 22, 23, 24};
        int [] motorcycleSpotDists = {51, 57, 53, 59, 50};
        for (int i = 0; i < 5; i++) {
            parkingLot.addParkingSpot(motorcycleSpotIds[i], motorcycleSpotDists[i], 1);
        }

        // Adds the commercial spots
        int [] commercialSpotIds = {31, 32, 33, 34};
        int [] commercialSpotDists = {201, 202, 203, 204};
        for (int i = 0; i < 4; i++) {
            parkingLot.addParkingSpot(commercialSpotIds[i], commercialSpotDists[i], 2);
        }
    }

    /*
     * Tests the constructor in 2 ways: Creates a local instance of parking lot with no spots
     * Tests the parking lot constructed above
     * Also, test the case where you try to add a spot with duplicate id
     */
    @Test
    public void constructorAndAddSpotsTest() {
        // Tests the empty lot
        ParkingLot emptyLot = new ParkingLot("Lot 0");
        assertEquals("Lot 0", emptyLot.getName());
        checkSpotsSize(emptyLot, 0, 0, 0, 0, 0);

        // Tests the sample spot above
        checkSpotsSize(parkingLot, 15, 6, 5, 4, 0);

        // Attempts to add spot with duplicate id
        ParkingSpot newSpot = parkingLot.addParkingSpot(12, 199, 2);
        assertNull(newSpot);
        checkSpotsSize(parkingLot, 15, 6, 5, 4, 0);

        // Attempts to add new spot without duplicate id;
        ParkingSpot newSpot2 = parkingLot.addParkingSpot(19, 199, 2);
        assertFalse(newSpot2 == null);
        checkSpotsSize(parkingLot, 16, 6, 5, 5, 0);
    }

    /* 
     * Tests to make sure the priority queue is initialized correctly. 
     * Just test the car one because they were implemented the same way
    */
    @Test
    public void priorityQueueInitializationTest() {
        PriorityQueue<CarSpot> vacantCarSpots = parkingLot.getVacantCarSpots();
        // Check the id's to make sure they are correct
        int[] idsInOrder = {15, 13, 14, 16, 12, 11};
        for (int i: idsInOrder) {
            CarSpot spot = vacantCarSpots.poll();
            assertEquals(i, spot.getId());
        }
    }

    ///// HELPER METHOD
    
    // Helper that checks the size of the dynamic fields that store parking spots
    public void checkSpotsSize(ParkingLot lot, int a, int b, int c, int d, int e) {
        assertEquals(a, lot.getParkingSpots().size());
        assertEquals(b, lot.getVacantCarSpots().size());
        assertEquals(c, lot.getVacantMotorcycleSpots().size());
        assertEquals(d, lot.getVacantCommericalSpots().size());
        assertEquals(e, lot.getHalfFullCarSpots().size());
    }
}
