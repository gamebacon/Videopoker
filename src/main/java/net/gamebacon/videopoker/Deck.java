package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Deck extends ArrayList<Card> {

	public Deck() {
		for(int suit = 0; suit < 4; suit++)
			for(int value = 0; value < 13; value++)
				add(new Card(suit, value));

		Collections.sort(this);
	}


	@Deprecated
	public Deck(boolean custom, int hand) {
	if(hand == 0) { //royal flush
		add(new Card(0, 8));
		add(new Card(0, 9));
		add(new Card(0, 10));
		add(new Card(0, 11));
		add(new Card(0, 12));
	}

	if(hand == 1) { //straight flush
		add(new Card(1, 4));
		add(new Card(1, 5));
		add(new Card(1, 6));
		add(new Card(1, 7));
		add(new Card(1, 8));
	}
			
	if(hand == 2) { //four of a kind 
		add(new Card(0, 3));
		add(new Card(2, 3));
		add(new Card(2, 3));
		add(new Card(2, 3));
		add(new Card(1, 4));
	}

	if(hand == 3) { //full house
		add(new Card(0, 0));
		add(new Card(0, 0));
		add(new Card(0, 2));
		add(new Card(0, 2));
		add(new Card(1, 2));
	}

	if(hand == 4) { //flush
		add(new Card(0, 0));
		add(new Card(0, 4));
		add(new Card(0, 2));
		add(new Card(0, 7));
		add(new Card(0, 12));
	}

	if(hand == 5) { //straight
		add(new Card(0, 2));
		add(new Card(0, 3));
		add(new Card(3, 4));
		add(new Card(0, 5));
		add(new Card(1, 6));
	}

	if(hand == 6) { //three of a kind 
		add(new Card(0, 12));
		add(new Card(0, 10));
		add(new Card(3, 10));
		add(new Card(0, 10));
		add(new Card(1, 6));
	}

	if(hand == 7) { //two pair
		add(new Card(0, 12));
		add(new Card(0, 10));
		add(new Card(3, 12));
		add(new Card(0, 10));
		add(new Card(1, 6));
	}

	if(hand == 8) { //jacks or better
		add(new Card(0, 12));
		add(new Card(0, 10));
		add(new Card(3, 0));
		add(new Card(0, 10));
		add(new Card(1, 6));
	}

	if(hand == 9) { //low pair
		add(new Card(0, 12));
		add(new Card(0, 0));
		add(new Card(3, 4));
		add(new Card(0, 0));
		add(new Card(1, 6));
	}

	if(hand == 10) { //high card
		add(new Card(0, 12));
		add(new Card(0, 10));
		add(new Card(3, 0));
		add(new Card(0, 3));
		add(new Card(1, 6));
	}

		//Collections.sort(;
	}
	
	
	void shuffle() {
		Collections.shuffle(this);
		Sound.playSound("shuffle",1);
	}

	Card draw() {
		Card card = null;
		try {
			card = get(0);
			remove(0);
		} catch (NullPointerException NPE) {System.out.println("No more cards in ");}
		return card;
	}
}
