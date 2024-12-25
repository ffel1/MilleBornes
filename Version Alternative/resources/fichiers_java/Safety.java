package com.millebornes;

/**
 * The Safety class represents a specific type of card in the game that is categorized as a Safety card.
 * 
 * This class extends the Card class and inherits its attributes and methods. It specifically handles cards 
 * that fall under the "Safety" category, as identified by the card's type from the TypeCard enum.
 * 
 * Responsibilities:
 * - Inherit properties from the Card class such as name, type, image, and kilometers.
 * - Provide specific functionality for Safety cards, which could include specific game logic in the future.
 * 
 * @see Card
 * @see TypeCard
 * @see Attack
 * @see Boot
 * @see Safety
 */
public class Safety extends Card {
    
    /**
     * Constructor to initialize a Safety card with the given type.
     * 
     * This constructor passes the card type and its corresponding string representation to the parent class
     * (Card) constructor. It initializes the Safety card with the appropriate name and image based on the 
     * provided type from the TypeCard enum.
     * 
     * @param t The type of the card (from the TypeCard enum).
     */
    public Safety(TypeCard t) {
        // Call the superclass constructor to initialize the card with its name and type
        super(t.toString(), t);
    }
}