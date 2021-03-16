package magic_clustering;

import java.util.Map;
import magic_clustering.io.parser.ParserDeck;
import magic_clustering.model.*;
import magic_clustering.algo.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ParserDeck parserDeck = new ParserDeck("data/cards.json");

        Deck deck = parserDeck.parse("data/tcdecks/ponza-1.txt");
        Deck deckBis = parserDeck.parse("data/tcdecks/erhnamgeddon-15.txt");
        Deck deckTer = parserDeck.parse("data/tcdecks/the-deck-10.txt");
        System.out.println(""+deck);

        for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
            System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Présence dans le set : " + entry.getValue());
        }

        ArrayList<Deck> listDeck = new ArrayList<Deck>();
        listDeck.add(deck);
        listDeck.add(deckTer);
        
        Jaccard jackard = new Jaccard(deck, listDeck);
		
        HashMap<HashMap<Card, ArrayList<Deck>>, Integer> distanceDeJaccard = jackard.jaccardDistEntre2Cartes();

        for(Map.Entry<HashMap<Card, ArrayList<Deck>>,Integer> forJacc : distanceDeJaccard.entrySet()){
            HashMap<Card, ArrayList<Deck>> hashMap = forJacc.getKey();

            int nombre = forJacc.getValue();
            for(Map.Entry<Card,ArrayList<Deck>> forDansJacc : hashMap.entrySet()){
				if(forDansJacc.getKey() == null){
					System.out.println("il n'y a pas de carte en commun");
				}else{
					System.out.println("Nom de la carte : " + forDansJacc.getKey().getName() + "premier deck : " + forDansJacc.getValue().get(0) + "second deck : " + forDansJacc.getValue().get(1) + "distance de Jaccard : " + forJacc.getValue());
				}
            }
        }/*unt=3, card=Card{manaCost=0, name=Forest}}]}*/
    }
}
