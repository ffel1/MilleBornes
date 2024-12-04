import java.util.ArrayList;

public class CPUAgro extends CPU{

    public CPUAgro(String nom, int k, int id){
        super(nom, k, id);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte attaques
     */
    public void choisirCarte(){
        ArrayList<Carte> main = getMain();
        Carte carteAJouer = null;
        boolean findAttaque = false, findParade = false;

        //botte -> attaque -> parade -> distance

        for(Carte carte : main){
            if(carte instanceof Botte){
                carteAJouer = carte;
                break;
            }
            else if(carte instanceof Attaque){
                carteAJouer = carte;
                findAttaque = true;
            }
            else if(carte instanceof Parade && !findAttaque){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findAttaque && !findParade){
                carteAJouer = carte;
            }
        }

        if (carteAJouer != null){
            jouerCarte(carteAJouer);
        }
    }

    public void appliquerAction(Carte c){};
}
