import java.util.ArrayList;

public class CPUFast extends CPU{
    
    public CPUFast(String nom, int k, int id){
        super(nom, k, id);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte distances
     */
    public void choisirCarte(){
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
        }
    }
    
    public void appliquerAction(Carte c){};
}
