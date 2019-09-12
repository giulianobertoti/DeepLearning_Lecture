package hello;

import static spark.Spark.get;

import java.util.Collection;

import com.google.gson.Gson;

public class Controller {
	
	private Word2VecModelo model;
	
	
	public Controller(Word2VecModelo model){
		this.model = model;
	}
	
	public void buscarPalavra(){
		get("/:word", (req, res) -> {
			
			String word = req.params(":word");
		
			Collection<String> words = model.getWords(word);
			
			return new Gson().toJson(words);
			
		});
	}
	
	
	

}
