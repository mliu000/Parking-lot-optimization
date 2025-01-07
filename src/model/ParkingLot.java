package model;

import java.util.HashSet;
import java.util.PriorityQueue;
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

    // Name of the parking lot (can be changed if needed)
    private String name;

    /*
     * Stores all the parking spots (all types). A set is chosen because parking spots are distinct, 
     * they cannot be added twice. moreover, a hashset is chosen because it maximizes the efficiency
     * looking up the parking spots, with the amortized runtime per lookup being O(1)
     * as opposed to O(n) for arrayList.
     */
    private Set<ParkingSpot> parkingSpots;

    /*
     * 4 separate Max Heap priority queues to store vacant parking spots: They are the following: 
     * - One that stores the vacant car spot
     * - One that stores the vacant motorcycle spots
     * - One that stores the vacant commercial spots
     * - One that stores half full car spots that have one motorcycle parked in it
     */
    private PriorityQueue<CarSpot> vacantCarSpots; 
    private PriorityQueue<MotorcycleSpot> vacantMotorcycleSpots;
    private PriorityQueue<CommericalSpot> vacantCommericalSpots;
    private PriorityQueue<CarSpot> halfFullCarSpots;

    
    // Constructs a new empty parking lot with no parking spots. Initializes set and priority queue
    public ParkingLot(String name) {
        this.name = name;
        this.parkingSpots = new HashSet<>();
        this.vacantCarSpots = new PriorityQueue<>((a, b) 
            -> Double.compare(b.getDistance(), a.getDistance()));
        this.vacantMotorcycleSpots = new PriorityQueue<>((a, b) 
            -> Double.compare(b.getDistance(), a.getDistance()));
        this.vacantCommericalSpots = new PriorityQueue<>((a, b) 
            -> Double.compare(b.getDistance(), a.getDistance()));
        this.halfFullCarSpots = new PriorityQueue<>((a, b) 
            -> Double.compare(b.getDistance(), a.getDistance()));
    }

    /*
     * MOST IMPORTANT FUNCTION: adds new parking spot with set id and distance. 
     * Stores it in set and appropriate priority queue. 
     * The integer flag determines which type of parking spot is created. 
     * 0 = car spot
     * 1 = motorcycle spot
     * 2 = commercial spot
     * 
     * REQUIRES: the integer flag must be 0, 1, or 2
    */
    public void addParkingSpot(int id, double distance, int flag) {
        switch(flag) {
            case 0:
                CarSpot newCarSpot = new CarSpot(id, distance);
                parkingSpots.add(newCarSpot);
                vacantCarSpots.add(newCarSpot);
                break;
            case 1:
                MotorcycleSpot newMotorcycleSpot = new MotorcycleSpot(id, distance);
                parkingSpots.add(newMotorcycleSpot);
                vacantMotorcycleSpots.add(newMotorcycleSpot);
                break;
            default: // Case 2
                CommericalSpot newCommercialSpot = new CommericalSpot(id, distance);
                parkingSpots.add(newCommercialSpot);
                vacantCommericalSpots.add(newCommercialSpot);
                break;
        }
    }

    /*
     * Occupies a parking spot corresponding to the vehicle by removing the parking spot from 
     * priority queue, and assigning plate to it
     * The integer flag is the same as the method above
     * 
     * The input license plate will be formatted to ATTEMPT to meet the conditions
     * 
     * REQUIRES: the integer flag must be 0, 1 or 2
     * Throws exception if license plate string does not meet conditions 
     */
    public void occupySpot(String plate, int flag) throws IllegalArgumentException {
        plate = formatPlate(plate);
        if (plate.length() < 5 || plate.length() > 7) {
            throw new IllegalArgumentException("Plate is too long or short");
        }

        switch (flag) {
            case 0:
                CarSpot carSpotToOccupy = vacantCarSpots.poll();
                carSpotToOccupy.occupy(plate);
                break;
            case 1:
                MotorcycleSpot motorcycleSpotToOccupy = vacantMotorcycleSpots.poll();
                motorcycleSpotToOccupy.occupy(plate);
                break;
            default: // Case 2: 
                CommericalSpot commericalSpotToOccupy = vacantCommericalSpots.poll();
                commericalSpotToOccupy.occupy(plate);
                break;
        }
    }

    /*
     * Unoccupies spot based on given license plate. 
     * Throws exception if plate cannot be found
     */
    public void unoccupySpot(String plate) {
        plate = formatPlate(plate);
    }

    /*
     * Unoccupies a parking spot based on license plate entry
     * Throws exception if parking spot corresponding to license plate is not found. 
     */

    // Changes the name of the parking lot (if the owner wishes to change it)
    public void changeLotName(String newName) {
        name = newName;
    }

    ///// HELPER METHODS /////
    
    /*
     * Formats the inputted license plate string to get rid of all spaces, special chars, 
     * as well as capitalize all inputted letters.
    */
    public String formatPlate(String input) {
        return input.replaceAll("[^A-Z0-9]", "");
    }

    ///// GETTER METHODS /////
    
    public String getName() {
        return name;
    }
    
    public Set<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }
}
