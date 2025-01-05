package model;

/*
 @ Mu Ye Liu, Jan 2025

 The parking spot class, as well as its inheritors, are essentially the nodes of the disjoint sets.
 Once a parking spot is occupied, it becomes rooted to the next closest spot. If the parked vehicle
 leaves the spot, the disjoint set
 */
public abstract class ParkingSpot {

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
        this.occupied = false;
    }

    // Sets the state of the parking spot (true = occupied, false = unoccupied)
    public void setOccupiedStatus(boolean status) {
        occupied = status;
    }

    // If the parking lot undergoes a id overhaul, the id can be changed with that method
    public void changeId(int newId) {
        id = newId;
    }

    ///// GETTER FUNCTIONS /////
    
    public int getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public boolean getOccupationStatus() {
        return occupied;
    }
}
