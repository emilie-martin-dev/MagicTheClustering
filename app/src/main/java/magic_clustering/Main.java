package magic_clustering;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import magic_clustering.io.parser.ParserDeck;
import magic_clustering.model.Card;
import magic_clustering.model.Deck.TypeEnum.*;
import magic_clustering.model.Deck;

public class Main {

	public static void main(String[] args) {
		ParserDeck parserDeck = new ParserDeck("data/cards.json");

		Deck deck = parserDeck.parse("data/tcdecks/zoo-1.txt");
		//System.out.println(""+deck);

		System.out.println("Liste des carte du deck : \n");
		for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
			System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Type de la carte : " + entry.getKey().getType() + " | Présence dans le set : " + entry.getValue());
		}

		System.out.println("\n"+ "Pour les courbes suivantes, une carte de cout - 1 correspond à un terrain");

		System.out.println("\n" + "Courbe de mana du deck : \n");
		for(Map.Entry<Integer, Integer> entry : deck.getManaCurve().entrySet()) {
			System.out.println("Cout des cartes : " + entry.getKey() + " | nombre de cartes : " + entry.getValue());
		}

		System.out.println("\n" + "Cube de mana du deck : \n");

		System.out.println("WIP");
		for(Map.Entry<magic_clustering.model.Deck.TypeEnum, HashMap<Integer, Integer>> entry : deck.getManaCube().entrySet()) {
			System.out.println("\n" + "Type de carte : " + entry.getKey() + "\n");
			for(Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
				System.out.println("Cout des cartes : " + entry2.getKey() + " | nombre de cartes : " + entry2.getValue());
			}
		}
	}

}
