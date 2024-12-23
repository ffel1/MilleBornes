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
// Private or protected member
    private JFrame windowMenu;
// Private or protected member
    private int height;
// Private or protected member
    private int width;
// Private or protected member
    private int heightCard;
// Private or protected member
    private JTextArea textArea;
// Private or protected member
    private JScrollPane scrollPane;
// Private or protected member
    private JButton buttonPlay;
// Private or protected member
    private JButton buttonQuit;
// Private or protected member
    private JButton buttonSaves;
// Private or protected member
    private JButton returnButton;
// Private or protected member
    private JButton buttonDraw;
// Private or protected member
    private JButton buttonDiscard;
// Private or protected member
    private JButton buttonEndOfTurn;
// Private or protected member
    private JButton buttonNewGame;
// Private or protected member
    private JPanel menuPanel;
// Private or protected member
    private JLayeredPane GamePanel;
// Private or protected member
    private ArrayList<JButton> buttonHandPlayer;
// Private or protected member
    private GraphicsEnvironment env;
// Private or protected member
    private GraphicsDevice screen;
// Private or protected member
    private JButton ButtonCar1;
// Private or protected member
    private JButton ButtonCar2;
// Private or protected member
    private JButton ButtonCar3;
// Private or protected member
    private int kilometersV1;
// Private or protected member
    private int kilometersV2;
// Private or protected member
    private int kilometersV3;
// Private or protected member
    private ImageIcon circuit;
// Private or protected member
    private String nameGame;
// Private or protected member
    private JPanel panelAttacksPlayer;
// Private or protected member
    private JPanel panelAttacksCPUFast;
// Private or protected member
    private JPanel panelAttacksCPUAgro;
// Private or protected member
    private JPanel panelBootsPlayer;
// Private or protected member
    private JPanel panelBootsCPUFast;
// Private or protected member
    private JPanel panelBootsCPUAgro;
// Private or protected member
    private ImageIcon soundOn;
// Private or protected member
    private ImageIcon soundOff;
// Private or protected member
    private JButton soundButton;
// Private or protected member
    private boolean turboV1;
// Private or protected member
    private boolean turboV2;
// Private or protected member
    private boolean turboV3;
// Private or protected member
    private boolean turbo;


// This method handles the logic for WindowGame
    public WindowGame(){
        // Initialisation
        heightCard = 0;
        buttonPlay = new JButton("Jouer");
        buttonQuit = new JButton("Quitter");
        //new
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
        GamePanel = new JLayeredPane();
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

        activerFullcran(windowMenu);
    } 

// This method handles the logic for getWindow
    public JFrame getWindow(){
        return windowMenu;
    }

// This method handles the logic for getdiscard
    public JButton getdiscard()
    {
        return buttonDiscard;
    }

// This method handles the logic for getbuttonHandPlayer
    public ArrayList<JButton> getbuttonHandPlayer(){
        return buttonHandPlayer;
    }

// Private or protected member
    private void activerFullcran(JFrame winetre) {
        winetre.dispose(); // Nécessaire pour certaines modifications de winêtre
        winetre.setUndecorated(true); // Supprime les bordures et la barre de titre
        screen.setFullScreenWindow(winetre); // Passe la winêtre en mode plein écran
    }

    /**
     * Permet de connecter une action au Button "play"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionbuttonPlay
    public void addActionbuttonPlay(ActionListener action){
        buttonPlay.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Historique"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addButtonActionSaves
    public void addButtonActionSaves(ActionListener action){
        buttonSaves.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Quitter"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionQuitButton
    public void addActionQuitButton(ActionListener action){
        buttonQuit.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Retour menu"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionreturnButton
    public void addActionreturnButton(ActionListener action){
        returnButton.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Nouvelle Game"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionbuttonNewGame
    public void addActionbuttonNewGame(ActionListener action){
        buttonNewGame.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Card"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionButtonCard
    public void addActionButtonCard(ActionListener action, int i){
        buttonHandPlayer.get(i).addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "draw"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionbuttonDraw
    public void addActionbuttonDraw(ActionListener action){
        buttonDraw.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Défausse"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionbuttonDiscard
    public void addActionbuttonDiscard(ActionListener action){
        buttonDiscard.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "ButtonCPUAgro"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionButtonCPUAgro
    public void addActionButtonCPUAgro(ActionListener action){
        ButtonCar2.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "ButtonCPUAgro"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionButtonCPUFast
    public void addActionButtonCPUFast(ActionListener action){
        ButtonCar3.addActionListener(action);
    }


    /**
     * Permet de connecter une action au Button "End de Tour"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionButtonEndDemyTurn
    public void addActionButtonEndDemyTurn(ActionListener action){
        buttonEndOfTurn.addActionListener(action);
    }

    /**
     * Permet de connecter une action au Button "Sound"
     * @param action L'action à exécuter lors du clic sur le Button
     */
