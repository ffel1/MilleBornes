package main;

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
import java.nio.file.Files;
import java.nio.file.Paths;

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

// This class represents WindowGame
public class WindowGame {
    public boolean getCurrentAnimation;
    private JFrame windowMenu;
    private int height;
    private int width;
    private int heightCard;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton buttonPlay;
    private JButton buttonQuit;
    private JButton buttonSaves;
    private JButton returnButton;
    private JButton buttonDraw;
    private JButton buttonDiscard;
    private JButton buttonEndOfTurn;
    private JButton buttonNewGame;
    private JPanel menuPanel;
    private JLayeredPane gamePanel;
    private ArrayList<JButton> buttonHandPlayer;
    private GraphicsEnvironment env;
    private GraphicsDevice screen;
    private JButton ButtonCar1;
    private JButton ButtonCar2;
    private JButton ButtonCar3;
    private int kilometersV1;
    private int kilometersV2;
    private int kilometersV3;
    private ImageIcon circuit;
    private String nameGame;
    private JPanel panelAttacksPlayer;
    private JPanel panelAttacksCPUFast;
    private JPanel panelAttacksCPUAgro;
    private JPanel panelBootsPlayer;
    private JPanel panelBootsCPUFast;
    private JPanel panelBootsCPUAgro;
    private ImageIcon soundOn;
    private ImageIcon soundOff;
    private JButton soundButton;
    private boolean turboV1;
    private boolean turboV2;
    private boolean turboV3;
    private boolean turbo;
    private Controler controler;


// This method handles the logic for WindowGame
    public WindowGame(){
        // Initialisation
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
        menuPanel  = new JPanel();  
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

        // winetre de la size de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        height = (int)dimension.getHeight();
        width  = (int)dimension.getWidth();
        windowMenu.setSize(width, height); 

        enableFullScreen(windowMenu);
    } 

// This method handles the logic for getWindow
    public JFrame getWindow(){
        return windowMenu;
    }

// This method handles the logic for getDiscard
    public JButton getDiscard()
    {
        return buttonDiscard;
    }

// This method handles the logic for getButtonHandPlayer
    public ArrayList<JButton> getButtonHandPlayer(){
        return buttonHandPlayer;
    }

// Private or protected member
private void enableFullScreen(JFrame winetre) {
    winetre.dispose(); // Necessary for certain window modifications
    winetre.setUndecorated(true); // Removes borders and the title bar
    screen.setFullScreenWindow(winetre); // Sets the window to full-screen mode
}

/**
 * Connects an action to the "Play" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonPlay
public void addActionButtonPlay(ActionListener action){
    buttonPlay.addActionListener(action);
}

/**
 * Connects an action to the "History" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addButtonActionSaves
public void addButtonActionSaves(ActionListener action){
    buttonSaves.addActionListener(action);
}

/**
 * Connects an action to the "Quit" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionQuitButton
public void addActionQuitButton(ActionListener action){
    buttonQuit.addActionListener(action);
}

/**
 * Connects an action to the "Return to Menu" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionReturnButton
public void addActionReturnButton(ActionListener action){
    returnButton.addActionListener(action);
}

/**
 * Connects an action to the "New Game" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonNewGame
public void addActionButtonNewGame(ActionListener action){
    buttonNewGame.addActionListener(action);
}

/**
 * Connects an action to a "Card" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonCard
public void addActionButtonCard(ActionListener action, int i){
    buttonHandPlayer.get(i).addActionListener(action);
}

/**
 * Connects an action to the "Draw" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonDraw
public void addActionButtonDraw(ActionListener action){
    buttonDraw.addActionListener(action);
}

/**
 * Connects an action to the "Discard" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonDiscard
public void addActionButtonDiscard(ActionListener action){
    buttonDiscard.addActionListener(action);
}

/**
 * Connects an action to the "CPU Agro" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonCPUAgro
public void addActionButtonCPUAgro(ActionListener action){
    ButtonCar2.addActionListener(action);
}

/**
 * Connects an action to the "CPU Fast" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionButtonCPUFast
public void addActionButtonCPUFast(ActionListener action){
    ButtonCar3.addActionListener(action);
}

/**
 * Connects an action to the "End Turn" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for AddActionButtonEndOfMyTurn
public void AddActionButtonEndOfMyTurn(ActionListener action){
    buttonEndOfTurn.addActionListener(action);
}

/**
 * Connects an action to the "Sound" button
 * @param action The action to execute when the button is clicked
 */
// This method handles the logic for addActionSoundButton
public void addActionSoundButton(ActionListener action){
    soundButton.addActionListener(action);
}

/*
 * Displays the start menu window
 */
// This method handles the logic for createWindowMenu
public void createWindowMenu(){

    // Initialize menuPanel and its layout manager
    menuPanel.setBounds(0, 0, width, height);
    menuPanel.setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();

    // "Play" button
    buttonPlay.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size in %
    grid.gridx = 0;
    grid.gridy = 0;
    grid.insets = new Insets(50, 0, 50, 0); // Spacing between components
    grid.anchor = GridBagConstraints.CENTER;
    menuPanel.add(buttonPlay, grid);

    // "Mille Bornes" image
    ImageIcon image = new ImageIcon("Images/MilleBornes.png");
    JLabel labelImage = new JLabel();
    labelImage.setIcon(image);
    labelImage.setHorizontalAlignment(JLabel.CENTER);
    labelImage.setVerticalAlignment(JLabel.CENTER);
    grid.gridx = 0;
    grid.gridy = 1;
    grid.insets = new Insets(50, 0, 50, 0); // Spacing between components
    grid.anchor = GridBagConstraints.CENTER;
    menuPanel.add(labelImage, grid);

    // "History" button
    buttonSaves.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size in %
    grid.gridx = 0;
    grid.gridy = 2;
    grid.anchor = GridBagConstraints.CENTER;
    menuPanel.add(buttonSaves, grid);

    // "Quit" button
    buttonQuit.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size in %
    grid.gridx = 0; 
    grid.gridy = 3; 
    grid.anchor = GridBagConstraints.CENTER; // Center
    menuPanel.add(buttonQuit, grid);

    windowMenu.setContentPane(menuPanel);
    windowMenu.setVisible(true);    
}

/*
 * Displays the game window
 */
// This method handles the logic for createWindowGame
public void createWindowGame(){
    windowMenu.setContentPane(gamePanel);
    windowMenu.revalidate();
    windowMenu.repaint();

    createDisplayAttacks();
    createDisplayBoots();

    // Circuit
    circuit = new ImageIcon("Images/circuit.png");
    Image imageRedimensionnee = circuit.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    circuit = new ImageIcon(imageRedimensionnee);
    JLabel labelCircuit = new JLabel();
    labelCircuit.setIcon(circuit);
    labelCircuit.setVerticalAlignment(JLabel.CENTER);
    labelCircuit.setHorizontalAlignment(JLabel.CENTER);
    labelCircuit.setBounds(0, 0, circuit.getIconWidth(), circuit.getIconHeight());
    gamePanel.add(labelCircuit, Integer.valueOf(1));

    // Elements
    printTextArea();
    printCars(circuit);

    // "Main Menu" button
    returnButton.setBounds(width - (width * 11 / 100), height - height * 9/100, width * 5 / 100, height * 5/ 100);
    returnButton.setContentAreaFilled(false);
    gamePanel.add(returnButton, Integer.valueOf(2));
    if(returnButton.getActionListeners().length == 1)
    {
        returnButton.removeActionListener(returnButton.getActionListeners()[0]);
    }

    // "End Turn" button
    buttonEndOfTurn.setBounds(width/2 + width * 355 / 1000, height / 2 + height * 175 / 1000, width * 6 / 100, height * 7 / 100);
    buttonEndOfTurn.setContentAreaFilled(false);
    gamePanel.add(buttonEndOfTurn, Integer.valueOf(2));
    if(buttonEndOfTurn.getActionListeners().length == 1)
    {
        buttonEndOfTurn.removeActionListener(buttonEndOfTurn.getActionListeners()[0]);
    }
    
    // "New Game" button
    buttonNewGame.setBounds(width - (width * 20 / 100), height - height * 9/100, width * 5 / 100, height * 5/ 100);
    buttonNewGame.setContentAreaFilled(false);
    gamePanel.add(buttonNewGame, Integer.valueOf(2));
    if(buttonNewGame.getActionListeners().length == 1)
    {
        buttonNewGame.removeActionListener(buttonNewGame.getActionListeners()[0]);
    }

    // "Draw" button
    buttonDraw.setBounds(width/2 - width*96/1000 , height/2 - height*5/100, (circuit.getIconWidth() * 6 / 100), (circuit.getIconHeight() * 7 / 100));
    buttonDraw.setContentAreaFilled(false);
    gamePanel.add(buttonDraw, Integer.valueOf(2));
    if(buttonDraw.getActionListeners().length == 1)
    {
        buttonDraw.removeActionListener(buttonDraw.getActionListeners()[0]);
    }

    // "Discard" button
    buttonDiscard.setBounds(width/2 + width*42/1000 , height/2 - height*4/100, (circuit.getIconWidth() * 5 / 100), (circuit.getIconHeight() * 5 / 100));
    buttonDiscard.setContentAreaFilled(false);
    gamePanel.add(buttonDiscard, Integer.valueOf(5));
    if(buttonDiscard.getActionListeners().length == 1)
    {
        buttonDiscard.removeActionListener(buttonDiscard.getActionListeners()[0]);
    }

    // "Sound" button
    soundOn = new ImageIcon("Images/sonON.png");
    Image imageRedimensionneeSoundOn = soundOn.getImage().getScaledInstance(width * 27 / 1000, height * 50 / 1000, Image.SCALE_SMOOTH);
    soundOn = new ImageIcon(imageRedimensionneeSoundOn);
    soundOff = new ImageIcon("Images/sonOFF.png");
    Image imageRedimensionneeSoundOff = soundOff.getImage().getScaledInstance(width * 27 / 1000, height * 50 / 1000, Image.SCALE_SMOOTH);
    soundOff = new ImageIcon(imageRedimensionneeSoundOff);
    soundButton.setIcon(soundOn);
    soundButton.setBounds(width - (soundOn.getIconWidth() * 135 / 100), height - (soundOn.getIconHeight() * 180 / 100), soundOn.getIconWidth(), soundOn.getIconHeight());
    soundButton.setFocusPainted(false);
    soundButton.setContentAreaFilled(false);
    soundButton.setOpaque(false);
    gamePanel.add(soundButton, Integer.valueOf(10));
    if(soundButton.getActionListeners().length == 1)
    {
        soundButton.removeActionListener(soundButton.getActionListeners()[0]);
    }

    windowMenu.setLayout(null);
    windowMenu.setVisible(true);
}


// This method handles the logic for changeImageSound
    public void changeImageSound(){
        if(soundButton.getIcon().equals(soundOn)){
            soundButton.setIcon(soundOff);  
        }else{
            soundButton.setIcon(soundOn);
        }
    }

    
// This method handles the logic for createWindowSaves
    @SuppressWarnings("unused")
    public void createWindowSaves() {
        JPanel panelHistorique = new JPanel();
   
        panelHistorique.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
    
        File round,Game;

        int i = 1;
        
        Game = new File("SauvegardeDesHistoriques/Partie_" + i+".txt");
        //Button Game_x
        while (Game.exists()) {
            String path = Game.getPath();
            JButton Buttonfile = new JButton("Partie " + i);
            Buttonfile.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); 
    
            Buttonfile.addActionListener(e -> {
                printContentFile(path);
            });

            grid.gridx = 1; 
            grid.gridy = i; 
            grid.insets = new Insets(40, 40, 40, 40);
            grid.anchor = GridBagConstraints.CENTER;

            panelHistorique.add(Buttonfile, grid);
    
            i++;
            Game = new File("SauvegardeDesHistoriques/Partie_" + i+".txt");
        }

