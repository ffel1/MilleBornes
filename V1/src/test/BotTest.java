package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Unit tests for bot actions, including card choice, card usage (distances, attacks, safeties), 
 * and priority behaviors of the CPU bots in the game.
 */
public class BotTest {

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private Attack cardAccident;
    private Attack cardFLAT_TIRE;
    private Safety cardGreenLight;
    private Safety cardSpareWheel;
    private Safety cardEndLimitation;
    private Boot asDuVolant;
    private Boot camionCiterne;
    private Boot punctureProof;
    private CPUFast botFast;
    private CPUAgro botAgro;
    private CPUFast botTestFast;
    private Game game;

    /**
     * Setup method executed before each test case.
     * Initializes game, players, cards, and bot instances.
     */
    @BeforeEach
    public void init() {
        // Create new game and initialize all the card instances
        game = new Game();
        game.newGame();

        // Initialize distance cards
        _25KM = new Distance(TypeCard._25KM, 25);
        _50KM = new Distance(TypeCard._50KM, 50);
        _75KM = new Distance(TypeCard._75KM, 75);
        _100KM = new Distance(TypeCard._100KM, 100);
        _200KM = new Distance(TypeCard._200KM, 200);

        // Initialize safety cards
        cardGreenLight = new Safety(TypeCard.GREEN_LIGHT);
        cardSpareWheel = new Safety(TypeCard.SPARE_WHEEL);
        cardEndLimitation = new Safety(TypeCard.END_SPEED_LIMITATION);

        // Initialize attack cards
        cardAccident = new Attack(TypeCard.ACCIDENT);
        cardFLAT_TIRE = new Attack(TypeCard.FLAT_TIRE);

        // Initialize boot cards
        asDuVolant = new Boot(TypeCard.EXPERT_DRIVER);
        camionCiterne = new Boot(TypeCard.TANK_TRUCK);
        punctureProof = new Boot(TypeCard.PUNCTURE_PROOF);

        // Get bot instances from the game
        botFast = (CPUFast)game.getPlayer3();
        botAgro = (CPUAgro)game.getPlayer2();
    }

    /**
     * Test case: Verifies the bot's card selection behavior.
     * The bot chooses the _25KM card when it is added and green light is on.
     */
    @Test
    public void testChooseCard() {
        // Add a card and enable the green light
        botTestFast.addCard(_25KM);
        botTestFast.setGreenLight(true);

        // Assert that the bot chooses the _25KM card
        assertEquals(_25KM, botTestFast.chooseCard());
    }

    /**
     * Test case: Verifies the bot's target selection behavior.
     * The bot chooses the correct target based on its opponent's distance.
     */
    @Test
    public void testChooseTarget() {
        // Start a new game and set player2's distance to 200
        game = new Game();
        game.newGame();
        game.getPlayer2().setKilometers(200);

        // Assert that the bot chooses the player2 as the target for the "Accident" attack
        assertEquals(200, game.getPlayer2().getKilometers());
        assertEquals(game.getPlayer2(), game.getPlayer3().getTarget(cardAccident));
    }

