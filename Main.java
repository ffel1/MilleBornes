public class Main {
    
    public static void main(String[] args){
        Partie partie = new Partie();
        FenetreJeu fen = new FenetreJeu();
        fen.creerFenetreMenu();
        Controleur control = new Controleur(partie, fen);
        control.getModel().jouer();
    }
}
