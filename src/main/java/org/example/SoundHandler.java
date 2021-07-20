package org.example;

import Frames.MenuSettings;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SoundHandler {
    static URL soundURL;
    public static Clip clip;
    static Thread sound;
    public static Thread music;

    public static void musicInit(){
        music = new Thread(() -> {
            try {
                clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(soundURL));
                clip.open(inputStream);
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                volumeControl.setValue((float) MenuSettings.volume-80);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
        });
        music.start();
    }
    public static void takeMusicAdress(int i){
        switch (i){
            case 0:{
                soundURL = SoundHandler.class.getResource("/sound/0TrickComposer_1.4_-_AlphaCentauriAndBeyond.wav");
                break;}
            case 1:{
                soundURL = SoundHandler.class.getResource("/sound/1TrickComposer_1.4_-_EngineEngage!.wav");
                break;}
            case 2:{
                soundURL = SoundHandler.class.getResource("/sound/2TrickComposer_1.4_-_DistantStars.wav");
                break;}
            case 3:{
                soundURL = SoundHandler.class.getResource("/sound/3TrickComposer_1.4_-_Standoff.wav");
                break;}
            case 4:{
                soundURL = SoundHandler.class.getResource("/sound/4TrickComposer_1.4_-_PushOn.wav");
                break;}
            case 5:{
                soundURL = SoundHandler.class.getResource("/sound/5TrickComposer_1.4_-_OxygenTanks.wav");
                break;}
            case 6:{
                soundURL = SoundHandler.class.getResource("/sound/6TrickComposer_1.4_-_LastFlight.wav");
                break;}
        }
    }
}
