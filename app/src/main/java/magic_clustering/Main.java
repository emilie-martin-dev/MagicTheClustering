package magic_clustering;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import magic_clustering.algo.Jaccard;

import magic_clustering.io.parser.ParserDeck;

import magic_clustering.model.Card;
import magic_clustering.model.TypeEnum;
import magic_clustering.model.Deck;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import magic_clustering.algo.Kmedoids;

public class Main {

	public static void main(String[] args) {
		/*ParserDeck parserDeck = new ParserDeck("data/cards.json");

		Deck deck = parserDeck.parse("data/tcdecks/zoo-1.txt");
		//System.out.println(""+deck);

		System.out.println("Liste des carte du deck : \n");
		for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
			System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Type de la carte : " + entry.getKey().getType() + " | Présence dans le set : " + entry.getValue());
		}

		System.out.println("\n"+ "Pour les courbes suivantes, une carte de cout - 1 correspond à un terrain");

		System.out.println("\n" + "Courbe de mana du deck : \n");
		for(Map.Entry<Integer, Integer> entry : deck.getManaCurve().entrySet()) {
			if(entry.getValue() != 0){
				System.out.println("Cout des cartes : " + entry.getKey() + " | nombre de cartes : " + entry.getValue());
			}
		}

		System.out.println("\n" + "Cube de mana du deck : \n");

		System.out.println("WIP");
		for(Map.Entry<TypeEnum, HashMap<Integer, Integer>> entry : deck.getManaCube().entrySet()) {
			System.out.println("\n" + "Type de carte : " + entry.getKey() + "\n");
			for(Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				if(entry2.getValue() != 0){
					System.out.println("Cout des cartes : " + entry2.getKey() + " | nombre de cartes : " + entry2.getValue());
				}
			}
		}

		System.out.println();
		System.out.println();
		System.out.println();
		
		Deck deckBis = parserDeck.parse("data/tcdecks/erhnamgeddon-15.txt");
		Deck deckTer = parserDeck.parse("data/tcdecks/the-deck-10.txt");
		
		ArrayList<Deck> listDeck = new ArrayList<>();
		listDeck.add(deck);
		listDeck.add(deckBis);
		listDeck.add(deckTer);

		Jaccard jaccard = new Jaccard(listDeck);*/

		/*HashMap<ArrayList<Deck>, Float> distanceDeJaccard = jaccard.jaccardDist();

		for(Map.Entry<ArrayList<Deck>, Float> forJacc : distanceDeJaccard.entrySet()){
			for(Deck d : forJacc.getKey())
				System.out.print(d.getName() + ", ");
			
			System.out.println( "distance de Jaccard : " + forJacc.getValue());
		}*/
		ParserDeck parserDeck = new ParserDeck("data/cards.json");
		List<Deck> decks = new ArrayList<>();
		File deckFolder = new File("data/tcdecks");
		for(File f : deckFolder.listFiles()) {
			decks.add(parserDeck.parse(f.getPath()));
		}
		
		Kmedoids kmedoids = new Kmedoids(decks, 75);
		HashMap<Integer, List<Deck>> clusters = kmedoids.compute();
		for(Entry<Integer, List<Deck>> entry : clusters.entrySet()) {
			System.out.println("=========================");
			System.out.println("Cluster numéro " + entry.getKey());
			System.out.println("=========================");
			
			for(Deck d : entry.getValue()) {
				System.out.println(d.getName());
			}
			
			System.out.println();
		}
	}
}