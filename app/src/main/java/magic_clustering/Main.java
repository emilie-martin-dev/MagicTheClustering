package magic_clustering;

import magic_clustering.io.parser.ParserDeck;

import io.parser.*;
import magic_clustering.model.Deck;

public class Main {

	public static void main(String[] args) {
		ParserDeck parserDeck = new ParserDeck();
		Deck deck = parserDeck.parse("ponza-1.txt");
	}

}
