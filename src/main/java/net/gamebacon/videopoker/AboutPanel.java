package net.gamebacon.videopoker;

import javax.swing.*;

public class AboutPanel extends JPanel {

    JTextArea text = new JTextArea(
            "VideoPoker is a popular digital 1-player casino game." +
            "" +
            "" +
            "" +
            "");

    public AboutPanel() {
        add(text);
    }
}
