package main;

import java.util.ArrayList;

public class Utilisateur extends Joueur{

    private boolean monTour = false, aPioche = false, aJoue = false, defausse = false, aDefausse = false, doitPiocher = false, enTraindAttaquer = false;
    private Carte enTraindAttaquerAvec;
    private CPU cible;

    public Utilisateur(String nom, int k, int id, Partie partie){
        super(nom, k, id, partie);
    }
    
    public void appliquerAction(Carte c){};

    @Override
    public Joueur getCible(Carte c)
    {
        return cible;
    }
    
    public Carte getEnTraindAttaquerAvec()
    {
        return enTraindAttaquerAvec;
    }

    public void setCible(CPU c)
    {
        cible = c;
    }

    public void setEstEnTraindAttaquerAvec(Carte c)
    {
        enTraindAttaquerAvec = c;
    }

    public void monTour(boolean monTour)
    {
        this.monTour = monTour;
    }

    public boolean getDoitPiocher()
    {
        return doitPiocher;
    }

    public boolean getaDefausse()
    {
        return aDefausse;
    }

    public void setaDefausse(boolean b)
    {
        aDefausse = b;
    }

    public boolean getenTraindAttaquer()
    {
        return enTraindAttaquer;
    }

    public void setEnTraindAttaquer(boolean b)
    {
        enTraindAttaquer = b;
    }

    public void setDoitPiocher(boolean b)
    {
        doitPiocher = b;
    }

    public boolean getDefausse()
    {
        return defausse;
    }

    public void setDefausse(boolean b)
    {
        defausse = b;
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
        ArrayList<Carte> pioche = getPartie().getPioche();  
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
        if(!(c instanceof Botte))
        {
            aJoue = true; 
        }
        if(c instanceof Attaque)
        {
            controleur.getVue().ajouterMessage("Vous avez attaqué le CPU " + getCible(c).getNom() + " avec " + c.getNom() + "! \n", true);
        }
        else
        {
            controleur.getVue().ajouterMessage("Vous avez joué la carte " + nbCarte + " (" + c.getNom() + ") \n", true); 
        }
        getMain().remove(c);
        controleur.getVue().effacerCartesJoueurs();
        controleur.getVue().afficherCartesJoueur(getMain());
        controleur.initialiserBoutonCartes(getMain());
        controleur.getVue().getFenetre().repaint();
        controleur.getVue().getFenetre().revalidate();
        if (c instanceof Attaque){
            Joueur cible = getCible(c);
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

    /*
     * Vérifie qu'une carte soit jouable
     * Renvoie à l'utilisateur la raison de la non validité de son action (chaque entier correspond à une raison), 0 veut dire que c'est valide
     */
    public int verificationUtilisateur(Carte c, Joueur u, Joueur cible){
        if (c instanceof Attaque){
            if(c.getType() == TypeCarte.FEU_ROUGE && !cible.getFeuVert())
            {
                return 7;
            }
            for (Carte carte : cible.getBottesPosées()) {
                switch (c.getType()) {
                    case CREVAISON:
                        if (carte.getType() == TypeCarte.INCREVABLE) return 1;
                        break;
                    case ACCIDENT:
                        if (carte.getType() == TypeCarte.AS_DU_VOLANT) return 1;
                        break;
                    case PANNE_D_ESSENCE:
                        if (carte.getType() == TypeCarte.CAMION_CITERNE) return 1;
                        break;
                    case LIMITATION_DE_VITESSE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE) return 1;
                        break;
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.VEHICULE_PRIORITAIRE) return 1;
                        break;
                    default:
                        break;
                }
            }
            for (Carte carte : cible.getAttaquesEnCours()) {
                switch (c.getType()) {
                    case CREVAISON:
                        if (carte.getType() == TypeCarte.CREVAISON) return 2;
                        break;
                    case ACCIDENT:
                        if (carte.getType() == TypeCarte.ACCIDENT) return 2;
                        break;
                    case PANNE_D_ESSENCE:
                        if (carte.getType() == TypeCarte.PANNE_D_ESSENCE) return 2;
                        break;
                    case LIMITATION_DE_VITESSE:
                        if (carte.getType() == TypeCarte.LIMITATION_DE_VITESSE) return 2;
                        break;
                    case FEU_ROUGE:
                        if (carte.getType() == TypeCarte.FEU_ROUGE) return 2;
                        break;
                    default:
                        break;
                }
            }
            
        } 
        else if (c instanceof Distance){
            if(!getFeuVert())
            {
                return 3;
            }
            if(c.getKilometre()+u.getKilometre() > 100)
            {
                return 10;
            }
            for (Carte carte : u.getAttaquesEnCours()){
                switch (c.getType()) {
                    case _25KM :
                    case _50KM :
                        if (carte.getType() == TypeCarte.FEU_ROUGE | carte.getType() == TypeCarte.PANNE_D_ESSENCE | carte.getType() == TypeCarte.CREVAISON | carte.getType() == TypeCarte.ACCIDENT) return 4;
                        break;
                    case _75KM : 
                    case _100KM :
                    case _200KM :
                        if (carte.getType() == TypeCarte.FEU_ROUGE | carte.getType() == TypeCarte.PANNE_D_ESSENCE | carte.getType() == TypeCarte.CREVAISON | carte.getType() == TypeCarte.ACCIDENT | carte.getType() == TypeCarte.LIMITATION_DE_VITESSE) return 4;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Parade){
            switch (c.getType()) {
                case FEU_VERT:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.FEU_ROUGE) || !getFeuVert())
                    {
                        return 0;
                    }
                    else if(getFeuVert())
                    {
                        return 5;
                    }
                case FIN_LIMITATION_VITESSE:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.LIMITATION_DE_VITESSE))
                    {
                        return 0;
                    }
                case ESSENCE:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.PANNE_D_ESSENCE))
                    {
                        return 0;
                    }
                case ROUE_DE_SECOURS:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.CREVAISON))
                    {
                        return 0;
                    }
                case REPARATION:
                    if(getAttaquesEnCours().stream().anyMatch(carte -> carte.getType() == TypeCarte.ACCIDENT))
                    {
                        return 0;
                    }
                default:
                    break;
            }
            return 6;
        }
        return 0;
    }

    public String defausse(Carte c, Controleur controleur)
    {
        getMain().remove(c);
        controleur.getVue().effacerCartesJoueurs();
        controleur.getVue().afficherCartesJoueur(getMain());
        controleur.initialiserBoutonCartes(getMain());
        controleur.getVue().getFenetre().repaint();
        controleur.getVue().getFenetre().revalidate();
        return "Vous avez défaussé la carte : " + c.getNom() + "\n";
    }
}

