package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

// This class represents Controler
public class Controler 
{
// Private or protected member
    private Game modele;
// Private or protected member
    private WindowGame vue;
// Private or protected member
    private Sound soundList;
// Private or protected member
    private int turnDelay;
// Private or protected member
    private Sound secondarySoundList;

// This method handles the logic for Controler
    @SuppressWarnings("unused")
    public Controler(Game modele, WindowGame vue) 
    {
        this.modele = modele;
        this.vue = vue;
        soundList = new Sound();
        secondarySoundList = new Sound();
        turnDelay = 3000; // 3 secondes
        vue.createWindowMenu();

        vue.addActionbuttonPlay(e -> {
            playMainMusic(0);
            vue.getWindow().getContentPane().removeAll();
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.createWindowGame();
            newGame(false, false);
        });

        vue.addButtonActionSaves(e -> {
            vue.getWindow().getContentPane().removeAll();
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.createWindowSaves();
        });

        vue.addActionQuitButton(e -> System.exit(0));
    }

// This method handles the logic for getsoundList
    public Sound getsoundList(){
        return soundList;
    }

// Private or protected member
    @SuppressWarnings("unused")
    private void newGame(boolean b, boolean EndGameForcee){

        File file = new File("save.ser");
        boolean loadedGame;
        if(file.exists() && !b)
        {
            loadingSave(file);
        }
        
        if(!modele.loadedGame() || b){
            modele.newGame();
            loadedGame = false;
            modele.initGameName();
        }
        else
        {
            loadedGame = true;
        }

        
        String nameGame = modele.getnameGame();
        vue.setNameGame(nameGame);
        if(loadedGame)
        {
            vue.loadLogs();
        }

        vue.refreshAttacks(modele);
        vue.refreshBoots(modele);
        vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
        vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());

        //Button Menu Principal
        vue.addActionreturnButton(e -> {
            if(soundList.getSoundON()){
                stopMusic();
            }
            soundList = new Sound();
            secondarySoundList = new Sound();
            initsoundButton();
            if(modele.getPlayer1().getIsAttackingWith() != null)
            {
                modele.getPlayer1().getIsAttackingWith().setImageBack();
            }
            save();
            vue.getWindow().getContentPane().removeAll();
            vue.getWindow().repaint();
            vue.getWindow().revalidate();
            vue.createWindowMenu(); 
            
        }); 

        //Button Nouvelle Game 
        vue.addActionbuttonNewGame(e -> {
            vue.addMessage("Vous avez mis End à la round " + modele.getNumeroround() + ", les pts ne seront pas comptabilisés\n", true);
            vue.resettingKilometers();
            newRound(true, true);
        });

        //Button défausse
        vue.addActionbuttonDiscard(e -> {
            if(!modele.getPlayer1().getmyTurn())
            {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }
            else if(modele.getPlayer1().getIsAttacking())
            {
                vue.addMessage("Vous ne pouvez pas défausser, vous êtes en train d'attaquer !", false);
            }
            else if(modele.getPlayer1().getMustDraw())
            {
                vue.addMessage("Après avoir joué une botte vous devez draw\n", false);
            }
            else if(!modele.getPlayer1().getHasDraw())
            {
                vue.addMessage("Vous devez draw avant de défausser ! \n", false);
            }
            else if(modele.getPlayer1().getaJjoue())
            {
                vue.addMessage("Vous avez déjà joué une card, vous ne pouvez plus défausser ! \n", false);
            }
            else if(!modele.getPlayer1().HandFull())
            {
                vue.addMessage("Vous n'avez pas plus de 6 cards, vous ne pouvez pas défausser \n", false);
            }
            else if(!modele.getPlayer1().getdiscard())
            {
                vue.addMessage("Cliquez sur la card que vous voulez défausser ! \nCliquez à nouveau sur la draw pour annuler \n", false);
                modele.getPlayer1().setdiscard(true);
                vue.getdiscard().setText("Annuler");
            }
            else
            {
                vue.addMessage("Vous avez change d'avis \n", false);
                modele.getPlayer1().setdiscard(false);
                vue.getdiscard().setText("");
            }
            
        });

