package utility;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
A custom defined templated pair class that is primarily used to store the adjacent vertices, along
with their distance.
*/
public class Pair<K, V> {

    private K first;
    private V second;

    // Constructs a new pair with K first an V second
    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    ///// SETTER METHODS /////
    
    public void setFirst(K first) { this.first = first; }
    public void setSecond(V second) { this.second = second; }

    ///// GETTER METHODS /////
    
    public K getFirst() { return first; }
    public V getSecond() { return second; }
    
}
