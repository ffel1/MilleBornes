package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ParadeTest {

    private Parade carteFeuVert;
    private Parade carteReparation;
    private Parade carteEssence;
    private Parade carteRoueDeSecours;
    private Parade carteFinDeLimitation;

    @BeforeEach
    public void init() {
        carteFeuVert = new Parade(TypeCarte.FEU_VERT);
        carteReparation = new Parade(TypeCarte.REPARATION);
        carteEssence = new Parade(TypeCarte.ESSENCE);
        carteRoueDeSecours = new Parade(TypeCarte.ROUE_DE_SECOURS);
        carteFinDeLimitation = new Parade(TypeCarte.FIN_LIMITATION_VITESSE);
    }

    @Test
    public void testFeuVert() {
        assertEquals("FEU_VERT", carteFeuVert.getNom());
        assertEquals(TypeCarte.FEU_VERT, carteFeuVert.getType());
        assertEquals(0, carteFeuVert.getKilometre());
        assertNotNull(carteFeuVert.getImage());
    }

    @Test
    public void testReparation() {
        assertEquals("REPARATION", carteReparation.getNom());
        assertEquals(TypeCarte.REPARATION, carteReparation.getType());
        assertEquals(0, carteReparation.getKilometre());
        assertNotNull(carteReparation.getImage());
    }

    @Test
    public void testEssence() {
        assertEquals("ESSENCE", carteEssence.getNom());
        assertEquals(TypeCarte.ESSENCE, carteEssence.getType());
        assertEquals(0, carteEssence.getKilometre());
        assertNotNull(carteEssence.getImage());
    }

    @Test
    public void testRoueDeSecours() {
        assertEquals("ROUE_DE_SECOURS", carteRoueDeSecours.getNom());
        assertEquals(TypeCarte.ROUE_DE_SECOURS, carteRoueDeSecours.getType());
        assertEquals(0, carteRoueDeSecours.getKilometre());
        assertNotNull(carteRoueDeSecours.getImage());
    }

    @Test
    public void testFinDeLimitation() {
        assertEquals("FIN_LIMITATION_VITESSE", carteFinDeLimitation.getNom());
        assertEquals(TypeCarte.FIN_LIMITATION_VITESSE, carteFinDeLimitation.getType());
        assertEquals(0, carteFinDeLimitation.getKilometre());
        assertNotNull(carteFinDeLimitation.getImage());
    }
}