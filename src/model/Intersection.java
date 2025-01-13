package model;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Represents an intersection in the parking lot, which is used for implementing Dijkstra's
shortest path to find the shortest path between the entrance to the parking spot. 
*/
public class Intersection extends Vertex {
    
    // Name and unique id of this intersection
    private String name;
    private int intersectionId;

    // Constructs a new intersections
    public Intersection(int intersectionId, String name) {
        super("I_" + Integer.toString(intersectionId)); // The 
        this.intersectionId = intersectionId;
        this.name = name;
    }

    ///// GETTER METHODS /////
    
    public String getName() { return name; }
    public int getIntersectionId() { return intersectionId; }
}