        //Button EndDemyTurn
        vue.addActionButtonEndDemyTurn(e -> {
            if(!modele.getPlayer1().getmyTurn())
            {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }
            else if(modele.getPlayer1().getdiscard())
            {
                vue.addMessage("Vous êtes en train de défausser, impossible de finir votre tour ! \n", false);
            }
            else if(modele.getPlayer1().getMustDraw())
            {
                vue.addMessage("Après avoir joué une botte vous devez drawr \n", false);
            }
            else if(modele.getPlayer1().getIsAttacking())
            {
                vue.addMessage("Vous ne pouvez pas finir votre tour, vous êtes en train d'attaquer ! \n", false);
            }
            else if(modele.getPlayer1().getaJjoue())
            {
                if(modele.getPlayer1().getHand().size() > 6)
                {
                    vue.addMessage("Vous devez défausser une card ! \n", false);
                }
                else
                {
                    vue.addMessage("\nC'est la fin de votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);
                    if(modele.winner() == modele.getPlayer1())
                    {
                        vue.addMessage("\n VOUS AVEZ GAGNE LA round !! BRAVO ! \n", true);
                        modele.endTheGame(vue);
                        newRound(true, false);
                        return;
                    }
                    vue.printCardsPlayer(modele.getPlayer1().getHand());
                    initButtonCards(modele.getPlayer1().getHand());
                    modele.getPlayer1().sethasPlayed(false);
                    modele.getPlayer1().setHasDraw(false);
                    modele.getPlayer1().myTurn(false);
                    modele.getPlayer1().setHasDiscard(false);
                    
                    vue.addMessage("\nEn attente du player Agro \n", true);
                    Timer chrono = new Timer();
                    chrono.schedule(new TimerTask(){
                        @Override
// This method handles the logic for run
                        public void run(){
                            modele.getPlayer2().botAction(getControler());
                            vue.refreshAttacks(modele);
                            vue.refreshBoots(modele);
                            if(modele.winner() != null || modele.getdraw().size() == 0)
                            {
                                if(modele.getdraw().size() == 0)
                                {
                                    vue.addMessage("La draw est vide ! \n", b);
                                }
                                modele.endTheGame(vue);
                                newRound(true, false);
                                return;
                            }
                            vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
                            vue.addMessage("\nEn attente du player Fast \n", true);
                            Timer chrono = new Timer();
                            chrono.schedule(new TimerTask(){
                                @Override
// This method handles the logic for run
                                public void run(){
                                    modele.getPlayer3().botAction(getControler());
                                    vue.refreshAttacks(modele);
                                    vue.refreshBoots(modele);
                                    if(modele.winner() != null || modele.getdraw().size() == 0)
                                    {
                                        if(modele.getdraw().size() == 0)
                                        {
                                            vue.addMessage("La draw est vide ! \n", b);
                                        }
                                        modele.endTheGame(vue);
                                        newRound(true, false);
                                        return;
                                    }
                                    vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());
                                    modele.getPlayer1().myTurn(true);
                                    vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                                    vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
                                    vue.refreshAttacks(modele);
                                    vue.refreshBoots(modele);
                                }
                            }, turnDelay);
                        }
                    }, turnDelay);
                }
            }
            else if(!modele.getPlayer1().getaJjoue() && modele.getPlayer1().getHand().size() <= 6)
            {
                if(!modele.getPlayer1().getHasDraw())
                {
                    vue.addMessage("Vous sautez votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);
                }
                else
                {
                    vue.addMessage("Vous ne jouez rien pendant ce tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n", true);
                }
                modele.getPlayer1().sethasPlayed(false);
                modele.getPlayer1().setHasDraw(false);
                modele.getPlayer1().myTurn(false);
                modele.getPlayer1().setHasDiscard(false);
                Timer chrono = new Timer();
                chrono.schedule(new TimerTask(){
                    @Override
// This method handles the logic for run
                    public void run(){
                        vue.addMessage("\nEn attente du player Agro\n", true);
                        modele.getPlayer2().botAction(getControler());
                        vue.refreshAttacks(modele);
                        vue.refreshBoots(modele);
                        if(modele.winner() != null || modele.getdraw().size() == 0)
                        {
                            if(modele.getdraw().size() == 0)
                            {
                                vue.addMessage("La draw est vide ! \n", b);
                            }
                            modele.endTheGame(vue);
                            newRound(true, false);
                            return;
                        }
                        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, getControler());
                        Timer chrono = new Timer();
                        chrono.schedule(new TimerTask(){
                            @Override
// This method handles the logic for run
                            public void run(){
                                vue.addMessage("\nEn attente du player Fast\n", true);
                                modele.getPlayer3().botAction(getControler());
                                vue.refreshAttacks(modele);
                                vue.refreshBoots(modele);
                                if(modele.winner() != null || modele.getdraw().size() == 0)
                                {
                                    if(modele.getdraw().size() == 0)
                                    {
                                        vue.addMessage("La draw est vide ! \n", b);
                                    }
                                    modele.endTheGame(vue);
                                    newRound(true, false);
                                    return;
                                }
                                vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, getControler());
                                modele.getPlayer1().myTurn(true);
                                vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                                vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, getControler());
                                vue.refreshAttacks(modele);
                                vue.refreshBoots(modele);
                            }
                        }, turnDelay);
                        
                    }
                }, turnDelay);
                
            }
            else
            {
                vue.addMessage("Vous ne pouvez pas Endir votre tour avec plus de 6 cards ! \n", true);
                if(modele.getPlayer1().getaJjoue())
                {
                    vue.addMessage("Vous devez défausser une card ! \n", true);
                }
                else
                {
                    vue.addMessage("Jouez ou défaussez une card ! \n", true);
                }
            }
        });

        //Button AttackBotAgro
        vue.addActionButtonCPUAgro(e -> {
            joueMusic(1);
            if(modele.getPlayer1().getmyTurn() && modele.getPlayer1().getIsAttacking())
            {
                modele.getPlayer1().settarget(modele.getPlayer2());
                if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 0)
                {
                    modele.getPlayer1().playCard(modele.getPlayer1().getIsAttackingWith(),getControler(),0);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 1)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer le CPU " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il a une botte qui le protège de cette attaque !\n", false);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 2)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il subit déjà cette attaque !\n", false);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 7)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il n'a même pas encore mit de feu vert !\n", false);
                }
                modele.getPlayer1().getIsAttackingWith().setImageBack();
                vue.effacerCardsPlayers();
                vue.printCardsPlayer(modele.getPlayer1().getHand());
                initButtonCards(modele.getPlayer1().getHand());
                modele.getPlayer1().setIsAttacking(false);
                modele.getPlayer1().setIsAttackingWith(null);
                modele.getPlayer1().settarget(null); 
            }
            else if(!(modele.getPlayer1().getIsAttacking()))
            {
                vue.addMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            else
            {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }

            vue.refreshAttacks(modele);
            vue.refreshBoots(modele);
        });

        //Button AttackBotFast
        vue.addActionButtonCPUFast(e -> {
            joueMusic(1);
            if(modele.getPlayer1().getmyTurn() && modele.getPlayer1().getIsAttacking())
            {
                modele.getPlayer1().settarget(modele.getPlayer3());
                if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 0)
                {
                    modele.getPlayer1().playCard(modele.getPlayer1().getIsAttackingWith(),getControler(),0);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 1)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer le CPU " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il a une botte qui le protège de cette attaque !\n", false);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 2)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il subit déjà cette attaque !\n", false);
                }
                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getIsAttackingWith(), modele.getPlayer1(), modele.getPlayer1().getTarget(null)) == 7)
                {
                    vue.addMessage("Vous ne pouvez pas attaquer " + modele.getPlayer1().getTarget(null).getName() + " avec " + modele.getPlayer1().getIsAttackingWith().getName() + " car il n'a même pas encore mit de feu vert !\n", false);
                }
                modele.getPlayer1().getIsAttackingWith().setImageBack();
                vue.effacerCardsPlayers();
                vue.printCardsPlayer(modele.getPlayer1().getHand());
                initButtonCards(modele.getPlayer1().getHand());
                modele.getPlayer1().setIsAttacking(false);
                modele.getPlayer1().setIsAttackingWith(null);
                modele.getPlayer1().settarget(null);
            }
            else if(!(modele.getPlayer1().getIsAttacking()))
            {
                vue.addMessage("Vous n'êtes pas en train d'attaquer ! \n", false);
            }
            else
            {
                vue.addMessage("Ce n'est pas votre tour ! \n", false);
            }

            vue.refreshAttacks(modele);
            vue.refreshBoots(modele);
        });

        //Button draw 
        vue.addActionbuttonDraw(e -> {
            if(modele.getPlayer1().getMustDraw())
            {
                modele.getPlayer1().setmustDraw(false);
                boolean dejHasDraw = modele.getPlayer1().getHasDraw();
                modele.getPlayer1().draw();
                modele.getPlayer1().setHasDraw(dejHasDraw);
                vue.printCardsPlayer(modele.getPlayer1().getHand());
                initButtonCards(modele.getPlayer1().getHand());
            }
            else if(modele.getPlayer1().getIsAttacking())
            {
                vue.addMessage("Vous êtes en train d'attaquer, vous ne pouvez pas draw ! \n", false);
            }
            else if(modele.getPlayer1().getmyTurn() && !modele.getPlayer1().getHasDraw())
            {
                vue.addMessage("Vous avez pioché", true);
                if(modele.getPlayer1().HandFull())
                {
                    vue.addMessage(" mais votre Hand est Full ! \n", false);
                }
                else
                {
                    vue.addMessage("\n", true);
                    modele.getPlayer1().draw();
                    vue.printCardsPlayer(modele.getPlayer1().getHand());
                    initButtonCards(modele.getPlayer1().getHand());
                }
            }
            else if(modele.getPlayer1().getHasDraw())
            {
                vue.addMessage(" Vous avez déjà pioché ! \n", false);
            }
            else
            {
                vue.addMessage(" Ce n'est pas votre tour \n", false);
            }

            if(modele.getdraw().size() == 0)
            {
                vue.addMessage("La draw est vide !", true);
                modele.endTheGame(vue);
                newRound(true, false);
                return;
            }
        }); 

        vue.addActionsoundButton(e -> {
            if(secondarySoundList.getSoundON()){
                secondarySoundList.setSoundON(false);
            }else{
                secondarySoundList.setSoundON(true);
            }
            stopMusic();
            vue.changeImageSound();
        });

        ArrayList<Card> Hand = modele.getPlayer1().getHand();
        vue.printCardsPlayer(Hand);
        initButtonCards(Hand);
        if(EndGameForcee)
        {
            vue.addMessage("Vous avez arrêté la round " + (modele.getNumeroround() - 1) + ", les pts ne seront pas comptabilisés ! \n", true);
        }
        if(loadedGame)
        {
            vue.addMessage("La round " + modele.getNumeroround() + " reprends, les score soundt de : \n", false);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " pts ! \n", false);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " pts ! \n", false);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " pts ! \n", false);
            vue.addMessage("C'est à vous de play ! \n", false);
            modele.getPlayer1().myTurn(true);
        }
        else if(modele.getwhoStart() == 0)
        {
            vue.addMessage("Début de la round " + modele.getNumeroround() +" ! Les score soundt de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " pts ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " pts ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " pts ! \n", true);
            vue.addMessage("Les participants soundt : \n", true);
            for(int i = 0; i < modele.getPlayers().size(); i++)
            {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("Vous commencez à play \n", true);
            vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
            modele.getPlayer1().myTurn(true);
        }
        else if(modele.getwhoStart() == 1)
        {
            vue.addMessage("Début de la round " + modele.getNumeroround() +" ! Les score soundt de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " pts ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " pts ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " pts ! \n", true);
            vue.addMessage("Les participants soundt : \n", true);
            for(int i = 0; i < modele.getPlayers().size(); i++)
            {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("CPU Agro commence à play ! \n", true);
            vue.addMessage("\nEn attente du player Agro\n", true);
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
                @Override
// This method handles the logic for run
                public void run(){
                    modele.getPlayer2().botAction(getControler());
                    vue.refreshAttacks(modele);
                    vue.refreshBoots(modele);
                    vue.addMessage("\nEn attente du player Fast\n", true);
                    Timer chrono = new Timer();
                    chrono.schedule(new TimerTask(){
                    @Override
// This method handles the logic for run
                    public void run(){
                        modele.getPlayer3().botAction(getControler());
                        modele.getPlayer1().myTurn(true);
                        vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                        vue.refreshAttacks(modele);
                        vue.refreshBoots(modele);
                        }
                    }, turnDelay);
                }
            }, turnDelay);
        }
        else if(modele.getwhoStart() == 2)
        {
            vue.addMessage("Début de la round " + modele.getNumeroround() +" ! Les score soundt de : \n", true);
            vue.addMessage("Vous avez " + modele.getPtsPlayer() + " pts ! \n", true);
            vue.addMessage("Le CPU Agro a  " + modele.getPointCPUAgro() + " pts ! \n", true);
            vue.addMessage("Le CPU Fast a " + modele.getPtsCPUFast() + " pts ! \n", true);
            vue.addMessage("Les participants soundt : \n", true);
            for(int i = 0; i < modele.getPlayers().size(); i++)
            {
                vue.addMessage("- " + modele.getPlayers().get(i).getName() + "\n", true);
            }
            vue.addMessage("CPU Fast commence à play ! \n", true);
            vue.addMessage("\nEn attente du player Fast\n", true);
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
            @Override
// This method handles the logic for run
            public void run(){
                modele.getPlayer3().botAction(getControler());
                modele.getPlayer1().myTurn(true);
                vue.addMessage("\nC'est votre tour ! Distance parcourue : " + modele.getPlayer1().getKilometers() + " km \n\n\n\n", true);
                vue.refreshAttacks(modele);
                vue.refreshBoots(modele);
                }
            }, turnDelay);
        }
        vue.getWindow().revalidate();
        vue.getWindow().repaint();
    }

    //Initialisation cards
