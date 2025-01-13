package exception;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Represents an exception class where the graph is disconnected.
*/
public class GraphDisconnectedException extends Exception {

    public GraphDisconnectedException(String message) {
        super(message);
    }
    
}