// This method handles the logic for addActionsoundButton
    public void addActionsoundButton(ActionListener action){
        soundButton.addActionListener(action);
    }


    /*
     * Affiche la winetre du menu de début
     */
// This method handles the logic for createWindowMenu
    public void createWindowMenu(){

        // Initialisation menuPanel et de le gestionnaire de disposition
		menuPanel.setBounds(0, 0, width, height);
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();

        // Button play
        buttonPlay.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size en %
        grid.gridx = 0;
        grid.gridy = 0;
        grid.insets = new Insets(50, 0, 50, 0); // Espacement entre les composants
        grid.anchor = GridBagConstraints.CENTER;
        menuPanel.add(buttonPlay, grid);

        // Image mille bornes
        ImageIcon image = new ImageIcon("Images/MilleBornes.png");
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        labelImage.setVerticalAlignment(JLabel.CENTER);
        grid.gridx = 0;
        grid.gridy = 1;
        grid.insets = new Insets(50, 0, 50, 0); // Espacement entre les composants
        grid.anchor = GridBagConstraints.CENTER;
        menuPanel.add(labelImage, grid);

        // Button historique
        buttonSaves.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size en %
        grid.gridx = 0;
        grid.gridy = 2;
        grid.anchor = GridBagConstraints.CENTER;
        menuPanel.add(buttonSaves, grid);

        //Button quitter
        buttonQuit.setPreferredSize(new Dimension(width * 25 / 100, height * 10 / 100)); // size en %
        grid.gridx = 0; 
        grid.gridy = 3; 
        grid.anchor = GridBagConstraints.CENTER; // Centrer
        menuPanel.add(buttonQuit, grid);


        windowMenu.setContentPane(menuPanel);
		windowMenu.setVisible(true);	
    }

    /*
     * Affiche la Game window
     */
// This method handles the logic for createWindowGame
    public void createWindowGame(){
        windowMenu.setContentPane(GamePanel);
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
        GamePanel.add(labelCircuit, Integer.valueOf(1));

        // Eléments
        PrintTextArea();
        printCars(circuit);

        // Button Menu principal
        returnButton.setBounds(width - (width * 11 / 100), height - height * 9/100, width * 5 / 100, height * 5/ 100);
        returnButton.setContentAreaFilled(false);
        GamePanel.add(returnButton, Integer.valueOf(2));
        if(returnButton.getActionListeners().length == 1)
        {
            returnButton.removeActionListener(returnButton.getActionListeners()[0]);
        }

        // Button End de mon tour
        buttonEndOfTurn.setBounds(width/2 + width * 355 / 1000, height / 2 + height * 175 / 1000, width * 6 / 100, height * 7 / 100);
        buttonEndOfTurn.setContentAreaFilled(false);
        GamePanel.add(buttonEndOfTurn, Integer.valueOf(2));
        if(buttonEndOfTurn.getActionListeners().length == 1)
        {
            buttonEndOfTurn.removeActionListener(buttonEndOfTurn.getActionListeners()[0]);
        }
		
        // Button "Nouvelle Game"
        buttonNewGame.setBounds(width - (width * 20 / 100), height - height * 9/100, width * 5 / 100, height * 5/ 100);
        buttonNewGame.setContentAreaFilled(false);
        GamePanel.add(buttonNewGame, Integer.valueOf(2));
        if(buttonNewGame.getActionListeners().length == 1)
        {
            buttonNewGame.removeActionListener(buttonNewGame.getActionListeners()[0]);
        }

        //buttonDraw
        buttonDraw.setBounds(width/2 - width*96/1000 , height/2 - height*5/100, (circuit.getIconWidth() * 6 / 100), (circuit.getIconHeight() * 7 / 100));
        buttonDraw.setContentAreaFilled(false);
        GamePanel.add(buttonDraw, Integer.valueOf(2));
        if(buttonDraw.getActionListeners().length == 1)
        {
            buttonDraw.removeActionListener(buttonDraw.getActionListeners()[0]);
        }

        //ButtonDéfausse
        buttonDiscard.setBounds(width/2 + width*42/1000 , height/2 - height*4/100, (circuit.getIconWidth() * 5 / 100), (circuit.getIconHeight() * 5 / 100));
        buttonDiscard.setContentAreaFilled(false);
        GamePanel.add(buttonDiscard, Integer.valueOf(5));
        if(buttonDiscard.getActionListeners().length == 1)
        {
            buttonDiscard.removeActionListener(buttonDiscard.getActionListeners()[0]);
        }

        // soundButton
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
        GamePanel.add(soundButton, Integer.valueOf(10));
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
            JButton Buttonfile = new JButton("round " + j);
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
        ButtonDelete.addActionListener(e -> DeleteHistorique());
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

// Private or protected member
    private void DeleteHistorique() {
        File directory = new File("SauvegardeDesHistoriques");
        File[] files = directory.listFiles();
        for (File file : files) {
            file.delete();
        }

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
            GamePanel.add(Button, Integer.valueOf(3));
            
            buttonHandPlayer.add(i, Button);
        }
    }

