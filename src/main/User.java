package main;

import java.util.ArrayList;

/**
 * This class represents a User player, inheriting from the Player class, 
 * and adds specific functionalities for the user to interact with the game,
 * including drawing cards, playing cards, and discarding cards.
 */
public class User extends Player {

    // Boolean flags for various player actions and states
    private boolean myTurn = false, hasDraw = false, hasPlayed = false, discard = false, hasDiscard = false, mustDraw = false, isAttacking = false;
    // The card that the player is using for attack
    private Card isAttackingWith;
    // The target CPU player being attacked
    private CPU target;

    /**
     * Constructor for the User class.
     * Initializes the User player with a name, kilometers, ID, and the game reference.
     * 
     * @param name - The name of the player.
     * @param k - The number of kilometers the player has traveled.
     * @param id - The unique ID of the player.
     * @param Game - The current game instance.
     */
    public User(String name, int k, int id, Game Game){
        super(name, k, id, Game);
    }

    /**
     * Gets the target player for attack.
     * 
     * @param c - The card being used for the attack.
     * @return The target player being attacked.
     */
    @Override
    public Player getTarget(Card c) {
        return target;
    }

    /**
     * Gets the card the user is using to attack.
     * 
     * @return The attacking card.
     */
    public Card getIsAttackingWith() {
        return isAttackingWith;
    }

    /**
     * Sets the target CPU player for the user to attack.
     * 
     * @param c - The CPU player to be set as the target.
     */
    public void setTarget(CPU c) {
        target = c;
    }

    /**
     * Sets the card the user is using to attack.
     * 
     * @param c - The attacking card.
     */
    public void setIsAttackingWith(Card c) {
        isAttackingWith = c;
    }

    /**
     * Sets the current turn status of the user.
     * 
     * @param myTurn - Boolean indicating if it's the user's turn.
     */
    public void myTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    /**
     * Gets the flag indicating if the user must draw a card.
     * 
     * @return True if the user must draw, false otherwise.
     */
    public boolean getMustDraw() {
        return mustDraw;
    }

    /**
     * Gets the flag indicating if the user has discarded a card.
     * 
     * @return True if the user has discarded, false otherwise.
     */
    public boolean gethasDiscard() {
        return hasDiscard;
    }

    /**
     * Sets whether the user has discarded a card.
     * 
     * @param b - Boolean indicating if the user has discarded a card.
     */
    public void setHasDiscard(boolean b) {
        hasDiscard = b;
    }

    /**
     * Gets the flag indicating if the user is currently attacking.
     * 
     * @return True if the user is attacking, false otherwise.
     */
    public boolean getIsAttacking() {
        return isAttacking;
    }

    /**
     * Sets whether the user is attacking.
     * 
     * @param b - Boolean indicating if the user is attacking.
     */
    public void setIsAttacking(boolean b) {
        isAttacking = b;
    }

    /**
     * Sets whether the user must draw a card.
     * 
     * @param b - Boolean indicating if the user must draw a card.
     */
    public void setmustDraw(boolean b) {
        mustDraw = b;
    }

    /**
     * Gets the discard status.
     * 
     * @return True if the user is discarding a card, false otherwise.
     */
    public boolean getdiscard() {
        return discard;
    }

    /**
     * Sets the discard status.
     * 
     * @param b - Boolean indicating whether the user is discarding a card.
     */
    public void setdiscard(boolean b) {
        discard = b;
    }

    /**
     * Gets the flag indicating whether the user has played a card.
     * 
     * @return True if the user has played a card, false otherwise.
     */
    public boolean getHasPlayed() {
        return hasPlayed;
    }

    /**
     * Sets whether the user has played a card.
     * 
     * @param b - Boolean indicating if the user has played a card.
     */
    public void sethasPlayed(boolean b) {
        hasPlayed = b;
    }

    /**
     * Draws a card from the draw pile and adds it to the user's hand.
     * The user's hand will not exceed its capacity.
     */
    public void draw() {
        ArrayList<Card> draw = getGame().getdraw();
        if (!HandFull()) {
            hasDraw = true;
            Card c = draw.get(0);
            getHand().add(c);
            draw.remove(c);
        } else {
            System.out.println("Hand Full");
        }
    }

    /**
     * Gets the flag indicating whether it is the user's turn.
     * 
     * @return True if it is the user's turn, false otherwise.
     */
    public boolean getmyTurn() {
        return myTurn;
    }

    /**
     * Gets the flag indicating whether the user has drawn a card.
     * 
     * @return True if the user has drawn a card, false otherwise.
     */
    public boolean getHasDraw() {
        return hasDraw;
    }

    /**
     * Sets whether the user has drawn a card.
     * 
     * @param b - Boolean indicating if the user has drawn a card.
     */
    public void setHasDraw(boolean b) {
        hasDraw = b;
    }

    /**
     * Plays a card based on its type and the game controller.
     * 
     * @param c - The card to be played.
     * @param Controler - The game controller instance.
     * @param nbCard - The card number being played.
     * @return A string message confirming the action.
     */
    public String playCard(Card c, Controler Controler, int nbCard) {
        if (!(c instanceof Boot)) {
            hasPlayed = true; 
        }
        if (c instanceof Attack) {
            Controler.getVue().addMessage("Vous avez attaqué le CPU " + getTarget(c).getName() + " avec " + c.getName() + "! \n", true);
        } else {
            Controler.getVue().addMessage("Vous avez joué la card " + nbCard + " (" + c.getName() + ") \n", true); 
        }
        getHand().remove(c);
        Controler.getVue().effacerCardsPlayers();
        Controler.getVue().printCardsPlayer(getHand());
        Controler.initButtonCards(getHand());
        Controler.getVue().getWindow().repaint();
        Controler.getVue().getWindow().revalidate();
        if (c instanceof Attack) {
            Player target = getTarget(c);
            return playAttack(c, target);
        } else if (c instanceof Safety) {
            return playSafety(c);
        } else if (c instanceof Boot) {
            return playBoot(c);
        } else if (c instanceof Distance) {
            return playDistance(c);
        }   
        return null;
    }

