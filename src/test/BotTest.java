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
    private CPUFast botTestFAST;
    private CPUAgro botTestAGRO;
    private Utilisateur joueurTest;

    @BeforeEach
    public void init(){
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
        botFast = new CPUFast("Source", 0, 0, null);
        botTestFAST = new CPUFast("fast", 0, 0, null);
        botTestAGRO = new CPUAgro("agro", 200, 0, null);
        joueurTest = new Utilisateur("joueur", 100, 0, null);

    }

    @Test
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
    public void testJouerDistance(){
        // Test utilisation de la carte
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
    public void testJouerBotte(){
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

}