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
    public void jouerCarte(Carte c) {
        aJoue = true; //A METTRE DANS LA VERIFICATION QUAND CA SERAIT FINIT (SAUF POUR LES CARTES BOTTES)
        /*if (c instanceof Attaque){
            Joueur cible = getCible();
            jouerAttaque(c, cible);
        } else if (c instanceof Parade){
            jouerParade(c);
        } else if (c instanceof Botte){
            jouerBotte(c);
        } else if (c instanceof Distance){
            jouerDistance(c);
        }   */
    }

}
