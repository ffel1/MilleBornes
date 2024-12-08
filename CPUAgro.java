import java.util.ArrayList;
import java.util.Random;

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
            else if(carte instanceof Attaque && !findAttaque && verification(carte, this, getCible(carte))){
                carteAJouer = carte;
                findAttaque = true;
            }
            else if (carte.getType() == TypeCarte.FEU_VERT && !findAttaque && verification(carte, this, null)) 
            {
                carteAJouer = carte;
                findFeuVert = true;
            }
            else if(carte instanceof Parade && !findAttaque && !findFeuVert && verification(carte, this, null)){
                carteAJouer = carte;
                findParade = true;
            }
            else if(carte instanceof Distance && !findAttaque && !findParade && !findFeuVert && verification(carte, this, null)){
                if((carteAJouer instanceof Distance && carte.getKilometre() > carteAJouer.getKilometre()) || !(carteAJouer instanceof Distance))
                {
                    carteAJouer = carte;
                    System.out.println("Le bot agro décide de jouer une carte distance");
                }
            }
        }

        return carteAJouer;
    }

    @Override
    public Carte choixDeDefausse()
    {
        ArrayList<Carte> main = getMain();
        Carte carteADefausser = main.get(0);

        //botte -> attaque -> parade -> distance

        for(Carte carte : main){
            if(carte instanceof Distance){
                if(!(carteADefausser instanceof Distance))
                {
                    carteADefausser = carte;
                }
                else if(carteADefausser instanceof Distance && carteADefausser.getKilometre() > carte.getKilometre())
                {
                    carteADefausser = carte;
                }
            }
            else if(carte instanceof Parade){
                if(carteADefausser instanceof Attaque || carteADefausser instanceof Botte)
                {
                    carteADefausser = carte;
                }
            }
            else if (carte instanceof Attaque) 
            {
                if(carteADefausser instanceof Botte)
                {
                    carteADefausser = carte;
                }
            }
        }
        System.out.println(getNom() + " décide de jeter la carte " + carteADefausser.getNom());
        return carteADefausser;
    }
}