// This method handles the logic for initButtonCards
    public void initButtonCards(ArrayList<Card> Hand)
    {
        for(int i = 0; i < Hand.size(); i++){
            int j = i;
            vue.addActionButtonCard(new ActionListener() {
                @Override
// This method handles the logic for actionPerformed
                public void actionPerformed(ActionEvent e){
                    //Pour faire play le player
                    if(modele.getPlayer1().getmyTurn())                           
                    {
                        if(!modele.getPlayer1().getHasDraw())
                        {
                            vue.addMessage("Vous devez d'abord draw pour play une card \n", false);
                        }
                        else if(modele.getPlayer1().getIsAttacking() && modele.getPlayer1().getHand().get(j) == modele.getPlayer1().getIsAttackingWith())
                        {
                            vue.addMessage("Vous avez changé d'avis \n", false);
                            modele.getPlayer1().getIsAttackingWith().setImageBack();
                            vue.effacerCardsPlayers();
                            vue.printCardsPlayer(Hand);
                            initButtonCards(Hand);
                            modele.getPlayer1().setIsAttacking(false);
                            modele.getPlayer1().setIsAttackingWith(null);
                            modele.getPlayer1().settarget(null);
                        }
                        else if(modele.getPlayer1().getIsAttacking())
                        {
                            vue.addMessage("Vous êtes en train d'attaquer, vous ne pouvez pas play de cards ! \n", false);
                        }
                        else if(modele.getPlayer1().getMustDraw())
                        {
                            vue.addMessage("Après avoir joué une botte vous devez draw\n", false);
                        }
                        else if(modele.getPlayer1().gethasDiscard())
                        {
                            vue.addMessage("Vous ne pouvez plus play après avoir défaussé\n", false);
                        }
                        else if(modele.getPlayer1().getaJjoue() && !(modele.getPlayer1().getHand().get(j) instanceof Boot))
                        {
                            vue.addMessage("Vous avez déjà joué lors de votre tour \n", false);
                        }
                        else if(modele.getPlayer1().getdiscard())
                        {
                            vue.addMessage(modele.getPlayer1().discard(modele.getPlayer1().getHand().get(j),getControler()), true);
                            modele.getPlayer1().setHasDiscard(true);
                            vue.getdiscard().setText("");
                            modele.getPlayer1().setdiscard(false);
                        }
                        else
                        {
                            if(modele.getPlayer1().getHand().get(j) instanceof Boot)
                            {
                                modele.getPlayer1().playCard(modele.getPlayer1().getHand().get(j),getControler(),j+1);
                                vue.addMessage("Vous devez draw à nouveau ! \n", false);
                                modele.getPlayer1().setmustDraw(true);
                            }
                            else if(modele.getPlayer1().getHand().get(j) instanceof Attack)
                            {
                                vue.addMessage("Choisissez le CPU sur lequel vous voulez lancer votre attaque \n", false);
                                modele.getPlayer1().setIsAttacking(true);
                                modele.getPlayer1().setIsAttackingWith(modele.getPlayer1().getHand().get(j));
                                modele.getPlayer1().getHand().get(j).setImageIcon(null);
                                vue.effacerCardsPlayers();
                                vue.printCardsPlayer(Hand);  
                                initButtonCards(modele.getPlayer1().getHand());                                         
                            }
                            else
                            {
                                if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 0)
                                {
                                    modele.getPlayer1().playCard(Hand.get(j),getControler(),j+1);
                                }
                                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 10)
                                {
                                    vue.addMessage("Vous devez atteindre PILE 700 bornes pour gagner, cette card vous fait aller trop loin ! \n", false);
                                }
                                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 3)
                                {
                                    vue.addMessage("Vous ne pouvez pas moveForwad sans feu vert ! \n", false);
                                }
                                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 4)
                                {
                                    String message = modele.getPlayer1().playDistance(modele.getPlayer1().getHand().get(j));
                                    vue.addMessage(message, false);
                                }
                                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 5)
                                {
                                    vue.addMessage("Vous avez déjà un feu vert ! \n", false);
                                }
                                else if(modele.getPlayer1().checkUser(modele.getPlayer1().getHand().get(j), modele.getPlayer1(), null) == 6)
                                {
                                    vue.addMessage("Vous ne subissez aucune attaque que cette parade permet de contrer !\n", false);
                                }
                            }
                        }
                        vue.refreshAttacks(modele);
                        vue.refreshBoots(modele);
                    }
                    else
                    {
                        vue.addMessage("Ce n'est pas votre tour ! \n", false);
                    }
                }
            }, i);
        }
        vue.getWindow().setLayout(null);
		vue.getWindow().setVisible(true);


        vue.moveForwadCar(modele.getPlayer1().getKilometers(), 0, this);
        vue.moveForwadCar(modele.getPlayer2().getKilometers(), 1, this);
        vue.moveForwadCar(modele.getPlayer3().getKilometers(), 2, this);
    }

