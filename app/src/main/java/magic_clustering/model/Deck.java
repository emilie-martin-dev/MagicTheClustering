package magic_clustering.model;

import java.util.HashMap;
import java.util.Map;

public class Deck {

	private HashMap<Card, Integer> cards;

	public Deck(HashMap<Card, Integer> cards) {
		this.cards = cards;
	}

	public HashMap<Card, Integer> getCards() {
		return this.cards;
	}

	@Override
	public String toString() {
		StringBuilder bufferCards = new StringBuilder();
		
		bufferCards.append("[");
		for(Map.Entry<Card, Integer> entry : cards.entrySet()) {
			bufferCards.append("{count=");
			bufferCards.append(entry.getValue());
			bufferCards.append(", card=");
			bufferCards.append(entry.getKey());
			bufferCards.append("}, ");
		}
		
		bufferCards.delete(bufferCards.length()-2, bufferCards.length());
		
		bufferCards.append("]");
		
		return "Deck{" + "cards=" + bufferCards.toString() + '}';
	}

}
