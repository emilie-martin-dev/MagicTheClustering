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
	 * - la liste des deux decks contenant la carte que l'on évalue (le deck que l'on compare avec l'autre deck)
	 * - la distance entre chaque carte du deck (deck) et son omologue dans les autres deck (listDeck):
	 * 
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
    
    /**on parcourt le hashMap de jaccardDistEntre2Cartes (voir main)
     * if deck = celui là alors bon: (moyenne des deux decks plus petite divisé par plus grande) ajout au hashmap
     * **/
    public HashMap<Integer, ArrayList<Deck>> jaccardDistEntre2DecksMoyenne(ArrayList<Deck> toutLesDecks){
		
		int moyenneJaccardCartes = 0;
		int nombresCartesCommunes = 0;
		
		Deck deckPremier = null;
		Deck deckComparer = null;
		
		HashMap<Integer, ArrayList<Deck>> distanceDeJaccardDeck = new HashMap<Integer, ArrayList<Deck>>();
		HashMap<HashMap<Card, ArrayList<Deck>>, Integer> distanceDeJaccard = jaccardDistEntre2Cartes();
		
		if(distanceDeJaccardDeck.isEmpty() == false){
			for(Map.Entry<HashMap<Card, ArrayList<Deck>>,Integer> forJacc : distanceDeJaccard.entrySet()){
				HashMap<Card, ArrayList<Deck>> hashMap = forJacc.getKey();

				for(Map.Entry<Card,ArrayList<Deck>> forDansJacc : hashMap.entrySet()){
					deckComparer = forDansJacc.getValue().get(0);
					Deck deckSecond = forDansJacc.getValue().get(1);
					
					if(deckPremier == null){
						deckPremier = deckSecond;
					}if(deckPremier == deckSecond){
						moyenneJaccardCartes += forJacc.getValue();
						nombresCartesCommunes += 1;
					}else{
								
						ArrayList<Deck> theDecks = new ArrayList<Deck>();
			
						moyenneJaccardCartes = moyenneJaccardCartes/nombresCartesCommunes;
						
						theDecks.add(deckComparer);
						theDecks.add(deckPremier);
						distanceDeJaccardDeck.put(moyenneJaccardCartes, theDecks);
						
						deckPremier = deckSecond;
						moyenneJaccardCartes = forJacc.getValue();
						nombresCartesCommunes = 1;
					}
				}
			}
			ArrayList<Deck> theDecks = new ArrayList<Deck>();
			moyenneJaccardCartes = moyenneJaccardCartes/nombresCartesCommunes;
			
			theDecks.add(deckComparer);
			theDecks.add(deckPremier);
			distanceDeJaccardDeck.put(moyenneJaccardCartes, theDecks);
		}
		if(distanceDeJaccardDeck.isEmpty() == false){
			for(Map.Entry<Integer,ArrayList<Deck>> forDansJacc : distanceDeJaccardDeck.entrySet()){
				for(int i = 0; i< toutLesDecks.size(); i++){
					if((toutLesDecks.get(i) == forDansJacc.getValue().get(0))||(toutLesDecks.get(i) == forDansJacc.getValue().get(1))){
						toutLesDecks.remove(i);
					}
				}
			}
		}
		while(toutLesDecks.isEmpty() == false){
			ArrayList<Deck> theDecksBis = new ArrayList<Deck>();
			theDecksBis.add(deckComparer);
			theDecksBis.add(toutLesDecks.get(0));
			distanceDeJaccardDeck.put(1, theDecksBis);
			toutLesDecks.remove(0);
		}return distanceDeJaccardDeck;
	}
	
	
	
	
	
	
	
	public HashMap<Integer, ArrayList<Deck>> jaccardSurJaccardDistEntre2Decks(ArrayList<Deck> toutLesDecks){
		
		int minimum = 0;
		int maximum = 0;
		int jaccardValeur = 0;
		
		Deck deckPremier = null;
		Deck deckComparer = null;
		
		HashMap<Integer, ArrayList<Deck>> distanceDeJaccardDeck = new HashMap<Integer, ArrayList<Deck>>();
		HashMap<HashMap<Card, ArrayList<Deck>>, Integer> distanceDeJaccard = jaccardDistEntre2Cartes();
		
		if(distanceDeJaccard.isEmpty() == false){
			for(Map.Entry<HashMap<Card, ArrayList<Deck>>,Integer> forJacc : distanceDeJaccard.entrySet()){
				HashMap<Card, ArrayList<Deck>> hashMap = forJacc.getKey();

				for(Map.Entry<Card,ArrayList<Deck>> forDansJacc : hashMap.entrySet()){
					deckComparer = forDansJacc.getValue().get(0);
					Deck deckSecond = forDansJacc.getValue().get(1);
					
					if(deckPremier == null){
						maximum = forJacc.getValue();
						minimum = forJacc.getValue();
						
					}if(deckPremier == deckSecond){
						if(maximum<forJacc.getValue()){
							maximum = forJacc.getValue();
						}if(minimum>forJacc.getValue()){
							minimum = forJacc.getValue();
						}
					}else{
						ArrayList<Deck> theDecks = new ArrayList<Deck>();
			
						jaccardValeur = minimum/maximum;
						
						theDecks.add(deckComparer);
						theDecks.add(deckPremier);
						distanceDeJaccardDeck.put(jaccardValeur, theDecks);
						
						deckPremier = deckSecond;
						maximum = forJacc.getValue();
						minimum = forJacc.getValue();
					}
				}
			}
			
			ArrayList<Deck> theDecks = new ArrayList<Deck>();
			
			jaccardValeur = minimum/maximum;
						
			theDecks.add(deckComparer);
			theDecks.add(deckPremier);
			distanceDeJaccardDeck.put(jaccardValeur, theDecks);
		}
		
		if(distanceDeJaccardDeck.isEmpty() == false){
			for(Map.Entry<Integer,ArrayList<Deck>> forDansJacc : distanceDeJaccardDeck.entrySet()){
				for(int i = 0; i< toutLesDecks.size(); i++){
					if((toutLesDecks.get(i) == forDansJacc.getValue().get(0))||(toutLesDecks.get(i) == forDansJacc.getValue().get(1))){
						toutLesDecks.remove(i);
					}
				}
			}
		}
		while(toutLesDecks.isEmpty() == false){
			ArrayList<Deck> theDecksBis = new ArrayList<Deck>();
			theDecksBis.add(deckComparer);
			theDecksBis.add(toutLesDecks.get(0));
			distanceDeJaccardDeck.put(1, theDecksBis);
			toutLesDecks.remove(0);
		}return distanceDeJaccardDeck;
	}
}
