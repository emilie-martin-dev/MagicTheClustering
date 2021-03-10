package io.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.io.*;

import model.*;

public class ParserCards{

	private final File file;
	private String cardName;

	public ParserCards(String file, String cardName){
		this.file  = new File("data/"+file);
		this.cardName = cardName;
		read();
	}

	private void read(){
		try{
			BufferedReader fileR = new BufferedReader(new FileReader(this.file));
			String line;

// 			GsonBuilder builder = new GsonBuilder();
// builder.setPrettyPrinting();
// Gson  m_gson = builder.create();
//
// Type myType= new TypeToken<ArrayList<Card>>() {}.getType();
// FileReader reader = new FileReader(path);
// List<Card> cards= m_gson.fromJson(reader, myType);
			while((line = fileR.readLine()) != null) {
				if(line.contains(this.cardName)){
					GsonBuilder builder = new GsonBuilder();
      				builder.setPrettyPrinting();
      				Gson gson = builder.create();
					System.out.println("la carte est présente dans le fichier");

				}
			}


		}catch(Exception e){
			System.out.println("Terrible erreur de la part des dev français! : "+e);
		}
	}

}
