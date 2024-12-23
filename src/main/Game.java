package main;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

 
// This class represents Game
public class Game implements Serializable{
    private int whoStart;
    private ArrayList<Player> players;
    private ArrayList<Card> draw;
    private String nameGame;
    private int ptsPlayer;
    private int ptsCPUFast;
    private int ptsCPUAgro;
    private int roundNumber;
    private Player winnerOfTheGame;

// This method handles the logic for Game
    public Game(){
        // Initialisation
        players = new ArrayList<Player>();
    }
    
// This method handles the logic for initGameName
    public void initGameName()
    {
        File file;
        int i = 1;

        File directory = new File("SauvegardeDesHistoriques");
        if(!directory.exists())
        {
            directory.mkdir();
        }

        file = new File("SauvegardeDesHistoriques/round_" + i+".txt");
        while(file.exists())
        {
            i++;
            file = new File("SauvegardeDesHistoriques/round_" + i+".txt");
        }

        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        nameGame = "round_"+i+".txt";
    }

// This method handles the logic for getnameGame
    public String getnameGame()
    {
        return nameGame;
    }
    /*
     * Initialisation de la draw #EndI
     */
// This method handles the logic for initDraw
    public void initDraw(){
        draw = new ArrayList<Card>();

        //voitureds Distances
        for(int i = 0; i < 12; i++){
            if(i < 10){
                draw.add(new Distance(TypeCard._25KM, 25));
                draw.add(new Distance(TypeCard._50KM, 50));
                draw.add(new Distance(TypeCard._75KM, 75));

                if(i < 4){
                    draw.add(new Distance(TypeCard._200KM, 200));
                }
            }
            draw.add(new Distance(TypeCard._100KM, 100));
        }

        //voitureds Attacks
        for(int i = 0; i < 5; i++){
            if(i < 4){
                draw.add(new Attack(TypeCard.SPEED_LIMITATION));

                if(i < 3){
                    draw.add(new Attack(TypeCard.ACCIDENT));
                    draw.add(new Attack(TypeCard.OUT_OF_FUEL));
                    draw.add(new Attack(TypeCard.FLAT_TIRE));
                }
            }
            draw.add(new Attack(TypeCard.RED_LIGHT));
        }

        //voitureds Safetys
        for(int i = 0; i < 14; i++){
            if(i < 6){
                draw.add(new Safety(TypeCard.REPAIR));
                draw.add(new Safety(TypeCard.FUEL));
                draw.add(new Safety(TypeCard.SPARE_WHEEL));
                draw.add(new Safety(TypeCard.END_SPEED_LIMITATION));
            }
            draw.add(new Safety(TypeCard.GREEN_LIGHT));
        }

        //voitureds Boots
        draw.add(new Boot(TypeCard.EXPERT_DRIVER));
        draw.add(new Boot(TypeCard.TANK_TRUCK));
        draw.add(new Boot(TypeCard.PUNCTURE_PROOF));
        draw.add(new Boot(TypeCard.PRIOTIRY_VEHICLE));

        //Melanger la draw
        Collections.shuffle(draw);
    }
    
// This method handles the logic for sizeDraw
    public int sizeDraw(){
        return draw.size();
    }

// This method handles the logic for getwhoStart
    public int getwhoStart()
    {
        return whoStart;
    }

// This method handles the logic for getdraw
    public ArrayList<Card> getdraw(){
        return draw;
    }

// This method handles the logic for loadedGame
    public boolean loadedGame(){
        return !players.isEmpty();
    }

// This method handles the logic for getPlayers
    public ArrayList<Player> getPlayers()
    {
        return players;
    }

// This method handles the logic for setPlayers
    public void setPlayers(Player player, int i)
    {
        players.set(i, player);
    }

// This method handles the logic for getPlayer1
    public User getPlayer1(){
        return (User)players.get(0);
    }

// This method handles the logic for getPlayer2
    public CPU getPlayer2(){
        return (CPU)players.get(1);
    }

// This method handles the logic for getPlayer3
    public CPU getPlayer3(){
        return (CPU)players.get(2);
    }

    /*
     * Création de la Game
     * PAS EndI
     */
// This method handles the logic for newGame
    public void newGame(){
        roundNumber++;
        initDraw();
        players.clear();
        System.out.println(getdraw().size());
        players.add(0, new User("Vous", 0, 0, this));
        players.add(1, new CPUAgro("Agro", 0, 1, this));
        players.add(2, new CPUFast("Fast", 0, 2, this));
        for(int i = 0; i < 6; i++){
            players.get(0).addCard(draw.get(i));
            draw.remove(i);
            players.get(1).addCard(draw.get(i));
            draw.remove(i);
            players.get(2).addCard(draw.get(i));
            draw.remove(i);
        }
        System.out.println(players.get(0).getHand().size());
        System.out.println("Il y a " + players.size() + " players !");
        System.out.println(getdraw().size());

        Random r = new Random();
        whoStart = r.nextInt(3);
    }

