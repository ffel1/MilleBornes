package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/**
 * Unit test class for "Attack" type cards in the game.
 * Each test verifies the basic properties of the cards and their behavior when played.
 */
public class AttackTest {

    private Attack cardA;
    private Attack cardC;
    private Attack cardP;
    private Attack cardF;
    private Attack cardL;
    private User source;
    private User target;

    /**
     * Initializes objects before each test.
     * Creates attack cards and users.
     */
    @BeforeEach
    public void ini() {
        cardA = new Attack(TypeCard.ACCIDENT);
        cardC = new Attack(TypeCard.FLAT_TIRE);
        cardP = new Attack(TypeCard.OUT_OF_FUEL);
        cardF = new Attack(TypeCard.RED_LIGHT);
        cardL = new Attack(TypeCard.SPEED_LIMITATION);
        source = new User("Source", 0, 0, null);
        target = new User("Target", 0, 0, null);
    }

    /**
     * Tests the properties and behavior of the "Accident" card.
     */
    @Test
    public void testAccident() {
        // Verify card properties
        assertEquals("Accident", cardA.getName());
        assertEquals(TypeCard.ACCIDENT, cardA.getType());
        assertEquals(0, cardA.getKilometers());
        assertNotNull(cardA.getImage());

        // Verify card usage
        source.addCard(cardA);
        assertEquals(source.check(cardA, source, target), true); // The card can be played
        source.playAttack(cardA, target);
        assertEquals(source.getHand().size(), 0); // The card is removed from the player's hand
        assertEquals(target.getCurrentAttacks().get(0), cardA); // The card is added to the target's current attacks
    }

    /**
     * Tests the properties and behavior of the "Flat Tire" card.
     */
    @Test
    public void testFLAT_TIRE() {
        // Verify card properties
        assertEquals("Crevaison", cardC.getName());
        assertEquals(TypeCard.FLAT_TIRE, cardC.getType());
        assertEquals(0, cardC.getKilometers());
        assertNotNull(cardC.getImage());

        // Verify card usage
        source.addCard(cardC);
        assertEquals(source.check(cardC, source, target), true); // The card can be played
        source.playAttack(cardC, target);
        assertEquals(source.getHand().size(), 0); // The card is removed from the player's hand
        assertEquals(target.getCurrentAttacks().get(0), cardC); // The card is added to the target's current attacks
    }

    /**
     * Tests the properties and behavior of the "Out of Fuel" card.
     */
    @Test
    public void testPanne() {
        // Verify card properties
        assertEquals("Panne d'essence", cardP.getName());
        assertEquals(TypeCard.OUT_OF_FUEL, cardP.getType());
        assertEquals(0, cardP.getKilometers());
        assertNotNull(cardP.getImage());

        // Verify card usage
        source.addCard(cardP);
        assertEquals(source.check(cardP, source, target), true); // The card can be played
        source.playAttack(cardP, target);
        assertEquals(source.getHand().size(), 0); // The card is removed from the player's hand
        assertEquals(target.getCurrentAttacks().get(0), cardP); // The card is added to the target's current attacks
    }

    /**
     * Tests the properties and behavior of the "Speed Limitation" card.
     */
    @Test
    public void testLimitation() {
        // Verify card properties
        assertEquals("Limite de vitesse", cardL.getName());
        assertEquals(TypeCard.SPEED_LIMITATION, cardL.getType());
        assertEquals(0, cardL.getKilometers());
        assertNotNull(cardL.getImage());

        // Verify card usage
        source.addCard(cardL);
        assertEquals(source.check(cardL, source, target), true); // The card can be played
        source.playAttack(cardL, target);
        assertEquals(source.getHand().size(), 0); // The card is removed from the player's hand
        assertEquals(target.getCurrentAttacks().get(0), cardL); // The card is added to the target's current attacks
    }

    /**
     * Tests the properties and behavior of the "Red Light" card.
     */
    @Test
    public void testFeu() {
        // Verify card properties
        assertEquals("Feu rouge", cardF.getName());
        assertEquals(TypeCard.RED_LIGHT, cardF.getType());
        assertEquals(0, cardF.getKilometers());
        assertNotNull(cardF.getImage());

        // Verify card usage
        source.addCard(cardF);
        assertEquals(source.check(cardF, source, target), false); // The card cannot be played because the target starts with a red light
        target.setGreenLight(true); // The target switches to green light
        assertEquals(source.check(cardF, source, target), true); // The card can now be played
        source.playAttack(cardF, target);
        assertEquals(source.getHand().size(), 0); // The card is removed from the player's hand
        assertEquals(target.getCurrentAttacks().get(0), cardF); // The card is added to the target's current attacks
    }
}