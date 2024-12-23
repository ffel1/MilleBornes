package main;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 
public class Partie implements Serializable{
    private int quiCommence;
    private ArrayList<Joueur> joueurs;
    private ArrayList<Carte> pioche;
    private String nomDeLaPartie;
    private int pointsJoueur;
    private int pointsCPUFast;
    private int pointsCPUAgro;
    private int numéroDeManche;
    private Joueur gagnantDePartie;

    public Partie(){
        // Initialisation
        joueurs = new ArrayList<Joueur>();
    }
    
    public void initialisationNomDeLaPartie()
    {
        File fichier;
        int i = 1;

        File dossier = new File("SauvegardeDesHistoriques");
        if(!dossier.exists())
        {
            dossier.mkdir();
        }

        fichier = new File("SauvegardeDesHistoriques/Manche_" + i+".txt");
        while(fichier.exists())
        {
            i++;
            fichier = new File("SauvegardeDesHistoriques/Manche_" + i+".txt");
        }

        try {
            fichier.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        nomDeLaPartie = "Manche_"+i+".txt";
    }

    public String getNomDeLaPartie()
    {
        return nomDeLaPartie;
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

                if(i < 4){
                    pioche.add(new Distance(TypeCarte._200KM, 200));
                }
            }
            pioche.add(new Distance(TypeCarte._100KM, 100));
        }

