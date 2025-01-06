package model;

/*
 * @ Mu Ye Liu, Jan 2025
 * 
Represents a large parking spot that can park buses and trucks. No other implementation because it
has the same features as the "base" parking spot class. Since commerical spots are very limited 
(if it exists at all), only commerical buses and trucks can park in them. 
*/
public class CommericalSpot extends ParkingSpot {

    public CommericalSpot(int id, double distance) {
        super(id, distance);
    }

}
