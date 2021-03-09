package jeu;

import java.util.Map;
import java.util.HashMap;

public class Deck{

	//String en Carte
	private HashMap<Card, Integer> cards;

    public Deck(HashMap<Card, Integer> cards){
		this.cards = cards;
	}

	public HashMap<Card, Integer> getDeck(){
		return this.cards;
	}

}
