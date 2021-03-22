package magic_clustering;

import java.util.Map;
import magic_clustering.io.parser.ParserDeck;
import magic_clustering.model.*;
import magic_clustering.algo.Jaccard2;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ParserDeck parserDeck = new ParserDeck("data/cards.json");

        Deck deck = parserDeck.parse("data/tcdecks/ponza-1.txt");
        Deck deckBis = parserDeck.parse("data/tcdecks/erhnamgeddon-15.txt");
        Deck deckTer = parserDeck.parse("data/tcdecks/the-deck-10.txt");
        /*System.out.println(""+deck);

        for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
            System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Présence dans le set : " + entry.getValue());
        }*/

        ArrayList<Deck> listDeck = new ArrayList<Deck>();
        listDeck.add(deck);
        listDeck.add(deckBis);
        listDeck.add(deckTer);
        
        Jaccard2 jackard = new Jaccard(deck, listDeck);

        HashMap<ArrayList<Deck>, Float> distanceDeJaccard = jackard.jaccardDistEntre2Deck();
        
        for(Map.Entry<ArrayList<Deck>, Float> forJacc : distanceDeJaccard.entrySet()){
			System.out.println( "distance de Jaccard : " + forJacc.getValue());
		}
    }
}
