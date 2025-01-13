package model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import exception.GraphDisconnectedException;

/*
 @ Mu Ye Liu, Jan 2025

Represents a non-directional graph for the parking lot, which is used to pre-compute the shortest 
path from entrance to the desired parking spot, using (Dijkstra's shortes path algorithm), so the 
shortest path can be instantly retrieved when the spot is requested

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

    /*
     * Adds vertices to the graph. Due to the nature of the hashset, if vertex is already added,
     * does nothing.
     */
    public void addVertex(Vertex vertex) {
        allVertices.add(vertex);
    }

    /*
     * Connects the 2 vertices and sets the distance
     * If the 2 vertices are already connected, simply change the distance, because there cannot be 
     * duplicate edges. The hashmap ensures it. 
     * 
     * REQUIRES: Distance must be positive (no negative weight edges allowed)
     */
    public void connectVertices(Vertex vertex1, Vertex vertex2, double distance) {
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

    /*
     * MOST IMPORTANT METHOD: COMPUTES THE SHORTEST PATH FROM START VERTEX TO END VERTEX
     * 
     * Returns the Shortest path int the form of a wrapper class that contains the path as well as
     * shortest distance
     * Also throws exception if graph is disconnected
     */
    public ShortestPath findShortestPath(Vertex start, Vertex end) throws GraphDisconnectedException {
        // Before finding shortest path, set all vertex distances to infinity,
        // all unvisited, predecessors to null.
        initialize();
        start.setDistance(0);

        // Initialize priority queue, and only add the start vertex
        PriorityQueue<Vertex> shPriorityQueue = new PriorityQueue<>((v1, v2) ->
            Double.compare(v1.getDistance(), v2.getDistance()));
        shPriorityQueue.add(start);

        // Set the predecessors for the shortest path, then obtain it when the algorithm is done
        setPredecessors(shPriorityQueue);
    
        return getShortestPath(start, end); // Stub
    }

    ///// HELPER METHODS /////
    
    // Iteratively run dijkstra's algorithm to compute the shortest path
    private void setPredecessors(PriorityQueue<Vertex> pq) {
        // Set the current node to visited.
        while (!pq.isEmpty()) {
            Vertex curr = pq.poll();
            curr.setVisited(true);
            for (Map.Entry<Vertex, Double> entry: curr.getAdjacentVertices().entrySet()) {
                Vertex neighbour = entry.getKey();
                double newDistance = curr.getDistance() + entry.getValue();
                if (!neighbour.getVisited() && newDistance < neighbour.getDistance()) {
                    // If current vertex is not visited and the current shortest path distance is shorter
                    // than the vertex's distance, update distance, and set predecessor to curr.
                    neighbour.setDistance(newDistance);
                    neighbour.setPredecessor(curr);
                    pq.add(neighbour);
                }
            }
        }
    }

    /*
     * Gets the shortest path by tracking the predecessors from end to start
     * Throws GraphDisconnectedException if there is no path from end to start (graph disconnected)
     */
    private ShortestPath getShortestPath(Vertex start, Vertex end) throws GraphDisconnectedException {
        ShortestPath sp = new ShortestPath(new LinkedList<>(), end.getDistance());
        Vertex curr = end;

        // Iteratively add the vertices starting from the back
        LinkedList<Vertex> path = sp.getPath();
        while (curr != start) {
            if (curr == null) {
                throw new GraphDisconnectedException("Graph is disconnected");
            }
            path.addFirst(curr);
            curr = curr.getPredecessor();
        }
        // Manually handle the last iteration
        path.addFirst(curr);

        return sp;
    }

    // Initializes the dijkstra's algorithm by setting all distances to 0, and all unvisited
    private void initialize() {
        for (Vertex vertex: allVertices) {
            vertex.setDistance(Double.POSITIVE_INFINITY);
            vertex.setVisited(false);
            vertex.setPredecessor(null);
        }
    }

    // Return true if the 2 vertices are connected, false if not
    private boolean isConnected(Vertex vertex1, Vertex vertex2) {
        return vertex1.getAdjacentVertices().containsKey(vertex2);
    }

    ///// GETTER METHODS /////
    
    public String getId() { return id; }
    public HashSet<Vertex> getAllVertices() { return allVertices; }

}
