package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes distances.
 */
public class BotTest{

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private Attaque carteAccident;
    private Attaque carteCrevaison;
    private Attaque cartePanne;
    private Attaque carteFeuRouge;
    private Attaque carteLimitation;
    private Parade carteFeuVert;
    private Parade carteReparation;
    private Parade carteEssence;
    private Parade carteRoueDeSecours;
    private Parade carteFinDeLimitation;
    private Botte asDuVolant;
    private Botte camionCiterne;
    private Botte increvable;
    private Botte vehiculePrioritaire;
    private CPUFast botFast;
    private CPUAgro botAgro;
    private CPUFast botTestFAST;
    private CPUAgro botTestAGRO;
    private Utilisateur joueurTest;
    private Partie partie;

    @BeforeEach
    public void init(){
        partie = new Partie();
        partie.nouvellePartie();
        _25KM = new Distance(TypeCarte._25KM, 25);
        _50KM = new Distance(TypeCarte._50KM, 50);
        _75KM = new Distance(TypeCarte._75KM, 75);
        _100KM = new Distance(TypeCarte._100KM, 100);
        _200KM = new Distance(TypeCarte._200KM, 200);
        carteFeuVert = new Parade(TypeCarte.FEU_VERT);
        carteReparation = new Parade(TypeCarte.REPARATION);
        carteEssence = new Parade(TypeCarte.ESSENCE);
        carteRoueDeSecours = new Parade(TypeCarte.ROUE_DE_SECOURS);
        carteFinDeLimitation = new Parade(TypeCarte.FIN_LIMITATION_VITESSE);
        carteAccident = new Attaque(TypeCarte.ACCIDENT);
        carteCrevaison = new Attaque(TypeCarte.CREVAISON);
        cartePanne = new Attaque(TypeCarte.PANNE_D_ESSENCE);
        carteFeuRouge = new Attaque(TypeCarte.FEU_ROUGE);
        carteLimitation = new Attaque(TypeCarte.LIMITATION_DE_VITESSE);
        asDuVolant = new Botte(TypeCarte.AS_DU_VOLANT);
        camionCiterne = new Botte(TypeCarte.CAMION_CITERNE);
        increvable = new Botte(TypeCarte.INCREVABLE);
        vehiculePrioritaire = new Botte(TypeCarte.VEHICULE_PRIORITAIRE);
        botFast = (CPUFast)partie.getJoueur3();
        botAgro = (CPUAgro)partie.getJoueur2();
        //botTestFAST = new CPUFast("fast", 0, 0, null);
        botTestAGRO = new CPUAgro("agro", 200, 2, null);
        joueurTest = new Utilisateur("joueur", 100, 0, null);

    }

    //@Test
    public void choixCarte(){
        // Test choix de la carte
        botTestFAST.ajouterCarte(_25KM);
        botTestFAST.setFeuVert(true);
        assertEquals(_25KM, botTestFAST.choisirCarte());
        
    }

    @Test
    public void choisirCible(){
        // Test choix cible
        Partie partie = new Partie();
        partie.nouvellePartie();
        partie.getJoueur2().setKilometre(200);
        assertEquals(200, partie.getJoueur2().getKilometre());
        assertEquals(partie.getJoueur2(), partie.getJoueur3().getCible(carteAccident));
        
    }

    /*
     * Joue en priorité la distance la plus grande
     */
    @Test
    public void testJouerDistanceFast(){
        for(int i = 0; i < 6; i++){
            botFast.retirerCarte(botFast.getMain().get(0));
        }
        // Test utilisation des cartes distance
        botFast.setFeuVert(true);
        botFast.ajouterCarte(_25KM);
        assertEquals(_25KM, botFast.choisirCarte());
        botFast.ajouterCarte(_50KM);
        assertEquals(_50KM, botFast.choisirCarte());
        botFast.ajouterCarte(_75KM);
        assertEquals(_75KM, botFast.choisirCarte());
        botFast.ajouterCarte(_100KM);
        assertEquals(_100KM, botFast.choisirCarte());
        botFast.ajouterCarte(_200KM);
        assertEquals(_200KM, botFast.choisirCarte());
        assertEquals(5, botFast.getMain().size());
    }