// This method handles the logic for newRound
    public void newRound(boolean b1, boolean b2)
    {
        modele.getPlayer1().setdiscard(false);
        modele.getPlayer1().myTurn(false);

        vue.getdiscard().setText("");
        vue.getWindow().getContentPane().removeAll();
        vue.getWindow().repaint();
        vue.getWindow().revalidate();
        vue.createWindowGame();
        if(modele.getwinnerOfTheGame() != null)
        {
            if(modele.getwinnerOfTheGame().getId() == modele.getPlayer1().getId())
            {
                int i = 0;
                vue.addMessage("VOUS AVEZ ACCUMULE 5000 pts!!!\n", true);
                vue.addMessage("VOUS AVEZ GAGNE !\n", true);
                while(i < 500)
                {
                    vue.addMessage("VOUS AVEZ ACCUMULE 5000 pts!!!\n", false);
                    vue.addMessage("VOUS AVEZ GAGNE !\n", false);
                    i++;
                }
            }
            else
            {
                vue.addMessage("Le CPU " + modele.getwinnerOfTheGame().getName() + " a accumulé 5000 pts...", true);
                vue.addMessage("Vous avez perdu cette Game...", true);
            }
            saveManagement();
            modele = new Game();
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
            @Override
            // This method handles the logic for run
            public void run(){
                vue.clearConsole();
                newGame(true, false);
                }
            }, turnDelay);
        }
        else if(b2)
        {
            vue.addMessage("Vous avez arrêté la round, les pts gagnés\nne seront pas comptabilisés \n\n\n", false);
            vue.addMessage("Chargement de la prochaine round...\n", false);
            vue.resettingKilometers();
            modele.getPlayers().clear();
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
            @Override
            public void run(){
                newGame(b1, b2);
                }
            }, turnDelay*2);
        }
        else
        {
            if(modele.getdraw().size() == 0)
            {
                vue.addMessage("La draw s'est vidé :\n", false);
                vue.addMessage("Le winner est donc celui qui est allé le plus loin...\n", false);
            }
            if(modele.theLeadingPlayer().getId() == modele.getPlayer1().getId())
            {
                vue.addMessage("VOUS AVEZ GAGNE CETTE round, BRAVO !! \n\n\n\n", false);
            }
            else
            {
                vue.addMessage("Dommage, c'est le CPU " + modele.theLeadingPlayer().getName() + " qui a gagné cette round, bravo !! \n\n\n\n", false);
            }
            vue.addMessage("Chargement de la Game suivante...\n\n\n", false);
            modele.getPlayers().clear();
            Timer chrono = new Timer();
            chrono.schedule(new TimerTask(){
            @Override
// This method handles the logic for run
            public void run(){
                newGame(true, false);
                }
            }, turnDelay);
        }
    }

