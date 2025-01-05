package model;


/*
 * @ Mu Ye Liu, Jan 2025
 * 
 The car spot class extends the parking spot class, with an added feature that 2 motorcycles can 
 park in a car spot if no motorcycle spots remain, or don't exist. 
 */
public class CarSpot extends ParkingSpot {

    // Integer that indicates number of motorcycles parked in a car spot (0, 1, or 2)
    private int motorcycleCount;


    // Constructs a Car parking spot
    public CarSpot(int id, int distance) {
        super(id, distance);
        this.motorcycleCount = 0;
    }

    // Sets the motorcycle occupation status. If the stall is occupied by one motorcycle, the
    // boolean status for cars (occupied) will be true, while motorcycle count will be 1. 
    // Input true if 
    public void setMotorcycleOccupied(boolean status) {
        // Set the status of the car stall
        occupied = status;

        // Shouldn't happen if implemented properly, but to safeguard the motorcycleCount from
        // holding an illegal integer
        if (status && (motorcycleCount == 0 || motorcycleCount == 1)) {
            motorcycleCount++;
        } else if (!status && (motorcycleCount == 1 || motorcycleCount == 2)) {
            motorcycleCount--;
        }
    }
}
