package main;
import java.util.ArrayList;

/**
 * The CPUAgro class represents an aggressive CPU-controlled player in the game.
 * 
 * This class extends the abstract `CPU` class and implements a specific strategy for the CPU player.
 * The CPUAgro player prioritizes playing Attack cards first, followed by Safety and Distance cards, making it an aggressive player that focuses on attacking other players.
 * The strategy is designed to prioritize offensive actions over defensive or distance-related actions.
 * 
 * Responsibilities:
 * - Make decisions about which card to play, prioritizing Attack cards first.
 * - Discard cards based on the player's strategy, preferring to discard less useful cards such as Distance or Safety cards.
 * 
 * @see CPU
 * @see Player
 * @see Card
 * @see Controler
 * @see TypeCard
 * @see Game
 */
public class CPUAgro extends CPU {

    /**
     * Constructor to initialize the CPUAgro player with its name, kilometers, id, and the game instance.
     * 
     * @param name The name of the CPUAgro player.
     * @param k The initial kilometers for the CPUAgro player.
     * @param id The unique ID of the CPUAgro player.
     * @param Game The game instance in which the CPUAgro is playing.
     */
    public CPUAgro(String name, int k, int id, Game Game){
        super(name, k, id, Game); // Call the superclass (CPU) constructor to set up the CPUAgro player
    }

    /**
     * Chooses the best card to play based on the CPUAgro player's strategy.
     * 
     * The strategy of CPUAgro is as follows:
     * 1. Play a Boot card if available (to enable another draw).
     * 2. Play an Attack card if possible, targeting the appropriate player.
     * 3. Play a Green Light card if an Attack card has been played, allowing further moves.
     * 4. Play a Safety card to defend against attacks.
     * 5. Play a Distance card if no other card can be played, prioritizing the longest distances.
     * 
     * @return The best card to play based on the CPUAgro player's strategy.
     */
    public Card chooseCard(){
        ArrayList<Card> Hand = getHand();
        Card playedCard = null;
        boolean findAttack = false, findSafety = false, findGreenLight = false;

        // Botte -> Attaque -> Parade -> Distance

        // Iterate through all cards in hand to choose the best card to play
        for(Card card : Hand){
            if(card instanceof Boot){
                playedCard = card; // Priority to play Boot card
                break;
            }
            else if(card instanceof Attack && !findAttack && check(card, this, getTarget(card))){
                playedCard = card; // Play Attack card if valid
                findAttack = true;
            }
            else if (card.getType() == TypeCard.GREEN_LIGHT && !findAttack && check(card, this, null)) {
                playedCard = card; // Play Green Light card if no Attack card has been played yet
                findGreenLight = true;
            }
            else if(card instanceof Safety && !findAttack && !findGreenLight && !findSafety && check(card, this, null)){
                playedCard = card; // Play Safety card if needed, before Distance card
                findSafety = true;
            }
            else if(card instanceof Distance && !findAttack && !findSafety && !findGreenLight && check(card, this, null)){
                // Play Distance card if no other cards can be played, preferring longer distances
                if((playedCard instanceof Distance && card.getKilometers() > playedCard.getKilometers()) || !(playedCard instanceof Distance))
                {
                    playedCard = card;
                }
            }
        }

        return playedCard; // Return the chosen card
    }

    /**
     * Chooses a card to discard based on the CPUAgro player's strategy.
     * 
     * The strategy for discarding is as follows:
     * 1. Discard Distance cards first, preferring to discard those with lower kilometers.
     * 2. Discard Safety cards if a Boot card or an Attack card is available.
     * 3. If a Safety card or Distance card isn't useful, discard it in favor of a more useful card.
     * 
     * @return The card chosen to be discarded.
     */
    @Override
    public Card discardChoice(){
        ArrayList<Card> Hand = getHand();
        Card cardToDiscard = Hand.get(0); // Default to the first card in hand for discarding

        // Botte -> Attaque -> Parade -> Distance

        // Iterate through all cards in hand to choose the card to discard
        for(Card card : Hand){
            if(card instanceof Distance){
                // Discard the Distance card with the lowest kilometers if necessary
                if(!(cardToDiscard instanceof Distance)){
                    cardToDiscard = card;
                }
                else if(cardToDiscard instanceof Distance && cardToDiscard.getKilometers() > card.getKilometers()){
                    cardToDiscard = card;
                }
            }
            else if(card instanceof Safety){
                // Discard Safety cards if more useful cards like Attack or Boot are available
                if(cardToDiscard instanceof Attack || cardToDiscard instanceof Boot){
                    cardToDiscard = card;
                }
            }
            else if(card instanceof Attack){
                // Discard Attack cards if Boot card is present in hand
                if(cardToDiscard instanceof Boot){
                    cardToDiscard = card;
                }
            }
        }

        // Log the discarded card for debugging purposes
        System.out.println(getName() + " décide de jeter la carte " + cardToDiscard.getName());
        return cardToDiscard; // Return the discarded card
    }
}