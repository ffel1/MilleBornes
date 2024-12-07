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
        boolean findDistance = false, findParade = false, findFeuVert = false;

        //botte -> attaque -> parade -> distance

        for(Carte carte : main){
            if(carte instanceof Botte){
                carteAJouer = carte;
                break;
            }
            else if(carte.getType() == TypeCarte.FEU_VERT && verification(carte, this, null)){
                carteAJouer = carte;
                findFeuVert = true;
            }
            else if(carte instanceof Parade && !findFeuVert && verification(carte, this, null)){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findParade && !findFeuVert && getFeuVert()){
                if(carteAJouer instanceof Distance && carte.getKilometre() > carteAJouer.getKilometre())
                {
                    carteAJouer = carte;
                }
            }
            else if(carte instanceof Attaque && !findDistance && !findFeuVert && verification(carte, this, getCible())){
                carteAJouer = carte;
            }
        }

        return carteAJouer;
    }
    
}
