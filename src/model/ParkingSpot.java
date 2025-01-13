package model;

/*
 @ Mu Ye Liu, Jan 2025

 The parking spot class, which represents an individual parking spot. It has a distinct id, distance 
 from entrance, and occupied status. Also, it records the license plate of the vehicle parked in it.
 */
public abstract class ParkingSpot {

    /*
     * License plate number of parked vehicle
     * Requirements: No spaces, must be all caps, 5-7 characters long including all chars and nums. 
     * Empty string if spot is vacant. 
     */
    protected String licensePlate;

    // Number/Id of the parking spot
    protected int parkingSpotId;

    /*
     * distance from the entrance (units are not specified as it is context
     * Cannot be changed as specific units of measurement are not relevant. Also the
     * location of the parking spot to the entrance will not change.
     */
    protected double distance;

    // True of parking spot is occupied, false otherwise.
    protected boolean occupied;

    /*
     * Constructs a new empty parking spot with given id, distance from entrance,
     * and unoccupied status
     */
    public ParkingSpot(int parkingSpotId, double distance) {
        this.parkingSpotId = parkingSpotId;
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

    ///// GETTER METHODS /////

    public double getDistance() { return distance; }
    public int getParkingSpotId() { return parkingSpotId; }
    public boolean getOccupiedStatus() { return occupied; }
    public String getLicensePlate() { return licensePlate; }
}
