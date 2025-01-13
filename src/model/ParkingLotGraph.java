package model;

import java.util.HashSet;

/*
 @ Mu Ye Liu, Jan 2025

Represents a non-directional graph for the parking lot, which is used to pre-compute the shortest path from 
entrance to the desired parking spot, so it can be instantly retrieved when the spot is 
requested to be occupied. Uses an adjacency list implementations
 */
public class ParkingLotGraph {

    // Id of graph, should be the same as the name of the parking lot
    private String id; 

    /*
     * Stores the list of all vertices. Uses a hashset to ensure efficient insert and removal, and
     * no duplicates
     */
    HashSet<Vertex> allVertices;

    // Creates a new graph with no vertices, and an set id
    public ParkingLotGraph(String id) {
        this.id = id + " GRAPH";
        allVertices = new HashSet<>();
    }

    // MOST IMPORANT METHOD: Adds vertices to the graph
    public void addVertex(Vertex vertex) {
        allVertices.add(vertex);
    }

    /*
     * Connects the 2 vertices and sets the distance
     * If the 2 vertices are already connected, simply change the distance, because there cannot be 
     * duplicate edges. The hashmap ensures it. 
     */
    public void connectVertices(Vertex vertex1, Vertex vertex2, int distance) {
        vertex1.getAdjacentVertices().put(vertex2, distance);
        vertex2.getAdjacentVertices().put(vertex1, distance);
    }

    /*
     * Tries to disconnect edges. If they are already disconnected, do nothing, 
     * if connected, disconnect.
     */
    public void disconnectVertices(Vertex vertex1, Vertex vertex2) {
        if (isConnected(vertex1, vertex2)) {
            vertex1.getAdjacentVertices().remove(vertex2);
            vertex2.getAdjacentVertices().remove(vertex1);
        }
    }

    ///// HELPER METHODS /////

    private boolean isConnected(Vertex vertex1, Vertex vertex2) {
        return vertex1.getAdjacentVertices().containsKey(vertex2);
    }

    ///// GETTER METHODS /////
    
    public String getId() { return id; }
    public HashSet<Vertex> getAllVertices() { return allVertices; }

}
