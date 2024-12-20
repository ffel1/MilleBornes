package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes parades.
 */
public class ParadeTest{

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
    }

    @Test
    public void testFeuVert(){
        // Test de la carte
        assertEquals("FEU_VERT", carteFeuVert.getNom());
        assertEquals(TypeCarte.FEU_VERT, carteFeuVert.getType());
        assertEquals(0, carteFeuVert.getKilometre());
        assertNotNull(carteFeuVert.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteFeuRouge);
        cible.setFeuVert(true); // Car on commence avec un feu rouge
        cible.ajouterCarte(carteFeuVert);
        source.jouerAttaque(carteFeuRouge, cible);
        cible.jouerParade(carteFeuVert);
        assertEquals(cible.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().size(), 0);
        assertEquals(cible.getFeuVert(), true);
    }

    @Test
    public void testReparation(){
        // Test de la carte
        assertEquals("REPARATION", carteReparation.getNom());
        assertEquals(TypeCarte.REPARATION, carteReparation.getType());
        assertEquals(0, carteReparation.getKilometre());
        assertNotNull(carteReparation.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteAccident);
        cible.ajouterCarte(carteReparation);
        source.jouerAttaque(carteAccident, cible);
        cible.jouerParade(carteReparation);
        assertEquals(cible.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testEssence(){
        // Test de la carte
        assertEquals("ESSENCE", carteEssence.getNom());
        assertEquals(TypeCarte.ESSENCE, carteEssence.getType());
        assertEquals(0, carteEssence.getKilometre());
        assertNotNull(carteEssence.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(cartePanne);
        cible.ajouterCarte(carteEssence);
        source.jouerAttaque(cartePanne, cible);
        cible.jouerParade(carteEssence);
        assertEquals(cible.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testRoueDeSecours(){
        // Test de la carte
        assertEquals("ROUE_DE_SECOURS", carteRoueDeSecours.getNom());
        assertEquals(TypeCarte.ROUE_DE_SECOURS, carteRoueDeSecours.getType());
        assertEquals(0, carteRoueDeSecours.getKilometre());
        assertNotNull(carteRoueDeSecours.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteCrevaison);
        cible.ajouterCarte(carteRoueDeSecours);
        source.jouerAttaque(carteCrevaison, cible);
        cible.jouerParade(carteRoueDeSecours);
        assertEquals(cible.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().size(), 0);
    }

    @Test
    public void testFinDeLimitation(){
        // Test de la carte
        assertEquals("FIN_LIMITATION_VITESSE", carteFinDeLimitation.getNom());
        assertEquals(TypeCarte.FIN_LIMITATION_VITESSE, carteFinDeLimitation.getType());
        assertEquals(0, carteFinDeLimitation.getKilometre());
        assertNotNull(carteFinDeLimitation.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteLimitation);
        cible.ajouterCarte(carteFinDeLimitation);
        source.jouerAttaque(carteLimitation, cible);
        cible.jouerParade(carteFinDeLimitation);
        assertEquals(cible.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().size(), 0);
    }
}