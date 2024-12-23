package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

// This class represents Sound{
public class Sound{
    
// Private or protected member
    private Clip clip;
// Private or protected member
    private File soundURL[] = new File[30];
// Private or protected member
    private Clip clipList[] = new Clip[30];
// Private or protected member
    private boolean soundON = true;
// Private or protected member
    private FloatControl fc;

// This method handles the logic for Sound
    public Sound(){ // add d'autre sound ici
        soundURL[0] = new File("son/Fond.wav"); // 0 = sound principal
        soundURL[1] = new File("son/Klaxon.wav");
        soundURL[2] = new File("son/voitureAvance.wav");
        soundURL[3] = new File("son/Foule.wav");
    }

// This method handles the logic for setSoundON
    public void setSoundON(boolean b){
        soundON = b;
    }

// This method handles the logic for getSoundON
    public boolean getSoundON(){
        return soundON;
    }

    /*
     * Creer le sound
     */
// This method handles the logic for setFile
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clipList[i] = AudioSystem.getClip();
            clipList[i].open(ais);  
            fc = (FloatControl) clipList[i].getControl(FloatControl.Type.MASTER_GAIN);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
     * Joue un sound une fois
     */
// This method handles the logic for play
    public void play(int i){
        if(soundON){
            clipList[i].start();
        }
    }

    /*
     * Joue un sound en boucle
     */
// This method handles the logic for loop
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /*
     * Stop la musique principale
     */
// This method handles the logic for stop
    public void stop(int i){
        if(clipList[i] != null){
            fc.setValue(0);
            clipList[i].stop();
            if(soundON){
                soundON = false;
            }else{
                soundON = true;
                play(0);
            }  
        }
    }
}
