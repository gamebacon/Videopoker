package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;
import net.gamebacon.videopoker.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class CardContainer extends JPanel implements MouseListener {

	private final VideoPoker main;
	private final JLabel cardImageLabel;
	private final JLabel topLabel;
	private final JLabel bottomLabel;

	private final Font heldFont = new Font("VCR OSD Mono", Font.BOLD, 30);

	private boolean isSelected;
	private Card card;

	public CardContainer(VideoPoker main) {
		this.main = main;

		cardImageLabel = new JLabel();
		cardImageLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		cardImageLabel.setOpaque(false);
		cardImageLabel.addMouseListener(this);

		bottomLabel = new JLabel("HELD");
		bottomLabel.setFont(heldFont);
		bottomLabel.setForeground(Color.BLACK);
		bottomLabel.setOpaque(true);
		bottomLabel.setVisible(false);
		bottomLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		bottomLabel.setBackground(Util.mainColor);

		topLabel = new JLabel("");
		topLabel.setOpaque(true);
		topLabel.setVisible(false);
		topLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		topLabel.setBackground(Util.mainColor);

		cardImageLabel.setIcon(Util.getCardIcon(card));
		topLabel.setIcon(Util.getArrowImage());

		setLayout(new BoxLayout(this, 1));

		add(topLabel);
		add(Box.createVerticalStrut(5));
		
		add(cardImageLabel);

		add(Box.createVerticalStrut(10));
		add(bottomLabel);

		setPreferredSize(new Dimension(800, 450));

		setBackground(Util.mainColor);
		//setOpaque(true);
	}
		

	public void mouseExited(MouseEvent me) { }
	public void mouseEntered(MouseEvent me) { }
	public void mouseReleased(MouseEvent me) { 
		if(!main.isGameOver())
			toggleSelect(true);
	}
	public void mousePressed(MouseEvent me) { }
	public void mouseClicked(MouseEvent me) {
	}

	void toggleSelect(boolean playSound) {
		if(card != null) {
			isSelected = !isSelected;
			if(playSound)
				Sound.playSound("select", 2);
			if(isSelected) {
				topLabel.setVisible(true);
				bottomLabel.setVisible(true);
				if(false)
				new Thread(new Runnable(){
					public void run() {
						playArrowIdleAnimation();
					}
				}).start();
			}
			else {
			    bottomLabel.setVisible(false);
				topLabel.setVisible(false);
			}
					
		}
	}	


	void newCard(Card card) {
		this.card = card;
		cardImageLabel.setIcon(Util.getCardIcon(card));
		cardImageLabel.setToolTipText(card.toWordString());
		Sound.playSound("deal", 3);
	}


	void resetFields() {
		isSelected = false;
		cardImageLabel.setIcon(Util.getCardIcon(null));
		bottomLabel.setVisible(false);
		topLabel.setVisible(false);
		card = null;
	}

	private void playArrowIdleAnimation() {
		while (isSelected) {
			try {
				topLabel.setVisible(true);
				Thread.sleep(1200);	
				topLabel.setVisible(false);
				Thread.sleep(200);	
			} catch (InterruptedException IE) {}
		}
	}
	
	boolean isSelected(){
		return isSelected;
	}

	public Card getCard() {
		return card;
	}
}
