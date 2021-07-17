package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;
import net.gamebacon.videopoker.util.Util;

import javax.swing.*;
import java.awt.Font;
import java.awt.Color;

class Board extends JPanel {

	private final VideoPoker main;
	private final Font font = new Font("VCR OSD Mono", Font.BOLD, 18);


	private final JPanel[] sections = new JPanel[6];
	private final JLabel[][] text = new JLabel[9][6];


	private int lastBetLevel = 1;


	String[][] rowData = {
	{"Royal flush........."," 250"," 500"," 750","1000","4000"},
	{"Straight flush......","  50"," 100"," 150"," 250"," 500"},
	{"Four of a kind......","  25","  50","  75"," 125"," 250"},
	{"Full house..........","   9","  18","  27","  45","  90"},
	{"Flush...............","   6","  15","  18","  30","  60"},
	{"Straight............","   4","   5","  12","  20","  40"},
	{"Three of a kind.....","   3","   6","   9","  15","  30"},
	{"Two pair............","   2","   4","   6","  10","  20"},
	{"Jack's or better....","   1","   2","   3","   5","  10"}
	};


	public Board(VideoPoker main) {
		this.main = main;
		//GridLayout grid = new GridLayout(1,6);
		//grid.setHgap(3);
		setLayout(new BoxLayout(this, 2));



		for(int i = 0; i < 6; i++) {
			sections[i] = new JPanel();
			sections[i].setLayout(new BoxLayout(sections[i], 1));
			sections[i].setBackground(Util.panelColor);
			sections[i].setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			add(Box.createHorizontalStrut(5));
			for(int j = 0; j < 9; j++) {
				text[j][i] = new JLabel(rowData[j][i], SwingConstants.RIGHT);
				text[j][i].setFont(font);
				text[j][i].setForeground(Util.brightYellow);
				sections[i].add(text[j][i]);
			}
			add(sections[i]);
		}
		sections[1].setBackground(Color.red);
		setBackground(Util.brightYellow);
		setBorder(BorderFactory.createLineBorder(Util.brightYellow, 3));
	}

	void switchBettingLevel(int lvl) {
		sections[lastBetLevel].setBackground(Util.panelColor);
		sections[lvl].setBackground(Color.red);
		lastBetLevel = lvl;
	}


	public JLabel[][] getBoard() {
		return text;
	}

	public void reset() {
		for(int i = 0; i < 9; i++)
		    for(int j = 0; j < 6; j++) {
				text[i][j].setForeground(Util.brightYellow);
			}
	}

	public void displayWin() {
		final JLabel handLabel = text[main.getCurrentHandValue()][0];
		final JLabel winLabel = text[main.getCurrentHandValue()][main.getBetLevel()];
		handLabel.setForeground(Util.winColor);
		winLabel.setForeground(Util.winColor);
		try {
		for(int i = 0; i < 3; i++) {
			Thread.sleep(150);
			Sound.playSound("bell", 1);
		}
		} catch(InterruptedException ex) {}
	}

	public int getWin(int handValue, int betLevel) {
	    return Integer.parseInt(text[handValue][betLevel].getText().trim());
	}
}