    /*
     * Verifie si la card est jouable
     * PAS EndI
     */
// This method handles the logic for playCard
    public boolean playCard(Card c, Player u, Player target){
        return true;
    }

    /*
     * Renvoie vrai si un player a gagné a au moins 700 km
     * PAS EndI
     */
// This method handles the logic for winner
    public Player winner(){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getKilometers() >= 700){
                return players.get(i);
            }
        }
        return null;
    }

// This method handles the logic for endTheGame
    public void endTheGame(WindowGame vue) {    
        countingpts();
    }

// This method handles the logic for getPtsPlayer
    public int getPtsPlayer()
    {
        return ptsPlayer;
    }

// This method handles the logic for getPointCPUAgro
    public int getPointCPUAgro()
    {
        return ptsCPUAgro;
    }

// This method handles the logic for getPtsCPUFast
    public int getPtsCPUFast()
    {
        return ptsCPUFast;
    }

// This method handles the logic for getNumeroround
    public int getNumeroround()
    {
        return roundNumber;
    }

// This method handles the logic for countingpts
    public void countingpts()
    {
        Player winner = winner();
        // pts de distance
        ptsPlayer += players.get(0).getKilometers();
        ptsCPUFast += players.get(1).getKilometers();
        ptsCPUAgro += players.get(2).getKilometers();

        // Bonus "Capot"
        if (players.get(1).getKilometers() == 0) {
            ptsPlayer += 500;
            ptsCPUAgro += 500; 
        }
        if (players.get(2).getKilometers() == 0) {
            ptsPlayer += 500;
            ptsCPUFast += 500;
        }
        if (players.get(0).getKilometers() == 0) {
            ptsCPUFast += 500;
            ptsCPUAgro += 500;
        }

        // pts de bottes 
        int nbBootsPlayer = players.get(0).getPlayedBoots().size();
        int nbBootsCPUFast = players.get(1).getPlayedBoots().size();
        int nbBootsCPUAgro = players.get(2).getPlayedBoots().size();

        if ( nbBootsPlayer == 4){
            ptsPlayer += 700; // pts de bottes posées
        }else{
            ptsPlayer += nbBootsPlayer * 100; // pts de bottes posées
        }

        if ( nbBootsCPUFast == 4){
            ptsCPUFast += 700; // pts de bottes posées
        }else{
            ptsCPUFast += nbBootsCPUFast * 100; // pts de bottes posées
        }

        if ( nbBootsCPUAgro == 4){
            ptsCPUAgro += 700; // pts de bottes posées
        }else{
            ptsCPUAgro += nbBootsCPUAgro * 100; // pts de bottes posées
        }
        
        // pts de coup fourré
        ptsPlayer += players.get(0).getdirtyTricks() * 300;
        ptsCPUFast += players.get(2).getdirtyTricks() * 300;
        ptsCPUAgro += players.get(1).getdirtyTricks() * 300;

        // pts de victoire
        if(winner == null)
        {
            winner = getPlayer1();
            for(Player player : players)
            {
                if(player.getKilometers() > winner.getKilometers())
                {
                    winner = player;
                }
            }
        }
        if (winner.getId() == 0){

            ptsPlayer += 400; // pts de round gagnée

            if (draw.isEmpty()){
                ptsPlayer += 300; // pts de couronnement
            }
            if (players.get(0).use200KM() == false){
                ptsPlayer += 300; // pts de aucun 200KM
            }

        }
        else if (winner == players.get(1)){
                
            ptsCPUFast += 400; // pts de round gagnée
            
            if (draw.isEmpty()){
                ptsCPUFast += 300; // pts de couronnement
            }
            if (players.get(1).use200KM() == false){
                ptsCPUFast += 300; // pts de aucun 200KM
            }
        }
        else if (winner == players.get(2)){
        
            ptsCPUAgro += 400; // pts de round gagnée
            
            if (draw.isEmpty()){
                ptsCPUAgro += 300; // pts de couronnement
            }
            if (players.get(2).use200KM() == false){
                ptsCPUAgro += 300; // pts de aucun 200KM
            }
        }
        getPlayer1().setpts(getPtsPlayer());
        getPlayer2().setpts(getPointCPUAgro());
        getPlayer3().setpts(getPtsCPUFast());
        Player localWinner = getPlayer1();
        for(Player player : players)
        {
            if(player.getPts() > localWinner.getPts())
            {
                localWinner = player;
            }
        }
        if(localWinner.getPts() > 5000)
        {
            winnerOfTheGame = localWinner;
        }
    }

// This method handles the logic for theLeadingPlayer
    public Player theLeadingPlayer()
    {
        Player winner = getPlayer1();
        for(Player player : getPlayers())
        {
            if(player.getKilometers() > winner.getKilometers())
            {
                winner = player;
            }
        }

        return winner;
    }

// This method handles the logic for getwinnerOfTheGame
    public Player getwinnerOfTheGame()
    {
        return winnerOfTheGame;
    }
}