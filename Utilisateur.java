import java.util.ArrayList;

public class Utilisateur extends Joueur{

    private boolean monTour = false, aPioche = false, aJoue = false;

    public Utilisateur(String nom, int k, int id, Partie partie){
        super(nom, k, id);
    }
    
    public void appliquerAction(Carte c){};

    @Override
    public Joueur getCible()
    {
        return null;
    }

    public void monTour(boolean monTour)
    {
        this.monTour = monTour;
    }

    public boolean getaJjoue()
    {
        return aJoue;
    }
    public void setaJoue(boolean b)
    {
        aJoue = b;
    }

    public void piocher()
    {
        ArrayList<Carte> pioche = Partie.getPioche();  
        //La pioche a déjà été mélangé dans Partie
        if(!mainPleine())
        {
            aPioche = true;
            Carte c = pioche.get(0);
            getMain().add(c);
            pioche.remove(c);
        }
        else
        {
            System.out.println("Main pleine");
        }
    }

    public boolean getMonTour()
    {
        return monTour;
    }

    public boolean getaPioche()
    {
        return aPioche;
    }
    
    public void setaPioche(boolean b)
    {
        aPioche = b;
    }

        /*
     * Choisir l'action en fonction du type de carte
     */
    public String jouerCarte(Carte c, Controleur controleur, int nbCarte) {
        aJoue = true; 
        controleur.getVue().ajouterMessage("Vous avez joué la carte " + nbCarte + " (" + c.getNom() + ") \n"); 
        getMain().remove(c);
        controleur.getVue().afficherCartesJoueur(getMain());
        controleur.initialiserBoutonCartes(getMain());
        controleur.getVue().getFenetre().repaint();
        controleur.getVue().getFenetre().revalidate();
        if (c instanceof Attaque){
            Joueur cible = getCible();
            return jouerAttaque(c, cible);
        } else if (c instanceof Parade){
            return jouerParade(c);
        } else if (c instanceof Botte){
            return jouerBotte(c);
        } else if (c instanceof Distance){
            return jouerDistance(c);
        }   
        return null;
    }

}
