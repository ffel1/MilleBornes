package com.millebornes;

/**
 * The Attack class represents a specific type of card, which is an attack card in the game.
 * 
 * The Attack card is a subclass of the `Card` class. It is used to perform attack actions in the game, 
 * typically targeting another player (e.g., a CPU). The card has a type associated with it, 
 * which determines its specific behavior and effect in the game.
 * 
 * Responsibilities:
 * - Inherit properties and behavior from the `Card` class.
 * - Provide a specific implementation for attack cards with a type assigned.
 * 
 * This class allows for the creation of an attack card that can be played during the game to initiate attacks on other players.
 * 
 * @see Card
 * @see TypeCard
 */
public class Attack extends Card {
    
    /**
     * Constructor for the Attack card.
     * 
     * This constructor initializes the Attack card by calling the superclass constructor 
     * and passing the string representation of the card type and the card type itself.
     * 
     * @param t The type of the card (from the TypeCard enum), which defines the card's behavior.
     */
    public Attack(TypeCard t){
        // Call the superclass constructor to initialize the card with its name and type
        super(t.toString(), t);
    }
}