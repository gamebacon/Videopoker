package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.Sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

class Deck {
	List<Card> deck = new ArrayList<Card>();
	
	public Deck() {
		for(int suit = 0; suit < 4; suit++)
			for(int value = 0; value < 13; value++)
				deck.add(new Card(suit, value));

		Collections.sort(deck);
	}


	@Deprecated
	public Deck(boolean custom, int hand) {
	if(hand == 0) { //royal flush
		deck.add(new Card(0, 8));
		deck.add(new Card(0, 9));
		deck.add(new Card(0, 10));
		deck.add(new Card(0, 11));
		deck.add(new Card(0, 12));
	}

	if(hand == 1) { //straight flush
		deck.add(new Card(1, 4));
		deck.add(new Card(1, 5));
		deck.add(new Card(1, 6));
		deck.add(new Card(1, 7));
		deck.add(new Card(1, 8));
	}
			
	if(hand == 2) { //four of a kind 
		deck.add(new Card(0, 3));
		deck.add(new Card(2, 3));
		deck.add(new Card(2, 3));
		deck.add(new Card(2, 3));
		deck.add(new Card(1, 4));
	}

	if(hand == 3) { //full house
		deck.add(new Card(0, 0));
		deck.add(new Card(0, 0));
		deck.add(new Card(0, 2));
		deck.add(new Card(0, 2));
		deck.add(new Card(1, 2));
	}

	if(hand == 4) { //flush
		deck.add(new Card(0, 0));
		deck.add(new Card(0, 4));
		deck.add(new Card(0, 2));
		deck.add(new Card(0, 7));
		deck.add(new Card(0, 12));
	}

	if(hand == 5) { //straight
		deck.add(new Card(0, 2));
		deck.add(new Card(0, 3));
		deck.add(new Card(3, 4));
		deck.add(new Card(0, 5));
		deck.add(new Card(1, 6));
	}

	if(hand == 6) { //three of a kind 
		deck.add(new Card(0, 12));
		deck.add(new Card(0, 10));
		deck.add(new Card(3, 10));
		deck.add(new Card(0, 10));
		deck.add(new Card(1, 6));
	}

	if(hand == 7) { //two pair
		deck.add(new Card(0, 12));
		deck.add(new Card(0, 10));
		deck.add(new Card(3, 12));
		deck.add(new Card(0, 10));
		deck.add(new Card(1, 6));
	}

	if(hand == 8) { //jacks or better
		deck.add(new Card(0, 12));
		deck.add(new Card(0, 10));
		deck.add(new Card(3, 0));
		deck.add(new Card(0, 10));
		deck.add(new Card(1, 6));
	}

	if(hand == 9) { //low pair
		deck.add(new Card(0, 12));
		deck.add(new Card(0, 0));
		deck.add(new Card(3, 4));
		deck.add(new Card(0, 0));
		deck.add(new Card(1, 6));
	}

	if(hand == 10) { //high card
		deck.add(new Card(0, 12));
		deck.add(new Card(0, 10));
		deck.add(new Card(3, 0));
		deck.add(new Card(0, 3));
		deck.add(new Card(1, 6));
	}

		//Collections.sort(deck);
	}
	
	
	void shuffle() {
		Collections.shuffle(deck);
		Sound.playSound("shuffle",1);
	}

	Card draw() {
		Card card = null;
		try {
			card = deck.get(0);
			deck.remove(0);
		} catch (NullPointerException NPE) {System.out.println("No more cards in deck.");}
		return card;
	}
}