    /*
     * Joue une botte jusqu'à ce qu'il n'en a plus
     */
    @Test
    public void testJouerBotteFastEtAgro(){
        for(int i = 0; i < 6; i++){
            botFast.retirerCarte(botFast.getMain().get(0));
        }

        // Quand il a une botte
        botFast.setFeuVert(true);
        botFast.ajouterCarte(_25KM);
        botFast.ajouterCarte(carteAccident);
        botFast.ajouterCarte(increvable);
        botFast.ajouterCarte(_200KM);
        assertEquals(increvable, botFast.choisirCarte());
        assertEquals(4, botFast.getMain().size());

        // Quand il a plusieurs bottes
        botFast.jouerBotte(increvable);
        assertEquals(3, botFast.getMain().size());
        botFast.ajouterCarte(asDuVolant);
        botFast.ajouterCarte(camionCiterne);
        assertEquals(asDuVolant, botFast.choisirCarte());
        botFast.jouerBotte(asDuVolant);
        assertEquals(camionCiterne, botFast.choisirCarte());
        botFast.jouerBotte(camionCiterne);
        assertEquals(_200KM, botFast.choisirCarte());
    }

    /*
     * Joue en priorité la parade si il a une attaque sur lui
     */
    @Test
    public void testJouerParadeFastEtAgro(){
        // Test utilisation de la carte
        for(int i = 0; i < 6; i++){
            botFast.retirerCarte(botFast.getMain().get(0));
        }

        // Choisi un feu vert
        botFast.ajouterCarte(carteFeuVert);
        botFast.ajouterCarte(_25KM);
        botFast.ajouterCarte(carteCrevaison);
        botFast.ajouterCarte(_200KM);
        assertEquals(carteFeuVert, botFast.choisirCarte());
        assertEquals(4, botFast.getMain().size());
        botFast.jouerParade(carteFeuVert);

        // Choisi une roue de secours
        botAgro.ajouterCarte(carteCrevaison);
        botAgro.jouerAttaque(carteCrevaison, botFast);
        assertEquals(2, botFast.getAttaquesEnCours().size());
        botFast.ajouterCarte(carteRoueDeSecours);
        assertEquals(carteRoueDeSecours, botFast.choisirCarte());
        botFast.jouerParade(carteRoueDeSecours);
        assertEquals(1, botFast.getAttaquesEnCours().size());

        // Choisi réparation
        botAgro.ajouterCarte(carteAccident);
        botAgro.jouerAttaque(carteAccident, botFast);
        assertEquals(2, botFast.getAttaquesEnCours().size());
        botFast.ajouterCarte(carteReparation);
        assertEquals(carteReparation, botFast.choisirCarte());
        botFast.jouerParade(carteReparation);
        assertEquals(1, botFast.getAttaquesEnCours().size());

        // Choisi fin limitation
        botAgro.ajouterCarte(carteLimitation);
        botAgro.jouerAttaque(carteLimitation, botFast);
        assertEquals(2, botFast.getAttaquesEnCours().size());
        botFast.ajouterCarte(carteFinDeLimitation);
        assertEquals(carteFinDeLimitation, botFast.choisirCarte());
        botFast.jouerParade(carteFinDeLimitation);
        assertEquals(1, botFast.getAttaquesEnCours().size());

        // Choisi fin limitation
        botAgro.ajouterCarte(cartePanne);
        botAgro.jouerAttaque(cartePanne, botFast);
        assertEquals(2, botFast.getAttaquesEnCours().size());
        botFast.ajouterCarte(carteEssence);
        assertEquals(carteEssence, botFast.choisirCarte());
        botFast.jouerParade(carteEssence);
        assertEquals(1, botFast.getAttaquesEnCours().size());
    }

