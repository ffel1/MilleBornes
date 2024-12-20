package main;

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
    private Son listeSon;

    public Controleur(Partie modele, FenetreJeu vue) 
    {
        this.modele = modele;
        this.vue = vue;
        listeSon = new Son();
        vue.creerFenetreMenu();

        vue.ajouterActionBoutonJouer(e -> {
            //joueMusic(0);
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreJeu();
            nouvellePartie(false);
        });

        vue.ajouterActionBoutonHistorique(e -> {
            vue.getFenetre().getContentPane().removeAll();
            vue.getFenetre().repaint();
            vue.getFenetre().revalidate();
            vue.creerFenetreHistorique();
        });

        vue.ajouterActionBoutonQuitter(e -> System.exit(0));
    }

    public Son getListeSon(){
        return listeSon;
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
            partieChargée = false;
            modele.initialisationNomDeLaPartie();
        }
        else
        {
            partieChargée = true;
        }

        String nomDeLaPartie = modele.getNomDeLaPartie();
        vue.setNomDeLaPartie(nomDeLaPartie);
        if(partieChargée)
        {
            vue.chargerLogs();
        }

        vue.mettreAJourAttaques(modele);
        vue.mettreAJourBottes(modele);

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
            vue.ajouterMessage("Vous avez mis fin à la partie, les points ne seront pas comptabilisés !", true);
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
            vue.avancerVoiture(700, 1, this);
            if(!modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n", false);
            }
            else if(modele.getJoueur1().getenTraindAttaquer())
            {
                vue.ajouterMessage("Vous ne pouvez pas défausser, vous êtes en train d'attaquer !", false);
            }
            else if(modele.getJoueur1().getDoitPiocher())
            {
                vue.ajouterMessage("Après avoir joué une botte vous devez piocher\n", false);
            }
            else if(!modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage("Vous devez piocher avant de défausser ! \n", false);
            }
            else if(modele.getJoueur1().getaJjoue())
            {
                vue.ajouterMessage("Vous avez déjà joué une carte, vous ne pouvez plus défausser ! \n", false);
            }
            else if(!modele.getJoueur1().mainPleine())
            {
                vue.ajouterMessage("Vous n'avez pas plus de 6 cartes, vous ne pouvez pas défausser \n", false);
            }
            else if(!modele.getJoueur1().getDefausse())
            {
                vue.ajouterMessage("Cliquez sur la carte que vous voulez défausser ! \nCliquez à nouveau sur la pioche pour annuler \n", false);
                modele.getJoueur1().setDefausse(true);
                vue.getDefausse().setText("Annuler");
            }
            else
            {
                vue.ajouterMessage("Vous avez changer d'avis \n", false);
                modele.getJoueur1().setDefausse(false);
                vue.getDefausse().setText("Défausse (temporaire)");
            }
            
        });

        //Bouton FinDeMonTour
        vue.ajouterActionBoutonFinDeMonTour(e -> {
            if(!modele.getJoueur1().getMonTour())
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n", false);
            }
            else if(modele.getJoueur1().getDefausse())
            {
                vue.ajouterMessage("Vous êtes en train de défausser, impossible de finir votre tour ! \n", false);
            }
            else if(modele.getJoueur1().getDoitPiocher())
            {
                vue.ajouterMessage("Après avoir joué une botte vous devez piocher \n", false);
            }
            else if(modele.getJoueur1().getenTraindAttaquer())
            {
                vue.ajouterMessage("Vous ne pouvez pas finir votre tour, vous êtes en train d'attaquer ! \n", false);
            }
            else if(modele.getJoueur1().getaJjoue())
            {
                if(modele.getJoueur1().getMain().size() > 6)
                {
                    vue.ajouterMessage("Vous devez défausser une carte ! \n", false);
                }
                else
                {
                    vue.ajouterMessage("\nC'est la fin de votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
                    if(modele.gagnant() == modele.getJoueur1())
                    {
                        vue.ajouterMessage("\n VOUS AVEZ GAGNE LA PARTIE !! BRAVO ! \n", true);
                    }
                    vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                    initialiserBoutonCartes(modele.getJoueur1().getMain());
                    modele.getJoueur1().setaJoue(false);
                    modele.getJoueur1().setaPioche(false);
                    modele.getJoueur1().monTour(false);
                    modele.getJoueur1().setaDefausse(false);
                    modele.getJoueur2().actionBot(this);
                    vue.avancerVoiture(modele.getJoueur2().getKilometre(), 1, this);
                    modele.getJoueur3().actionBot(this);
                    vue.avancerVoiture(modele.getJoueur3().getKilometre(), 2, this);
                    modele.getJoueur1().monTour(true);
                    vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
                    vue.avancerVoiture(modele.getJoueur1().getKilometre(), 0, this);
                    vue.mettreAJourAttaques(modele);
                    vue.mettreAJourBottes(modele);
                }
            }
            else if(!modele.getJoueur1().getaJjoue() && modele.getJoueur1().getMain().size() <= 6)
            {
                if(!modele.getJoueur1().getaPioche())
                {
                    vue.ajouterMessage("Vous sautez votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
                }
                else
                {
                    vue.ajouterMessage("Vous ne jouez rien pendant ce tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
                }
                modele.getJoueur1().setaJoue(false);
                modele.getJoueur1().setaPioche(false);
                modele.getJoueur1().monTour(false);
                modele.getJoueur1().setaDefausse(false);
                modele.getJoueur2().actionBot(this);
                vue.avancerVoiture(modele.getJoueur2().getKilometre(), 1, this);
                modele.getJoueur3().actionBot(this);
                vue.avancerVoiture(modele.getJoueur3().getKilometre(), 2, this);
                modele.getJoueur1().monTour(true);
                vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
                vue.avancerVoiture(modele.getJoueur1().getKilometre(), 0, this);
                vue.mettreAJourAttaques(modele);
                vue.mettreAJourBottes(modele);
            }
            else
            {
                vue.ajouterMessage("Vous ne pouvez pas finir votre tour avec plus de 6 cartes ! \n", true);
                if(modele.getJoueur1().getaJjoue())
                {
                    vue.ajouterMessage("Vous devez défausser une carte ! \n", true);
                }
                else
                {
                    vue.ajouterMessage("Jouez ou défaussez une carte ! \n", true);
                }
            }
        });

        //Bouton AttaqueBotAgro
        vue.ajouterActionBoutonCPUAgro(e -> {
            joueMusic(1);
            if(modele.getJoueur1().getMonTour() && modele.getJoueur1().getenTraindAttaquer())
            {
                modele.getJoueur1().setCible(modele.getJoueur2());
                if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 0)
                {
                    modele.getJoueur1().jouerCarte(modele.getJoueur1().getEnTraindAttaquerAvec(),getControleur(),0);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 1)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer le CPU " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il a une botte qui le protège de cette attaque !\n", false);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 2)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il subit déjà cette attaque !\n", false);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 7)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il n'a même pas encore mit de feu vert !\n", false);
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
                vue.ajouterMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            else
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n", false);
            }
        });

        //Bouton AttaqueBotFast
        vue.ajouterActionBoutonCPUFast(e -> {
            joueMusic(1);
            if(modele.getJoueur1().getMonTour() && modele.getJoueur1().getenTraindAttaquer())
            {
                modele.getJoueur1().setCible(modele.getJoueur3());
                if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 0)
                {
                    modele.getJoueur1().jouerCarte(modele.getJoueur1().getEnTraindAttaquerAvec(),getControleur(),0);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 1)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer le CPU " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il a une botte qui le protège de cette attaque !\n", false);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 2)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il subit déjà cette attaque !\n", false);
                }
                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getEnTraindAttaquerAvec(), modele.getJoueur1(), modele.getJoueur1().getCible(null)) == 7)
                {
                    vue.ajouterMessage("Vous ne pouvez pas attaquer " + modele.getJoueur1().getCible(null).getNom() + " avec " + modele.getJoueur1().getEnTraindAttaquerAvec().getNom() + " car il n'a même pas encore mit de feu vert !\n", false);
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
                vue.ajouterMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            else
            {
                vue.ajouterMessage("Ce n'est pas votre tour ! \n", false);
            }
        });

        //Bouton Pioche 
        vue.ajouterActionBoutonPioche(e -> {
            //joueMusic(2);
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
                vue.ajouterMessage("Vous êtes en train d'attaquer, vous ne pouvez pas piocher ! \n", false);
            }
            else if(modele.getJoueur1().getMonTour() && !modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage("Vous avez pioché", true);
                if(modele.getJoueur1().mainPleine())
                {
                    vue.ajouterMessage(" mais votre main est pleine ! \n", false);
                }
                else
                {
                    vue.ajouterMessage("\n", true);
                    modele.getJoueur1().piocher();
                    vue.afficherCartesJoueur(modele.getJoueur1().getMain());
                    initialiserBoutonCartes(modele.getJoueur1().getMain());
                }
            }
            else if(modele.getJoueur1().getaPioche())
            {
                vue.ajouterMessage(" Vous avez déjà pioché ! \n", false);
            }
            else
            {
                vue.ajouterMessage(" Ce n'est pas votre tour \n", false);
            }
        }); 

        vue.ajouterActionBoutonSon(e -> {
            stopMusic();
            vue.changerImageSon();
        });

        ArrayList<Carte> main = modele.getJoueur1().getMain();
        vue.afficherCartesJoueur(main);
        initialiserBoutonCartes(main);
        if(partieChargée)
        {
            vue.ajouterMessage("\nLa partie reprends, c'était votre tour ! \n", false);
            modele.getJoueur1().monTour(true);
        }
        else if(modele.getQuiCommence() == 0)
        {
            vue.ajouterMessage("C'est le début d'une nouvelle course ! \n", true);
            vue.ajouterMessage("Les participants sont : \n", true);
            for(int i = 0; i < modele.getJoueurs().size(); i++)
            {
                vue.ajouterMessage("- " + modele.getJoueurs().get(i).getNom() + "\n", true);
            }
            vue.ajouterMessage("Vous commencez à jouer ! \n", true);
            vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
            modele.getJoueur1().monTour(true);
        }
        else if(modele.getQuiCommence() == 1)
        {
            vue.ajouterMessage("C'est le début d'une nouvelle course ! \n", true);
            vue.ajouterMessage("Les participants sont : \n", true);
            for(int i = 0; i < modele.getJoueurs().size(); i++)
            {
                vue.ajouterMessage("- " + modele.getJoueurs().get(i).getNom() + "\n", true);
            }
            vue.ajouterMessage("CPU Agro commence à jouer ! \n", true);
            modele.getJoueur2().actionBot(this);
            modele.getJoueur3().actionBot(this);
            modele.getJoueur1().monTour(true);
            vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
        }
        else if(modele.getQuiCommence() == 2)
        {
            vue.ajouterMessage("C'est le début d'une nouvelle course ! \n", true);
            vue.ajouterMessage("Les participants sont : \n", true);
            for(int i = 0; i < modele.getJoueurs().size(); i++)
            {
                vue.ajouterMessage("- " + modele.getJoueurs().get(i).getNom() + "\n", true);
            }
            vue.ajouterMessage("CPU Fast commence à jouer ! \n", true);
            modele.getJoueur3().actionBot(this);
            modele.getJoueur1().monTour(true);
            vue.ajouterMessage("\nC'est votre tour ! Distance parcourue : " + modele.getJoueur1().getKilometre() + " km \n", true);
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
                            vue.ajouterMessage("Vous devez d'abord piocher pour jouer une carte \n", false);
                        }
                        else if(modele.getJoueur1().getenTraindAttaquer() && modele.getJoueur1().getMain().get(j) == modele.getJoueur1().getEnTraindAttaquerAvec())
                        {
                            vue.ajouterMessage("Vous avez changé d'avis \n", false);
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
                            vue.ajouterMessage("Vous êtes en train d'attaquer, vous ne pouvez pas jouer de cartes ! \n", false);
                        }
                        else if(modele.getJoueur1().getDoitPiocher())
                        {
                            vue.ajouterMessage("Après avoir joué une botte vous devez piocher\n", false);
                        }
                        else if(modele.getJoueur1().getaDefausse())
                        {
                            vue.ajouterMessage("Vous ne pouvez plus jouer après avoir défaussé\n", false);
                        }
                        else if(modele.getJoueur1().getaJjoue() && !(modele.getJoueur1().getMain().get(j) instanceof Botte))
                        {
                            vue.ajouterMessage("Vous avez déjà joué lors de votre tour \n", false);
                        }
                        else if(modele.getJoueur1().getDefausse())
                        {
                            vue.ajouterMessage(modele.getJoueur1().defausse(modele.getJoueur1().getMain().get(j),getControleur()), true);
                            modele.getJoueur1().setaDefausse(true);
                            vue.getDefausse().setText("Défausse (temporaire)");
                            modele.getJoueur1().setDefausse(false);
                        }
                        else
                        {
                            if(modele.getJoueur1().getMain().get(j) instanceof Botte)
                            {
                                modele.getJoueur1().jouerCarte(modele.getJoueur1().getMain().get(j),getControleur(),j+1);
                                vue.ajouterMessage("Vous devez piocher à nouveau ! \n", false);
                                modele.getJoueur1().setDoitPiocher(true);
                            }
                            else if(modele.getJoueur1().getMain().get(j) instanceof Attaque)
                            {
                                vue.ajouterMessage("Choisissez le CPU sur lequel vous voulez lancer votre attaque \n", false);
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
                                    vue.ajouterMessage("Vous ne pouvez pas avancer sans feu vert ! \n", false);
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 4)
                                {
                                    String message = modele.getJoueur1().jouerDistance(modele.getJoueur1().getMain().get(j));
                                    vue.ajouterMessage(message, false);
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 5)
                                {
                                    vue.ajouterMessage("Vous avez déjà un feu vert ! \n", false);
                                }
                                else if(modele.getJoueur1().verificationUtilisateur(modele.getJoueur1().getMain().get(j), modele.getJoueur1(), null) == 6)
                                {
                                    vue.ajouterMessage("Vous ne subissez aucune attaque que cette parade permet de contrer !\n", false);
                                }
                            }
                        }
                    }
                    else
                    {
                        vue.ajouterMessage("Ce n'est pas votre tour ! \n", false);
                    }
                }
            }, i);
        }
        vue.getFenetre().setLayout(null);
		vue.getFenetre().setVisible(true);

        vue.avancerVoiture(modele.getJoueur1().getKilometre(), 0, this);
        vue.avancerVoiture(modele.getJoueur2().getKilometre(), 1, this);
        vue.avancerVoiture(modele.getJoueur3().getKilometre(), 2, this);
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
            System.out.println(modele.getPioche().size());
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

    private void joueMusic(int i){
        listeSon.setFile(i);
        listeSon.play();
    }

    private void joueEnContinueMusic(int i){
        listeSon.setFile(i);
        listeSon.play();
        listeSon.loop();
    }

    public void stopMusic(){
        listeSon.stop();
    }
}