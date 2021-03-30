package magic_clustering.io.parser;

import magic_clustering.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ParserDeck implements IParser<Deck> {

	private String cardsPath;
	private List<Card> cardsList;

	public ParserDeck(String cardsPath) {
		this.cardsPath = cardsPath;
		
		ParserCards parserCards = new ParserCards();
		cardsList = parserCards.parse(cardsPath);
	}

	@Override
	public Deck parse(String path) {
		HashMap<String, Integer> deckRequestedCards = new HashMap<>();

		String nomDeck = "";

		HashMap<Card, Integer> deckCards = new HashMap<>();


		try {
			// Récupère le nom du deck en fonction du nom du fichier
			String[] pathSplitted = path.split("/");
			nomDeck = pathSplitted[pathSplitted.length - 1].replaceAll("\\.[^.]*$", "");
			
			BufferedReader inputStream = new BufferedReader(new FileReader(path));
			String line;

			while((line = inputStream.readLine()) != null) {
				// Si la ligne commence par un numéro, c'est une carte et le nombre d'exemplaire
				if(!line.isEmpty() && line.matches("^[0-9]+\\ .*")) {
					Integer nombre = Integer.valueOf(line.substring(0, line.indexOf(" ")));
					String cardName = line.substring(line.indexOf(" ") + 1);

					// On ajoute la carte à la liste de celles que l'on souhaite
					deckRequestedCards.put(cardName, nombre);
				}
			}

			// On parcours chaques cartes pour trouver celles que l'on souhaite
			for(Card c : cardsList) {
				// Si la carte a le même nom, c'est celle que l'on cherche
				if(deckRequestedCards.containsKey(c.name)) {
					deckCards.put(c, deckRequestedCards.get(c.name));
					deckRequestedCards.remove(c.name);

					// Si on a trouvé toutes les cartes on stop
					if(deckRequestedCards.isEmpty())
						break;
				}
			}

			return new Deck(nomDeck, deckCards);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