        int j = 1;
        round = new File("SauvegardeDesHistoriques/round_" + j+".txt");
        //Button round_x
        while (round.exists()) {
            String path = round.getPath();
            JButton Buttonfile = new JButton("Manche " + j);
            Buttonfile.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); 
    
            Buttonfile.addActionListener(e -> {
                printContentFile(path);
            });

            grid.gridx = 1; 
            grid.gridy = i; 
            grid.insets = new Insets(40, 40, 40, 40);
            grid.anchor = GridBagConstraints.CENTER;

            panelHistorique.add(Buttonfile, grid);
    
            j++;
            i++;
            round = new File("SauvegardeDesHistoriques/round_" + j+".txt");
        }

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
    
// Private or protected member
    @SuppressWarnings("unused")
    private void printContentFile(String cheminfile) {
        JPanel panelPrint = new JPanel();
        panelPrint.setLayout(new GridBagLayout()); 
        GridBagConstraints grid = new GridBagConstraints();
    
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(textArea);
       
        scrollPane.setPreferredSize(new Dimension(width*95/100, height * 75 / 100)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        grid.gridx = 0;
        grid.gridy = 0;
        grid.anchor = GridBagConstraints.CENTER;
        panelPrint.add(scrollPane, grid);
    
        try (BufferedReader read = new BufferedReader(new FileReader(cheminfile))) {
            String line;
            while ((line = read.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Erreur lors de la lecture du fichier.");
        }

        JButton returnButtonMenu = new JButton("Menu historique");
        returnButtonMenu.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size en %
        returnButtonMenu.addActionListener(e -> createWindowSaves());
        grid.gridx = 0;
        grid.gridy = 1; 
        grid.insets = new Insets(20, 0, 10, 0); 
        grid.anchor = GridBagConstraints.CENTER;
    
        panelPrint.add(returnButtonMenu, grid);

        windowMenu.setContentPane(panelPrint);
        windowMenu.revalidate();
        windowMenu.repaint();
    }

    public void setControler(Controler controler)
    {
        this.controler = controler;
    }

//This method delete all the saves and the actual round
    private void deleteHistory() {
        controler.setModele(new Game());
        File directory = new File("SauvegardeDesHistoriques");
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }

        File fileSaves = new File("save.ser");
        fileSaves.delete();
        createWindowSaves();
    }
    
// This method handles the logic for printCardsPlayer
    public void printCardsPlayer(ArrayList<Card> Hand){
        if(heightCard == 0)
        {
            heightCard = Hand.get(0).getImage().getIconHeight();
        }
        for(int i = 0; i < Hand.size(); i++){
            Card card = Hand.get(i);
            ImageIcon image = card.getImage();
            int larg = width*61/1000; 
            int y = height * 845 / 1000;
            int x = (width / 2 - width * 735 / 10000) - (larg * 3) + (larg * i);
            int hight = (height * 20 / 100);
            JButton Button;
            if(image != null)
            {
                Button = new JButton(null, image);
            }
            else
            {
                Button = new JButton("Annuler");
            }
            Button.setIcon(image);
            Button.setBackground(Color.PINK);
            Button.setBounds(x, y, larg*60/100, hight*49/100);
            Button.setFocusPainted(false);
            Button.setContentAreaFilled(false);
            Button.setVisible(true);
            gamePanel.add(Button, Integer.valueOf(3));
            
            buttonHandPlayer.add(i, Button);
        }
    }

    // This method handles the logic for deleteCardsPlayers
    public void deleteCardsPlayers(){
        // Delete all the cards
        for(int j = 0; j < buttonHandPlayer.size(); j++){
            buttonHandPlayer.get(j).setIcon(null);
            buttonHandPlayer.get(j).setVisible(false);
        }
    }

    // This method handles the logic for getCurrentAnimation
    public boolean getCurrentAnimation()
    {
        return getCurrentAnimation;
    }


    // This method handles the logic for createDisplayAttacks
    public void createDisplayAttacks() {
        // panel global for Player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);

        panelAttacksPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksPlayer.setOpaque(false);
        panelGlobalPlayer.add(panelAttacksPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 620 /10000 , height * 12 /100, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // panel global for CPU Fast
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);

        panelAttacksCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUFast.setOpaque(false);
        panelGlobalCPUFast.add(panelAttacksCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 620 /10000 , height * 439 /1000, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // panel global for CPU Agro
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);

        panelAttacksCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUAgro.setOpaque(false);
        panelGlobalCPUAgro.add(panelAttacksCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 620 /10000 , height * 2795 /10000, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));
    }

    // This method handles the logic for createDisplayBoots
    public void createDisplayBoots() {
        // panel global for Player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);

        panelBootsPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsPlayer.setOpaque(false);
        panelGlobalPlayer.add(panelBootsPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 8585 /10000 , height * 12 /100, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // panel global pour CPU Fast
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);

        panelBootsCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUFast.setOpaque(false);
        panelGlobalCPUFast.add(panelBootsCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 8585 /10000 , height * 439 /1000, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // panel global pour CPU Agro
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);

        panelBootsCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUAgro.setOpaque(false);
        panelGlobalCPUAgro.add(panelBootsCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 8585 /10000 , height * 2795 /10000, width * 20/100, height * 20/100);
        gamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));

    }


    
