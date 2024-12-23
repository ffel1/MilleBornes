package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.*;

/**
 * Unit test class for "Distance" type cards in the game.
 * Each test verifies the basic properties of the cards and their behavior when played.
 */
public class DistanceTest {

    private Distance _25KM;
    private Distance _50KM;
    private Distance _75KM;
    private Distance _100KM;
    private Distance _200KM;
    private User source;

    /**
     * Initializes objects before each test.
     * Creates distance cards and a user.
     */
    @BeforeEach
    public void init() {
        _25KM = new Distance(TypeCard._25KM, 25);
        _50KM = new Distance(TypeCard._50KM, 50);
        _75KM = new Distance(TypeCard._75KM, 75);
        _100KM = new Distance(TypeCard._100KM, 100);
        _200KM = new Distance(TypeCard._200KM, 200);
        source = new User("Source", 0, 0, null);
    }

    /**
     * Tests the properties and behavior of the "25KM" distance card.
     */
    @Test
    public void test25KM() {
        // Verify card properties
        assertEquals("Distance25 km", _25KM.getName());
        assertEquals(TypeCard._25KM, _25KM.getType());
        assertEquals(25, _25KM.getKilometers());
        assertNotNull(_25KM.getImage());

        // Verify card usage
        source.playDistance(_25KM);
        assertEquals(source.check(_25KM, source, source), false); // Cannot play without green light
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true); // Set green light
        assertEquals(source.check(_25KM, source, source), true); // Can now play
        source.playDistance(_25KM);
        assertEquals(source.getKilometers(), 25); // Kilometers updated
        source.playDistance(_25KM);
        assertEquals(source.getKilometers(), 50); // Kilometers updated again
    }

    /**
     * Tests the properties and behavior of the "50KM" distance card.
     */
    @Test
    public void test50KM() {
        // Verify card properties
        assertEquals("Distance50 km", _50KM.getName());
        assertEquals(TypeCard._50KM, _50KM.getType());
        assertEquals(50, _50KM.getKilometers());
        assertNotNull(_50KM.getImage());

        // Verify card usage
        source.playDistance(_50KM);
        assertEquals(source.check(_50KM, source, source), false); // Cannot play without green light
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true); // Set green light
        assertEquals(source.check(_50KM, source, source), true); // Can now play
        source.playDistance(_50KM);
        assertEquals(source.getKilometers(), 50); // Kilometers updated
        source.playDistance(_50KM);
        assertEquals(source.getKilometers(), 100); // Kilometers updated again
    }

    /**
     * Tests the properties and behavior of the "75KM" distance card.
     */
    @Test
    public void test75KM() {
        // Verify card properties
        assertEquals("Distance75 km", _75KM.getName());
        assertEquals(TypeCard._75KM, _75KM.getType());
        assertEquals(75, _75KM.getKilometers());
        assertNotNull(_75KM.getImage());

        // Verify card usage
        source.playDistance(_75KM);
        assertEquals(source.check(_75KM, source, source), false); // Cannot play without green light
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true); // Set green light
        assertEquals(source.check(_75KM, source, source), true); // Can now play
        source.playDistance(_75KM);
        assertEquals(source.getKilometers(), 75); // Kilometers updated
        source.playDistance(_75KM);
        assertEquals(source.getKilometers(), 150); // Kilometers updated again
    }

    /**
     * Tests the properties and behavior of the "100KM" distance card.
     */
    @Test
    public void test100KM() {
        // Verify card properties
        assertEquals("Distance100 km", _100KM.getName());
        assertEquals(TypeCard._100KM, _100KM.getType());
        assertEquals(100, _100KM.getKilometers());
        assertNotNull(_100KM.getImage());

        // Verify card usage
        source.playDistance(_100KM);
        assertEquals(source.check(_100KM, source, source), false); // Cannot play without green light
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true); // Set green light
        assertEquals(source.check(_100KM, source, source), true); // Can now play
        source.playDistance(_100KM);
        assertEquals(source.getKilometers(), 100); // Kilometers updated
        source.playDistance(_100KM);
        assertEquals(source.getKilometers(), 200); // Kilometers updated again
    }

    /**
     * Tests the properties and behavior of the "200KM" distance card.
     */
    @Test
    public void test200KM() {
        // Verify card properties
        assertEquals("Distance200 km", _200KM.getName());
        assertEquals(TypeCard._200KM, _200KM.getType());
        assertEquals(200, _200KM.getKilometers());
        assertNotNull(_200KM.getImage());

        // Verify card usage
        source.playDistance(_200KM);
        assertEquals(source.check(_200KM, source, source), false); // Cannot play without green light
        assertEquals(source.getKilometers(), 0);
        source.setGreenLight(true); // Set green light
        assertEquals(source.check(_200KM, source, source), true); // Can now play
        source.playDistance(_200KM);
        assertEquals(source.getKilometers(), 200); // Kilometers updated
        source.playDistance(_200KM);
        assertEquals(source.getKilometers(), 400); // Kilometers updated again
    }
}