    /**
     * Test case: Verifies that the bot plays the highest distance card available first.
     * The bot should always prioritize the largest distance available.
     */
    @Test
    public void testPlayDistanceFast() {
        // Remove cards from hand to test distance card selection
        for (int i = 0; i < 6; i++) {
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Add distance cards and assert that the bot chooses the largest available distance
        botFast.setGreenLight(true);
        botFast.addCard(_25KM);
        assertEquals(_25KM, botFast.chooseCard());
        botFast.addCard(_50KM);
        assertEquals(_50KM, botFast.chooseCard());
        botFast.addCard(_75KM);
        assertEquals(_75KM, botFast.chooseCard());
        botFast.addCard(_100KM);
        assertEquals(_100KM, botFast.chooseCard());
        botFast.addCard(_200KM);
        assertEquals(_200KM, botFast.chooseCard());

        // Ensure the hand size is reduced as expected
        assertEquals(5, botFast.getHand().size());
    }

    /**
     * Test case: Verifies that the bot plays boots until none are left.
     * The bot plays boots as long as it has them in its hand.
     */
    @Test
    public void testPlayBootFastAndAgro() {
        // Remove cards to test boot card usage
        for (int i = 0; i < 6; i++) {
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Add boot cards and verify the bot plays each one in order
        botFast.setGreenLight(true);
        botFast.addCard(_25KM);
        botFast.addCard(cardAccident);
        botFast.addCard(punctureProof);
        botFast.addCard(_200KM);

        // Verify that the bot chooses the PUNCTURE_PROOF boot first
        assertEquals(punctureProof, botFast.chooseCard());
        assertEquals(4, botFast.getHand().size());

        // Play the first boot and ensure the next boot is chosen
        botFast.playBoot(punctureProof);
        assertEquals(3, botFast.getHand().size());
        botFast.addCard(asDuVolant);
        botFast.addCard(camionCiterne);

        // Assert that the bot chooses "asDuVolant" and plays it
        assertEquals(asDuVolant, botFast.chooseCard());
        botFast.playBoot(asDuVolant);
        assertEquals(camionCiterne, botFast.chooseCard());
        botFast.playBoot(camionCiterne);

        // Finally, ensure the bot plays the distance card
        assertEquals(_200KM, botFast.chooseCard());
    }

    /**
     * Test case: Verifies that the bot prioritizes using safety cards when an attack is on it.
     * The bot should use the best available safety card when under attack.
     */
    @Test
    public void testPlaySafetyFastAndAgro() {
        // Remove cards to test safety card usage
        for (int i = 0; i < 6; i++) {
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Add cards and check if the bot chooses the best available safety
        botFast.addCard(cardGreenLight);
        botFast.addCard(_25KM);
        botFast.addCard(cardFLAT_TIRE);
        botFast.addCard(_200KM);

        // Ensure the bot chooses the "Green Light" safety card
        assertEquals(cardGreenLight, botFast.chooseCard());
        assertEquals(4, botFast.getHand().size());

        // Play the green light safety card
        botFast.playSafety(cardGreenLight);

        // Test using "Spare Wheel" safety after being attacked
        botAgro.addCard(cardFLAT_TIRE);
        botAgro.playAttack(cardFLAT_TIRE, botFast);
        assertEquals(2, botFast.getCurrentAttacks().size());
        botFast.addCard(cardSpareWheel);
        assertEquals(cardSpareWheel, botFast.chooseCard());
        botFast.playSafety(cardSpareWheel);
        assertEquals(1, botFast.getCurrentAttacks().size());
    }

    /**
     * Test case: Verifies that the bot plays an attack card when it cannot do anything else.
     * The bot should prioritize playing attack cards if it has no other options.
     */
    @Test
    public void testPlayAttackFast() {
        // Remove cards to test attack card usage
        for (int i = 0; i < 6; i++) {
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Add cards and ensure the bot plays the first available attack card
        botFast.addCard(_75KM);
        botFast.addCard(_25KM);
        botFast.addCard(cardEndLimitation);
        botFast.addCard(_200KM);
        botFast.addCard(cardAccident);

        // Verify the bot chooses the "Accident" attack card
        assertEquals(cardAccident, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardAccident, botAgro);

        // Test with a second attack card (FLAT_TIRE)
        botFast.addCard(cardFLAT_TIRE);
        assertEquals(cardFLAT_TIRE, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardFLAT_TIRE, botAgro);
    }

    /**
     * Test case: Verifies that the agro bot prioritizes playing attacks even when it could move forward.
     * The bot should attack whenever possible, even if it can move forward.
     */
    @Test
    public void testPlayAttackAgro() {
        // Remove cards to test attack card usage
        for (int i = 0; i < 6; i++) {
            botAgro.removeCard(botAgro.getHand().get(0));
        }

        // Add cards and ensure the agro bot prioritizes attacks
        botAgro.setGreenLight(true);
        botAgro.addCard(_25KM);
        botAgro.addCard(_75KM);
        botAgro.addCard(cardEndLimitation);
        botAgro.addCard(_200KM);
        botAgro.addCard(cardAccident);

        // Assert that the bot chooses the "Accident" attack first, even though it can move
        assertEquals(cardAccident, botAgro.chooseCard());
        assertEquals(5, botAgro.getHand().size());
        botAgro.playAttack(cardAccident, botFast);

        // Test attack with multiple cards (FLAT_TIRE)
        botAgro.addCard(cardFLAT_TIRE);
        botAgro.addCard(cardGreenLight);
        botAgro.setGreenLight(false);
        assertEquals(cardFLAT_TIRE, botAgro.chooseCard());
        assertEquals(6, botAgro.getHand().size());
        botAgro.playAttack(cardFLAT_TIRE, botFast);
    }
}