// This method handles the logic for effacerCardsPlayers
    public void effacerCardsPlayers(){
        // Efface toutes les cards
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
        // panel global pour Player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);
        //JLabel labelPlayer = new JLabel("Player", JLabel.CENTER);
        //panelGlobalPlayer.add(labelPlayer, BorderLayout.NORTH);

        panelAttacksPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksPlayer.setOpaque(false);
        //panelAttacksPlayer.setBackground(Color.LIGHT_GRAY);
        panelGlobalPlayer.add(panelAttacksPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 620 /10000 , height * 12 /100, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // panel global pour CPU Fast
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);
        //JLabel labelCPUFast = new JLabel("CPU Agro", JLabel.CENTER);
        //panelGlobalCPUFast.add(labelCPUFast, BorderLayout.NORTH);

        panelAttacksCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUFast.setOpaque(false);
        //panelAttacksCPUFast.setBackground(Color.LIGHT_GRAY);
        panelGlobalCPUFast.add(panelAttacksCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 620 /10000 , height * 439 /1000, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // panel global pour CPU Agro
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);
        //JLabel labelCPUAgro = new JLabel("CPU Fast", JLabel.CENTER);
        //panelGlobalCPUAgro.add(labelCPUAgro, BorderLayout.NORTH);

        panelAttacksCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelAttacksCPUAgro.setOpaque(false);
        //panelAttacksCPUAgro.setBackground(Color.LIGHT_GRAY);
        panelGlobalCPUAgro.add(panelAttacksCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 620 /10000 , height * 2795 /10000, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));
    }

// This method handles the logic for createDisplayBoots
    public void createDisplayBoots() {
        // panel global pour Player
        JPanel panelGlobalPlayer = new JPanel(new BorderLayout());
        panelGlobalPlayer.setOpaque(false);
        //JLabel labelPlayer = new JLabel("Player", JLabel.CENTER);
        //panelGlobalPlayer.add(labelPlayer, BorderLayout.NORTH);

        panelBootsPlayer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsPlayer.setOpaque(false);
        panelGlobalPlayer.add(panelBootsPlayer, BorderLayout.CENTER);
        panelGlobalPlayer.setBounds(width * 8585 /10000 , height * 12 /100, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalPlayer, Integer.valueOf(3));

        // panel global pour CPU Fast
        JPanel panelGlobalCPUFast = new JPanel(new BorderLayout());
        panelGlobalCPUFast.setOpaque(false);

        panelBootsCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUFast.setOpaque(false);
        panelGlobalCPUFast.add(panelBootsCPUFast, BorderLayout.CENTER);
        panelGlobalCPUFast.setBounds(width * 8585 /10000 , height * 439 /1000, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalCPUFast, Integer.valueOf(3));

        // panel global pour CPU Agro
        JPanel panelGlobalCPUAgro = new JPanel(new BorderLayout());
        panelGlobalCPUAgro.setOpaque(false);

        panelBootsCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBootsCPUAgro.setOpaque(false);
        panelGlobalCPUAgro.add(panelBootsCPUAgro, BorderLayout.CENTER);
        panelGlobalCPUAgro.setBounds(width * 8585 /10000 , height * 2795 /10000, width * 20/100, height * 20/100);
        GamePanel.add(panelGlobalCPUAgro, Integer.valueOf(3));

    }


    
