package main;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Joueur implements Serializable{
    private ArrayList<Carte> main;
    private ArrayList<Carte> attaquesEnCours;
    private boolean feuVert;
    private ArrayList<Carte> bottesPosées;
    private String nom;
    private int kilometreP;
    private int id;
    private Partie partie;
    private int coupFourrees = 0;
    private ArrayList<Carte> cartesDistanceJouees;

    public Joueur(String name, int km, int id, Partie partie){
        cartesDistanceJouees  = new ArrayList<Carte>();
        main = new ArrayList<Carte>();
        attaquesEnCours = new ArrayList<Carte>();
        feuVert = false;
        bottesPosées = new ArrayList<Carte>();
        nom = name;
        kilometreP = km;
        this.id = id; 
        this.partie = partie;
    }

    public ArrayList<Carte> getMain(){
        return main;
    }
    public ArrayList<Carte> getAttaquesEnCours(){
        return attaquesEnCours;
    }
    public boolean getFeuVert(){
        return feuVert;
    }

    public Partie getPartie()
    {
        return partie;
    }

    public void setFeuVert(boolean b)
    {
        feuVert = b;
    }
    public ArrayList<Carte> getBottesPosées(){
        return bottesPosées;
    }
    public String getNom(){
        return nom;
    }
    public int getKilometre(){
        return kilometreP;
    }
    public int getId(){
        return id;
    }

    public int getCoupFourres(){
        return coupFourrees;
    }

    public boolean mainPleine(){
        return main.size() >= 7;
    }

    public void retirerCarte(Carte c){
        main.remove(c);
    }

    public void ajouterCarte(Carte c){
        main.add(c);

    }
    
    public boolean utilise200KM() {
        for (Carte carte : cartesDistanceJouees) {
            if (carte instanceof Distance && ((Distance) carte).getKilometre() == 200) {
                return true;
            }
        }
        return false;
    }

    public void piocher()
    {
        ArrayList<Carte> pioche = partie.getPioche();  
        //La pioche a déjà été mélangé dans Partie
        if(!mainPleine())
        {
            Carte c = pioche.get(0);
            getMain().add(c);
            pioche.remove(c);
        }
        else
        {
            System.out.println("Main pleine");
        }
    }

    /*
     * Choisir l'action en fonction du type de carte
     */
    public String jouerCarte(Carte c, Joueur cible) {
        if (c instanceof Attaque){
            return jouerAttaque(c, cible);
        } else if (c instanceof Parade){
            return jouerParade(c);
        } else if (c instanceof Botte){
            return jouerBotte(c);
        } else if (c instanceof Distance){
            return jouerDistance(c);
        }   
        return null;
    }

    public abstract Joueur getCible(Carte c);

    /*
     * Joue une carte botte au joueur et enleve l'attaque en cours si il y en a une
     */
    public String jouerBotte(Carte c) {
        if (c instanceof Botte) {
            bottesPosées.add(c);
            boolean coupFourre = false;
            switch (c.getType()) {
                case AS_DU_VOLANT:  
                    coupFourre = attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.ACCIDENT); 
                    break;
                case CAMION_CITERNE:
                    coupFourre = attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.PANNE_D_ESSENCE);
                    break;
                case INCREVABLE:
                    coupFourre = attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.CREVAISON);
                    break;
                case VEHICULE_PRIORITAIRE:
                    coupFourre = attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.LIMITATION_DE_VITESSE || carte.getType() == TypeCarte.FEU_ROUGE);
                    break;
                default:
                    break;
            }
            if(coupFourre){
                coupFourrees++;
            }
            retirerCarte(c);
            return getNom() + " joue la botte : " + c.getNom() + "\n";
        }
        return null;
    }

    public abstract String defausse(Carte c, Controleur controleur);
    
    /*
     * Joue une carte parade et enleve l'attaque en cours
     */
    public String jouerParade(Carte c) {
        if (c instanceof Parade) {
            switch (c.getType()) {
                case FEU_VERT:
                    attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.FEU_ROUGE); 
                    attaquesEnCours.add(0,c);
                    feuVert = true;
                    break;
                case FIN_LIMITATION_VITESSE:
                    attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.LIMITATION_DE_VITESSE);
                    break;
                case ESSENCE:
                    attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.PANNE_D_ESSENCE);
                    break;
                case ROUE_DE_SECOURS:
                    attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.CREVAISON);
                    break;
                case REPARATION:
                    attaquesEnCours.removeIf(carte -> carte.getType() == TypeCarte.ACCIDENT);
                    break;
                default:
                    break;
            }
            retirerCarte(c);
            if(c.getType() == TypeCarte.FEU_VERT)
            {
                return getNom() + " joue un " + c.getNom() + " ! \n";
            }
            return getNom() + " joue la parade : " + c.getNom() + "\n";
        }
        return null;
    }
    
    /*
     * Joue une carte attaque et demande sur quel joueur
     */
    public String jouerAttaque(Carte c, Joueur cible) {
        if (verification(c, this, cible)) {
            if(c.getType() == TypeCarte.FEU_ROUGE)
            {
                cible.setFeuVert(false);
                for(Carte carte : cible.attaquesEnCours)
                {
                    if(carte.getType() == TypeCarte.FEU_VERT)
                    {

                        cible.attaquesEnCours.remove(carte);
                        break;
                    }
                }
            }
            cible.getAttaquesEnCours().add(c);
            retirerCarte(c);
            return getNom() + " joue l'attaque " + c.getNom() + " contre " + cible.getNom() + "\n";
        }
        return null;
    }
    

    /*
     * Augmente le nombre de km du joueur
     */
    public String jouerDistance(Carte c) {
        if (verification(c, this, this)) {
            int kilometre = ((Distance) c).getKilometre();
            kilometreP += kilometre;
            //cartesDistanceJouees.add(c);
            retirerCarte(c);
            return (getNom() + " avance de " + kilometre + " km. Distance totale : " + kilometreP + " km. \n");
        }
        else{
            String message = nom+" ne peut pas avancer. Problème : ";
            for(Carte carte : attaquesEnCours)
            {
                if(carte.getType() == TypeCarte.FEU_ROUGE)
                {
                    return message+"feu rouge.\n";
                }

                if(carte.getType() == TypeCarte.PANNE_D_ESSENCE)
                {
                    return message+"panne d'essence.\n";
                }

                if(carte.getType() == TypeCarte.CREVAISON)
                {
                    return message+"crevaison.\n";
                }

                if(carte.getType() == TypeCarte.ACCIDENT)
                {
                    return message+"accident.\n";
                }

                if(carte.getType() == TypeCarte.LIMITATION_DE_VITESSE)
                {
                    return message+"limitation de vitesse.\n";
                }
            }
        }
        return null;
    }

    /*
     * Vérifie qu'une carte soit jouable
     */
    public boolean verification(Carte c, Joueur u, Joueur cible){
        if (c instanceof Attaque){
            if(cible == null)
            {
                return false;
            }
            if(c.getType() == TypeCarte.FEU_ROUGE && !cible.getFeuVert())
            {
                return false;
            }
            for (Carte carte : cible.getBottesPosées()) {
                switch (c.getType()) {
                    case CREVAISON:
                        if (carte.getType() == TypeCarte.INCREVABLE) return false;
                        break;
                    case ACCIDENT:
                        if (carte.getType() == TypeCarte.AS_DU_VOLANT) return false;
                        break;
                    case PANNE_D_ESSENCE:
                        if (carte.getType() == TypeCarte.CAMION_CITERNE) return false;
                        break;
                    case LIMITATION_DE_VITESSE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE) return false;
                        break;
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE) return false;
                        break;
                    default:
                        break;
                }
            }
            for (Carte carte : cible.getAttaquesEnCours()) {
                switch (c.getType()) {
                    case CREVAISON:
                        if (carte.getType() == TypeCarte.CREVAISON) return false;
                        break;
                    case ACCIDENT:
                        if (carte.getType() == TypeCarte.ACCIDENT) return false;
                        break;
                    case PANNE_D_ESSENCE:
                        if (carte.getType() == TypeCarte.PANNE_D_ESSENCE) return false;
                        break;
                    case LIMITATION_DE_VITESSE:
                        if (carte.getType() == TypeCarte.LIMITATION_DE_VITESSE) return false;
                        break;
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.FEU_ROUGE) return false;
                        break;
                    default:
                        break;
                }
            }
            
        } else if (c instanceof Distance){
            if(!feuVert)
            {
                return false;
            }
            if(c.getKilometre()+u.getKilometre() > 100)
            {
                return false;
            }
            for (Carte carte : u.getAttaquesEnCours()){
                switch (c.getType()) {
                    case _25KM :
                    case _50KM :
                        if (carte.getType() == TypeCarte.FEU_ROUGE | carte.getType() == TypeCarte.PANNE_D_ESSENCE | carte.getType() == TypeCarte.CREVAISON | carte.getType() == TypeCarte.ACCIDENT) return false;
                        break;
                    case _75KM : 
                    case _100KM :
                    case _200KM :
                        if (carte.getType() == TypeCarte.FEU_ROUGE | carte.getType() == TypeCarte.PANNE_D_ESSENCE | carte.getType() == TypeCarte.CREVAISON | carte.getType() == TypeCarte.ACCIDENT | carte.getType() == TypeCarte.LIMITATION_DE_VITESSE) return false;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Parade){
            switch (c.getType()) {
                case FEU_VERT:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.FEU_ROUGE) || !getFeuVert())
                    {
                        return true;
                    }
                    else if(getFeuVert())
                    {
                        return false;
                    }
                case FIN_LIMITATION_VITESSE:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.LIMITATION_DE_VITESSE))
                    {
                        return true;
                    }
                case ESSENCE:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.PANNE_D_ESSENCE))
                    {
                        return true;
                    }
                case ROUE_DE_SECOURS:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.CREVAISON))
                    {
                        return true;
                    }
                case REPARATION:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.ACCIDENT))
                    {
                        return true;
                    }
                default:
                    break;
            }
            return false;
        }
        return true;
    }
}