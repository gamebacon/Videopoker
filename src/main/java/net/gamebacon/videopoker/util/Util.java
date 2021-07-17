package net.gamebacon.videopoker.util;

import net.gamebacon.videopoker.VideoPoker;

import java.awt.*;
import java.io.File;

public class Util {

    public static final Color mainColor = new Color(198, 218, 248);
    public static final Color greenColor = new Color(0, 175, 110);
    public static final Color redColor = new Color(190, 70, 10);
    public static final Color panelColor = new Color(0, 50, 105);
    public static final Color winColor = Color.WHITE;
    public static final Color brightYellow = (Color.yellow);
    public static final Color mainFrameColor = new Color(212, 79, 174);


    public static File getResource(String pathInResourceFolder) {
            return new File(pathInResourceFolder);
    }
}
