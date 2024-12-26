package com.millebornes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

/**
 * This class manages the gameplay logic for the card game, including handling user actions, 
 * game state transitions, and interactions with the view (UI). It includes methods for:
 * - Starting and managing rounds of the game.
 * - Managing game saves and loading previously saved games.
 * - Playing and controlling game music.
 * - Handling button actions for playing cards in the player's hand.
 * - Checking conditions for valid actions (such as drawing a card, attacking an opponent, or discarding a card).
 * 
 * The class coordinates with the model and view components to:
 * - Update the state of the game based on player actions.
 * - Provide feedback to the player via messages displayed in the UI.
 * - Ensure proper game flow and maintain consistency between the game state and the user interface.
 * 
 * Methods like `newRound()`, `saveManagement()`, `initButtonCards()`, and others are key to managing gameplay,
 * while music-related methods control the sound during gameplay. Save/load functionality allows the game to persist between sessions.
 * 
 * Attributes:
 * - `modele`: The game model that stores the current state of the game, including players, hands, and actions.
 * - `vue`: The view that displays the game interface, including cards, messages, and player actions.
 * - `soundList`: An object that controls the primary game music.
 * - `secondarySoundList`: An object that controls secondary sounds in the game.
 * - `turnDelay`: The delay between game turns for animations or transitions.
 * 
 * This class ensures the game's rules are enforced, and the user interface remains in sync with the game's internal state.
 */
public class Controler {
    private Game modele;              // Game model (logic)
    private WindowGame vue;           // Game view (UI)
    private Sound soundList;          // Sound effects manager
    private int turnDelay;            // Delay between turns
    private Sound secondarySoundList; // Secondary sound effects manager (for background sounds)

    /**
     * Constructor for Controler class. Initializes the game, window, and sound objects, and sets up actions for buttons.
     * 
     * @param modele The game model
     * @param vue The game window (view)
     */
    public Controler(Game modele, WindowGame vue) {
        this.modele = modele;
        this.vue = vue;
        vue.setControler(this);
        soundList = new Sound();
        secondarySoundList = new Sound();
        turnDelay = 3000; // Set the turn delay to 3 seconds
        vue.createWindowMenu(); // Create the main menu window

        // Set up action listener for "Play" button
        vue.addActionButtonPlay(e -> {
            playContiniouselyMusic(0); // Play main music
            vue.getWindow().getContentPane().removeAll(); // Clear the window content
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.resettingKilometers();
            vue.createWindowGame(); // Create the game window
            newGame(false, false); // Start a new game
        });

        // Set up action listener for "Saves" button
        vue.addButtonActionSaves(e -> {
            vue.getWindow().getContentPane().removeAll(); // Clear the window content
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.createWindowSaves(); // Create the saves window
        });

        // Set up action listener for "Quit" button
        vue.addActionQuitButton(e -> System.exit(0)); // Exit the game when clicked
    }

    /**
     * Gets the sound list used for game sounds.
     * 
     * @return The sound list
     */
    public Sound getsoundList() {
        return soundList;
    }

    /**
     * Starts a new game. If a save file exists and no flag is passed, it loads the saved game.
     * 
     * @param b If true, starts a new game regardless of existing save.
     * @param endGameForced If true, forces the end of the game without saving.
     */
    private void newGame(boolean b, boolean endGameForced) {


        String appPath = System.getProperty("user.dir");
        String directoryPath = appPath + File.separator + "resources" + File.separator + "save.ser";
        File file = new File(directoryPath);

        
        boolean loadedGame;
        if (file.exists() && !b) {
            loadingSave(file); // Load saved game if it exists
        }

        // If the game is not loaded or the flag b is true, start a new game
        if (!modele.loadedGame() || b) {
            modele.newGame();
            loadedGame = false;
            modele.initGameName();
        } else {
            loadedGame = true; // Game was loaded from save
        }

        String nameGame = modele.getnameGame(); // Get the game name
        vue.setNameGame(nameGame); // Update the game name in the view

        // If a game was loaded, load logs
        if (loadedGame) {
            vue.loadLogs();
        }

        // Refresh the view with player status
        vue.refreshAttacks(modele);
        vue.refreshBoots(modele);
        vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
        vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());


