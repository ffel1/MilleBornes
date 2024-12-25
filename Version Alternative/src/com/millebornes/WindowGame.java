package com.millebornes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.print.DocFlavor.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * The WindowGame class manages the main graphical user interface (GUI) for the "1000 Bornes" racing game.
 * It is responsible for displaying the game window, controlling various panels and buttons, and handling 
 * player interactions such as starting the game, quitting, drawing cards, and managing sound settings.
 * 
 * This class also tracks the state of the game, such as the current distance driven by each car, 
 * the turbo status of the cars, and the game's progress (e.g., showing menus, history, etc.).
 * 
 * Key Responsibilities:
 * - **Game Window**: It creates and displays the main game window using JFrame, and adjusts it to fullscreen mode.
 * - **Buttons and Controls**: Initializes and manages various buttons for gameplay (e.g., Start, Quit, Draw Cards, End Turn, New Game).
 * - **Panels**: Manages panels that display different game information and actions, such as car movements, attack moves, and boots for players and CPU.
 * - **Car and Game State**: Tracks the kilometers driven by each car and their turbo status, and provides methods to update the game state.
 * - **Sound Control**: Handles the toggling of sound through a dedicated button.
 * 
 * Methods:
 * - `WindowGame()`: The constructor initializes the main game window, sets up the GUI components, and adjusts for full-screen mode. It also initializes variables tracking the game state (kilometers driven, turbo status, etc.).
 * 
 * Notes:
 * - The game window is designed to be dynamic and can be resized according to the screen's size.
 * - Full-screen mode is enabled by default, ensuring the game occupies the entire screen.
 * - Various panels and buttons are initialized but may be populated and used later in the game logic (e.g., the player's hand of cards, attack moves, boots, etc.).
 * - The class could be expanded with additional game logic, such as managing different game phases, handling events, or updating the car's state based on user input.
 */
public class WindowGame {
    // Boolean variable to control the current animation state (not fully implemented)
    public boolean getCurrentAnimation;

    // Private member variables for various GUI components and game state tracking
    private JFrame windowMenu;           // Main game window
    private int height;                  // Height of the screen
    private int width;                   // Width of the screen
    private int heightCard;              // Height of a card (unused here, could be for card display)
    private JTextArea textArea;          // Area for displaying text messages (e.g., game status)
    private JScrollPane scrollPane;      // ScrollPane for the text area
    private JButton buttonPlay;          // Button for starting the game
    private JButton buttonQuit;          // Button for quitting the game
    private JButton buttonSaves;         // Button for viewing game history
    private JButton returnButton;        // Button to return to the previous screen
    private JButton buttonDraw;          // Button for drawing a card
    private JButton buttonDiscard;       // Button for discarding a card
    private JButton buttonEndOfTurn;     // Button for ending the player's turn
    private JButton buttonNewGame;       // Button for starting a new game
    private JPanel menuPanel;            // Panel for displaying the menu UI
    private JLayeredPane gamePanel;      // Panel for displaying the game UI (layers of components)
    private ArrayList<JButton> buttonHandPlayer; // List of player's hand cards (buttons)
    private GraphicsEnvironment env;     // Graphics environment for screen settings
    private GraphicsDevice screen;       // Device used for full-screen settings
    private JButton ButtonCar1;          // Button for car 1 in the game
    private JButton ButtonCar2;          // Button for car 2 in the game
    private JButton ButtonCar3;          // Button for car 3 in the game
    private int kilometersV1;            // Kilometers driven by car 1
    private int kilometersV2;            // Kilometers driven by car 2
    private int kilometersV3;            // Kilometers driven by car 3
    private ImageIcon circuit;           // Image for the game circuit (track)
    private String nameGame;             // Name of the game
    private JPanel panelAttacksPlayer;   // Panel for displaying player's attack moves
    private JPanel panelAttacksCPUFast;  // Panel for displaying fast CPU's attack moves
    private JPanel panelAttacksCPUAgro;  // Panel for displaying aggressive CPU's attack moves
    private JPanel panelBootsPlayer;     // Panel for displaying player's boots
    private JPanel panelBootsCPUFast;    // Panel for displaying fast CPU's boots
    private JPanel panelBootsCPUAgro;    // Panel for displaying aggressive CPU's boots
    private ImageIcon soundOn;           // Image for the sound on button
    private ImageIcon soundOff;          // Image for the sound off button
    private JButton soundButton;         // Button for toggling sound
    private boolean turboV1;             // Boolean flag for turbo status of car 1
    private boolean turboV2;             // Boolean flag for turbo status of car 2
    private boolean turboV3;             // Boolean flag for turbo status of car 3
    private boolean turbo;               // General turbo status (affects all cars)
    private Controler controler;

    /**
     * Constructor for initializing the game window and GUI components.
     */
    public WindowGame() {
        // Initialize components
        heightCard = 0;
        buttonPlay = new JButton("Jouer");
        buttonQuit = new JButton("Quitter");
        buttonSaves = new JButton("Historique");
        returnButton = new JButton();
        buttonNewGame = new JButton();
        buttonDraw = new JButton();
        buttonDiscard = new JButton();
        buttonEndOfTurn = new JButton();
        soundButton = new JButton("Sound");
        windowMenu = new JFrame("1000 Bornes");
        textArea = new JTextArea("Début de la partie");
        menuPanel = new JPanel();  
        gamePanel = new JLayeredPane();
        buttonHandPlayer = new ArrayList<JButton>();
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screen = env.getDefaultScreenDevice();
        kilometersV1 = 0;
        kilometersV2 = 0;
        kilometersV3 = 0;
        turboV1 = true;
        turboV2 = true;
        turboV3 = true;

        // Get screen size for full-screen display
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        height = (int)dimension.getHeight();
        width = (int)dimension.getWidth();
        windowMenu.setSize(width, height);

        // Enable full-screen mode
        enableFullScreen(windowMenu);
    } 

    /**
     * Returns the main window of the game.
     * 
     * @return The main JFrame of the game window
     */
    public JFrame getWindow() {
        return windowMenu;
    }

    /**
     * Returns the discard button used in the game.
     * 
     * @return The discard JButton for discarding a card
     */
    public JButton getDiscard() {
        return buttonDiscard;
    }

    /**
     * Returns the list of buttons representing the player's hand of cards.
     * 
     * @return An ArrayList of JButton objects representing the player's hand
     */
    public ArrayList<JButton> getButtonHandPlayer() {
        return buttonHandPlayer;
    }

    /**
     * Enables full-screen mode for the game window.
     * 
     * @param winetre The JFrame to be set to full-screen mode
     */
    private void enableFullScreen(JFrame winetre) {
        winetre.dispose();  // Necessary for some modifications to the window
        winetre.setUndecorated(true);  // Removes window borders and title bar
        screen.setFullScreenWindow(winetre);  // Set the window to full-screen mode
    }
    
    /**
     * Connects an action to the "Play" button.
     * 
     * @param action The action to be executed when the "Play" button is clicked
     */
    public void addActionButtonPlay(ActionListener action) {
        buttonPlay.addActionListener(action);
    }

    /**
     * Connects an action to the "Historique" button (History button).
     * 
     * @param action The action to be executed when the "Historique" button is clicked
     */
    public void addButtonActionSaves(ActionListener action) {
        buttonSaves.addActionListener(action);
    }

    /**
     * Connects an action to the "Quitter" button (Quit button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionQuitButton(ActionListener action){
        buttonQuit.addActionListener(action);
    }

    /**
     * Connects an action to the "Retour menu" button (Return to menu button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionReturnButton(ActionListener action){
        returnButton.addActionListener(action);
    }

    /**
     * Connects an action to the "Nouvelle Game" button (New Game button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionButtonNewGame(ActionListener action){
        buttonNewGame.addActionListener(action);
    }

    /**
     * Connects an action to a specific card button in the player's hand.
     * @param action The action to execute when the card button is clicked
     * @param i The index of the card button in the player's hand
     */
    public void addActionButtonCard(ActionListener action, int i){
        buttonHandPlayer.get(i).addActionListener(action);
    }

    /**
     * Connects an action to the "draw" button (Draw card button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionButtonDraw(ActionListener action){
        buttonDraw.addActionListener(action);
    }

    /**
     * Connects an action to the "Défausse" button (Discard card button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionButtonDiscard(ActionListener action){
        buttonDiscard.addActionListener(action);
    }

    /**
     * Connects an action to the "ButtonCPUAgro" button (CPU Aggressive car button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionButtonCPUAgro(ActionListener action){
        ButtonCar2.addActionListener(action);
    }

    /**
     * Connects an action to the "ButtonCPUFast" button (CPU Fast car button).
     * @param action The action to execute when the button is clicked
     */
    public void addActionButtonCPUFast(ActionListener action){
        ButtonCar3.addActionListener(action);
    }

    /**
     * Connects an action to the "End of Turn" button.
     * @param action The action to execute when the button is clicked
     */
    public void AddActionButtonEndOfMyTurn(ActionListener action){
        buttonEndOfTurn.addActionListener(action);
    }

    /**
     * Connects an action to the "Sound" button (toggle sound on/off).
     * @param action The action to execute when the button is clicked
     */
    public void addActionSoundButton(ActionListener action){
        soundButton.addActionListener(action);
    }

    /**
     * Creates and displays the main menu window with buttons for starting the game,
     * viewing history, and quitting, as well as the game logo.
     * This method also sets up the layout of the menu and positions components.
     */
    public void createWindowMenu(){
        // Initialize menuPanel and set the layout manager to GridBagLayout
        menuPanel.setBounds(0, 0, width, height);
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // Configure and add the "Play" button
        buttonPlay.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // Set size as percentage of screen
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(50, 0, 50, 0); // Set spacing between components
        grid.anchor = GridBagConstraints.CENTER; // Center the button
        menuPanel.add(buttonPlay, grid);

        // Add the game logo (image)
        ImageIcon image = new ImageIcon(getClass().getResource("/Images/MilleBornes.png"));
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER); // Center the image horizontally
        labelImage.setVerticalAlignment(JLabel.CENTER);   // Center the image vertically
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(50, 0, 50, 0); // Set spacing between components
        grid.anchor = GridBagConstraints.CENTER; // Center the image
        menuPanel.add(labelImage, grid);


        // Configure and add the "Historique" button (History button)
        buttonSaves.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // Set size as percentage of screen
        grid.gridx = 0;
        grid.gridy = 2;
        grid.anchor = GridBagConstraints.CENTER; // Center the button
        menuPanel.add(buttonSaves, grid);

        // Configure and add the "Quitter" button (Quit button)
        buttonQuit.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // Set size as percentage of screen
        grid.gridx = 0; 
        grid.gridy = 3; 
        grid.anchor = GridBagConstraints.CENTER; // Center the button
        menuPanel.add(buttonQuit, grid);

        // Set the content pane of the window to the menuPanel and make it visible
        windowMenu.setContentPane(menuPanel);
        windowMenu.setVisible(true);    
    }

    /**
     * Displays the game window with all game elements such as the circuit image,
     * buttons, and interactive components.
     */
    public void createWindowGame() {
        // Set the content pane to the game panel and refresh the window
        windowMenu.setContentPane(gamePanel);
        windowMenu.revalidate();
        windowMenu.repaint();

        // Initialize and display attack and boot elements
        createDisplayAttacks();
        createDisplayBoots();

        // Load and display the circuit background image
        circuit = new ImageIcon(getClass().getResource("/Images/circuit.png"));
        Image resizedImage = circuit.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        circuit = new ImageIcon(resizedImage);
        JLabel labelCircuit = new JLabel();
        labelCircuit.setIcon(circuit);
        labelCircuit.setVerticalAlignment(JLabel.CENTER);
        labelCircuit.setHorizontalAlignment(JLabel.CENTER);
        labelCircuit.setBounds(0, 0, circuit.getIconWidth(), circuit.getIconHeight());
        gamePanel.add(labelCircuit, Integer.valueOf(1));

        // Display additional game elements such as text areas and cars
        printTextArea();
        printCars(circuit);

        // Add the "Return to Main Menu" button
        returnButton.setBounds(width - (width * 11 / 100), height - height * 9 / 100, width * 5 / 100, height * 5 / 100);
        returnButton.setContentAreaFilled(false);
        gamePanel.add(returnButton, Integer.valueOf(2));
        if (returnButton.getActionListeners().length == 1) {
            returnButton.removeActionListener(returnButton.getActionListeners()[0]);
        }

        // Add the "End My Turn" button
        buttonEndOfTurn.setBounds(width / 2 + width * 355 / 1000, height / 2 + height * 175 / 1000, width * 6 / 100, height * 7 / 100);
        buttonEndOfTurn.setContentAreaFilled(false);
        gamePanel.add(buttonEndOfTurn, Integer.valueOf(2));
        if (buttonEndOfTurn.getActionListeners().length == 1) {
            buttonEndOfTurn.removeActionListener(buttonEndOfTurn.getActionListeners()[0]);
        }

        // Add the "New Game" button
        buttonNewGame.setBounds(width - (width * 20 / 100), height - height * 9 / 100, width * 5 / 100, height * 5 / 100);
        buttonNewGame.setContentAreaFilled(false);
        gamePanel.add(buttonNewGame, Integer.valueOf(2));
        if (buttonNewGame.getActionListeners().length == 1) {
            buttonNewGame.removeActionListener(buttonNewGame.getActionListeners()[0]);
        }

        // Add the "Draw Card" button
        buttonDraw.setBounds(width / 2 - width * 96 / 1000, height / 2 - height * 5 / 100, (circuit.getIconWidth() * 6 / 100), (circuit.getIconHeight() * 7 / 100));
        buttonDraw.setContentAreaFilled(false);
        gamePanel.add(buttonDraw, Integer.valueOf(2));
        if (buttonDraw.getActionListeners().length == 1) {
            buttonDraw.removeActionListener(buttonDraw.getActionListeners()[0]);
        }

        // Add the "Discard Card" button
        buttonDiscard.setBounds(width / 2 + width * 42 / 1000, height / 2 - height * 4 / 100, (circuit.getIconWidth() * 5 / 100), (circuit.getIconHeight() * 5 / 100));
        buttonDiscard.setContentAreaFilled(false);
        gamePanel.add(buttonDiscard, Integer.valueOf(5));
        if (buttonDiscard.getActionListeners().length == 1) {
            buttonDiscard.removeActionListener(buttonDiscard.getActionListeners()[0]);
        }

        // Add the "Sound Toggle" button with appropriate icons
        soundOn = new ImageIcon(getClass().getResource("/Images/SonON.png"));
        Image resizedSoundOnImage = soundOn.getImage().getScaledInstance(width * 27 / 1000, height * 50 / 1000, Image.SCALE_SMOOTH);
        soundOn = new ImageIcon(resizedSoundOnImage);
        soundOff = new ImageIcon(getClass().getResource("/Images/SonOFF.png"));
        Image resizedSoundOffImage = soundOff.getImage().getScaledInstance(width * 27 / 1000, height * 50 / 1000, Image.SCALE_SMOOTH);
        soundOff = new ImageIcon(resizedSoundOffImage);
        soundButton.setIcon(soundOn);
        soundButton.setBounds(width - (soundOn.getIconWidth() * 135 / 100), height - (soundOn.getIconHeight() * 180 / 100), soundOn.getIconWidth(), soundOn.getIconHeight());
        soundButton.setFocusPainted(false);
        soundButton.setContentAreaFilled(false);
        soundButton.setOpaque(false);
        gamePanel.add(soundButton, Integer.valueOf(10));
        if (soundButton.getActionListeners().length == 1) {
            soundButton.removeActionListener(soundButton.getActionListeners()[0]);
        }

        // Finalize the window layout and make it visible
        windowMenu.setLayout(null);
        windowMenu.setVisible(true);
    }

    /**
     * Toggles the sound button icon between "sound on" and "sound off."
     */
    public void changeImageSound() {
        if (soundButton.getIcon().equals(soundOn)) {
            soundButton.setIcon(soundOff);  
        } else {
            soundButton.setIcon(soundOn);
        }
    }

    /**
     * Displays the save files window, allowing users to view and interact with game saves.
     * Creates buttons dynamically for each existing save file and round file.
     */
    public void createWindowSaves() {
        JPanel panelHistorique = new JPanel();
        panelHistorique.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        
        // Utiliser getResource pour accéder aux fichiers dans le JAR
        String savePath = System.getProperty("user.home") + File.separator + "SauvegardeDesHistoriques";
        File saveDirectory = new File(savePath);
        
        // Créer le répertoire s'il n'existe pas
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }
        
        File round, Game;
        int i = 1;
    
        // Dynamically add buttons for game save files
        Game = new File(saveDirectory, "Partie_" + i + ".txt");
        while (Game.exists()) {
            final String path = Game.getAbsolutePath(); // Utiliser le chemin absolu
            JButton Buttonfile = new JButton("Partie " + i);
            Buttonfile.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100));
            Buttonfile.addActionListener(e -> printContentFile(path));
            grid.gridx = 1;
            grid.gridy = i;
            grid.insets = new Insets(40, 40, 40, 40);
            grid.anchor = GridBagConstraints.CENTER;
            panelHistorique.add(Buttonfile, grid);
            i++;
            Game = new File(saveDirectory, "Partie_" + i + ".txt");
        }
    
        // Similar handling for round files
        int j = 1;
        round = new File(saveDirectory, "round_" + j + ".txt");
        while (round.exists()) {
            final String path = round.getAbsolutePath(); // Utiliser le chemin absolu
            JButton Buttonfile = new JButton("round " + j);
            Buttonfile.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100));
            Buttonfile.addActionListener(e -> printContentFile(path));
            grid.gridx = 1;
            grid.gridy = i;
            grid.insets = new Insets(40, 40, 40, 40);
            grid.anchor = GridBagConstraints.CENTER;
            panelHistorique.add(Buttonfile, grid);
            j++;
            i++;
            round = new File(saveDirectory, "round_" + j + ".txt");
        }
    
        // Reste du code inchangé
        JButton ButtonDelete = new JButton("Supprimer historique");
        ButtonDelete.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100));
        ButtonDelete.addActionListener(e -> deleteHistory());
        grid.gridx = 0;
        grid.gridy = i;
        grid.insets = new Insets(20, 0, 10, 0);
        grid.anchor = GridBagConstraints.CENTER;
        panelHistorique.add(ButtonDelete, grid);
    
        JButton returnButtonMenu = new JButton("Menu Principal");
        returnButtonMenu.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100));
        returnButtonMenu.addActionListener(e -> createWindowMenu());
        grid.gridx = 2;
        grid.gridy = i;
        grid.insets = new Insets(20, 0, 10, 0);
        grid.anchor = GridBagConstraints.CENTER;
        panelHistorique.add(returnButtonMenu, grid);
    
        JScrollPane scrollPane = new JScrollPane(panelHistorique);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
    
        windowMenu.setContentPane(scrollPane);
        windowMenu.revalidate();
        windowMenu.repaint();
    }
    
    
    /**
    * Displays the content of a text file in a new window.
    * @param cheminfile Path to the file to be displayed.
    */
    private void printContentFile(String cheminfile) {
        JPanel panelPrint = new JPanel();
        panelPrint.setLayout(new GridBagLayout()); 
        GridBagConstraints grid = new GridBagConstraints();

        // Text area to display the file's content
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        // Adding a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(width * 95 / 100, height * 75 / 100)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.CENTER;
        panelPrint.add(scrollPane, grid);

        // Reading the file and displaying its content
        try (BufferedReader read = new BufferedReader(new FileReader(cheminfile))) {
            String line;
            while ((line = read.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Error reading the file.");
        }

        // Button to return to the save menu
        JButton returnButtonMenu = new JButton("History menu");
        returnButtonMenu.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); 
        returnButtonMenu.addActionListener(e -> createWindowSaves());
        grid.gridx = 0;
        grid.gridy = 1; 
        grid.insets = new Insets(20, 0, 10, 0); 
        grid.anchor = GridBagConstraints.CENTER;

        panelPrint.add(returnButtonMenu, grid);

        // Adding the panel to the main window
        windowMenu.setContentPane(panelPrint);
        windowMenu.revalidate();
        windowMenu.repaint();
    }

    public void setControler(Controler controler){
        this.controler = controler;
    }

    /**
    * Deletes all history files in the save directory.
    */
    private void deleteHistory() {
        controler.setModele(new Game());
        
        // Obtenir le chemin du dossier de l'application
        String appPath = System.getProperty("user.dir");
        
        // Création des chemins avec le dossier de l'application
        File directory = new File(appPath + File.separator + "SauvegardeDesHistoriques");
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) {
                        file.delete();
                    }
                }
            }
        }
    
        // Suppression du fichier de sauvegarde avec le chemin complet
        File fileSaves = new File(appPath + File.separator + "save.ser");
        if (fileSaves.exists()) {
            fileSaves.delete();
        }
    
        // Retour au menu des sauvegardes
        createWindowSaves();
    }
    

    /**
    * Displays the player's hand cards on the game panel.
    * @param Hand List of the player's cards.
    */
    public void printCardsPlayer(ArrayList<Card> Hand) {
        if (heightCard == 0) {
            heightCard = Hand.get(0).getImage().getIconHeight();
        }

        // Adds each card as an interactive button
        for (int i = 0; i < Hand.size(); i++) {
            Card card = Hand.get(i);
            ImageIcon image = card.getImage();
            int larg = width * 61 / 1000; 
            int y = height * 845 / 1000;
            int x = (width / 2 - width * 735 / 10000) - (larg * 3) + (larg * i);
            int hight = (height * 20 / 100);
            JButton Button;

            // Creates a button for each card
            if (image != null) {
                Button = new JButton(null, image);
            } else {
                Button = new JButton("Cancel");
            }
            Button.setIcon(image);
            Button.setBackground(Color.PINK);
            Button.setBounds(x, y, larg * 60 / 100, hight * 49 / 100);
            Button.setFocusPainted(false);
            Button.setContentAreaFilled(false);
            Button.setVisible(true);
            gamePanel.add(Button, Integer.valueOf(3));
            buttonHandPlayer.add(i, Button);
        }
    }

    /**
    * Clears all the cards displayed in the player's hand.
    */
    public void deleteCardsPlayers() {
        for (int j = 0; j < buttonHandPlayer.size(); j++) {
            buttonHandPlayer.get(j).setIcon(null);
            buttonHandPlayer.get(j).setVisible(false);
        }
    }

    /**
    * Indicates if an animation is currently running.
    * @return true if an animation is running, otherwise false.
    */
    public boolean getCurrentAnimation() {
        return getCurrentAnimation;
    }

    /**
    * Initializes panels to display attacks for players and opponents.
    */
    public void createDisplayAttacks() {
        // Global panel for the player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);

        panelAttacksPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksPlayer.setOpaque(false);
        panelGlobalPlayer.add(panelAttacksPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 620 / 10000, height * 12 / 100, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // Global panel for the fast CPU opponent
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);

        panelAttacksCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUFast.setOpaque(false);
        panelGlobalCPUFast.add(panelAttacksCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 620 / 10000, height * 439 / 1000, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // Global panel for the aggressive CPU opponent
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);

        panelAttacksCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUAgro.setOpaque(false);
        panelGlobalCPUAgro.add(panelAttacksCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 620 / 10000, height * 2795 / 10000, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));
    }


    /**
    * Initializes panels to display boots for players and CPUs.
    */
    public void createDisplayBoots() {
        // Global panel for the player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);

        panelBootsPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsPlayer.setOpaque(false);
        panelGlobalPlayer.add(panelBootsPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 8585 / 10000, height * 12 / 100, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // Global panel for the fast CPU
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);

        panelBootsCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUFast.setOpaque(false);
        panelGlobalCPUFast.add(panelBootsCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 8585 / 10000, height * 439 / 1000, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // Global panel for the aggressive CPU
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);

        panelBootsCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUAgro.setOpaque(false);
        panelGlobalCPUAgro.add(panelBootsCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 8585 / 10000, height * 2795 / 10000, width * 20 / 100, height * 20 / 100);
        gamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));
    }

    /**
    * Clears and updates the panels displaying attacks for all participants.
    * @param Game The current game state containing player and CPU data.
    */
    public void refreshAttacks(Game Game) {
        // Clear the old cards from the panels
        panelAttacksPlayer.removeAll();
        panelAttacksCPUFast.removeAll();
        panelAttacksCPUAgro.removeAll();

        // Add the new cards for each participant
        addAttacksForPlayer(Game.getPlayer1(), panelAttacksPlayer);
        addAttacksForPlayer(Game.getPlayer2(), panelAttacksCPUFast);
        addAttacksForPlayer(Game.getPlayer3(), panelAttacksCPUAgro);

        // Refresh the panels
        panelAttacksPlayer.revalidate();
        panelAttacksPlayer.repaint();
        panelAttacksCPUFast.revalidate();
        panelAttacksCPUFast.repaint();
        panelAttacksCPUAgro.revalidate();
        panelAttacksCPUAgro.repaint();
    }

    /**
    * Clears and updates the panels displaying boots for all participants.
    * @param Game The current game state containing player and CPU data.
    */
    public void refreshBoots(Game Game) {
        // Clear the old cards from the panels
        panelBootsPlayer.removeAll();
        panelBootsCPUFast.removeAll();
        panelBootsCPUAgro.removeAll();

        // Add the new cards for each participant
        addBootsForPlayer(Game.getPlayer1(), panelBootsPlayer);
        addBootsForPlayer(Game.getPlayer2(), panelBootsCPUFast);
        addBootsForPlayer(Game.getPlayer3(), panelBootsCPUAgro);

        // Refresh the panels
        panelBootsPlayer.revalidate();
        panelBootsPlayer.repaint();
        panelBootsCPUFast.revalidate();
        panelBootsCPUFast.repaint();
        panelBootsCPUAgro.revalidate();
        panelBootsCPUAgro.repaint();
    }

    /**
    * Adds the player's attack cards to the specified panel.
    * @param player The player whose attacks are being displayed.
    * @param panel The panel to which the attacks will be added.
    */
    private void addAttacksForPlayer(Player player, JPanel panel) {
        if (player == null || player.getCurrentAttacks().size() == 0) {
            return;
        }

        // Scale and add each card to the panel
        for (Card card : player.getCurrentAttacks()) {
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(
                width * 25 / 1000, height * 7 / 100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // Add the card to the panel
        }
    }

    /**
    * Adds the player's boots cards to the specified panel.
    * @param player The player whose boots are being displayed.
    * @param panel The panel to which the boots will be added.
    */
    private void addBootsForPlayer(Player player, JPanel panel) {
        if (player == null || player.getPlayedBoots().size() == 0) {
            return;
        }

        // Scale and add each card to the panel
        for (Card card : player.getPlayedBoots()) {
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(
                width * 25 / 1000, height * 7 / 100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // Add the card to the panel
        }
    }

    /**
    * Retrieves the list of buttons representing the player's hand.
    * @return A list of JButton objects representing the player's hand.
    */
    public ArrayList<JButton> getButtonHandsPlayers() {
        return buttonHandPlayer;
    }

    /**
    * Initializes a transparent text area for displaying messages.
    */
    private void printTextArea() {
        // Panel to display messages
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false); // Make the panel transparent
        messagePanel.setBounds(42, height * 62 / 100, width * 15 / 100, height * 30 / 100);
        messagePanel.setLayout(new BorderLayout());
        gamePanel.add(messagePanel, Integer.valueOf(10));

        // Configure the text area
        textArea.setEditable(false);
        textArea.setOpaque(false); // Make the text area transparent
        textArea.setForeground(Color.BLACK); // Set text color

        // Configure the scroll pane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false); // Make the scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make the viewport transparent
        scrollPane.setBorder(null); // Remove border if necessary
        scrollPane.setBounds(0, height * 50 / 100, width * 15 / 100, height * 49 / 100);

        // Add components
        messagePanel.add(scrollPane);
        textArea.setText("");
    }

    /**
    * Sets the name of the game.
    * @param nameGame The name of the game.
    */
    public void setNameGame(String nameGame) {
        this.nameGame = nameGame;
    }

    /**
    * Clears all text from the message display area.
    */
    public void clearConsole() {
        textArea.setText("");
    }

    /**
    * Adds a message to the text area and optionally appends it to the game history file.
    * @param message The message to be added.
    * @param saveToHistory Boolean indicating if the message should be saved to the game history.
    */
    public void addMessage(String message, boolean saveToHistory) {
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    
        if (saveToHistory) {
            String appPath = System.getProperty("user.dir");
            File directory = new File(appPath + File.separator + "SauvegardeDesHistoriques");
            
            // Créer le dossier s'il n'existe pas
            if (!directory.exists()) {
                directory.mkdirs();
            }
    
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(
                    appPath + File.separator + "SauvegardeDesHistoriques" + File.separator + nameGame, true))) {
                if (message != null) {
                    writer.write(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    /**
    * Reads the content of the game's history file and appends it to the text area.
    */
    public void loadLogs() {
    String appPath = System.getProperty("user.dir");
    Path logPath = Paths.get(appPath, "SauvegardeDesHistoriques", nameGame);
    
    try {
        if (Files.exists(logPath)) {
            String content = new String(Files.readAllBytes(logPath));
            addMessage(content, false);
        }
    } catch (IOException e) {
        System.out.println("Error reading the file. (loadLogs)");
        e.printStackTrace();
    }
}


    /**
    * Creates and places car buttons on the circuit image, representing players' positions.
    * @param circuit The background image of the circuit.
    */
    private void printCars(ImageIcon circuit) {
        // Car 1
        ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture rouge idle haut.gif"));
        ButtonCar1 = new JButton("", Car1);
        ButtonCar1.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar1.setFocusPainted(false);
        ButtonCar1.setContentAreaFilled(false);
        ButtonCar1.setOpaque(false);
        ButtonCar1.setBounds(
            (circuit.getIconWidth() * 705 / 1000),
            (circuit.getIconHeight() * 62 / 100),
            (Car1.getIconWidth() * 10 / 100),
            (Car1.getIconHeight() * 60 / 100)
        );
        gamePanel.add(ButtonCar1, Integer.valueOf(2));
    
        // Car 2
        ImageIcon Car2 = new ImageIcon(getClass().getResource("/Images/voiture bleue idle haut.gif"));
        ButtonCar2 = new JButton("", Car2);
        ButtonCar2.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar2.setFocusPainted(false);
        ButtonCar2.setContentAreaFilled(false);
        ButtonCar2.setOpaque(false);
        ButtonCar2.setBounds(
            (circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000),
            (circuit.getIconHeight() * 62 / 100),
            (Car2.getIconWidth() * 10 / 100),
            (Car1.getIconHeight() * 60 / 100)
        );
        gamePanel.add(ButtonCar2, Integer.valueOf(2));
    
        // Car 3
        ImageIcon Car3 = new ImageIcon(getClass().getResource("/Images/voiture verte idle haut.gif"));
        ButtonCar3 = new JButton("", Car3);
        ButtonCar3.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar3.setFocusPainted(false);
        ButtonCar3.setContentAreaFilled(false);
        ButtonCar3.setOpaque(false);
        ButtonCar3.setBounds(
            (circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000) * 2,
            (circuit.getIconHeight() * 62 / 100),
            (Car3.getIconWidth() * 10 / 100),
            (Car1.getIconHeight() * 60 / 100)
        );
        gamePanel.add(ButtonCar3, Integer.valueOf(2));
    }
    

    /**
    * Resets the kilometers for all cars to zero.
    */
    public void resettingKilometers() {
        kilometersV1 = 0;
        kilometersV2 = 0;
        kilometersV3 = 0;
    }


    /**
     * Moves the car forward based on the total distance of the player.
     * Handles movement logic for a specific player based on their ID.
     * 
     * @param distance The distance the car needs to travel.
     * @param player The ID of the player (0, 1, or 2).
     * @param control The controller managing the game logic and sounds.
     */
    public void moveForwadCar(int distance, int player, Controler control){
        int vitesse = 5; // Speed of the car
        JButton Car; // Represents the car's button
        int newDistance = distance; // Adjusted distance to be calculated
        String color; // Color of the car
        int kilo = 0; // Distance already traveled by the car
        boolean zero = false; // Flag to check if the car has not started moving yet

        // Determine the car's attributes based on the player ID
        if(player == 0){
            Car = ButtonCar1; 
            color = "rouge"; // Red car
            kilo = kilometersV1;
            turbo = turboV1;
            if(distance <= 175 && kilometersV1 != 0){
                newDistance -= kilometersV1 - 25; // Calculate adjusted distance
            } else if(kilometersV1 == 0){
                zero = true; // The car has not started yet
            }
        } else if(player == 1){
            Car = ButtonCar2; 
            color = "bleue"; // Blue car
            kilo = kilometersV2;
            turbo = turboV2;
            if(distance < 175 && kilometersV2 != 0){
                newDistance -= kilometersV2 - 25; // Calculate adjusted distance
            } else if(kilometersV2 == 0){
                zero = true; // The car has not started yet
            }
        } else {
            Car = ButtonCar3; 
            color = "verte"; // Green car
            kilo = kilometersV3;
            turbo = turboV3;
            if(distance < 175 && kilometersV3 != 0){
                newDistance -= kilometersV3 - 25; // Calculate adjusted distance
            } else if(kilometersV3 == 0){
                zero = true; // The car has not started yet
            }
        }
        
        // Check if the car needs to move forward based on the last movement
        if((player == 0 && distance > kilometersV1) | (player == 1 && distance > kilometersV2) | (player == 2 && distance > kilometersV3)){
            Rectangle position = Car.getBounds(); // Get the current position of the car
            int movementY = 0; // Vertical movement of the car
            int percentageX = 0; // Horizontal movement of the car
            
            // Calculate the final position of the car
            if(distance <= 25){
                movementY = (circuit.getIconHeight() * 9 / 100); // Small vertical movement
                position.setBounds((int)position.getX(), (int)position.getY() - movementY, 1, 1);
            } else if(Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle haut.gif").toString())){ 
                // Between 50 and 150 km, car moves upward
                movementY = (((newDistance - 25) / 25)) * (circuit.getIconHeight() * 7 / 100);
                if(zero){
                    movementY += (circuit.getIconHeight() * 9 / 100); // Adjust movement if car starts from zero
                }
                position.setBounds((int)position.getX(), (int)position.getY() - movementY, 1, 1);
            } else if(Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle gauche.gif").toString()) && distance > 175){
                // Between 175 and 375 km, car moves to the left
                percentageX = ((newDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() - percentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            } else if(Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle bas.gif").toString()) && distance > 400){
                // Between 400 and 525 km, car moves downward
                movementY = (((newDistance - kilo) / 25)) * (circuit.getIconHeight() * 7 / 100);
                position.setBounds((int)position.getX(), (int)position.getY() + movementY, (int)position.getWidth(), (int)position.getHeight());
            } else if(Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle droite.gif").toString()) && distance > 550){
                // Between 550 and 700 km, car moves to the right
                percentageX = ((newDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() + percentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }

            // Move the car based on the calculated direction
            if(Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle haut.gif").toString())){
                // Car starts moving upward
                ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " démarrage haut.gif"));
                Car.setIcon(Car1);
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " roule haut.gif"));
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { 
                            // Updates every 10 milliseconds
                            int x = Car.getX();
                            int y = Car.getY();
                            @Override
                            public void actionPerformed(ActionEvent e){
                                // Update the coordinates
                                y -= vitesse;
                                Car.setLocation(x, y);
                        
                                if(distance >= 175 && (Car.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() - ((Car.getIcon().getIconHeight() * 50 / 100) * player) | y < (circuit.getIconHeight() * 125 / 1000) - player * (circuit.getIconHeight() * 50 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle haut.gif"));
                                    
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = 175;
                                        turboV1 = false;
                                    } else if(player == 1){
                                        kilometersV2 = 175;
                                        turboV2 = false;
                                    } else {
                                        kilometersV3 = 175;
                                        turboV3 = false;
                                    }
                                    if(distance >= 175){ 
                                        // Turn left after reaching a distance of 175
                                        turbo = false;
                                        Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " tourne haut vers gauche.gif"));
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 64 / 100) + (circuit.getIconWidth() * 2 / 100) * player, 
                                                      (circuit.getIconHeight() * 7 / 100) - (circuit.getIconHeight() * 4 / 100) * player, 
                                                      (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        
                                        java.util.Timer chrono = new java.util.Timer();
                                        chrono.schedule(new TimerTask() {
                                            @Override
                                            public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                                ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle gauche.gif"));
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 66 / 100) + (circuit.getIconWidth() * 3 / 100) * player, 
                                                              (circuit.getIconHeight() * 14 / 100) - (circuit.getIconHeight() * 5 / 100) * player, 
                                                              (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { 
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    @Override
                                                    public void actionPerformed(ActionEvent e){
                                                        // Update the coordinates
                                                        x -= vitesse;
                                                        Car.setLocation(x, y);
                                                        if(x < (circuit.getIconWidth() * 64 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();  
                                            }
                                        }, 700);
                                    }
                                } else if(distance < 175 && (Car.getY() <= (int)position.getY())){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle haut.gif"));
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    } else if(player == 1){
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    } else {
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, 2500);
                
            }else if (Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle gauche.gif").toString())){
                // Car starting left
                int temps = 0;
                if (turbo) {
                    // Change to "starting left" animation if turbo mode is activated
                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " démarrage gauche.gif"));
                    Car.setIcon(Car1);
                    temps = 2500; // Turbo animation duration
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // TimerTask to handle switching from "starting" to "moving" animation
                    public void run() {
                        ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " roule gauche.gif"));
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Updates every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            @Override
                            // Timer to update the car's position when moving left
                            public void actionPerformed(ActionEvent e) {
                                x -= vitesse; // Update x-coordinate
                                Car.setLocation(x, y);
                                if (distance >= 400 && (Car.getX() + (circuit.getIconWidth() * 0 / 1300) <= (int) position.getX() - ((circuit.getIconWidth() * 50 / 100) * player)
                                        || x < (circuit.getIconWidth() * 300 / 1000) - player * (circuit.getIconWidth() * 40 / 1000))) {
                                    // Stop the timer and reset the car to idle state
                                    ((javax.swing.Timer) e.getSource()).stop();
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle gauche.gif"));
                                    Car.setIcon(Car1);
                                    if (player == 0) {
                                        kilometersV1 = 400;
                                        turboV1 = false;
                                    } else if (player == 1) {
                                        kilometersV2 = 400;
                                        turboV2 = false;
                                    } else {
                                        kilometersV3 = 400;
                                        turboV3 = false;
                                    }
                                    if (distance >= 400) { // Turn downwards
                                        Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " tourne gauche vers bas.gif"));
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 23 / 100) - (circuit.getIconWidth() * 2 / 100) * player,
                                                (circuit.getIconHeight() * 60 / 1000) - (circuit.getIconHeight() * 40 / 1000) * player,
                                                (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
                                        chrono.schedule(new TimerTask() {
                                            @Override
                                            // TimerTask to handle turning downwards animation
                                            public void run() {
                                                ((javax.swing.Timer) e.getSource()).stop();
                                                ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle bas.gif"));
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 2 / 100) * player,
                                                        (circuit.getIconHeight() * 80 / 1000) - (circuit.getIconHeight() * 3 / 100) * player,
                                                        (circuit.getIconWidth() * 3 / 100), (Car1.getIconHeight() * 50 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Updates every 10 ms
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    @Override
                                                    // Timer to update the car's position when moving downwards
                                                    public void actionPerformed(ActionEvent e) {
                                                        y += vitesse; // Update y-coordinate
                                                        Car.setLocation(x, y);
                                                        if (y > (circuit.getIconHeight() * 14 / 100)) {
                                                            ((javax.swing.Timer) e.getSource()).stop();
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();
                                            }
                                        }, 700); // Delay before changing state
                                    }
                                } else if (distance < 400 && (Car.getX() <= (int) position.getX())) {
                                    // Stop the timer and reset to idle state when distance is not sufficient
                                    ((javax.swing.Timer) e.getSource()).stop();
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle gauche.gif"));
                                    Car.setIcon(Car1);
                                    if (player == 0) {
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    } else if (player == 1) {
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    } else {
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, temps); // Delay for turbo animation if applicable
            } else if (Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle bas.gif").toString())) {
                // Car starting downwards
                int temps = 0;
                if (turbo) {
                    // Change to "starting down" animation if turbo mode is activated
                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " démarrage bas.gif"));
                    Car.setIcon(Car1);
                    temps = 2500; // Turbo animation duration
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // TimerTask to handle switching from "starting" to "moving" animation
                    public void run() {
                        ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " roule bas.gif"));
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Updates every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            @Override
                            // Timer to update the car's position when moving downwards
                            public void actionPerformed(ActionEvent e) {
                                y += vitesse; // Update y-coordinate
                                Car.setLocation(x, y);

                                if (distance >= 550 && y > (circuit.getIconHeight() * 580 / 1000) + player * (circuit.getIconHeight() * 45 / 1000)) {
                                    // Stop the timer and reset the car to idle state
                                    ((javax.swing.Timer) e.getSource()).stop();
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle bas.gif"));
                                    Car.setIcon(Car1);
                                    if (player == 0) {
                                        kilometersV1 = 550;
                                        turboV1 = false;
                                    } else if (player == 1) {
                                        kilometersV2 = 550;
                                        turboV2 = false;
                                    } else {
                                        kilometersV3 = 550;
                                        turboV3 = false;
                                    }
                                    if (distance >= 550) { // Turn to the right
                                        Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " tourne bas vers droite.gif"));
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 22 / 100) - (circuit.getIconWidth() * 2 / 100) * player,
                                                (circuit.getIconHeight() * 530 / 1000) + (circuit.getIconHeight() * 4 / 100) * player,
                                                (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
                                        chrono.schedule(new TimerTask() {
                                            @Override
                                            // TimerTask to handle turning to the right animation
                                            public void run() {
                                                ((javax.swing.Timer) e.getSource()).stop();
                                                ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle droite.gif"));
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 3 / 100) * player,
                                                        (circuit.getIconHeight() * 610 / 1000) + (circuit.getIconHeight() * 45 / 1000) * player,
                                                        (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Updates every 10 ms
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    @Override
                                                    // Timer to update the car's position when moving right
                                                    public void actionPerformed(ActionEvent e) {
                                                        x += vitesse; // Update x-coordinate
                                                        Car.setLocation(x, y);
                                                        if (x > (circuit.getIconWidth() * 29 / 100)) {
                                                            ((javax.swing.Timer) e.getSource()).stop();
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();
                                            }
                                        }, 700); // Delay before changing state
                                    }
                                } else if (distance < 550 && (Car.getY() > (int) position.getY())) {
                                    // Stop the timer and reset to idle state when distance is not sufficient
                                    ((javax.swing.Timer) e.getSource()).stop();
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle bas.gif"));
                                    Car.setIcon(Car1);
                                    if (player == 0) {
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    } else if (player == 1) {
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    } else {
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, temps); // Delay for turbo animation if applicable
            } else if (Car.getIcon().toString().equals(getClass().getResource("/Images/voiture " + color + " idle droite.gif").toString())) {
                // Car starting right
                int temps = 0;
                if (turbo) {
                    // Change to "starting right" animation if turbo mode is activated
                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " démarrage droite.gif"));
                    Car.setIcon(Car1);
                    temps = 2500; // Turbo animation duration
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // TimerTask to handle switching from "starting" to "moving" animation
                    public void run() {
                        ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " roule droite.gif"));
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Updates every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            @Override
                            // Timer to update the car's position when moving right
                            public void actionPerformed(ActionEvent e) {
                                x += vitesse; // Update x-coordinate
                                Car.setLocation(x, y);
                                if (Car.getX() > (int) position.getX()) {
                                    // Stop the timer and reset to idle state
                                    ((javax.swing.Timer) e.getSource()).stop();
                                    ImageIcon Car1 = new ImageIcon(getClass().getResource("/Images/voiture " + color + " idle droite.gif"));
                                    Car.setIcon(Car1);
                                    if (player == 0) {
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    } else if (player == 1) {
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    } else {
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, temps); // Delay for turbo animation if applicable
            }
        }
        newDistance = 0; // Reset newDistance
    }
}