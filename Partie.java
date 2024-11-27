import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
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
 
public class Partie implements Serializable{
    private int points;
    private ArrayList<Joueur> joueurs;
    private static ArrayList<Carte> pioche;
    private JFrame fenetreMenu;
    private int hauteur;
    private int largeur;

    public Partie(){
        // Initialisation
        joueurs = new ArrayList<Joueur>();
        fenetreMenu = new JFrame("1000 Bornes");

        // Fenetre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        hauteur = (int)dimension.getHeight();
        largeur  = (int)dimension.getWidth();
        fenetreMenu.setSize(largeur, hauteur);
    } 

    /*
     * Initialisation de la pioche #FINI
     */
    public void initialiserPioche(){
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

    public Joueur getJoueur1(){
        return joueurs.get(0);
    }

    public Joueur getJoueur2(){
        return joueurs.get(1);
    }

    public Joueur getJoueur3(){
        return joueurs.get(2);
    }

    /*
     * Création de la partie
     * PAS FINI
     */
    public void nouvellePartie(){
        initialiserPioche();
        System.out.println(getPioche().size());
        joueurs.add(0, new Utilisateur("Moi", 0, 0));
        joueurs.add(1, new CPUAgro("Agro", 0, 1));
        joueurs.add(2, new CPUFast("Fast", 0, 2));
        for(int i = 0; i < 6; i++){
            joueurs.get(0).ajouterCarte(pioche.get(i));
            pioche.remove(i);
            joueurs.get(1).ajouterCarte(pioche.get(i));
            pioche.remove(i);
            joueurs.get(2).ajouterCarte(pioche.get(i));
            pioche.remove(i);
        }
        System.out.println(joueurs.get(0).getMain().size());
        System.out.println(getPioche().size());
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
        for(int i = 0; i < joueurs.size(); i++){
            if(joueurs.get(i).getKilometre() >= 700){
                return true;
            }
        }
        return false;
    }
    
    public void afficherAction(Carte c, Joueur u, Joueur cible){};
    public void ajouterHistorique(Carte c, Joueur u, Joueur cible){};
    public void nouvelleManche(){};
    public void quitterPartie(){};
    public void sauvegarderPartie(){};
}
