public class CPU extends Joueur{
    
    public CPU(String nom, int k, int id){
        super(nom, k, id);
    }

    public void piocher(){};
    public boolean verification(Carte c, Joueur u, Joueur cible){return true;};
    public void jouerCarte(Carte c){};
    public void appliquerAction(Carte c){};
    public void retirerCarte(Carte c){};
    public void ajouterCarte(Carte c){};
}
