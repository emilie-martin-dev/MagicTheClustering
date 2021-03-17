package magic_clustering.io.parser;

import magic_clustering.model.*;
import magic_clustering.model.Deck.TypeEnum.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ParserDeck implements IParser<Deck> {

	private String cardsPath;

	public ParserDeck(String cardsPath) {
		this.cardsPath = cardsPath;
	}

	@Override
	public Deck parse(String path) {
		HashMap<String, Integer> deckRequestedCards = new HashMap<>();

		HashMap<Card, Integer> deckCards = new HashMap<>();

		HashMap<Integer, Integer> deckManaCurve = new HashMap<>();
		Integer numbCard;

		HashMap<magic_clustering.model.Deck.TypeEnum, HashMap<Integer, Integer>> deckManaCube = new HashMap<magic_clustering.model.Deck.TypeEnum, HashMap<Integer, Integer>>();
		HashMap<Integer, Integer> artifact = new HashMap<>();
		HashMap<Integer, Integer> creature = new HashMap<>();
		HashMap<Integer, Integer> instant = new HashMap<>();
		HashMap<Integer, Integer> land= new HashMap<>();
		HashMap<Integer, Integer> enchantment= new HashMap<>();
		HashMap<Integer, Integer> sorcery = new HashMap<>();

		ParserCards parserCards = new ParserCards();
		List<Card> cardsList = parserCards.parse(cardsPath);

		try {
			BufferedReader inputStream = new BufferedReader(new FileReader(path));
			String line;

			while((line = inputStream.readLine()) != null) {
				if(!line.isEmpty() && line.matches("^[0-9]+\\ .*")) {
					Integer nombre = Integer.valueOf(line.substring(0, line.indexOf(" ")));
					String cardName = line.substring(line.indexOf(" ") + 1);

					deckRequestedCards.put(cardName, nombre);
				}
			}

			for(Card c : cardsList) {
				if(deckRequestedCards.containsKey(c.name)) {
					//création de l'ensemble des cartes
					deckCards.put(c, deckRequestedCards.get(c.name));
					//création de la courbe de mana et manaCube
					//gestion cas particulier des terrains pour la courbes et le cube
					if(c.type.contains("Land")){
						if(deckManaCurve.containsKey(-1)){
							numbCard = deckManaCurve.get(-1) + deckRequestedCards.get(c.name);
						}
						else{
							numbCard = deckRequestedCards.get(c.name);
						}
						deckManaCurve.put(-1, numbCard);
						land.put(-1, numbCard);
					}
					else{
						//HashMap courbe de mana
						if(deckManaCurve.containsKey(c.manaCost)){
							numbCard = deckManaCurve.get(c.manaCost) + deckRequestedCards.get(c.name);
						}
						else{
							numbCard = deckRequestedCards.get(c.name);
						}
						deckManaCurve.put(c.manaCost, numbCard);

						//Hashmap du cube de mana: partie HashMap<Integer, Integer> du cube de mana
						createManaCube(artifact, deckRequestedCards, c, "Artifact", numbCard);
						createManaCube(creature, deckRequestedCards, c, "Creature", numbCard);
						createManaCube(enchantment, deckRequestedCards, c, "Enchantment", numbCard);
						createManaCube(instant, deckRequestedCards, c, "Instant", numbCard);
						createManaCube(sorcery, deckRequestedCards, c, "Sorcery", numbCard);
					}
					deckRequestedCards.remove(c.name);

					// Si on a trouvé toutes les cartes on stop
					if(deckRequestedCards.isEmpty())
						break;
				}
			}
			//Hashmap du cube de mana
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Artifact, artifact);
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Creature, creature);
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Enchantment, enchantment);
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Instant, instant);
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Land, land);
			deckManaCube.put(magic_clustering.model.Deck.TypeEnum.Sorcery, sorcery);

			return new Deck(deckCards, deckManaCurve, deckManaCube);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private void createManaCube( HashMap<Integer, Integer> typeMap, HashMap<String, Integer> deck, Card c, String type, Integer numbCard){

		if(c.type.contains(type)){
			if(typeMap.containsKey(c.manaCost)){
				numbCard = typeMap.get(c.manaCost) + deck.get(c.name);
			}
			else{
				numbCard = deck.get(c.name);
			}
			typeMap.put(c.manaCost, numbCard);
		}

	}
}
