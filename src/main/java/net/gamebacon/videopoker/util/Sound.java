package net.gamebacon.videopoker.util;

import net.gamebacon.videopoker.VideoPoker;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

public class Sound {

    public static void playSound(String sound, int variants) {
        variants = (int) (Math.random() * variants + 1);
        try {
            Clip clip  = AudioSystem.getClip();
            String src = String.format("/sounds/%s%d.wav", sound, variants);
            BufferedInputStream myStream = new BufferedInputStream(VideoPoker.class.getResourceAsStream(src));
            AudioInputStream stream = AudioSystem.getAudioInputStream(myStream);
            clip.open(stream);
            clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

}
