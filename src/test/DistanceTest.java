package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/*
 * Tests sur les cards distances.
 */
public class DistanceTest{

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private User source;

    @BeforeEach
    public void init(){
        _25KM = new Distance(TypeCard._25KM, 25);
        _50KM = new Distance(TypeCard._50KM, 50);
        _75KM = new Distance(TypeCard._75KM, 75);
        _100KM = new Distance(TypeCard._100KM, 100);
        _200KM = new Distance(TypeCard._200KM, 200);
        source = new User("Source", 0, 0, null);
    }

    @Test
    public void test25KM(){
        // Test de la card
        assertEquals("Distance25_KM", _25KM.getName());
        assertEquals(TypeCard._25KM, _25KM.getType());
        assertEquals(25, _25KM.getKilometers());
        assertNotNull(_25KM.getImage());

        // Test utilisation de la card
        source.playDistance(_25KM);
        assertEquals(source.check(_25KM, source, source), false);
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true);
        assertEquals(source.check(_25KM, source, source), true);
        source.playDistance(_25KM);
        assertEquals(source.getKilometers(), 25);
        source.playDistance(_25KM);
        assertEquals(source.getKilometers(), 50);
    }

    @Test
    public void test50KM(){
        // Test de la card
        assertEquals("Distance50_KM", _50KM.getName());
        assertEquals(TypeCard._50KM, _50KM.getType());
        assertEquals(50, _50KM.getKilometers());
        assertNotNull(_50KM.getImage());

        // Test utilisation de la card
        source.playDistance(_50KM);
        assertEquals(source.check(_50KM, source, source), false);
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true);
        assertEquals(source.check(_50KM, source, source), true);
        source.playDistance(_50KM);
        assertEquals(source.getKilometers(), 50);
        source.playDistance(_50KM);
        assertEquals(source.getKilometers(), 100);
    }

    @Test
    public void test75KM(){
        // Test de la card
        assertEquals("Distance75_KM", _75KM.getName());
        assertEquals(TypeCard._75KM, _75KM.getType());
        assertEquals(75, _75KM.getKilometers());
        assertNotNull(_75KM.getImage());

        // Test utilisation de la card
        source.playDistance(_75KM);
        assertEquals(source.check(_75KM, source, source), false);
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true);
        assertEquals(source.check(_75KM, source, source), true);
        source.playDistance(_75KM);
        assertEquals(source.getKilometers(), 75);
        source.playDistance(_75KM);
        assertEquals(source.getKilometers(), 150);
    }

    @Test
    public void test100KM(){
        // Test de la card
        assertEquals("Distance100_KM", _100KM.getName());
        assertEquals(TypeCard._100KM, _100KM.getType());
        assertEquals(100, _100KM.getKilometers());
        assertNotNull(_100KM.getImage());

        // Test utilisation de la card
        source.playDistance(_100KM);
        assertEquals(source.check(_100KM, source, source), false);
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true);
        assertEquals(source.check(_100KM, source, source), true);
        source.playDistance(_100KM);
        assertEquals(source.getKilometers(), 100);
        source.playDistance(_100KM);
        assertEquals(source.getKilometers(), 200);
    }

    @Test
    public void test200KM(){
        // Test de la card
        assertEquals("Distance200_KM", _200KM.getName());
        assertEquals(TypeCard._200KM, _200KM.getType());
        assertEquals(200, _200KM.getKilometers());
        assertNotNull(_200KM.getImage());

        // Test utilisation de la card
        source.playDistance(_200KM);
        assertEquals(source.check(_200KM, source, source), false);
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true);
        assertEquals(source.check(_200KM, source, source), true);
        source.playDistance(_200KM);
        assertEquals(source.getKilometers(), 200);
        source.playDistance(_200KM);
        assertEquals(source.getKilometers(), 400);
    }
}