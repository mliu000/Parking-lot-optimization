package model;
import exception.PlateNotFoundException;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
 The car spot class extends the parking spot class, with an added feature that 2 motorcycles can 
 park in a car spot if no motorcycle spots remain, or don't exist. 
 */
public class CarSpot extends ParkingSpot {

    // Integer that indicates number of motorcycles parked in a car spot (0, 1, or 2)
    private int motorcycleCount;

    // Only used when motorcycles use the car spot
    private String licensePlate2;


    // Constructs a Car parking spot
    public CarSpot(int id, int distance) {
        super(id, distance);
        this.motorcycleCount = 0;
        this.licensePlate2 = "";
    }

    // Sets the motorcycle occupation status. If the stall is occupied by one motorcycle, the
    // boolean status for cars (occupied) will be true, while motorcycle count will be 1. 
    // Input true for status if motorcycle occupies, false for leave.
    public void setMotorcycleOccupied(boolean status, String plate) throws PlateNotFoundException {
        // Changes the motorcycle count and status of the stall
        // Shouldn't happen if implemented properly, but to safeguard the motorcycleCount from
        // holding an illegal integer
        if (status && (motorcycleCount == 0 || motorcycleCount == 1)) {
            // Occupy case
            occupied = status;
            motorcycleCount++;
            if (motorcycleCount == 0) {
                // Case where spot is empty
                licensePlate = plate;
            } else {
                // Case where one motorcycle is parked, but we need to determine side of stall.
                if (licensePlate.equals("")) {
                    licensePlate = plate;
                } else {
                    licensePlate2 = plate;
                }
            }
        } else if (!status && (motorcycleCount == 1 || motorcycleCount == 2)) {
            // Leave case
            if (licensePlate.equals(plate)) {
                licensePlate = "";
            } else if (licensePlate2.equals(plate)) {
                licensePlate2 = "";
            } else {
                throw new PlateNotFoundException();
            }

            motorcycleCount--;
            // If there are 2 motorcycles parked, but one leaves, we do not change the status
            if (motorcycleCount == 1) {
                occupied = status;
            }
        }
    }

    ///// GETTER METHODS /////
    
    public int getMotocycleCount() {
        return motorcycleCount;
    }

    public String getLicensePlate2() {
        return licensePlate2;
    }
}
