import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.*;
 
public class Partie {
    private int points;
    private ArrayList<Joueur> joueurs;
    private static ArrayList<Carte> pioche;
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;
    private JTextArea textArea;
    private JScrollPane scrollPane;

    public Partie(){
        // Initialisation
        
        fenetreMenu = new JFrame("1000 Bornes");

        // Fenetre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        hauteur = (int)dimension.getHeight();
        largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);
    } 

    /*
     * Affiche la fenetre du menu de début
     */
    public void creerFenetreMenu(){
        // Bouton jouer
        JPanel jouerPanel = new JPanel();
		jouerPanel.setBounds(0, 0, largeur, hauteur / 5);
		jouerPanel.setLayout(new BorderLayout());
        fenetreMenu.add(jouerPanel);
        JLabel labelJouer = new JLabel();
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                points = 0;
                joueurs = new ArrayList<Joueur>();
                pioche = new ArrayList<Carte>();
                nouvellePartie();
                creerFenetreJeu();
            }
        });
        boutonJouer.setBounds(largeur / 2 - 75, hauteur / 5 - 50, 150, 50);
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
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.setBounds(largeur / 2 - 75, 0, 150, 50);
        boutonQuitter.addActionListener(e -> System.exit(0));
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
    private void creerFenetreJeu(){

        JPanel panelJeu = new JPanel();
        fenetreMenu.setContentPane(panelJeu);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();

        // Eléments
        afficherCartesJoueur();
        afficherZoneDeTexte();

        // Bouton Menu principal
        JPanel quitterPanel = new JPanel();
		quitterPanel.setBounds(largeur - 155, hauteur / 2, largeur, hauteur / 5);
		quitterPanel.setLayout(new BorderLayout());
        JLabel labelQuitter = new JLabel();
        JButton boutonQuitter = new JButton("Menu Principal");
        boutonQuitter.setBounds(0, 0, 150, 50);
        boutonQuitter.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(fenetreMenu, "Voulez-vous vraiment retourner au menu principal ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            // Vérifier la réponse de l'utilisateur
            if (option == JOptionPane.YES_OPTION) {
                fenetreMenu.getContentPane().removeAll();
                fenetreMenu.repaint();
                fenetreMenu.revalidate();
                creerFenetreMenu(); 
            }
        }); 
        labelQuitter.add(boutonQuitter);
        quitterPanel.add(labelQuitter);
        fenetreMenu.add(quitterPanel);

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
        // Bouton "Écran principal"
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(largeur - 155, hauteur / 2 - 75, largeur, hauteur / 5);
        mainPanel.setLayout(new BorderLayout());
        JLabel labelMain = new JLabel();
        JButton boutonMain = new JButton("Nouvelle partie");
        boutonMain.setBounds(0, 0, 150, 50);
        boutonMain.addActionListener(e -> {
            fenetreMenu.getContentPane().removeAll();
            fenetreMenu.repaint();
            fenetreMenu.revalidate(); 
            points = 0;
            joueurs = new ArrayList<Joueur>();
            pioche = new ArrayList<Carte>();
            nouvellePartie();
            creerFenetreJeu(); 
        });

        labelMain.add(boutonMain);
        mainPanel.add(labelMain);
        fenetreMenu.add(mainPanel);

        fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);
    }

    /*
     * Pas fini ajouter fonction pour utiliser carte selectionee
     */
    private void afficherCartesJoueur(){
        for(int i = 0; i < 6; i++){
            JPanel imagePanel = new JPanel();
            Carte carte = joueurs.get(0).getMain().get(i);
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
            int j = i;
            bouton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    ajouterMessage("\n Carte " + (j + 1) + " (" + carte.getNom()+")");
                    joueurs.get(0).jouerCarte(carte);
                }
            });
            fenetreMenu.add(bouton);
            imagePanel.add(bouton);
        }
    }

    private void afficherZoneDeTexte(){
        // Zone affichage des messages
        JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.GRAY);
		messagePanel.setBounds(0, hauteur / 2, 250, hauteur / 2 - 75);
		messagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(messagePanel);

        textArea = new JTextArea("Début de la partie");
        textArea.setBounds(0, hauteur / 2, 250, hauteur / 2);
        textArea.setEditable(false);
        textArea.setBackground(Color.PINK);
        fenetreMenu.add(textArea);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(0, hauteur / 2, 250, hauteur / 2);
        fenetreMenu.add(scrollPane);
        messagePanel.add(scrollPane);
    }

    private void ajouterMessage(String message){
        textArea.append(message);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setValue(verticalBar.getMaximum());
    }

    /*
     * Initialisation de la pioche #FINI
     */
    private void initialiserPioche(){
        pioche = new ArrayList<Carte>();

        //Cartes Distances
        for(int i = 0; i < 12; i++){
            if(i < 10){
                pioche.add(new Distance(TypeCarte._25KM, 25));
                pioche.add(new Distance(TypeCarte._50KM, 50));
                pioche.add(new Distance(TypeCarte._75KM, 75));

                if(i < 8){
                    pioche.add(new Distance(TypeCarte._200KM, 200));
                }
            }
            pioche.add(new Distance(TypeCarte._100KM, 100));
        }

        //Cartes Attaques
        for(int i = 0; i < 6; i++){
            if(i < 5){
                pioche.add(new Attaque(TypeCarte.FEU_ROUGE));

                if(i < 3){
                    pioche.add(new Attaque(TypeCarte.ACCIDENT));
                    pioche.add(new Attaque(TypeCarte.PANNE_D_ESSENCE));
                    pioche.add(new Attaque(TypeCarte.CREVAISON));
                }
            }
            pioche.add(new Attaque(TypeCarte.LIMITATION_DE_VITESSE));
        }

        //Cartes Parades
        for(int i = 0; i < 14; i++){
            if(i < 6){
                pioche.add(new Parade(TypeCarte.REPARATION));
                pioche.add(new Parade(TypeCarte.ESSENCE));
                pioche.add(new Parade(TypeCarte.ROUE_DE_SECOURS));
                pioche.add(new Parade(TypeCarte.FIN_LIMITATION_VITESSE));
            }
            pioche.add(new Parade(TypeCarte.FEU_VERT));
        }

        //Cartes Bottes
        pioche.add(new Botte(TypeCarte.AS_DU_VOLANT));
        pioche.add(new Botte(TypeCarte.CAMION_CITERNE));
        pioche.add(new Botte(TypeCarte.INCREVABLE));
        pioche.add(new Botte(TypeCarte.VEHICULE_PRIORITAIRE));

        //Melanger la pioche
        Collections.shuffle(pioche);
    }
    
    public int taillePioche(){
        return pioche.size();
    }

    public static ArrayList<Carte> getPioche(){
        return pioche;
    }

    /*
     * Création de la partie
     * PAS FINI
     */
    public void nouvellePartie(){
        initialiserPioche();
        System.out.println(getPioche().size());
        joueurs.add(new Utilisateur("Moi", 0, 0));
        for(int i = 0; i < 6; i++){
            joueurs.get(0).ajouterCarte(pioche.get(i));
            pioche.remove(i);
        }
        System.out.println(joueurs.get(0).getMain().size());
        joueurs.add(new CPUAgro("Agro", 0, 1));
        joueurs.add(new CPUFast("Fast", 0, 2));
        System.out.println(getPioche().size());

        //jouer();
    }

    /*
     * Verifie si la carte est jouable
     * PAS FINI
     */
    public boolean jouerCarte(Carte c, Joueur u, Joueur cible){
        return true;
    }

    /*
     * Boucle du jeu
     * PAS FINI
     */
    private void jouer(){
        
    }

    /*
     * Renvoie vrai si un joueur a gagné a au moins 700 km
     * PAS FINI
     */
    private boolean gagnant(){
        return false;
    }
    
    public void afficherAction(Carte c, Joueur u, Joueur cible){};
    public void ajouterHistorique(Carte c, Joueur u, Joueur cible){};
    public void nouvelleManche(){};
    public void quitterPartie(){};
    public void sauvegarderPartie(){};
}
