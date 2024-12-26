package com.millebornes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a player in the game, with the ability to hold a hand of cards, perform attacks, play defense cards,
 * manage distance, and interact with other players.
 * A player can have a set of current attacks, green light status, played boots, and other attributes like kilometers 
 * traveled, points, and dirty tricks performed.
 */
public abstract class Player implements Serializable {
    // Private attributes
    private ArrayList<Card> Hand;  // The player's hand of cards
    private ArrayList<Card> currentAttacks;  // The attacks currently being played by the player
    private boolean greenLight;  // Green light status (can play distance cards)
    private ArrayList<Card> playedBoots;  // The boots (special cards) played by the player
    private String name;  // The player's name
    private int kilometersP;  // Kilometers traveled by the player
    private int id;  // Unique player ID
    private int pts;  // Points the player has earned
    private Game Game;  // The game the player is part of
    private int dirtyTrickes = 0;  // Count of dirty tricks performed
    private ArrayList<Card> cardsDistanceJouees;  // Distance cards played by the player

    /**
     * Constructor to initialize a new player with the specified name, kilometers traveled, ID, and associated game.
     * The player starts with an empty hand, no attacks, no green light, and no played boots.
     * 
     * @param name The player's name.
     * @param km The initial kilometers traveled by the player.
     * @param id The player's unique ID.
     * @param Game The game the player is part of.
     */
    public Player(String name, int km, int id, Game Game) {
        cardsDistanceJouees = new ArrayList<Card>();
        Hand = new ArrayList<Card>();
        currentAttacks = new ArrayList<Card>();
        greenLight = false;
        playedBoots = new ArrayList<Card>();
        this.name = name;
        kilometersP = km;
        this.id = id;
        this.Game = Game;
    }

    /**
     * Returns the player's hand of cards.
     *
     * @return ArrayList<Card> The player's hand.
     */
    public ArrayList<Card> getHand() {
        return Hand;
    }

    /**
     * Returns the list of attacks currently being played by the player.
     *
     * @return ArrayList<Card> The player's current attacks.
     */
    public ArrayList<Card> getCurrentAttacks() {
        return currentAttacks;
    }

    /**
     * Returns the green light status for the player.
     *
     * @return boolean True if the player has the green light, false otherwise.
     */
    public boolean getgreenLight() {
        return greenLight;
    }

    /**
     * Sets the player's points.
     *
     * @param pts The points to set.
     */
    public void setpts(int pts) {
        this.pts = pts;
    }

    /**
     * Returns the player's points.
     *
     * @return int The player's points.
     */
    public int getPts() {
        return pts;
    }

    /**
     * Returns the game that the player is part of.
     *
     * @return Game The game object associated with the player.
     */
    public Game getGame() {
        return Game;
    }

    /**
     * Sets the player's green light status.
     *
     * @param b True to enable the green light, false to disable.
     */
    public void setGreenLight(boolean b) {
        greenLight = b;
    }

    /**
     * Returns the list of boots (special cards) that the player has played.
     *
     * @return ArrayList<Card> The player's played boots.
     */
    public ArrayList<Card> getPlayedBoots() {
        return playedBoots;
    }

    /**
     * Returns the player's name.
     *
     * @return String The player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the total kilometers the player has traveled.
     *
     * @return int The player's kilometers.
     */
    public int getKilometers() {
        return kilometersP;
    }

    /**
     * Sets the player's kilometers by adding the specified value.
     *
     * @param Kilometers The kilometers to add.
     */
    public void setKilometers(int Kilometers) {
        kilometersP += Kilometers;
    }

    /**
     * Returns the player's unique ID.
     *
     * @return int The player's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the number of dirty tricks the player has performed.
     *
     * @return int The player's dirty tricks count.
     */
    public int getdirtyTricks() {
        return dirtyTrickes;
    }

    /**
     * Checks if the player's hand is full (i.e., has 7 or more cards).
     *
     * @return boolean True if the player's hand is full, false otherwise.
     */
    public boolean HandFull() {
        return Hand.size() >= 7;
    }