// This method handles the logic for saveManagement
    public void saveManagement()
    {
        String directoryPath = "SauvegardeDesHistoriques";
        String outputFile;
        File file;
        int i = 1;

        file = new File("SauvegardeDesHistoriques/Game_" + i+".txt");
        while(file.exists())
        {
            i++;
            file = new File("SauvegardeDesHistoriques/Game_" + i+".txt");
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        outputFile = "SauvegardeDesHistoriques/Game_"+i+".txt";

        try (
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))
        ) {
            // Parcourir les files du répertoire
            Files.list(Paths.get(directoryPath))
                .filter(path -> path.getFileName().toString().startsWith("round") && path.getFileName().toString().endsWith(".txt"))
                .forEach(fileBis -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileBis.toFile()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.newLine();
                        }
                        System.out.println("Copié : " + fileBis.getFileName());
                        
                        // Delete le file après copie
                        Files.delete(fileBis);
                        System.out.println("Supprimé : " + fileBis.getFileName());
                    } catch (IOException e) {
                        System.err.println("Erreur avec le file " + fileBis.getFileName() + ": " + e.getMessage());
                    }
                });

            System.out.println("Fusion terminée dans le file : " + outputFile);
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }

// This method handles the logic for getControler
    public Controler getControler()
    {
        return this;
    }

