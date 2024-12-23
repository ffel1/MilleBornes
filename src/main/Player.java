package main;

import java.io.Serializable;
import java.util.ArrayList;

// This class represents Player
public abstract class Player implements Serializable{
// Private or protected member
    private ArrayList<Card> Hand;
// Private or protected member
    private ArrayList<Card> currentAttacks;
// Private or protected member
    private boolean greenLight;
// Private or protected member
    private ArrayList<Card> playedBoots;
// Private or protected member
    private String name;
// Private or protected member
    private int kilometersP;
// Private or protected member
    private int id;
// Private or protected member
    private int pts;
// Private or protected member
    private Game Game;
// Private or protected member
    private int dirtyTrickes = 0;
// Private or protected member
    private ArrayList<Card> cardsDistanceJouees;

// This method handles the logic for Player
    public Player(String name, int km, int id, Game Game){
        cardsDistanceJouees  = new ArrayList<Card>();
        Hand = new ArrayList<Card>();
        currentAttacks = new ArrayList<Card>();
        greenLight = false;
        playedBoots = new ArrayList<Card>();
        this.name = name;
        kilometersP = km;
        this.id = id; 
        this.Game = Game;
    }

// This method handles the logic for getHand
    public ArrayList<Card> getHand(){
        return Hand;
    }
// This method handles the logic for getCurrentAttacks
    public ArrayList<Card> getCurrentAttacks(){
        return currentAttacks;
    }
// This method handles the logic for getgreenLight
    public boolean getgreenLight(){
        return greenLight;
    }

// This method handles the logic for setpts
    public void setpts(int pts)
    {
        this.pts = pts;
    }

// This method handles the logic for getPts
    public int getPts()
    {
        return pts;
    }

// This method handles the logic for getGame
    public Game getGame()
    {
        return Game;
    }

// This method handles the logic for setGreenLight
    public void setGreenLight(boolean b)
    {
        greenLight = b;
    }
// This method handles the logic for getPlayedBoots
    public ArrayList<Card> getPlayedBoots(){
        return playedBoots;
    }
// This method handles the logic for getName
    public String getName(){
        return name;
    }
// This method handles the logic for getKilometers
    public int getKilometers(){
        return kilometersP;
    }
// This method handles the logic for setKilometers
    public void setKilometers(int Kilometers)
    {
        kilometersP+=Kilometers;
    }
// This method handles the logic for getId
    public int getId(){
        return id;
    }

// This method handles the logic for getdirtyTricks
    public int getdirtyTricks(){
        return dirtyTrickes;
    }

// This method handles the logic for HandFull
    public boolean HandFull(){
        return Hand.size() >= 7;
    }

// This method handles the logic for removeCard
    public void removeCard(Card c){
        Hand.remove(c);
    }

// This method handles the logic for addCard
    public void addCard(Card c){
        Hand.add(c);

    }
    
// This method handles the logic for use200KM
    public boolean use200KM() {
        for (Card card : cardsDistanceJouees) {
            if (card instanceof Distance && ((Distance) card).getKilometers() == 200) {
                return true;
            }
        }
        return false;
    }

// This method handles the logic for draw
    public void draw()
    {
        ArrayList<Card> draw = Game.getdraw();  
        //La draw a déjà été mélangé dans Game
        if(!HandFull())
        {
            Card c = draw.get(0);
            getHand().add(c);
            draw.remove(c);
        }
        else
        {
            System.out.println("Hand Full");
        }
    }

    /*
     * choose l'action en function du type de card
     */
// This method handles the logic for playCard
    public String playCard(Card c, Player target) {
        if (c instanceof Attack){
            return playAttack(c, target);
        } else if (c instanceof Safety){
            return playSafety(c);
        } else if (c instanceof Boot){
            return playBoot(c);
        } else if (c instanceof Distance){
            return playDistance(c);
        }   
        return null;
    }

// This method handles the logic for getTarget
    public abstract Player getTarget(Card c);

    /*
     * Joue une card botte au player et enleve l'attaque en cours si il y en a une
     */
// This method handles the logic for playBoot
    public String playBoot(Card c) {
        if (c instanceof Boot) {
            playedBoots.add(c);
            boolean dirtyTrick = false;
            switch (c.getType()) {
                case EXPERT_DRIVER:  
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.ACCIDENT); 
                    break;
                case TANK_TRUCK:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.OUT_OF_FUEL);
                    break;
                case PUNCTURE_PROOF:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.FLAT_TIRE);
                    break;
                case PRIORITY_VEHICLE:
                    dirtyTrick = currentAttacks.removeIf(card -> card.getType() == TypeCard.SPEED_LIMITATION || card.getType() == TypeCard.RED_LIGHT);
                    setGreenLight(true);
                    break;
                default:
                    break;
            }
            if(dirtyTrick){
                dirtyTrickes++;
            }
            removeCard(c);
            return getName() + " joue la botte : " + c.getName() + "\n";
        }
        return null;
    }

// This method handles the logic for discard
    public abstract String discard(Card c, Controler Controler);
    
    /*
     * Joue une card parade et enleve l'attaque en cours
     */
