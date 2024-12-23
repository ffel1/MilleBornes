package main;
import java.util.ArrayList;

// This class represents CPUAgro
public class CPUAgro extends CPU{

// This method handles the logic for CPUAgro
    public CPUAgro(String name, int k, int id, Game Game){
        super(name, k, id, Game);
    }

    /*
     * Choisi de play une card, en priorité des card attaques
     */
// This method handles the logic for chooseCard
    public Card chooseCard(){
        ArrayList<Card> Hand = getHand();
        Card playedCard = null;
        boolean EnddAttack = false, endSafety = false, endGreenLight = false;

        //botte -> attaque -> parade -> distance

        for(Card card : Hand){
            if(card instanceof Boot){
                playedCard = card;
                break;
            }
            else if(card instanceof Attack && !EnddAttack && check(card, this, getTarget(card))){
                playedCard = card;
                EnddAttack = true;
            }
            else if (card.getType() == TypeCard.GREEN_LIGHT && !EnddAttack && check(card, this, null)) 
            {
                playedCard = card;
                endGreenLight = true;
            }
            else if(card instanceof Safety && !EnddAttack && !endGreenLight && !endSafety && check(card, this, null)){
                playedCard = card;
                endSafety = true;
            }
            else if(card instanceof Distance && !EnddAttack && !endSafety && !endGreenLight && check(card, this, null)){
                if((playedCard instanceof Distance && card.getKilometers() > playedCard.getKilometers()) || !(playedCard instanceof Distance))
                {
                    playedCard = card;
                }
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

        //botte -> attaque -> parade -> distance

        for(Card card : Hand){
            if(card instanceof Distance){
                if(!(cartdToDiscard instanceof Distance))
                {
                    cartdToDiscard = card;
                }
                else if(cartdToDiscard instanceof Distance && cartdToDiscard.getKilometers() > card.getKilometers())
                {
                    cartdToDiscard = card;
                }
            }
            else if(card instanceof Safety){
                if(cartdToDiscard instanceof Attack || cartdToDiscard instanceof Boot)
                {
                    cartdToDiscard = card;
                }
            }
            else if (card instanceof Attack) 
            {
                if(cartdToDiscard instanceof Boot)
                {
                    cartdToDiscard = card;
                }
            }
        }
        return cartdToDiscard;
    }
}