        // Set up action listener for "Main Menu" button
        vue.addActionReturnButton(e -> {
            if(!modele.getPlayer1().getmyTurn())
            {
                vue.addMessage("Attendez votre tour pour quitter la partie !\n", false);
                return;
            }
            if (soundList.getSoundON()) {
                stopMusic(); // Stop the music if it's playing
            }
            soundList = new Sound();
            secondarySoundList = new Sound();
            initSoundButton(); // Initialize sound buttons
            if (modele.getPlayer1().getIsAttackingWith() != null) {
                modele.getPlayer1().getIsAttackingWith().setImageBack(); // Reset attack image
            }
            save(); // Save game state
            vue.getWindow().getContentPane().removeAll(); // Clear the window content
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.createWindowMenu(); // Return to the main menu window
        });

        // Set up action listener for "New Game" button
        vue.addActionButtonNewGame(e -> {
            vue.addMessage("Vous avez mis fin à la manche " + modele.getNumeroround() + ", les points ne seront pas comptabilisés\n", true);
            vue.resettingKilometers();
            newRound(true, true); // Start a new round
        });

        // Set up action listener for "Discard" button
        vue.addActionButtonDiscard(e -> {
            // Various conditions check if the player can discard a card
            if (!modele.getPlayer1().getmyTurn()) {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            } else if (modele.getPlayer1().getIsAttacking()) {
                vue.addMessage("Vous ne pouvez pas défausser, vous êtes en train d'attaquer !", false);
            } else if (modele.getPlayer1().getMustDraw()) {
                vue.addMessage("Après avoir joué une botte vous devez piocher\n", false);
            } else if (!modele.getPlayer1().getHasDraw()) {
                vue.addMessage("Vous devez piocher avant de défausser ! \n", false);
            } else if (modele.getPlayer1().getHasPlayed()) {
                vue.addMessage("Vous avez déjà joué une carte, vous ne pouvez plus défausser ! \n", false);
            } else if (!modele.getPlayer1().HandFull()) {
                vue.addMessage("Vous n'avez pas plus de 6 cartes, vous ne pouvez pas défausser \n", false);
            } else if (!modele.getPlayer1().getDiscard()) {
                vue.addMessage("Cliquez sur la card que vous voulez défausser ! \nCliquez à nouveau sur la pioche pour annuler \n", false);
                modele.getPlayer1().setdiscard(true); // Enable discard mode
                vue.getDiscard().setText("Annuler"); // Update discard button text
            } else {
                vue.addMessage("Vous avez change d'avis \n", false);
                modele.getPlayer1().setdiscard(false); // Disable discard mode
                vue.getDiscard().setText(""); // Reset discard button text
            }
        });

