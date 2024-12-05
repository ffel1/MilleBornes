public class CPU extends Joueur{
    
    public CPU(String nom, int k, int id){
        super(nom, k, id);
    }

    public void actionBot(Controleur controleur)
    {
        controleur.getVue().ajouterMessage("C'est au tour du CPU " + getNom() + "\n");
    }

    public void appliquerAction(Carte c){};

    @Override
    public Joueur getCible()
    {
        return null;
    }
}
