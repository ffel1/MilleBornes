package com.millebornes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * The Sound class manages sound effects and background music in the game.
 */
public class Sound {
    private Clip clip;
    private URL[] soundURL = new URL[4]; // Array to store sound file locations
    private boolean soundOn = true; // Flag to control whether sound is enabled

    /**
     * Constructor initializes the sound URLs.
     */
    public Sound() {
        soundURL[0] = getClass().getResource("/son/Fond.wav"); // Main background sound
        soundURL[1] = getClass().getResource("/son/Klaxon.wav");
        soundURL[2] = getClass().getResource("/son/voitureAvance.wav");
        soundURL[3] = getClass().getResource("/son/Foule.wav");
    }

    /**
     * Sets the sound file to be used.
     *
     * @param i The index of the sound file in the soundURL array.
     */
    public void setFile(int i) {
        try {
            if (soundURL[i] != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL[i]);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } else {
                System.err.println("Sound file at index " + i + " is null.");
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the current sound file.
     */
    public void play() {
        if (soundOn && clip != null) {
            clip.start();
        }
    }

    /**
     * Plays the sound file at the specified index.
     *
     * @param i The index of the sound file in the soundURL array.
     */
    public void play(int i) {
        setFile(i);
        play(); // Reuse the existing play() method
    }

    /**
     * Plays the sound in a loop at the specified index.
     *
     * @param i The index of the sound file to loop.
     */
    public void loop(int i) {
        setFile(i);
        if (soundOn && clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Plays the default sound in a loop (index 0).
     */
    public void loop() {
        loop(0); // Default to looping the first sound
    }

    /**
     * Stops the currently playing sound.
     */
    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    /**
     * Stops the currently playing sound at the specified index.
     *
     * @param i The index of the sound file to stop.
     */
    public void stop(int i) {
        setFile(i);
        stop(); // Reuse the existing stop() method
    }

    /**
     * Toggles the sound on or off.
     */
    public void toggleSound() {
        soundOn = !soundOn;
        if (!soundOn && clip != null) {
            clip.stop();
        }
    }

    /**
     * Checks if sound is enabled.
     *
     * @return True if sound is on, false otherwise.
     */
    public boolean getSoundON() {
        return soundOn;
    }

    /**
     * Enables or disables the sound.
     *
     * @param soundON True to enable sound, false to disable.
     */
    public void setSoundON(boolean soundON) {
        this.soundOn = soundON;
    }
}