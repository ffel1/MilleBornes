package main;

// This class represents Distance
public class Distance extends Card{
// Private or protected member
    private int Kilometers;

// This method handles the logic for Distance
    public Distance(TypeCard t, int k){
        super("Distance"+t.toString(), t);
        Kilometers = k;
    }

// This method handles the logic for getKilometers
    public int getKilometers(){
        return Kilometers;
    }
}