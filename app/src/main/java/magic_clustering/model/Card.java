package magic_clustering.model;

import com.google.gson.annotations.SerializedName;

public class Card {

	@SerializedName("convertedManaCost")
	public int manaCost;

	public String name;
	
	public Card() {
		
	}

	public Card(String name, int manaCost) {
		this.name = name;
		this.manaCost = manaCost;
	}

	public String getName() {
		return this.name;
	}

	public int getManaCost() {
		return this.manaCost;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	@Override
	public String toString() {
		return "Card{" + "manaCost=" + manaCost + ", name=" + name + '}';
	}	
	
}
