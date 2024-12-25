package com.millebornes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    private Clip[] clipList = new Clip[30];
    private boolean soundON = true;
    private FloatControl fc;

    public Sound() {
        try {
            // Chargement des sons
            loadSound(0, "/son/Fond.wav");      // Son de fond
            loadSound(1, "/son/Klaxon.wav");    // Klaxon
            loadSound(2, "/son/VoitureAvance.wav"); // Voiture
            loadSound(3, "/son/Foule.wav");     // Foule
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Correct way to load sound resources
    private void loadSound(int index, String path) {
        try {
            URL soundURL = getClass().getResource(path);
            if (soundURL == null) {
                System.err.println("Could not find sound file: " + path);
                return;
            }
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL);
            clipList[index] = AudioSystem.getClip();
            clipList[index].open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setSoundON(boolean b) {
        soundON = b;
    }

    public boolean getSoundON() {
        return soundON;
    }

    public void setFile(int i) {
        try {
            if (clipList[i] != null) {
                fc = (FloatControl) clipList[i].getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(int i) {
        if (soundON && clipList[i] != null) {
            clipList[i].setFramePosition(0);
            clipList[i].start();
        }
    }

    public void loop() {
        if (clipList[0] != null) {
            clipList[0].loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop(int i) {
        if (clipList[i] != null) {
            if (fc != null) {
                fc.setValue(0);
            }
            clipList[i].stop();
            soundON = !soundON;
            if (soundON) {
                play(0);
            }
        }
    }
}
