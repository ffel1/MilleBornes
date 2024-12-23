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

public class FenetreJeu {
    public boolean animationEnCours;
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;
    private int hauteurCarte;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton boutonJouer;
    private JButton boutonQuitter;
    private JButton boutonHistorique;
    private JButton boutonRetour;
    private JButton boutonPioche;
    private JButton boutonDefausse;
    private JButton boutonFindeTour;
    private JButton boutonNouvellePartie;
    private JPanel menuPanel;
    private JLayeredPane panelJeu;
    private ArrayList<JButton> boutonsMainJoueur;
    private GraphicsEnvironment env;
    private GraphicsDevice ecran;
    private JButton boutonVoiture1;
    private JButton boutonVoiture2;
    private JButton boutonVoiture3;
    private int kilometreV1;
    private int kilometreV2;
    private int kilometreV3;
    private ImageIcon circuit;
    private String nomDeLaPartie;
    private JPanel panneauAttaquesJoueur;
    private JPanel panneauAttaquesCPUFast;
    private JPanel panneauAttaquesCPUAgro;
    private JPanel panneauBottesJoueur;
    private JPanel panneauBottesCPUFast;
    private JPanel panneauBottesCPUAgro;
    private ImageIcon sonOn;
    private ImageIcon sonOff;
    private JButton boutonSon;
    private boolean turboV1;
    private boolean turboV2;
    private boolean turboV3;
    private boolean turbo;


    public FenetreJeu(){
        // Initialisation
        hauteurCarte = 0;
        boutonJouer = new JButton("Jouer");
        boutonQuitter = new JButton("Quitter");
        //new
        boutonHistorique = new JButton("Historique");
        boutonRetour = new JButton();
        boutonNouvellePartie = new JButton();
        boutonPioche = new JButton();
        boutonDefausse = new JButton();
        boutonFindeTour = new JButton();
        boutonSon = new JButton("Son");
        fenetreMenu = new JFrame("1000 Bornes");
        textArea = new JTextArea("Début de la partie");
        menuPanel  = new JPanel();  
        panelJeu = new JLayeredPane();
        boutonsMainJoueur = new ArrayList<JButton>();
        env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ecran = env.getDefaultScreenDevice();
        kilometreV1 = 0;
        kilometreV2 = 0;
        kilometreV3 = 0;
        turboV1 = true;
        turboV2 = true;
        turboV3 = true;

        // Fenetre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        hauteur = (int)dimension.getHeight();
        largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur); 

