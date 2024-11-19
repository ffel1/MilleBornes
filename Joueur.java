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
        System.out.println("OK");
    }
}