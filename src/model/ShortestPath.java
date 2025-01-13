package model;

import java.util.LinkedList;

/*
 @ Mu Ye Liu, Jan 2025

Wrapper clas that computes stores the shortest path, as well as its distance.
 */
public class ShortestPath {

    // The shortest path sequence. Uses a linked list to ensure efficient insertion at front of list.
    private LinkedList<Vertex> path;

    // The distance of the shortest path
    private double distance;

    // Constructs a shortest path by taking in path and distance parameters.
    public ShortestPath(LinkedList<Vertex> path, double distance) {
        this.path = path;
        this.distance = distance;
    }

    ///// GETTER METHODS /////
    
    public LinkedList<Vertex> getPath() { return path; }
    public double getDistance() { return distance; }
}
