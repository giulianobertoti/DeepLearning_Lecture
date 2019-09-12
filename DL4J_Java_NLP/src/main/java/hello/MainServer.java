package hello;

import static spark.Spark.*;

import java.io.IOException;


public class MainServer {
	
	//final static Model model = new Model();
	
    public static void main(String[] args) throws IOException {

    	Word2VecModelo word2vec = new Word2VecModelo();
    	
    	word2vec.setup();
    	
		// Get port config of heroku on environment variable
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 1234;
        }
        port(port);

		//Servir conteudo html, css e javascript
		staticFileLocation("/static");

		

		Controller controller = new Controller(word2vec);
		controller.buscarPalavra();
		
		
    }
    
}
