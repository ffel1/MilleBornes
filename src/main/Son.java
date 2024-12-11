package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Son {
    
    private Clip clip;
    private URL[] sonURLs = new URL[30];  // Changed from File[] to URL[]

    public Son() {
        // Initialize the array with sound files as URLs
        sonURLs[0] = getClass().getResource("/son/Foule.wav");  // Use relative path or absolute URL
        sonURLs[1] = getClass().getResource("/son/Klaxon.wav");
        // Add more files as needed
    }

    public void setFile(int i) {
        try {
            // Check if the URL is null (file might not be found)
            if (sonURLs[i] == null) {
                System.out.println("Sound file not found for index: " + i);
                return;
            }

            // Load the audio input stream from the URL
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonURLs[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip != null) {
            clip.start();
        } else {
            System.out.println("No sound loaded to play.");
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("No sound loaded to loop.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.out.println("No sound loaded to stop.");
        }
    }
}