// This method handles the logic for refreshAttacks
    public void refreshAttacks(Game Game) {
        // Delete previous cards

        panelAttacksPlayer.removeAll();
        panelAttacksCPUFast.removeAll();
        panelAttacksCPUAgro.removeAll();
    
        // add new cards
        addAttacksForPlayer(Game.getPlayer1(), panelAttacksPlayer);
        addAttacksForPlayer(Game.getPlayer2(), panelAttacksCPUFast);
        addAttacksForPlayer(Game.getPlayer3(), panelAttacksCPUAgro);
    
        panelAttacksPlayer.revalidate();
        panelAttacksPlayer.repaint();
        panelAttacksCPUFast.revalidate();
        panelAttacksCPUFast.repaint();
        panelAttacksCPUAgro.revalidate();
        panelAttacksCPUAgro.repaint();
    }

// This method handles the logic for refreshBoots
    public void refreshBoots(Game Game) {
        // Delete previous cards
        panelBootsPlayer.removeAll();
        panelBootsCPUFast.removeAll();
        panelBootsCPUAgro.removeAll();
    
        // add new cards
        addBootsForPlayer(Game.getPlayer1(), panelBootsPlayer);
        addBootsForPlayer(Game.getPlayer2(), panelBootsCPUFast);
        addBootsForPlayer(Game.getPlayer3(), panelBootsCPUAgro);
    
        panelBootsPlayer.revalidate();
        panelBootsPlayer.repaint();
        panelBootsCPUFast.revalidate();
        panelBootsCPUFast.repaint();
        panelBootsCPUAgro.revalidate();
        panelBootsCPUAgro.repaint();
    }


