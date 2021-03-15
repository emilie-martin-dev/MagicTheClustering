package magic_clustering;

import java.util.Map;
import java.util.Scanner;

import magic_clustering.io.parser.ParserDeck;
import magic_clustering.model.Card;

import magic_clustering.model.Deck;

public class Main {

	public static void main(String[] args) {
		ParserDeck parserDeck = new ParserDeck("data/cards.json");

		System.out.println("entrez le nom du deck que vous souhaitez affichez : ");
		Scanner scan = new Scanner(System.in);
		int i = 0;

		while(i == 0){
			try{
				String deckName = scan.nextLine();

				Deck deck = parserDeck.parse("data/tcdecks/"+deckName);
				//System.out.println(""+deck);

				for(Map.Entry<Card, Integer> entry : deck.getCards().entrySet()) {
					System.out.println("Nom de la carte : " + entry.getKey().getName() + " | Cout en mana : " + entry.getKey().getManaCost()+ " | Type de la carte : " + entry.getKey().getType() + " | Présence dans le set : " + entry.getValue());
				}
				 i=1;
			}catch(Exception e){
				System.out.println("Le deck selectionner n'exite pas, veuillez entrez un nom de deck existant svp.");
			}
		}
	}

}