// This method handles the logic for refreshAttacks
    public void refreshAttacks(Game Game) {
        // Effacer les anciennes cards des panelx

        panelAttacksPlayer.removeAll();
        panelAttacksCPUFast.removeAll();
        panelAttacksCPUAgro.removeAll();
    
        // add les nouvelles cards pour chaque participant
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
        // Effacer les anciennes cards des panelx
        panelBootsPlayer.removeAll();
        panelBootsCPUFast.removeAll();
        panelBootsCPUAgro.removeAll();
    
        // add les nouvelles cards pour chaque participant
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
            // Réduire la size de l'image de la card
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(width*25/1000, height * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // add la card au panel
        }
    }

// Private or protected member
    private void addBootsForPlayer(Player player, JPanel panel) {
        if (player == null || player.getPlayedBoots().size() == 0) {
            return;
        }
        
        for (Card card : player.getPlayedBoots()) {
            // Réduire la size de l'image de la card
            ImageIcon image = new ImageIcon(card.getImage().getImage().getScaledInstance(width*25/1000, height * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panel.add(label); // add la card au panel
        }
    }
    
    
    


// This method handles the logic for getbuttonHandsPlayers
    public ArrayList<JButton> getbuttonHandsPlayers()
    {
        return buttonHandPlayer;
    }

// Private or protected member
    private void PrintTextArea() {
    
        // Zone Display des messages
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false); // Rend le panel transparent
        messagePanel.setBounds(42, height * 62 / 100, width * 15 / 100, height * 30 / 100);
        messagePanel.setLayout(new BorderLayout());
        GamePanel.add(messagePanel, Integer.valueOf(10));
    
        // Configurer la zone de texte
        textArea.setEditable(false);
        textArea.setOpaque(false); // Rendre la zone de texte transparente
        textArea.setForeground(Color.BLACK); // Couleur du texte
    
        // Configurer le JScrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false); // Rendre le JScrollPane transparent
        scrollPane.getViewport().setOpaque(false); // Rendre la vue du viewport transparente
        scrollPane.setBorder(null); // Supprime la bordure si nécessaire
        scrollPane.setBounds(0, height * 50 / 100, width * 15 / 100, height * 49 / 100);
    
        // add les composants
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
    public void addMessage(String message, boolean b){ //Booléen pour savoir si on l'ajoute à l'historique de Game
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
                e.printStackTrace(); // Gérer les exceptions d'écriture
            }
        }
    }

// This method handles the logic for loadLogs
    public void loadLogs()
    {
        try {
            // Lire tout le Content du file
            String Content = new String(Files.readAllBytes(Paths.get("SauvegardeDesHistoriques/" + nameGame)));
            // Print le Content
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
        GamePanel.add(ButtonCar1, Integer.valueOf(2));

        // Car 2
        ImageIcon Car2 = new ImageIcon("Images/voiture bleue idle haut.gif");
        ButtonCar2 = new JButton("", Car2);
        ButtonCar2.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar2.setFocusPainted(false);
        ButtonCar2.setContentAreaFilled(false);
        ButtonCar2.setOpaque(false);
        //ButtonCar2.setBackground(Color.pink);
        ButtonCar2.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000), (circuit.getIconHeight() * 62 / 100), (Car2.getIconWidth() * 10/100 ), (Car1.getIconHeight() * 60 / 100));
        GamePanel.add(ButtonCar2, Integer.valueOf(2));

        // Car 3
        ImageIcon Car3 = new ImageIcon("Images/voiture verte idle haut.gif");
        ButtonCar3 = new JButton("", Car3);
        ButtonCar3.setBorder(BorderFactory.createEmptyBorder());
        ButtonCar3.setFocusPainted(false);
        ButtonCar3.setContentAreaFilled(false);
        ButtonCar3.setOpaque(false);
        //ButtonCar3.setBackground(Color.magenta);
        ButtonCar3.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000) * 2, (circuit.getIconHeight() * 62 / 100), (Car3.getIconWidth() * 10/100), (Car1.getIconHeight() * 60/ 100));
        GamePanel.add(ButtonCar3, Integer.valueOf(2));
    }

// This method handles the logic for resettingKilometers
    public void resettingKilometers()
    {
        kilometersV1 = 0;
        kilometersV2 = 0;
        kilometersV3 = 0;
        printCars(circuit);
    }

    /*
     * Avance la Car en function des kilomètres totaux du player
     * Seulement la première line droite pour l'instant
     * player = id du player
     */
