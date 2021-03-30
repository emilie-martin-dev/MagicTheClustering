package magic_clustering;

import java.io.File;

import magic_clustering.io.parser.ParserDeck;

import magic_clustering.model.Deck;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import magic_clustering.algo.Kmedoids;
import magic_clustering.model.Card;
import magic_clustering.model.TypeEnum;

public class Main {

	private static final String PATH_CARDS = "data/cards.json";
	private static final String PATH_DECKS = "data/tcdecks";
	private static final int K_MEDOIDS = 75;
	
	public static void main(String[] args) {
		ParserDeck parserDeck = new ParserDeck(PATH_CARDS);

		//showManaStats(parserDeck);

		// Récupère tous les decks du dossier
		List<Deck> decks = new ArrayList<>();
		File deckFolder = new File(PATH_DECKS);
		for(File f : deckFolder.listFiles()) {
			decks.add(parserDeck.parse(f.getPath()));
		}
		
		// Effectue le clustering des deck
		Kmedoids kmedoids = new Kmedoids(decks, K_MEDOIDS);
		HashMap<Integer, List<Deck>> clusters = kmedoids.compute();
		
		// Affiche les résultats
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
	
	
	public static void showManaStats(ParserDeck parserDeck) {
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

		for(Map.Entry<TypeEnum, HashMap<Integer, Integer>> entry : deck.getManaCube().entrySet()) {
			System.out.println("\n" + "Type de carte : " + entry.getKey() + "\n");
			for(Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				if(entry2.getValue() != 0){
					System.out.println("Cout des cartes : " + entry2.getKey() + " | nombre de cartes : " + entry2.getValue());
				}
			}
		}
	}
}