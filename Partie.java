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
    private static JFrame fenetreMenu;

    public Partie(){
        points = 0;
        joueurs = new ArrayList<Joueur>();
        pioche = new ArrayList<Carte>();
    } 

    public static void main(String[] args){
        initialiserPioche();
        creerFenetreMenu();
    }

    /*
     * Affiche la fenetre du menu de début
     */
    private static void creerFenetreMenu(){
        fenetreMenu = new JFrame("1000 Bornes");

        // Fentre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int)dimension.getHeight();
        int largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);

        // Bouton jouer
        JPanel jouerPanel = new JPanel();
		jouerPanel.setBounds(0, 0, largeur, hauteur / 5);
		jouerPanel.setLayout(new BorderLayout());
        fenetreMenu.add(jouerPanel);
        JLabel labelJouer = new JLabel();
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

    private static void creerFenetreJeu(){
        JPanel panelJeu = new JPanel();
        fenetreMenu.setContentPane(panelJeu);
        fenetreMenu.revalidate();
        fenetreMenu.repaint();

        // Fenetre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int)dimension.getHeight();
        int largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);

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

    private static void afficherCartesJoueur(){
        // Fentre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int)dimension.getHeight();
        int largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);

        // Carte 1
        JPanel imagePanel = new JPanel();
        ImageIcon image = new ImageIcon("Images/25.png");
        imagePanel.setBackground(Color.pink);
        imagePanel.setBounds(largeur / 2 - (125 * 3), hauteur - 250, 125, hauteur / 5);
        imagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel);

        JLabel labelImage = new JLabel();
        labelImage.setIcon(image);
        labelImage.setVerticalAlignment(JLabel.CENTER);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        imagePanel.add(labelImage);

        // Carte 2
        JPanel imagePanel2 = new JPanel();
        ImageIcon image2 = new ImageIcon("Images/25.png");
        imagePanel2.setBackground(Color.pink);
        imagePanel2.setBounds(largeur / 2 - (125 * 2), hauteur - 250, 125, hauteur / 5);
        imagePanel2.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel2);
        
        JLabel labelImage2 = new JLabel();
        labelImage2.setIcon(image2);
        labelImage2.setVerticalAlignment(JLabel.CENTER);
        labelImage2.setHorizontalAlignment(JLabel.CENTER);
        imagePanel2.add(labelImage2);

        // Carte 3
        JPanel imagePanel3 = new JPanel();
        ImageIcon image3 = new ImageIcon("Images/50.png");
        imagePanel3.setBackground(Color.pink);
        imagePanel3.setBounds(largeur / 2 - 125, hauteur - 250, 125, hauteur / 5);
        imagePanel3.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel3);
        
        JLabel labelImage3 = new JLabel();
        labelImage3.setIcon(image3);
        labelImage3.setVerticalAlignment(JLabel.CENTER);
        labelImage3.setHorizontalAlignment(JLabel.CENTER);
        imagePanel3.add(labelImage3);

        // Carte 4
        JPanel imagePanel4 = new JPanel();
        ImageIcon image4 = new ImageIcon("Images/75.png");
        imagePanel4.setBackground(Color.pink);
        imagePanel4.setBounds(largeur / 2, hauteur - 250, 125, hauteur / 5);
        imagePanel4.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel4);
        
        JLabel labelImage4 = new JLabel();
        labelImage4.setIcon(image4);
        labelImage4.setVerticalAlignment(JLabel.CENTER);
        labelImage4.setHorizontalAlignment(JLabel.CENTER);
        imagePanel4.add(labelImage4);

        // Carte 5
        JPanel imagePanel5 = new JPanel();
        ImageIcon image5 = new ImageIcon("Images/100.png");
        imagePanel5.setBackground(Color.pink);
        imagePanel5.setBounds(largeur / 2 + 125, hauteur - 250, 125, hauteur / 5);
        imagePanel5.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel5);
        
        JLabel labelImage5 = new JLabel();
        labelImage5.setIcon(image5);
        labelImage5.setVerticalAlignment(JLabel.CENTER);
        labelImage5.setHorizontalAlignment(JLabel.CENTER);
        imagePanel5.add(labelImage5);

        // Carte 6
        JPanel imagePanel6 = new JPanel();
        ImageIcon image6 = new ImageIcon("Images/200.png");
        imagePanel6.setBackground(Color.pink);
        imagePanel6.setBounds(largeur / 2 + (125 * 2), hauteur - 250, 125, hauteur / 5);
        imagePanel6.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel6);
        
        JLabel labelImage6 = new JLabel();
        labelImage6.setIcon(image6);
        labelImage6.setVerticalAlignment(JLabel.CENTER);
        labelImage6.setHorizontalAlignment(JLabel.CENTER);
        imagePanel6.add(labelImage6);
    }

    private static void initialiserPioche(){
        pioche = new ArrayList<Carte>();

        //Cartes Distance
        for(int i = 0;i<12;i++){
            if(i<10){
                pioche.add(new Distance(TypeCarte._25KM, 25));
                pioche.add(new Distance(TypeCarte._50KM, 50));
                pioche.add(new Distance(TypeCarte._75KM, 75));

                if(i<8){
                    pioche.add(new Distance(TypeCarte._200KM, 200));
                }
            }
            pioche.add(new Distance(TypeCarte._100KM, 100));
        }

        //Cartes Attaque
        for(int i = 0;i<6;i++){
            if(i<5){
                pioche.add(new Attaque(TypeCarte.FEU_ROUGE));

                if(i<3){
                    pioche.add(new Attaque(TypeCarte.ACCIDENT));
                    pioche.add(new Attaque(TypeCarte.PANNE_D_ESSENCE));
                    pioche.add(new Attaque(TypeCarte.CREVAISON));
                }
            }
            pioche.add(new Attaque(TypeCarte.LIMITATION_DE_VITESSE));
        }

        //Cartes Parade
        for(int i = 0;i<14;i++){
            if(i<6){
                pioche.add(new Parade(TypeCarte.REPARATION));
                pioche.add(new Parade(TypeCarte.ESSENCE));
                pioche.add(new Parade(TypeCarte.ROUE_DE_SECOURS));
                pioche.add(new Parade(TypeCarte.FIN_LIMITATION_VITESSE));
            }
            pioche.add(new Parade(TypeCarte.FEU_VERT));
        }

        //Cartes Botte
        pioche.add(new Botte(TypeCarte.AS_DU_VOLANT));
        pioche.add(new Botte(TypeCarte.CAMION_CITERNE));
        pioche.add(new Botte(TypeCarte.INCREVABLE));
        pioche.add(new Botte(TypeCarte.VEHICULE_PRIORITAIRE));

        //Melanger la pioche
        Collections.shuffle(pioche);
    }
    
    public static int taillePioche(){
        return pioche.size();
    }

    public static ArrayList<Carte> getPioche(){
        return pioche;
    }

    public void nouvellePartie(){};
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
