package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes parades.
 */
public class BotteTest{

    private Parade carteFeuVert;
    private Parade carteReparation;
    private Parade carteEssence;
    private Parade carteRoueDeSecours;
    private Parade carteFinDeLimitation;
    private Attaque carteAccident;
    private Attaque carteCrevaison;
    private Attaque cartePanne;
    private Attaque carteFeuRouge;
    private Attaque carteLimitation;
    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private Botte asDuVolant;
    private Botte camionCiterne;
    private Botte increvable;
    private Botte vehiculePrioritaire;
    private Utilisateur source;
    private Utilisateur cible;

    @BeforeEach
    public void init(){
        carteFeuVert = new Parade(TypeCarte.FEU_VERT);
        carteReparation = new Parade(TypeCarte.REPARATION);
        carteEssence = new Parade(TypeCarte.ESSENCE);
        carteRoueDeSecours = new Parade(TypeCarte.ROUE_DE_SECOURS);
        carteFinDeLimitation = new Parade(TypeCarte.FIN_LIMITATION_VITESSE);
        source = new Utilisateur("Source", 0, 0, null);
        cible = new Utilisateur("Cible", 0, 0, null);
        carteAccident = new Attaque(TypeCarte.ACCIDENT);
        carteCrevaison = new Attaque(TypeCarte.CREVAISON);
        cartePanne = new Attaque(TypeCarte.PANNE_D_ESSENCE);
        carteFeuRouge = new Attaque(TypeCarte.FEU_ROUGE);
        carteLimitation = new Attaque(TypeCarte.LIMITATION_DE_VITESSE);
        _25KM = new Distance(TypeCarte._25KM, 25);
        _50KM = new Distance(TypeCarte._50KM, 50);
        _75KM = new Distance(TypeCarte._75KM, 75);
        _100KM = new Distance(TypeCarte._100KM, 100);
        _200KM = new Distance(TypeCarte._200KM, 200);
        asDuVolant = new Botte(TypeCarte.AS_DU_VOLANT);
        camionCiterne = new Botte(TypeCarte.CAMION_CITERNE);
        increvable = new Botte(TypeCarte.INCREVABLE);
        vehiculePrioritaire = new Botte(TypeCarte.VEHICULE_PRIORITAIRE);
    }

    @Test
    public void testAsDuVolant(){
        // Test de la carte
        assertEquals("AS_DU_VOLANT", asDuVolant.getNom());
        assertEquals(TypeCarte.AS_DU_VOLANT, asDuVolant.getType());
        assertEquals(0, asDuVolant.getKilometre());
        assertNotNull(asDuVolant.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(asDuVolant);
        source.jouerBotte(asDuVolant);
        cible.ajouterCarte(carteAccident);
        cible.jouerAttaque(carteAccident, source);
        assertEquals(source.getBottesPosées().size(), 1);
        assertEquals(source.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testCamionCiterne(){
        // Test de la carte
        assertEquals("CAMION_CITERNE", camionCiterne.getNom());
        assertEquals(TypeCarte.CAMION_CITERNE, camionCiterne.getType());
        assertEquals(0, camionCiterne.getKilometre());
        assertNotNull(camionCiterne.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(camionCiterne);
        source.jouerBotte(camionCiterne);
        cible.ajouterCarte(cartePanne);
        cible.jouerAttaque(cartePanne, source);
        assertEquals(source.getBottesPosées().size(), 1);
        assertEquals(source.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testIncrevable(){
        // Test de la carte
        assertEquals("INCREVABLE", increvable.getNom());
        assertEquals(TypeCarte.INCREVABLE, increvable.getType());
        assertEquals(0, increvable.getKilometre());
        assertNotNull(increvable.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(increvable);
        source.jouerBotte(increvable);
        cible.ajouterCarte(carteCrevaison);
        cible.jouerAttaque(carteCrevaison, source);
        assertEquals(source.getBottesPosées().size(), 1);
        assertEquals(source.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testVehiculePrioritaire(){
        // Test de la carte
        assertEquals("VEHICULE_PRIORITAIRE", vehiculePrioritaire.getNom());
        assertEquals(TypeCarte.VEHICULE_PRIORITAIRE, vehiculePrioritaire.getType());
        assertEquals(0, vehiculePrioritaire.getKilometre());
        assertNotNull(vehiculePrioritaire.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(vehiculePrioritaire);
        source.jouerBotte(vehiculePrioritaire);
        cible.ajouterCarte(carteFeuRouge);
        cible.jouerAttaque(carteFeuRouge, source);
        assertEquals(source.getBottesPosées().size(), 1);
        assertEquals(source.getAttaquesEnCours().size(), 0);
        cible.ajouterCarte(carteLimitation);
        cible.jouerAttaque(carteLimitation, source);
        assertEquals(source.getBottesPosées().size(), 1);
        assertEquals(source.getAttaquesEnCours().size(), 0);
    }
}