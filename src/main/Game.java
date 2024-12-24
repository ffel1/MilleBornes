package main;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Game class represents the logic of the card game, including initialization, game rounds, and tracking player progress.
 * It manages the players, deck (draw), and keeps track of game state such as scores and the winner.
 * The class also handles game progression, including checking for win conditions and calculating points.
 *
 * Responsibilities:
 * - Initialize the game, players, and cards.
 * - Handle the logic of the game rounds.
 * - Track the game state such as points and winner determination.
 * - Save the game progress in a file for history tracking.
 * 
 * @see Player
 * @see Card
 * @see CPU
 * @see User
 * @see TypeCard
 */
public class Game implements Serializable {

    private int whoStart; // The index of the player who starts the game
    private ArrayList<Player> players; // List of players in the game
    private ArrayList<Card> draw; // The draw pile containing the cards
    private String nameGame; // Name of the game file
    private int ptsPlayer; // Points for the player (User)
    private int ptsCPUFast; // Points for the CPUFast
    private int ptsCPUAgro; // Points for the CPUAgro
    private int roundNumber; // The current round number
    private Player winnerOfTheGame; // The winner of the game

    /**
     * Constructor to initialize the game.
     * Initializes an empty list of players and prepares for game setup.
     */
    public Game(){
        players = new ArrayList<Player>(); // Create an empty list of players
    }