// This method handles the logic for playSafety
    public String playSafety(Card c) {
        if (c instanceof Safety) {
            switch (c.getType()) {
                case GREEN_LIGHT:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.RED_LIGHT); 
                    currentAttacks.add(0,c);
                    greenLight = true;
                    break;
                case END_SPEED_LIMITATION:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.SPEED_LIMITATION);
                    break;
                case FUEL:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.OUT_OF_FUEL);
                    break;
                case SPARE_WHEEL:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.FLAT_TIRE);
                    break;
                case REPAIR:
                    currentAttacks.removeIf(card -> card.getType() == TypeCard.ACCIDENT);
                    break;
                default:
                    break;
            }
            removeCard(c);
            if(c.getType() == TypeCard.GREEN_LIGHT)
            {
                return getName() + " joue un " + c.getName() + " ! \n";
            }
            return getName() + " joue la parade : " + c.getName() + "\n";
        }
        return null;
    }
    
    /*
     * Joue une card attaque et demande sur quel player
     */
// This method handles the logic for playAttack
    public String playAttack(Card c, Player target) {
        if (check(c, this, target)) {
            if(c.getType() == TypeCard.RED_LIGHT)
            {
                target.setGreenLight(false);
                for(Card card : target.currentAttacks)
                {
                    if(card.getType() == TypeCard.GREEN_LIGHT)
                    {

                        target.currentAttacks.remove(card);
                        break;
                    }
                }
            }
            target.getCurrentAttacks().add(c);
            removeCard(c);
            return getName() + " joue l'attaque " + c.getName() + " contre " + target.getName() + "\n";
        }
        return null;
    }
    

    /*
     * Augmente le namebre de km du player
     */
// This method handles the logic for playDistance
    public String playDistance(Card c) {
        if (check(c, this, this)) {
            int Kilometers = ((Distance) c).getKilometers();
            kilometersP += Kilometers;
            cardsDistanceJouees.add(c);
            removeCard(c);
            return (getName() + " avance de " + Kilometers + " km. Distance totale : " + kilometersP + " km. \n");
        }
        else{
            String message = name+" ne peut pas moveForwad. Problème : ";
            for(Card card : currentAttacks)
            {
                if(card.getType() == TypeCard.RED_LIGHT)
                {
                    return message+"feu rouge.\n";
                }

                if(card.getType() == TypeCard.OUT_OF_FUEL)
                {
                    return message+"panne d'FUEL.\n";
                }

                if(card.getType() == TypeCard.FLAT_TIRE)
                {
                    return message+"FLAT_TIRE.\n";
                }

                if(card.getType() == TypeCard.ACCIDENT)
                {
                    return message+"accident.\n";
                }

                if(card.getType() == TypeCard.SPEED_LIMITATION)
                {
                    return message+"limitation de vitesse.\n";
                }
            }
        }
        return null;
    }

    /*
     * Vérifie qu'une card soit jouable
     */
// This method handles the logic for check
    public boolean check(Card c, Player u, Player target){
        if (c instanceof Attack){
            if(target == null)
            {
                return false;
            }
            if(c.getType() == TypeCard.RED_LIGHT && !target.getgreenLight())
            {
                return false;
            }
            for (Card card : target.getPlayedBoots()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.PUNCTURE_PROOF) return false;
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.EXPERT_DRIVER) return false;
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.TANK_TRUCK) return false;
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return false;
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return false;
                        break;
                    default:
                        break;
                }
            }
            for (Card card : target.getCurrentAttacks()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.FLAT_TIRE) return false;
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.ACCIDENT) return false;
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.OUT_OF_FUEL) return false;
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.SPEED_LIMITATION) return false;
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.RED_LIGHT) return false;
                        break;
                    default:
                        break;
                }
            }
            
        } else if (c instanceof Distance){
            if(!greenLight)
            {
                return false;
            }
            if(c.getKilometers()+u.getKilometers() > 700)
            {
                return false;
            }
            for (Card card : u.getCurrentAttacks()){
                switch (c.getType()) {
                    case _25KM :
                    case _50KM :
                        if (card.getType() == TypeCard.RED_LIGHT | card.getType() == TypeCard.OUT_OF_FUEL | card.getType() == TypeCard.FLAT_TIRE | card.getType() == TypeCard.ACCIDENT) return false;
                        break;
                    case _75KM : 
                    case _100KM :
                    case _200KM :
                        if (card.getType() == TypeCard.RED_LIGHT | card.getType() == TypeCard.OUT_OF_FUEL | card.getType() == TypeCard.FLAT_TIRE | card.getType() == TypeCard.ACCIDENT | card.getType() == TypeCard.SPEED_LIMITATION) return false;
                        break;
                    default:
                        break;
                }
            }
        } else if (c instanceof Safety){
            switch (c.getType()) {
                case GREEN_LIGHT:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.RED_LIGHT) || !getgreenLight())
                    {
                        return true;
                    }
                    else if(getgreenLight())
                    {
                        return false;
                    }
                case END_SPEED_LIMITATION:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.SPEED_LIMITATION))
                    {
                        return true;
                    }
                case FUEL:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.OUT_OF_FUEL))
                    {
                        return true;
                    }
                case SPARE_WHEEL:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.FLAT_TIRE))
                    {
                        return true;
                    }
                case REPAIR:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.ACCIDENT))
                    {
                        return true;
                    }
                default:
                    break;
            }
            return false;
        }
        return true;
    }
}