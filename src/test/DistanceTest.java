package test;

import main.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Tests sur les cartes distances.
 */
public class DistanceTest{

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private Utilisateur source;

    @BeforeEach
    public void init(){
        _25KM = new Distance(TypeCarte._25KM, 25);
        _50KM = new Distance(TypeCarte._50KM, 50);
        _75KM = new Distance(TypeCarte._75KM, 75);
        _100KM = new Distance(TypeCarte._100KM, 100);
        _200KM = new Distance(TypeCarte._200KM, 200);
        source = new Utilisateur("Source", 0, 0, null);
    }

    @Test
    public void test25KM(){
        // Test de la carte
        assertEquals("Distance25_KM", _25KM.getNom());
        assertEquals(TypeCarte._25KM, _25KM.getType());
        assertEquals(25, _25KM.getKilometre());
        assertNotNull(_25KM.getImage());

        // Test utilisation de la carte
        source.jouerDistance(_25KM);
        assertEquals(source.verification(_25KM, source, source), false);
        assertEquals(source.getKilometre(), 0);
        source.setFeuVert(true);
        assertEquals(source.verification(_25KM, source, source), true);
        source.jouerDistance(_25KM);
        assertEquals(source.getKilometre(), 25);
        source.jouerDistance(_25KM);
        assertEquals(source.getKilometre(), 50);
    }

    @Test
    public void test50KM(){
        // Test de la carte
        assertEquals("Distance50_KM", _50KM.getNom());
        assertEquals(TypeCarte._50KM, _50KM.getType());
        assertEquals(50, _50KM.getKilometre());
        assertNotNull(_50KM.getImage());

        // Test utilisation de la carte
        source.jouerDistance(_50KM);
        assertEquals(source.verification(_50KM, source, source), false);
        assertEquals(source.getKilometre(), 0);
        source.setFeuVert(true);
        assertEquals(source.verification(_50KM, source, source), true);
        source.jouerDistance(_50KM);
        assertEquals(source.getKilometre(), 50);
        source.jouerDistance(_50KM);
        assertEquals(source.getKilometre(), 100);
    }

    @Test
    public void test75KM(){
        // Test de la carte
        assertEquals("Distance75_KM", _75KM.getNom());
        assertEquals(TypeCarte._75KM, _75KM.getType());
        assertEquals(75, _75KM.getKilometre());
        assertNotNull(_75KM.getImage());

        // Test utilisation de la carte
        source.jouerDistance(_75KM);
        assertEquals(source.verification(_75KM, source, source), false);
        assertEquals(source.getKilometre(), 0);
        source.setFeuVert(true);
        assertEquals(source.verification(_75KM, source, source), true);
        source.jouerDistance(_75KM);
        assertEquals(source.getKilometre(), 75);
        source.jouerDistance(_75KM);
        assertEquals(source.getKilometre(), 150);
    }

    @Test
    public void test100KM(){
        // Test de la carte
        assertEquals("Distance100_KM", _100KM.getNom());
        assertEquals(TypeCarte._100KM, _100KM.getType());
        assertEquals(100, _100KM.getKilometre());
        assertNotNull(_100KM.getImage());

        // Test utilisation de la carte
        source.jouerDistance(_100KM);
        assertEquals(source.verification(_100KM, source, source), false);
        assertEquals(source.getKilometre(), 0);
        source.setFeuVert(true);
        assertEquals(source.verification(_100KM, source, source), true);
        source.jouerDistance(_100KM);
        assertEquals(source.getKilometre(), 100);
        source.jouerDistance(_100KM);
        assertEquals(source.getKilometre(), 200);
    }

    @Test
    public void test200KM(){
        // Test de la carte
        assertEquals("Distance200_KM", _200KM.getNom());
        assertEquals(TypeCarte._200KM, _200KM.getType());
        assertEquals(200, _200KM.getKilometre());
        assertNotNull(_200KM.getImage());

        // Test utilisation de la carte
        source.jouerDistance(_200KM);
        assertEquals(source.verification(_200KM, source, source), false);
        assertEquals(source.getKilometre(), 0);
        source.setFeuVert(true);
        assertEquals(source.verification(_200KM, source, source), true);
        source.jouerDistance(_200KM);
        assertEquals(source.getKilometre(), 200);
        source.jouerDistance(_200KM);
        assertEquals(source.getKilometre(), 400);
    }
}