    /*
     * Joue en priorité une attaque si il ne peut rien faire d'autre
     */
    @Test
    public void testJouerAttaqueFast(){
        // Test utilisation de la carte
        for(int i = 0; i < 6; i++){
            botFast.retirerCarte(botFast.getMain().get(0));
        }

        // Choisi une attaque accident
        botFast.ajouterCarte(_75KM);
        botFast.ajouterCarte(_25KM);
        botFast.ajouterCarte(carteFinDeLimitation);
        botFast.ajouterCarte(_200KM);
        botFast.ajouterCarte(carteAccident);
        assertEquals(carteAccident, botFast.choisirCarte());
        assertEquals(5, botFast.getMain().size());
        botFast.jouerAttaque(carteAccident, botAgro);

        // Choisi une attaque crevaison
        botFast.ajouterCarte(carteCrevaison);
        assertEquals(carteCrevaison, botFast.choisirCarte());
        assertEquals(5, botFast.getMain().size());
        botFast.jouerAttaque(carteCrevaison, botAgro);

        // Choisi une attaque limitation
        botFast.ajouterCarte(carteLimitation);
        assertEquals(carteLimitation, botFast.choisirCarte());
        assertEquals(5, botFast.getMain().size());
        botFast.jouerAttaque(carteLimitation, botAgro);

        // Choisi une attaque quand il en a plusieurs (feu rouge et panne)
        botFast.ajouterCarte(cartePanne);
        botFast.ajouterCarte(carteFeuRouge);
        assertEquals(cartePanne, botFast.choisirCarte());
        assertEquals(6, botFast.getMain().size());
        botFast.jouerAttaque(cartePanne, botAgro);
        assertEquals(null, botFast.choisirCarte()); // Car les autres joueurs ont un feu rouge
        botAgro.setFeuVert(true);
        assertEquals(carteFeuRouge, botFast.choisirCarte());
        assertEquals(5, botFast.getMain().size());
        botFast.jouerAttaque(carteFeuRouge, botAgro);
    }

     /*
     * Joue en priorité une attaque même si il peut avancer
     */
    @Test
    public void testJouerAttaqueAgro(){
        // Test utilisation de la carte
        for(int i = 0; i < 6; i++){
            botAgro.retirerCarte(botAgro.getMain().get(0));
        }

        // Choisi une attaque accident même si il peut avancer
        botAgro.setFeuVert(true); 
        botAgro.ajouterCarte(_25KM);
        botAgro.ajouterCarte(_75KM);
        botAgro.ajouterCarte(carteFinDeLimitation);
        botAgro.ajouterCarte(_200KM);
        botAgro.ajouterCarte(carteAccident);
        assertEquals(carteAccident, botAgro.choisirCarte());
        assertEquals(5, botAgro.getMain().size());
        botAgro.jouerAttaque(carteAccident, botFast);

        // Choisi une attaque crevaison même si il peut jouer une parade
        botAgro.ajouterCarte(carteCrevaison);
        botAgro.ajouterCarte(carteFeuVert);
        botAgro.setFeuVert(false);
        assertEquals(carteCrevaison, botAgro.choisirCarte());
        assertEquals(6, botAgro.getMain().size());
        botAgro.jouerAttaque(carteCrevaison, botFast);
        botAgro.jouerParade(carteFeuVert);
        botAgro.setFeuVert(false);

        // Choisi une attaque limitation
        botAgro.ajouterCarte(carteLimitation);
        assertEquals(carteLimitation, botAgro.choisirCarte());
        assertEquals(5, botAgro.getMain().size());
        botAgro.jouerAttaque(carteLimitation, botFast);

        // Choisi une attaque quand il en a plusieurs (feu rouge et panne)
        botAgro.ajouterCarte(cartePanne);
        botAgro.ajouterCarte(carteFeuRouge);
        assertEquals(cartePanne, botAgro.choisirCarte());
        assertEquals(6, botAgro.getMain().size());
        botAgro.jouerAttaque(cartePanne, botFast);
        assertEquals(null, botAgro.choisirCarte()); // Car les autres joueurs ont un feu rouge
        botFast.setFeuVert(true);
        assertEquals(carteFeuRouge, botAgro.choisirCarte());
        assertEquals(5, botAgro.getMain().size());
        botAgro.jouerAttaque(carteFeuRouge, botFast);
    }
}