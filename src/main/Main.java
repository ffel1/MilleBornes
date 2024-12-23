package main;

/**
 * The Main class is the entry point of the application.
 * 
 * This class initializes the game by creating instances of the Game and WindowGame classes,
 * and then passing them to the Controller class to handle the game's logic and user interface interactions.
 * 
 * The main method in this class serves as the starting point for the application, setting up the game
 * environment and establishing the connections between the game's model, view, and controller.
 * 
 * Responsibilities:
 * - Create a new instance of the Game class to manage the game state and rules.
 * - Create a new instance of the WindowGame class to display the user interface.
 * - Initialize a Controller with the game model and view to handle game logic and interactions.
 * 
 * This class doesn't contain any game logic itself but acts as the launcher for the game.
 */
public class Main {

    /**
     * The main method is the entry point for the game application.
     * 
     * It creates the necessary game objects (Game and WindowGame) and passes them to the Controller class.
     * The Controller manages interactions between the game model and the view.
     * 
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String[] args){
        // Create a new Game object to represent the state and rules of the game
        Game Game = new Game();

        // Create a new WindowGame object to manage the user interface of the game
        WindowGame win = new WindowGame();

        // Pass the game and window objects to the Controller to handle the game logic and UI interactions
        new Controler(Game, win);
    }
}