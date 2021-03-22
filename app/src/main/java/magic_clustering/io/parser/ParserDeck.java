package magic_clustering.io.parser;

import magic_clustering.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.nio.file.Files;

public class ParserDeck implements IParser<Deck> {

	private String cardsPath;

	public ParserDeck(String cardsPath) {
		this.cardsPath = cardsPath;
	}

	@Override
	public Deck parse(String path) {
		HashMap<Card, Integer> deckCards = new HashMap<>();
		HashMap<String, Integer> deckRequestedCards = new HashMap<>();

		ParserCards parserCards = new ParserCards();
		List<Card> cardsList = parserCards.parse(cardsPath);

		int parseNomDebut = 0;
		int parseNomFin = 0;
		String nomDeck = "";


		try {

			for(int nbrCaractere = 0; nbrCaractere < path.length() ; nbrCaractere++){
				if(Character.toString(path.charAt(nbrCaractere)).equals(".")){
					parseNomFin = nbrCaractere;
				}
				else if(Character.toString(path.charAt(nbrCaractere)).equals("/")){
					parseNomDebut = nbrCaractere;
				}
			}

			for(parseNomDebut+=1; parseNomDebut < parseNomFin; parseNomDebut++){
				nomDeck+=path.charAt(parseNomDebut);
			}
			
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
					deckCards.put(c, deckRequestedCards.get(c.name));
					deckRequestedCards.remove(c.name);

					// Si on a trouvé toutes les cartes on stop
					if(deckRequestedCards.isEmpty())
						break;
				}
			}

			return new Deck(nomDeck,deckCards);
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
