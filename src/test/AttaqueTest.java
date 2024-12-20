package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes attaques.
 */
public class AttaqueTest{

    private Attaque carteA;
    private Attaque carteC;
    private Attaque carteP;
    private Attaque carteF;
    private Attaque carteL;
    private Utilisateur source;
    private Utilisateur cible;

    @BeforeEach
	public void ini(){
        carteA = new Attaque(TypeCarte.ACCIDENT);
        carteC = new Attaque(TypeCarte.CREVAISON);
        carteP = new Attaque(TypeCarte.PANNE_D_ESSENCE);
        carteF = new Attaque(TypeCarte.FEU_ROUGE);
        carteL = new Attaque(TypeCarte.LIMITATION_DE_VITESSE);
        source = new Utilisateur("Source", 0, 0, null);
        cible = new Utilisateur("Cible", 0, 0, null);
    }

    @Test
    public void testAccident(){
        // Test de la carte
        assertEquals("ACCIDENT", carteA.getNom());
        assertEquals(TypeCarte.ACCIDENT, carteA.getType());
        assertEquals(0, carteA.getKilometre());
        assertNotNull(carteA.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteA);
        assertEquals(source.verification(carteA, source, cible), true);
        source.jouerAttaque(carteA, cible);
        assertEquals(source.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().get(0), carteA);
    }

    @Test
    public void testCrevaison(){
        // Test de la carte
        assertEquals("CREVAISON", carteC.getNom());
        assertEquals(TypeCarte.CREVAISON, carteC.getType());
        assertEquals(0, carteC.getKilometre());
        assertNotNull(carteC.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteC);
        assertEquals(source.verification(carteC, source, cible), true);
        source.jouerAttaque(carteC, cible);
        assertEquals(source.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().get(0), carteC);
    }

    @Test
    public void testPanne(){
        // Test de la carte
        assertEquals("PANNE_D_ESSENCE", carteP.getNom());
        assertEquals(TypeCarte.PANNE_D_ESSENCE, carteP.getType());
        assertEquals(0, carteP.getKilometre());
        assertNotNull(carteP.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteP);
        assertEquals(source.verification(carteP, source, cible), true);
        source.jouerAttaque(carteP, cible);
        assertEquals(source.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().get(0), carteP);
    }

    @Test
    public void testLimitation(){
        // Test de la carte
        assertEquals("LIMITE_DE_VITESSE", carteL.getNom());
        assertEquals(TypeCarte.LIMITATION_DE_VITESSE, carteL.getType());
        assertEquals(0, carteL.getKilometre());
        assertNotNull(carteL.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteL);
        assertEquals(source.verification(carteL, source, cible), true);
        source.jouerAttaque(carteL, cible);
        assertEquals(source.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().get(0), carteL);
    }

    @Test
    public void testFeu(){
        // Test de la carte
        assertEquals("FEU_ROUGE", carteF.getNom());
        assertEquals(TypeCarte.FEU_ROUGE, carteF.getType());
        assertEquals(0, carteF.getKilometre());
        assertNotNull(carteF.getImage());

        // Test utilisation de la carte
        source.ajouterCarte(carteF);
        assertEquals(source.verification(carteF, source, cible), false); // Car on commence avec un feu rouge
        cible.setFeuVert(true);
        assertEquals(source.verification(carteF, source, cible), true);
        source.jouerAttaque(carteF, cible);
        assertEquals(source.getMain().size(), 0);
        assertEquals(cible.getAttaquesEnCours().get(0), carteF);
    }
}