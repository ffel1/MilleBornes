import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JOptionPane;

public class Controleur {
    private Partie modele;
    private FenetreJeu vue;

    public Controleur(Partie modele, FenetreJeu vue) {
        this.modele = modele;
        this.vue = vue;

        vue.ajouterActionBoutonJouer(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                vue.creerFenetreJeu();
                modele.nouvellePartie();
                nouvellePartie();
            }
        });

        vue.ajouterActionBoutonQuitter(e -> System.exit(0));

        vue.ajouterActionBoutonRetour(e -> {
            int option = JOptionPane.showConfirmDialog(vue.getFenetre(), "Voulez-vous vraiment retourner au menu principal ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            // Vérifier la réponse de l'utilisateur
            if (option == JOptionPane.YES_OPTION) {
                vue.getFenetre().getContentPane().removeAll();
                vue.getFenetre().repaint();
                vue.getFenetre().revalidate();
                vue.creerFenetreMenu(); 
            }
        }); 

        vue.ajouterActionBoutonNouvellePartie(e -> {
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate(); 
            vue.creerFenetreJeu();
            modele.nouvellePartie();
            nouvellePartie(); 
        });  
    }

    private void nouvellePartie(){
        ArrayList<Carte> main = modele.getJoueur1().getMain();
        vue.afficherCartesJoueur(main);
        for(int i = 0; i < 6; i++){
            int j = i;
            vue.ajouterActionBoutonCarte(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    vue.ajouterMessage("\n Carte " + (j + 1) + " (" + main.get(j).getNom() + ")");
                }
            }, i);
        }
        vue.getFenetre().setLayout(null);
		vue.getFenetre().setVisible(true);
    }
}
