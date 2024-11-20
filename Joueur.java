import java.util.ArrayList;

public abstract class Joueur{
    private ArrayList<Carte> main;
    private String nom;
    private int kilometre;
    //private Etat etat;
    private int id;

    public Joueur(String name, int km, int id){
        main = new ArrayList<Carte>();
        nom = name;
        kilometre = km;
        //etat = null;
        id = this.id;
    }

    public ArrayList<Carte> getMain(){
        return main;
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
        switch(c.getType()){
            /* Les cartes bottes */
            case AS_DU_VOLANT :
            case CAMION_CITERNE :
            case INCREVABLE :
            case VEHICULE_PRIORITAIRE :
                if(verification(c, this, null)){ // null car pas besoin de cible
                    jouerBotte(c);
                }
                break;
            // Continuer
            default:
                break;
        }
    }

    /*
     * Joue une carte botte au joueur
     * PAS FINI
     */
    public void jouerBotte(Carte c){

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
        return true;
    }

    public void appliquerAction(Carte c){}
}