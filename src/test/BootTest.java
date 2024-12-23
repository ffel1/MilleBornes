package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Tests sur les cards parades.
 */
public class BootTest{

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

    @BeforeEach
    public void init(){
        source = new User("Source", 0, 0, null);
        target = new User("target", 0, 0, null);
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

    @Test
    public void testAsDuVolant(){
        // Test de la card
        assertEquals("EXPERT_DRIVER", asDuVolant.getName());
        assertEquals(TypeCard.EXPERT_DRIVER, asDuVolant.getType());
        assertEquals(0, asDuVolant.getKilometers());
        assertNotNull(asDuVolant.getImage());

        // Test utilisation de la card
        source.addCard(asDuVolant);
        source.playBoot(asDuVolant);
        target.addCard(cardAccident);
        target.playAttack(cardAccident, source);
        assertEquals(source.getPlayedBoots().size(), 1);
        assertEquals(source.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testCamionCiterne(){
        // Test de la card
        assertEquals("TANK_TRUCK", camionCiterne.getName());
        assertEquals(TypeCard.TANK_TRUCK, camionCiterne.getType());
        assertEquals(0, camionCiterne.getKilometers());
        assertNotNull(camionCiterne.getImage());

        // Test utilisation de la card
        source.addCard(camionCiterne);
        source.playBoot(camionCiterne);
        target.addCard(cardPanne);
        target.playAttack(cardPanne, source);
        assertEquals(source.getPlayedBoots().size(), 1);
        assertEquals(source.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testPUNCTURE_PROOF(){
        // Test de la card
        assertEquals("PUNCTURE_PROOF", PUNCTURE_PROOF.getName());
        assertEquals(TypeCard.PUNCTURE_PROOF, PUNCTURE_PROOF.getType());
        assertEquals(0, PUNCTURE_PROOF.getKilometers());
        assertNotNull(PUNCTURE_PROOF.getImage());

        // Test utilisation de la card
        source.addCard(PUNCTURE_PROOF);
        source.playBoot(PUNCTURE_PROOF);
        target.addCard(cardFLAT_TIRE);
        target.playAttack(cardFLAT_TIRE, source);
        assertEquals(source.getPlayedBoots().size(), 1);
        assertEquals(source.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testVehiculePrioritaire(){
        // Test de la card
        assertEquals("PRIOTIRY_VEHICLE", vehiculePrioritaire.getName());
        assertEquals(TypeCard.PRIOTIRY_VEHICLE, vehiculePrioritaire.getType());
        assertEquals(0, vehiculePrioritaire.getKilometers());
        assertNotNull(vehiculePrioritaire.getImage());

        // Test utilisation de la card
        source.addCard(vehiculePrioritaire);
        source.playBoot(vehiculePrioritaire);
        target.addCard(cardFeuRouge);
        target.playAttack(cardFeuRouge, source);
        assertEquals(source.getPlayedBoots().size(), 1);
        assertEquals(source.getCurrentAttacks().size(), 0);
        target.addCard(cardLimitation);
        target.playAttack(cardLimitation, source);
        assertEquals(source.getPlayedBoots().size(), 1);
        assertEquals(source.getCurrentAttacks().size(), 0);
    }
}