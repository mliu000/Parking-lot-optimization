package model;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Represents a small parking that can only park motorcycles. No other implementation because it
has the same features as its superclass: ParkingSpot 
*/
public class MotorcycleSpot extends ParkingSpot {

    public MotorcycleSpot(int id, double distance) {
        super(id, distance);
    }
}
