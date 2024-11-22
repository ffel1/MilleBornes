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

        for (Carte carte : main) {
            if (carte instanceof Distance){
                carteAJouer = carte;
                break;
            }
        }

        if (carteAJouer == null && !main.isEmpty()){
            
        }

        if (carteAJouer != null){
            jouerCarte(carteAJouer);
            appliquerAction(carteAJouer);
            retirerCarte(carteAJouer);
        }
    }
    
    public void appliquerAction(Carte c){};
}
