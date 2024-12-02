public class Utilisateur extends Joueur{

    public Utilisateur(String nom, int k, int id, Partie partie){
        super(nom, k, id);
    }
    
    public void appliquerAction(Carte c){};

    @Override
    public Joueur getCible()
    {
        return null;
    }
}
