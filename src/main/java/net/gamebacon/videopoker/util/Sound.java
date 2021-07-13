package net.gamebacon.videopoker.util;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    private final static String soundsPath = "src/main/resources/sounds/";

    public static void playSound(String sound, int variants) {
        variants = (int) (Math.random() * variants + 1);
        try {
            Clip clip  = AudioSystem.getClip();
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(String.format("%s%s%d.wav", soundsPath, sound, variants)));
            clip.open(stream);
            clip.start();
            System.out.println("now playing " + sound + variants);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

}
