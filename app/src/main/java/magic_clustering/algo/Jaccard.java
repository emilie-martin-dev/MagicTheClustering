package magic_clustering.algo;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import magic_clustering.model.*;

public class Jaccard {

	public List<Deck> listDeck;

	public Jaccard(List<Deck> listDeck) {
		this.listDeck = listDeck;
	}

	public HashMap<Deck, HashMap<Deck, Float>> jaccardDist(){
		HashMap<Deck, HashMap<Deck, Float>> jaccardMatrice = new HashMap<>();
		for(int i = 0; i < this.listDeck.size(); i++){
			for(int j = 0 ; j < this.listDeck.size() ; j++) {
				float min = 0;
				float max = 0;

				Deck deckI = this.listDeck.get(i);
				Deck deckJ = this.listDeck.get(j);
				
				// Si les deux decks sont les meme, la distance est de 0
				if(i == j) {						
					ArrayList<Deck> theDecks = new ArrayList<>();

					theDecks.add(deckJ);
					theDecks.add(deckI);

					if(!jaccardMatrice.containsKey(deckJ))
						jaccardMatrice.put(deckJ, new HashMap<>());
					
					jaccardMatrice.get(deckJ).put(deckI, 0f);
					
					continue;
				}
				
				// Hashmap pour connaitres les cartes non communes dans les decks
				HashMap<Card, Integer> lesCartesDeDeckINonCommunes = new HashMap<>();

				for(Map.Entry<Card, Integer> entryBis : deckI.getCards().entrySet()) {
					lesCartesDeDeckINonCommunes.put(entryBis.getKey(), entryBis.getValue());
				}

				HashMap<Card, Integer> lesCartesDeDeckJNonCommune = new HashMap<>();

				for(Map.Entry<Card, Integer> entry : deckJ.getCards().entrySet()) {
					lesCartesDeDeckJNonCommune.put(entry.getKey(), entry.getValue());
				}

				// On calcule les cartes en communes
				for(Map.Entry<Card, Integer> entryJ : deckJ.getCards().entrySet()){
					for(Map.Entry<Card, Integer> entryI : deckI.getCards().entrySet()) {

						if(entryJ.getKey().getName().equals(entryI.getKey().getName())){
							if(entryJ.getValue() <= entryI.getValue()){
								min += entryJ.getValue();
								max += entryI.getValue();
							}else{
								max += entryJ.getValue();
								min += entryI.getValue();
							}

							lesCartesDeDeckINonCommunes.remove(entryI.getKey());
							lesCartesDeDeckJNonCommune.remove(entryJ.getKey());

							break;
						}	
					}
					
					if(lesCartesDeDeckINonCommunes.isEmpty() || lesCartesDeDeckJNonCommune.isEmpty()){
						break;
					}
				}

				// On traite les cartes non communes
				if(!lesCartesDeDeckINonCommunes.isEmpty()){
					for(Map.Entry<Card, Integer> entryQuatre : lesCartesDeDeckINonCommunes.entrySet()) {
						max += entryQuatre.getValue();
						min += 0;
					}
				}
				if(!lesCartesDeDeckJNonCommune.isEmpty()){
					for(Map.Entry<Card, Integer> entryQuatre : lesCartesDeDeckJNonCommune.entrySet()) {
						max += entryQuatre.getValue();
						min += 0;
					}
				}
	
				// On calcule la distance de Jaccard
				float dist = 1-(min/max);
				
				if(!jaccardMatrice.containsKey(deckJ))
					jaccardMatrice.put(deckJ, new HashMap<>());
				
				jaccardMatrice.get(deckJ).put(deckI, dist);
			}
		}
		
		return jaccardMatrice;
	}
}
