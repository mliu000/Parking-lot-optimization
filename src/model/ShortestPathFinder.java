package model;

/*
 @ Mu Ye Liu, Jan 2025

The class the computes the shortest path from entrance to parking spot using the Dijkstra's shortest
algorithm. Then, stores the path in each parking stall. Uses the singleton design pattern we only 
need on instance of this class
 */
public class ShortestPathFinder {

    private static ShortestPathFinder instance;

    private ShortestPathFinder() {}

    // Method to retrieve the instance of the path finder
    public static ShortestPathFinder getPathFinder() {
        if (instance == null) {
            instance = new ShortestPathFinder();
        }
        return instance;
    }

    // find the shortest path given start and end vertices.
    public ShortestPath findShortestPath() {
        return null; // stub
    }
}
