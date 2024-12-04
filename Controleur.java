import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Controleur 
{
    private Partie modele;
    private FenetreJeu vue;


    public Controleur(Partie modele, FenetreJeu vue) {
        this.modele = modele;
        this.vue = vue;

        vue.ajouterActionBoutonJouer(e -> {
            vue.creerFenetreJeu();
            nouvellePartie(false);
        });

        vue.ajouterActionBoutonQuitter(e -> System.exit(0));

        //Bouton Menu Principal
        vue.ajouterActionBoutonRetour(e -> {
            sauvegarder();
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreMenu(); 
            
        }); 

        //Bouton Nouvelle Partie 
        vue.ajouterActionBoutonNouvellePartie(e -> {
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate(); 
            vue.creerFenetreJeu();
            nouvellePartie(true); 
        });
    }

    private void nouvellePartie(boolean b){

        File fichier = new File("save.ser");
        if(fichier.exists())
        {
            chargerSauvegarder(fichier);
        }
        
        if(!modele.partieCree() || b){
            modele.nouvellePartie();
            vue.ajouterMessage("Nouvelle partie créée \n");
        }
        else
        {
            vue.ajouterMessage("Partie chargée \n");
        }

        //Bouton Pioche 
        vue.ajouterActionBoutonPioche(e -> {
        if(modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("L'utilisateur a pioché");
                if(modele.getJoueur1().mainPleine())
                {
                    vue.ajouterMessage(" mais sa main est pleine ! \n");
                }
                else
                {
                    vue.ajouterMessage(" \n");
                }
                modele.getJoueur1().piocher();
                vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                initialiserBoutonCartes(modele.getJoueur1().getMain());
            }
        }); 

        ArrayList<Carte> main = modele.getJoueur1().getMain();
        vue.ajouterMessage("La main du joueur a " + modele.getJoueur1().getMain().size() + "\n");
        vue.afficherCartesJoueur(main);
        initialiserBoutonCartes(main);
        vue.getFenetre().setLayout(null);
		vue.getFenetre().setVisible(true);
        modele.getJoueur1().monTour(true); // A ENLEVER APRES
        vue.ajouterMessage("Les participants sont : \n");
        for(int i = 0; i < modele.getJoueurs().size(); i++)
        {
            vue.ajouterMessage("- " + modele.getJoueurs().get(i).getNom() + "\n");
        }
        if(modele.getJoueur1().getMonTour())
        {
            vue.ajouterMessage("C'est le tour de l'utilisateur \n");
        }
    }

    public void initialiserBoutonCartes(ArrayList<Carte> main)
    {
        for(int i = 0; i < main.size(); i++){
            int j = i;
            vue.ajouterActionBoutonCarte(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    vue.ajouterMessage("\n Carte " + (j + 1) + " (" + main.get(j).getNom() + ")");
                    //Pour faire jouer le joueur
                    if(modele.getLeTourDe() == 0)                           
                    {
                        modele.getJoueur1().jouerCarte(main.get(j));
                    }
                    else
                    {
                        vue.ajouterMessage("Ce n'est pas votre tour ! \n");
                    }
                }
            }, i);
        }
        vue.getFenetre().setLayout(null);
        vue.getFenetre().setVisible(true);
    }

    public Partie getModel()
    {
        return modele;
    }
    public FenetreJeu getVue()
    {
        return vue;
    }

    private void sauvegarder(){
        try (FileOutputStream fichier = new FileOutputStream("save.ser", false); ObjectOutputStream oos = new ObjectOutputStream(fichier)){
            oos.writeObject(modele);
            System.out.println("Sauvegarde OK !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

    private void chargerSauvegarder(File fichier) {

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
}