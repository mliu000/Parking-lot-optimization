package model;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
 The car spot class extends the parking spot class, with an added feature that 2 motorcycles can 
 park in a car spot if no motorcycle spots remain, or don't exist. 
 */
public class CarSpot extends ParkingSpot {

    // Integer that indicates number of motorcycles parked in a car spot (0, 1, or
    // 2)
    private int motorcycleCount;

    // Only used when motorcycles use the car spot. Basically splitting the car
    // stall in half. Has the same requirements as licensePlate in ParkingSpot super class
    private String licensePlate2;

    // Constructs an unoccupied car spot.
    public CarSpot(int id, double distance) {
        super(id, distance);
        this.motorcycleCount = 0;
        this.licensePlate2 = "";
    }

    /*
     * The following 2 methods are to occupy or unoccupy motorcycles. To occupy spot
     * with car,
     * use the functions in the ParkingSpot super class.
     */

    // Occupies the car spot with motorcycle
    // REQUIRES: the spot is not fully occupied. The motorcycleCount must be 0 or 1, plate
    //           must meet requirements.
    public void occupyWithMotorcycle(String plate) {
        // Set occupied status to true
        occupied = true;
        if (motorcycleCount == 0) {
            // Empty stall case (motorcycleCount == 0)
            licensePlate = plate;
        } else {
            // Case where one motorcycle is already parked in it (motorcycleCount == 1)
            if (licensePlate.equals("")) {
                // Another motorcycle parked in second part of stall
                licensePlate = plate;
            } else {
                // Another motorcycle parked in first part of stall
                licensePlate2 = plate;
            }
        }

        // Increment the motorcycleCount
        motorcycleCount++;
    }

    // Unoccupies the car spot that a motorcycle parked in
    // REQUIRES: the spot must not be empty (count = 1 or 2), the plate parameter
    // must match one of: licensePlate, licensePlate2
    public void unoccupyMotorcycle(String plate) {
        // Car stall is half full (has only 1 motorcycle), the occupied status becomes
        // false. The stall is free to then be occupied by both cars and motorcycles
        if (motorcycleCount == 1) {
            occupied = false;
        }

        // Check which part of stall the motorcycle to remove is in
        if (licensePlate.equals(plate)) {
            licensePlate = "";
        } else {
            licensePlate2 = "";
        }

        // Finally decrement motorcycleCount
        motorcycleCount--;
    }

    ///// GETTER METHODS /////

    public int getMotorcycleCount() {
        return motorcycleCount;
    }

    public String getLicensePlate2() {
        return licensePlate2;
    }
}
