package magic_clustering.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Deck {

	private String name;
	private HashMap<Card, Integer> cards;
	private HashMap<Integer, Integer> manaCurve;
	private HashMap<TypeEnum, HashMap<Integer, Integer>> manaCurvePerType;

	public Deck(String nameDeck, HashMap<Card, Integer> cards) {
		this.name = nameDeck;
		this.cards = cards;
		
		computeManaStats();
	}
	
	private void computeManaStats() {
		manaCurve = new HashMap<>();
		init(manaCurve);
		Integer numbCard;

		manaCurvePerType = new HashMap<>();
		
		HashMap<Integer, Integer> artifact = new HashMap<>();
		init(artifact);
		
		HashMap<Integer, Integer> creature = new HashMap<>();
		init(creature);
		
		HashMap<Integer, Integer> instant = new HashMap<>();
		init(instant);
		
		HashMap<Integer, Integer> land = new HashMap<>();
		init(land);
		
		HashMap<Integer, Integer> enchantment = new HashMap<>();
		init(enchantment);
		
		HashMap<Integer, Integer> sorcery = new HashMap<>();
		init(sorcery);
		
		for(Map.Entry<Card, Integer> entry : this.cards.entrySet()) {
			Card c = entry.getKey();
			int n = entry.getValue();
			//gestion cas particulier des terrains pour la courbes et le cube
			if(c.type.contains("Land")){
				numbCard = manaCurve.get(-1) + n;
				manaCurve.put(-1, numbCard);
				land.put(-1, numbCard);
			}
			
			else{
				//HashMap courbe de mana
				numbCard = manaCurve.get(c.manaCost) + n;
				manaCurve.put(c.manaCost, numbCard);

				// Courbe de mana par type
				updateManaStat(artifact, n, c, "Artifact");
				updateManaStat(creature, n, c, "Creature");
				updateManaStat(enchantment, n, c, "Enchantment");
				updateManaStat(instant, n, c, "Instant");
				updateManaStat(sorcery, n, c, "Sorcery");
			}			
		}		
		
		manaCurvePerType.put(TypeEnum.Artifact, artifact);
		manaCurvePerType.put(TypeEnum.Creature, creature);
		manaCurvePerType.put(TypeEnum.Enchantment, enchantment);
		manaCurvePerType.put(TypeEnum.Instant, instant);
		manaCurvePerType.put(TypeEnum.Land, land);
		manaCurvePerType.put(TypeEnum.Sorcery, sorcery);
	}
	
	// Met a jour la courbe d'un type donné
	private void updateManaStat(HashMap<Integer, Integer> typeMap, int n, Card c, String type){
		int numbCard = 0;
		if(c.type.contains(type)){
			if(typeMap.containsKey(c.manaCost)){
				numbCard = typeMap.get(c.manaCost) + n;
			} else {
				numbCard = n;
			}
			
			typeMap.put(c.manaCost, numbCard);
		}
	}

	/*initialisation des Hashmap<Integer, Integer avec les clé allant de -1 (terrain)
	à 10 (cout max cartes de 93-94) et valeur à 0 pour faciliter les comparaisons de
	deux courbes/cubes de mana*/
	private void init(HashMap<Integer, Integer> mana){
		for(int i = -1; i <= 10; i++){
			mana.put(i,0);
		}
	}

	public HashMap<Card, Integer> getCards() {
		return this.cards;
	}

	public HashMap<Integer, Integer> getManaCurve() {
		return this.manaCurve;
	}

	public HashMap<TypeEnum, HashMap<Integer, Integer>> getManaCube() {
		return this.manaCurvePerType;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		StringBuilder bufferCards = new StringBuilder();

		bufferCards.append("[");
		for(Map.Entry<Card, Integer> entry : cards.entrySet()) {
			bufferCards.append("{count=");
			bufferCards.append(entry.getValue());
			bufferCards.append(", card=");
			bufferCards.append(entry.getKey());
			bufferCards.append("}, ");
		}

		bufferCards.delete(bufferCards.length()-2, bufferCards.length());

		bufferCards.append("]");

		return "Deck{ name=" + this.name + "cards=" + bufferCards.toString() + '}';
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 59 * hash + Objects.hashCode(this.name);
		hash = 59 * hash + Objects.hashCode(this.cards);
		hash = 59 * hash + Objects.hashCode(this.manaCurve);
		hash = 59 * hash + Objects.hashCode(this.manaCurvePerType);
		return hash;
	}

}
