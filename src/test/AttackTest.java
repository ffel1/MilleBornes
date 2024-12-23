package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Tests sur les cards attaques.
 */
public class AttackTest{

    private Attack cardA;
    private Attack cardC;
    private Attack cardP;
    private Attack cardF;
    private Attack cardL;
    private User source;
    private User target;

    @BeforeEach
	public void ini(){
        cardA = new Attack(TypeCard.ACCIDENT);
        cardC = new Attack(TypeCard.FLAT_TIRE);
        cardP = new Attack(TypeCard.OUT_OF_FUEL);
        cardF = new Attack(TypeCard.RED_LIGHT);
        cardL = new Attack(TypeCard.SPEED_LIMITATION);
        source = new User("Source", 0, 0, null);
        target = new User("target", 0, 0, null);
    }

    @Test
    public void testAccident(){
        // Test de la card
        assertEquals("ACCIDENT", cardA.getName());
        assertEquals(TypeCard.ACCIDENT, cardA.getType());
        assertEquals(0, cardA.getKilometers());
        assertNotNull(cardA.getImage());

        // Test utilisation de la card
        source.addCard(cardA);
        assertEquals(source.check(cardA, source, target), true);
        source.playAttack(cardA, target);
        assertEquals(source.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().get(0), cardA);
    }

    @Test
    public void testFLAT_TIRE(){
        // Test de la card
        assertEquals("FLAT_TIRE", cardC.getName());
        assertEquals(TypeCard.FLAT_TIRE, cardC.getType());
        assertEquals(0, cardC.getKilometers());
        assertNotNull(cardC.getImage());

        // Test utilisation de la card
        source.addCard(cardC);
        assertEquals(source.check(cardC, source, target), true);
        source.playAttack(cardC, target);
        assertEquals(source.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().get(0), cardC);
    }

    @Test
    public void testPanne(){
        // Test de la card
        assertEquals("OUT_OF_FUEL", cardP.getName());
        assertEquals(TypeCard.OUT_OF_FUEL, cardP.getType());
        assertEquals(0, cardP.getKilometers());
        assertNotNull(cardP.getImage());

        // Test utilisation de la card
        source.addCard(cardP);
        assertEquals(source.check(cardP, source, target), true);
        source.playAttack(cardP, target);
        assertEquals(source.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().get(0), cardP);
    }

    @Test
    public void testLimitation(){
        // Test de la card
        assertEquals("LIMITE_DE_VITESSE", cardL.getName());
        assertEquals(TypeCard.SPEED_LIMITATION, cardL.getType());
        assertEquals(0, cardL.getKilometers());
        assertNotNull(cardL.getImage());

        // Test utilisation de la card
        source.addCard(cardL);
        assertEquals(source.check(cardL, source, target), true);
        source.playAttack(cardL, target);
        assertEquals(source.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().get(0), cardL);
    }

    @Test
    public void testFeu(){
        // Test de la card
        assertEquals("RED_LIGHT", cardF.getName());
        assertEquals(TypeCard.RED_LIGHT, cardF.getType());
        assertEquals(0, cardF.getKilometers());
        assertNotNull(cardF.getImage());

        // Test utilisation de la card
        source.addCard(cardF);
        assertEquals(source.check(cardF, source, target), false); // Car on commence avec un feu rouge
        target.setGreenLight(true);
        assertEquals(source.check(cardF, source, target), true);
        source.playAttack(cardF, target);
        assertEquals(source.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().get(0), cardF);
    }
}