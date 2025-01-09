package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import model.CarSpot;
import model.CommericalSpot;
import model.MotorcycleSpot;
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
     * Also, test the case where you try to add a spot with duplicate id and, 
     * tests to make sure the priority queue is initialized correctly. 
     * Just test the car one because they were implemented the same way
     */
    @Test
    public void constructorAndAddSpotsTest() {
        // Tests the empty lot
        ParkingLot emptyLot = new ParkingLot("Lot 0");
        assertEquals("Lot 0", emptyLot.getName());
        checkVacantSpotCounts(emptyLot, 0, 0, 0, 0, 0);

        // Tests the sample spot above
        checkVacantSpotCounts(parkingLot, 15, 6, 5, 4, 0);

        // Attempts to add spot with duplicate id
        ParkingSpot newSpot = parkingLot.addParkingSpot(12, 199, 2);
        assertNull(newSpot);
        checkVacantSpotCounts(parkingLot, 15, 6, 5, 4, 0);

        // Attempts to add new spot without duplicate id;
        ParkingSpot newSpot2 = parkingLot.addParkingSpot(19, 199, 2);
        assertFalse(newSpot2 == null);
        checkVacantSpotCounts(parkingLot, 16, 6, 5, 5, 0);

        // Check the id's to make sure the priority queues are initialized correctly
        PriorityQueue<CarSpot> vacantCarSpots = parkingLot.getVacantCarSpots();
        int[] idsInOrderCar = {15, 13, 14, 16, 12, 11};
        for (int i: idsInOrderCar) {
            CarSpot spot = vacantCarSpots.poll();
            assertEquals(i, spot.getId());
        }
        PriorityQueue<MotorcycleSpot> vacantMotorcycleSpots = parkingLot.getVacantMotorcycleSpots();
        int [] idsInOrderMotorcycle = {23, 21, 22, 20, 24};
        for (int i: idsInOrderMotorcycle) {
            MotorcycleSpot spot = vacantMotorcycleSpots.poll();
            assertEquals(i, spot.getId());
        }
        PriorityQueue<CommericalSpot> vacantCommericalSpots = parkingLot.getVacantCommericalSpots();
        int [] idsInOrderCommercial = {34, 33, 32, 31};
        for (int i: idsInOrderCommercial) {
            CommericalSpot spot = vacantCommericalSpots.poll();
            assertEquals(i, spot.getId());
        }
    }

    /*
     * Simple tests where you occupy parking spots 
     */
    @Test
    public void occupySpotTest() {
        // Occupy car spot with car
        ParkingSpot carSpot1 = parkingLot.occupySpot("EJ323N", 0);
        checkVacantSpotCounts(parkingLot, 15, 5, 5, 4, 0);
        checkParkingSpotParameters(carSpot1, true, "EJ323N");

        // Occupy motorcycle spot with motorcycle
        ParkingSpot motorcycleSpot1 = parkingLot.occupySpot("Y00342", 1);
        checkVacantSpotCounts(parkingLot, 15, 5, 4, 4, 0);
        checkParkingSpotParameters(motorcycleSpot1, true, "Y00342");
        
        // Occupy commercial spot with commercial vehicle
        ParkingSpot commercialSpot1 = parkingLot.occupySpot("BR4252", 2);
        checkVacantSpotCounts(parkingLot, 15, 5, 4, 3, 0);
        checkParkingSpotParameters(commercialSpot1, true, "BR4252");

        // Occupy car spot with motorcycle: 3 cases, empty and half-full (2 sides), create new lot
        ParkingLot lotWithoutMotorcycleSpot = new ParkingLot("Lot no motorcycles");
        lotWithoutMotorcycleSpot.addParkingSpot(111, 100, 0);
        lotWithoutMotorcycleSpot.addParkingSpot(112, 104, 0);
        lotWithoutMotorcycleSpot.addParkingSpot(113, 102, 0);
        // Case 1: empty car spot
        CarSpot carSpot2 = (CarSpot) lotWithoutMotorcycleSpot.occupySpot("Y12345", 1);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 1);
        checkCarSpotParameters(carSpot2, true, "Y12345", "", 1);
        // Case 2: half full spot (slot 1 already occupied)
        CarSpot carSpot3 = (CarSpot) lotWithoutMotorcycleSpot.occupySpot("Y23456", 1);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 0);
        checkCarSpotParameters(carSpot3, true, "Y12345", "Y23456", 2);
        // Case 3: half full spot (slot 2 already occupied)
        CarSpot carSpot4 = (CarSpot) lotWithoutMotorcycleSpot.unoccupySpot("Y12345");
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 1);
        checkCarSpotParameters(carSpot4, true, "", "Y23456", 1);
        CarSpot carSpot5 = (CarSpot) lotWithoutMotorcycleSpot.occupySpot("y00000", 1);
        checkCarSpotParameters(carSpot5, true, "Y00000", "Y23456", 2);

        // License plate is invalid (too long)
        try {
            parkingLot.occupySpot("fjkdjfa%s%", 0);
            fail();
        } catch (IllegalArgumentException e) {
            // Nothing
        }
        // License plate is too short
        try {
            parkingLot.occupySpot("jfk2#@$", 0);
            fail();
        } catch (IllegalArgumentException e) {
            // Nothing
        } 
        // License plate is valid
        try {
            parkingLot.occupySpot("fjk993", 0);
        } catch (IllegalArgumentException e) {
            fail();
        }
    }

    // Tests the unoccupy method (both regular case and unoccupying motorcycle from car spot)
    @Test
    public void unoccupySpotTest() {
        // Regular case: Unoccupy car from car spot, omitting motorcycle and commercial since they
        // are the same
        // Case 1: Successful removal 
        // - car
        parkingLot.occupySpot("GA325G", 0);
        CarSpot carSpot1 = (CarSpot) parkingLot.unoccupySpot("GA325G");
        checkVacantSpotCounts(parkingLot, 15, 6, 5, 4, 0);
        assertFalse(carSpot1 == null);
        // - motorcycle
        parkingLot.occupySpot("y23526", 1);
        MotorcycleSpot motorcycleSpot1 = (MotorcycleSpot) parkingLot.unoccupySpot("Y23526");
        checkVacantSpotCounts(parkingLot, 15, 6, 5, 4, 0);
        assertFalse(motorcycleSpot1 == null);
        // - commercial
        parkingLot.occupySpot("GE3244", 2);
        CommericalSpot commericalSpot1 = (CommericalSpot) parkingLot.unoccupySpot("GE3244");
        checkVacantSpotCounts(parkingLot, 15, 6, 5, 4, 0);
        assertFalse(commericalSpot1 == null);
        // Case 2: Unsuccessful removal
        // - car
        parkingLot.occupySpot("JG245G", 0);
        CarSpot carSpot2 = (CarSpot) parkingLot.unoccupySpot("HH207H");
        assertNull(carSpot2);
        checkVacantSpotCounts(parkingLot, 15, 5, 5, 4, 0);
        // - motorcycle
        parkingLot.occupySpot("Y07744", 1);
        MotorcycleSpot motorcycleSpot2 = (MotorcycleSpot) parkingLot.unoccupySpot("Y07324");
        assertNull(motorcycleSpot2);
        checkVacantSpotCounts(parkingLot, 15, 5, 4, 4, 0);
        // - motorcycle
        parkingLot.occupySpot("JG2424", 2);
        CommericalSpot commercialSpot2 = (CommericalSpot) parkingLot.unoccupySpot("JG2423");
        assertNull(commercialSpot2);
        checkVacantSpotCounts(parkingLot, 15, 5, 4, 3, 0);

        // Motorcycle unoccupying car spot
        ParkingLot lotWithoutMotorcycleSpot = new ParkingLot("Lot no motorcycles");
        lotWithoutMotorcycleSpot.addParkingSpot(111, 100, 0);
        lotWithoutMotorcycleSpot.addParkingSpot(112, 104, 0);
        lotWithoutMotorcycleSpot.addParkingSpot(113, 102, 0);
        lotWithoutMotorcycleSpot.occupySpot("Y12345", 1);
        lotWithoutMotorcycleSpot.occupySpot("Y24680", 1);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 0);
        // Case 1: motorcycle unoccupying full spot (spot 1)
        CarSpot carSpot11 = (CarSpot) lotWithoutMotorcycleSpot.unoccupySpot("Y12345");
        checkCarSpotParameters(carSpot11, true, "", "Y24680", 1);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 1);
        // Case 2: unoccuping half full spot (one parked in spot 2), empty afterwards
        CarSpot carSpot12 = (CarSpot) lotWithoutMotorcycleSpot.unoccupySpot("Y24680");
        checkCarSpotParameters(carSpot12, false, "", "", 0);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 3, 0, 0, 0);
        // Case 3: motorcycle unoccuping full spot (spot 2)
        lotWithoutMotorcycleSpot.occupySpot("Y12345", 1);
        lotWithoutMotorcycleSpot.occupySpot("Y24680", 1);
        CarSpot carSpot13 = (CarSpot) lotWithoutMotorcycleSpot.unoccupySpot("Y24680");
        checkCarSpotParameters(carSpot13, true, "Y12345", "", 1);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 2, 0, 0, 1);
        // Case 4: unoccuping half full spot (one parked in spot 2), empty afterwards
        CarSpot carSpot14 = (CarSpot) lotWithoutMotorcycleSpot.unoccupySpot("Y12345");
        checkCarSpotParameters(carSpot14, false, "", "", 0);
        checkVacantSpotCounts(lotWithoutMotorcycleSpot, 3, 3, 0, 0, 0);





    }
    ///// HELPER METHODs /////
    
    // Helper that checks the size of the dynamic fields that store parking spots
    public void checkVacantSpotCounts(ParkingLot lot, int a, int b, int c, int d, int e) {
        assertEquals(a, lot.getParkingSpots().size());
        assertEquals(b, lot.getVacantCarSpots().size());
        assertEquals(c, lot.getVacantMotorcycleSpots().size());
        assertEquals(d, lot.getVacantCommericalSpots().size());
        assertEquals(e, lot.getHalfFullCarSpots().size());
    }

    /*
     * Tests the parameters of the parking spots 
     * Id and distance omitted because they are not changed during the spot occupation process
    */ 
    public void checkParkingSpotParameters(ParkingSpot spot, boolean a, String b) {
        assertEquals(a, spot.getOccupiedStatus());
        assertEquals(b, spot.getLicensePlate());
    }

    // Tests the parameters of the parking spots 
    public void checkCarSpotParameters(CarSpot spot, boolean a, String b, String c, int d) {
        assertEquals(a, spot.getOccupiedStatus());
        assertEquals(b, spot.getLicensePlate());
        assertEquals(c, spot.getLicensePlate2());
        assertEquals(d, spot.getMotorcycleCount());
    }
}
