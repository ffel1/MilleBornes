package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
    private ImageIcon circuit;

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
        boutonCPUAgro.addActionListener(action);
    }

    /**
     * Permet de connecter une action au bouton "BoutonCPUAgro"
     * @param action L'action à exécuter lors du clic sur le bouton
     */
    public void ajouterActionBoutonCPUFast(ActionListener action){
        boutonCPUFast.addActionListener(action);
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

        //BoutonBoutonCPUAgro
        boutonCPUAgro.setBounds(largeur - (largeur*10/100), hauteur*10/100, (circuit.getIconWidth() * 10 / 100), (circuit.getIconHeight() * 5 / 100));
        panelJeu.add(boutonCPUAgro, Integer.valueOf(2));
        if(boutonCPUAgro.getActionListeners().length == 1)
        {
            boutonCPUAgro.removeActionListener(boutonCPUAgro.getActionListeners()[0]);
        }
        //BoutonBoutonCPUFast
        boutonCPUFast.setBounds(largeur - (largeur*10/100), hauteur*20/100, (circuit.getIconWidth() * 10 / 100), (circuit.getIconHeight() * 5 / 100));
        panelJeu.add(boutonCPUFast, Integer.valueOf(2));
        if(boutonCPUFast.getActionListeners().length == 1)
        {
            boutonCPUFast.removeActionListener(boutonCPUFast.getActionListeners()[0]);
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

    public void ajouterMessage(String message){
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
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
    }

    /*
     * Avance la voiture en fonction des kilomètres totaux du joueur
     * Seulement la première ligne droite pour l'instant
     */
    public void avancerVoiture(int distance){
        int deplacementY = 0;
        int pourcentageX = 0;
        int vitesse = 5;
        Rectangle position = boutonVoiture1.getBounds();
        
        // Calcul la position finale
        if(distance <= 25){
            deplacementY = circuit.getIconHeight() * (distance * 20 / 100) / 100;
            position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, (int)position.getWidth(), (int)position.getHeight());
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/voiture rouge roule2.gif") == 0){ // Entre 50 et 150 km = voiture vers le haut
            deplacementY = (25 * 20 / 100) + ((distance / 25)) * (circuit.getIconHeight() * 85 / 1000);
            position.setBounds((int)position.getX(), (int)position.getY() - deplacementY, (int)position.getWidth(), (int)position.getHeight());
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersGauche.gif") == 0 && distance > 175){ // Entre 175 et 375 km = voiture vers la gauche
            pourcentageX = ((distance - 175) / 25 ) * (circuit.getIconWidth() * 81 / 1300);
            position.setBounds((int)position.getX() - pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersBas.gif") == 0 && distance > 400){ // Entre 400 et 525 km = voiture vers le bas
            deplacementY = (((distance - 400) / 25)) * (circuit.getIconHeight() * 73 / 1000);
            position.setBounds((int)position.getX(), (int)position.getY() + deplacementY, (int)position.getWidth(), (int)position.getHeight());
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersDroite.gif") == 0 && distance > 550){ // Entre 550 et 700 km = voiture vers la droite
            pourcentageX = ((distance - 550) / 25 ) * (circuit.getIconWidth() * 81 / 1300);
            position.setBounds((int)position.getX() + pourcentageX, (int)position.getY(), (int)position.getWidth(), (int)position.getHeight());
            System.out.println(((distance - 175) / 25));
        }
        
        // Fait déplacer la voiture
        if(boutonVoiture1.getIcon().toString().compareTo("assets/voiture rouge roule2.gif") == 0){
            Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                int x = boutonVoiture1.getX();
                int y = boutonVoiture1.getY();
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mettre à jour les coordonnées
                    y -= vitesse;
                    boutonVoiture1.setLocation(x, y);

    
                    if(boutonVoiture1.getY() - (circuit.getIconWidth() * 50 / 1000) <= (int)position.getY() | y < (circuit.getIconWidth() * 85 / 1000)) {
                        ((Timer) e.getSource()).stop(); // Arrêter le Timer
                        if(distance >= 175){ // Tourne vers la gauche
                            ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersGauche.gif");
                            boutonVoiture1.setIcon(voiture1);
                            boutonVoiture1.setBounds((largeur * 52 / 100) + (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 60 / 100), (circuit.getIconWidth() * 50 / 1000) + (voiture1.getIconHeight() * 105 / 100), 
                                                        (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                            avancerVoiture(distance);
                        }
                    }
                }
            });
            timer.start();
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersGauche.gif") == 0){
            Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                int x = boutonVoiture1.getX();
                int y = boutonVoiture1.getY();
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mettre à jour les coordonnées
                    x -= vitesse;
                    boutonVoiture1.setLocation(x, y);
    
                    if(boutonVoiture1.getX() + (circuit.getIconWidth() * 25 / 1300) <= (int)position.getX() | x < (circuit.getIconWidth() * 340 / 1000)) {
                        ((Timer) e.getSource()).stop(); // Arrêter le Timer
                        if(distance >= 400){ // Tourne vers la bas
                            ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersBas.gif");
                            boutonVoiture1.setIcon(voiture1);
                            boutonVoiture1.setBounds((largeur * 52 / 100) - (circuit.getIconWidth() * 29 / 100) - (voiture1.getIconWidth() * 135 / 100), (circuit.getIconWidth() * 70 / 1000), 
                                                        (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                            avancerVoiture(distance);
                        }
                    }
                }
            });
            timer.start();
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersBas.gif") == 0){
            Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                int x = boutonVoiture1.getX();
                int y = boutonVoiture1.getY();
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mettre à jour les coordonnées
                    y += vitesse;
                    boutonVoiture1.setLocation(x, y);
    
                    if(boutonVoiture1.getY() - (circuit.getIconWidth() * 20 / 1000) > (int)position.getY() | y > (circuit.getIconHeight() * 561 / 1000)) {
                        ((Timer) e.getSource()).stop(); // Arrêter le Timer
                        System.out.println("STOP");
                        if(distance >= 550){ // Tourne vers la droite
                            ImageIcon voiture1 = new ImageIcon("assets/VoitureRougeRouleVersDroite.gif");
                            boutonVoiture1.setIcon(voiture1);
                            boutonVoiture1.setBounds(x + (voiture1.getIconWidth() * 10 / 100), y + (circuit.getIconWidth() * 20 / 1000) + (voiture1.getIconHeight() * 105 / 100), 
                                                        (voiture1.getIconWidth() * 100 / 100), (voiture1.getIconHeight() * 100 / 100));
                            avancerVoiture(distance);
                        }
                    }
                }
            });
            // Lancer le Timer
            timer.start();
        }else if(boutonVoiture1.getIcon().toString().compareTo("assets/VoitureRougeRouleVersDroite.gif") == 0){
            Timer timer = new Timer(10, new ActionListener() { // Mise à jour toutes les 10 ms
                int x = boutonVoiture1.getX();
                int y = boutonVoiture1.getY();
    
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Mettre à jour les coordonnées
                    x += vitesse;
                    boutonVoiture1.setLocation(x, y);
    
                    if(boutonVoiture1.getX()  > (int)position.getX()) {
                        ((Timer) e.getSource()).stop(); // Arrêter le Timer
                    }
                }
            });
            timer.start();
        }
    }
}