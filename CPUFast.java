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
            else if(carte instanceof Distance && !findParade && !findFeuVert && verification(carte, this, null)){
                if((carteAJouer instanceof Distance && carte.getKilometre() > carteAJouer.getKilometre()) || !(carteAJouer instanceof Distance))
                {
                    carteAJouer = carte;
                }
            }
            else if(carte instanceof Attaque && !findDistance && !findFeuVert && verification(carte, this, getCible())){
                carteAJouer = carte;
                System.out.println("Le bot fast décide de jouer une carte distance");
            }
        }

        return carteAJouer;
    }

    @Override
    public Carte choixDeDefausse()
    {
        ArrayList<Carte> main = getMain();
        Carte carteADefausser = main.get(0);

        //botte -> distance -> parade -> attaque

        for(Carte carte : main){
            if(carte instanceof Attaque) 
            {
                carteADefausser = carte;
            }
            else if(carte instanceof Parade)
            {
                if(carteADefausser instanceof Distance || carteADefausser instanceof Botte)
                {
                    carteADefausser = carte;
                }
            }
            else if(carte instanceof Distance)
            {
                if(carteADefausser instanceof Distance && carteADefausser.getKilometre() > carte.getKilometre())
                {
                    carteADefausser = carte;
                }
            }
        }
        System.out.println(getNom() + " décide de jeter la carte " + carteADefausser.getNom());
        return carteADefausser;
    }
}
