package main;

// This class represents Hand
public class main {
    
// This method handles the logic for Hand
    public static void main(String[] args){
        Game Game = new Game();
        WindowGame win = new WindowGame();
        new Controler(Game, win);
    }
}
