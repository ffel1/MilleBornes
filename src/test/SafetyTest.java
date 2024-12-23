package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Tests sur les cards parades.
 */
public class SafetyTest{

    private Safety cardgreenLight;
    private Safety cardREPAIR;
    private Safety cardFUEL;
    private Safety cardRoueDeSecours;
    private Safety cardEndDeLimitation;
    private Attack cardAccident;
    private Attack cardFLAT_TIRE;
    private Attack cardPanne;
    private Attack cardFeuRouge;
    private Attack cardLimitation;
    private User source;
    private User target;

    @BeforeEach
    public void init(){
        cardgreenLight = new Safety(TypeCard.GREEN_LIGHT);
        cardREPAIR = new Safety(TypeCard.REPAIR);
        cardFUEL = new Safety(TypeCard.FUEL);
        cardRoueDeSecours = new Safety(TypeCard.SPARE_WHEEL);
        cardEndDeLimitation = new Safety(TypeCard.END_SPEED_LIMITATION);
        source = new User("Source", 0, 0, null);
        target = new User("target", 0, 0, null);
        cardAccident = new Attack(TypeCard.ACCIDENT);
        cardFLAT_TIRE = new Attack(TypeCard.FLAT_TIRE);
        cardPanne = new Attack(TypeCard.OUT_OF_FUEL);
        cardFeuRouge = new Attack(TypeCard.RED_LIGHT);
        cardLimitation = new Attack(TypeCard.SPEED_LIMITATION);
    }

    @Test
    public void testgreenLight(){
        // Test de la card
        assertEquals("GREEN_LIGHT", cardgreenLight.getName());
        assertEquals(TypeCard.GREEN_LIGHT, cardgreenLight.getType());
        assertEquals(0, cardgreenLight.getKilometers());
        assertNotNull(cardgreenLight.getImage());

        // Test utilisation de la card
        source.addCard(cardFeuRouge);
        target.setGreenLight(true); // Car on commence avec un feu rouge
        target.addCard(cardgreenLight);
        source.playAttack(cardFeuRouge, target);
        target.playSafety(cardgreenLight);
        assertEquals(target.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().size(), 0);
        assertEquals(target.getgreenLight(), true);
    }

    @Test
    public void testREPAIR(){
        // Test de la card
        assertEquals("REPAIR", cardREPAIR.getName());
        assertEquals(TypeCard.REPAIR, cardREPAIR.getType());
        assertEquals(0, cardREPAIR.getKilometers());
        assertNotNull(cardREPAIR.getImage());

        // Test utilisation de la card
        source.addCard(cardAccident);
        target.addCard(cardREPAIR);
        source.playAttack(cardAccident, target);
        target.playSafety(cardREPAIR);
        assertEquals(target.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testFUEL(){
        // Test de la card
        assertEquals("FUEL", cardFUEL.getName());
        assertEquals(TypeCard.FUEL, cardFUEL.getType());
        assertEquals(0, cardFUEL.getKilometers());
        assertNotNull(cardFUEL.getImage());

        // Test utilisation de la card
        source.addCard(cardPanne);
        target.addCard(cardFUEL);
        source.playAttack(cardPanne, target);
        target.playSafety(cardFUEL);
        assertEquals(target.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testRoueDeSecours(){
        // Test de la card
        assertEquals("SPARE_WHEEL", cardRoueDeSecours.getName());
        assertEquals(TypeCard.SPARE_WHEEL, cardRoueDeSecours.getType());
        assertEquals(0, cardRoueDeSecours.getKilometers());
        assertNotNull(cardRoueDeSecours.getImage());

        // Test utilisation de la card
        source.addCard(cardFLAT_TIRE);
        target.addCard(cardRoueDeSecours);
        source.playAttack(cardFLAT_TIRE, target);
        target.playSafety(cardRoueDeSecours);
        assertEquals(target.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().size(), 0);
    }

    @Test
    public void testEndDeLimitation(){
        // Test de la card
        assertEquals("END_SPEED_LIMITATION", cardEndDeLimitation.getName());
        assertEquals(TypeCard.END_SPEED_LIMITATION, cardEndDeLimitation.getType());
        assertEquals(0, cardEndDeLimitation.getKilometers());
        assertNotNull(cardEndDeLimitation.getImage());

        // Test utilisation de la card
        source.addCard(cardLimitation);
        target.addCard(cardEndDeLimitation);
        source.playAttack(cardLimitation, target);
        target.playSafety(cardEndDeLimitation);
        assertEquals(target.getHand().size(), 0);
        assertEquals(target.getCurrentAttacks().size(), 0);
    }
}