// Private or protected member
    private void addAttacksForPlayer(Player player, JPanel panel) {
        if (player == null || player.getCurrentAttacks().size() == 0) {
            return;
        }

        for (Card card : player.getCurrentAttacks()) {
            // resize image
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(width*25/1000, height * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // add card to the panel
        }
    }

// Private or protected member
    private void addBootsForPlayer(Player player, JPanel panel) {
        if (player == null || player.getPlayedBoots().size() == 0) {
            return;
        }
        
        for (Card card : player.getPlayedBoots()) {
            // Resize th card
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(width*25/1000, height * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // add card to the panel
        }
    }
    
    
    


// This method handles the logic for getButtonHandsPlayers
    public ArrayList<JButton> getButtonHandsPlayers()
    {
        return buttonHandPlayer;
    }

// Private or protected member
    private void printTextArea() {
    
        // Message area
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false); // Rend le panel transparent
        messagePanel.setBounds(42, height * 62 / 100, width * 15 / 100, height * 30 / 100);
        messagePanel.setLayout(new BorderLayout());
        gamePanel.add(messagePanel, Integer.valueOf(10));
    
        // config text area
        textArea.setEditable(false);
        textArea.setOpaque(false); // Rendre la zone de texte transparente
        textArea.setForeground(Color.BLACK); // Couleur du texte
    
        // Config JScrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false); // Rendre le JScrollPane transparent
        scrollPane.getViewport().setOpaque(false); // Rendre la vue du viewport transparente
        scrollPane.setBorder(null); // Supprime la bordure si nécessaire
        scrollPane.setBounds(0, height * 50 / 100, width * 15 / 100, height * 49 / 100);
    
        // add components
        messagePanel.add(scrollPane);
        textArea.setText("");
    }

// This method handles the logic for setNameGame
    public void setNameGame(String nameGame)
    {
        this.nameGame = nameGame;
    }

// This method handles the logic for clearConsole
    public void clearConsole()
    {
        textArea.setText("");
    }

// This method handles the logic for addMessage
    public void addMessage(String message, boolean b){ //to know if we add the log to the saves
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
        if(b)
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("SauvegardeDesHistoriques/" + nameGame, true))) {
                if(message != null)
                {
                    writer.write(message);
                }

    
            } catch (IOException e) {
                e.printStackTrace(); // handle exceptions
            }
        }
    }

