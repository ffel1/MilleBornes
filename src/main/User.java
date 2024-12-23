package main;

import java.util.ArrayList;

// This class represents User
public class User extends Player{

// Private or protected member
    private boolean myTurn = false, hasDraw = false, hasPlayed = false, discard = false, hasDiscard = false, mustDraw = false, isAttacking = false;
// Private or protected member
    private Card isAttackingWith;
// Private or protected member
    private CPU target;

// This method handles the logic for User
    public User(String name, int k, int id, Game Game){
        super(name, k, id, Game);
    }

    @Override
// This method handles the logic for getTarget
    public Player getTarget(Card c)
    {
        return target;
    }
    
// This method handles the logic for getIsAttackingWith
    public Card getIsAttackingWith()
    {
        return isAttackingWith;
    }

// This method handles the logic for setTarget
    public void setTarget(CPU c)
    {
        target = c;
    }

// This method handles the logic for setIsAttackingWith
    public void setIsAttackingWith(Card c)
    {
        isAttackingWith = c;
    }

// This method handles the logic for myTurn
    public void myTurn(boolean myTurn)
    {
        this.myTurn = myTurn;
    }

// This method handles the logic for getMustDraw
    public boolean getMustDraw()
    {
        return mustDraw;
    }

// This method handles the logic for gethasDiscard
    public boolean gethasDiscard()
    {
        return hasDiscard;
    }

// This method handles the logic for setHasDiscard
    public void setHasDiscard(boolean b)
    {
        hasDiscard = b;
    }

// This method handles the logic for getIsAttacking
    public boolean getIsAttacking()
    {
        return isAttacking;
    }

// This method handles the logic for setIsAttacking
    public void setIsAttacking(boolean b)
    {
        isAttacking = b;
    }

// This method handles the logic for setmustDraw
    public void setmustDraw(boolean b)
    {
        mustDraw = b;
    }

// This method handles the logic for getdiscard
    public boolean getdiscard()
    {
        return discard;
    }

// This method handles the logic for setdiscard
    public void setdiscard(boolean b)
    {
        discard = b;
    }

// This method handles the logic for getHasPlayed
    public boolean getHasPlayed()
    {
        return hasPlayed;
    }
// This method handles the logic for sethasPlayed
    public void sethasPlayed(boolean b)
    {
        hasPlayed = b;
    }

// This method handles the logic for draw
    public void draw()
    {
        ArrayList<Card> draw = getGame().getdraw();  
        //La draw a déjà été mélangé dans Game
        if(!HandFull())
        {
            hasDraw = true;
            Card c = draw.get(0);
            getHand().add(c);
            draw.remove(c);
        }
        else
        {
            System.out.println("Hand Full");
        }
    }

// This method handles the logic for getmyTurn
    public boolean getmyTurn()
    {
        return myTurn;
    }

// This method handles the logic for getHasDraw
    public boolean getHasDraw()
    {
        return hasDraw;
    }
    
// This method handles the logic for setHasDraw
    public void setHasDraw(boolean b)
    {
        hasDraw = b;
    }

        /*
     * choose l'action en function du type de card
     */
// This method handles the logic for playCard
    public String playCard(Card c, Controler Controler, int nbCard) {
        if(!(c instanceof Boot))
        {
            hasPlayed = true; 
        }
        if(c instanceof Attack)
        {
            Controler.getVue().addMessage("Vous avez attaqué le CPU " + getTarget(c).getName() + " avec " + c.getName() + "! \n", true);
        }
        else
        {
            Controler.getVue().addMessage("Vous avez joué la card " + nbCard + " (" + c.getName() + ") \n", true); 
        }
        getHand().remove(c);
        Controler.getVue().effacerCardsPlayers();
        Controler.getVue().printCardsPlayer(getHand());
        Controler.initButtonCards(getHand());
        Controler.getVue().getWindow().repaint();
        Controler.getVue().getWindow().revalidate();
        if (c instanceof Attack){
            Player target = getTarget(c);
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

    /*
     * Vérifie qu'une card soit jouable
     * Renvoie à l'utilisateur la raisound de la non validité de sound action (chaque entier correspond à une raisound), 0 veut dire que c'est valide
     */
// This method handles the logic for checkUser
    public int checkUser(Card c, Player u, Player target){
        if (c instanceof Attack){
            if(c.getType() == TypeCard.RED_LIGHT && !target.getgreenLight())
            {
                return 7;
            }
            for (Card card : target.getPlayedBoots()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.PUNCTURE_PROOF) return 1;
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.EXPERT_DRIVER) return 1;
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.TANK_TRUCK) return 1;
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return 1;
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.PRIORITY_VEHICLE) return 1;
                        break;
                    default:
                        break;
                }
            }
            for (Card card : target.getCurrentAttacks()) {
                switch (c.getType()) {
                    case FLAT_TIRE:
                        if (card.getType() == TypeCard.FLAT_TIRE) return 2;
                        break;
                    case ACCIDENT:
                        if (card.getType() == TypeCard.ACCIDENT) return 2;
                        break;
                    case OUT_OF_FUEL:
                        if (card.getType() == TypeCard.OUT_OF_FUEL) return 2;
                        break;
                    case SPEED_LIMITATION:
                        if (card.getType() == TypeCard.SPEED_LIMITATION) return 2;
                        break;
                    case RED_LIGHT:
                        if (card.getType() == TypeCard.RED_LIGHT) return 2;
                        break;
                    default:
                        break;
                }
            }
            
        } 
        else if (c instanceof Distance){
            if(!getgreenLight())
            {
                return 3;
            }
            if(c.getKilometers()+u.getKilometers() > 700)
            {
                return 10;
            }
            for (Card card : u.getCurrentAttacks()){
                switch (c.getType()) {
                    case _25KM :
                    case _50KM :
                        if (card.getType() == TypeCard.RED_LIGHT | card.getType() == TypeCard.OUT_OF_FUEL | card.getType() == TypeCard.FLAT_TIRE | card.getType() == TypeCard.ACCIDENT) return 4;
                        break;
                    case _75KM : 
                    case _100KM :
                    case _200KM :
                        if (card.getType() == TypeCard.RED_LIGHT | card.getType() == TypeCard.OUT_OF_FUEL | card.getType() == TypeCard.FLAT_TIRE | card.getType() == TypeCard.ACCIDENT | card.getType() == TypeCard.SPEED_LIMITATION) return 4;
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
                        return 0;
                    }
                    else if(getgreenLight())
                    {
                        return 5;
                    }
                case END_SPEED_LIMITATION:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.SPEED_LIMITATION))
                    {
                        return 0;
                    }
                case FUEL:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.OUT_OF_FUEL))
                    {
                        return 0;
                    }
                case SPARE_WHEEL:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.FLAT_TIRE))
                    {
                        return 0;
                    }
                case REPAIR:
                    if(getCurrentAttacks().stream().anyMatch(card -> card.getType() == TypeCard.ACCIDENT))
                    {
                        return 0;
                    }
                default:
                    break;
            }
            return 6;
        }
        return 0;
    }

// This method handles the logic for discard
    public String discard(Card c, Controler Controler)
    {
        getHand().remove(c);
        Controler.getVue().effacerCardsPlayers();
        Controler.getVue().printCardsPlayer(getHand());
        Controler.initButtonCards(getHand());
        Controler.getVue().getWindow().repaint();
        Controler.getVue().getWindow().revalidate();
        return "Vous avez défaussé la card : " + c.getName() + "\n";
    }
}

