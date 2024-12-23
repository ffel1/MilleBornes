package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Tests sur les cards distances.
 */
public class BotTest{

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private Attack cardAccident;
    private Attack cardFLAT_TIRE;
    private Attack cardPanne;
    private Attack cardFeuRouge;
    private Attack cardLimitation;
    private Safety cardgreenLight;
    private Safety cardREPAIR;
    private Safety cardFUEL;
    private Safety cardRoueDeSecours;
    private Safety cardEndDeLimitation;
    private Boot asDuVolant;
    private Boot camionCiterne;
    private Boot PUNCTURE_PROOF;
    private CPUFast botFast;
    private CPUAgro botAgro;
    private CPUFast botTestFAST;
    private Game Game;

    @BeforeEach
    public void init(){
        Game = new Game();
        Game.newGame();
        _25KM = new Distance(TypeCard._25KM, 25);
        _50KM = new Distance(TypeCard._50KM, 50);
        _75KM = new Distance(TypeCard._75KM, 75);
        _100KM = new Distance(TypeCard._100KM, 100);
        _200KM = new Distance(TypeCard._200KM, 200);
        cardgreenLight = new Safety(TypeCard.GREEN_LIGHT);
        cardREPAIR = new Safety(TypeCard.REPAIR);
        cardFUEL = new Safety(TypeCard.FUEL);
        cardRoueDeSecours = new Safety(TypeCard.SPARE_WHEEL);
        cardEndDeLimitation = new Safety(TypeCard.END_SPEED_LIMITATION);
        cardAccident = new Attack(TypeCard.ACCIDENT);
        cardFLAT_TIRE = new Attack(TypeCard.FLAT_TIRE);
        cardPanne = new Attack(TypeCard.OUT_OF_FUEL);
        cardFeuRouge = new Attack(TypeCard.RED_LIGHT);
        cardLimitation = new Attack(TypeCard.SPEED_LIMITATION);
        asDuVolant = new Boot(TypeCard.EXPERT_DRIVER);
        camionCiterne = new Boot(TypeCard.TANK_TRUCK);
        PUNCTURE_PROOF = new Boot(TypeCard.PUNCTURE_PROOF);
        botFast = (CPUFast)Game.getPlayer3();
        botAgro = (CPUAgro)Game.getPlayer2();
        //botTestFAST = new CPUFast("fast", 0, 0, null);

    }

    //@Test
    public void choixcard(){
        // Test choix de la card
        botTestFAST.addCard(_25KM);
        botTestFAST.setGreenLight(true);
        assertEquals(_25KM, botTestFAST.chooseCard());
        
    }

    @Test
    public void choosetarget(){
        // Test choix target
        Game Game = new Game();
        Game.newGame();
        Game.getPlayer2().setKilometers(200);
        assertEquals(200, Game.getPlayer2().getKilometers());
        assertEquals(Game.getPlayer2(), Game.getPlayer3().getTarget(cardAccident));
        
    }

    /*
     * Joue en priorité la distance la plus grande
     */
    @Test
    public void testplayDistanceFast(){
        for(int i = 0; i < 6; i++){
            botFast.removeCard(botFast.getHand().get(0));
        }
        // Test utilisation des cards distance
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
        assertEquals(5, botFast.getHand().size());
    }

