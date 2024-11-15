import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
        creerFenetre();
    }

    /*
     * Affiche la fenetre du menu de début
     */
    private static void creerFenetre(){
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
        boutonJouer.setBounds(largeur / 2 - 75, hauteur / 5 - 50, 150, 50);
        labelJouer.add(boutonJouer);
        jouerPanel.add(labelJouer);

        // Image
        JPanel imagePanel = new JPanel();
		imagePanel.setBounds(0, hauteur / 5, largeur, 3 * hauteur / 5);
		imagePanel.setLayout(new BorderLayout());
        fenetreMenu.add(imagePanel);
        ImageIcon image = new ImageIcon("MilleBornes.png");
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

    private static void initialiserPioche(){
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
