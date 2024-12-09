import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.SwingConstants;

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
        boolean partieChargée;
        if(fichier.exists())
        {
            chargerSauvegarder(fichier);
        }
        
        if(!modele.partieCree() || b){
            modele.nouvellePartie();
            vue.ajouterMessage("Nouvelle partie créée \n");
            partieChargée = false;
        }
        else
        {
            vue.ajouterMessage("Partie chargée \n");
            partieChargée = true;
        }

        //Bouton Menu Principal
        vue.ajouterActionBoutonRetour(e -> {
            if(modele.getJoueur1().getEnTraindAttaquerAvec() != null)
            {
                modele.getJoueur1().getEnTraindAttaquerAvec().setImageBack();
            }
            sauvegarder();
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreMenu(); 
            
        }); 

        //Bouton Nouvelle Partie 
        vue.ajouterActionBoutonNouvellePartie(e -> {
            modele.getJoueur1().setDefausse(false);
            vue.getDefausse().setText("Défausse (temporaire)");
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreJeu();
            nouvellePartie(true); 
        });

        //Bouton défausse
        vue.ajouterActionBoutonBoutonDefausse(e -> {
            if(!modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n");
            }
            else if(modele.getJoueur1().getenTraindAttaquer())
            {
                vue.ajouterMessage("Vous ne pouvez pas défausser, vous êtes en train d'attaquer !");
            }
            else if(modele.getJoueur1().getDoitPiocher())
            {
                vue.ajouterMessage("Après avoir joué une botte vous devez piocher\n");
            }
            else if(!modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage("Vous devez piocher avant de défausser ! \n");
            }
            else if(modele.getJoueur1().getaJjoue())
            {
                vue.ajouterMessage("Vous avez déjà joué une carte, vous ne pouvez plus défausser ! \n");
            }
            else if(!modele.getJoueur1().mainPleine())
            {
                vue.ajouterMessage("Vous n'avez pas plus de 6 cartes, vous ne pouvez pas défausser \n");
            }
            else if(!modele.getJoueur1().getDefausse())
            {
                vue.ajouterMessage("Cliquez sur la carte que vous voulez défausser ! \nCliquez à nouveau sur la pioche pour annuler \n");
                modele.getJoueur1().setDefausse(true);
                vue.getDefausse().setText("Annuler");
            }
            else
            {
                vue.ajouterMessage("Vous avez changer d'avis \n");
                modele.getJoueur1().setDefausse(false);
                vue.getDefausse().setText("Défausse (temporaire)");
            }
            
        });

        //Bouton FinDeMonTour
        vue.ajouterActionBoutonFinDeMonTour(e -> {
            if(!modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n");
            }
            else if(modele.getJoueur1().getDefausse())
            {
                vue.ajouterMessage("Vous êtes en train de défausser, impossible de finir votre tour ! \n");
            }
            else if(modele.getJoueur1().getDoitPiocher())
            {
                vue.ajouterMessage("Après avoir joué une botte vous devez piocher \n");
            }
            else if(modele.getJoueur1().getenTraindAttaquer())
            {
                vue.ajouterMessage("Vous ne pouvez pas finir votre tour, vous êtes en train d'attaquer ! \n");
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
                    vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                    initialiserBoutonCartes(modele.getJoueur1().getMain());
                    modele.getJoueur1().setaJoue(false);
                    modele.getJoueur1().setaPioche(false);
                    modele.getJoueur1().monTour(false);
                    modele.getJoueur1().setaDefausse(false);
                    modele.getJoueur2().actionBot(this);
                    modele.getJoueur3().actionBot(this);
                    modele.getJoueur1().monTour(true);
                    vue.ajouterMessage("\nC'est votre tour ! \n");
                }
            }
            else if(!modele.getJoueur1().getaJjoue() && modele.getJoueur1().getMain().size() <= 6)
            {
                if(!modele.getJoueur1().getaPioche())
                {
                    vue.ajouterMessage("Vous sautez votre tour ! \n");
                }
                else
                {
                    vue.ajouterMessage("Vous ne jouez rien pendant ce tour ! \n");
                }
                modele.getJoueur1().setaJoue(false);
                modele.getJoueur1().setaPioche(false);
                modele.getJoueur1().monTour(false);
                modele.getJoueur1().setaDefausse(false);
                modele.getJoueur2().actionBot(this);
                modele.getJoueur3().actionBot(this);
                modele.getJoueur1().monTour(true);
                vue.ajouterMessage("\nC'est votre tour ! \n");
            }
            else
            {
                vue.ajouterMessage("Vous ne pouvez pas finir votre tour avec plus de 6 cartes ! \n");
                if(modele.getJoueur1().getaJjoue())
                {
                    vue.ajouterMessage("Vous devez défausser une carte ! \n");
                }
                else
                {
                    vue.ajouterMessage("Jouez ou défaussez une carte ! \n");
                }
            }
        });

        //Bouton AttaqueBotAgro
        vue.ajouterActionBoutonCPUAgro(e -> {
            if(modele.getJoueur1().getMonTour() && modele.getJoueur1().getenTraindAttaquer())
            {
                modele.getJoueur1().setCible(modele.getJoueur2());
                if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 0)
                {
                    modele.getJoueur1().jouerCarte(modele.getJoueur1().getEnTraindAttaquerAvec(),getControleur(),0);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 1)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer le CPU " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il a une botte qui le protège de cette attaque !\n");
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 2)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il subit déjà cette attaque !\n");
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 7)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il n'a même pas encore mit de feu vert !\n");
                }
                modele.getJoueur1().getEnTraindAttaquerAvec().setImageBack();
                vue.effacerCartesJoueurs();
                vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                initialiserBoutonCartes(modele.getJoueur1().getMain());
                modele.getJoueur1().setEnTraindAttaquer(false);
                modele.getJoueur1().setEstEnTraindAttaquerAvec(null);
                modele.getJoueur1().setCible(null);

                
            }
            else if(!(modele.getJoueur1().getenTraindAttaquer()))
            {
                vue.ajouterMessage("Vous n'êtes pas en train d'attaquer ! \n");
            }
            else
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n");
            }
        });

        //Bouton AttaqueBotFast
        vue.ajouterActionBoutonCPUFast(e -> {
            if(modele.getJoueur1().getMonTour() && modele.getJoueur1().getenTraindAttaquer())
            {
                modele.getJoueur1().setCible(modele.getJoueur2());
                if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 0)
                {
                    modele.getJoueur1().jouerCarte(modele.getJoueur1().getEnTraindAttaquerAvec(),getControleur(),0);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 1)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer le CPU " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il a une botte qui le protège de cette attaque !\n");
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 2)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il subit déjà cette attaque !\n");
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 7)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il n'a même pas encore mit de feu vert !\n");
                }
                modele.getJoueur1().getEnTraindAttaquerAvec().setImageBack();
                vue.effacerCartesJoueurs();
                vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                initialiserBoutonCartes(modele.getJoueur1().getMain());
                modele.getJoueur1().setEnTraindAttaquer(false);
                modele.getJoueur1().setEstEnTraindAttaquerAvec(null);
                modele.getJoueur1().setCible(null);

                
            }
            else if(!(modele.getJoueur1().getenTraindAttaquer()))
            {
                vue.ajouterMessage("Vous n'êtes pas en train d'attaquer ! \n");
            }
            else
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n");
            }
        });

        //Bouton Pioche 
        vue.ajouterActionBoutonPioche(e -> {
            if(modele.getJoueur1().getDoitPiocher())
            {
                modele.getJoueur1().setDoitPiocher(false);
                boolean dejaPioche = modele.getJoueur1().getaPioche();
                modele.getJoueur1().piocher();
                modele.getJoueur1().setaPioche(dejaPioche);
                vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                initialiserBoutonCartes(modele.getJoueur1().getMain());
            }
            else if(modele.getJoueur1().getenTraindAttaquer())
            {
                vue.ajouterMessage("Vous êtes en train d'attaquer, vous ne pouvez pas piocher ! \n");
            }
            else if(modele.getJoueur1().getMonTour() && !modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage("Vous avez pioché");
                if(modele.getJoueur1().mainPleine())
                {
                    vue.ajouterMessage(" mais votre main est pleine ! \n");
                }
                else
                {
                    vue.ajouterMessage("\n");
                    modele.getJoueur1().piocher();
                    vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                    initialiserBoutonCartes(modele.getJoueur1().getMain());
                }
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
        vue.ajouterMessage("Les participants sont : \n");
        for(int i = 0; i < modele.getJoueurs().size(); i++)
        {
            vue.ajouterMessage("- " + modele.getJoueurs().get(i).getNom() + "\n");
        }
        if(partieChargée)
        {
            vue.ajouterMessage("La partie reprends, c'était votre tour ! \n");
            modele.getJoueur1().monTour(true);
        }
        else if(modele.getQuiCommence() == 0)
        {
            vue.ajouterMessage("Vous commencez à jouer ! \n");
            modele.getJoueur1().monTour(true);
        }
        else if(modele.getQuiCommence() == 1)
        {
            vue.ajouterMessage("CPU Agro commence à jouer ! \n");
            modele.getJoueur2().actionBot(this);
            modele.getJoueur3().actionBot(this);
            modele.getJoueur1().monTour(true);
            vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n");
        }
        else if(modele.getQuiCommence() == 2)
        {
            vue.ajouterMessage("CPU Fast commence à jouer ! \n");
            modele.getJoueur3().actionBot(this);
            modele.getJoueur1().monTour(true);
            vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n");
        }
        vue.getFenetre().revalidate();
        vue.getFenetre().repaint();
    }

    //Initialisation cartes
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
                        else if(modele.getJoueur1().getenTraindAttaquer() && modele.getJoueur1().getMain().get(j) == modele.getJoueur1().getEnTraindAttaquerAvec())
                        {
                            vue.ajouterMessage("Vous avez changé d'avis \n");
                            modele.getJoueur1().getEnTraindAttaquerAvec().setImageBack();
                            vue.effacerCartesJoueurs();
                            vue.afficherCartesJoueur(main);
                            initialiserBoutonCartes(main);
                            modele.getJoueur1().setEnTraindAttaquer(false);
                            modele.getJoueur1().setEstEnTraindAttaquerAvec(null);
                            modele.getJoueur1().setCible(null);
                        }
                        else if(modele.getJoueur1().getenTraindAttaquer())
                        {
                            vue.ajouterMessage("Vous êtes en train d'attaquer, vous ne pouvez pas jouer de cartes ! \n");
                        }
                        else if(modele.getJoueur1().getDoitPiocher())
                        {
                            vue.ajouterMessage("Après avoir joué une botte vous devez piocher\n");
                        }
                        else if(modele.getJoueur1().getaDefausse())
                        {
                            vue.ajouterMessage("Vous ne pouvez plus jouer après avoir défaussé\n");
                        }
                        else if(modele.getJoueur1().getaJjoue() && !(modele.getJoueur1().getMain().get(j) instanceof Botte))
                        {
                            vue.ajouterMessage("Vous avez déjà joué lors de votre tour \n");
                        }
                        else if(modele.getJoueur1().getDefausse())
                        {
                            vue.ajouterMessage(modele.getJoueur1().defausse(modele.getJoueur1().getMain().get(j),getControleur()));
                            modele.getJoueur1().setaDefausse(true);
                            vue.getDefausse().setText("Défausse (temporaire)");
                            modele.getJoueur1().setDefausse(false);
                        }
                        else
                        {
                            if(modele.getJoueur1().getMain().get(j) instanceof Botte)
                            {
                                modele.getJoueur1().jouerCarte(modele.getJoueur1().getMain().get(j),getControleur(),j+1);
                                vue.ajouterMessage("Vous devez piocher à nouveau ! \n");
                                modele.getJoueur1().setDoitPiocher(true);
                            }
                            else if(modele.getJoueur1().getMain().get(j) instanceof Attaque)
                            {
                                vue.ajouterMessage("Choisissez le CPU sur lequel vous voulez lancer votre attaque \n");
                                modele.getJoueur1().setEnTraindAttaquer(true);
                                modele.getJoueur1().setEstEnTraindAttaquerAvec(modele.getJoueur1().getMain().get(j));
                                modele.getJoueur1().getMain().get(j).setImageIcon(null);
                                vue.effacerCartesJoueurs();
                                vue.afficherCartesJoueur(main);  
                                initialiserBoutonCartes(modele.getJoueur1().getMain());                                         
                            }
                            else
                            {
                                if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 0)
                                {
                                    modele.getJoueur1().jouerCarte(main.get(j),getControleur(),j+1);
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 3)
                                {
                                    vue.ajouterMessage("Vous ne pouvez pas avancer sans feu vert ! \n");
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 4)
                                {
                                    //vue.ajouterMessage("Une attaque vous empêche d'avancer ! \n");
                                    String message = modele.getJoueur1().jouerDistance(modele.getJoueur1().getMain().get(j));
                                    vue.ajouterMessage(message);
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 5)
                                {
                                    vue.ajouterMessage("Vous avez déjà un feu vert ! \n");
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 6)
                                {
                                    vue.ajouterMessage("Vous ne subissez aucune attaque que cette parade permet de contrer !\n");
                                }
                            }
                            //vue.AvancerVoiture(modele.getJoueur1().getKilometre());
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

    public Controleur getControleur()
    {
        return this;
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
            modele.getJoueur1().setDefausse(false);
            vue.getDefausse().setText("Défausse (temporaire)");
            modele.getJoueur1().setEnTraindAttaquer(false);
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