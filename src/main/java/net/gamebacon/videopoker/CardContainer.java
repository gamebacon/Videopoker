package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Dimension;

class CardContainer extends JPanel implements MouseListener {
	int id;
	Card card;
	boolean isSelected;
	JLabel bottomLabel;

	JLabel cardImageLabel;
	JLabel topLabel;

	BufferedImage cardImage;
	BufferedImage arrowImage;
	
	public CardContainer(int id) {
		this.id = id;
			
		cardImageLabel = new JLabel();
		cardImageLabel.setAlignmentX(cardImageLabel.CENTER_ALIGNMENT);
		cardImageLabel.setOpaque(false);

		bottomLabel = new JLabel("HELD", SwingConstants.CENTER);
		bottomLabel.setFont(new Font("VCR OSD Mono", Font.BOLD, 20));
		bottomLabel.setOpaque(false);
		bottomLabel.setVisible(false);

		topLabel = new JLabel("", SwingConstants.CENTER);
		topLabel.setOpaque(false);
		topLabel.setVisible(false);

		setImage("src/main/resources/images/deck2/back.png", cardImage, cardImageLabel, new Dimension(180, 100));
		setImage("src/main/resources/images/arrow.png", arrowImage, topLabel, new Dimension(10, 10));

		setLayout(new BoxLayout(this, 1));
		setOpaque(false);

		JPanel topPanel = new JPanel();
		//topPanel.setOpaque(false);
		topPanel.add(topLabel);
		add(topPanel);

		add(Box.createVerticalStrut(5));
		
		add(cardImageLabel);

		add(Box.createVerticalStrut(5));

		JPanel bottomPanel = new JPanel();
		//bottomPanel.setOpaque(false);
		bottomPanel.add(bottomLabel);
		add(bottomPanel);

		addMouseListener(this);
	}
		

	public void mouseExited(MouseEvent me) { }
	public void mouseEntered(MouseEvent me) { }
	public void mouseReleased(MouseEvent me) { 
		if(!Game.gameover)
			toggleSelect(true);
	}
	public void mousePressed(MouseEvent me) { }
	public void mouseClicked(MouseEvent me) {
	}

	void toggleSelect(boolean playSound) {
		if(card != null) {
			isSelected = !isSelected;
			if(playSound)
				Sound.playSound("deal", 3);
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
		setImage("src/main/resources/images/deck2/" + card.cardImageFileName() + ".png", cardImage, cardImageLabel, new Dimension(180, 100));
		setToolTipText(card.toString());
		Sound.playSound("deal", 3);
	}


	void setImage(String path, BufferedImage image, JLabel destination, Dimension size) {
		try {                
        	//image = ImageIO.read(new File(path));
			destination.setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(size.height, size.width, Image.SCALE_DEFAULT)));
		} catch (Exception ex) {System.out.println("no wok loading " + path);}
	}

	void resetFields() {
		isSelected = false;
		setImage("src/main/resources/images/deck2/back.png", cardImage, cardImageLabel, new Dimension(180, 100));
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
