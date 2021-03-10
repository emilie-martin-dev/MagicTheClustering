package magic_clustering.model;

import java.util.HashMap;

public class Deck {

	private HashMap<Card, Integer> cards;

	public Deck(HashMap<Card, Integer> cards) {
		this.cards = cards;
	}

	public HashMap<Card, Integer> getDeck() {
		return this.cards;
	}

}
