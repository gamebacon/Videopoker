package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

class Board extends JPanel {

	int lastBetlvl = 1;

	Color panelColor;
	Color textNBorderColor;
	Color winColor;

	JPanel[] sections = new JPanel[6];
	JLabel[][] text = new JLabel[9][6];

	Font font;
	Font bigFont;

	String[][] rowData = {
	{"Royal flush", "250","500","750","1000","4000"},
	{"Straight flush", "50","100","150","250","500"},
	{"Four of a kind", "25","50","75","125","250"},
	{"Full house", "9","18","27","45","90"},
	{"Flush", "6","15","18","30","60"},
	{"Straight", "4","5","12","20","40"},
	{"Three of a kind", "3","6","9","15","30"},
	{"Two pair", "2","4", "6", "10","20"},
	{"Jack's or better", "1","2","3","5","10"}
	};


	public Board() {
		GridLayout grid = new GridLayout(1,6);
		grid.setHgap(3);
		setLayout(grid);

		font = new Font("VCR OSD Mono", Font.BOLD, 15);
		bigFont = new Font("VCR OSD Mono", Font.BOLD, 25);

		panelColor = new Color(0, 50, 105);
		winColor = new Color(240,40,150);
		textNBorderColor = (Color.yellow);
		
		for(int i = 0; i < 6; i++) {
			sections[i] = new JPanel();
			sections[i].setLayout(new BoxLayout(sections[i], 1));
			sections[i].setBackground(panelColor);
			sections[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			for(int j = 0; j < 9; j++) {
				text[j][i] = new JLabel((i > 0 ? "$" : "") + rowData[j][i]);
				text[j][i].setFont(font);
				text[j][i].setForeground(textNBorderColor);
				sections[i].add(text[j][i]);
			}
			add(sections[i]);
		}
		sections[1].setBackground(Color.red);
		setBackground(textNBorderColor);
		setBorder(BorderFactory.createLineBorder(textNBorderColor, 3));
	}


	
	void displayWin(int handValue, int betLevel) {
		new Thread(new Runnable() { public void run (){
		while(Game.gameover && Game.handValue == handValue  ) {
			try {
				Sound.playSound("bell", 1);
				text[handValue][0].setBackground(winColor);;
				text[handValue][0].setOpaque(true);
				text[handValue][betLevel].setBackground(winColor);;
				text[handValue][betLevel].setOpaque(true);
				Thread.sleep(500);
				text[handValue][0].setBackground(null);
				text[handValue][0].setOpaque(false);
				text[handValue][betLevel].setBackground(null);
				text[handValue][betLevel].setOpaque(false);
				Thread.sleep(250);
			} catch (InterruptedException ie) {}
		}
		}}).start();
	}

	void switchBettingLevel(int lvl) {
		sections[lastBetlvl].setBackground(panelColor);
		sections[lvl].setBackground(Color.red);
		lastBetlvl = lvl;
	}
}
