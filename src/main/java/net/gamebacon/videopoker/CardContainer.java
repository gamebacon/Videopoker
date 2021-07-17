package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;
import net.gamebacon.videopoker.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.net.URL;

class CardContainer extends JPanel implements MouseListener {

	private final VideoPoker main;
	private final JLabel cardImageLabel;
	private final JLabel topLabel;
	private final JLabel bottomLabel;

	private final Dimension cardSize = new Dimension(140, 220);
	private final Dimension arrowSize = new Dimension(15, 15);
	private final Font heldFont = new Font("VCR OSD Mono", Font.BOLD, 30);

	private boolean isSelected;
	Card card;

	public CardContainer(VideoPoker main) {
		this.main = main;

		cardImageLabel = new JLabel();
		cardImageLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		cardImageLabel.setOpaque(false);
		cardImageLabel.addMouseListener(this);

		bottomLabel = new JLabel("HELD");
		bottomLabel.setFont(heldFont);
		bottomLabel.setForeground(Color.WHITE);
		bottomLabel.setOpaque(true);
		bottomLabel.setVisible(false);
		bottomLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		bottomLabel.setBackground(Util.mainColor);

		topLabel = new JLabel("");
		topLabel.setOpaque(true);
		topLabel.setVisible(false);
		topLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		topLabel.setBackground(Util.mainColor);

		setImage("deck2/back.png", cardImageLabel, cardSize);
		setImage("arrow.png", topLabel, arrowSize);

		setLayout(new BoxLayout(this, 1));

		add(topLabel);
		add(Box.createVerticalStrut(5));
		
		add(cardImageLabel);

		add(Box.createVerticalStrut(10));
		add(bottomLabel);


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
		setImage("deck2/" + card.cardImageFileName() + ".png", cardImageLabel, cardSize);
		setToolTipText(card.toString());
		Sound.playSound("deal", 3);
	}


	void setImage(String path, JLabel destination, Dimension size) {
		try {                
			path = "/images/" + path;
            URL url = getClass().getResource(path);
			destination.setIcon(new ImageIcon(new ImageIcon(url).getImage().getScaledInstance(size.width, size.height, Image.SCALE_DEFAULT)));
		} catch (Exception ex) {
			System.out.println("no wok loading " + path);
			ex.printStackTrace();
		}
	}

	void resetFields() {
		isSelected = false;
		setImage("deck2/back.png", cardImageLabel, cardSize);
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
}
