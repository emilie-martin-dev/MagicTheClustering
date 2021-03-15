package magic_clustering.model;

import com.google.gson.annotations.SerializedName;

public class Card {

	@SerializedName("convertedManaCost")
	public int manaCost;

	public String name;

	public String type;

	public Card() {

	}

	public Card(String name, int manaCost, String type) {
		this.name = name;
		this.manaCost = manaCost;
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public int getManaCost() {
		return this.manaCost;
	}

	public String getType(){
		return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setManaCost(int manaCost) {
		this.manaCost = manaCost;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Card{" + "manaCost=" + manaCost + ", name=" + name + '}';
	}

}