        //Cartes Attaques
        for(int i = 0; i < 5; i++){
            if(i < 4){
                pioche.add(new Attaque(TypeCarte.LIMITATION_DE_VITESSE));

                if(i < 3){
                    pioche.add(new Attaque(TypeCarte.ACCIDENT));
                    pioche.add(new Attaque(TypeCarte.PANNE_D_ESSENCE));
                    pioche.add(new Attaque(TypeCarte.CREVAISON));
                }
            }
            pioche.add(new Attaque(TypeCarte.FEU_ROUGE));
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

    public int getQuiCommence()
    {
        return quiCommence;
    }

    public ArrayList<Carte> getPioche(){
        return pioche;
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

    public Utilisateur getJoueur1(){
        return (Utilisateur)joueurs.get(0);
    }

    public CPU getJoueur2(){
        return (CPU)joueurs.get(1);
    }

    public CPU getJoueur3(){
        return (CPU)joueurs.get(2);
    }

    /*
     * Création de la partie
     * PAS FINI
     */
    public void nouvellePartie(){
        numéroDeManche++;
        initialiserPioche();
        joueurs.clear();
        System.out.println(getPioche().size());
        joueurs.add(0, new Utilisateur("Vous", 0, 0, this));
        joueurs.add(1, new CPUAgro("Agro", 0, 1, this));
        joueurs.add(2, new CPUFast("Fast", 0, 2, this));
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
        quiCommence = r.nextInt(3);
    }

    /*
     * Verifie si la carte est jouable
     * PAS FINI
     */
    public boolean jouerCarte(Carte c, Joueur u, Joueur cible){
        return true;
    }

    /*
     * Renvoie vrai si un joueur a gagné a au moins 700 km
     * PAS FINI
     */
    public Joueur gagnant(){
        for(int i = 0; i < joueurs.size(); i++){
            if(joueurs.get(i).getKilometre() >= 700){
                return joueurs.get(i);
            }
        }
        return null;
    }

    public void finDePartie(FenetreJeu vue) {
        vue.ajouterMessage("\nLancement de la manche " + (numéroDeManche + 1)+ " dans :\n", false);
    
        compteurPoints();
    }

    public int getPointsJoueur()
    {
        return pointsJoueur;
    }

    public int getPointCPUAgro()
    {
        return pointsCPUAgro;
    }

    public int getPointsCPUFast()
    {
        return pointsCPUFast;
    }

    public int getNumeroManche()
    {
        return numéroDeManche;
    }

    public void compteurPoints()
    {
        Joueur gagnant = gagnant();
        // Points de distance
        pointsJoueur += joueurs.get(0).getKilometre();
        pointsCPUFast += joueurs.get(1).getKilometre();
        pointsCPUAgro += joueurs.get(2).getKilometre();

        // Bonus "Capot"
        if (joueurs.get(1).getKilometre() == 0) {
            pointsJoueur += 500;
            pointsCPUAgro += 500; 
        }
        if (joueurs.get(2).getKilometre() == 0) {
            pointsJoueur += 500;
            pointsCPUFast += 500;
        }
        if (joueurs.get(0).getKilometre() == 0) {
            pointsCPUFast += 500;
            pointsCPUAgro += 500;
        }

        // Points de bottes 
        int nbreBottesJoueur = joueurs.get(0).getBottesPosées().size();
        int nbreBottesCPUFast = joueurs.get(1).getBottesPosées().size();
        int nbreBottesCPUAgro = joueurs.get(2).getBottesPosées().size();

        if ( nbreBottesJoueur == 4){
            pointsJoueur += 700; // pts de bottes posées
        }else{
            pointsJoueur += nbreBottesJoueur * 100; // pts de bottes posées
        }

        if ( nbreBottesCPUFast == 4){
            pointsCPUFast += 700; // pts de bottes posées
        }else{
            pointsCPUFast += nbreBottesCPUFast * 100; // pts de bottes posées
        }

        if ( nbreBottesCPUAgro == 4){
            pointsCPUAgro += 700; // pts de bottes posées
        }else{
            pointsCPUAgro += nbreBottesCPUAgro * 100; // pts de bottes posées
        }
        
        // Points de coup fourré
        pointsJoueur += joueurs.get(0).getCoupFourres() * 300;
        pointsCPUFast += joueurs.get(2).getCoupFourres() * 300;
        pointsCPUAgro += joueurs.get(1).getCoupFourres() * 300;

        // Points de victoire
        if(gagnant == null)
        {
            gagnant = getJoueur1();
            for(Joueur joueur : joueurs)
            {
                if(joueur.getKilometre() > gagnant.getKilometre())
                {
                    gagnant = joueur;
                }
            }
        }
        if (gagnant.getId() == 0){

            pointsJoueur += 400; // pts de manche gagnée

            if (pioche.isEmpty()){
                pointsJoueur += 300; // Points de couronnement
            }
            if (joueurs.get(0).utilise200KM() == false){
                pointsJoueur += 300; // Points de aucun 200KM
            }

        }
        else if (gagnant == joueurs.get(1)){
                
            pointsCPUFast += 400; // pts de manche gagnée
            
            if (pioche.isEmpty()){
                pointsCPUFast += 300; // Points de couronnement
            }
            if (joueurs.get(1).utilise200KM() == false){
                pointsCPUFast += 300; // Points de aucun 200KM
            }
        }
        else if (gagnant == joueurs.get(2)){
        
            pointsCPUAgro += 400; // pts de manche gagnée
            
            if (pioche.isEmpty()){
                pointsCPUAgro += 300; // Points de couronnement
            }
            if (joueurs.get(2).utilise200KM() == false){
                pointsCPUAgro += 300; // Points de aucun 200KM
            }
        }
        getJoueur1().setPoints(getPointsJoueur());
        getJoueur2().setPoints(getPointCPUAgro());
        getJoueur3().setPoints(getPointsCPUFast());
        Joueur gagnantLocal = getJoueur1();
        for(Joueur joueur : joueurs)
        {
            if(joueur.getPoints() > gagnantLocal.getPoints())
            {
                gagnantLocal = joueur;
            }
        }
        if(gagnantLocal.getPoints() > 5000)
        {
            gagnantDePartie = gagnantLocal;
        }
    }

    public Joueur lePlusAvance()
    {
        Joueur gagnant = getJoueur1();
        for(Joueur joueur : getJoueurs())
        {
            if(joueur.getKilometre() > gagnant.getKilometre())
            {
                gagnant = joueur;
            }
        }

        return gagnant;
    }

    public Joueur getGagnantDePartie()
    {
        return gagnantDePartie;
    }
    
    public void afficherAction(Carte c, Joueur u, Joueur cible){};
    public void ajouterHistorique(Carte c, Joueur u, Joueur cible){};
    public void nouvelleManche(){};
    public void quitterPartie(){};
    public void sauvegarderPartie(){};
}