package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttaqueTest{

    private Attaque carteA;
    private Attaque carteC;
    private Attaque carteP;
    private Attaque carteF;
    private Attaque carteL;

    @BeforeEach
	public void ini(){
        carteA = new Attaque(TypeCarte.ACCIDENT);
        carteC = new Attaque(TypeCarte.CREVAISON);
        carteP = new Attaque(TypeCarte.PANNE_D_ESSENCE);
        carteF = new Attaque(TypeCarte.FEU_ROUGE);
        carteL = new Attaque(TypeCarte.LIMITATION_DE_VITESSE);
    }

    @Test
    public void testAccident(){
        assertEquals("ACCIDENT", carteA.getNom());
        assertEquals(TypeCarte.ACCIDENT, carteA.getType());
        assertEquals(0, carteA.getKilometre());
        assertNotNull(carteA.getImage());
    }

    @Test
    public void testCrevaison(){
        assertEquals("CREVAISON", carteC.getNom());
        assertEquals(TypeCarte.CREVAISON, carteC.getType());
        assertEquals(0, carteC.getKilometre());
        assertNotNull(carteC.getImage());
    }

    @Test
    public void testPanne(){
        assertEquals("PANNE_D_ESSENCE", carteP.getNom());
        assertEquals(TypeCarte.PANNE_D_ESSENCE, carteP.getType());
        assertEquals(0, carteP.getKilometre());
        assertNotNull(carteP.getImage());
    }

    @Test
    public void testLimitation(){
        assertEquals("LIMITE_DE_VITESSE", carteL.getNom());
        assertEquals(TypeCarte.LIMITATION_DE_VITESSE, carteL.getType());
        assertEquals(0, carteL.getKilometre());
        assertNotNull(carteL.getImage());
    }

    @Test
    public void testFeu(){
        assertEquals("FEU_ROUGE", carteF.getNom());
        assertEquals(TypeCarte.FEU_ROUGE, carteF.getType());
        assertEquals(0, carteF.getKilometre());
        assertNotNull(carteF.getImage());
    }
}