package jeu;

public class Card{

	//@SerializedName("convertedManaCost")
	int manaCost;
	private String name;

    public Card(String name, int costMana){
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
		return this.name;
	}
}
