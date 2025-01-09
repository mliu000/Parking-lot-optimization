package model;

import java.util.LinkedList;

/*
 @ Mu Ye Liu, Jan 2025

This class represents a vertex, in our adjacency list version of graph.
 */
public abstract class Vertex {

    private final int id;

    // Uses linked list for better runtime
    private LinkedList<Vertex> adjacentVertices;

    // Creates a new vertex
    public Vertex(int id) {
        this.id = id;
        this.adjacentVertices = new LinkedList<>();
    }

}
