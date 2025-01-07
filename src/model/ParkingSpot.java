package model;

/*
 @ Mu Ye Liu, Jan 2025

 The parking spot class, as well as its inheritors, are essentially the nodes of the disjoint sets.
 Once a parking spot is occupied, it becomes rooted to the next closest spot. If the parked vehicle
 leaves the spot, the disjoint set
 */
public abstract class ParkingSpot {

    /*
     * License plate number of parked vehicle
     * Requirements: No spaces, must be all caps, 5-7 characters long including all chars and nums. 
     * Empty string if spot is vacant. 
     */
    protected String licensePlate;

    // Number/Id of the parking spot
    protected int id;

    /*
     * distance from the entrance (units are not specified as it is context
     * Cannot be changed as specific units of measurement are not relevant. Also the
     * location of the parking spot to the entrance will not change.
     */
    protected final double distance;

    // True of parking spot is occupied, false otherwise.
    protected boolean occupied;

    /*
     * Constructs a new empty parking spot with given id, distance from entrance,
     * and unoccupied status
     */
    public ParkingSpot(int id, double distance) {
        this.id = id;
        this.distance = distance;
        this.licensePlate = "";
        this.occupied = false;
    }

    // Occupies the spot and sets the license plate
    // REQUIRES: The spot is not already occupied, plate must meet requirements
    public void occupy(String plate) {
        licensePlate = plate;
        occupied = true;
    }

    // Unoccupies the occupied spot
    // REQUIRES: The spot must be originally occupied
    public void unoccupy() {
        licensePlate = "";
        occupied = false;
    }

    // If the parking lot undergoes a id overhaul, the id can be changed with that
    // method
    public void changeId(int newId) {
        id = newId;
    }

    ///// GETTER METHODS /////

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public boolean getOccupiedStatus() {
        return occupied;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
