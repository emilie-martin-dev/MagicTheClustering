package model;

public class Card{

	//@SerializedName("convertedManaCost")
	int manaCost;
	private String name;

	public Card(String name, int manaCost){
		this.name = name;
		this.manaCost = manaCost;
	}

	public String getName(){
		return this.name;
	}

	public int getManaCost(){
		return this.manaCost;
	}

	public String toString(){
		return "name :"+this.name+" mana cost : "+this.manaCost;
	}
}
