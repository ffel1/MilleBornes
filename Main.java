public class Main {
    
    public static void main(String[] args){
        Partie partie = new Partie();
        FenetreJeu fen = new FenetreJeu();
        fen.creerFenetreMenu();
        new Controleur(partie, fen);
    }
}
