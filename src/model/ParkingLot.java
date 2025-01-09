package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

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
     * Stores all the parking spots (all types). A hashmap is chosen because parking spots are distinct, 
     * they cannot be added twice. moreover, a hashset is chosen because it maximizes the efficiency
     * looking up the parking spots, with the amortized runtime per lookup being O(1)
     * as opposed to O(n) for arrayList.
     */
    private HashMap<Integer, ParkingSpot> parkingSpots;

    private HashSet<String> licensePlates;

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

    
    /* Constructs a new empty parking lot with no parking spots.
     * Initializes hashmap and priority queue. 
    */
    public ParkingLot(String name) {
        this.name = name;
        this.parkingSpots = new HashMap<>();
        this.licensePlates = new HashSet<>();
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
     * Stores it in hashmap and appropriate priority queue. 
     * The integer flag determines which type of parking spot is created. 
     * 0 = car spot
     * 1 = motorcycle spot
     * 2 = commercial spot
     * 
     * Throws DuplicateIdException if duplicate id's are 
     * Returns the new parking spot that was added, null if parking spot not added
     * 
     * REQUIRES: the integer flag must be 0, 1, or 2
    */
    public ParkingSpot addParkingSpot(int id, double distance, int flag) {
        // Return null if parking spot with inputted id already exists.
        if (parkingSpots.get(id) != null) {
            return null;
        }

        switch(flag) {
            case 0:
                CarSpot newCarSpot = new CarSpot(id, distance);
                parkingSpots.put(id, newCarSpot);
                vacantCarSpots.add(newCarSpot);
                return newCarSpot;
            case 1:
                MotorcycleSpot newMotorcycleSpot = new MotorcycleSpot(id, distance);
                parkingSpots.put(id, newMotorcycleSpot);
                vacantMotorcycleSpots.add(newMotorcycleSpot);
                return newMotorcycleSpot;
            default: // Case 2 
                CommericalSpot newCommercialSpot = new CommericalSpot(id, distance);
                parkingSpots.put(id, newCommercialSpot);
                vacantCommericalSpots.add(newCommercialSpot);
                return newCommercialSpot;
        }
    }

    /*
     * Occupies a parking spot corresponding to the vehicle by removing the parking spot from 
     * priority queue, and assigning plate to it
     * The integer flag is the same as the method above 
     * In the case occupying spot for motorcycle, if there are no motorcycle spots left, 
     * attempt to occupy half full car spots, then full car spots, hierarchically.
     * 
     * The input license plate will be formatted to ATTEMPT to meet the conditions
     * 
     * REQUIRES: the integer flag must be 0, 1 or 2
     * Throws exception if license plate doesn't meet reqs, or if plate already exists
     * 
     * Returns the spot that was occupied if success, null if spot is full
     */
    public ParkingSpot occupySpot(String plate, int flag) throws IllegalArgumentException {
        plate = formatPlate(plate); // Potentially throws IllegalArgumentException
        checkDuplicatePlate(plate); // Potentially throws IllegalArgumentException

        switch (flag) {
            case 0:
                CarSpot carSpotToOccupy = vacantCarSpots.poll();
                if (carSpotToOccupy == null) {
                    return null;
                }
                carSpotToOccupy.occupy(plate);
                licensePlates.add(plate);
                return carSpotToOccupy;
            case 1:
                MotorcycleSpot motorcycleSpotToOccupy = vacantMotorcycleSpots.poll();
                if (motorcycleSpotToOccupy == null) {
                    CarSpot carSpotUsedForMotorcycle = occupyCarSpotWithMotorcycle(plate);
                    if (carSpotUsedForMotorcycle == null) {
                        return null;
                    }
                    licensePlates.add(plate);
                    return carSpotUsedForMotorcycle;
                }
                motorcycleSpotToOccupy.occupy(plate); 
                licensePlates.add(plate);
                return motorcycleSpotToOccupy;
            default:
                CommericalSpot commericalSpotToOccupy = vacantCommericalSpots.poll();
                if (commericalSpotToOccupy == null) {
                    return null;
                }
                commericalSpotToOccupy.occupy(plate);
                licensePlates.add(plate);
                return commericalSpotToOccupy;
        }
    }

    /*
     * Manually occupies a spot based on given spot id and license plate by searching hashmap
     * Returns the parking spot that is just occupied, for null if no spot if found and occupied
     * Like the previous function, throws exception if plate does not meet requirements
     * Also, manually remove spot from associated priority queue
     * 
     * Boolean flag: true if you want to occupy car spot with motorcycle, false otherwise
     */
    public ParkingSpot manuallyOccupy(int id, String plate, boolean flag) throws IllegalArgumentException {
        plate = formatPlate(plate); // Potentially throws IllegalArgumentException
        ParkingSpot spotToOccupy = findParkingSpot(id);
        if (spotToOccupy == null) { // Id not found
            return null;
        }

        if (spotToOccupy.getOccupiedStatus()) {
            // Spot is marked as occupied
            if (spotToOccupy instanceof CarSpot carSpot && carSpot.getMotorcycleCount() == 1 && flag) {
                // Wants to occupy half full car spot with motorcycle
                carSpot.occupyWithMotorcycle(plate);
                halfFullCarSpots.remove(carSpot);
            } else {
                // Other, inoperable case
                return null;
            }
        } else {
            // Spot is not marked as occupied
            if (spotToOccupy instanceof CarSpot carSpot) {
                // The spot to occupy is a car spot
                if (flag) {
                    // Occupy empty car spot with motorcycle
                    carSpot.occupyWithMotorcycle(plate);
                    vacantCarSpots.remove(carSpot);
                    halfFullCarSpots.add(carSpot);
                } else {
                    // Occupy with car
                    carSpot.occupy(plate);
                    vacantCarSpots.remove(carSpot);
                }
            } else if (spotToOccupy instanceof MotorcycleSpot) {
                // The spot to occupy is a motorcycle spot
                spotToOccupy.occupy(plate);
                vacantMotorcycleSpots.remove(spotToOccupy);
            } else { 
                // The spot to occupy is commerical spot
                spotToOccupy.occupy(plate);
                vacantCommericalSpots.remove(spotToOccupy);
            }
        }

        licensePlates.add(plate);
        return spotToOccupy;
    }

    /*
     * Unoccupies spot based on given license plate. Then, puts it back into the priority queue
     * Returns unoccupied spot if successful, null if not.
     * 
     */
    public ParkingSpot unoccupySpot(String plate) throws IllegalArgumentException {
        plate = formatPlate(plate);
        // Iterate through set of all parking spot to see if the spot can be found
        for (ParkingSpot spot : parkingSpots.values()) {
            if (spot instanceof CarSpot carSpot) {
                // Case where you unoccupy motorcycle from car
                if (carSpot.getMotorcycleCount() > 0 && (carSpot.getLicensePlate().equals(plate) || 
                    carSpot.getLicensePlate2().equals(plate))) {
                    carSpot.unoccupyMotorcycle(plate);
                    addSpotBackToPriorityQueue(carSpot, true);
                    licensePlates.remove(plate);
                    return carSpot;
                }
            } 
            // Regular case
            if (spot.getLicensePlate().equals(plate)) {
                spot.unoccupy();
                addSpotBackToPriorityQueue(spot, false);
                licensePlates.remove(plate);
                return spot;
            }
        }
        // If no spot found, return null
        return null;
    }

    // Changes the name of the parking lot (if the owner wishes to change it)
    public void changeLotName(String newName) {
        name = newName;
    }

    ///// HELPER METHODS /////
    
    /*
     * Formats the inputted license plate string to get rid of all spaces, special chars, 
     * as well as capitalize all inputted letters. Throws exception if plate is too long or short
    */
    private String formatPlate(String input) throws IllegalArgumentException {
        input = input.replaceAll("[^a-zA-Z0-9]", "").toUpperCase();
        if (input.length() < 5 || input.length() > 7) {
            throw new IllegalArgumentException("Plate is too long or short");
        }
        return input;
    }

    /* 
     * Checks to see whether or not license plate is already in parking lot system.
     * Throws exception if yes
    */
    private void checkDuplicatePlate(String plate) throws IllegalArgumentException {
        if (licensePlates.contains(plate)) {
            throw new IllegalArgumentException("Plate already exists");
        }
    }

    /*
     * Finds and returns the parking spot based on id. Returns null if id doesn't exist.
     * Should have O(1) runtime
    */
    public ParkingSpot findParkingSpot(int spotId) {
        return parkingSpots.get(spotId);
    }

    /*
     * Adds previously occupied spot back into priority queue
     * Flag: false = regular case, true = case where you unoccupy motorcycle spot from car
     * 
     * REQUIRES: Must first unoccupy spot first in caller method before calling this method.
    */
    private void addSpotBackToPriorityQueue(ParkingSpot spot, boolean flag) {
        if (flag) {
            CarSpot carSpot = (CarSpot) spot;
            if (carSpot.getMotorcycleCount() == 1) {
                halfFullCarSpots.add(carSpot);
            } else {
                halfFullCarSpots.remove(carSpot);
                vacantCarSpots.add(carSpot);
            }
        } else {
            if (spot instanceof CarSpot) {
                CarSpot carSpot = (CarSpot) spot;
                vacantCarSpots.add(carSpot);
            } else if (spot instanceof MotorcycleSpot) {
                MotorcycleSpot motorcycleSpot = (MotorcycleSpot) spot;
                vacantMotorcycleSpots.add(motorcycleSpot);
            } else { // Commercial spot
                CommericalSpot commericalSpot = (CommericalSpot) spot;
                vacantCommericalSpots.add(commericalSpot);
            }
        }
    }

    /* 
     * Occupies car spot with motorcycle. Always retrieves furthest half full spot car first.
     * If no half full spots available, get full spots
     * 
     * Returns the spot that was occupied
     */
    private CarSpot occupyCarSpotWithMotorcycle(String plate) {
        CarSpot carSpotHalfOccupied = halfFullCarSpots.poll();
        if (carSpotHalfOccupied == null) {
            CarSpot emptyCarSpot = vacantCarSpots.poll();
            if (emptyCarSpot == null) {
                return null; // Exceptional case where there are no half full or full spots left
            }
            
            emptyCarSpot.occupyWithMotorcycle(plate);
            halfFullCarSpots.add(emptyCarSpot);
            return emptyCarSpot; // Case where there are no half full spots, but there are empty spots
        }

        carSpotHalfOccupied.occupyWithMotorcycle(plate);
        return carSpotHalfOccupied; // Case where there are half full spots
    }

    ///// GETTER METHODS /////
    
    public String getName() { return name;}
    public HashMap<Integer, ParkingSpot> getParkingSpots() { return parkingSpots; }
    public HashSet<String> getLicensePlates() { return licensePlates; }
    public PriorityQueue<CarSpot> getVacantCarSpots() { return vacantCarSpots; }
    public PriorityQueue<CommericalSpot> getVacantCommericalSpots() { return vacantCommericalSpots; }
    public PriorityQueue<MotorcycleSpot> getVacantMotorcycleSpots() { return vacantMotorcycleSpots; }
    public PriorityQueue<CarSpot> getHalfFullCarSpots() { return halfFullCarSpots; }
}
