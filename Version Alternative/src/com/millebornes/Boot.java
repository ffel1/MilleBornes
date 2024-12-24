package com.millebornes;

/**
 * The Boot class represents a specific type of card, which is a boot card in the game.
 * 
 * The Boot card is a subclass of the `Card` class. It is typically used to perform specific 
 * actions related to boots in the game, like enabling the player to move forward or perform special actions.
 * The card has a type associated with it, which determines its behavior and effect in the game.
 * 
 * Responsibilities:
 * - Inherit properties and behavior from the `Card` class.
 * - Provide a specific implementation for boot cards with a type assigned.
 * 
 * This class allows for the creation of a boot card that can be played in the game for special actions.
 * 
 * @see Card
 * @see TypeCard
 */
public class Boot extends Card {

    /**
     * Constructor for the Boot card.
     * 
     * This constructor initializes the Boot card by calling the superclass constructor 
     * and passing the string representation of the card type and the card type itself.
     * 
     * @param t The type of the card (from the TypeCard enum), which defines the card's behavior.
     */
    public Boot(TypeCard t){
        // Call the superclass constructor to initialize the card with its name and type
        super(t.toString(), t);
    }
}