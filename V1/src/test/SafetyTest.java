package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/**
 * Unit test class for "Safety" type cards in the game.
 * Each test verifies the basic properties of the cards and their behavior when played.
 */
public class SafetyTest {

    private Safety cardGreenLight;
    private Safety cardRepair;
    private Safety cardFuel;
    private Safety cardSpareWheel;
    private Safety cardEndSpeedLimitation;
    private Attack cardAccident;
    private Attack cardFlatTire;
    private Attack cardOutOfFuel;
    private Attack cardRedLight;
    private Attack cardSpeedLimitation;
    private User source;
    private User target;

    /**
     * Initializes objects before each test.
     * Creates safety and attack cards, and user instances.
     */
    @BeforeEach
    public void init() {
        cardGreenLight = new Safety(TypeCard.GREEN_LIGHT);
        cardRepair = new Safety(TypeCard.REPAIR);
        cardFuel = new Safety(TypeCard.FUEL);
        cardSpareWheel = new Safety(TypeCard.SPARE_WHEEL);
        cardEndSpeedLimitation = new Safety(TypeCard.END_SPEED_LIMITATION);
        source = new User("Source", 0, 0, null);
        target = new User("Target", 0, 0, null);
        cardAccident = new Attack(TypeCard.ACCIDENT);
        cardFlatTire = new Attack(TypeCard.FLAT_TIRE);
        cardOutOfFuel = new Attack(TypeCard.OUT_OF_FUEL);
        cardRedLight = new Attack(TypeCard.RED_LIGHT);
        cardSpeedLimitation = new Attack(TypeCard.SPEED_LIMITATION);
    }

    /**
     * Tests the properties and behavior of the "Green Light" safety card.
     */
    @Test
    public void testGreenLight() {
        // Verify card properties
        assertEquals("Feu vert", cardGreenLight.getName());
        assertEquals(TypeCard.GREEN_LIGHT, cardGreenLight.getType());
        assertEquals(0, cardGreenLight.getKilometers());
        assertNotNull(cardGreenLight.getImage());

        // Verify card usage
        source.addCard(cardRedLight);
        target.setGreenLight(false); // Start with a red light
        target.addCard(cardGreenLight);
        source.playAttack(cardRedLight, target);
        target.playSafety(cardGreenLight);
        assertEquals(target.getHand().size(), 0); // Card played, hand size reduced
        assertEquals(target.getCurrentAttacks().size(), 1); // A red light
        assertTrue(target.getgreenLight()); // Green light is set
    }

    /**
     * Tests the properties and behavior of the "Repair" safety card.
     */
    @Test
    public void testRepair() {
        // Verify card properties
        assertEquals("Réparation", cardRepair.getName());
        assertEquals(TypeCard.REPAIR, cardRepair.getType());
        assertEquals(0, cardRepair.getKilometers());
        assertNotNull(cardRepair.getImage());

        // Verify card usage
        source.addCard(cardAccident);
        target.addCard(cardRepair);
        source.playAttack(cardAccident, target);
        target.playSafety(cardRepair);
        assertEquals(target.getHand().size(), 0); // Card played, hand size reduced
        assertEquals(target.getCurrentAttacks().size(), 0); // No active attacks left
    }

    /**
     * Tests the properties and behavior of the "Fuel" safety card.
     */
    @Test
    public void testFuel() {
        // Verify card properties
        assertEquals("Essence", cardFuel.getName());
        assertEquals(TypeCard.FUEL, cardFuel.getType());
        assertEquals(0, cardFuel.getKilometers());
        assertNotNull(cardFuel.getImage());

        // Verify card usage
        source.addCard(cardOutOfFuel);
        target.addCard(cardFuel);
        source.playAttack(cardOutOfFuel, target);
        target.playSafety(cardFuel);
        assertEquals(target.getHand().size(), 0); // Card played, hand size reduced
        assertEquals(target.getCurrentAttacks().size(), 0); // No active attacks left
    }

    /**
     * Tests the properties and behavior of the "Spare Wheel" safety card.
     */
    @Test
    public void testSpareWheel() {
        // Verify card properties
        assertEquals("Roue de secours", cardSpareWheel.getName());
        assertEquals(TypeCard.SPARE_WHEEL, cardSpareWheel.getType());
        assertEquals(0, cardSpareWheel.getKilometers());
        assertNotNull(cardSpareWheel.getImage());

        // Verify card usage
        source.addCard(cardFlatTire);
        target.addCard(cardSpareWheel);
        source.playAttack(cardFlatTire, target);
        target.playSafety(cardSpareWheel);
        assertEquals(target.getHand().size(), 0); // Card played, hand size reduced
        assertEquals(target.getCurrentAttacks().size(), 0); // No active attacks left
    }

    /**
     * Tests the properties and behavior of the "End Speed Limitation" safety card.
     */
    @Test
    public void testEndSpeedLimitation() {
        // Verify card properties
        assertEquals("Fin de limitation de vitesse", cardEndSpeedLimitation.getName());
        assertEquals(TypeCard.END_SPEED_LIMITATION, cardEndSpeedLimitation.getType());
        assertEquals(0, cardEndSpeedLimitation.getKilometers());
        assertNotNull(cardEndSpeedLimitation.getImage());

        // Verify card usage
        source.addCard(cardSpeedLimitation);
        target.addCard(cardEndSpeedLimitation);
        source.playAttack(cardSpeedLimitation, target);
        target.playSafety(cardEndSpeedLimitation);
        assertEquals(target.getHand().size(), 0); // Card played, hand size reduced
        assertEquals(target.getCurrentAttacks().size(), 0); // No active attacks left
    }
}