// This method handles the logic for getModel
    public Game getModel()
    {
        return modele;
    }
// This method handles the logic for getVue
    public WindowGame getVue()
    {
        return vue;
    }

// Private or protected member
    private void save(){
        try (FileOutputStream file = new FileOutputStream("save.ser", false); ObjectOutputStream oos = new ObjectOutputStream(file)){
            oos.writeObject(modele);
            System.out.println("Sauvegarde OK !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde : " + e.getMessage());
        }
    }

// Private or protected member
    private void loadingSave(File file) {

            try (FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileIn)) {
            modele = (Game) ois.readObject();
            modele.getPlayer1().setdiscard(false);
            vue.getdiscard().setText("");
            modele.getPlayer1().setIsAttacking(false);
            System.out.println(modele.getdraw().size());
            if (modele != null && modele.getPlayer1() != null) {
                System.out.println("Première card de la Hand : " + modele.getPlayer1().getHand().get(0));
            } else {
                System.err.println("Les données chargées soundt incomplètes ou corrompues.");
            }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur lors du chargement : ");
                e.printStackTrace();
            }
    }

// Private or protected member
    private void playMainMusic(int i){
        soundList.setFile(i);
        soundList.play(i);
    }

// Private or protected member
    private void joueMusic(int i){
        if(i != 0){
            secondarySoundList.setFile(i);
            secondarySoundList.play(i);
        }
    }

// Private or protected member
    @SuppressWarnings("unused")
    private void playContiniouselyMusic(int i){
        soundList.setFile(i);
        soundList.play(i);
        soundList.loop();
    }

// This method handles the logic for stopMusic
    public void stopMusic(){
        soundList.stop(0);
    }

// This method handles the logic for initsoundButton
    @SuppressWarnings("unused")
    public void initsoundButton(){
        vue.addActionsoundButton(e -> {
            if(secondarySoundList.getSoundON()){
                secondarySoundList.setSoundON(false);
            }else{
                secondarySoundList.setSoundON(true);
            }
            stopMusic();
            vue.changeImageSound();
        });
    }
}