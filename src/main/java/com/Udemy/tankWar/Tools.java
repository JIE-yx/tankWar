package com.Udemy.tankWar;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Tools {

    static Image getImage(String imageName){
        return new ImageIcon("assets/images/" + imageName).getImage();
    }

    static void playAudio(String audioType ){
        try {
            //    File yourFile;
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;
            File shootSoundFile = new File("assets/audios/"+audioType);
            //    yourFile = new File("assets/audios/shoot.wav");
            stream = AudioSystem.getAudioInputStream(shootSoundFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        }
        catch (Exception e) {
            //whatevers
        }

    }
}
