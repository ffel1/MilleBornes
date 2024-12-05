import java.io.Serializable;
import java.util.ArrayList;

public abstract class Joueur implements Serializable{
    private ArrayList<Carte> main;
    private ArrayList<Carte> botteAttaque;
    private String nom;
    private int kilometreP;
    //private Etat etat;
    private int id;

    public Joueur(String name, int km, int id){
        main = new ArrayList<Carte>();
        botteAttaque = new ArrayList<Carte>();
        nom = name;
        kilometreP = km;
        //etat = null;
        id = this.id;
    }

    public ArrayList<Carte> getMain(){
        return main;
    }
    public ArrayList<Carte> getBotteAttaque(){
        return botteAttaque;
    }
    public String getNom(){
        return nom;
    }
    public int getKilometre(){
        return kilometreP;
    }
    /*public Etat getEtat(){
        return etat;
    }*/
    public int getId(){
        return id;
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

    public void piocher()
    {
        ArrayList<Carte> pioche = Partie.getPioche();  
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

    public void jouerCarte(Carte c)
    {
        /*if (c instanceof Attaque){
            Joueur cible = getCible();
            jouerAttaque(c, cible);
        } else if (c instanceof Parade){
            jouerParade(c);
        } else if (c instanceof Botte){
            jouerBotte(c);
        } else if (c instanceof Distance){
            jouerDistance(c);
        }   */
    }
    

    public Joueur getCible(){
        Joueur opps;
        return null;
    }

    /*
     * Joue une carte botte au joueur et enleve l'attaque en cours si il y en a une
     */
    public void jouerBotte(Carte c) {
        if (c instanceof Botte) {
            botteAttaque.add(c);
    
            switch (c.getType()) {
                case AS_DU_VOLANT:
                    botteAttaque.removeIf(carte -> carte.getType() == TypeCarte.ACCIDENT); 
                    break;
                case CAMION_CITERNE:
                    botteAttaque.removeIf(carte -> carte.getType() == TypeCarte.PANNE_D_ESSENCE);
                    break;
                case INCREVABLE:
                    botteAttaque.removeIf(carte -> carte.getType() == TypeCarte.CREVAISON);
                    break;
                case VEHICULE_PRIORITAIRE:
                    botteAttaque.removeIf(carte -> carte.getType() == TypeCarte.LIMITATION_DE_VITESSE || carte.getType() == TypeCarte.FEU_ROUGE);
                    break;
                default:
                    break;
            }
    
            System.out.println(getNom() + " joue la botte : " + c.getNom());
            retirerCarte(c);
    }
}
    
    /*
     * Joue une carte parade et enleve l'attaque en cours
     */
    public void jouerParade(Carte c) {
        for (Carte carteAttaque : botteAttaque) {
            if (verification(c, this, this)) {
                botteAttaque.remove(carteAttaque);
                //System.out.println(getNom() + " joue la par"+appliquerAction(carteAJouer);
                //retirerCarte(carteAJouer);
            }
        }
    }
    
    /*
     * Joue une carte attaque et demande sur quel joueur
     */
    public void jouerAttaque(Carte c, Joueur cible) {
        if (verification(c, this, cible)) {
            cible.getBotteAttaque().add(c);
            System.out.println(getNom() + " joue l'attaque " + cible.getNom() + " contre " + c.getNom());
            retirerCarte(c);
        }
    }
    

    /*
     * Augmente le nombre de km du joueur
     */
    public void jouerDistance(Carte c) {
        if (verification(c, this, this)) {
            int kilometre = ((Distance) c).getKilometre();
            kilometreP += kilometre;
            System.out.println(getNom() + " avance de " + kilometre + " km. Distance totale : " + kilometreP + " km.");
            retirerCarte(c);
        }
    }
    

    /*
     * Vérifie qu'une carte soit jouable
     */
    public boolean verification(Carte c, Joueur u, Joueur cible){
        if (c instanceof Attaque){
            for (Carte carte : cible.getBotteAttaque()) {
                switch (c.getType()) {
                    case CREVAISON:
                        if (carte.getType() == TypeCarte.INCREVABLE | carte.getType() == TypeCarte.CREVAISON) return false;
                        break;
                    case ACCIDENT:
                        if (carte.getType() == TypeCarte.AS_DU_VOLANT | carte.getType() == TypeCarte.ACCIDENT) return false;
                        break;
                    case PANNE_D_ESSENCE:
                        if (carte.getType() == TypeCarte.CAMION_CITERNE | carte.getType() == TypeCarte.PANNE_D_ESSENCE) return false;
                        break;
                    case LIMITATION_DE_VITESSE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE | carte.getType() == TypeCarte.LIMITATION_DE_VITESSE) return false;
                        break;
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE | carte.getType() == TypeCarte.FEU_ROUGE) return false;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Distance){
            for (Carte carte : u.getBotteAttaque()){
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
            for (Carte carte : u.getBotteAttaque()){
                switch (c.getType()) {
                    case FEU_VERT:
                        if (carte.getType() != TypeCarte.FEU_ROUGE) return false;
                        break;
                    case FIN_LIMITATION_VITESSE:
                        if (carte.getType() != TypeCarte.LIMITATION_DE_VITESSE) return false;
                        break;
                    case ESSENCE:
                        if (carte.getType() != TypeCarte.PANNE_D_ESSENCE) return false;
                        break;
                    case ROUE_DE_SECOURS:
                        if (carte.getType() != TypeCarte.CREVAISON) return false;
                        break;
                    case REPARATION:
                        if (carte.getType() != TypeCarte.ACCIDENT) return false;
                        break;
                    default:
                        break;
                }
            }
        }
        return true;
    }

    public void appliquerAction(Carte c){}
}