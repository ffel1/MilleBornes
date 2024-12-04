public class Main {
    
    public static void main(String[] args){
        Partie partie = new Partie();
        FenetreJeu fen = new FenetreJeu();
        new Controleur(partie, fen);
    }
}
