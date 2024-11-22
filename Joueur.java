import java.util.ArrayList;

public abstract class Joueur{
    private ArrayList<Carte> main;
    private ArrayList<Carte> botteAttaque;
    private String nom;
    private int kilometre;
    //private Etat etat;
    private int id;

    public Joueur(String name, int km, int id){
        main = new ArrayList<Carte>();
        botteAttaque = new ArrayList<Carte>();
        nom = name;
        kilometre = km;
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
        return kilometre;
    }
    /*public Etat getEtat(){
        return etat;
    }*/
    public int getId(){
        return id;
    }

    public boolean mainPleine(){
        return main.size() >= 6;
    }

    public void piocher(){
        ArrayList<Carte> pioche = Partie.getPioche();
        
        //La pioche a déjà été mélangé dans Partie
        if(!mainPleine()){
            Carte c = pioche.getFirst();
            main.add(c);
            pioche.remove(c);
        }
        else{
            System.out.println("Main pleine");
        }
       
    }

    public void retirerCarte(Carte c){
        main.remove(c);
    }

    public void ajouterCarte(Carte c){
        main.add(c);
    }

    /*
     * Choisir l'action en fonction du type de carte
     * PAS FINI
     */
    public void jouerCarte(Carte c){
        Carte carteAJouer = c;

        if (carteAJouer != null){
            appliquerAction(carteAJouer);
            retirerCarte(carteAJouer);
            System.out.println(getNom() + " joue : " + carteAJouer.getNom());
        }
    }

    /*
     * Joue une carte botte au joueur
     * PAS FINI
     */
    public void jouerBotte(Carte c){
        switch(c.getType()){
            /* Les cartes bottes */
            case AS_DU_VOLANT :
            case CAMION_CITERNE :
            case INCREVABLE :
            case VEHICULE_PRIORITAIRE :
                break;
            default:
                break;
        }
    }

    /*
     * Joue une carte parade et enleve le malus
     * PAS FINI
     */
    public void jouerParade(Carte c){

    }

    /*
     * Joue une carte attaque et demande sur quel joueur
     * PAS FINI
     */
    public void jouerAttaque(Carte c){

    }

    /*
     * Augmente le nombre de km du joueur
     * PAS FINI
     */
    public void jouerDistance(Carte c){

    }

    /*
     * Vérifie qu'une carte soit jouable
     */
    public boolean verification(Carte c, Joueur u, Joueur cible){
        if (c instanceof Attaque){
            for (Carte carte : cible.getBotteAttaque()) {
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
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE) return false;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Parade || c instanceof Botte){
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