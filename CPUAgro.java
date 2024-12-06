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
        boolean findAttaque = false, findParade = false;

        //botte -> attaque -> parade -> distance

        for(Carte carte : main){
            if(carte instanceof Botte && verification(carteAJouer, this, getCible())){
                carteAJouer = carte;
                break;
            }
            else if(carte instanceof Attaque && verification(carteAJouer, this, getCible())){
                carteAJouer = carte;
                findAttaque = true;
            }
            else if(carte instanceof Parade && !findAttaque && verification(carteAJouer, this, getCible())){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findAttaque && !findParade){
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
