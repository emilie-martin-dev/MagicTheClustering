package magic_clustering;

import java.util.Map;
import magic_clustering.io.parser.ParserDeck;
import magic_clustering.model.Card;

import magic_clustering.model.Deck;

public class Main {

	public static void main(String[] args) {
		ParserDeck parserDeck = new ParserDeck("data/cards.json");
		
		Deck deck = parserDeck.parse("data/tcdecks/ponza-1.txt");
		System.out.println(""+deck);
		
		for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
			System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Présence dans le set : " + entry.getValue());
		}
	}

}
