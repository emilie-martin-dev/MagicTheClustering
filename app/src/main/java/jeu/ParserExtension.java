package jeu;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ParserExtension{

	private final File file;
	private String cardName;

	public ParserExtension(String file, String cardName){
		this.file  = new File("src/main/json/"+file);
		this.cardName = cardName;
		read();
	}

	private void read(){
		try{
			BufferedReader fileR = new BufferedReader(new FileReader(this.file));
			String line;

			while((line = fileR.readLine()) != null) {
				if(line.contains(this.cardName)){
					GsonBuilder builder = new GsonBuilder();
      				builder.setPrettyPrinting();
      				Gson gson = builder.create();
					System.out;println("la carte est présente dans le deck");

				}else{
					System.out.println("la carte recherchée n'est pas dans cette extension");
				}
			}


		}catch(Exception e){
			System.out.println("Terrible erreur de la part des dev français! : "+e);
		}
	}

}
