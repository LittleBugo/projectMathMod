package authorReco;

import java.io.File;
import java.io.IOException;
import java.util.*;


import authorEval.*;
import langModel.MiscUtils;

/**
 * Class BaselineAuthorRecognizer: a baseline author recognition system that "recognizes" 
 * the author of a sentence by randomly choosing one author recognized by the recognition system.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class BaselineAuthorRecognizer extends AuthorRecognizerAbstractClass {
	/**
	 * Random generator to pick up an author.
	 */
	protected Random rand;

	/**
	 * Constructor.
	 * 
	 * @param authorFile the file path of the file containing 
	 * the names of the authors recognized by the system.
	 */
	public BaselineAuthorRecognizer(String authorFile) {
		super();
		loadAuthorFile(authorFile);
		//initializes the random generator attribute
		rand = new Random(System.currentTimeMillis());
	}

	/**
	 * Method recognizing and returning the author of the given sentence
	 * (the unknown author can also be picked up).
	 *
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		return authors.get(rand.nextInt(authors.size()));
	}

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		BaselineAuthorRecognizer b = new BaselineAuthorRecognizer("data/small_author_corpus/validation/authors_100sentences_ref.txt");
		
		//computation of the hypothesis author file
		try {
			File sentenceFile = new File("data/small_author_corpus/validation/sentences_100sentences.txt");
			Scanner scan = new Scanner(sentenceFile);
			MiscUtils mot = new MiscUtils();
			mot.writeFile("","data/small_author_corpus/validation/authors_100sentences_hyp-baseline.txt",false);
			String temoin = "nothing here";
			while (scan.hasNextLine())
			{
				temoin = scan.nextLine();
				mot.writeFile(b.recognizeAuthorSentence(temoin) + "\n", "data/small_author_corpus/validation/authors_100sentences_hyp-baseline.txt",true);
			}
			System.out.println("FINIS !");

		} catch (IOException e) {
			e.printStackTrace();
		}

		//computation of the performance of the recognition system
		System.out.println(RecognizerPerformance.evaluate("data/small_author_corpus/validation/authors_100sentences_ref.txt","data/small_author_corpus/validation/authors_100sentences_hyp-baseline.txt"));
	}
}