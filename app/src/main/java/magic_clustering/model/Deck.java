package model;

import java.util.HashMap;
import java.util.Map;


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
