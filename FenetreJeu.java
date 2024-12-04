import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
import java.awt.Insets;

public class FenetreJeu {
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton boutonJouer;
    private JButton boutonQuitter;
    private JButton boutonRetour;
    private JButton boutonPioche;
    private JButton boutonNouvellePartie;
    private ArrayList<JButton> boutonsMainJoueur;
    private GraphicsEnvironment env;
    private GraphicsDevice ecran;

    public FenetreJeu(){
        // Initialisation
        boutonJouer = new JButton("Jouer");
        boutonQuitter = new JButton("Quitter");
        boutonRetour = new JButton("Menu Principal");
        boutonNouvellePartie = new JButton("Nouvelle partie");
        boutonPioche = new JButton("Pioche (temporaire)");
        fenetreMenu = new JFrame("1000 Bornes");
        textArea = new JTextArea("Début de la partie");
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

    /*
     * Affiche la fenetre du menu de début
     */
    public void creerFenetreMenu(){

        // Initialisation menuPanel et de le gestionnaire de disposition
        JPanel menuPanel  = new JPanel();
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

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetreMenu.add(menuPanel);
        fenetreMenu.setContentPane(menuPanel);
		fenetreMenu.setVisible(true);	
    }

    /*
     * Affiche la fenêtre de jeu
     */
    public void creerFenetreJeu(){
        JLayeredPane panelJeu = new JLayeredPane();
        fenetreMenu.setContentPane(panelJeu);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();


        // Circuit
        ImageIcon circuit = new ImageIcon("Images/circuit.png");
        JLabel labelCircuit = new JLabel();
        labelCircuit.setIcon(circuit);
        labelCircuit.setVerticalAlignment(JLabel.CENTER);
        labelCircuit.setHorizontalAlignment(JLabel.CENTER);
        labelCircuit.setBounds(largeur/2-700, -70, 1300, 1001);
        panelJeu.add(labelCircuit, Integer.valueOf(1));

        // Eléments
        afficherZoneDeTexte();

        // Bouton Menu principal
        boutonRetour.setBounds(largeur - 155, hauteur / 2, 150, 50);
        fenetreMenu.add(boutonRetour, Integer.valueOf(2));

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Bouton "Écran principal"
        boutonNouvellePartie.setBounds(largeur - 155, hauteur / 2 - 75, 150, 50);
        fenetreMenu.add(boutonNouvellePartie, Integer.valueOf(2));

        //BoutonPioche
        boutonPioche.setBounds(largeur/2-255, hauteur/2-47, 150, 75);
        fenetreMenu.add(boutonPioche, Integer.valueOf(2));

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);
    }

    public void afficherCartesJoueur(ArrayList<Carte> main){
        for(int i = 0; i < 6; i++){
            JPanel imagePanel = new JPanel();
            Carte carte = main.get(i);
            ImageIcon image = carte.getImage();
            imagePanel.setBackground(Color.pink);
            imagePanel.setBounds(largeur / 2 - (125 * 3) + (125 * i), hauteur - 220, 125, hauteur / 5);
            imagePanel.setLayout(new BorderLayout());
            fenetreMenu.add(imagePanel);
            JButton bouton = new JButton("", image);
            bouton.setBackground(Color.PINK);
            bouton.setBounds(largeur / 2 - (125 * 3) + (125 * i), hauteur - 220, 125, hauteur / 5);
            bouton.setFocusPainted(false);
            bouton.setContentAreaFilled(false);
            fenetreMenu.add(bouton);
            imagePanel.add(bouton);
            boutonsMainJoueur.add(i, bouton);
        }
    }

    private void afficherZoneDeTexte(){
        System.out.println("Affiche");
        // Zone affichage des messages
        JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.GRAY);
		messagePanel.setBounds(0, hauteur / 2, 250, hauteur / 2 - 75);
		messagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(messagePanel);

        textArea.setBounds(0, hauteur / 2, 250, hauteur / 2);
        textArea.setEditable(false);
        textArea.setBackground(Color.PINK);
        fenetreMenu.add(textArea);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, hauteur / 2, 250, hauteur / 2);
        fenetreMenu.add(scrollPane);
        messagePanel.add(scrollPane);
        textArea.setText("");
    }

    public void ajouterMessage(String message){
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    }
}