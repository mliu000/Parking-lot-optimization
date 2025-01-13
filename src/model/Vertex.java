package model;

import java.util.HashMap;
/*
 @ Mu Ye Liu, Jan 2025

This class represents a vertex, in our adjacency list version of graph. It stores an id, as well as 
a list of adjacent vertices with distance 
 */
public abstract class Vertex {

    // Unique identifier for the vertex. "I_id" for intersection, "P_id" for parking spot
    private String id;

    // Distance from entrance (initialized to infinity for dijkstra's algorithm) 
    private double distance;

    // Predecessor vertex used to track shortest path
    private Vertex predecessor;

    /*
     * Uses a Hashmap to store adjacent vertices for better runtime for insert and removal, and search
     * The integer value stores the distance between vertices, and the Vertex key is the adj vertex.
     * Hashmap also ensures there are no double edges
     */
    private HashMap<Vertex, Integer> adjacentVertices;

    // Creates a new vertex with given id, distance set to inf, no predecessor, and empty adj list.
    public Vertex(String id) {
        this.id = id;
        this.distance = Double.POSITIVE_INFINITY;
        this.predecessor = null;
        this.adjacentVertices = new HashMap<>();
    }

    ///// GETTER METHODS /////
    
    public String getId() { return id; };
    public HashMap<Vertex, Integer> getAdjacentVertices() { return adjacentVertices; }
    public Double getDistance() { return distance; }
    public Vertex getPredecessor() { return predecessor; }
    

}
