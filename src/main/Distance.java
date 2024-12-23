package main;

/**
 * The Distance class represents a specific type of card in the game that deals with the distance.
 * 
 * This class extends the Card class and adds a specific functionality for Distance cards. A Distance card
 * is associated with a specific number of kilometers, which is tracked through the Kilometers attribute.
 * 
 * Responsibilities:
 * - Initialize a Distance card with a name, type, and a specific number of kilometers.
 * - Provide a method to retrieve the number of kilometers associated with the card.
 * 
 * @see Card
 * @see TypeCard
 * @author Your Name
 * @version 1.0
 */
public class Distance extends Card {
    
    // Private member to store the number of kilometers associated with the Distance card
    private int Kilometers;
    
    /**
     * Constructor to initialize a Distance card with the given type and kilometers.
     * 
     * This constructor initializes the Distance card with a name (combination of "Distance" and the 
     * string representation of the type), a type (from the TypeCard enum), and the specified number 
     * of kilometers.
     * 
     * @param t The type of the card (from the TypeCard enum).
     * @param k The number of kilometers associated with the card.
     */
    public Distance(TypeCard t, int k) {
        super("Distance" + t.toString(), t); // Call the constructor of the superclass (Card) to set name and type
        Kilometers = k; // Set the number of kilometers associated with the card
    }
    
    /**
     * This method returns the number of kilometers associated with this Distance card.
     * 
     * @return The number of kilometers associated with this card.
     */
    public int getKilometers() {
        return Kilometers; // Return the number of kilometers
    }
}