    /**
     * Removes the specified card from the player's hand.
     *
     * @param c The card to remove.
     */
    public void removeCard(Card c) {
        Hand.remove(c);
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param c The card to add.
     */
    public void addCard(Card c) {
        Hand.add(c);
    }

    /**
     * Checks if the player has already played a 200KM distance card.
     *
     * @return boolean True if the player has used a 200KM distance card, false otherwise.
     */
    public boolean use200KM() {
        for (Card card : cardsDistanceJouees) {
            if (card instanceof Distance && ((Distance) card).getKilometers() == 200) {
                return true;
            }
        }
        return false;
    }

    /**
     * Draws a card from the game deck into the player's hand, if the hand is not full.
     */
    public void draw() {
        ArrayList<Card> draw = Game.getdraw();
        if (!HandFull()) {
            Card c = draw.get(0);
            getHand().add(c);
            draw.remove(c);
        } else {
            System.out.println("Hand Full");
        }
    }

    /**
     * Plays a card based on its type, either an attack, safety, boot, or distance card.
     * The method will call the appropriate play method for each type of card.
     *
     * @param c The card to play.
     * @param target The target player (for attacks).
     * @return String The result of playing the card.
     */
    public String playCard(Card c, Player target) {
        if (c instanceof Attack) {
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
     * Abstract method to get the target player for an attack card.
     * This method needs to be implemented by subclasses to determine the target.
     *
     * @param c The card being played.
     * @return Player The target player for the attack.
     */
    public abstract Player getTarget(Card c);

    /**
     * Plays a boot card to the player, removing any relevant attacks in progress.
     * The method also tracks "dirty tricks" performed by the player and updates the player's state.
     *
     * @param c The boot card to play.
     * @return String A message indicating the result of playing the boot card.
     */
    public String playBoot(Card c) {
        if (c instanceof Boot) {
            playedBoots.add(c);
            boolean dirtyTrick = false;
            switch (c.getType()) {
                case EXPERT_DRIVER:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.ACCIDENT);
                    break;
                case TANK_TRUCK:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.OUT_OF_FUEL);
                    break;
                case PUNCTURE_PROOF:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.FLAT_TIRE);
                    break;
                case PRIORITY_VEHICLE:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.SPEED_LIMITATION || card.getType() == TypeCard.RED_LIGHT);
                    setGreenLight(true);
                    break;
                default:
                    break;
            }
            if (dirtyTrick) {
                dirtyTrickes++;
            }
            removeCard(c);
            return getName() + " joue la botte : " + c.getName() + "\n";
        }
        return null;
    }

    /**
     * Abstract method for discarding a card. 
     * This method must be implemented by subclasses to handle specific discard logic.
     *
     * @param c The card to discard.
     * @param Controler The controller that manages the game.
     * @return String A message indicating the result of discarding the card.
     */
    public abstract String discard(Card c, Controler Controler);
    
    /**
     * Plays a safety card to defend against specific attacks and removes the corresponding attacks from the player.
     *
     * @param c The safety card to play.
     * @return String A message indicating the result of playing the safety card.
     */
    public String playSafety(Card c) {
        if (c instanceof Safety) {
            switch (c.getType()) {
                case GREEN_LIGHT:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.RED_LIGHT);
                    currentAttacks.add(0, c);
                    greenLight = true;
                    break;
                case END_SPEED_LIMITATION:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.SPEED_LIMITATION);
                    break;
                case FUEL:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.OUT_OF_FUEL);
                    break;
                case SPARE_WHEEL:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.FLAT_TIRE);
                    break;
                case REPAIR:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.ACCIDENT);
                    break;
                default:
                    break;
            }
            removeCard(c);
            if (c.getType() == TypeCard.GREEN_LIGHT) {
                return getName() + " joue un " + c.getName() + " ! \n";
            }
            return getName() + " joue la parade : " + c.getName() + "\n";
        }
        return null;
    }
    
    /**
     * Plays an attack card against a target player. 
     * If the attack is valid, it applies the attack and removes the card from the player's hand.
     *
     * @param c The attack card to play.
     * @param target The target player for the attack.
     * @return String A message indicating the result of playing the attack card.
     */
    public String playAttack(Card c, Player target) {
        if (check(c, this, target)) {
            if (c.getType() == TypeCard.RED_LIGHT) {
                target.setGreenLight(false);
                for (Card card : target.currentAttacks) {
                    if (card.getType() == TypeCard.GREEN_LIGHT) {
                        target.currentAttacks.remove(card);
                        break;
                    }
                }
            }
            target.getCurrentAttacks().add(c);
            removeCard(c);
            return getName() + " joue l'attaque " + c.getName() + " contre " + target.getName() + "\n";
        }
        return null;
    }
    
    /*
     * Handles the logic for playing a Distance card, which increases the player's kilometers.
     * It also checks if there are any attack cards that prevent the player from advancing.
     * If there are no attacks preventing the movement, the player's kilometers are updated, 
     * and the card is added to the list of played Distance cards.
     *
     * @param c The Distance card being played.
     * @return A string indicating the result of playing the Distance card.
     */
    public String playDistance(Card c) {
        if (check(c, this, this)) {
            int Kilometers = ((Distance) c).getKilometers(); // Retrieve the kilometers from the Distance card
            kilometersP += Kilometers; // Add the kilometers to the player's total distance
            cardsDistanceJouees.add(c); // Add the played Distance card to the list
            removeCard(c); // Remove the card from the player's hand
            return (getName() + " avance de " + Kilometers + " km. Distance totale : " + kilometersP + " km. \n");
        } else {
            String message = name + " ne pouvez pas avancer. Problème : ";
            // Loop through the current attack cards and check if any prevent movement
            for (Card card : currentAttacks) {
                if (card.getType() == TypeCard.RED_LIGHT) {
                    return message + "feu rouge.\n"; // Red light stops movement
                }
                if (card.getType() == TypeCard.OUT_OF_FUEL) {
                    return message + "panne d'FUEL.\n"; // Out of fuel stops movement
                }
                if (card.getType() == TypeCard.FLAT_TIRE) {
                    return message + "FLAT_TIRE.\n"; // Flat tire stops movement
                }
                if (card.getType() == TypeCard.ACCIDENT) {
                    return message + "accident.\n"; // Accident stops movement
                }
                if (card.getType() == TypeCard.SPEED_LIMITATION) {
                    return message + "limitation de vitesse.\n"; // Speed limitation stops movement
                }
            }
        }
        return null;
    }

    /*
     * Verifies if a given card can be played based on the current player's state 
     * and the target player's current attacks and conditions.
     * The check ensures that the attack can be made, the distance can be covered, 
     * or the safety card can be played, based on the existing conditions and attacks.
     *
     * @param c The card being checked.
     * @param u The player attempting to play the card.
     * @param target The target player for attack (may be null for some actions).
     * @return True if the card is valid to play, false otherwise.
     */
    public boolean check(Card c, Player u, Player target) {
        if (c instanceof Attack) {
            if (target == null) {
                return false; // Attack requires a target
            }
            if (c.getType() == TypeCard.RED_LIGHT && !target.getgreenLight()) {
                return false; // Cannot attack if the target is not green-lighted
            }
            // Check if any played boots on the target prevent the attack
            for (Card card : target.getPlayedBoots()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.PUNCTURE_PROOF) return false; // Puncture-proof prevents flat tire
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.EXPERT_DRIVER) return false; // Expert driver prevents accident
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.TANK_TRUCK) return false; // Tank truck prevents fuel shortage
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return false; // Priority vehicle prevents speed limitation
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return false; // Priority vehicle prevents red light
                        break;
                    default:
                        break;
                }
            }
            // Check if any active attacks on the target prevent the current attack
            for (Card card : target.getCurrentAttacks()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.FLAT_TIRE) return false; // Flat tire blocks another flat tire
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.ACCIDENT) return false; // Accident blocks another accident
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.OUT_OF_FUEL) return false; // Out of fuel blocks another fuel shortage
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.SPEED_LIMITATION) return false; // Speed limitation blocks another speed limitation
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.RED_LIGHT) return false; // Red light blocks another red light
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Distance) {
            if (!greenLight) {
                return false; // Cannot play Distance card without green light
            }
            if (c.getKilometers() + u.getKilometers() > 700) {
                return false; // Cannot exceed 700 km total distance
            }
            // Check if any attack cards prevent movement for Distance cards
            for (Card card : u.getCurrentAttacks()) {
                switch (c.getType()) {
                    case _25KM:
                    case _50KM:
                        if (card.getType() == TypeCard.RED_LIGHT || card.getType() == TypeCard.OUT_OF_FUEL || 
                            card.getType() == TypeCard.FLAT_TIRE || card.getType() == TypeCard.ACCIDENT) return false;
                        break;
                    case _75KM:
                    case _100KM:
                    case _200KM:
                        if (card.getType() == TypeCard.RED_LIGHT || card.getType() == TypeCard.OUT_OF_FUEL ||
                            card.getType() == TypeCard.FLAT_TIRE || card.getType() == TypeCard.ACCIDENT || 
                            card.getType() == TypeCard.SPEED_LIMITATION) return false;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Safety) {
            // Safety cards can only be played if specific attack conditions exist
            switch (c.getType()) {
                case GREEN_LIGHT:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.RED_LIGHT) || !getgreenLight()) {
                        return true; // Green light can be played if there is a red light or no green light
                    } else if (getgreenLight()) {
                        return false; // Green light cannot be played if already active
                    }
                case END_SPEED_LIMITATION:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.SPEED_LIMITATION)) {
                        return true; // Speed limitation can be removed if it's already active
                    }
                case FUEL:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.OUT_OF_FUEL)) {
                        return true; // Fuel card can be played if out of fuel
                    }
                case SPARE_WHEEL:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.FLAT_TIRE)) {
                        return true; // Spare wheel can be played if there's a flat tire
                    }
                case REPAIR:
                    if (getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.ACCIDENT)) {
                        return true; // Repair can be played if there's an accident
                    }
                default:
                    break;
            }
            return false;
        }
        return true; // Default return for valid conditions
    }
}