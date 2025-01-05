package model;

import exception.PlateNotFoundException;

/*
 @ Mu Ye Liu, Jan 2025

 The parking spot class, as well as its inheritors, are essentially the nodes of the disjoint sets.
 Once a parking spot is occupied, it becomes rooted to the next closest spot. If the parked vehicle
 leaves the spot, the disjoint set
 */
public abstract class ParkingSpot {

    // License plate number of parked vehicle, omitting spaces and all upper case letters.
    // Empty string if spot is vacant.
    protected String licensePlate;

    // Number/Id of the parking spot
    protected int id;
    
    // Distance from the entrance (units are not specified as it is context dependent)
    protected int distance;

    // True of parking spot is occupied, false otherwise. 
    protected boolean occupied;

    // Constructs a new empty parking spot with given id, distance from entrance, and unoccupied 
    // Status
    public ParkingSpot(int id, int distance) {
        this.id = id;
        this.distance = distance;
        this.licensePlate = "";
        this.occupied = false;
    }

    // Sets the state of the parking spot (true = occupied, false = unoccupied), when a vehicle gets
    // Assigned the spot.
    // Input true and license plate number to occupy, false and license plate number to unoccupy.
    // Throws exception if the license plate is not found. 
    public void setOccupiedStatus(boolean status, String plate) throws PlateNotFoundException {
        if (status) {
            // Case to occupy spot
            licensePlate = plate;
        } else {
            // Case to unoccupy spot
            if (licensePlate.equals(plate)) {
                licensePlate = "";
            } else {
                throw new PlateNotFoundException();
            }
        }

        // If changing the license plate succeeds, finish off by setting the status of the spot
        occupied = status;
        
    }

    // If the parking lot undergoes a id overhaul, the id can be changed with that method
    public void changeId(int newId) {
        id = newId;
    }

    ///// GETTER METHODS /////
    
    public int getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public boolean getOccupationStatus() {
        return occupied;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
