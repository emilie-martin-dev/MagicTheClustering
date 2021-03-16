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
						if(deckManaCurve.containsKey(c.manaCost)){
							numbCard = deckManaCurve.get(c.manaCost) + deckRequestedCards.get(c.name);
						}
						else{
							numbCard = deckRequestedCards.get(c.name);
						}
						deckManaCurve.put(c.manaCost, numbCard);
						//Hashmap du cube de mana
						if(c.type.contains("Artifact")){
							if(artifact.containsKey(c.manaCost)){
								numbCard = artifact.get(c.manaCost) + deckRequestedCards.get(c.name);
							}
							else{
								numbCard = deckRequestedCards.get(c.name);
							}
							artifact.put(c.manaCost, numbCard);
						}

						else if(c.type.contains("Creature")){
							if(creature.containsKey(c.manaCost)){
								numbCard = creature.get(c.manaCost) + deckRequestedCards.get(c.name);
							}
							else{
								numbCard = deckRequestedCards.get(c.name);
							}
							creature.put(c.manaCost, numbCard);
						}

						else if(c.type.contains("Enchantment")){
							if(enchantment.containsKey(c.manaCost)){
								numbCard = enchantment.get(c.manaCost) + deckRequestedCards.get(c.name);
							}
							else{
								numbCard = deckRequestedCards.get(c.name);
							}
							enchantment.put(c.manaCost, numbCard);
						}

						else if(c.type.contains("Instant")){
							if(instant.containsKey(c.manaCost)){
								numbCard = instant.get(c.manaCost) + deckRequestedCards.get(c.name);
							}
							else{
								numbCard = deckRequestedCards.get(c.name);
							}
							instant.put(c.manaCost, numbCard);
						}

						else if(c.type.contains("Sorcery")){
							if(sorcery.containsKey(c.manaCost)){
								numbCard = sorcery.get(c.manaCost) + deckRequestedCards.get(c.name);
							}
							else{
								numbCard = deckRequestedCards.get(c.name);
							}
							sorcery.put(c.manaCost, numbCard);
						}
					}
					deckRequestedCards.remove(c.name);

					// Si on a trouvé toutes les cartes on stop
					if(deckRequestedCards.isEmpty())
						break;
				}
			}
			//temporaire
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

}
