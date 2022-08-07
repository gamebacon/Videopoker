package net.gamebacon.videopoker.util;

import net.gamebacon.videopoker.Card;
import net.gamebacon.videopoker.Deck;
import net.gamebacon.videopoker.VideoPoker;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashMap;

public class Util {

    private static final HashMap<String, ImageIcon> cardImages = new HashMap();

    private static final Dimension CARD_IMAGE_SIZE = new Dimension(140, 220);
    private static final Dimension ARROW_IMAGE_SIZE = new Dimension(15, 15);

    private static final ImageIcon cardBackImage = getScaledImage("/images/deck2/back.png", CARD_IMAGE_SIZE);
    private static final ImageIcon arrowImage = getScaledImage("/images/arrow.png", ARROW_IMAGE_SIZE);

    static {
        final Deck deck = new Deck();

        for(final Card card : deck) {
            ImageIcon imageIcon = getScaledImage("/images/deck2/" + card.cardImageFileName(), CARD_IMAGE_SIZE);
            cardImages.put(card.toString(), imageIcon);
        }
    }

    private static ImageIcon getScaledImage(String source, Dimension size) {
        final Image originalImage = new ImageIcon(VideoPoker.class.getResource(source)).getImage();
        return new ImageIcon(originalImage.getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT));
    }

    public static final Color greenColor = new Color(0, 175, 110);
    public static final Color mainColor = new Color(190, 70, 10);
    public static final Color panelColor = new Color(0, 50, 105);
    public static final Color winColor = Color.WHITE;
    public static final Color brightYellow = new Color(211, 211, 0);
    public static final Color mainFrameColor = new Color(255, 255, 255);
    public static final Color boardSelectColor = new Color(171, 0, 0);



    public static ImageIcon getArrowImage() {
        return arrowImage;
    }

    public static ImageIcon getCardBackImage() {
        return cardBackImage;
    }

    public static ImageIcon getCardIcon(Card card) {
        return card == null ? cardBackImage : cardImages.get(card.toString());
    }

}
