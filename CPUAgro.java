public class CPUAgro extends CPU{

    public CPUAgro(String nom, int k, int id){
        super(nom, k, id);
    }

    /*
     * Choisi de jouer une carte, en priorité des carte attaques
     */
    public void choisirCarte(){
        // A Completer
        jouerCarte(null);
    }

    public void appliquerAction(Carte c){};
}
