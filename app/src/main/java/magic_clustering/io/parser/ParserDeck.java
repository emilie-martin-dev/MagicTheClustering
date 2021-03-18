package magic_clustering.io.parser;

import magic_clustering.model.*;
import magic_clustering.model.TypeEnum.TypeEnume;

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
		init(deckManaCurve);
		Integer numbCard;

		HashMap<TypeEnume, HashMap<Integer, Integer>> deckManaCube = new HashMap<TypeEnume, HashMap<Integer, Integer>>();
		HashMap<Integer, Integer> artifact = new HashMap<>();
		init(artifact);
		HashMap<Integer, Integer> creature = new HashMap<>();
		init(creature);
		HashMap<Integer, Integer> instant = new HashMap<>();
		init(instant);
		HashMap<Integer, Integer> land= new HashMap<>();
		init(land);
		HashMap<Integer, Integer> enchantment= new HashMap<>();
		init(enchantment);
		HashMap<Integer, Integer> sorcery = new HashMap<>();
		init(sorcery);
		
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
						numbCard = deckManaCurve.get(-1) + deckRequestedCards.get(c.name);
						deckManaCurve.put(-1, numbCard);
						land.put(-1, numbCard);
					}
					else{
						//HashMap courbe de mana
						numbCard = deckManaCurve.get(c.manaCost) + deckRequestedCards.get(c.name);
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
			deckManaCube.put(TypeEnume.Artifact, artifact);
			deckManaCube.put(TypeEnume.Creature, creature);
			deckManaCube.put(TypeEnume.Enchantment, enchantment);
			deckManaCube.put(TypeEnume.Instant, instant);
			deckManaCube.put(TypeEnume.Land, land);
			deckManaCube.put(TypeEnume.Sorcery, sorcery);

			return new Deck(deckCards, deckManaCurve, deckManaCube);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	//gestion des HashMap<Integer, Integer> du cube de mana
	private void createManaCube(HashMap<Integer, Integer> typeMap, HashMap<String, Integer> deck, Card c, String type, Integer numbCard){
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

	/*initialisation des Hashmap<Integer, Integer avec les clé allant de -1 (terrain)
	à 10 (cout max cartes de 93-94) et valeur à 0 pour faciliter les comparaisons de
	deux courbes/cubes de mana*/
	private void init(HashMap<Integer, Integer> mana){
		for(int i = -1; i <= 10; i++){
			mana.put(i,0);
		}
	}
}
