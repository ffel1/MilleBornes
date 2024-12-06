import java.util.ArrayList;

public class CPUFast extends CPU{
    
    public CPUFast(String nom, int k, int id,Partie partie){
        super(nom, k, id, partie);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte distances
     */
    public Carte choisirCarte(){
        ArrayList<Carte> main = getMain();
        Carte carteAJouer = null;
        boolean findParade = false, findDistance = false;

        //botte -> parade -> distance -> attaque

        for(Carte carte : main){
            if(carte instanceof Botte){
                carteAJouer = carte;
                break;
            }
            else if(carte instanceof Parade){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findParade){
                carteAJouer = carte;
                findDistance = true;
            }
            else if(carte instanceof Attaque && !findParade && !findDistance){
                carteAJouer = carte;
            }
        }

        if (carteAJouer != null){
            jouerCarte(carteAJouer);
            return carteAJouer;
        }
        return null;
    }
    
}
