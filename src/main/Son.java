package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Son{
    
    private Clip clip;
    private File sonURL[] = new File[30];
    private boolean sonON = true;

    public Son(){ // Ajouter d'autre son ici
        sonURL[0] = new File("son/Foule.wav");
        sonURL[1] = new File("son/Klaxon.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(sonURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);  
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        if(sonON){
            clip.start();
        }
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
        if(sonON){
            sonON = false;
        }else{
            sonON = true;
            play();
        }   
    }
}
