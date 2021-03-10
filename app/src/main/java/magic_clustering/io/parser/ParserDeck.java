package magic_clustering.io.parser;

import magic_clustering.model.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class ParserDeck implements IParser<Deck>{

	public ParserDeck() {
		
	}

	public Deck parse(String path) {
		HashMap<Card,Integer> listeDeck = new HashMap<>();
		
		try{
			BufferedReader fileR = new BufferedReader(new FileReader(path));
			String line;

			while((line = fileR.readLine()) != null) {
				// si la ligne n'est pas vide et que le premier caractère est nombre :https://java2blog.com/java-isnumeric/
				if(line.length() != 0 &&line.substring(0, 1).matches("[0-9]+")){
					Integer nombre = Integer.valueOf(line.substring(0, line.indexOf(" ")));
					String nameCard = line.substring(line.indexOf(" ")+1);//prend la chaine après l'espace
					ParserCards parse = new ParserCards("cards.json");
					
					List<Card> list = parse.parse(nameCard);
					for(Card c: list){
						if(c.name.equals(nameCard)){
							listeDeck.put(c, nombre);
							break;//break pour éviter les doublons
						}
					}
				}
			}
			
			return new Deck(listeDeck);
		} catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

}
