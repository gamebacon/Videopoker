package net.gamebacon.videopoker;

import javax.swing.*;

public class Driver {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VideoPoker::new);
    }

}
