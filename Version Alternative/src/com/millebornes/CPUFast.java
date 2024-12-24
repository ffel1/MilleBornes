package com.millebornes;

import java.util.ArrayList;

/**
 * The CPUFast class represents a fast CPU-controlled player in the game.
 * 
 * This class extends the abstract `CPU` class and implements a specific strategy for the CPU player.
 * The CPUFast player prioritizes playing distance cards, and it chooses its actions based on certain game rules, such as playing a Boot card first, then a Safety card, followed by Distance cards, and finally an Attack card if no other option is available.
 * 
 * Responsibilities:
 * - Make decisions about which card to play, prioritizing Distance cards, Boot cards, and Safety cards.
 * - Discard cards based on the player's strategy, with a preference for discarding Attack cards and less useful cards like Safety or Distance cards.
 * 
 * @see CPU
 * @see Player
 * @see Card
 * @see Controler
 * @see TypeCard
 * @see Game
 */
public class CPUFast extends CPU {

    /**
     * Constructor to initialize the CPUFast player with its name, kilometers, id, and the game instance.
     * 
     * @param name The name of the CPUFast player.
     * @param k The initial kilometers for the CPUFast player.
     * @param id The unique ID of the CPUFast player.
     * @param Game The game instance in which the CPUFast is playing.
     */
    public CPUFast(String name, int k, int id, Game Game) {
        super(name, k, id, Game); // Call the superclass (CPU) constructor to set up the CPUFast player
    }

    /**
     * Chooses the best card to play based on the CPUFast player's strategy.
     * 
     * The strategy of CPUFast is as follows:
     * 1. Play a Boot card if available (to enable another draw).
     * 2. Play a Green Light card if possible.
     * 3. Play a Safety card if Green Light is not played yet.
     * 4. Play a Distance card if no Safety or Green Light is chosen, preferring higher distance.
     * 5. Play an Attack card if no other card can be played.
     * 
     * @return The best card to play based on the CPUFast player's strategy.
     */
    public Card chooseCard() {
        ArrayList<Card> Hand = getHand();
        Card playedCard = null;
        boolean EnddDistance = false, findSafety = false, findGreenLight = false;

        // Botte -> Attaque -> Parade -> Distance

        // Iterate through all cards in hand to choose the best card to play
        for (Card card : Hand) {
            if (card instanceof Boot) {
                playedCard = card; // Priority to play Boot card
                break;
            }
            else if (card.getType() == TypeCard.GREEN_LIGHT && check(card, this, null)) {
                playedCard = card; // Play Green Light card if valid
                findGreenLight = true;
            }
            else if (card instanceof Safety && !findGreenLight && !findSafety && check(card, this, null)) {
                playedCard = card; // Play Safety card if no Green Light has been played yet
                findSafety = true;
            }
            else if (card instanceof Distance && !findSafety && !findGreenLight && check(card, this, null)) {
                // Play Distance card, preferring higher kilometers if multiple Distance cards are available
                if ((playedCard instanceof Distance && card.getKilometers() > playedCard.getKilometers()) || !(playedCard instanceof Distance)) {
                    playedCard = card;
                    EnddDistance = true;
                }
            }
            else if (card instanceof Attack && !EnddDistance && !findGreenLight && check(card, this, getTarget(card))) {
                playedCard = card; // Play Attack card if no other card can be played
            }
        }

        return playedCard; // Return the chosen card
    }

    /**
     * Chooses a card to discard based on the CPUFast player's strategy.
     * 
     * The strategy for discarding is as follows:
     * 1. Discard Attack cards first.
     * 2. If no Attack cards are available, discard Safety cards or Distance cards, depending on the current hand.
     * 3. If the hand contains no useful cards, discard the least useful card.
     * 
     * @return The card chosen to be discarded.
     */
    @Override
    public Card discardChoice() {
        ArrayList<Card> Hand = getHand();
        Card cardToDiscard = Hand.get(0); // Default to the first card in hand for discarding

        // Botte -> Distance -> Parade -> Attaque

        // Iterate through all cards in hand to choose the card to discard
        for (Card card : Hand) {
            if (card instanceof Attack) {
                cardToDiscard = card; // Discard Attack cards first
            }
            else if (card instanceof Safety) {
                if (cardToDiscard instanceof Distance || cardToDiscard instanceof Boot) {
                    cardToDiscard = card; // Discard Safety cards if they are less useful than Distance or Boot cards
                }
            }
            else if (card instanceof Distance) {
                // Discard the Distance card with the lowest kilometers if necessary
                if (cardToDiscard instanceof Distance && cardToDiscard.getKilometers() > card.getKilometers()) {
                    cardToDiscard = card;
                }
            }
        }

        // Log the discarded card for debugging purposes
        System.out.println(getName() + " décide de jeter la carte " + cardToDiscard.getName());
        return cardToDiscard; // Return the discarded card
    }
}