// This method handles the logic for loadLogs
    public void loadLogs()
    {
        try {
            // read all the content
            String Content = new String(Files.readAllBytes(Paths.get("SauvegardeDesHistoriques/" + nameGame)));
            // Print content
            addMessage(Content, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

// Private or protected member
    private void printCars(ImageIcon circuit){
        // Car 1
        ImageIcon Car1 = new ImageIcon("Images/voiture rouge idle haut.gif");
        ButtonCar1 = new JButton("", Car1);
        ButtonCar1.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar1.setFocusPainted(false);
        ButtonCar1.setContentAreaFilled(false);
        ButtonCar1.setOpaque(false);
        //ButtonCar1.setBackground(Color.yellow);
        ButtonCar1.setBounds((circuit.getIconWidth() * 705 / 1000), (circuit.getIconHeight() * 62 / 100), (Car1.getIconWidth() * 10 / 100 ), (Car1.getIconHeight() * 60 / 100));
        gamePanel.add(ButtonCar1, Integer.valueOf(2));

        // Car 2
        ImageIcon Car2 = new ImageIcon("Images/voiture bleue idle haut.gif");
        ButtonCar2 = new JButton("", Car2);
        ButtonCar2.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar2.setFocusPainted(false);
        ButtonCar2.setContentAreaFilled(false);
        ButtonCar2.setOpaque(false);
        //ButtonCar2.setBackground(Color.pink);
        ButtonCar2.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000), (circuit.getIconHeight() * 62 / 100), (Car2.getIconWidth() * 10/100 ), (Car1.getIconHeight() * 60 / 100));
        gamePanel.add(ButtonCar2, Integer.valueOf(2));

        // Car 3
        ImageIcon Car3 = new ImageIcon("Images/voiture verte idle haut.gif");
        ButtonCar3 = new JButton("", Car3);
        ButtonCar3.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar3.setFocusPainted(false);
        ButtonCar3.setContentAreaFilled(false);
        ButtonCar3.setOpaque(false);
        //ButtonCar3.setBackground(Color.magenta);
        ButtonCar3.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000) * 2, (circuit.getIconHeight() * 62 / 100), (Car3.getIconWidth() * 10/100), (Car1.getIconHeight() * 60/ 100));
        gamePanel.add(ButtonCar3, Integer.valueOf(2));
    }

