import java.util.ArrayList;

public class CPUAgro extends CPU{

    public CPUAgro(String nom, int k, int id){
        super(nom, k, id);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte attaques
     */
    public void choisirCarte(Carte c){
        ArrayList<Carte> main = getMain();
        Carte carteAJouer = null;

        for (Carte carte : main) {
            if (carte instanceof Attaque){
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
