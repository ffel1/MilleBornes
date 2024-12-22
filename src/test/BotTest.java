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
    private Attaque ACCIDENT;
    private CPUFast botTestFAST;
    private CPUAgro botTestAGRO;
    private Utilisateur joueurTest;

    @BeforeEach
    public void init(){
        _25KM = new Distance(TypeCarte._25KM, 25);
        _50KM = new Distance(TypeCarte._50KM, 50);
        _75KM = new Distance(TypeCarte._75KM, 75);
        _100KM = new Distance(TypeCarte._100KM, 100);
        _200KM = new Distance(TypeCarte._200KM, 200);
        ACCIDENT = new Attaque(TypeCarte.ACCIDENT);
        botTestFAST = new CPUFast("fast", 0, 0, null);
        botTestAGRO = new CPUAgro("agro", 200, 0, null);
        joueurTest = new Utilisateur("joueur", 100, 0, null);

    }

    @Test
    public void choixCarte(){
        // Test choix de la carte
        botTestFAST.ajouterCarte(_25KM);
        botTestFAST.setFeuVert(true);
        assertEquals(_25KM, botTestFAST.choisirCarte());
        
    }

    @Test
    public void choisirCible(){
        // Test choix cible
        Partie partie = new Partie();
        partie.nouvellePartie();
        partie.getJoueur2().setKilometre(200);
        assertEquals(200, partie.getJoueur2().getKilometre());
        assertEquals(partie.getJoueur2(), partie.getJoueur3().getCible(ACCIDENT));
        
    }

}