// This method handles the logic for resettingKilometers
    public void resettingKilometers()
    {
        kilometersV1 = 0;
        kilometersV2 = 0;
        kilometersV3 = 0;
    }

    /*
     * Move the car with the kilometers of each players
     * player = player id
     */
// This method handles the logic for moveForwadCar
    public void moveForwadCar(int distance, int player, Controler control){
        int vitesse = 5;
        JButton Car;
        int newDistance = distance;
        String color;
        int kilo = 0;
        boolean zero = false;

        // Choice with the id of the player
        if(player == 0){
            Car = ButtonCar1; 
            color = "rouge";
            kilo = kilometersV1;
            turbo = turboV1;
            if(distance <= 175 && kilometersV1 != 0){
                newDistance -= kilometersV1 - 25;
            }else if(kilometersV1 == 0){
                zero = true;
            }
        }else if(player == 1){
            Car = ButtonCar2; 
            color = "bleue";
            kilo = kilometersV2;
            turbo = turboV2;
            if(distance < 175 && kilometersV2 != 0){
                newDistance -= kilometersV2 - 25;
            }else if(kilometersV2 == 0){
                zero = true;
            }
        }else{
            Car = ButtonCar3; 
            color = "verte";
            kilo = kilometersV3;
            turbo = turboV3;
            if(distance < 175 && kilometersV3 != 0){
                newDistance -= kilometersV3 - 25;
            }else if(kilometersV3 == 0){
                zero = true;
            }
        }
        
        // if the car has to move and has already move
        if((player == 0 && distance > kilometersV1) | (player == 1 && distance > kilometersV2) | (player == 2 && distance > kilometersV3)){
            Rectangle position = Car.getBounds();
            int movementY = 0;
            int percentageX = 0;
            
            // calcul of the finale position of the car
            if(distance <= 25){
                movementY = (circuit.getIconHeight() * 9 / 100);
                position.setBounds((int)position.getX(), (int)position.getY() - movementY, 1, 1);
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle haut.gif") == 0){ // Entre 50 et 150 km = Car vers le hight
                movementY = (((newDistance - 25) / 25)) * (circuit.getIconHeight() * 7 / 100);
                if(zero){
                    movementY += (circuit.getIconHeight() * 9 / 100);
                }
                position.setBounds((int)position.getX(), (int)position.getY() - movementY, 1, 1);
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle gauche.gif") == 0 && distance > 175){ // Entre 175 et 375 km = Car vers la gauche
                percentageX = ((newDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() - percentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle bas.gif") == 0 && distance > 400){ // Entre 400 et 525 km = Car vers le bas
                movementY = (((newDistance - kilo) / 25)) * (circuit.getIconHeight() * 7 / 100);
                position.setBounds((int)position.getX(), (int)position.getY() + movementY, (int)position.getWidth(), (int)position.getHeight());
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle droite.gif") == 0 && distance > 550){ // Entre 550 et 700 km = Car vers la droite
                percentageX = ((newDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() + percentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }
            
            // move the car
            if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle haut.gif") == 0){
                // Car start hight
                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " démarrage haut.gif");
                Car.setIcon(Car1);
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // This method handles the logic for run
                    public void run(){
                        ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " roule haut.gif");
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            boolean sound = true;
                            @Override
                            // This method handles the logic for actionPerformed
                            public void actionPerformed(ActionEvent e){
                                if(sound){
                                    control.getsoundList().setFile(2);
                                    control.getsoundList().play(2);
                                    sound = false;
                                }
                                // refresh position
                                y -= vitesse;
                                Car.setLocation(x, y);
                        
                                if(distance >= 175 && (Car.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() - ((Car.getIcon().getIconHeight() * 50 / 100) * player) | y < (circuit.getIconHeight() * 125 / 1000) - player * (circuit.getIconHeight() * 50 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture" + color + " idle haut.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = 175;
                                        turboV1 = false;
                                    }else if(player == 1){
                                        kilometersV2 = 175;
                                        turboV2 = false;
                                    }else{
                                        kilometersV3 = 175;
                                        turboV3 = false;
                                    }
                                    if(distance >= 175){ // turn left
                                        turbo = false;
                                        Car1 = new ImageIcon("Images/voiture " + color + " tourne haut vers gauche.gif");
                                        Car.setIcon(Car1);
                                        
                                        Car.setBounds((circuit.getIconWidth() * 64 / 100) + (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 7 / 100) - (circuit.getIconHeight() * 4 / 100) * player, 
                                                            (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        
                                        
										java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
                                            // This method handles the logic for run
											public void run(){
												((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
												ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle gauche.gif");
												Car.setIcon(Car1);
                                                
                                                Car.setBounds((circuit.getIconWidth() * 66 / 100) + (circuit.getIconWidth() * 3 / 100) * player, (circuit.getIconHeight() * 14 / 100) - (circuit.getIconHeight() * 5 / 100) * player, 
																    (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                
												javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    boolean sound = true;
                                                    @Override
                                                    // This method handles the logic for actionPerformed
                                                    public void actionPerformed(ActionEvent e){
                                                        if(sound){
                                                            control.getsoundList().setFile(2);
                                                            control.getsoundList().play(2);
                                                            sound = false;
                                                        }
                                                        // Refresh position
                                                        x -= vitesse;
                                                        Car.setLocation(x, y);
                                                        if(x < (circuit.getIconWidth() * 64 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Stop the Timer
                                                            control.getsoundList().stop(2);
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();  
											}
                                    	}, 700);
                                    }
                                }else if(distance < 175 && (Car.getY() <= (int)position.getY())){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the Timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle haut.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    }else if(player == 1){
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, 2500);
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle gauche.gif") == 0){
                // Car start left
                int temps = 0;
                if(turbo){
                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " démarrage gauche.gif");
                    Car.setIcon(Car1);
                    temps = 2500;
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask(){
                    @Override
                    // This method handles the logic for run
                    public void run(){
                        ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " roule gauche.gif");
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            boolean sound = true;
                            @Override
                            // This method handles the logic for actionPerformed
                            public void actionPerformed(ActionEvent e) {
                                if(sound){
                                    control.getsoundList().setFile(2);
                                    control.getsoundList().play(2);
                                    sound = false;
                                }
                                // Refresh position
                                x -= vitesse;
                                Car.setLocation(x, y);
                                if(distance >= 400 && (Car.getX() + (circuit.getIconWidth() * 0 / 1300) <= (int)position.getX() - ((circuit.getIconWidth() * 50 / 100) * player) | x < (circuit.getIconWidth() * 300 / 1000) - player * (circuit.getIconWidth() * 40 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // stop the timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle gauche.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = 400;
                                        turboV1 = false;
                                    }else if(player == 1){
                                        kilometersV2 = 400;
                                        turboV2 = false;
                                    }else{
                                        kilometersV3 = 400;
                                        turboV3 = false;
                                    }
                                    if(distance >= 400){ // turn down
                                        Car1 = new ImageIcon("Images/voiture " + color + " tourne gauche vers bas.gif");
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 23 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 60 / 1000) - (circuit.getIconHeight() * 40 / 1000) * player, 
                                                            (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
                                            // This method handles the logic for run
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // stop the Timer
                                                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle bas.gif");
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 80 / 1000) - (circuit.getIconHeight() * 3 / 100) * player, 
																	(circuit.getIconWidth() * 3 / 100), (Car1.getIconHeight() * 50 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    boolean sound = true;
                                                    @Override
                                                    // This method handles the logic for actionPerformed
                                                    public void actionPerformed(ActionEvent e){
                                                        if(sound){
                                                            control.getsoundList().setFile(2);
                                                            control.getsoundList().play(2);
                                                            sound = false;
                                                        }
                                                        // Refresh position
                                                        y += vitesse;
                                                        Car.setLocation(x, y);
                                                        if(y > (circuit.getIconHeight() * 14 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // stop the timer
                                                            control.getsoundList().stop(2);
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();  
											}
                                    	}, 700);
									}
                                }else if(distance < 400 && (Car.getX() <= (int)position.getX())){
                                    ((javax.swing.Timer) e.getSource()).stop(); // stop the Timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle gauche.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    }else if(player == 1){
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
								}
							}
						});
						timer.start();
                    }
				}, temps);
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle bas.gif") == 0){
                // Car start down
                int temps = 0;
                if(turbo){
                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " démarrage bas.gif");
                    Car.setIcon(Car1);
                    temps = 2500;
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // This method handles the logic for run
                    public void run(){
                        ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " roule bas.gif");
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            boolean sound = true;
                            @Override
                            // This method handles the logic for actionPerformed
                            public void actionPerformed(ActionEvent e) {
                                if(sound){
                                    control.getsoundList().setFile(2);
                                    control.getsoundList().play(2);
                                    sound = false;
                                }
                                // Refresh position
                                y += vitesse;
                                Car.setLocation(x, y);
                                    
                                if(distance >= 550 && y > (circuit.getIconHeight() * 580 / 1000) + player * (circuit.getIconHeight() * 45 / 1000)) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle bas.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = 550;
                                        turboV1 = false;
                                    }else if(player == 1){
                                        kilometersV2 = 550;
                                        turboV2 = false;
                                    }else{
                                        kilometersV3 = 550;
                                        turboV3 = false;
                                    }
                                    if(distance >= 550){ // Turn right
                                        Car1 = new ImageIcon("Images/voiture " + color + " tourne bas vers droite.gif");
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 22 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 530 / 1000) + (circuit.getIconHeight() * 4 / 100) * player, 
															(Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
                                            // This method handles the logic for run
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle droite.gif");
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 3 / 100) * player, (circuit.getIconHeight() * 610 / 1000) + (circuit.getIconHeight() * 45 / 1000) * player, 
                                                                    (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                                                    int x = Car.getX();
                                                    int y = Car.getY();
                                                    boolean sound = true;
                                                    @Override
                                                    // This method handles the logic for actionPerformed
                                                    public void actionPerformed(ActionEvent e){
                                                        if(sound){
                                                            control.getsoundList().setFile(2);
                                                            control.getsoundList().play(2);
                                                            sound = false;
                                                        }
                                                        // refresh position
                                                        x += vitesse;
                                                        Car.setLocation(x, y);
                                                        if(x > (circuit.getIconWidth() * 29 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                                            control.getsoundList().stop(2);
                                                            moveForwadCar(distance, player, control);
                                                        }
                                                    }
                                                });
                                                timer.start();
                                            }
										}, 700);
									}
                                }else if(distance < 550 && (Car.getY() > (int)position.getY())) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle bas.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    }else if(player == 1){
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                    
								}
							}
						});
                        timer.start();
                    }
				}, temps); 
            }else if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle droite.gif") == 0){
                // Car start right
                int temps = 0;
                if(turbo){
                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " démarrage droite.gif");
                    Car.setIcon(Car1);
                    temps = 2500;
                }
				java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    // This method handles the logic for run
                    public void run(){
                        ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " roule droite.gif");
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Refresh every 10 ms
                            int x = Car.getX();
                            int y = Car.getY();
                            boolean sound = true;
                            @Override
                            // This method handles the logic for actionPerformed
                            public void actionPerformed(ActionEvent e){
                                if(sound){
                                    control.getsoundList().setFile(2);
                                    control.getsoundList().play(2);
                                    sound = false;
                                }
                                // Refresh position
                                x += vitesse;
                                Car.setLocation(x, y);
                                if(Car.getX()  > (int)position.getX()){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Stop the timer
                                    ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle droite.gif");
                                    Car.setIcon(Car1);
                                    if(player == 0){
                                        kilometersV1 = distance;
                                        turboV1 = true;
                                    }else if(player == 1){
                                        kilometersV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometersV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
				}, temps); 
            }
        }
        newDistance = 0;
    }
}