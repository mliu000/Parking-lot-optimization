package model;

import java.util.LinkedList;

import utility.Pair;

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
     * Uses a linked list to store adjacent vertices to ensure better runtime for insert and removal
     * The integer in the pair stores the distance between vertices, and the 
     */
    private LinkedList<Pair<Integer, Vertex>> adjacentVertices;

    // Creates a new vertex with given id, distance set to inf, no predecessor, and empty adj list.
    public Vertex(String id) {
        this.id = id;
        this.distance = Double.POSITIVE_INFINITY;
        this.predecessor = null;
        this.adjacentVertices = new LinkedList<>();
    }

    ///// GETTER METHODS /////
    
    public String getId() { return id; };
    public LinkedList<Pair<Integer, Vertex>> getAdjacentVertices() { return adjacentVertices; }
    public Double getDistance() { return distance; }
    public Vertex getPredecessor() { return predecessor; }
    

}
