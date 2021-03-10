package magic_clustering.io.parser;

import magic_clustering.model.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class ParserCards implements IParser<List<Card>> {

	private final File file;

	public ParserCards(String file){
		this.file  = new File("data/"+file);
	}

	public List<Card> parse(String path) {
		try{
			GsonBuilder builder = new GsonBuilder();
			builder.setPrettyPrinting();
			Gson gson = builder.create();

			Type myType= new TypeToken<ArrayList<Card>>() {}.getType();
			FileReader reader = new FileReader(this.file);

			return gson.fromJson(reader, myType);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