        // Button to end the player's turn
        vue.AddActionButtonEndOfMyTurn(e -> {
            // Check if it's the player's turn
            if (!modele.getPlayer1().getmyTurn()) {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }
            // Check if the player is in the middle of discarding cards
            else if (modele.getPlayer1().getDiscard()) {
                vue.addMessage("Vous êtes en train de défausser, impossible de finir votre tour ! \n", false);
            }
            // Check if the player needs to draw a card after playing a boot
            else if (modele.getPlayer1().getMustDraw()) {
                vue.addMessage("Après avoir joué une botte vous devez piocher \n", false);
            }
            // Check if the player is in the middle of an attack
            else if (modele.getPlayer1().getIsAttacking()) {
                vue.addMessage("Vous ne pouvez pas finir votre tour, vous êtes en train d'attaquer ! \n", false);
            }
            // Check if the player has played a card and needs to discard if they have more than 6 cards
            else if (modele.getPlayer1().getHasPlayed()) {
                if (modele.getPlayer1().getHand().size() > 6) {
                    vue.addMessage("Vous devez défausser une carte ! \n", false);
                } else {
                    vue.addMessage("\nC'est la fin de votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);

                    // Check if the player is the winner and end the game
                    if (modele.winner() == modele.getPlayer1()) {
                        vue.addMessage("\n VOUS AVEZ GAGNE LA MANCHE !! BRAVO ! \n", true);
                        newRound(true, false);
                        return;
                    }

                    vue.printCardsPlayer(modele.getPlayer1().getHand());
                    initButtonCards(modele.getPlayer1().getHand());
                    modele.getPlayer1().sethasPlayed(false);
                    modele.getPlayer1().setHasDraw(false);
                    modele.getPlayer1().myTurn(false);
                    modele.getPlayer1().setHasDiscard(false);

                    vue.addMessage("\nEn attente du joueur Agro \n", true);

                    // Timer to handle the next player's turn (Player 2)
                    Timer chrono = new Timer();
                    chrono.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            modele.getPlayer2().botAction(getControler()); // Perform Player 2's bot action
                            vue.refreshAttacks(modele);
                            vue.refreshBoots(modele);
                            
                            // End the game if there's a winner or the draw pile is empty
                            if (modele.winner() != null || modele.getdraw().size() == 0) {
                                if (modele.getdraw().size() == 0) {
                                    vue.addMessage("La pioche est vide ! \n", b);
                                }
                                newRound(true, false);
                                return;
                            }
                            
                            vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
                            vue.addMessage("\nEn attente du joueur Fast \n", true);

                            // Timer to handle the next player's turn (Player 3)
                            Timer chrono = new Timer();
                            chrono.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    modele.getPlayer3().botAction(getControler()); // Perform Player 3's bot action
                                    vue.refreshAttacks(modele);
                                    vue.refreshBoots(modele);
                                    
                                    // End the game if there's a winner or the draw pile is empty
                                    if (modele.winner() != null || modele.getdraw().size() == 0) {
                                        if (modele.getdraw().size() == 0) {
                                            vue.addMessage("La pioche est vide ! \n", b);
                                        }
                                        newRound(true, false);
                                        return;
                                    }
                                    
                                    vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());
                                    modele.getPlayer1().myTurn(true);
                                    vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                                    vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
                                    vue.refreshAttacks(modele);
                                    vue.refreshBoots(modele);
                                }
                            }, turnDelay); // Delay before the next player's turn
                        }
                    }, turnDelay); // Delay before Player 2's action
                }
            }
            // Check if the player has not played a card but has fewer than 6 cards
            else if (!modele.getPlayer1().getHasPlayed() && modele.getPlayer1().getHand().size() <= 6) {
                if (!modele.getPlayer1().getHasDraw()) {
                    vue.addMessage("Vous sautez votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);
                } else {
                    vue.addMessage("Vous ne jouez rien pendant ce tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);
                }

                modele.getPlayer1().sethasPlayed(false);
                modele.getPlayer1().setHasDraw(false);
                modele.getPlayer1().myTurn(false);
                modele.getPlayer1().setHasDiscard(false);

                // Timer to handle the next player's turn (Player 2)
                Timer chrono = new Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        vue.addMessage("\nEn attente du joueur Agro\n", true);
                        modele.getPlayer2().botAction(getControler());
                        vue.refreshAttacks(modele);
                        vue.refreshBoots(modele);
                        
                        // End the game if there's a winner or the draw pile is empty
                        if (modele.winner() != null || modele.getdraw().size() == 0){
                            if (modele.getdraw().size() == 0) {
                                vue.addMessage("La pioche est vide ! \n", b);
                            }
                            newRound(true, false);
                            return;
                        }
                        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
                        Timer chrono = new Timer();
                        chrono.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                vue.addMessage("\nEn attente du joueur Fast\n", true);
                                modele.getPlayer3().botAction(getControler());
                                vue.refreshAttacks(modele);
                                vue.refreshBoots(modele);
                                
                                // End the game if there's a winner or the draw pile is empty
                                if (modele.winner() != null || modele.getdraw().size() == 0) {
                                    if (modele.getdraw().size() == 0) {
                                        vue.addMessage("La pioche est vide ! \n", b);
                                    }
                                    newRound(true, false);
                                    return;
                                }

                                vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());
                                modele.getPlayer1().myTurn(true);
                                vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                                vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
                                vue.refreshAttacks(modele);
                                vue.refreshBoots(modele);
                            }
                        }, turnDelay);
                    }
                }, turnDelay);
            } else {
                vue.addMessage("Vous ne pouvez pas finir votre tour avec plus de 6 carte ! \n", true);

                if (modele.getPlayer1().getHasPlayed()) {
                    vue.addMessage("Vous devez défausser une carte ! \n", true);
                } else {
                    vue.addMessage("Jouez ou défaussez une carte ! \n", true);
                }
            }
        });

        //Button AttackBotAgro
        vue.addActionButtonCPUAgro(e -> {
            playMusic(1);
            // Check if it's the player's turn and if they are attacking
            if(modele.getPlayer1().getmyTurn() && modele.getPlayer1().getIsAttacking()) {
                modele.getPlayer1().setTarget(modele.getPlayer2()); // Set target as Player 2
                // Check if the player can attack the target with the selected card
                if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 0) {
                    modele.getPlayer1().playCard(modele.getPlayer1().getIsAttackingWith(),getControler(),0); // Perform the attack
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 1) {
                    // Message if the target is protected by a defense ability
                    vue.addMessage("Vous ne pouvez pas attaquer le CPU " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il a une botte qui le protège de cette attaque !\n", false);
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 2) {
                    // Message if the target is already affected by the attack
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il subit déjà cette attaque !\n", false);
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 7) {
                    // Message if the target hasn't given a "green light" yet
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il n'a même pas encore mit de feu vert !\n", false);
                }
                // Reset the attacking card and update the player's hand
                modele.getPlayer1().getIsAttackingWith().setImageBack();
                vue.deleteCardsPlayers();
                vue.printCardsPlayer(modele.getPlayer1().getHand());
                initButtonCards(modele.getPlayer1().getHand());
                // End the attacking state
                modele.getPlayer1().setIsAttacking(false);
                modele.getPlayer1().setIsAttackingWith(null);
                modele.getPlayer1().setTarget(null); 
            }
            // Handle case when the player is not attacking
            else if(!(modele.getPlayer1().getIsAttacking())) {
                vue.addMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            // Handle case when it is not the player's turn
            else {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }

            vue.refreshAttacks(modele); // Refresh the attacks on the UI
            vue.refreshBoots(modele);   // Refresh the boots on the UI
        });

        //Button AttackBotFast
        vue.addActionButtonCPUFast(e -> {
            playMusic(1);
            // Check if it's the player's turn and if they are attacking
            if(modele.getPlayer1().getmyTurn() && modele.getPlayer1().getIsAttacking()) {
                modele.getPlayer1().setTarget(modele.getPlayer3()); // Set target as Player 3
                // Check if the player can attack the target with the selected card
                if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 0) {
                    modele.getPlayer1().playCard(modele.getPlayer1().getIsAttackingWith(),getControler(),0); // Perform the attack
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 1) {
                    // Message if the target is protected by a defense ability
                    vue.addMessage("Vous ne pouvez pas attaquer le CPU " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il a une botte qui le protège de cette attaque !\n", false);
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 2) {
                    // Message if the target is already affected by the attack
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il subit déjà cette attaque !\n", false);
                } else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 7) {
                    // Message if the target hasn't given a "green light" yet
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il n'a même pas encore mit de feu vert !\n", false);
                }
                // Reset the attacking card and update the player's hand
                modele.getPlayer1().getIsAttackingWith().setImageBack();
                vue.deleteCardsPlayers();
                vue.printCardsPlayer(modele.getPlayer1().getHand());
                initButtonCards(modele.getPlayer1().getHand());
                // End the attacking state
                modele.getPlayer1().setIsAttacking(false);
                modele.getPlayer1().setIsAttackingWith(null);
                modele.getPlayer1().setTarget(null);
            }
            // Handle case when the player is not attacking
            else if(!(modele.getPlayer1().getIsAttacking())) {
                vue.addMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            // Handle case when it is not the player's turn
            else {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }

            vue.refreshAttacks(modele); // Refresh the attacks on the UI
            vue.refreshBoots(modele);   // Refresh the boots on the UI
        });

        //Button draw 
        vue.addActionButtonDraw(e -> {
            // Check if the player must draw a card
            if(modele.getPlayer1().getMustDraw()) {
                modele.getPlayer1().setmustDraw(false); // Mark that the player no longer needs to draw
                boolean dejHasDraw = modele.getPlayer1().getHasDraw(); // Save the player's draw status
                modele.getPlayer1().draw(); // Draw a card
                modele.getPlayer1().setHasDraw(dejHasDraw); // Restore the player's draw status
                vue.printCardsPlayer(modele.getPlayer1().getHand()); // Update the player's hand
                initButtonCards(modele.getPlayer1().getHand()); // Initialize the buttons for the new hand
            } 
            // Handle case when the player is attacking and cannot draw
            else if(modele.getPlayer1().getIsAttacking()) {
                vue.addMessage("Vous êtes en train d'attaquer, vous ne pouvez pas piocher ! \n", false);
            }
            // Handle case when it's the player's turn and they haven't drawn yet
            else if(modele.getPlayer1().getmyTurn() && !modele.getPlayer1().getHasDraw()) {
                vue.addMessage("Vous avez pioché", true);
                // Check if the player's hand is full
                if(modele.getPlayer1().HandFull()) {
                    vue.addMessage(" mais votre main est pleine ! \n", false);
                } else {
                    vue.addMessage("\n", true);
                    modele.getPlayer1().draw(); // Draw a card
                    vue.printCardsPlayer(modele.getPlayer1().getHand()); // Update the player's hand
                    initButtonCards(modele.getPlayer1().getHand()); // Initialize the buttons for the new hand
                }
            } 
            // Handle case when the player has already drawn
            else if(modele.getPlayer1().getHasDraw()) {
                vue.addMessage(" Vous avez déjà pioché ! \n", false);
            }
            // Handle case when it's not the player's turn
            else {
                vue.addMessage(" Ce n'est pas votre tour \n", false);
            }

            // Check if the draw pile is empty
            if(modele.getdraw().size() == 0) {
                vue.addMessage("La pioche est vide !", true);
                newRound(true, false); // Start a new round
                return;
            }
        }); 

        // Button to toggle sound on and off
        vue.addActionSoundButton(e -> {
            if(secondarySoundList.getSoundON()){
                secondarySoundList.setSoundON(false); // Turn off the sound
            } else {
                secondarySoundList.setSoundON(true); // Turn on the sound
            }
            stopMusic(); // Stop any music that might be playing
            vue.changeImageSound(); // Update the sound icon on the UI
        });

        ArrayList<Card> Hand = modele.getPlayer1().getHand();
        vue.printCardsPlayer(Hand);
        initButtonCards(Hand);
        // Check if the round was forced to end.
        // If the game is loaded, display the scores and allow the player to start playing.
        if (loadedGame) {
            vue.addMessage("La manche " + modele.getNumeroround() + " reprends, les score sont de : \n", false);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " points ! \n", false);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " points ! \n", false);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " points ! \n", false);
            vue.addMessage("C'est à vous de jouer ! \n", false);
            modele.getPlayer1().myTurn(true);
        }
        // If it's the first round or the round is being started by the player.
        else if (modele.getwhoStart() == 0) {
            vue.addMessage("Début de la manche " + modele.getNumeroround() + " ! Les score sont de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " points ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " points ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " points ! \n", true);
            vue.addMessage("Les participants sont : \n", true);
            for (int i = 0; i < modele.getPlayers().size(); i++) {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("Vous commencez à jouer \n", true);
            vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
            modele.getPlayer1().myTurn(true);
        }
        // If CPU Agro starts.
        else if (modele.getwhoStart() == 1) {
            vue.addMessage("Début de la manche " + modele.getNumeroround() + " ! Les score sont de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " points ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " points ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " points ! \n", true);
            vue.addMessage("Les participants sont : \n", true);
            for (int i = 0; i < modele.getPlayers().size(); i++) {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("CPU Agro commence à jouer ! \n", true);
            vue.addMessage("\nEn attente du joueur Agro\n", true);
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask() {
                @Override
                // This method handles the logic for running the bot action for CPU Agro.
                public void run() {
                    modele.getPlayer2().botAction(getControler());
                    vue.refreshAttacks(modele);
                    vue.refreshBoots(modele);
                    vue.addMessage("\nEn attente du joueur Fast\n", true);
                    Timer chrono = new Timer();
                    chrono.schedule(new TimerTask() {
                        @Override
                        // This method handles the logic for running the bot action for CPU Fast.
                        public void run() {
                            modele.getPlayer3().botAction(getControler());
                            modele.getPlayer1().myTurn(true);
                            vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                            vue.refreshAttacks(modele);
                            vue.refreshBoots(modele);
                        }
                    }, turnDelay);
                }
            }, turnDelay);
        }
        // If CPU Fast starts.
        else if (modele.getwhoStart() == 2) {
            vue.addMessage("Début de la manche " + modele.getNumeroround() + " ! Les score sont de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " points ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " points ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " points ! \n", true);
            vue.addMessage("Les participants sont : \n", true);
            for (int i = 0; i < modele.getPlayers().size(); i++) {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("CPU Fast commence à jouer ! \n", true);
            vue.addMessage("\nEn attente du joueur Fast\n", true);
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask() {
                @Override
                // This method handles the logic for running the bot action for CPU Fast.
                public void run() {
                    modele.getPlayer3().botAction(getControler());
                    modele.getPlayer1().myTurn(true);
                    vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                    vue.refreshAttacks(modele);
                    vue.refreshBoots(modele);
                }
            }, turnDelay);
        }

        vue.getWindow().revalidate();
        vue.getWindow().repaint();
    }

    /**
     * Initializes action buttons for each card in the player's hand. When a card is clicked, the corresponding action is executed 
     * based on the current game state, such as drawing a card, attacking an opponent, or playing a card with special rules.
     * 
     * The method handles the following game logic:
     * - Ensures the player must draw a card before playing if they haven't done so.
     * - Prevents playing cards while attacking or after discarding.
     * - Executes specific card actions like drawing a card after playing a Boot card or attacking an opponent with an Attack card.
     * - Validates whether the card can be played based on the current game state (e.g., checking if the player needs to reach a certain distance to play a card).
     * 
     * @param Hand The list of cards in the player's hand that will be assigned action buttons.
     */
    public void initButtonCards(ArrayList<Card> Hand) {
        for (int i = 0; i < Hand.size(); i++) {
            int j = i;
            vue.addActionButtonCard(new ActionListener() {
                @Override
                // This method handles the logic for action performed when a card is clicked.
                public void actionPerformed(ActionEvent e) {
                    // If it's the player's turn
                    if (modele.getPlayer1().getmyTurn()) {
                        // If the player hasn't drawn yet, they need to draw first.
                        if (!modele.getPlayer1().getHasDraw()) {
                            vue.addMessage("Vous devez d'abord piocher pour jouer une carte \n", false);
                        }
                        // If the player is attacking and has selected the same card again.
                        else if (modele.getPlayer1().getIsAttacking() && modele.getPlayer1().getHand().get(j) == modele.getPlayer1().getIsAttackingWith()) {
                            vue.addMessage("Vous avez changé d'avis \n", false);
                            modele.getPlayer1().getIsAttackingWith().setImageBack();
                            vue.deleteCardsPlayers();
                            vue.printCardsPlayer(Hand);
                            initButtonCards(Hand);
                            modele.getPlayer1().setIsAttacking(false);
                            modele.getPlayer1().setIsAttackingWith(null);
                            modele.getPlayer1().setTarget(null);
                        }
                        // If the player is attacking, they can't play any other cards.
                        else if (modele.getPlayer1().getIsAttacking()) {
                            vue.addMessage("Vous êtes en train d'attaquer, vous ne pouvez pas jouer de carte ! \n", false);
                        }
                        // If the player needs to draw after playing a Boot card.
                        else if (modele.getPlayer1().getMustDraw()) {
                            vue.addMessage("Après avoir joué une botte vous devez piocher\n", false);
                        }
                        // If the player has discarded, they can't play any other cards.
                        else if (modele.getPlayer1().gethasDiscard()) {
                            vue.addMessage("Vous ne pouvez plus jouer après avoir défaussé\n", false);
                        }
                        // If the player already played during their turn.
                        else if (modele.getPlayer1().getHasPlayed() && !(modele.getPlayer1().getHand().get(j) instanceof Boot)) {
                            vue.addMessage("Vous avez déjà joué lors de votre tour \n", false);
                        }
                        // If the player is discarding a card.
                        else if (modele.getPlayer1().getDiscard()) {
                            vue.addMessage(modele.getPlayer1().discard(modele.getPlayer1().getHand().get(j), getControler()), true);
                            modele.getPlayer1().setHasDiscard(true);
                            vue.getDiscard().setText("");
                            modele.getPlayer1().setdiscard(false);
                        }
                        else {
                            // If the card is a Boot, the player needs to draw again.
                            if (modele.getPlayer1().getHand().get(j) instanceof Boot) {
                                modele.getPlayer1().playCard(modele.getPlayer1().getHand().get(j), getControler(), j + 1);
                                vue.addMessage("Vous devez piocher à nouveau ! \n", false);
                                modele.getPlayer1().setmustDraw(true);
                            }
                            // If the card is an Attack, the player needs to choose a target.
                            else if (modele.getPlayer1().getHand().get(j) instanceof Attack) {
                                vue.addMessage("Choisissez le CPU sur lequel vous voulez lancer votre attaque \n", false);
                                modele.getPlayer1().setIsAttacking(true);
                                modele.getPlayer1().setIsAttackingWith(modele.getPlayer1().getHand().get(j));
                                modele.getPlayer1().getHand().get(j).setImageIcon(null);
                                vue.deleteCardsPlayers();
                                vue.printCardsPlayer(Hand);
                                initButtonCards(modele.getPlayer1().getHand());
                            }
                            else {
                                // Check if the card is playable based on the player's current state.
                                if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 0) {
                                    modele.getPlayer1().playCard(Hand.get(j), getControler(), j + 1);
                                }
                                else if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 10) {
                                    vue.addMessage("Vous devez atteindre PILE 700 bornes pour gagner, cette carte vous fait aller trop loin ! \n", false);
                                }
                                else if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 3) {
                                    vue.addMessage("Vous ne pouvez pas avancer sans feu vert ! \n", false);
                                }
                                else if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 4) {
                                    String message = modele.getPlayer1().playDistance(modele.getPlayer1().getHand().get(j));
                                    vue.addMessage(message, false);
                                }
                                else if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 5) {
                                    vue.addMessage("Vous avez déjà un feu vert ! \n", false);
                                }
                                else if (modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 6) {
                                    vue.addMessage("Vous ne subissez aucune attaque que cette parade permet de contrer !\n", false);
                                }
                            }
                        }
                        vue.refreshAttacks(modele);
                        vue.refreshBoots(modele);
                    }
                    // If it's not the player's turn.
                    else {
                        vue.addMessage("Ce n'est pas votre tour ! \n", false);
                    }
                }
            }, i);
        }
        vue.getWindow().setLayout(null);
        vue.getWindow().setVisible(true);

        // Move the cars forward based on the player's distance.
        vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, this);
        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, this);
        vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, this);
    }

    /**
     * Starts a new round in the game.
     * 
     * @param b1 A boolean flag indicating whether to start the game.
     * @param b2 A boolean flag indicating whether the round was forced to stop.
     */
    public void newRound(boolean b1, boolean b2){
        // Reset discard and turn status for player 1
        modele.getPlayer1().setdiscard(false);
        modele.getPlayer1().myTurn(false);

        // Clear and refresh the game window
        vue.getDiscard().setText("");
        vue.getWindow().getContentPane().removeAll();
        vue.getWindow().repaint();
        vue.getWindow().revalidate();
        vue.createWindowGame();
        vue.resettingKilometers();    

        if(b2){
            // If the round was forced to stop by the player
            vue.addMessage("Vous avez arrêté la manche, les points gagnés\nne seront pas comptabilisés \n\n\n", false);
            vue.addMessage("Chargement de la prochaine manche...\n\n\n", false);
            vue.resettingKilometers();
            
            modele.getPlayers().clear();
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
                @Override
                public void run(){
                    newGame(b1, b2);
                }
            }, turnDelay*2);
        }
        else
        {
            modele.countingPts();
            if(modele.getwinnerOfTheGame() != null){// If there's a winner, handle the result
                // If player 1 is the winner
                if(modele.getwinnerOfTheGame().getId() == modele.getPlayer1().getId())
                {
                    int i = 0;
                    vue.addMessage("VOUS AVEZ ACCUMULE 5000 POINTS!!!\n", true);
                    vue.addMessage("VOUS AVEZ GAGNE !\n", true);
                    while(i < 500)
                    {
                        vue.addMessage("VOUS AVEZ ACCUMULE 5000 POINTS!!!\n", false);
                        vue.addMessage("VOUS AVEZ GAGNE !\n", false);
                        i++;
                    }
                }
                else{
                // If CPU is the winner
                vue.addMessage("Le CPU " + modele.getwinnerOfTheGame().getName() + " a accumulé 5000 POINTS...", true);
                vue.addMessage("Vous avez perdu cette Partie...", true);
                }
            
                // Save game history and reset the game
                saveManagement();
                modele = new Game();
                Timer chrono = new Timer();
                chrono.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        vue.clearConsole();
                        newGame(true, false);
                    }
                }, turnDelay);
            }
            else{
                // If there was no winner and the round ended normally
                if(modele.getdraw().size() == 0){
                    vue.addMessage("La pioche s'est vidé :\n", false);
                    vue.addMessage("Le gagnant est donc celui qui est allé le plus loin...\n", false);
                }
    
                // Check if player 1 is the leading player
                if(modele.theLeadingPlayer().getId() == modele.getPlayer1().getId()){
                    vue.addMessage("VOUS AVEZ GAGNE CETTE MANCHE, BRAVO !! \n\n\n\n", false);
                }else{
                    vue.addMessage("Dommage, c'est le CPU " + modele.theLeadingPlayer().getName() + " qui a gagné cette manche, bravo !! \n\n\n\n", false);
                }
                // Start the next round
                vue.addMessage("Chargement de la partie suivante...\n\n\n", false);
                modele.getPlayers().clear();
                Timer chrono = new Timer();
                chrono.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        newGame(true, false);
                    }
                }, turnDelay);
            }

        }
    }

    //setter for the modele 
    //used for reset de modele when saves are deleted
    public void setModele(Game newModele)
    {
        modele = newModele;
    }

    /**
     * Manages the saving of game history.
     * Copies and saves history files from the directory into a new file.
     */
    public void saveManagement() {
        String appPath = System.getProperty("user.dir");
        String directoryPath = appPath + File.separator + "resources" + File.separator +"SauvegardeDesHistoriques";
 
        String outputFile;
        File file;
        int i = 1;
    
        // Créer le dossier s'il n'existe pas
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    
        // Check for available file name by incrementing the index
        file = new File(directoryPath + File.separator + "Partie_" + i + ".txt");
        while(file.exists()) {
            //obtenir chemin du fichier
            System.out.println(file.getAbsolutePath());
            i++;
            file = new File(directoryPath + File.separator + "Partie_" + i + ".txt");
        }
    
        // Create the new file
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        outputFile = directoryPath + File.separator + "Partie_" + i + ".txt";
    
        // Write history to the new file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            // Traverse through the files in the directory
            Files.list(Paths.get(directoryPath))
                .filter(path -> path.getFileName().toString().startsWith("round") && 
                              path.getFileName().toString().endsWith(".txt"))
                .forEach(fileBis -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileBis.toFile()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                        System.out.println("Copié : " + fileBis.getFileName());
                        
                        // Delete the file after copying
                        Files.delete(fileBis);
                        System.out.println("Supprimé : " + fileBis.getFileName());
                    } catch (IOException e) {
                        System.err.println("Erreur avec le file " + fileBis.getFileName() + ": " + e.getMessage());
                    }
                });
    
            System.out.println("Fusion terminée dans le file : " + outputFile);
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
    

    /**
     * Returns the controller instance.
     * 
     * @return The controller instance.
     */
    public Controler getControler(){
        return this;
    }

    /**
     * Returns the current game model.
     * 
     * @return The current game model.
     */
    public Game getModel(){
        return modele;
    }

    /**
     * Returns the current window game view.
     * 
     * @return The current window game view.
     */
    public WindowGame getVue(){
        return vue;
    }

    /**
     * Saves the current game state to a file.
     * 
     * @throws IOException If an I/O error occurs during saving the game.
     */
    private void save() {
        String appPath = System.getProperty("user.dir");
        String directoryPath = appPath + File.separator + "resources" + File.separator + "save.ser";
        File saveFile = new File(directoryPath);
        
        if(!saveFile.exists()){
            System.out.println("Fichier de sauvegarde non trouvé : " + saveFile.getAbsolutePath());
        }
        else{
            try (FileOutputStream file = new FileOutputStream(saveFile, false);
                ObjectOutputStream oos = new ObjectOutputStream(file)) {
                oos.writeObject(modele);
                System.out.println("Sauvegarde OK !");
            } catch (IOException e) {
                System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
            }
        }
    }


    /**
     * Loads a saved game from the given file.
     * 
     * @param file The file from which the game will be loaded.
     * @throws IOException If an I/O error occurs during loading the game.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    private void loadingSave(File file) {
            try (FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {
                modele = (Game)ois.readObject();
                modele.getPlayer1().setdiscard(false);
                vue.getDiscard().setText("");
                modele.getPlayer1().setIsAttacking(false);
                System.out.println(modele.getdraw().size());
                if (modele != null && modele.getPlayer1() != null){
                    System.out.println("Première cartes de la main : " + modele.getPlayer1().getHand().get(0));
                }else{
                    System.err.println("Les données chargées sont incomplètes ou corrompues.");
                }
            }catch(IOException | ClassNotFoundException e){
                System.err.println("Erreur lors du chargement : ");
                e.printStackTrace();
            }
    }

    /**
     * Plays the main music from the sound list based on the given index.
     * 
     * @param i The index of the music to play.
     */
    @SuppressWarnings("unused")
    private void playMainMusic(int i){
        soundList.setFile(i);
        soundList.play(i);
    }

    /**
     * Plays secondary music based on the given index if it's not 0.
     * 
     * @param i The index of the music to play. If 0, no music will be played.
     */
    private void playMusic(int i){
        if(i != 0){
            secondarySoundList.setFile(i);
            secondarySoundList.play(i);
        }
    }

    /**
     * Plays the music continuously in a loop based on the given index.
     * 
     * @param i The index of the music to play in a continuous loop.
     */
    private void playContiniouselyMusic(int i){
        soundList.setFile(i);
        //soundList.play(i);
        soundList.loop();
    }

    /**
     * Stops the currently playing music.
     */
    public void stopMusic(){
        soundList.stop(0);
    }

    /**
     * Initializes the sound button's action and toggles the sound on/off when clicked.
     * It also updates the sound button's image and stops the current music.
     */
    @SuppressWarnings("unused")
    public void initSoundButton(){
        vue.addActionSoundButton(e -> {
            if(secondarySoundList.getSoundON()){
                secondarySoundList.setSoundON(false);
            }else{
                secondarySoundList.setSoundON(true);
            }
            stopMusic();
            vue.changeImageSound();
        });
    }
}