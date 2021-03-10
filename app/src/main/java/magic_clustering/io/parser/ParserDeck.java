package io.parser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import model.*;

public class ParserDeck{

	private final File file;
	private Deck deck;

	public ParserDeck(String fichier){
		this.file = new File("data/tcdecks/"+fichier);
		read();
	}

	private void read(){
		HashMap<Card,Integer> listeDeck = new HashMap<Card, Integer>();
		try{
			BufferedReader fileR = new BufferedReader(new FileReader(this.file));
			String line;

			while((line = fileR.readLine()) != null) {
				// si la ligne n'est pas vide et que le premier caractère est nombre :https://java2blog.com/java-isnumeric/
				if(line.length()!=0 &&line.substring(0, 1).matches("[0-9]+")){
					Integer nombre = Integer.valueOf(line.substring(0, line.indexOf(" ")));
					String nameCard = line.substring(line.indexOf(" ")+1);//prend la chaine après l'espace
					ParserCards parse = new ParserCards("cards.json");
					List<Card> list = parse.read(nameCard);
					for(Card c: list){
						if(c.name.equals(nameCard)){
							listeDeck.put(c, nombre);
							break;//break pour éviter les doublons
						}
					}
				}

			}
			this.deck = new Deck(listeDeck);
			for(Map.Entry<Card, Integer> liste : listeDeck.entrySet()){
				System.out.println(liste.getKey()+"  number :"+ liste.getValue());
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
