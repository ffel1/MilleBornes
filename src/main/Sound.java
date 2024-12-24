package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

/**
 * This class represents the Sound system, handling the playback and control of various sound effects.
 * It allows playing, looping, and stopping sounds, with a toggle option to enable or disable sound.
 */
public class Sound {

    // Array of Clip objects to manage sound playback
    private Clip clip;
    // Array of File objects that represent the sound files
    private File soundURL[] = new File[30];
    // Array of Clip objects corresponding to each sound file
    private Clip clipList[] = new Clip[30];
    // Boolean flag to track whether sound is enabled or disabled
    private boolean soundON = true;
    // FloatControl to adjust the volume of sound clips
    private FloatControl fc;

    /**
     * Constructor for the Sound class.
     * Initializes an array of sound files and maps each sound to an index.
     */
    public Sound() { 
        soundURL[0] = new File("son/Fond.wav"); // 0 = main background sound
        soundURL[1] = new File("son/Klaxon.wav");
        soundURL[2] = new File("son/voitureAvance.wav");
        soundURL[3] = new File("son/Foule.wav");
    }

    /**
     * Sets whether the sound is on or off.
     * 
     * @param b - Boolean value indicating whether sound should be on (true) or off (false).
     */
    public void setSoundON(boolean b) {
        soundON = b;
    }

    /**
     * Gets the current sound state (on or off).
     * 
     * @return boolean - true if sound is enabled, false otherwise.
     */
    public boolean getSoundON() {
        return soundON;
    }

    /**
     * Loads a sound file and prepares it for playback.
     * 
     * @param i - Index of the sound file to load (0 to 29).
     */
    public void setFile(int i) {
        try {
            // Open the sound file as an AudioInputStream
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            // Create a new Clip object to play the sound
            clipList[i] = AudioSystem.getClip();
            clipList[i].open(ais);
            // Get the FloatControl for volume adjustment
            fc = (FloatControl) clipList[i].getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception e) {
            e.printStackTrace();  // Handle any exceptions by printing the stack trace
        }
    }

    /**
     * Plays a sound once, if sound is enabled.
     * 
     * @param i - Index of the sound file to play (0 to 29).
     */
    public void play(int i) {
        if (soundON) {
            clipList[i].start();  // Start the sound if sound is on
        }
    }

    /**
     * Loops the main sound continuously until manually stopped.
     */
    public void loop() {
        // Loop the clip continuously
        clipList[0].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops the main background music and toggles the sound state.
     * If the sound is on, it will be turned off; otherwise, it will be turned on and the main sound will play.
     * 
     * @param i - Index of the sound file to stop (0 to 29).
     */
    public void stop(int i) {
        if (clipList[i] != null) {
            fc.setValue(0);  // Mute the clip
            clipList[i].stop();  // Stop the clip
            // Toggle the sound state
            if (soundON) {
                soundON = false;  // Turn off sound
            } else {
                soundON = true;  // Turn on sound
                play(0);  // Play the main sound again
            }  
        }
    }
}