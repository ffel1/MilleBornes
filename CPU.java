import java.util.ArrayList;
import java.util.Random;

public abstract class CPU extends Joueur{
    
    private Partie partie;
    private Joueur cible;

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
        for(Carte carte : getMain())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n");
        }
        controleur.getVue().ajouterMessage("Les attaques\n");
        for(Carte carte : getAttaquesEnCours())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n");
        }
        controleur.getVue().ajouterMessage("Les bottes\n");
        for(Carte carte : getBottesPosées())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n");
        }

        Carte carteJoué = choisirCarte();

        if(carteJoué == null)
        {
            controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer ! \n");
            defausse(choixDeDefausse(), controleur);
        }
        while (carteJoué instanceof Botte) 
        {
            controleur.getVue().ajouterMessage(jouerCarte(carteJoué, cible));
            piocher();
            controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a encore pioché ! \n");
            carteJoué = choisirCarte();
            if(carteJoué == null)
            {
                controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer !\n");
                defausse(choixDeDefausse(), controleur);
            }
        }
        controleur.getVue().ajouterMessage(jouerCarte(carteJoué, cible));
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

    public Partie getPartie()
    {
        return partie;
    }

    public void setCurrentCible(Joueur c)
    {
        cible = c;
    }
    public Joueur getCurrentCible()
    {
        return cible;
    }

    public Joueur getCible(Carte c)
    {
        Joueur gagnantActuel = partie.getJoueur1();
        for(Joueur j : partie.getJoueurs())
        {
            if(j.getKilometre() > gagnantActuel.getKilometre() && j.getId() != getId())
            {
                gagnantActuel = j;
            }
            else if(j.getKilometre() == gagnantActuel.getKilometre() && j.getId() != getId())
            {
                Random r = new Random();
                int i = r.nextInt(2);
                if(i == 0)
                {
                    gagnantActuel = j;
                }
            }
        }
        if(verification(c, this, gagnantActuel))
        {
            cible = gagnantActuel;
            return gagnantActuel;
        }
        else
        {
            for(Joueur j : partie.getJoueurs())
            {
                if(j.getId() != getId() && j.getId() != gagnantActuel.getId()&& verification(c, this, j))
                {
                    cible = j;
                    return j;
                }
            }
        }

        return null;
    }
}
