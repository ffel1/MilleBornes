package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes distances.
 */
public class BotTest{

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private CPUFast botTest;

    @BeforeEach
    public void init(){
        _25KM = new Distance(TypeCarte._25KM, 25);
        _50KM = new Distance(TypeCarte._50KM, 50);
        _75KM = new Distance(TypeCarte._75KM, 75);
        _100KM = new Distance(TypeCarte._100KM, 100);
        _200KM = new Distance(TypeCarte._200KM, 200);
        botTest = new CPUFast("Source", 0, 0, null);

    }

    @Test
    public void Jouer25KM(){
        // Test utilisation de la carte
        botTest.ajouterCarte(_25KM);
        assertEquals(_25KM, botTest.choisirCarte());
        
    }
}