// This method handles the logic for moveForwadCar
    public void moveForwadCar(int distance, int player, Controler control){
        int vitesse = 5;
        JButton Car;
        int newDistance = distance;
        String color;
        int kilo = 0;
        boolean zero = false;

        // Choix en function du numéro de la Car
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
        
        // Si la Car Must moveForwad par rapport à sound dernier déplacement
        if((player == 0 && distance > kilometersV1) | (player == 1 && distance > kilometersV2) | (player == 2 && distance > kilometersV3)){
            Rectangle position = Car.getBounds();
            int movementY = 0;
            int percentageX = 0;
            
            // Calcul la position Endale de la Car
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
            
            // Fait déplacer la Car en function de la direction
            if(Car.getIcon().toString().compareTo("Images/voiture " + color + " idle haut.gif") == 0){
                // Car démarrage hight
                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " démarrage haut.gif");
                Car.setIcon(Car1);
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
// This method handles the logic for run
                    public void run(){
                        ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " roule haut.gif");
                        Car.setIcon(Car1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                // Mettre à jour les coordonnées
                                y -= vitesse;
                                Car.setLocation(x, y);
                        
                                if(distance >= 175 && (Car.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() - ((Car.getIcon().getIconHeight() * 50 / 100) * player) | y < (circuit.getIconHeight() * 125 / 1000) - player * (circuit.getIconHeight() * 50 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    if(distance >= 175){ // Tourne vers la gauche
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
												((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
												ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle gauche.gif");
												Car.setIcon(Car1);
                                                
                                                Car.setBounds((circuit.getIconWidth() * 66 / 100) + (circuit.getIconWidth() * 3 / 100) * player, (circuit.getIconHeight() * 14 / 100) - (circuit.getIconHeight() * 5 / 100) * player, 
																    (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                
												javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                                        // Mettre à jour les coordonnées
                                                        x -= vitesse;
                                                        Car.setLocation(x, y);
                                                        if(x < (circuit.getIconWidth() * 64 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                // Car démarrage gauche
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
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                // Mettre à jour les coordonnées
                                x -= vitesse;
                                Car.setLocation(x, y);
                                if(distance >= 400 && (Car.getX() + (circuit.getIconWidth() * 0 / 1300) <= (int)position.getX() - ((circuit.getIconWidth() * 50 / 100) * player) | x < (circuit.getIconWidth() * 300 / 1000) - player * (circuit.getIconWidth() * 40 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    if(distance >= 400){ // Tourne vers la bas
                                        Car1 = new ImageIcon("Images/voiture " + color + " tourne gauche vers bas.gif");
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 23 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 60 / 1000) - (circuit.getIconHeight() * 40 / 1000) * player, 
                                                            (Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
// This method handles the logic for run
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle bas.gif");
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 80 / 1000) - (circuit.getIconHeight() * 3 / 100) * player, 
																	(circuit.getIconWidth() * 3 / 100), (Car1.getIconHeight() * 50 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                                        // Mettre à jour les coordonnées
                                                        y += vitesse;
                                                        Car.setLocation(x, y);
                                                        if(y > (circuit.getIconHeight() * 14 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                // Car démarrage bas
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
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                // Mettre à jour les coordonnées
                                y += vitesse;
                                Car.setLocation(x, y);
                                    
                                if(distance >= 550 && y > (circuit.getIconHeight() * 580 / 1000) + player * (circuit.getIconHeight() * 45 / 1000)) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    if(distance >= 550){ // Tourne vers la droite
                                        Car1 = new ImageIcon("Images/voiture " + color + " tourne bas vers droite.gif");
                                        Car.setIcon(Car1);
                                        Car.setBounds((circuit.getIconWidth() * 22 / 100) - (circuit.getIconWidth() * 2 / 100) * player, (circuit.getIconHeight() * 530 / 1000) + (circuit.getIconHeight() * 4 / 100) * player, 
															(Car1.getIconWidth() * 100 / 100), (Car1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
// This method handles the logic for run
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                ImageIcon Car1 = new ImageIcon("Images/voiture " + color + " idle droite.gif");
                                                Car.setIcon(Car1);
                                                Car.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 3 / 100) * player, (circuit.getIconHeight() * 610 / 1000) + (circuit.getIconHeight() * 45 / 1000) * player, 
                                                                    (Car1.getIconWidth() * 50 / 100), (Car1.getIconHeight() * 20 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                                        // Mettre à jour les coordonnées
                                                        x += vitesse;
                                                        Car.setLocation(x, y);
                                                        if(x > (circuit.getIconWidth() * 29 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
                // Car démarrage droit
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
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
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
                                // Mettre à jour les coordonnées
                                x += vitesse;
                                Car.setLocation(x, y);
                                if(Car.getX()  > (int)position.getX()){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
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
            //control.getsoundList().stop();
        }
        newDistance = 0;
    }
}