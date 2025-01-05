package model;

import java.util.ArrayList;
import java.util.List;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
The parking lot uses uses a MAX HEAP PRIORITY QUEUE implementation that stores the parking spots 
based on the distance to the entrance. Once a vehicle is assigned a spot, the parking spot is 
removed from the priority queue (still in the cumulative list of parking spots). Once the spot is 
no longer occupied, the spot goes back into the priority queue.
*/
public class ParkingLot {

    // Name of the parking lot.
    private final String NAME;

    // Stores the list of parking spots
    private List<ParkingSpot> parkingSpots;

    // Separate priority queues of motorcycle, car and commerical spots. 
    
    // Constructs a new parking lot with no parking spots
    public ParkingLot(String name) {
        this.parkingSpots = new ArrayList<>();
        this.NAME = name;
    }

    
    
}
