package hello;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.deeplearning4j.models.embeddings.learning.impl.elements.SkipGram;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.VocabWord;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.FileSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;

public class Word2VecModelo {

	private String inputFilePath = "input/content.txt";
    private String modelFilePath = "output/word2vec.bin";

    Word2Vec word2Vec = new Word2Vec();
    
    public void setup() throws IOException{

        
        
        train();

        word2Vec = WordVectorSerializer.readWord2VecModel(new File(modelFilePath));

        
        
    }
    
    public Collection<String> getWords(String word){
    	return word2Vec.wordsNearest(word, 10);
    }

    public  void train() throws IOException {
        SentenceIterator sentenceIterator = new FileSentenceIterator(new File(inputFilePath));
        TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        Word2Vec vec = new Word2Vec.Builder()
                .minWordFrequency(2)
                .layerSize(300)
                .windowSize(5)
                .seed(42)
                .epochs(10)
                .elementsLearningAlgorithm(new SkipGram<VocabWord>())
                .iterate(sentenceIterator)
                .tokenizerFactory(tokenizerFactory)
                .build();
        vec.fit();

        WordVectorSerializer.writeWord2VecModel(vec, "output/word2vec.bin");
        
        
    }
}
