package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/**
 * Unit test class for "Boot" type cards in the game.
 * Each test verifies the basic properties of the cards and their behavior when played.
 */
public class BootTest {

    private Attack cardAccident; 
    private Attack cardFLAT_TIRE;
    private Attack cardPanne;
    private Attack cardFeuRouge;
    private Attack cardLimitation;
    private Boot asDuVolant;
    private Boot camionCiterne;
    private Boot PUNCTURE_PROOF;
    private Boot vehiculePrioritaire;
    private User source;
    private User target;

    /**
     * Initializes objects before each test.
     * Creates attack and boot cards, and users.
     */
    @BeforeEach
    public void init() {
        source = new User("Source", 0, 0, null);
        target = new User("Target", 0, 0, null);
        cardAccident = new Attack(TypeCard.ACCIDENT);
        cardFLAT_TIRE = new Attack(TypeCard.FLAT_TIRE);
        cardPanne = new Attack(TypeCard.OUT_OF_FUEL);
        cardFeuRouge = new Attack(TypeCard.RED_LIGHT);
        cardLimitation = new Attack(TypeCard.SPEED_LIMITATION);
        asDuVolant = new Boot(TypeCard.EXPERT_DRIVER);
        camionCiterne = new Boot(TypeCard.TANK_TRUCK);
        PUNCTURE_PROOF = new Boot(TypeCard.PUNCTURE_PROOF);
        vehiculePrioritaire = new Boot(TypeCard.PRIOTIRY_VEHICLE);
    }

    /**
     * Tests the properties and behavior of the "Expert Driver" boot card.
     */
    @Test
    public void testAsDuVolant() {
        // Verify card properties
        assertEquals("As du volant", asDuVolant.getName());
        assertEquals(TypeCard.EXPERT_DRIVER, asDuVolant.getType());
        assertEquals(0, asDuVolant.getKilometers());
        assertNotNull(asDuVolant.getImage());

        // Verify card usage
        source.addCard(asDuVolant);
        source.playBoot(asDuVolant);
        target.addCard(cardAccident);
        target.playAttack(cardAccident, source);
        assertEquals(source.getPlayedBoots().size(), 1); // Boot card is active
        assertEquals(source.getCurrentAttacks().size(), 0); // Attack is neutralized
    }

    /**
     * Tests the properties and behavior of the "Tank Truck" boot card.
     */
    @Test
    public void testCamionCiterne() {
        // Verify card properties
        assertEquals("Camion citerne", camionCiterne.getName());
        assertEquals(TypeCard.TANK_TRUCK, camionCiterne.getType());
        assertEquals(0, camionCiterne.getKilometers());
        assertNotNull(camionCiterne.getImage());

        // Verify card usage
        source.addCard(camionCiterne);
        source.playBoot(camionCiterne);
        target.addCard(cardPanne);
        target.playAttack(cardPanne, source);
        assertEquals(source.getPlayedBoots().size(), 1); // Boot card is active
        assertEquals(source.getCurrentAttacks().size(), 0); // Attack is neutralized
    }

    /**
     * Tests the properties and behavior of the "Puncture Proof" boot card.
     */
    @Test
    public void testPUNCTURE_PROOF() {
        // Verify card properties
        assertEquals("Increvable", PUNCTURE_PROOF.getName());
        assertEquals(TypeCard.PUNCTURE_PROOF, PUNCTURE_PROOF.getType());
        assertEquals(0, PUNCTURE_PROOF.getKilometers());
        assertNotNull(PUNCTURE_PROOF.getImage());

        // Verify card usage
        source.addCard(PUNCTURE_PROOF);
        source.playBoot(PUNCTURE_PROOF);
        target.addCard(cardFLAT_TIRE);
        target.playAttack(cardFLAT_TIRE, source);
        assertEquals(source.getPlayedBoots().size(), 1); // Boot card is active
        assertEquals(source.getCurrentAttacks().size(), 0); // Attack is neutralized
    }

    /**
     * Tests the properties and behavior of the "Priority Vehicle" boot card.
     */
    @Test
    public void testVehiculePrioritaire() {
        // Verify card properties
        assertEquals("Véhicule prioritaire", vehiculePrioritaire.getName());
        assertEquals(TypeCard.PRIOTIRY_VEHICLE, vehiculePrioritaire.getType());
        assertEquals(0, vehiculePrioritaire.getKilometers());
        assertNotNull(vehiculePrioritaire.getImage());

        // Verify card usage
        source.addCard(vehiculePrioritaire);
        source.playBoot(vehiculePrioritaire);
        target.addCard(cardFeuRouge);
        target.playAttack(cardFeuRouge, source);
        assertEquals(source.getPlayedBoots().size(), 1); // Boot card is active
        assertEquals(source.getCurrentAttacks().size(), 0); // Red Light attack is neutralized
        target.addCard(cardLimitation);
        target.playAttack(cardLimitation, source);
        assertEquals(source.getPlayedBoots().size(), 1); // Boot card remains active
        assertEquals(source.getCurrentAttacks().size(), 0); // Speed Limitation attack is neutralized
    }
}
