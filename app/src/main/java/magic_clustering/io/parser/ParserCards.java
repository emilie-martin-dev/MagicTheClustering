package magic_clustering.io.parser;

import magic_clustering.model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ParserCards implements IParser<List<Card>> {

	public ParserCards() {

	}

	@Override
	public List<Card> parse(String path) {
		try {
			// Initialise le parser
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();
			Gson gson = builder.create();

			// Comme on veut obtenir une arraylist, il faut définir le type d'une manière assez spéciale
			Type myType = new TypeToken<ArrayList<Card>>() {}.getType();

			// On parse le fichier
			return gson.fromJson(new FileReader(path), myType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
