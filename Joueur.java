import java.util.ArrayList;
import java.util.Random;

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

    public void piocher(){
        Random hasard = new Random();
        int indexCarte = hasard.nextInt(/*pioche.size()*/);
    }

    public boolean verification(Carte c, Joueur u, Joueur cible){
        return true;
    }
    public void jouerCarte(Carte c){}

    public void appliquerAction(Carte c){}

    public void retirerCarte(Carte c){
        main.remove(c);
    }

    public void ajouterCarte(Carte c){
        main.add(c);
    }
}