package net.gamebacon.videopoker;

import net.gamebacon.videopoker.util.CardManager;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

class Game {

	private final int DEAL_DELAY_MILLIS = 120;
	private final int POST_DEAL_DELAY_MILLIS = 500;
	public static final int STARTING_BALANCE = 100;

	private Deck deck;
	String currentHand;
	int handValue;
	boolean gameover = true;

	final String[] handName = {
	"Royal flush",
	"Straight flush",
	"Four of a kind",
	"Full house",
	"Flush",
	"Straight",
	"Three of a kind",
	"Two pair",
	"Jack's or better",
	"Low pair",
	"High Card"
	};

	public Game() {
		deck = new Deck();
		deck.shuffle();
		newCards(true);
		gameover = false;
	}
	
	void newCards(boolean homo) {
			for(int j = 0; j < 5; j++)
				if(homo || !VideoPoker.cardContainer[j].isSelected())
					VideoPoker.cardContainer[j].resetFields();
			try { Thread.sleep(POST_DEAL_DELAY_MILLIS); } catch (Exception ex) {}

			for(int i = 0; i < 5; i++) {
				if(homo || !VideoPoker.cardContainer[i].isSelected()) {
					VideoPoker.cardContainer[i].newCard(deck.draw());
					try { Thread.sleep(DEAL_DELAY_MILLIS); } catch (Exception ex) {}
				} else
					VideoPoker.cardContainer[i].toggleSelect(false);
			}
		handValue = getHandValue(getHand());
		currentHand = handName[handValue];
	}



	void finish() {
		newCards(false);
		gameover = true;
	}	

	private Card[] getHand() {
		Card[] output = new Card[5];
		for(int i = 0; i < 5; i++) {
			output[i] = VideoPoker.cardContainer[i].getCard();
		}	
		return output;
	}
	private Card[] sortHand(Card[] cards_) {
		boolean sorted;
		do {
			sorted = true;	
			for(int i = 0; i < 4; i++)
				if(cards_[i].getValue() > cards_[i + 1].getValue()) {
					Card temp = cards_[i + 1];				
					cards_[i + 1] = cards_[i];
					cards_[i] = temp;
					sorted = false;
				}	
		} while (!sorted); 
		return cards_;
	}
	private int getHandValue(Card[] cards_) {
		if(cards_.length != 5)
			return -1;
		boolean flush;
		boolean straight = true;

		//Flush		
			Set<String> suit = new TreeSet<String>();
			for(Card c : cards_)
				suit.add(CardManager.suitsSymbols[c.getSuit()]);
			flush = suit.size() == 1;


		//Straight
			//Sort
			cards_ = sortHand(cards_);		
			//Check for A-5 straight
			boolean A5Straight = false;
			if(cards_[0].getValue() == 0 && cards_[4].getValue() == 12) {
				for(int i = 0; i < 3; i++) 
					if(cards_[i].getValue() + 1 != cards_[i + 1].getValue())
						straight = false;
				//rotate order from 2-A to A-5
				if(straight) {
					A5Straight = true;
					Card[] tempArr = new Card[5];
					tempArr[0] = cards_[4];
					for(int i = 1; i < 5; i++)
						tempArr[i] = cards_[i-1];
					cards_ = tempArr;
				}
			}
			//Check for any straight
			if(!A5Straight)
				for(int i = 0; i < 4; i++) 
					if(cards_[i].getValue() + 1 != cards_[i + 1].getValue())
						straight = false;


			if(flush)
				if(straight)
					if(cards_[4].getValue() == 12) //royal flush
						return 0;
					else
						return 1; //straight flush
				else
					return 4; //flush
			if(straight)
				return 5; //straight
				
			//Check for duplicates with a HashMap holding cardvalue and its quantity
			Map<String, Integer> pair = new HashMap<String, Integer>();
			for(int i = 0; i < 5; i++) {
				String value = CardManager.stringValues[cards_[i].getValue()];
				if(pair.containsKey(value))
					pair.put(value, pair.get(value) + 1);
				else
					pair.put(value, 1);
				

	
			}

			if(pair.size() != 5) { 
				if(pair.containsValue(4)) //four of a kind
					return 2;
				else if(pair.containsValue(3))  
					if(pair.containsValue(2)) //full house
						return 3;
					else //three of a kind
						return 6;
     			 else if(pair.size() == 3) //two pair
					return 7;
				else 
					for(int i = 0; i < 4; i++)
						if(pair.containsKey("JQKA".split("")[i]))
							if(pair.get("JQKA".split("")[i]) == 2)
								return 8; //jacks or better
					return 9; //low pair
			}			
			return 10; //High card
	}
}
