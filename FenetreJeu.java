import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class FenetreJeu {
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton boutonJouer;
    private JButton boutonQuitter;
    private JButton boutonRetour;
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

    //modif
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

    /*
     * Affiche la fenetre du menu de début
     */
    public void creerFenetreMenu(){
        // Bouton jouer
        JPanel jouerPanel = new JPanel();
		jouerPanel.setBounds(0, 0, largeur, hauteur / 5);
		jouerPanel.setLayout(new BorderLayout());
        JLabel labelJouer = new JLabel();
        boutonJouer.setBounds(largeur / 2 - 75, hauteur / 5 - 75, 150, 50);
        labelJouer.add(boutonJouer);
        jouerPanel.add(labelJouer);

        // Image
        JPanel imagePanel = new JPanel();
		imagePanel.setBounds(0, hauteur / 5, largeur, 3 * hauteur / 5);
		imagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel);
        ImageIcon image = new ImageIcon("Images/MilleBornes.png");
        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setVerticalAlignment(JLabel.CENTER);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(labelImage);

        // Bouton quitter
        JPanel quitterPanel = new JPanel();
		quitterPanel.setBounds(0, hauteur * 4 / 5, largeur, hauteur / 5);
		quitterPanel.setLayout(new BorderLayout());
        JLabel labelQuitter = new JLabel();
        boutonQuitter.setBounds(largeur / 2 - 75, 0, 150, 50);
        labelQuitter.add(boutonQuitter);
        quitterPanel.add(labelQuitter);

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetreMenu.add(jouerPanel);
		fenetreMenu.add(imagePanel);
		fenetreMenu.add(quitterPanel);

        

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);	
    }

    /*
     * Affiche la fenêtre de jeu
     */
    public void creerFenetreJeu(){
        JPanel panelJeu = new JPanel();
        fenetreMenu.setContentPane(panelJeu);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();

        // Eléments
        afficherZoneDeTexte();

        // Bouton Menu principal
        JPanel quitterPanel = new JPanel();
		quitterPanel.setBounds(largeur - 155, hauteur / 2, largeur, hauteur / 5);
		quitterPanel.setLayout(new BorderLayout());
        JLabel labelQuitter = new JLabel();
        boutonRetour.setBounds(0, 0, 150, 50);
        labelQuitter.add(boutonRetour);
        quitterPanel.add(labelQuitter);
        fenetreMenu.add(quitterPanel);

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Bouton "Écran principal"
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(largeur - 155, hauteur / 2 - 75, largeur, hauteur / 5);
        mainPanel.setLayout(new BorderLayout());
        JLabel labelMain = new JLabel();
        boutonNouvellePartie.setBounds(0, 0, 150, 50);
        labelMain.add(boutonNouvellePartie);
        mainPanel.add(labelMain);
        fenetreMenu.add(mainPanel);

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);
    }

    public void afficherCartesJoueur(ArrayList<Carte> main){
        for(int i = 0; i < 6; i++){
            JPanel imagePanel = new JPanel();
            Carte carte = main.get(i);
            ImageIcon image = carte.getImage();
            imagePanel.setBackground(Color.pink);
            imagePanel.setBounds(largeur / 2 - (125 * 3) + (125 * i), hauteur - 250, 125, hauteur / 5);
            imagePanel.setLayout(new BorderLayout());
            fenetreMenu.add(imagePanel);
            JButton bouton = new JButton("", image);
            bouton.setBackground(Color.PINK);
            bouton.setBounds(largeur / 2 - (125 * 3) + (125 * i), hauteur - 250, 125, hauteur / 5);
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
        textArea.setText("Début de la partie");
    }

    public void ajouterMessage(String message){
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    }
}
