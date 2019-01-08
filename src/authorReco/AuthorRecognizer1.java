package authorReco;

import java.io.File;
import java.util.*;

import authorEval.*;
import langModel.*;

/**
 * Class AuthorRecognizer1: a first author recognition system that recognizes 
 * the author of a sentence by using the language models read from a configuration system.
 * (no unknown author can be detected)
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class AuthorRecognizer1 extends AuthorRecognizerAbstractClass {
	/**
	 * Map of LanguageModels associated with each author (each author can be 
	 * associated with several language models). 
	 * The keys to the first map are the names of the authors (e.g., "zola"), the keys 
	 * to the second map are the names associated with each file containing a language model 
	 * (e.g., "zola-bigrams"), and the values of the second map are the LanguageModel objects 
	 * built from the file path given in the AuthorConfigurationFile attribute.
	 */
	protected Map<String, Map<String, LanguageModelInterface>> authorLangModelsMap;

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
	public AuthorRecognizer1(String configFile, String vocabFile, String authorFile) {
	    /*//Initialisation du scanneur pour lire le fichier
	    Scanner scan = new Scanner(configFile);
	    //Initialisation de la HashMap qui est en attribut
	    this.authorLangModelsMap = new HashMap();
	    //Initialisation du ngramCounts qui va permettre de créer le languagueModel qui est dans la MAP.
	    NgramCounts ngram = new NgramCounts();
	    //Initialisation du Vocabulary qui va de paire avec le ngram pour le languageModel.
	    Vocabulary vocab = new Vocabulary();
	    //remplir le vocabulaire avec le chemin reçu en paramètre
	    vocab.readVocabularyFile(vocabFile);
	    //Initialisation du language (ici Naive mais on pourrait aussi avoir LaPlace
        LanguageModelInterface language = new NaiveLanguageModel();
        //Lire chaque ligne du fichier s'il existe.
		while(scan.hasNextLine()){
            //Avancée dans le fichier
            String ligne = scan.nextLine();
            //découper dans le tableau les
            String[] contientDesMots = ligne.split(" ");
            //Remplir le ngramCounts grâce au chemin qui même à l'"authorFile".
            ngram.readNgramCountsFile(contientDesMots[2]);
            //initialise l'intérieur du language avec le ngram et vocab initialisé juste avant
            language.setNgramCounts(ngram, vocab);
            //Créé la map qui est intégrée par la suite à la map
            Map table = new HashMap();
            //ajouter à la table, la ligne
            table.put(contientDesMots[1], language);
            //ajouter à l'atribut la table créé préalablement.
            this.authorLangModelsMap.put(contientDesMots[0], table);
        }
        */

	    super();
	    loadAuthorConfigurationFile(configFile);
        loadVocabularyFile(vocabFile);
	    loadAuthorFile(authorFile);
        this.authorLangModelsMap = new HashMap<>();
        for (String auteur : super.authors)
        {
            Map tempToAdd = new HashMap();
            tempToAdd.put("", "");
        }
	}

	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence) {
		if (sentence != " "){
			return super.authors.get(authors.size());
		}
		return UNKNOWN_AUTHOR;
	}

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		AuthorRecognizer1 aut = new AuthorRecognizer1(" "," ", " ");

		//computation of the hypothesis author file
		File sentenceFile = new File("data/small_author_corpus/validation/authors.txt");
		String sentence = " " + " n ";
		System.out.println(aut.recognizeAuthorSentence(sentence));
		
		//computation of the performance of the recognition system
		
	}
}