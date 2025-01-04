package model;

public class DisjointSets {

    private static DisjointSets instance;
    public int a;

    private DisjointSets() {
        a = 2;
    }

    public static DisjointSets getInstance() {
        if (instance == null) {
            instance = new DisjointSets();
        }
        return instance;
    }
}