        activerPleinEcran(fenetreMenu);
    } 

    public JFrame getFenetre(){
        return fenetreMenu;
    }

    public JButton getDefausse()
    {
        return boutonDefausse;
    }

    public ArrayList<JButton> getBoutonsMainJoueur(){
        return boutonsMainJoueur;
    }

    private void activerPleinEcran(JFrame fenetre) {
        fenetre.dispose(); // Nécessaire pour certaines modifications de fenêtre
        fenetre.setUndecorated(true); // Supprime les bordures et la barre de titre
        ecran.setFullScreenWindow(fenetre); // Passe la fenêtre en mode plein écran
    }

    /**
     * Permet de connecter une action au bouton "Jouer"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonJouer(ActionListener action){
        boutonJouer.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Historique"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonHistorique(ActionListener action){
        boutonHistorique.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Quitter"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonQuitter(ActionListener action){
        boutonQuitter.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Retour menu"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonRetour(ActionListener action){
        boutonRetour.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Nouvelle partie"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonNouvellePartie(ActionListener action){
        boutonNouvellePartie.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Carte"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonCarte(ActionListener action, int i){
        boutonsMainJoueur.get(i).addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Pioche"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonPioche(ActionListener action){
        boutonPioche.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Défausse"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonBoutonDefausse(ActionListener action){
        boutonDefausse.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "BoutonCPUAgro"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonCPUAgro(ActionListener action){
        boutonVoiture2.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "BoutonCPUAgro"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonCPUFast(ActionListener action){
        boutonVoiture3.addActionListener(action);
    }


    /**
     * Permet de connecter une action au bouton "Fin de Tour"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonFinDeMonTour(ActionListener action){
        boutonFindeTour.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "Son"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonSon(ActionListener action){
        boutonSon.addActionListener(action);
    }


    /*
     * Affiche la fenetre du menu de début
     */
    public void creerFenetreMenu(){

        // Initialisation menuPanel et de le gestionnaire de disposition
		menuPanel.setBounds(0, 0, largeur, hauteur);
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints grille = new GridBagConstraints();

        // Bouton jouer
        boutonJouer.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); // Taille en %
        grille.gridx = 0;
        grille.gridy = 0;
        grille.insets = new Insets(50, 0, 50, 0); // Espacement entre les composants
        grille.anchor = GridBagConstraints.CENTER;
        menuPanel.add(boutonJouer, grille);

        // Image mille bornes
        ImageIcon image = new ImageIcon("Images/MilleBornes.png");
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        labelImage.setVerticalAlignment(JLabel.CENTER);
        grille.gridx = 0;
        grille.gridy = 1;
        grille.insets = new Insets(50, 0, 50, 0); // Espacement entre les composants
        grille.anchor = GridBagConstraints.CENTER;
        menuPanel.add(labelImage, grille);

        // Bouton historique
        boutonHistorique.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); // Taille en %
        grille.gridx = 0;
        grille.gridy = 2;
        grille.anchor = GridBagConstraints.CENTER;
        menuPanel.add(boutonHistorique, grille);

        //Bouton quitter
        boutonQuitter.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); // Taille en %
        grille.gridx = 0; 
        grille.gridy = 3; 
        grille.anchor = GridBagConstraints.CENTER; // Centrer
        menuPanel.add(boutonQuitter, grille);


        fenetreMenu.setContentPane(menuPanel);
		fenetreMenu.setVisible(true);	
    }

    /*
     * Affiche la fenêtre de jeu
     */
    public void creerFenetreJeu(){
        fenetreMenu.setContentPane(panelJeu);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();

        creerAffichageAttaques();
        creerAffichageBottes();

        // Circuit
        circuit = new ImageIcon("Images/circuit.png");
        Image imageRedimensionnee = circuit.getImage().getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH);
        circuit = new ImageIcon(imageRedimensionnee);
        JLabel labelCircuit = new JLabel();
        labelCircuit.setIcon(circuit);
        labelCircuit.setVerticalAlignment(JLabel.CENTER);
        labelCircuit.setHorizontalAlignment(JLabel.CENTER);
        labelCircuit.setBounds(0, 0, circuit.getIconWidth(), circuit.getIconHeight());
        panelJeu.add(labelCircuit, Integer.valueOf(1));

        // Eléments
        afficherZoneDeTexte();
        afficherVoitures(circuit);

        // Bouton Menu principal
        boutonRetour.setBounds(largeur - (largeur * 11 / 100), hauteur - hauteur * 9/100, largeur * 5 / 100, hauteur * 5/ 100);
        boutonRetour.setContentAreaFilled(false);
        panelJeu.add(boutonRetour, Integer.valueOf(2));
        if(boutonRetour.getActionListeners().length == 1)
        {
            boutonRetour.removeActionListener(boutonRetour.getActionListeners()[0]);
        }

        // Bouton fin de mon tour
        boutonFindeTour.setBounds(largeur/2 + largeur * 355 / 1000, hauteur / 2 + hauteur * 175 / 1000, largeur * 6 / 100, hauteur * 7 / 100);
        boutonFindeTour.setContentAreaFilled(false);
        panelJeu.add(boutonFindeTour, Integer.valueOf(2));
        if(boutonFindeTour.getActionListeners().length == 1)
        {
            boutonFindeTour.removeActionListener(boutonFindeTour.getActionListeners()[0]);
        }
		
        // Bouton "Nouvelle partie"
        boutonNouvellePartie.setBounds(largeur - (largeur * 20 / 100), hauteur - hauteur * 9/100, largeur * 5 / 100, hauteur * 5/ 100);
        boutonNouvellePartie.setContentAreaFilled(false);
        panelJeu.add(boutonNouvellePartie, Integer.valueOf(2));
        if(boutonNouvellePartie.getActionListeners().length == 1)
        {
            boutonNouvellePartie.removeActionListener(boutonNouvellePartie.getActionListeners()[0]);
        }

        //BoutonPioche
        boutonPioche.setBounds(largeur/2 - largeur*96/1000 , hauteur/2 - hauteur*5/100, (circuit.getIconWidth() * 6 / 100), (circuit.getIconHeight() * 7 / 100));
        boutonPioche.setContentAreaFilled(false);
        panelJeu.add(boutonPioche, Integer.valueOf(2));
        if(boutonPioche.getActionListeners().length == 1)
        {
            boutonPioche.removeActionListener(boutonPioche.getActionListeners()[0]);
        }

        //BoutonDéfausse
        boutonDefausse.setBounds(largeur/2 + largeur*42/1000 , hauteur/2 - hauteur*4/100, (circuit.getIconWidth() * 5 / 100), (circuit.getIconHeight() * 5 / 100));
        boutonDefausse.setContentAreaFilled(false);
        panelJeu.add(boutonDefausse, Integer.valueOf(5));
        if(boutonDefausse.getActionListeners().length == 1)
        {
            boutonDefausse.removeActionListener(boutonDefausse.getActionListeners()[0]);
        }

        // BoutonSon
        sonOn = new ImageIcon("Images/SonON.png");
        Image imageRedimensionneeSonOn = sonOn.getImage().getScaledInstance(largeur * 2 / 100, hauteur * 25 / 1000, Image.SCALE_SMOOTH);
        sonOn = new ImageIcon(imageRedimensionneeSonOn);
        sonOff = new ImageIcon("Images/SonOFF.png");
        Image imageRedimensionneeSonOff = sonOff.getImage().getScaledInstance(largeur * 5 / 100, hauteur * 5 / 100, Image.SCALE_SMOOTH);
        sonOff = new ImageIcon(imageRedimensionneeSonOff);
        boutonSon.setIcon(sonOn);
        boutonSon.setBounds(largeur - sonOn.getIconWidth() - 26, hauteur - sonOn.getIconHeight() - 56, sonOn.getIconWidth(), sonOn.getIconHeight());
        panelJeu.add(boutonSon, Integer.valueOf(10));
        

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);
    }

    public void changerImageSon(){
        if(boutonSon.getIcon().equals(sonOn)){
            boutonSon.setIcon(sonOff);  
        }else{
            boutonSon.setIcon(sonOn);
        }
    }

    
    public void creerFenetreHistorique() {
        JPanel panelHistorique = new JPanel();
   
        panelHistorique.setLayout(new GridBagLayout());
        GridBagConstraints grille = new GridBagConstraints();
    
        File fichier;
        int i = 1;
    
        fichier = new File("SauvegardeDesHistoriques/Manche_" + i+".txt");

        while (fichier.exists()) {
            String path = fichier.getPath();
            JButton boutonFichier = new JButton("Manche " + i);
            boutonFichier.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); 
    
            boutonFichier.addActionListener(e -> {
                afficherContenuFichier(path);
            });

            grille.gridx = 1; 
            grille.gridy = i; 
            grille.insets = new Insets(40, 40, 40, 40);
            grille.anchor = GridBagConstraints.CENTER;

            panelHistorique.add(boutonFichier, grille);
    
            i++;
            fichier = new File("SauvegardeDesHistoriques/Manche_" + i+".txt");
        }

        JButton boutonSupprimer = new JButton("Supprimer historique");
        boutonSupprimer.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); 
        boutonSupprimer.addActionListener(e -> supprimerHistorique());
        grille.gridx = 0;
        grille.gridy = i; 
        grille.insets = new Insets(20, 0, 10, 0); 
        grille.anchor = GridBagConstraints.CENTER;

        panelHistorique.add(boutonSupprimer, grille);
    
        JButton boutonRetourMenu = new JButton("Menu Principal");
        boutonRetourMenu.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); 
        boutonRetourMenu.addActionListener(e -> creerFenetreMenu());
        grille.gridx = 2;
        grille.gridy = i; 
        grille.insets = new Insets(20, 0, 10, 0); 
        grille.anchor = GridBagConstraints.CENTER;
    
        panelHistorique.add(boutonRetourMenu, grille);

        JScrollPane scrollPane = new JScrollPane(panelHistorique);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40); 

        fenetreMenu.setContentPane(scrollPane);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();
    }
    
    private void afficherContenuFichier(String cheminFichier) {
        JPanel panelAfficher = new JPanel();
        panelAfficher.setLayout(new GridBagLayout()); 
        GridBagConstraints grille = new GridBagConstraints();
    
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(textArea);
       
        scrollPane.setPreferredSize(new Dimension(largeur*95/100, hauteur * 75 / 100)); 
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    
        grille.gridx = 0;
        grille.gridy = 0;
        grille.anchor = GridBagConstraints.CENTER;
        panelAfficher.add(scrollPane, grille);
    
        try (BufferedReader read = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = read.readLine()) != null) {
                textArea.append(ligne + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("Erreur lors de la lecture du fichier.");
        }

        JButton boutonRetourMenu = new JButton("Menu historique");
        boutonRetourMenu.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); // Taille en %
        boutonRetourMenu.addActionListener(e -> creerFenetreHistorique());
        grille.gridx = 0;
        grille.gridy = 1; 
        grille.insets = new Insets(20, 0, 10, 0); 
        grille.anchor = GridBagConstraints.CENTER;
    
        panelAfficher.add(boutonRetourMenu, grille);

        fenetreMenu.setContentPane(panelAfficher);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();
    }

    private void supprimerHistorique() {
        File dossier = new File("SauvegardeDesHistoriques");
        File[] fichiers = dossier.listFiles();
        for (File fichier : fichiers) {
            fichier.delete();
        }

        creerFenetreHistorique();
    }
    
    public void afficherCartesJoueur(ArrayList<Carte> main){
        if(hauteurCarte == 0)
        {
            hauteurCarte = main.get(0).getImage().getIconHeight();
        }
        for(int i = 0; i < main.size(); i++){
            Carte carte = main.get(i);
            ImageIcon image = carte.getImage();
            int larg = 11700/100; 
            int y = hauteur * 845 / 1000;
            int x = (largeur / 2 - largeur * 735 / 10000) - (larg * 3) + (larg * i);
            int haut = (hauteur * 20 / 100);
            JButton bouton;
            if(image != null)
            {
                bouton = new JButton(null, image);
            }
            else
            {
                bouton = new JButton("Annuler");
            }
            bouton.setIcon(image);
            bouton.setBackground(Color.PINK);
            bouton.setBounds(x, y, larg*60/100, haut*49/100);
            bouton.setFocusPainted(false);
            bouton.setContentAreaFilled(false);
            bouton.setVisible(true);
            panelJeu.add(bouton, Integer.valueOf(3));
            
            boutonsMainJoueur.add(i, bouton);
        }
    }

    public void effacerCartesJoueurs(){
        // Efface toutes les cartes
        for(int j = 0; j < boutonsMainJoueur.size(); j++){
            boutonsMainJoueur.get(j).setIcon(null);
            boutonsMainJoueur.get(j).setVisible(false);
        }
    }

    public boolean getAnimationEnCours()
    {
        return animationEnCours;
    }


    public void creerAffichageAttaques() {
        // Panneau global pour Joueur
        JPanel panneauGlobalJoueur = new JPanel(new BorderLayout());
        panneauGlobalJoueur.setOpaque(false);
        //JLabel labelJoueur = new JLabel("Joueur", JLabel.CENTER);
        //panneauGlobalJoueur.add(labelJoueur, BorderLayout.NORTH);

        panneauAttaquesJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesJoueur.setOpaque(false);
        //panneauAttaquesJoueur.setBackground(Color.LIGHT_GRAY);
        panneauGlobalJoueur.add(panneauAttaquesJoueur, BorderLayout.CENTER);
        panneauGlobalJoueur.setBounds(largeur * 620 /10000 , hauteur * 12 /100, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalJoueur, Integer.valueOf(3));

        // Panneau global pour CPU Fast
        JPanel panneauGlobalCPUFast = new JPanel(new BorderLayout());
        panneauGlobalCPUFast.setOpaque(false);
        //JLabel labelCPUFast = new JLabel("CPU Agro", JLabel.CENTER);
        //panneauGlobalCPUFast.add(labelCPUFast, BorderLayout.NORTH);

        panneauAttaquesCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesCPUFast.setOpaque(false);
        //panneauAttaquesCPUFast.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUFast.add(panneauAttaquesCPUFast, BorderLayout.CENTER);
        panneauGlobalCPUFast.setBounds(largeur * 620 /10000 , hauteur * 439 /1000, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalCPUFast, Integer.valueOf(3));

        // Panneau global pour CPU Agro
        JPanel panneauGlobalCPUAgro = new JPanel(new BorderLayout());
        panneauGlobalCPUAgro.setOpaque(false);
        //JLabel labelCPUAgro = new JLabel("CPU Fast", JLabel.CENTER);
        //panneauGlobalCPUAgro.add(labelCPUAgro, BorderLayout.NORTH);

        panneauAttaquesCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesCPUAgro.setOpaque(false);
        //panneauAttaquesCPUAgro.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUAgro.add(panneauAttaquesCPUAgro, BorderLayout.CENTER);
        panneauGlobalCPUAgro.setBounds(largeur * 620 /10000 , hauteur * 2795 /10000, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalCPUAgro, Integer.valueOf(3));
    }

    public void creerAffichageBottes() {
        // Panneau global pour Joueur
        JPanel panneauGlobalJoueur = new JPanel(new BorderLayout());
        panneauGlobalJoueur.setOpaque(false);
        //JLabel labelJoueur = new JLabel("Joueur", JLabel.CENTER);
        //panneauGlobalJoueur.add(labelJoueur, BorderLayout.NORTH);

        panneauBottesJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesJoueur.setOpaque(false);
        panneauGlobalJoueur.add(panneauBottesJoueur, BorderLayout.CENTER);
        panneauGlobalJoueur.setBounds(largeur * 8585 /10000 , hauteur * 12 /100, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalJoueur, Integer.valueOf(3));

        // Panneau global pour CPU Fast
        JPanel panneauGlobalCPUFast = new JPanel(new BorderLayout());
        panneauGlobalCPUFast.setOpaque(false);

        panneauBottesCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesCPUFast.setOpaque(false);
        panneauGlobalCPUFast.add(panneauBottesCPUFast, BorderLayout.CENTER);
        panneauGlobalCPUFast.setBounds(largeur * 8585 /10000 , hauteur * 439 /1000, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalCPUFast, Integer.valueOf(3));

        // Panneau global pour CPU Agro
        JPanel panneauGlobalCPUAgro = new JPanel(new BorderLayout());
        panneauGlobalCPUAgro.setOpaque(false);

        panneauBottesCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesCPUAgro.setOpaque(false);
        panneauGlobalCPUAgro.add(panneauBottesCPUAgro, BorderLayout.CENTER);
        panneauGlobalCPUAgro.setBounds(largeur * 8585 /10000 , hauteur * 2795 /1000, largeur * 20/100, hauteur * 20/100);
        panelJeu.add(panneauGlobalCPUAgro, Integer.valueOf(3));

    }


    
    public void mettreAJourAttaques(Partie partie) {
        // Effacer les anciennes cartes des panneaux
        panneauAttaquesJoueur.removeAll();
        panneauAttaquesCPUFast.removeAll();
        panneauAttaquesCPUAgro.removeAll();
    
        // Ajouter les nouvelles cartes pour chaque participant
        ajouterAttaquesPourJoueur(partie.getJoueur1(), panneauAttaquesJoueur);
        ajouterAttaquesPourJoueur(partie.getJoueur2(), panneauAttaquesCPUFast);
        ajouterAttaquesPourJoueur(partie.getJoueur3(), panneauAttaquesCPUAgro);
    
        panneauAttaquesJoueur.revalidate();
        panneauAttaquesJoueur.repaint();
        panneauAttaquesCPUFast.revalidate();
        panneauAttaquesCPUFast.repaint();
        panneauAttaquesCPUAgro.revalidate();
        panneauAttaquesCPUAgro.repaint();
    }

    public void mettreAJourBottes(Partie partie) {
        // Effacer les anciennes cartes des panneaux
        panneauBottesJoueur.removeAll();
        panneauBottesCPUFast.removeAll();
        panneauBottesCPUAgro.removeAll();
    
        // Ajouter les nouvelles cartes pour chaque participant
        ajouterBottesPourJoueur(partie.getJoueur1(), panneauBottesJoueur);
        ajouterBottesPourJoueur(partie.getJoueur2(), panneauBottesCPUFast);
        ajouterBottesPourJoueur(partie.getJoueur3(), panneauBottesCPUAgro);
    
        panneauBottesJoueur.revalidate();
        panneauBottesJoueur.repaint();
        panneauBottesCPUFast.revalidate();
        panneauBottesCPUFast.repaint();
        panneauBottesCPUAgro.revalidate();
        panneauBottesCPUAgro.repaint();
    }


    private void ajouterAttaquesPourJoueur(Joueur joueur, JPanel panneau) {
        if (joueur == null || joueur.getAttaquesEnCours() == null) {
            return;
        }

        for (Carte carte : joueur.getAttaquesEnCours()) {
            // Réduire la taille de l'image de la carte
            ImageIcon image = new ImageIcon(carte.getImage().getImage().getScaledInstance(largeur*25/1000, hauteur * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panneau.add(label); // Ajouter la carte au panneau
        }
    }

    private void ajouterBottesPourJoueur(Joueur joueur, JPanel panneau) {
        if (joueur == null || joueur.getBottesPosées() == null) {
            return;
        }
        
        for (Carte carte : joueur.getBottesPosées()) {
            // Réduire la taille de l'image de la carte
            ImageIcon image = new ImageIcon(carte.getImage().getImage().getScaledInstance(largeur*25/1000, hauteur * 7/100, Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panneau.add(label); // Ajouter la carte au panneau
        }
    }
    
    
    


    public ArrayList<JButton> getBoutonMainsJoueurs()
    {
        return boutonsMainJoueur;
    }

    private void afficherZoneDeTexte() {
    
        // Zone affichage des messages
        JPanel messagePanel = new JPanel();
        messagePanel.setOpaque(false); // Rend le panneau transparent
        messagePanel.setBounds(42, hauteur * 62 / 100, largeur * 15 / 100, hauteur * 30 / 100);
        messagePanel.setLayout(new BorderLayout());
        panelJeu.add(messagePanel, Integer.valueOf(10));
    
        // Configurer la zone de texte
        textArea.setEditable(false);
        textArea.setOpaque(false); // Rendre la zone de texte transparente
        textArea.setForeground(Color.BLACK); // Couleur du texte
    
        // Configurer le JScrollPane
        scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false); // Rendre le JScrollPane transparent
        scrollPane.getViewport().setOpaque(false); // Rendre la vue du viewport transparente
        scrollPane.setBorder(null); // Supprime la bordure si nécessaire
        scrollPane.setBounds(0, hauteur * 50 / 100, largeur * 15 / 100, hauteur * 49 / 100);
    
        // Ajouter les composants
        messagePanel.add(scrollPane);
        textArea.setText("");
    }

    public void setNomDeLaPartie(String nomDeLaPartie)
    {
        this.nomDeLaPartie = nomDeLaPartie;
    }

    public void clearConsole()
    {
        textArea.setText("");
    }

    public void ajouterMessage(String message, boolean b){ //Booléen pour savoir si on l'ajoute à l'historique de partie
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
        if(b)
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("SauvegardeDesHistoriques/" + nomDeLaPartie, true))) {
                if(message != null)
                {
                    writer.write(message);
                }

    
            } catch (IOException e) {
                e.printStackTrace(); // Gérer les exceptions d'écriture
            }
        }
    }

    public void chargerLogs()
    {
        try {
            // Lire tout le contenu du fichier
            String contenu = new String(Files.readAllBytes(Paths.get("SauvegardeDesHistoriques/" + nomDeLaPartie)));
            // Afficher le contenu
            ajouterMessage(contenu, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void afficherVoitures(ImageIcon circuit){
        // Voiture 1
        ImageIcon voiture1 = new ImageIcon("Images/voiture rouge idle haut.gif");
        boutonVoiture1 = new JButton("", voiture1);
        boutonVoiture1.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture1.setFocusPainted(false);
        boutonVoiture1.setContentAreaFilled(false);
        boutonVoiture1.setOpaque(false);
        //boutonVoiture1.setBackground(Color.yellow);
        boutonVoiture1.setBounds((circuit.getIconWidth() * 705 / 1000), (circuit.getIconHeight() * 64 / 100), (voiture1.getIconWidth() * 10 / 100 ), (voiture1.getIconHeight() * 35 / 100));
        panelJeu.add(boutonVoiture1, Integer.valueOf(2));

        // Voiture 2
        ImageIcon voiture2 = new ImageIcon("Images/voiture bleue idle haut.gif");
        boutonVoiture2 = new JButton("", voiture2);
        boutonVoiture2.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture2.setFocusPainted(false);
        boutonVoiture2.setContentAreaFilled(false);
        boutonVoiture2.setOpaque(false);
        //boutonVoiture2.setBackground(Color.pink);
        boutonVoiture2.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000), (circuit.getIconHeight() * 64 / 100), (voiture2.getIconWidth() * 10/100 ), (voiture1.getIconHeight() * 35 / 100));
        panelJeu.add(boutonVoiture2, Integer.valueOf(2));

        // Voiture 3
        ImageIcon voiture3 = new ImageIcon("Images/voiture verte idle haut.gif");
        boutonVoiture3 = new JButton("", voiture3);
        boutonVoiture3.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture3.setFocusPainted(false);
        boutonVoiture3.setContentAreaFilled(false);
        boutonVoiture3.setOpaque(false);
        //boutonVoiture3.setBackground(Color.magenta);
        boutonVoiture3.setBounds((circuit.getIconWidth() * 705 / 1000) + (circuit.getIconWidth() * 21 / 1000) * 2, (circuit.getIconHeight() * 64 / 100), (voiture3.getIconWidth() * 10/100), (voiture1.getIconHeight() * 35 / 100));
        panelJeu.add(boutonVoiture3, Integer.valueOf(2));
    }

    /*
     * Avance la voiture en fonction des kilomètres totaux du joueur
     * Seulement la première ligne droite pour l'instant
     * joueur = id du joueur
     */
    public void avancerVoiture(int distance, int joueur, Controleur control){
        int vitesse = 5;
        JButton voiture;
        int nouvelleDistance = distance;
        String couleur;
        int kilo = 0;
        boolean zero = false;

        // Choix en fonction du numéro de la voiture
        if(joueur == 0){
            voiture = boutonVoiture1; 
            couleur = "rouge";
            kilo = kilometreV1;
            turbo = turboV1;
            if(distance <= 175 && kilometreV1 != 0){
                nouvelleDistance -= kilometreV1 - 25;
            }else if(kilometreV1 == 0){
                zero = true;
            }
        }else if(joueur == 1){
            voiture = boutonVoiture2; 
            couleur = "bleue";
            kilo = kilometreV2;
            turbo = turboV2;
            if(distance < 175 && kilometreV2 != 0){
                nouvelleDistance -= kilometreV2 - 25;
            }else if(kilometreV2 == 0){
                zero = true;
            }
        }else{
            voiture = boutonVoiture3; 
            couleur = "verte";
            kilo = kilometreV3;
            turbo = turboV3;
            if(distance < 175 && kilometreV3 != 0){
                nouvelleDistance -= kilometreV3 - 25;
            }else if(kilometreV3 == 0){
                zero = true;
            }
        }
        
        // Si la voiture doit avancer par rapport à son dernier déplacement
        if((joueur == 0 && distance > kilometreV1) | (joueur == 1 && distance > kilometreV2) | (joueur == 2 && distance > kilometreV3)){
            Rectangle position = voiture.getBounds();
            int deplacementY = 0;
            int pourcentageX = 0;
            
            // Calcul la position finale de la voiture
            if(distance <= 25){
                deplacementY = (circuit.getIconHeight() * 9 / 100);
                position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, 1, 1);
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle haut.gif") == 0){ // Entre 50 et 150 km = voiture vers le haut
                deplacementY = (((nouvelleDistance - 25) / 25)) * (circuit.getIconHeight() * 7 / 100);
                if(zero){
                    deplacementY += (circuit.getIconHeight() * 9 / 100);
                }
                position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, 1, 1);
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle gauche.gif") == 0 && distance > 175){ // Entre 175 et 375 km = voiture vers la gauche
                pourcentageX = ((nouvelleDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() - pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle bas.gif") == 0 && distance > 400){ // Entre 400 et 525 km = voiture vers le bas
                deplacementY = (((nouvelleDistance - kilo) / 25)) * (circuit.getIconHeight() * 7 / 100);
                position.setBounds((int)position.getX(), (int)position.getY() + deplacementY, (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle droite.gif") == 0 && distance > 550){ // Entre 550 et 700 km = voiture vers la droite
                pourcentageX = ((nouvelleDistance - kilo) / 25 ) * (circuit.getIconWidth() * 40 / 1000);
                position.setBounds((int)position.getX() + pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }
            
            // Fait déplacer la voiture en fonction de la direction
            if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle haut.gif") == 0){
                // voiture démarrage haut
                ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " démarrage haut.gif");
                voiture.setIcon(voiture1);
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " roule haut.gif");
                        voiture.setIcon(voiture1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                            int x = voiture.getX();
                            int y = voiture.getY();
                            boolean son = true;
                            @Override
                            public void actionPerformed(ActionEvent e){
                                if(son){
                                    control.getListeSon().setFile(2);
                                    control.getListeSon().play();
                                    son = false;
                                }
                                // Mettre à jour les coordonnées
                                y -= vitesse;
                                voiture.setLocation(x, y);
                        
                                if(distance >= 175 && (voiture.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() - ((voiture.getIcon().getIconHeight() * 50 / 100) * joueur) | y < (circuit.getIconHeight() * 125 / 1000) - joueur * (circuit.getIconHeight() * 50 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture" + couleur + " idle haut.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = 175;
                                        turboV1 = false;
                                    }else if(joueur == 1){
                                        kilometreV2 = 175;
                                        turboV2 = false;
                                    }else{
                                        kilometreV3 = 175;
                                        turboV3 = false;
                                    }
                                    if(distance >= 175){ // Tourne vers la gauche
                                        turbo = false;
                                        voiture1 = new ImageIcon("Images/voiture " + couleur + " tourne haut vers gauche.gif");
                                        voiture.setIcon(voiture1);
                                        
                                        voiture.setBounds((circuit.getIconWidth() * 64 / 100) + (circuit.getIconWidth() * 2 / 100) * joueur, (circuit.getIconHeight() * 7 / 100) - (circuit.getIconHeight() * 4 / 100) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                        
                                        
										java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
											public void run(){
												((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
												ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle gauche.gif");
												voiture.setIcon(voiture1);
                                                
                                                voiture.setBounds((circuit.getIconWidth() * 66 / 100) + (circuit.getIconWidth() * 3 / 100) * joueur, (circuit.getIconHeight() * 14 / 100) - (circuit.getIconHeight() * 5 / 100) * joueur, 
																    (voiture1.getIconWidth() * 50 / 100), (voiture1.getIconHeight() * 20 / 100));
                                                
												javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                                                    int x = voiture.getX();
                                                    int y = voiture.getY();
                                                    boolean son = true;
                                                    @Override
                                                    public void actionPerformed(ActionEvent e){
                                                        if(son){
                                                            control.getListeSon().setFile(2);
                                                            control.getListeSon().play();
                                                            son = false;
                                                        }
                                                        // Mettre à jour les coordonnées
                                                        x -= vitesse;
                                                        voiture.setLocation(x, y);
                                                        if(x < (circuit.getIconWidth() * 64 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                            control.getListeSon().stop();
                                                            avancerVoiture(distance, joueur, control);
                                                        }
                                                    }
                                                });
                                                timer.start();  
											}
                                    	}, 700);
                                    }
                                }else if(distance < 175 && (voiture.getY() <= (int)position.getY())){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle haut.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = distance;
                                        turboV1 = true;
                                    }else if(joueur == 1){
                                        kilometreV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometreV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
                }, 2500);
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle gauche.gif") == 0){
                // voiture démarrage gauche
                int temps = 0;
                if(turbo){
                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " démarrage gauche.gif");
                    voiture.setIcon(voiture1);
                    temps = 2500;
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask(){
                    @Override
                    public void run(){
                        ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " roule gauche.gif");
                        voiture.setIcon(voiture1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                            int x = voiture.getX();
                            int y = voiture.getY();
                            boolean son = true;
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(son){
                                    control.getListeSon().setFile(2);
                                    control.getListeSon().play();
                                    son = false;
                                }
                                // Mettre à jour les coordonnées
                                x -= vitesse;
                                voiture.setLocation(x, y);
                                if(distance >= 400 && (voiture.getX() + (circuit.getIconWidth() * 0 / 1300) <= (int)position.getX() - ((circuit.getIconWidth() * 50 / 100) * joueur) | x < (circuit.getIconWidth() * 300 / 1000) - joueur * (circuit.getIconWidth() * 40 / 1000))) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle gauche.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = 400;
                                        turboV1 = false;
                                    }else if(joueur == 1){
                                        kilometreV2 = 400;
                                        turboV2 = false;
                                    }else{
                                        kilometreV3 = 400;
                                        turboV3 = false;
                                    }
                                    if(distance >= 400){ // Tourne vers la bas
                                        voiture1 = new ImageIcon("Images/voiture " + couleur + " tourne gauche vers bas.gif");
                                        voiture.setIcon(voiture1);
                                        voiture.setBounds((circuit.getIconWidth() * 23 / 100) - (circuit.getIconWidth() * 2 / 100) * joueur, (circuit.getIconHeight() * 60 / 1000) - (circuit.getIconHeight() * 40 / 1000) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle bas.gif");
                                                voiture.setIcon(voiture1);
                                                voiture.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 2 / 100) * joueur, (circuit.getIconHeight() * 80 / 1000) - (circuit.getIconHeight() * 3 / 100) * joueur, 
																	(circuit.getIconWidth() * 3 / 100), (voiture1.getIconHeight() * 50 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                                                    int x = voiture.getX();
                                                    int y = voiture.getY();
                                                    boolean son = true;
                                                    @Override
                                                    public void actionPerformed(ActionEvent e){
                                                        if(son){
                                                            control.getListeSon().setFile(2);
                                                            control.getListeSon().play();
                                                            son = false;
                                                        }
                                                        // Mettre à jour les coordonnées
                                                        y += vitesse;
                                                        voiture.setLocation(x, y);
                                                        if(y > (circuit.getIconHeight() * 14 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                            control.getListeSon().stop();
                                                            avancerVoiture(distance, joueur, control);
                                                        }
                                                    }
                                                });
                                                timer.start();  
											}
                                    	}, 700);
									}
                                }else if(distance < 400 && (voiture.getX() <= (int)position.getX())){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle gauche.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = distance;
                                        turboV1 = true;
                                    }else if(joueur == 1){
                                        kilometreV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometreV3 = distance;
                                        turboV3 = true;
                                    }
								}
							}
						});
						timer.start();
                    }
				}, temps);
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle bas.gif") == 0){
                // voiture démarrage bas
                int temps = 0;
                if(turbo){
                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " démarrage bas.gif");
                    voiture.setIcon(voiture1);
                    temps = 2500;
                }
                java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " roule bas.gif");
                        voiture.setIcon(voiture1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                            int x = voiture.getX();
                            int y = voiture.getY();
                            boolean son = true;
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(son){
                                    control.getListeSon().setFile(2);
                                    control.getListeSon().play();
                                    son = false;
                                }
                                // Mettre à jour les coordonnées
                                y += vitesse;
                                voiture.setLocation(x, y);
                                    
                                if(distance >= 550 && y > (circuit.getIconHeight() * 580 / 1000) + joueur * (circuit.getIconHeight() * 45 / 1000)) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle bas.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = 550;
                                        turboV1 = false;
                                    }else if(joueur == 1){
                                        kilometreV2 = 550;
                                        turboV2 = false;
                                    }else{
                                        kilometreV3 = 550;
                                        turboV3 = false;
                                    }
                                    if(distance >= 550){ // Tourne vers la droite
                                        voiture1 = new ImageIcon("Images/voiture " + couleur + " tourne bas vers droite.gif");
                                        voiture.setIcon(voiture1);
                                        voiture.setBounds((circuit.getIconWidth() * 22 / 100) - (circuit.getIconWidth() * 2 / 100) * joueur, (circuit.getIconHeight() * 530 / 1000) + (circuit.getIconHeight() * 4 / 100) * joueur, 
															(voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                        java.util.Timer chrono = new java.util.Timer();
										chrono.schedule(new TimerTask() {
											@Override
											public void run(){
                                                ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle droite.gif");
                                                voiture.setIcon(voiture1);
                                                voiture.setBounds((circuit.getIconWidth() * 27 / 100) - (circuit.getIconWidth() * 3 / 100) * joueur, (circuit.getIconHeight() * 610 / 1000) + (circuit.getIconHeight() * 45 / 1000) * joueur, 
                                                                    (voiture1.getIconWidth() * 50 / 100), (voiture1.getIconHeight() * 20 / 100));
                                                javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                                                    int x = voiture.getX();
                                                    int y = voiture.getY();
                                                    boolean son = true;
                                                    @Override
                                                    public void actionPerformed(ActionEvent e){
                                                        if(son){
                                                            control.getListeSon().setFile(2);
                                                            control.getListeSon().play();
                                                            son = false;
                                                        }
                                                        // Mettre à jour les coordonnées
                                                        x += vitesse;
                                                        voiture.setLocation(x, y);
                                                        if(x > (circuit.getIconWidth() * 29 / 100)){
                                                            ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                                            control.getListeSon().stop();
                                                            avancerVoiture(distance, joueur, control);
                                                        }
                                                    }
                                                });
                                                timer.start();
                                            }
										}, 700);
									}
                                }else if(distance < 550 && (voiture.getY() > (int)position.getY())) {
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle bas.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = distance;
                                        turboV1 = true;
                                    }else if(joueur == 1){
                                        kilometreV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometreV3 = distance;
                                        turboV3 = true;
                                    }
                                    
								}
							}
						});
                        timer.start();
                    }
				}, temps); 
            }else if(voiture.getIcon().toString().compareTo("Images/voiture " + couleur + " idle droite.gif") == 0){
                // voiture démarrage droit
                int temps = 0;
                if(turbo){
                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " démarrage droite.gif");
                    voiture.setIcon(voiture1);
                    temps = 2500;
                }
				java.util.Timer chrono = new java.util.Timer();
                chrono.schedule(new TimerTask() {
                    @Override
                    public void run(){
                        ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " roule droite.gif");
                        voiture.setIcon(voiture1);
                        javax.swing.Timer timer = new javax.swing.Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                            int x = voiture.getX();
                            int y = voiture.getY();
                            boolean son = true;
                            @Override
                            public void actionPerformed(ActionEvent e){
                                if(son){
                                    control.getListeSon().setFile(2);
                                    control.getListeSon().play();
                                    son = false;
                                }
                                // Mettre à jour les coordonnées
                                x += vitesse;
                                voiture.setLocation(x, y);
                                if(voiture.getX()  > (int)position.getX()){
                                    ((javax.swing.Timer) e.getSource()).stop(); // Arrêter le Timer
                                    ImageIcon voiture1 = new ImageIcon("Images/voiture " + couleur + " idle droite.gif");
                                    voiture.setIcon(voiture1);
                                    if(joueur == 0){
                                        kilometreV1 = distance;
                                        turboV1 = true;
                                    }else if(joueur == 1){
                                        kilometreV2 = distance;
                                        turboV2 = true;
                                    }else{
                                        kilometreV3 = distance;
                                        turboV3 = true;
                                    }
                                }
                            }
                        });
                        timer.start();
                    }
				}, temps); 
            }
            //control.getListeSon().stop();
        }
        nouvelleDistance = 0;
    }
}