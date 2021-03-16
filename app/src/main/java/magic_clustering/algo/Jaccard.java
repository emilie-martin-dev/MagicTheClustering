package magic_clustering.algo;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import magic_clustering.model.*;
import magic_clustering.io.parser.ParserDeck;

/**Cette classe nous permet de calculer la distance entre deux decks en fonction:
 * - du nombre de présence de chaque carte dans les deux decks.
 **/
public class Jaccard {

    public Deck deck;
    public ArrayList<Deck> listDeck;
	
	//on crée le constructeur de la classe qui prend en paramètre un deck (deck) que nous allons comparer à une liste de deck (listDeck)
    public Jaccard(Deck deck, ArrayList<Deck> listDeck) {
        this.deck = deck;
        this.listDeck = listDeck;
    }

	/**Cet algorithme va renvoyer:
	 * - la carte dont on évalue la distance (Card)
	 * -
	 * - la distance entre chaque carte du deck (deck) et son omologue dans les autres deck (listDeck):
	 * - 
	 * 
	 * **/
    public HashMap<HashMap<Card, ArrayList<Deck>>, Integer> jaccardDistEntre2Cartes(){

		int nombreDeJaccard;
		int distanceDeJaccard;
		
        HashMap<Card, ArrayList<Deck>> theCardDecks = new HashMap<Card, ArrayList<Deck>>();
        HashMap<HashMap<Card, ArrayList<Deck>>, Integer> jack_ard = new HashMap<HashMap<Card, ArrayList<Deck>>, Integer>();

        for(Map.Entry<Card, Integer> entry : this.deck.getCards().entrySet()) {

            for( int i = 0; i < this.listDeck.size(); i++){
				
                Deck deckY = this.listDeck.get(i);
                
                for(Map.Entry<Card, Integer> entryBis : deckY.getCards().entrySet()) {
					
					if(entryBis.getKey().getName() == entry.getKey().getName()){
						ArrayList<Deck> theDecks = new ArrayList<Deck>();
						theDecks.add(deck);
						theDecks.add(deckY);

						theCardDecks.put(entry.getKey(),theDecks);

						if(entry.getValue() == entryBis.getValue()){
							nombreDeJaccard = 1;
						}
						if(entry.getValue() < entryBis.getValue()){
							nombreDeJaccard = entry.getValue()/entryBis.getValue();
						}
						else{
							nombreDeJaccard = entryBis.getValue()/entry.getValue();
						}
						distanceDeJaccard = 1 - nombreDeJaccard;
						jack_ard.put(theCardDecks, distanceDeJaccard);
					}
                }
            }
        }
        return jack_ard;
    }
}
