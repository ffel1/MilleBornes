package com.millebornes;

import java.util.Random;

/**
 * The CPU class represents a CPU-controlled player in the game.
 * 
 * This abstract class extends the Player class and handles the logic for bot actions during the game.
 * The CPU player draws cards, plays cards based on its strategy, discards cards when needed, and selects a target for attacks.
 * 
 * Responsibilities:
 * - Handle the bot's turn, including drawing cards, choosing a card to play, and possibly discarding a card.
 * - Make decisions about which card to play and which player to attack.
 * - Determine when to end the CPU's turn and check for victory conditions.
 * 
 * @see Player
 * @see Card
 * @see Controler
 * @see Game
 * @see TypeCard
 */
public abstract class CPU extends Player {

    // Private member to store the target player of the CPU
    private Player target;

    /**
     * Constructor to initialize the CPU player with its name, kilometers, id, and the game instance.
     * 
     * @param name The name of the CPU player.
     * @param k The initial kilometers for the CPU player.
     * @param id The unique ID of the CPU player.
     * @param Game The game instance in which the CPU is playing.
     */
    public CPU(String name, int k, int id, Game Game) {
        super(name, k, id, Game); // Call the superclass (Player) constructor to set up the CPU player
    }

    /**
     * Handles the bot's actions during its turn.
     * 
     * The bot will draw a card, choose a card to play, and either play it or discard it if it cannot play.
     * The CPU will continue drawing and playing cards if it plays a Boot card (which allows a re-draw).
     * The turn ends when the CPU completes its actions, and the game state is updated.
     * 
     * @param Controler The game controller that manages the flow of the game.
     */
    public void botAction(Controler Controler) {
        Controler.getVue().addMessage("\nC'est au tour du CPU " + getName() + " ! Distance parcourue : " + getKilometers() + " km \n", true);
        draw(); // CPU draws a card
        Controler.getVue().addMessage("Le CPU " + getName() + " a pioché ! \n", true);

        // Choose a card to play
        Card playedCard = chooseCard();

        if (playedCard == null) {
            // If no card is available to play, discard a card
            Controler.getVue().addMessage("Le CPU " + getName() + " ne peut pas jouer ! \n", true);
            discard(discardChoice(), Controler);
        } else {
            // Handle playing a card
            while (playedCard instanceof Boot) {
                // If the card is a Boot, the CPU can play it, draw a new card, and continue
                Controler.getVue().addMessage(playCard(playedCard, target), true);
                draw();
                Controler.getVue().addMessage("Le CPU " + getName() + " a encore pioché ! \n", true);
                playedCard = chooseCard(); // Choose a new card after playing the Boot card
                if (playedCard == null) {
                    Controler.getVue().addMessage("Le CPU " + getName() + " ne peut pas jouer !\n", true);
                    discard(discardChoice(), Controler); // Discard a card if no card is available to play
                }
            }
            // Once a card is played, end the turn
            Controler.getVue().addMessage(playCard(playedCard, target), true);
            Controler.getVue().addMessage("C'est la fin du tour de " + getName() + " ! Distance parcourue : " + getKilometers() + " km \n", true);

            // Check if the CPU has won after its turn
            if (Controler.getModel().winner() == this) {
                Controler.getVue().addMessage("\n Le CPU " + getName() + " a gagné... La prochaine fois peut être... \n", true);
            }
        }

        // Refresh the game state after the CPU's turn
        Controler.getVue().refreshAttacks(getGame());
        Controler.getVue().refreshBoots(getGame());
    }

    /**
     * Abstract method for choosing a card to play.
     * 
     * This method should be implemented by the concrete subclass of CPU to decide which card to play during the bot's turn.
     * 
     * @return The card chosen by the CPU to play.
     */
    public abstract Card chooseCard();

    /**
     * Abstract method for choosing a card to discard.
     * 
     * This method should be implemented by the concrete subclass of CPU to decide which card to discard during the bot's turn.
     * 
     * @return The card chosen by the CPU to discard.
     */
    public abstract Card discardChoice();

    /**
     * Discards a chosen card and removes it from the CPU's hand.
     * 
     * @param c The card to discard.
     * @param Controler The game controller.
     * @return A message indicating the card has been discarded.
     */
    public String discard(Card c, Controler Controler) {
        Controler.getVue().addMessage("Le CPU " + getName() + " défausse la carte :" + c.getName() + " \n", true);
        getHand().remove(c); // Remove the discarded card from the hand
        return null;
    }

    /**
     * Sets the current target player for the CPU.
     * 
     * @param c The player that the CPU will target for its actions.
     */
    public void setCurrenttarget(Player c) {
        target = c; // Set the target player
    }

    /**
     * Gets the current target player for the CPU.
     * 
     * @return The target player of the CPU.
     */
    public Player getCurrenttarget() {
        return target; // Return the current target player
    }

    /**
     * Determines the best target for a specific action based on the current game state.
     * 
     * This method compares the players' distances and selects the one that is the most appropriate target based on the game rules.
     * 
     * @param c The card that is being played to determine the target.
     * @return The best target player for the action based on the CPU's strategy.
     */
    public Player getTarget(Card c) {
        Player actualWinner = getGame().getPlayer1();
        // Iterate through players to find the one with the highest kilometers
        for (Player j : getGame().getPlayers()) {
            if (j.getKilometers() > actualWinner.getKilometers() && j.getId() != getId()) {
                actualWinner = j; // Choose the player with the greatest distance
            } else if (j.getKilometers() == actualWinner.getKilometers() && j.getId() != getId()) {
                Random r = new Random();
                int i = r.nextInt(2); // Randomly choose between players with the same distance
                if (i == 0) {
                    actualWinner = j;
                }
            }
        }
        // If the card can be used on the selected target, return that player
        if (check(c, this, actualWinner)) {
            target = actualWinner;
            return actualWinner;
        } else {
            // If the first target isn't valid, check other players for a valid target
            for (Player j : getGame().getPlayers()) {
                if (j.getId() != getId() && j.getId() != actualWinner.getId() && check(c, this, j)) {
                    target = j;
                    return j;
                }
            }
        }

        return null; // Return null if no valid target is found
    }
}
