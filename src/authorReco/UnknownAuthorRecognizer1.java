package authorReco;

import java.io.File;
import java.io.IOException;
import java.util.*;

import authorEval.*;
import langModel.*;

/**
 * Class UnknownAuthorRecognizer1: a first author recognition system that recognizes 
 * the author of a sentence by using the language models read from a configuration system.
 * (unknown authors can be detected)
 * http://www.iro.umontreal.ca/~nie/IFT6255/modele_langue.pdf
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class UnknownAuthorRecognizer1 extends AuthorRecognizer1 {

	/**
	 * Constructor.
	 * 
	 * @param configFile the file path of the configuration file containing the information 
	 * on the language models (author, name and file path).
	 * @param vocabFile the file path of the file containing the common vocabulary
	 * for all the language models used in the recognition system. 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system.
	 */
	public UnknownAuthorRecognizer1(String configFile, String vocabFile, String authorFile) {
		super(configFile, vocabFile, authorFile);
	}

	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		double count=0.0;
		double tmp;
		Map<String, LanguageModelInterface> auteurLangModel;
		LanguageModelInterface langModel;
		//Initialise l'auteur à unknown.
		String recognizedAuthor= UNKNOWN_AUTHOR;
		//System.out.println(" ");
		for(String author : super.authors)
		{
			//System.out.println(author);
			auteurLangModel = this.authorLangModelsMap.get(author);
			//System.out.println(auteurLangModel);
			langModel = auteurLangModel.get(author+"_bi");
			//System.out.println(langModel.getLMOrder());
			tmp = langModel.getSentenceProb(sentence);
			//System.out.println(tmp);
			if(tmp >= count && tmp > 1.0E-300) //On récupère celui qui a la probabilité la plus forte seulement s'elle est supérieur
			{
				count=tmp;
				recognizedAuthor=author;
			}
			//System.out.println(" ");
			//System.out.println("----------------");
			//System.out.println(" ");
		}
		return recognizedAuthor;
	}

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args){

		UnknownAuthorRecognizer1 b = new UnknownAuthorRecognizer1("lm/small_author_corpus/fichConfig_bigram_1000sentences.txt","lm/small_author_corpus/corpus_20000.vocab", "data/author_corpus/validation/authors.txt");

		//computation of the hypothesis author file
		try {
			File sentenceFile = new File("data/small_author_corpus/validation/sentences_100sentences.txt");
			Scanner scan = new Scanner(sentenceFile);
			MiscUtils mot = new MiscUtils();
			mot.writeFile("","data/small_author_corpus/validation/authors_100sentences_hyp1Unknown.txt",false);
			String temoin = "nothing here";
			while (scan.hasNextLine())
			{
				temoin = scan.nextLine();
				mot.writeFile(b.recognizeAuthorSentence(temoin) + "\n", "data/small_author_corpus/validation/authors_100sentences_hyp1Unknown.txt",true);
			}
			System.out.println("FINIS !");

		} catch (IOException e) {
			e.printStackTrace();
		}

		//computation of the performance of the recognition system

		System.out.println(RecognizerPerformance.evaluateAuthors("data/small_author_corpus/validation/authors_100sentences_ref.txt","data/small_author_corpus/validation/authors_100sentences_hyp1Unknown.txt"));

		//TODO afficher le nombre de unknown parmis la liste.
		//computation of the performance of the recognition system

	}
	}
