import java.util.ArrayList;
import java.util.List;

public abstract class Joueur{
    private List<Carte> main;
    private String nom;
    private int kilometre;
    //private Etat etat;
    private int id;

    public Joueur(String n, int k, int i){
        main = new ArrayList<Carte>();
        nom = n;
        kilometre = k;
        //etat = null;
        id = i;
    }

    public List<Carte> getMain(){
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

    public void piocher(){};
    public boolean verification(Carte c, Joueur u, Joueur cible){return true;};
    public void jouerCarte(Carte c){};
    public void appliquerAction(Carte c){};
    public void retirerCarte(Carte c){};
    public void ajouterCarte(Carte c){};

}