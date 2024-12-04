import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

 
public class Partie implements Serializable{
    private int points;
    private ArrayList<Joueur> joueurs;
    private static ArrayList<Carte> pioche;
    private int leTourDe;


    public Partie(){
        // Initialisation
        joueurs = new ArrayList<Joueur>();
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
    public int getLeTourDe()
    {
        return leTourDe;
    }

    public boolean partieCree(){
        return !joueurs.isEmpty();
    }

    public ArrayList<Joueur> getJoueurs()
    {
        return joueurs;
    }

    public void setJoueurs(Joueur joueur, int i)
    {
        joueurs.set(i, joueur);
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
        joueurs.clear();
        System.out.println(getPioche().size());
        joueurs.add(0, new Utilisateur("Moi", 0, 0, this));
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
        System.out.println("Il y a " + joueurs.size() + " joueurs !");
        System.out.println(getPioche().size());

        Random r = new Random();
        leTourDe = r.nextInt(3);
        leTourDe = 0; //A ENLEVER APRES, C'EST PLUS PRATIQUE POUR CODER
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
    public void jouer(Controleur controleur)
    {
        /* 
        System.out.println(joueurs.size());
        controleur.getVue().ajouterMessage("C'est au tour de :" + joueurs.get(leTourDe).getId());
        while(!gagnant())
        {
            joueurs.get(leTourDe).monTour(true);
            if(!joueurs.get(leTourDe).getMonTour())
            {
                leTourDe++;
            }
            if(leTourDe == 4)
            {
                leTourDe = 0;
            }
        }
        */
        for(Joueur joueur : joueurs){
            joueur.piocher();
            Carte c = joueur.choisirCarte();
            controleur.getVue().ajouterMessage(joueur.getNom()+" a joué : "+c);
        }
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
