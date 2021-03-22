package magic_clustering.algo;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import magic_clustering.model.*;
import magic_clustering.io.parser.ParserDeck;

/**Cette classe nous permet de calculer la distance entre deux decks en fonction:
 * - du nombre de présence de chaque carte dans les deux decks.
 *
 * Pour se faire, on réclamme à l'utilisateur:
 * - un deck que le va comparer au autres (deck)
 * - une liste de decks (listDeck) que l'on va comparer au deck principale (deck)
 **/
public class Jaccard {

    public Deck deck;
    public ArrayList<Deck> listDeck;

    public Jaccard(Deck deck, ArrayList<Deck> listDeck) {
        this.deck = deck;
        this.listDeck = listDeck;
    }

	/**Cet algorithme va renvoyer:
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
	 public HashMap<ArrayList<Deck>, Float> jaccardDistEntre2Deck(){
		 
		float min = 0;
		float max = 0;

		HashMap<ArrayList<Deck>, Float> jack_ard = new HashMap<ArrayList<Deck>, Float>();

        for( int i = 0; i < this.listDeck.size(); i++){

			Deck deckY = this.listDeck.get(i);

			HashMap<Card, Integer> lesCartesDeDeckX = new HashMap<Card, Integer>();
			HashMap<Card, Integer> lesCartesDeDeckXBis = new HashMap<Card, Integer>();
			
	        for(Map.Entry<Card, Integer> entry : this.deck.getCards().entrySet()) {
				lesCartesDeDeckX.put(entry.getKey(), entry.getValue());
				lesCartesDeDeckXBis.put(entry.getKey(), entry.getValue());
	   		}

			HashMap<Card, Integer> lesCartesDeDeckY = new HashMap<Card, Integer>();
			HashMap<Card, Integer> lesCartesDeDeckYBis = new HashMap<Card, Integer>();
			
   		 	for(Map.Entry<Card, Integer> entryBis : deckY.getCards().entrySet()) {
   				lesCartesDeDeckY.put(entryBis.getKey(), entryBis.getValue());
				lesCartesDeDeckYBis.put(entryBis.getKey(), entryBis.getValue());
			}
			
			System.out.println("liste i :"+i+" Carte communes: ");
			
			for(Map.Entry<Card, Integer> entryX : lesCartesDeDeckX.entrySet()){
				for(Map.Entry<Card, Integer> entryY : lesCartesDeDeckY.entrySet()) {

					if(entryX.getKey().getName().equals(entryY.getKey().getName())){
						System.out.println("entryX : "+entryX.getKey().getName()+" - "+entryX.getValue()+" | entryY : "+entryY.getKey().getName()+" - "+entryY.getValue());
						if(entryX.getValue() <= entryY.getValue()){
							min += entryX.getValue();
							max += entryY.getValue();
						}else{
							max += entryX.getValue();
							min += entryY.getValue();
						}
						
						lesCartesDeDeckYBis.remove(entryY.getKey());
						lesCartesDeDeckXBis.remove(entryX.getKey());

						break; //pk le break?
					}	
				}
				if(lesCartesDeDeckYBis.isEmpty() || lesCartesDeDeckXBis.isEmpty()){
					break;
				}
			}

			if(! lesCartesDeDeckYBis.isEmpty()){
				for(Map.Entry<Card, Integer> entryQuatre : lesCartesDeDeckYBis.entrySet()) {
					max += entryQuatre.getValue();
					//rajout:
					min += 0;
				}
			}
			if(! lesCartesDeDeckXBis.isEmpty()){
				for(Map.Entry<Card, Integer> entryQuatre : lesCartesDeDeckXBis.entrySet()) {
					max += entryQuatre.getValue();
					//rajout
					min += 0;
				}
			}
			System.out.println("min " + min + " max " + max);
			
			//rajout
			ArrayList<Deck> theDecks = new ArrayList<Deck>();
			
			theDecks.add(this.deck);
			theDecks.add(deckY);

			float div = 1-(min/max);
			
			System.out.println("Indice de jaccard : "+div);
			//rajout
			System.out.println("length he ceck: "+ theDecks.size());

			jack_ard.put(theDecks, div);

			System.out.println();
			
			min = 0;
			max = 0;
			
		}
		
        return jack_ard;
	}
}
