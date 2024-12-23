package main;

import java.util.ArrayList;

// This class represents CPUFast
public class CPUFast extends CPU{
    
// This method handles the logic for CPUFast
    public CPUFast(String name, int k, int id,Game Game){
        super(name, k, id, Game);
    }

    /*
     * Choisi de play une card, en priorité des card distances
     */
// This method handles the logic for chooseCard
    public Card chooseCard(){
        ArrayList<Card> Hand = getHand();
        Card playedCard = null;
        boolean EnddDistance = false, endSafety = false, endGreenLight = false;

        //botte -> attaque -> parade -> distance

        for(Card card : Hand){
            if(card instanceof Boot){
                playedCard = card;
                break;
            }
            else if(card.getType() == TypeCard.GREEN_LIGHT && check(card, this, null)){
                playedCard = card;
                endGreenLight = true;
            }
            else if(card instanceof Safety && !endGreenLight && !endSafety && check(card, this, null)){
                playedCard = card;
                endSafety = true;
            }
            else if(card instanceof Distance && !endSafety && !endGreenLight && check(card, this, null)){
                if((playedCard instanceof Distance && card.getKilometers() > playedCard.getKilometers()) || !(playedCard instanceof Distance))
                {
                    playedCard = card;
                    EnddDistance = true;
                }
            }
            else if(card instanceof Attack && !EnddDistance && !endGreenLight && check(card, this, getTarget(card))){
                playedCard = card;
            }
        }

        return playedCard;
    }

    @Override
// This method handles the logic for discardChoice
    public Card discardChoice()
    {
        ArrayList<Card> Hand = getHand();
        Card cartdToDiscard = Hand.get(0);

        //botte -> distance -> parade -> attaque

        for(Card card : Hand){
            if(card instanceof Attack) 
            {
                cartdToDiscard = card;
            }
            else if(card instanceof Safety)
            {
                if(cartdToDiscard instanceof Distance || cartdToDiscard instanceof Boot)
                {
                    cartdToDiscard = card;
                }
            }
            else if(card instanceof Distance)
            {
                if(cartdToDiscard instanceof Distance && cartdToDiscard.getKilometers() > card.getKilometers())
                {
                    cartdToDiscard = card;
                }
            }
        }
        System.out.println(getName() + " décide de jeter la card " + cartdToDiscard.getName());
        return cartdToDiscard;
    }
}
