package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.io.BufferedWriter;
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
import javax.swing.Timer;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

public class FenetreJeu {
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;
    private int hauteurCarte;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton boutonJouer;
    private JButton boutonQuitter;
    private JButton boutonRetour;
    private JButton boutonPioche;
    private JButton boutonDefausse;
    private JButton boutonFindeTour;
    private JButton boutonNouvellePartie;
    private JButton boutonCPUAgro, boutonCPUFast;
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

    public FenetreJeu(){
        // Initialisation
        hauteurCarte = 0;
        boutonJouer = new JButton("Jouer");
        boutonQuitter = new JButton("Quitter");
        boutonRetour = new JButton("Menu Principal");
        boutonNouvellePartie = new JButton("Nouvelle partie");
        boutonPioche = new JButton("Pioche (temporaire)");
        boutonDefausse = new JButton("Défausse (temporaire)");
        boutonCPUAgro = new JButton("CPU Agro");
        boutonCPUFast = new JButton("CPU Fast");
        boutonFindeTour = new JButton("Fin de mon tour");
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

        //Bouton quitter
        boutonQuitter.setPreferredSize(new Dimension(largeur * 25 / 100, hauteur * 10 / 100)); // Taille en %
        grille.gridx = 0; 
        grille.gridy = 2; 
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
        Image imageRedimensionnee = circuit.getImage().getScaledInstance(largeur * 77 / 100, hauteur * 95 / 100, Image.SCALE_SMOOTH);
        circuit = new ImageIcon(imageRedimensionnee);
        JLabel labelCircuit = new JLabel();
        labelCircuit.setIcon(circuit);
        labelCircuit.setVerticalAlignment(JLabel.CENTER);
        labelCircuit.setHorizontalAlignment(JLabel.CENTER);
        labelCircuit.setBounds((largeur * 52 / 100) - circuit.getIconWidth() / 2, - (circuit.getIconHeight() * 9 / 100), circuit.getIconWidth(), circuit.getIconHeight());
        panelJeu.add(labelCircuit, Integer.valueOf(1));

        // Eléments
        afficherZoneDeTexte();
        afficherVoitures(circuit);

        // Bouton Menu principal
        boutonRetour.setBounds(largeur - (largeur * 12 / 100), (hauteur / 2) + (hauteur * 4 / 100), largeur * 12 / 100, hauteur * 7 / 100);
        panelJeu.add(boutonRetour, Integer.valueOf(2));
        if(boutonRetour.getActionListeners().length == 1)
        {
            boutonRetour.removeActionListener(boutonRetour.getActionListeners()[0]);
        }

        // Bouton fin de mon tour
        boutonFindeTour.setBounds(largeur - 2*(largeur * 12 / 100), (hauteur / 2) + 9*(hauteur * 4 / 100), largeur * 12 / 100, hauteur * 7 / 100);
        panelJeu.add(boutonFindeTour, Integer.valueOf(2));
        if(boutonFindeTour.getActionListeners().length == 1)
        {
            boutonFindeTour.removeActionListener(boutonFindeTour.getActionListeners()[0]);
        }
		
        // Bouton "Nouvelle partie"
        boutonNouvellePartie.setBounds(largeur - (largeur * 12 / 100), (hauteur / 2) - (hauteur * 4 / 100), largeur * 12 / 100, hauteur * 7 / 100);
        panelJeu.add(boutonNouvellePartie, Integer.valueOf(2));
        if(boutonNouvellePartie.getActionListeners().length == 1)
        {
            boutonNouvellePartie.removeActionListener(boutonNouvellePartie.getActionListeners()[0]);
        }

        //BoutonPioche
        boutonPioche.setBounds((largeur * 52 / 100) - (circuit.getIconWidth() * 18 / 100), (circuit.getIconHeight() * 45 / 100), (circuit.getIconWidth() * 155 / 1000), (circuit.getIconHeight() * 13 / 100));
        //boutonPioche.setBackground(new Color(109, 121, 132));
        panelJeu.add(boutonPioche, Integer.valueOf(2));
        if(boutonPioche.getActionListeners().length == 1)
        {
            boutonPioche.removeActionListener(boutonPioche.getActionListeners()[0]);
        }

        //BoutonDéfausse
        boutonDefausse.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 25 / 1000), (circuit.getIconHeight() * 45 / 100), (circuit.getIconWidth() * 155 / 1000), (circuit.getIconHeight() * 13 / 100));
        panelJeu.add(boutonDefausse, Integer.valueOf(5));
        if(boutonDefausse.getActionListeners().length == 1)
        {
            boutonDefausse.removeActionListener(boutonDefausse.getActionListeners()[0]);
        }

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);
    }

    public void afficherCartesJoueur(ArrayList<Carte> main){
        if(hauteurCarte == 0)
        {
            hauteurCarte = main.get(0).getImage().getIconHeight();
        }
        for(int i = 0; i < main.size(); i++){
            Carte carte = main.get(i);
            ImageIcon image = carte.getImage();
            int larg = 125; 
            int y = (hauteur * 97 / 100) - (hauteurCarte);
            int x = (largeur / 2 - 75) - (larg * 3) + (larg * i);
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
            bouton.setBounds(x, y, larg, haut);
            bouton.setFocusPainted(false);
            bouton.setContentAreaFilled(false);
            bouton.setVisible(true);
            panelJeu.add(bouton);
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


    public void creerAffichageAttaques() {
        // Panneau global pour Joueur
        JPanel panneauGlobalJoueur = new JPanel(new BorderLayout());
        JLabel labelJoueur = new JLabel("Joueur", JLabel.CENTER);
        panneauGlobalJoueur.add(labelJoueur, BorderLayout.NORTH);

        panneauAttaquesJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesJoueur.setBackground(Color.LIGHT_GRAY);
        panneauGlobalJoueur.add(panneauAttaquesJoueur, BorderLayout.CENTER);
        panneauGlobalJoueur.setBounds(0 , (int)(hauteur * 0.01),(int)(largeur * 0.175), (int)(hauteur * 0.12));
        panelJeu.add(panneauGlobalJoueur, Integer.valueOf(3));

        // Panneau global pour CPU Fast
        JPanel panneauGlobalCPUFast = new JPanel(new BorderLayout());
        JLabel labelCPUFast = new JLabel("CPU Fast", JLabel.CENTER);
        panneauGlobalCPUFast.add(labelCPUFast, BorderLayout.NORTH);

        panneauAttaquesCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesCPUFast.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUFast.add(panneauAttaquesCPUFast, BorderLayout.CENTER);
        panneauGlobalCPUFast.setBounds(0 , (int)(hauteur * 0.14),(int)(largeur * 0.175), (int)(hauteur * 0.12));
        panelJeu.add(panneauGlobalCPUFast, Integer.valueOf(3));

        // Panneau global pour CPU Agro
        JPanel panneauGlobalCPUAgro = new JPanel(new BorderLayout());
        JLabel labelCPUAgro = new JLabel("CPU Agro", JLabel.CENTER);
        panneauGlobalCPUAgro.add(labelCPUAgro, BorderLayout.NORTH);

        panneauAttaquesCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauAttaquesCPUAgro.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUAgro.add(panneauAttaquesCPUAgro, BorderLayout.CENTER);
        panneauGlobalCPUAgro.setBounds(0 , (int)(hauteur * 0.27),(int)(largeur * 0.175), (int)(hauteur * 0.12));
        panelJeu.add(panneauGlobalCPUAgro, Integer.valueOf(3));
    }

    public void creerAffichageBottes() {
        // Panneau global pour Joueur
        JPanel panneauGlobalJoueur = new JPanel(new BorderLayout());
        JLabel labelJoueur = new JLabel("Joueur", JLabel.CENTER);
        panneauGlobalJoueur.add(labelJoueur, BorderLayout.NORTH);

        panneauBottesJoueur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesJoueur.setBackground(Color.LIGHT_GRAY);
        panneauGlobalJoueur.add(panneauBottesJoueur, BorderLayout.CENTER);
        panneauGlobalJoueur.setBounds(largeur - (int)(largeur * 0.14), (int)(hauteur * 0.01),(int)(largeur * 0.14), (int)(hauteur * 0.12));
        panelJeu.add(panneauGlobalJoueur, Integer.valueOf(3));

        // Panneau global pour CPU Fast
        JPanel panneauGlobalCPUFast = new JPanel(new BorderLayout());
        JLabel labelCPUFast = new JLabel("CPU Fast", JLabel.CENTER);
        panneauGlobalCPUFast.add(labelCPUFast, BorderLayout.NORTH);

        panneauBottesCPUFast = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesCPUFast.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUFast.add(panneauBottesCPUFast, BorderLayout.CENTER);
        panneauGlobalCPUFast.setBounds(largeur - (int)(largeur * 0.14), (int)(hauteur * 0.14),(int)(largeur * 0.14), (int)(hauteur * 0.12));
        panelJeu.add(panneauGlobalCPUFast, Integer.valueOf(3));

        // Panneau global pour CPU Agro
        JPanel panneauGlobalCPUAgro = new JPanel(new BorderLayout());
        JLabel labelCPUAgro = new JLabel("CPU Agro", JLabel.CENTER);
        panneauGlobalCPUAgro.add(labelCPUAgro, BorderLayout.NORTH);

        panneauBottesCPUAgro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panneauBottesCPUAgro.setBackground(Color.LIGHT_GRAY);
        panneauGlobalCPUAgro.add(panneauBottesCPUAgro, BorderLayout.CENTER);
        panneauGlobalCPUAgro.setBounds(largeur - (int)(largeur * 0.14), (int)(hauteur * 0.27),(int)(largeur * 0.14), (int)(hauteur * 0.12));
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
            ImageIcon image = new ImageIcon(carte.getImage().getImage().getScaledInstance((int)(largeur * 0.03), (int)(hauteur * 0.084), Image.SCALE_SMOOTH));
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
            ImageIcon image = new ImageIcon(carte.getImage().getImage().getScaledInstance((int)(largeur * 0.03), (int)(hauteur * 0.084), Image.SCALE_SMOOTH));
            JLabel label = new JLabel(image);
            panneau.add(label); // Ajouter la carte au panneau
        }
    }
    
    
    


    public ArrayList<JButton> getBoutonMainsJoueurs()
    {
        return boutonsMainJoueur;
    }

    private void afficherZoneDeTexte(){
        System.out.println("Affiche");
        // Zone affichage des messages
        JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.GRAY);
		messagePanel.setBounds(0, hauteur * 50 / 100, largeur * 15 / 100, hauteur * 49 / 100);
		messagePanel.setLayout(new BorderLayout());
        panelJeu.add(messagePanel);

        textArea.setBounds(0, hauteur * 50 / 100, largeur * 15 / 100, hauteur * 49 / 100);
        textArea.setEditable(false);
        textArea.setBackground(Color.PINK);
        panelJeu.add(textArea);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, hauteur * 50 / 100, largeur * 15 / 100, hauteur * 49 / 100);
        panelJeu.add(scrollPane);
        messagePanel.add(scrollPane);
        textArea.setText("");
    }

    public void setNomDeLaPartie(String nomDeLaPartie)
    {
        this.nomDeLaPartie = nomDeLaPartie;
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
        ImageIcon voiture1 = new ImageIcon("assets/voiture rouge roule2.gif");
        boutonVoiture1 = new JButton("", voiture1);
        boutonVoiture1.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture1.setFocusPainted(false);
        boutonVoiture1.setContentAreaFilled(false);
        boutonVoiture1.setOpaque(false);
        boutonVoiture1.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100), (circuit.getIconHeight() * 64 / 100), (circuit.getIconWidth() / 25), (voiture1.getIconHeight() * 100 / 100));
        panelJeu.add(boutonVoiture1, Integer.valueOf(2));

        // Voiture 2
        ImageIcon voiture2 = new ImageIcon("assets/voiture rouge roule2.gif"); // A changer
        boutonVoiture2 = new JButton("", voiture2);
        boutonVoiture2.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture2.setFocusPainted(false);
        boutonVoiture2.setContentAreaFilled(false);
        boutonVoiture2.setOpaque(false);
        boutonVoiture2.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100) + (circuit.getIconWidth() * 35 / 1000), (circuit.getIconHeight() * 64 / 100), (circuit.getIconWidth() / 25), (voiture1.getIconHeight() * 100 / 100));
        panelJeu.add(boutonVoiture2, Integer.valueOf(2));

        // Voiture 3
        ImageIcon voiture3 = new ImageIcon("assets/voiture rouge roule2.gif"); // A changer
        boutonVoiture3 = new JButton("", voiture3);
        boutonVoiture3.setBorder(BorderFactory.createEmptyBorder());
        boutonVoiture3.setFocusPainted(false);
        boutonVoiture3.setContentAreaFilled(false);
        boutonVoiture3.setOpaque(false);
        boutonVoiture3.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100) + (circuit.getIconWidth() * 35 / 1000) * 2, (circuit.getIconHeight() * 64 / 100), (circuit.getIconWidth() / 25), (voiture1.getIconHeight() * 100 / 100));
        panelJeu.add(boutonVoiture3, Integer.valueOf(2));
    }

    /*
     * Avance la voiture en fonction des kilomètres totaux du joueur
     * Seulement la première ligne droite pour l'instant
     * joueur = id du joueur
     */
    public void avancerVoiture(int distance, int joueur){
        int deplacementY = 0;
        int pourcentageX = 0;
        int vitesse = 5;
        JButton voiture;
        int trop = 0;
        int nouvelleDistance = distance;
        System.out.println(kilometreV1);

        if(joueur == 0){
            voiture = boutonVoiture1; 
            if(distance <= 175 && kilometreV1 != 0){
                nouvelleDistance -= kilometreV1 - 25;
                System.out.println(nouvelleDistance);
            }
        }else if(joueur == 1){
            voiture = boutonVoiture2; 
            if(distance < 175 && kilometreV2 != 0){
                nouvelleDistance -= kilometreV2 - 25;
                System.out.println(nouvelleDistance);
            }
        }else{
            voiture = boutonVoiture3; 
            if(distance < 175 && kilometreV3 != 0){
                nouvelleDistance -= kilometreV3 - 25;
                System.out.println(nouvelleDistance);
            }
        }
        
        if((joueur == 0 && distance > kilometreV1) | (joueur == 1 && distance > kilometreV2) | (joueur == 2 && distance > kilometreV3)){
            Rectangle position = voiture.getBounds();
            
            // Calcul la position finale
            if(distance <= 25){
                deplacementY = (circuit.getIconHeight() * 120 / 1000);
                position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("assets/voiture rouge roule2.gif") == 0){ // Entre 50 et 150 km = voiture vers le haut
                deplacementY = (25 * 20 / 100) + ((nouvelleDistance / 25)) * (circuit.getIconHeight() * 90 / 1000);
                position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersGauche.gif") == 0 && distance > 175){ // Entre 175 et 375 km = voiture vers la gauche
                pourcentageX = ((distance - 175) / 25 ) * (circuit.getIconWidth() * 81 / 1300);
                position.setBounds((int)position.getX() - pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersBas.gif") == 0 && distance > 400){ // Entre 400 et 525 km = voiture vers le bas
                deplacementY = (((distance - 400) / 25)) * (circuit.getIconHeight() * 73 / 1000);
                position.setBounds((int)position.getX(), (int)position.getY() + deplacementY, (int)position.getWidth(), (int)position.getHeight());
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersDroite.gif") == 0 && distance > 550){ // Entre 550 et 700 km = voiture vers la droite
                pourcentageX = ((distance - 550) / 25 ) * (circuit.getIconWidth() * 81 / 1300);
                position.setBounds((int)position.getX() + pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            }
            
            // Fait déplacer la voiture
            if(voiture.getIcon().toString().compareTo("assets/voiture rouge roule2.gif") == 0){
                Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                    int x = voiture.getX();
                    int y = voiture.getY();
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Mettre à jour les coordonnées
                        y -= vitesse;
                        voiture.setLocation(x, y);
        
                        if(distance >= 175 && (voiture.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() - ((voiture.getIcon().getIconHeight() * 50 / 100) * joueur) | y < (circuit.getIconHeight() * 125 / 1000) - joueur * (circuit.getIconHeight() * 50 / 1000))) {
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = 175;
                            }else if(joueur == 1){
                                kilometreV2 = 175;
                            }else{
                                kilometreV3 = 175;
                            }
                            if(distance >= 175){ // Tourne vers la gauche
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersGauche.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 60 / 100), y - (voiture1.getIconHeight() * 2 / 100) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }else if(distance < 175 && (voiture.getY() - (circuit.getIconHeight() * 82 / 1000) <= (int)position.getY() | y < (circuit.getIconHeight() * 125 / 1000) - joueur * (circuit.getIconHeight() * 50 / 1000))){
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = distance;
                            }else if(joueur == 1){
                                kilometreV2 = distance;
                            }else{
                                kilometreV3 = distance;
                            }
                            if(distance >= 175){ // Tourne vers la gauche
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersGauche.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 60 / 100), y - (voiture1.getIconHeight() * 2 / 100) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }
                    }
                });
                timer.start();
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersGauche.gif") == 0){
                Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                    int x = voiture.getX();
                    int y = voiture.getY();
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Mettre à jour les coordonnées
                        x -= vitesse;
                        voiture.setLocation(x, y);
        
                        if(distance >= 400 && (voiture.getX() + (circuit.getIconWidth() * 25 / 1300) <= (int)position.getX() - ((voiture.getIcon().getIconWidth() * 50 / 100) * joueur) | x < (circuit.getIconWidth() * 340 / 1000) - joueur * (circuit.getIconWidth() * 40 / 1000))) {
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = 400;
                            }else if(joueur == 1){
                                kilometreV2 = 400;
                            }else{
                                kilometreV3 = 400;
                            }
                            if(distance >= 400){ // Tourne vers la bas
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersBas.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds((largeur * 52 / 100) - (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 135 / 100) - (voiture1.getIconWidth() * 86 / 100) * joueur, (circuit.getIconWidth() * 70 / 1000), 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }else if(distance < 400 && (voiture.getX() + (circuit.getIconWidth() * 25 / 1300) <= (int)position.getX() | x < (circuit.getIconWidth() * 340 / 1000) - joueur * (circuit.getIconWidth() * 40 / 1000))){
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = distance;
                            }else if(joueur == 1){
                                kilometreV2 = distance;
                            }else{
                                kilometreV3 = distance;
                            }
                            if(distance >= 400){ // Tourne vers la bas
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersBas.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds((largeur * 52 / 100) - (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 135 / 100) - (voiture1.getIconWidth() * 86 / 100) * joueur, (circuit.getIconWidth() * 70 / 1000), 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }
                    }
                });
                timer.start();
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersBas.gif") == 0){
                Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                    int x = voiture.getX();
                    int y = voiture.getY();
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Mettre à jour les coordonnées
                        y += vitesse;
                        voiture.setLocation(x, y);
        
                        if(distance >= 550 && (voiture.getY() - (circuit.getIconWidth() * 20 / 1000) > (int)position.getY() + ((voiture.getIcon().getIconHeight() * 50 / 100) * joueur) | y > (circuit.getIconHeight() * 561 / 1000) + joueur * (circuit.getIconHeight() * 45 / 1000))) {
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = 550;
                            }else if(joueur == 1){
                                kilometreV2 = 550;
                            }else{
                                kilometreV3 = 550;
                            }
                            if(distance >= 550){ // Tourne vers la droite
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersDroite.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds(x + (voiture1.getIconWidth() * 10 / 100) + (voiture1.getIconWidth() * 40 / 100) * joueur, y + (circuit.getIconHeight() * 17 / 1000) + (voiture1.getIconHeight() * 100 / 100) + (voiture1.getIconHeight() * 2 / 100) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }else if(distance < 550 && (voiture.getY() - (circuit.getIconWidth() * 20 / 1000) > (int)position.getY() | y > (circuit.getIconHeight() * 561 / 1000) + joueur * (circuit.getIconHeight() * 45 / 1000))) {
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = distance;
                            }else if(joueur == 1){
                                kilometreV2 = distance;
                            }else{
                                kilometreV3 = distance;
                            }
                            if(distance >= 550){ // Tourne vers la droite
                                ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersDroite.gif");
                                voiture.setIcon(voiture1);
                                voiture.setBounds(x + (voiture1.getIconWidth() * 10 / 100) + (voiture1.getIconWidth() * 40 / 100) * joueur, y + (circuit.getIconHeight() * 17 / 1000) + (voiture1.getIconHeight() * 100 / 100) + (voiture1.getIconHeight() * 2 / 100) * joueur, 
                                                            (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                                avancerVoiture(distance, joueur);
                            }
                        }
                    }
                });
                // Lancer le Timer
                timer.start();
            }else if(voiture.getIcon().toString().compareTo("assets/VoitureRougeRouleVersDroite.gif") == 0){
                Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                    int x = voiture.getX();
                    int y = voiture.getY();
        
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Mettre à jour les coordonnées
                        x += vitesse;
                        voiture.setLocation(x, y);
        
                        if(voiture.getX()  > (int)position.getX()) {
                            ((Timer) e.getSource()).stop(); // Arrêter le Timer
                            if(joueur == 0){
                                kilometreV1 = distance;
                            }else if(joueur == 1){
                                kilometreV2 = distance;
                            }else{
                                kilometreV3 = distance;
                            }
                        }
                    }
                });
                timer.start();
            }
        }
        nouvelleDistance = 0;
        
    }
}