import java.util.ArrayList;

public abstract class CPU extends Joueur{
    
    private Partie partie;

    public CPU(String nom, int k, int id, Partie partie){
        super(nom, k, id);
        this.partie = partie;
    }

    public void actionBot(Controleur controleur)
    {
        controleur.getVue().ajouterMessage("C'est au tour du CPU " + getNom() + "\n");
        piocher();
        controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a pioché ! \n");
        Carte carteJoué = choisirCarte();
        jouerCarte(carteJoué);
    }

    public abstract Carte choisirCarte();

    public void appliquerAction(Carte c){};

    @Override
    public Joueur getCible()
    {
        Joueur joueurPremier = partie.getJoueur1(); 
        for(Joueur joueurCurrent : partie.getJoueurs())
        {
            if(joueurCurrent.getId() != getId() && joueurCurrent.getKilometre() > joueurPremier.getKilometre())
            {
                joueurPremier = joueurCurrent;
            }
        }
        System.out.println("Le bot a prit pour cible ");
        return joueurPremier;
    }
}
