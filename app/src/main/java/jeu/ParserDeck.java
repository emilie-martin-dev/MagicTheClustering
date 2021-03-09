package jeu;


import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
public class ParserDeck{

	private final File file;
	private Deck deck;

	public ParserDeck(String fichier){
		this.file = new File("src/main/tcdecks/"+fichier);
		read();
	}

	private void read(){
		HashMap<Card,Integer> listeDeck = new HashMap<Card, Integer>();
		try{
			BufferedReader fileR = new BufferedReader(new FileReader(this.file));
			String line;

			while((line = fileR.readLine()) != null) {
				// si la ligne n'est pas vide et que le premier caractère est nombre :https://java2blog.com/java-isnumeric/
				if(line.length()!=0 &&line.substring(0, 1).matches("[-+]?\\d*\\.?\\d+")){
					Integer nombre = Integer.valueOf(line.substring(0, line.indexOf(" ")));
					String nameCarte = line.substring(line.indexOf(" "));
					Card card = new Card(nameCarte, 0); //0 = valeur par défaut le temps de faire le parser json
					listeDeck.put(card, nombre);
				}

			}
			this.deck = new Deck(listeDeck);

			for(Map.Entry<Card, Integer> liste : listeDeck.entrySet()){
				System.out.println(liste.getKey()+"  number :"+ liste.getValue());
			}

		}catch(Exception e){
			System.out.println("Terrible erreur de la part des dev français! : "+e);
		}
	}

}
