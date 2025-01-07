package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
The parking lot uses uses a MAX HEAP PRIORITY QUEUE implementation that stores the parking spots 
based on the distance to the entrance. Once a vehicle is assigned a spot, the parking spot is 
removed from the priority queue (still in the cumulative list of parking spots). Once the spot is 
no longer occupied, the spot goes back into the priority queue.
*/
public class ParkingLot {

    // Name of the parking lot (cannot be changed if needed)
    private String name;

    /*
     * Stores the set of parking spots. A set is chosen because parking spots are distinct, 
     * they cannot be added twice. moreover, a hashset is chosen because it maximizes the efficiency
     * looking up the parking spots, with the amortized runtime per lookup being O(1)
     * as opposed to O(n) for arrayList.
     */
    private Set<ParkingSpot> parkingSpots;

    // Separate priority queues of motorcycle, car and commerical spots. 
    
    // Constructs a new parking lot with no parking spots
    public ParkingLot(String name) {
        this.parkingSpots = new HashSet<>();
        this.name = name;
    }

    // MOST IMPORTANT FUNCTION:
    public void addParkingSpot() {
        
    }

    // Changes the name of the parking lot (if the owner wishes to change it)
    public void changeLotName(String newName) {
        name = newName;
    }

    
    
}
