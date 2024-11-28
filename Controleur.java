import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                chargerSauvegarder();
                vue.creerFenetreJeu();
                nouvellePartie(false);
            }
        });

        vue.ajouterActionBoutonQuitter(e -> System.exit(0));

        vue.ajouterActionBoutonRetour(e -> {
            int option = JOptionPane.showConfirmDialog(vue.getFenetre(), "Voulez-vous vraiment retourner au menu principal ?", "Confirmer", JOptionPane.YES_NO_OPTION);
            // Vérifier la réponse de l'utilisateur
            if (option == JOptionPane.YES_OPTION) {
                sauvegarder();
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
            nouvellePartie(true); 
        });  

        /* 
        vue.ajouterActionBoutonSauvegarder(e -> {
            sauvegarder();
        });  

        vue.ajouterActionBoutonChargerSauvegarde(e -> {
            chargerSauvegarder();
            vue.creerFenetreJeu();
            nouvellePartie(false);
        });
        */
    }

    private void nouvellePartie(boolean b){
        if(b){
            modele.nouvellePartie();
        }
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
            System.out.println(main.get(j).getNom());
        }
        vue.getFenetre().setLayout(null);
		vue.getFenetre().setVisible(true);
    }

    private void sauvegarder(){
        try (FileOutputStream fichier = new FileOutputStream("save.ser"); ObjectOutputStream oos = new ObjectOutputStream(fichier)){
            oos.writeObject(modele);
            System.out.println("Sauvegarde OK !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void chargerSauvegarder() {
        File fichier = new File("save.ser");
        if (!fichier.exists()) {
            sauvegarder(); 
        }

        try (FileInputStream fileIn = new FileInputStream(fichier);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {

            modele = (Partie) ois.readObject();

            if (modele != null && modele.getJoueur1() != null) {
                System.out.println("Première carte de la main : " + modele.getJoueur1().getMain().get(0));
            } else {
                System.err.println("Les données chargées sont incomplètes ou corrompues.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : ");
            e.printStackTrace();
        }
    }

    /* 
    private void chargerSauvegarder(){
        try (FileInputStream fichier = new FileInputStream("save.ser"); ObjectInputStream ois = new ObjectInputStream(fichier)) {
            modele = (Partie)ois.readObject();
            System.out.println(modele.getJoueur1().getMain().get(0));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur lors du chargement : " + e.getMessage());
        }
    }
    */
}