    /*
     * Joue une botte jusqu'à ce qu'il n'en a plus
     */
    @Test
    public void testplayBootFastEtAgro(){
        for(int i = 0; i < 6; i++){
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Quand il a une botte
        botFast.setGreenLight(true);
        botFast.addCard(_25KM);
        botFast.addCard(cardAccident);
        botFast.addCard(PUNCTURE_PROOF);
        botFast.addCard(_200KM);
        assertEquals(PUNCTURE_PROOF, botFast.chooseCard());
        assertEquals(4, botFast.getHand().size());

        // Quand il a plusieurs bottes
        botFast.playBoot(PUNCTURE_PROOF);
        assertEquals(3, botFast.getHand().size());
        botFast.addCard(asDuVolant);
        botFast.addCard(camionCiterne);
        assertEquals(asDuVolant, botFast.chooseCard());
        botFast.playBoot(asDuVolant);
        assertEquals(camionCiterne, botFast.chooseCard());
        botFast.playBoot(camionCiterne);
        assertEquals(_200KM, botFast.chooseCard());
    }

    /*
     * Joue en priorité la parade si il a une attaque sur lui
     */
    @Test
    public void testplaySafetyFastEtAgro(){
        // Test utilisation de la card
        for(int i = 0; i < 6; i++){
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Choisi un feu vert
        botFast.addCard(cardgreenLight);
        botFast.addCard(_25KM);
        botFast.addCard(cardFLAT_TIRE);
        botFast.addCard(_200KM);
        assertEquals(cardgreenLight, botFast.chooseCard());
        assertEquals(4, botFast.getHand().size());
        botFast.playSafety(cardgreenLight);

        // Choisi une roue de secours
        botAgro.addCard(cardFLAT_TIRE);
        botAgro.playAttack(cardFLAT_TIRE, botFast);
        assertEquals(2, botFast.getCurrentAttacks().size());
        botFast.addCard(cardRoueDeSecours);
        assertEquals(cardRoueDeSecours, botFast.chooseCard());
        botFast.playSafety(cardRoueDeSecours);
        assertEquals(1, botFast.getCurrentAttacks().size());

        // Choisi réparation
        botAgro.addCard(cardAccident);
        botAgro.playAttack(cardAccident, botFast);
        assertEquals(2, botFast.getCurrentAttacks().size());
        botFast.addCard(cardREPAIR);
        assertEquals(cardREPAIR, botFast.chooseCard());
        botFast.playSafety(cardREPAIR);
        assertEquals(1, botFast.getCurrentAttacks().size());

        // Choisi End limitation
        botAgro.addCard(cardLimitation);
        botAgro.playAttack(cardLimitation, botFast);
        assertEquals(2, botFast.getCurrentAttacks().size());
        botFast.addCard(cardEndDeLimitation);
        assertEquals(cardEndDeLimitation, botFast.chooseCard());
        botFast.playSafety(cardEndDeLimitation);
        assertEquals(1, botFast.getCurrentAttacks().size());

        // Choisi End limitation
        botAgro.addCard(cardPanne);
        botAgro.playAttack(cardPanne, botFast);
        assertEquals(2, botFast.getCurrentAttacks().size());
        botFast.addCard(cardFUEL);
        assertEquals(cardFUEL, botFast.chooseCard());
        botFast.playSafety(cardFUEL);
        assertEquals(1, botFast.getCurrentAttacks().size());
    }

    /*
     * Joue en priorité une attaque si il ne peut rien faire d'autre
     */
    @Test
    public void testplayAttackFast(){
        // Test utilisation de la card
        for(int i = 0; i < 6; i++){
            botFast.removeCard(botFast.getHand().get(0));
        }

        // Choisi une attaque accident
        botFast.addCard(_75KM);
        botFast.addCard(_25KM);
        botFast.addCard(cardEndDeLimitation);
        botFast.addCard(_200KM);
        botFast.addCard(cardAccident);
        assertEquals(cardAccident, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardAccident, botAgro);

        // Choisi une attaque FLAT_TIRE
        botFast.addCard(cardFLAT_TIRE);
        assertEquals(cardFLAT_TIRE, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardFLAT_TIRE, botAgro);

        // Choisi une attaque limitation
        botFast.addCard(cardLimitation);
        assertEquals(cardLimitation, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardLimitation, botAgro);

        // Choisi une attaque quand il en a plusieurs (feu rouge et panne)
        botFast.addCard(cardPanne);
        botFast.addCard(cardFeuRouge);
        assertEquals(cardPanne, botFast.chooseCard());
        assertEquals(6, botFast.getHand().size());
        botFast.playAttack(cardPanne, botAgro);
        assertEquals(null, botFast.chooseCard()); // Car les autres players ont un feu rouge
        botAgro.setGreenLight(true);
        assertEquals(cardFeuRouge, botFast.chooseCard());
        assertEquals(5, botFast.getHand().size());
        botFast.playAttack(cardFeuRouge, botAgro);
    }

     /*
     * Joue en priorité une attaque même si il peut moveForwad
     */
    @Test
    public void testplayAttackAgro(){
        // Test utilisation de la card
        for(int i = 0; i < 6; i++){
            botAgro.removeCard(botAgro.getHand().get(0));
        }

        // Choisi une attaque accident même si il peut moveForwad
        botAgro.setGreenLight(true); 
        botAgro.addCard(_25KM);
        botAgro.addCard(_75KM);
        botAgro.addCard(cardEndDeLimitation);
        botAgro.addCard(_200KM);
        botAgro.addCard(cardAccident);
        assertEquals(cardAccident, botAgro.chooseCard());
        assertEquals(5, botAgro.getHand().size());
        botAgro.playAttack(cardAccident, botFast);

        // Choisi une attaque FLAT_TIRE même si il peut play une parade
        botAgro.addCard(cardFLAT_TIRE);
        botAgro.addCard(cardgreenLight);
        botAgro.setGreenLight(false);
        assertEquals(cardFLAT_TIRE, botAgro.chooseCard());
        assertEquals(6, botAgro.getHand().size());
        botAgro.playAttack(cardFLAT_TIRE, botFast);
        botAgro.playSafety(cardgreenLight);
        botAgro.setGreenLight(false);

        // Choisi une attaque limitation
        botAgro.addCard(cardLimitation);
        assertEquals(cardLimitation, botAgro.chooseCard());
        assertEquals(5, botAgro.getHand().size());
        botAgro.playAttack(cardLimitation, botFast);

        // Choisi une attaque quand il en a plusieurs (feu rouge et panne)
        botAgro.addCard(cardPanne);
        botAgro.addCard(cardFeuRouge);
        assertEquals(cardPanne, botAgro.chooseCard());
        assertEquals(6, botAgro.getHand().size());
        botAgro.playAttack(cardPanne, botFast);
        assertEquals(null, botAgro.chooseCard()); // Car les autres players ont un feu rouge
        botFast.setGreenLight(true);
        assertEquals(cardFeuRouge, botAgro.chooseCard());
        assertEquals(5, botAgro.getHand().size());
        botAgro.playAttack(cardFeuRouge, botFast);
    }
}