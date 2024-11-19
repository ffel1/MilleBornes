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
 
public class Partie {
    private int points;
    private ArrayList<Joueur> joueurs;
    private static ArrayList<Carte> pioche;
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;

    public Partie(){
        // Initialisation
        points = 0;
        joueurs = new ArrayList<Joueur>();
        pioche = new ArrayList<Carte>();
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
		fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);	
		fenetreMenu.add(jouerPanel);
		fenetreMenu.add(imagePanel);
		fenetreMenu.add(quitterPanel);
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

        // Bouton quitter
        JPanel quitterPanel = new JPanel();
		quitterPanel.setBounds(largeur - 155, hauteur / 2, largeur, hauteur / 5);
		quitterPanel.setLayout(new BorderLayout());
        JLabel labelQuitter = new JLabel();
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.setBounds(0, 0, 150, 50);
        boutonQuitter.addActionListener(e -> System.exit(0));
        labelQuitter.add(boutonQuitter);
        quitterPanel.add(labelQuitter);
        fenetreMenu.add(quitterPanel);

        // Zone affichage des messages
        JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.GRAY);
		messagePanel.setBounds(0, hauteur / 2, 250, hauteur / 2);
		messagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(messagePanel);

        fenetreMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetreMenu.setLayout(null);
		fenetreMenu.setVisible(true);	
    }

    /*
     * Pas fini
     */
    private void afficherCartesJoueur(){
        // Fentre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int)dimension.getHeight();
        int largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);

        // Carte 1
        JPanel imagePanel = new JPanel();
        ImageIcon image = joueurs.get(0).getMain().get(0).getImage();
        imagePanel.setBackground(Color.pink);
        imagePanel.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        imagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel);

        JButton bouton = new JButton("", image);
        bouton.setBackground(Color.PINK);
        bouton.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton.setFocusPainted(false);
        bouton.setContentAreaFilled(false);
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 1 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton);
        imagePanel.add(bouton);

        // Carte 2
        JPanel imagePanel2 = new JPanel();
        ImageIcon image2 = joueurs.get(0).getMain().get(1).getImage();
        imagePanel2.setBackground(Color.pink);
        imagePanel2.setBounds(largeur / 2 - (125 * 2), hauteur - 250, 125, hauteur / 5);
        imagePanel2.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel2);
        
        JButton bouton2 = new JButton("", image2);
        bouton2.setBackground(Color.PINK);
        bouton2.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton2.setFocusPainted(false);
        bouton2.setContentAreaFilled(false);
        bouton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 2 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton2);
        imagePanel2.add(bouton2);

        // Carte 3
        JPanel imagePanel3 = new JPanel();
        ImageIcon image3 = joueurs.get(0).getMain().get(2).getImage();
        imagePanel3.setBackground(Color.pink);
        imagePanel3.setBounds(largeur / 2 - 125, hauteur - 250, 125, hauteur / 5);
        imagePanel3.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel3);
        
        JButton bouton3 = new JButton("", image3);
        bouton3.setBackground(Color.PINK);
        bouton3.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton3.setFocusPainted(false);
        bouton3.setContentAreaFilled(false);
        bouton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 3 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton3);
        imagePanel3.add(bouton3);

        // Carte 4
        JPanel imagePanel4 = new JPanel();
        ImageIcon image4 = joueurs.get(0).getMain().get(3).getImage();
        imagePanel4.setBackground(Color.pink);
        imagePanel4.setBounds(largeur / 2, hauteur - 250, 125, hauteur / 5);
        imagePanel4.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel4);
        
        JButton bouton4 = new JButton("", image4);
        bouton4.setBackground(Color.PINK);
        bouton4.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton4.setFocusPainted(false);
        bouton4.setContentAreaFilled(false);
        bouton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 4 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton4);
        imagePanel4.add(bouton4);

        // Carte 5
        JPanel imagePanel5 = new JPanel();
        ImageIcon image5 = joueurs.get(0).getMain().get(4).getImage();
        imagePanel5.setBackground(Color.pink);
        imagePanel5.setBounds(largeur / 2 + 125, hauteur - 250, 125, hauteur / 5);
        imagePanel5.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel5);
        
        JButton bouton5 = new JButton("", image5);
        bouton5.setBackground(Color.PINK);
        bouton5.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton5.setFocusPainted(false);
        bouton5.setContentAreaFilled(false);
        bouton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 5 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton5);
        imagePanel5.add(bouton5);
 
        // Carte 6
        JPanel imagePanel6 = new JPanel();
        ImageIcon image6 = joueurs.get(0).getMain().get(5).getImage();
        imagePanel6.setBackground(Color.pink);
        imagePanel6.setBounds(largeur / 2 + (125 * 2), hauteur - 250, 125, hauteur / 5);
        imagePanel6.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel6);
        
        JButton bouton6 = new JButton("", image6);
        bouton6.setBackground(Color.PINK);
        bouton6.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        bouton6.setFocusPainted(false);
        bouton6.setContentAreaFilled(false);
        bouton6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Carte 6 séléctionnée.");
            }
        });
        fenetreMenu.add(bouton6);
        imagePanel6.add(bouton6);
    }

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
    }
    public void quitterPartie(){};
    public void sauvegarderPartie(){};
    public boolean jouerCarte(Carte c, Joueur u, Joueur cible){
        return true;
    };
    public void jouer(Carte c, Joueur u, Joueur cible){};
    public void afficherAction(Carte c, Joueur u, Joueur cible){};
    public void ajouterHistorique(Carte c, Joueur u, Joueur cible){};
    public void nouvelleManche(){};

    
}