    /**
     * Verifies if a card is playable by the user and returns a corresponding reason for its validity.
     * Each integer returned corresponds to a specific reason for invalidity:
     * - 0: Valid card.
     * - 1: The target has a card that negates the effect of the played card.
     * - 2: The target already has the same card in play or is otherwise protected.
     * - 3: The player is not allowed to play the card due to the "green light" status.
     * - 4: The player cannot play the distance card due to other attacks from the opponent.
     * - 5: The player has already used their "green light" or the attack is blocked by one.
     * - 6: The player cannot perform the safety action due to an opposing attack.
     * - 7: The target is immune to the RED_LIGHT attack.
     *
     * @param c The card being played by the user.
     * @param u The player who is playing the card.
     * @param target The target player of the card action.
     * @return int The reason code for the card's validity.
     */
    public int checkUser(Card c, Player u, Player target) {
        if (c instanceof Attack) {
            // Check for RED_LIGHT and greenLight status
            if (c.getType() == TypeCard.RED_LIGHT && !target.getgreenLight()) {
                return 7; // Target is immune to RED_LIGHT attack
            }
            
            // Check if the target has boots that negate attacks
            for (Card card : target.getPlayedBoots()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.PUNCTURE_PROOF) return 1; // Blocked by Puncture Proof
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.EXPERT_DRIVER) return 1; // Blocked by Expert Driver
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.TANK_TRUCK) return 1; // Blocked by Tank Truck
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return 1; // Blocked by Priority Vehicle
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return 1; // Blocked by Priority Vehicle
                        break;
                    default:
                        break;
                }
            }

            // Check if the target has any current attacks that could negate the attack
            for (Card card : target.getCurrentAttacks()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.FLAT_TIRE) return 2; // Already have a Flat Tire attack
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.ACCIDENT) return 2; // Already have an Accident attack
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.OUT_OF_FUEL) return 2; // Already have an Out of Fuel attack
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.SPEED_LIMITATION) return 2; // Already have a Speed Limitation attack
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.RED_LIGHT) return 2; // Already have a Red Light attack
                        break;
                    default:
                        break;
                }
            }

        } else if (c instanceof Distance) {
            // Check if the player has a green light to move
            if (!getgreenLight()) {
                return 3; // Green light is required to play distance card
            }

            // Check if the total distance exceeds 700 km
            if (c.getKilometers() + u.getKilometers() > 700) {
                return 10; // Too far to travel, max distance exceeded
            }

            // Check for opposing attacks that could block the distance card
            for (Card card : u.getCurrentAttacks()) {
                switch (c.getType()) {
                    case _25KM:
                    case _50KM:
                        if (card.getType() == TypeCard.RED_LIGHT || card.getType() == TypeCard.OUT_OF_FUEL ||
                            card.getType() == TypeCard.FLAT_TIRE || card.getType() == TypeCard.ACCIDENT) return 4; // Blocked by other attacks
                        break;
                    case _75KM:
                    case _100KM:
                    case _200KM:
                        if (card.getType() == TypeCard.RED_LIGHT || card.getType() == TypeCard.OUT_OF_FUEL ||
                            card.getType() == TypeCard.FLAT_TIRE || card.getType() == TypeCard.ACCIDENT ||
                            card.getType() == TypeCard.SPEED_LIMITATION) return 4; // Blocked by other attacks
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Safety) {
            // Check if the safety card can be played based on current attacks
            switch (c.getType()) {
                case GREEN_LIGHT:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.RED_LIGHT) || !getgreenLight()) {
                        return 0; // Invalid if RED_LIGHT is active or green light is not available
                    } else if (getgreenLight()) {
                        return 5; // Valid if green light is available
                    }
                    break;
                case END_SPEED_LIMITATION:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.SPEED_LIMITATION)) {
                        return 0; // Invalid if Speed Limitation is still active
                    }
                    break;
                case FUEL:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.OUT_OF_FUEL)) {
                        return 0; // Invalid if Out of Fuel attack is active
                    }
                    break;
                case SPARE_WHEEL:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.FLAT_TIRE)) {
                        return 0; // Invalid if Flat Tire attack is active
                    }
                    break;
                case REPAIR:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.ACCIDENT)) {
                        return 0; // Invalid if Accident attack is active
                    }
                    break;
                default:
                    break;
            }
            return 6; // Return 6 for other safety cards that do not fall into the previous categories
        }
        return 0; // Return 0 if the card is valid
    }

    /**
     * Discards a card from the player's hand and updates the game view.
     * The card is removed from the player's hand, and the game interface is updated accordingly.
     *
     * @param c The card being discarded.
     * @param Controler The controller that handles game logic and view updates.
     * @return String A message indicating the card has been discarded.
     */
    public String discard(Card c, Controler Controler) {
        // Remove the card from the player's hand
        getHand().remove(c);

        // Update the game view by clearing the cards and printing the updated hand
        Controler.getVue().effacerCardsPlayers();
        Controler.getVue().printCardsPlayer(getHand());

        // Initialize buttons for the remaining cards and refresh the view
        Controler.initButtonCards(getHand());
        Controler.getVue().getWindow().repaint();
        Controler.getVue().getWindow().revalidate();

        // Return a message indicating which card was discarded
        return "Vous avez défaussé la card : " + c.getName() + "\n";
    }
}