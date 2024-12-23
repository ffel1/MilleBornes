package main;

import java.util.ArrayList;
import java.util.Random;

public abstract class CPU extends Joueur{
    
    private Joueur cible;

    public CPU(String nom, int k, int id, Partie partie){
        super(nom, k, id, partie);
    }

    public void actionBot(Controleur controleur) //le boolean sert à gérer le cas ou on pose une botte (on rejoue sans afficher nouveau tour)
    {
        controleur.getVue().ajouterMessage("\nC'est au tour du CPU " + getNom() + " ! Distance parcourue : " + getKilometre() + " km \n", true);
        piocher();
        controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a pioché ! \n", true);

        //Pour afficher les cartes des bots pour vérifier si leur coups sont biens
/*
        for(Carte carte : getMain())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n", false);
        }
        controleur.getVue().ajouterMessage("Les attaques\n", false);
        for(Carte carte : getAttaquesEnCours())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n", false);
        }
        controleur.getVue().ajouterMessage("Les bottes\n", false);
        for(Carte carte : getBottesPosées())
        {
            controleur.getVue().ajouterMessage(carte.getNom()+"\n", false);
        }
*/
        Carte carteJoué = choisirCarte();

        if(carteJoué == null)
        {
            controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer ! \n", true);
            defausse(choixDeDefausse(), controleur);
        }
        else
        {
            while (carteJoué instanceof Botte) 
            {
                controleur.getVue().ajouterMessage(jouerCarte(carteJoué, cible), true);
                piocher();
                controleur.getVue().ajouterMessage("Le CPU " + getNom() + " a encore pioché ! \n", true);
                carteJoué = choisirCarte();
                if(carteJoué == null)
                {
                    controleur.getVue().ajouterMessage("Le CPU " + getNom() + " ne peut pas jouer !\n", true);
                    defausse(choixDeDefausse(), controleur);
                }
            }
            controleur.getVue().ajouterMessage(jouerCarte(carteJoué, cible), true);
            controleur.getVue().ajouterMessage("C'est la fin du tour de " +  getNom() + " ! Distance parcourue : " + getKilometre() + " km \n", true);
            if(controleur.getModel().gagnant() == this)
            {
                controleur.getVue().ajouterMessage( "\n Le CPU "  + getNom() + " a gagné... La prochaine fois peut être... \n", true);
            }
        }

        controleur.getVue().mettreAJourAttaques(getPartie());
        controleur.getVue().mettreAJourBottes(getPartie());
        
    }

    public abstract Carte choixDeDefausse();

    public String defausse(Carte c,Controleur controleur)
    {
        controleur.getVue().ajouterMessage("Le CPU " + getNom() + " défausse la carte :" + c.getNom() +" \n", true);
        getMain().remove(c);
        return null;
    }
    public abstract Carte choisirCarte();

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
        Joueur gagnantActuel = getPartie().getJoueur1();
        for(Joueur j : getPartie().getJoueurs())
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
            for(Joueur j : getPartie().getJoueurs())
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
