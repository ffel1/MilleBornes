import java.util.ArrayList;

public abstract class CPU extends Joueur{
    
    private Partie partie;

    public CPU(String nom, int k, int id, Partie partie){
        super(nom, k, id);
        this.partie = partie;
    }

    public void actionBot(Controleur controleur) //le boolean sert à gérer le cas ou on pose une botte (on rejoue sans afficher nouveau tour)
    {
        controleur.getVue().ajouterMessage("\nC'est au tour du CPU " + getNom() + "\n");
        piocher();
        controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a pioché ! \n");

        //Pour afficher les cartes des bots pour vérifier si leur coups sont biens
        /*for(Carte carte : getMain())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n");
        }*/ 


        Carte carteJoué = choisirCarte();

        if(carteJoué == null)
        {
            controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer ! \n");
            defausse(choixDeDefausse(), controleur);
        }
        while (carteJoué instanceof Botte) 
        {
            controleur.getVue().ajouterMessage(jouerCarte(carteJoué));
            piocher();
            controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a encore pioché ! \n");
            carteJoué = choisirCarte();
            if(carteJoué == null)
            {
                controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer !\n");
                defausse(choixDeDefausse(), controleur);
            }
        }
        controleur.getVue().ajouterMessage(jouerCarte(carteJoué));
        controleur.getVue().ajouterMessage("C'est la fin du tour de " +  getNom() +"\n");    
    }

    public abstract Carte choixDeDefausse();

    public String defausse(Carte c,Controleur controleur)
    {
        controleur.getVue().ajouterMessage("Le CPU " + getNom() + " défausse la carte :" + c.getNom() +" \n");
        getMain().remove(c);
        return null;
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
