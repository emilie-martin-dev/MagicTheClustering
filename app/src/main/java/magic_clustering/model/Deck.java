package magic_clustering.model;

import java.util.HashMap;
import java.util.Map;

public class Deck {
	private HashMap<Card, Integer> cards;
	private HashMap<Integer, Integer> manaCurve;
	private HashMap<TypeEnum, HashMap<Integer, Integer>> manaCube;

	public Deck(HashMap<Card, Integer> cards, HashMap<Integer, Integer> manaCurve, HashMap<TypeEnum, HashMap<Integer, Integer>> manaCube) {
		this.cards = cards;
		this.manaCurve = manaCurve;
		this.manaCube = manaCube;
	}

	public HashMap<Card, Integer> getCards() {
		return this.cards;
	}

	public HashMap<Integer, Integer> getManaCurve() {
		return this.manaCurve;
	}

	public HashMap<TypeEnum, HashMap<Integer, Integer>> getManaCube() {
		return this.manaCube;
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
