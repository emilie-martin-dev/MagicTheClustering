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
    
    /**Cet algorithme va renvoyer:
	 * - la liste des deux decks que l'on compare.
	 * - la distance entre les deux decks.
	 * 
	 * Pour récuperer la distance de jaccard entre deux decks on fait ici une moyenne des distances entres les cartes communes de ces deux decks :
	 * 1) on verifi que la liste de decks (listDeck) ai des cartes en commun
	 * 2) si il y a des cartes en commun:
	 * 		a) on parcourt le double HashMap (distanceDe Jaccard) contenant la distance entre les cartes
	 * 		b) on verifie si la variable deckPremier est null, si tel est le cas c'est que nous venons de commencer à parcourir le premier deck avec le quelle on compare this.deck donc on récupère la distance de jaccard de la première carte commune de ce deck avec this.deck.
	 * 		c) si la variable deckPremier et deckSecond sont égale, c'est que l'on est toujours dans le deck dont on est entrain de chercher la distance de jaccard.
	 * 		d) si la variable deckPremier et differente de deckSecond, c'est que l'on vient de rentrer dans un nouveau deck, on :
	 * 			- calcul donc la moyenne des distance de jaccard entre les cartes communes des decks.
	 * 			- ajoute au HashMap distanceDeJaccardDeck  la liste des deux decks que l'on compare ainsi que la moyenne de jaccard.
	 * 			- fait que deckPremier et égale à deckSecond pour dire que l'on se trouve maintenant dans un nouveau deck
	 * 			- récupère la distance de jaccard entre la première carte du deck commune.
	 * 		e) on réapplique ce que l'on faisait quand deckPremier et deckSecond à la fin du parcourt afin du pouvoir rajouter la moyenne de Jaccard du dernier deck de la liste.
	 * 		f) On vérifie que le HashMap que l'on a créer précedemment n'est pas vide:
	 * 			- 
	 * 			- si il l'est c'est que les decks n'ont pas de carte en commun
	 * 		
	 * 
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
	 * 3) si il n'y a pas de carte en commun:
	 * 		- la distance de jaccard entre les decks est de 1.
	 * **/
    public HashMap<Integer, ArrayList<Deck>> jaccardDistEntre2DecksMoyenne(ArrayList<Deck> toutLesDecks){
		
		int moyenneJaccardCartes = 0;
		int nombresCartesCommunes = 0;
		
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
						deckPremier = deckSecond;
						moyenneJaccardCartes = forJacc.getValue();
						nombresCartesCommunes = 1;
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
