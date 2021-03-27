package magic_clustering.algo;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import magic_clustering.model.*;

/**Cette classe nous permet de calculer la distance entre deux decks en fonction:
 * - du nombre de présence de chaque carte dans les deux decks.
 *
 * Pour se faire, on réclamme à l'utilisateur:
 * - un deck que le va comparer au autres (deck)
 * - une liste de decks (listDeck) que l'on va comparer au deck principale (deck)
 **/
public class Jaccard {

	public List<Deck> listDeck;

	public Jaccard(List<Deck> listDeck) {
		this.listDeck = listDeck;
	}

	/**
	 * Cet algorithme va renvoyer:
	 * - la carte dont on évalue la distance (Card).
	 * - la liste des deux decks contenant la carte que l'on évalue (le deck que l'on compare avec l'autre deck).
	 * - la distance entre chaque carte du deck (deck) et son omologue dans les autres deck (listDeck).
	 *
	 * Pour récuperer la distance de jaccard entre deux même cartes de decks séparés on fait:
	 * 1) parcourir le premier deck (this.deck).
	 * 2) parcourir la liste de deck (this.listDeck) pour récuperer les decks à comparer un à un.
	 * 3) parcourir le deck récupérer de la liste de Deck (this.listDeck).
	 * 4) comparer le nom des cartes
	 * 5) si il s'agit du même :
	 * 		- on ajoute le premier deck et celui que l'on compare à une liste (theDecks)
	 * 		- on calcul la distance de jaccard (1 - (minimum de representation de la cartedans les deux decks)/(maximum de représentation de la carte dans les deux decks))
	 * 		- on ajoute la liste de decks (theDecks) et la carte commune à un HashMap (theCardDecks)
	 * 		- on ajoute le HashMap précédemment créer (theCardDecks) et la distance de Jaccard (distanceDeJaccard) à un nouvel HashMap (jack_ard)
	 * 6) on retourne le nouvel HashMap (jack_ard), ce dernier peut être vide si aucune des cartes n'est communes entre les différets decks.
	 * **/
	 public HashMap<Deck, HashMap<Deck, Float>> jaccardDist(){
		HashMap<Deck, HashMap<Deck, Float>> jaccardMatrice = new HashMap<>();

		for( int i = 0; i < this.listDeck.size(); i++){
			for(int j = 0 ; j < this.listDeck.size() ; j++) {
				float min = 0;
				float max = 0;

				Deck deckI = this.listDeck.get(i);
				Deck deckJ = this.listDeck.get(j);
				
				if(i == j) {						
					ArrayList<Deck> theDecks = new ArrayList<>();

					theDecks.add(deckJ);
					theDecks.add(deckI);

					if(!jaccardMatrice.containsKey(deckJ))
						jaccardMatrice.put(deckJ, new HashMap<>());
					
					jaccardMatrice.get(deckJ).put(deckI, 0f);
					continue;
				}
				
				HashMap<Card, Integer> lesCartesDeDeckINonCommunes = new HashMap<>();

				for(Map.Entry<Card, Integer> entryBis : deckI.getCards().entrySet()) {
					lesCartesDeDeckINonCommunes.put(entryBis.getKey(), entryBis.getValue());
				}

				HashMap<Card, Integer> lesCartesDeDeckJNonCommune = new HashMap<>();

				for(Map.Entry<Card, Integer> entry : deckJ.getCards().entrySet()) {
					lesCartesDeDeckJNonCommune.put(entry.getKey(), entry.getValue());
				}

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
	

				float dist = 1-(min/max);
				
				if(!jaccardMatrice.containsKey(deckJ))
					jaccardMatrice.put(deckJ, new HashMap<>());
				
				jaccardMatrice.get(deckJ).put(deckI, dist);
			}
		}
		
		return jaccardMatrice;
	}
}
