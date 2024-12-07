import java.util.ArrayList;

public class CPUAgro extends CPU{

    public CPUAgro(String nom, int k, int id, Partie partie){
        super(nom, k, id, partie);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte attaques
     */
    public Carte choisirCarte(){
        ArrayList<Carte> main = getMain();
        Carte carteAJouer = null;
        boolean findAttaque = false, findParade = false, findFeuVert = false;

        //botte -> attaque -> parade -> distance

        for(Carte carte : main){
            if(carte instanceof Botte){
                carteAJouer = carte;
                break;
            }
            else if(carte instanceof Attaque && verification(carte, this, getCible())){
                carteAJouer = carte;
                findAttaque = true;
            }
            else if (carte.getType() == TypeCarte.FEU_VERT && !findAttaque) 
            {
                carteAJouer = carte;
                findFeuVert = true;
            }
            else if(carte instanceof Parade && !findAttaque && !findFeuVert && verification(carte, this, null)){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findAttaque && !findParade && !findFeuVert && getFeuVert()){
                carteAJouer = carte;
            }
        }

        return carteAJouer;
    }

}
