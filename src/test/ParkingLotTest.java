package test;

import org.junit.Before;
import org.junit.Test;

import model.ParkingLot;

public class ParkingLotTest {

    public ParkingLot parkingLot;

    @Before
    public void constructParkingLotAndSpots() {
        parkingLot = new ParkingLot("Lot 1");
        

    }

    @Test
    public void constructorTest() {

    }
    
}
