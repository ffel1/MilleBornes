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
        vue.creerFenetreMenu();

        vue.ajouterActionBoutonJouer(e -> {
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreJeu();
            nouvellePartie(false);
        });

        vue.ajouterActionBoutonQuitter(e -> System.exit(0));
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

        //Bouton FinDeMonTour
        vue.ajouterActionBoutonFinDeMonTour(e -> {
            if(!modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n");
            }
            else if(modele.getJoueur1().getaJjoue())
            {
                if(modele.getJoueur1().getMain().size() > 6)
                {
                    vue.ajouterMessage("Vous devez défausser une carte ! \n");
                }
                else
                {
                    vue.ajouterMessage("C'est la fin de votre tour ! \n");
                    modele.getJoueur1().setaJoue(false);
                    modele.getJoueur1().setaPioche(false);
                    modele.getJoueur1().monTour(false);
                    modele.getJoueur2().actionBot(this);
                }
            }
            else if(!modele.getJoueur1().getaJjoue() && modele.getJoueur1().getMain().size() <= 6)
            {
                vue.ajouterMessage("Vous sautez votre tour ! \n");
                modele.getJoueur1().setaJoue(false);
                modele.getJoueur1().setaPioche(false);
                modele.getJoueur1().monTour(false);
                modele.getJoueur2().actionBot(this);
            }
            else
            {
                vue.ajouterMessage("Vous devez défausser une carte ! \n");
            }
        });

        //Bouton Pioche 
        vue.ajouterActionBoutonPioche(e -> {
            if(modele.getJoueur1().getMonTour() && !modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage("Vous avez pioché");
                if(modele.getJoueur1().mainPleine())
                {
                    vue.ajouterMessage(" mais votre main est pleine ! \n");
                }
                else
                {
                    vue.ajouterMessage(" \n");
                }
                modele.getJoueur1().piocher();
                vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                initialiserBoutonCartes(modele.getJoueur1().getMain());
            }
            else if(modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage(" Vous avez déjà pioché ! \n");
            }
            else
            {
                vue.ajouterMessage(" Ce n'est pas votre tour \n");
            }
        }); 

        ArrayList<Carte> main = modele.getJoueur1().getMain();
        vue.afficherCartesJoueur(main);
        initialiserBoutonCartes(main);
        vue.getFenetre().setLayout(null);
		vue.getFenetre().setVisible(true);
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
                    //Pour faire jouer le joueur
                    if(modele.getJoueur1().getMonTour())                           
                    {
                        if(!modele.getJoueur1().getaPioche())
                        {
                            vue.ajouterMessage("Vous devez d'abord piocher pour jouer une carte \n");
                        }
                        else if(modele.getJoueur1().getaJjoue())
                        {
                            vue.ajouterMessage("Vous avez déjà joué lors de votre tour \n");
                        }
                        else
                        {
                            vue.ajouterMessage("Vous avez joué la carte " + (j + 1) + " (" + main.get(j).getNom() + ") \n");
                            modele.getJoueur1().jouerCarte(main.get(j));
                        }
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