    /**
     * Initializes the game name by generating a unique filename for the game round.
     * It checks for existing files and increments the round number until a unique filename is found.
     */
    public void initGameName() {
        File file;
        int i = 1;

        // Create the directory for saving game history if it doesn't exist
        File directory = new File("SauvegardeDesHistoriques");
        if(!directory.exists()) {
            directory.mkdir();
        }

        // Find a unique filename for the game round
        file = new File("SauvegardeDesHistoriques/round_" + i + ".txt");
        while(file.exists()) {
            i++;
            file = new File("SauvegardeDesHistoriques/round_" + i + ".txt");
        }

        try {
            file.createNewFile(); // Create the new file
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        nameGame = "round_" + i + ".txt"; // Set the name of the game file
    }

    /**
     * Returns the name of the game (filename).
     * 
     * @return The name of the game file.
     */
    public String getnameGame() {
        return nameGame;
    }

    /**
     * Initializes the draw pile with cards.
     * Adds Distance, Attack, Safety, and Boot cards to the draw pile and shuffles them.
     */
    public void initDraw() {
        draw = new ArrayList<Card>();

        // Add Distance cards (various kilometer values)
        for (int i = 0; i < 12; i++) {
            if (i < 10) {
                draw.add(new Distance(TypeCard._25KM, 25));
                draw.add(new Distance(TypeCard._50KM, 50));
                draw.add(new Distance(TypeCard._75KM, 75));

                if (i < 4) {
                    draw.add(new Distance(TypeCard._200KM, 200));
                }
            }
            draw.add(new Distance(TypeCard._100KM, 100));
        }

        // Add Attack cards (various attack types)
        for (int i = 0; i < 5; i++) {
            if (i < 4) {
                draw.add(new Attack(TypeCard.SPEED_LIMITATION));

                if (i < 3) {
                    draw.add(new Attack(TypeCard.ACCIDENT));
                    draw.add(new Attack(TypeCard.OUT_OF_FUEL));
                    draw.add(new Attack(TypeCard.FLAT_TIRE));
                }
            }
            draw.add(new Attack(TypeCard.RED_LIGHT));
        }

        // Add Safety cards (various safety types)
        for (int i = 0; i < 14; i++) {
            if (i < 6) {
                draw.add(new Safety(TypeCard.REPAIR));
                draw.add(new Safety(TypeCard.FUEL));
                draw.add(new Safety(TypeCard.SPARE_WHEEL));
                draw.add(new Safety(TypeCard.END_SPEED_LIMITATION));
            }
            draw.add(new Safety(TypeCard.GREEN_LIGHT));
        }

        // Add Boot cards (various boot types)
        draw.add(new Boot(TypeCard.EXPERT_DRIVER));
        draw.add(new Boot(TypeCard.TANK_TRUCK));
        draw.add(new Boot(TypeCard.PUNCTURE_PROOF));
        draw.add(new Boot(TypeCard.PRIORITY_VEHICLE));

        // Shuffle the draw pile to randomize the card order
        Collections.shuffle(draw);
    }

    /**
     * Returns the size of the draw pile.
     * 
     * @return The number of cards in the draw pile.
     */
    public int sizeDraw() {
        return draw.size();
    }

    /**
     * Returns the index of the player who starts the game.
     * 
     * @return The index of the starting player.
     */
    public int getwhoStart() {
        return whoStart;
    }

    /**
     * Returns the current draw pile.
     * 
     * @return The list of cards in the draw pile.
     */
    public ArrayList<Card> getdraw() {
        return draw;
    }

    /**
     * Returns true if a game has been loaded (i.e., there are players).
     * 
     * @return True if a game has been loaded, false otherwise.
     */
    public boolean loadedGame() {
        return !players.isEmpty();
    }

    /**
     * Returns the list of players in the game.
     * 
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Sets the player at the specified index in the list of players.
     * 
     * @param player The player to set.
     * @param i The index where the player will be set.
     */
    public void setPlayers(Player player, int i) {
        players.set(i, player);
    }

    /**
     * Returns the User player (the first player in the list).
     * 
     * @return The User player.
     */
    public User getPlayer1() {
        return (User) players.get(0);
    }

    /**
     * Returns the first CPU player (CPUAgro).
     * 
     * @return The CPUAgro player.
     */
    public CPU getPlayer2() {
        return (CPU) players.get(1);
    }

    /**
     * Returns the second CPU player (CPUFast).
     * 
     * @return The CPUFast player.
     */
    public CPU getPlayer3() {
        return (CPU) players.get(2);
    }

    /**
     * Starts a new game by resetting the round number, initializing the draw pile, and creating the players.
     * It also assigns cards to each player and randomly selects the starting player.
     */
    public void newGame() {
        roundNumber++;
        initDraw(); // Initialize the draw pile
        players.clear(); // Clear previous players

        // Add players to the game
        players.add(0, new User("Vous", 0, 0, this));
        players.add(1, new CPUAgro("Agro", 0, 1, this));
        players.add(2, new CPUFast("Fast", 0, 2, this));

        // Deal 6 cards to each player from the draw pile
        for (int i = 0; i < 6; i++) {
            players.get(0).addCard(draw.get(i));
            draw.remove(i);
            players.get(1).addCard(draw.get(i));
            draw.remove(i);
            players.get(2).addCard(draw.get(i));
            draw.remove(i);
        }

        // Randomly select the starting player
        Random r = new Random();
        whoStart = r.nextInt(3); // Randomly choose a player to start
    }

    /**
     * Checks if a card can be played based on the game logic.
     * 
     * @param c The card to be played.
     * @param u The player attempting to play the card.
     * @param target The target player for the card.
     * @return True if the card can be played, false otherwise.
     */
    public boolean playCard(Card c, Player u, Player target) {
        return true; // Simplified for this example
    }

    /**
     * Determines the winner of the game based on the player's kilometers.
     * 
     * @return The player who has won, or null if no player has won yet.
     */
    public Player winner() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getKilometers() >= 700) {
                return players.get(i); // Return the player who has reached 700 kilometers
            }
        }
        return null; // No winner yet
    }

    // @return the points of the main player
    public int getPtsPlayer() {
        return ptsPlayer;
    }

    // @return the points of the Agro CPU player
    public int getPointCPUAgro() {
        return ptsCPUAgro;
    }

    // @return the points of the Fast CPU player
    public int getPtsCPUFast() {
        return ptsCPUFast;
    }

    // @return the round number
    public int getNumeroround() {
        return roundNumber;
    }

    /**
     * This method counts the points for each player at the end of the round. 
     * The points are calculated based on several factors such as:
     * - kilometers driven
     * - special bonuses (e.g., Capot)
     * - boots played
     * - dirty tricks
     * - victory points for the winner of the round
     */
    public void countingPts() {
        Player winner = winner();  // Get the winner of the round

        // Points from distance traveled
        ptsPlayer += players.get(0).getKilometers();
        ptsCPUAgro += players.get(1).getKilometers();
        ptsCPUFast += players.get(2).getKilometers();

        // "Capot" bonus for players with 0 kilometers traveled
        if (players.get(1).getKilometers() == 0) {
            ptsPlayer += 500;
            ptsCPUAgro += 500; 
        }
        if (players.get(2).getKilometers() == 0) {
            ptsPlayer += 500;
            ptsCPUFast += 500;
        }
        if (players.get(0).getKilometers() == 0) {
            ptsCPUFast += 500;
            ptsCPUAgro += 500;
        }

        // Points from boots played by each player
        int nbBootsPlayer = players.get(0).getPlayedBoots().size();
        int nbBootsCPUAgro = players.get(1).getPlayedBoots().size();
        int nbBootsCPUFast = players.get(2).getPlayedBoots().size();

        // Points for each boot
        ptsPlayer += (nbBootsPlayer == 4) ? 700 : nbBootsPlayer * 100;
        ptsCPUFast += (nbBootsCPUFast == 4) ? 700 : nbBootsCPUFast * 100;
        ptsCPUAgro += (nbBootsCPUAgro == 4) ? 700 : nbBootsCPUAgro * 100;

        // Points from dirty tricks
        ptsPlayer += players.get(0).getdirtyTricks() * 300;
        ptsCPUAgro += players.get(2).getdirtyTricks() * 300;
        ptsCPUFast += players.get(1).getdirtyTricks() * 300;

        // Points for round victory
        if (winner == null) {
            winner = getPlayer1(); // Default winner if no one has more kilometers
            for (Player player : players) {
                if (player.getKilometers() > winner.getKilometers()) {
                    winner = player;
                }
            }
        }

        // Award points based on the winner of the round
        if (winner.getId() == 0) {
            ptsPlayer += 400;  // Points for winning the round
            if (draw.isEmpty()) {
                ptsPlayer += 300;  // Points for "crowning" (winning with no cards left)
            }
            if (!players.get(0).use200KM()) {
                ptsPlayer += 300;  // Points for not using 200KM card
            }
        }
        else if (winner == players.get(1)) {
            ptsCPUFast += 400;  // Points for winning the round
            if (draw.isEmpty()) {
                ptsCPUFast += 300;  // Points for "crowning"
            }
            if (!players.get(1).use200KM()) {
                ptsCPUFast += 300;  // Points for not using 200KM card
            }
        }
        else if (winner == players.get(2)) {
            ptsCPUAgro += 400;  // Points for winning the round
            if (draw.isEmpty()) {
                ptsCPUAgro += 300;  // Points for "crowning"
            }
            if (!players.get(2).use200KM()) {
                ptsCPUAgro += 300;  // Points for not using 200KM card
            }
        }

        // Update each player's points after the round
        getPlayer1().setpts(getPtsPlayer());
        getPlayer2().setpts(getPointCPUAgro());
        getPlayer3().setpts(getPtsCPUFast());

        // Determine the overall winner based on points
        Player localWinner = getPlayer1();
        for (Player player : players) {
            if (player.getPts() > localWinner.getPts()) {
                localWinner = player;
            }
        }

        // Set the overall winner if points exceed 5000
        if (localWinner.getPts() > 5000) {
            winnerOfTheGame = localWinner;
        }

        System.out.println(ptsPlayer);
    }

    /**
     * This method returns the player who is leading in terms of kilometers.
     * @return the player with the most kilometers
     */
    public Player theLeadingPlayer() {
        Player winner = getPlayer1();
        for (Player player : getPlayers()) {
            if (player.getKilometers() > winner.getKilometers()) {
                winner = player;
            }
        }
        return winner;
    }

    /**
     * This method returns the overall winner of the game.
     * @return the player who won the game
     */
    public Player getwinnerOfTheGame() {
        return winnerOfTheGame;
    }

}