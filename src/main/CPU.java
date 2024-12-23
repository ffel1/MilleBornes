package main;

import java.util.Random;

// This class represents CPU
public abstract class CPU extends Player{
    
// Private or protected member
    private Player target;

// This method handles the logic for CPU
    public CPU(String name, int k, int id, Game Game){
        super(name, k, id, Game);
    }

// This method handles the logic for botAction
    public void botAction(Controler Controler) //le boolean sert à gérer le cas ou on pose une botte (on rejoue sans Print nouveau tour)
    {
        Controler.getVue().addMessage("\nC'est au tour du CPU " + getName() + " ! Distance parcourue : " + getKilometers() + " km \n", true);
        draw();
        Controler.getVue().addMessage("Le CPU " + getName() + " a pioché ! \n", true);

        Card playedCard = chooseCard();

        if(playedCard == null)
        {
            Controler.getVue().addMessage("Le CPU " + getName() + " ne peut pas play ! \n", true);
            discard(discardChoice(), Controler);
        }
        else
        {
            while (playedCard instanceof Boot) 
            {
                Controler.getVue().addMessage(playCard(playedCard, target), true);
                draw();
                Controler.getVue().addMessage("Le CPU " + getName() + " a encore pioché ! \n", true);
                playedCard = chooseCard();
                if(playedCard == null)
                {
                    Controler.getVue().addMessage("Le CPU " + getName() + " ne peut pas play !\n", true);
                    discard(discardChoice(), Controler);
                }
            }
            Controler.getVue().addMessage(playCard(playedCard, target), true);
            Controler.getVue().addMessage("C'est la End du tour de " +  getName() + " ! Distance parcourue : " + getKilometers() + " km \n", true);
            if(Controler.getModel().winner() == this)
            {
                Controler.getVue().addMessage( "\n Le CPU "  + getName() + " a gagné... La prochaine fois peut être... \n", true);
            }
        }

        Controler.getVue().refreshAttacks(getGame());
        Controler.getVue().refreshBoots(getGame());
        
    }

// This method handles the logic for discardChoice
    public abstract Card discardChoice();

// This method handles the logic for discard
    public String discard(Card c,Controler Controler)
    {
        Controler.getVue().addMessage("Le CPU " + getName() + " défausse la card :" + c.getName() +" \n", true);
        getHand().remove(c);
        return null;
    }
// This method handles the logic for chooseCard
    public abstract Card chooseCard();

// This method handles the logic for setCurrenttarget
    public void setCurrenttarget(Player c)
    {
        target = c;
    }
// This method handles the logic for getCurrenttarget
    public Player getCurrenttarget()
    {
        return target;
    }

// This method handles the logic for getTarget
    public Player getTarget(Card c)
    {
        Player actualWinner = getGame().getPlayer1();
        for(Player j : getGame().getPlayers())
        {
            if(j.getKilometers() > actualWinner.getKilometers() && j.getId() != getId())
            {
                actualWinner = j;
            }
            else if(j.getKilometers() == actualWinner.getKilometers() && j.getId() != getId())
            {
                Random r = new Random();
                int i = r.nextInt(2);
                if(i == 0)
                {
                    actualWinner = j;
                }
            }
        }
        if(check(c, this, actualWinner))
        {
            target = actualWinner;
            return actualWinner;
        }
        else
        {
            for(Player j : getGame().getPlayers())
            {
                if(j.getId() != getId() && j.getId() != actualWinner.getId()&& check(c, this, j))
                {
                    target = j;
                    return j;
                }
            }
        }

        return null;
    }
}
