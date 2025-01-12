package model;

import java.util.LinkedList;

/*
 @ Mu Ye Liu, Jan 2025

Represents the graph for the parking lot, which is used to pre-compute the shortest path from 
entrance to the desired parking spot, so it can be instantly retrieved when the spot is 
requested to be occupied. Uses an adjacency list implementations
 */
public class ParkingLotGraph {

    // Id of graph, should be the same as the name of the parking lot
    private String id; 

    // Stores the list of all vertices. Uses a linked list to ensure efficient insert and removal 
    LinkedList<Vertex> allVertices;

    // Creates a new graph with no vertices, and an set id
    public ParkingLotGraph(String id) {
        this.id = id + " GRAPH";
        allVertices = new LinkedList<>();
    }

    /*
     * MOST IMPORANT METHOD: Adds vertices to the graph
     * Returns the vertex if successfully set, false if not
     */
    public Vertex addVertex(int id) {
        return null;
    }

    public void connectVertex() {
        
    }

    ///// GETTER METHODS /////
    
    public String getId() { return id; }
    public LinkedList<Vertex> getAllVertices() { return allVertices; }

}
