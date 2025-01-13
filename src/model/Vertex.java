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

    /* 
     * Distance from entrance and visited marker (initlialized to inf and false respectively for
     * dijkstra's shortest path algorithm)
    */
    private double distance;
    private boolean visited;

    // Predecessor vertex used to track shortest path
    private Vertex predecessor;

    /*
     * Uses a Hashmap to store adjacent vertices for better runtime for insert and removal, and search
     * The double value stores the distance between vertices, and the Vertex key is the adj vertex.
     * Hashmap also ensures there are no double edges
     */
    private HashMap<Vertex, Double> adjacentVertices;

    // Creates a new vertex with given id, distance set to inf, no predecessor, and empty adj list.
    public Vertex(String id) {
        this.id = id;
        this.distance = Double.POSITIVE_INFINITY;
        this.visited = false;
        this.predecessor = null;
        this.adjacentVertices = new HashMap<>();
    }

    ///// SETTER METHODS /////
    
    public void setId(String id) { this.id = id; }
    public void setDistance(double distance) { this.distance = distance; }
    public void setPredecessor(Vertex predecessor) { this.predecessor = predecessor; }
    public void setVisited(boolean visited) { this.visited = visited; }

    ///// GETTER METHODS /////
    
    public String getId() { return id; };
    public HashMap<Vertex, Double> getAdjacentVertices() { return adjacentVertices; }
    public Double getDistance() { return distance; }
    public Vertex getPredecessor() { return predecessor; }
    public boolean getVisited() { return visited; }

}
