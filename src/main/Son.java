package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.io.File;

public class Son{
    
    private Clip clip;
    private File sonURL[] = new File[30];
    private Clip clipList[] = new Clip[30];
    private boolean sonON = true;
    private FloatControl fc;

    public Son(){ // Ajouter d'autre son ici
        sonURL[0] = new File("son/Fond.wav"); // 0 = son principal
        sonURL[1] = new File("son/Klaxon.wav");
        sonURL[2] = new File("son/VoitureAvance.wav");
        sonURL[3] = new File("son/Foule.wav");
    }

    public void setSonON(boolean b){
        sonON = b;
    }

    public boolean getSonON(){
        return sonON;
    }

    /*
     * Creer le son
     */
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonURL[i]);
            clipList[i] = AudioSystem.getClip();
            clipList[i].open(ais);  
            fc = (FloatControl) clipList[i].getControl(FloatControl.Type.MASTER_GAIN);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
     * Joue un son une fois
     */
    public void play(int i){
        if(sonON){
            clipList[i].start();
        }
    }

    /*
     * Joue un son en boucle
     */
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /*
     * Stop la musique principale
     */
    public void stop(int i){
        if(clipList[i] != null){
            fc.setValue(0);
            clipList[i].stop();
            if(sonON){
                sonON = false;
            }else{
                sonON = true;
                play(0);
            }  
        }
    }
}
