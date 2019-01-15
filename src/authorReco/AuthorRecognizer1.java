package authorReco;

import java.io.File;
import java.io.IOException;
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
	    //Initialisation du fichier de configuration
		super.loadAuthorConfigurationFile(configFile);
		//Initialisation du scanneur pour lire le fichier
	    List<String> scanConfig = MiscUtils.readTextFileAsStringList(configFile);
	    //Initialisation de la HashMap qui est en attribut
	    this.authorLangModelsMap = new HashMap<String, Map<String, LanguageModelInterface>>();

	    //Initialisation du Vocabulary qui va de paire avec le ngram pour le languageModel. grâce à la classe supérieure
	    super.loadVocabularyFile(vocabFile);

        //Initialisation des auteurs qui seront reconnus par le système
		super.loadAuthorFile(authorFile);
        //Lecture de chaque ligne du fichier
		for(String ligne : scanConfig){

            //découper dans le tableau chaque String pour avoir des mots séparés (séparation \t est la représentation de l'espace.
            String[] contientDesMots = ligne.split("\t");

			//Initialisation du language LaPlace
			LanguageModelInterface language = new LaplaceLanguageModel();
			//Initialisation du ngramCounts qui va permettre de créer le languagueModel qui est dans la MAP.
			NgramCounts ngram = new NgramCounts();
			
            //Vérification que la ligne où il y a écris le nom de l'auteur coordonne avec les auteurs entrés dans le système.
			if(super.authors.contains(contientDesMots[0]))
			{

				//Remplir le ngramCounts grâce au chemin qui même à l'"authorFile".
				ngram.readNgramCountsFile(contientDesMots[2]);
				//initialise l'intérieur du language avec le ngram et vocab initialisé juste avant
				language.setNgramCounts(ngram, super.vocabularyLM);
				//Créé la map qui est intégrée par la suite à la map
				Map<String, LanguageModelInterface> table = new HashMap();
				//ajouter à la table, la ligne
				table.put(contientDesMots[1], language);
				//ajouter à l'atribut la table créé préalablement.
				this.authorLangModelsMap.put(contientDesMots[0], table);

			}

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
			if(tmp >= count && tmp > 0.0) //On récupère celui qui a la probabilité la plus forte (!= null)
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
	public static void main(String[] args) {


		//AuthorRecognizer1 exo3 = new AuthorRecognizer1("data/author_corpus/train/fichConfig_bigram_sentences.txt","lm/small_author_corpus/corpus_20000.vocab", "data/author_corpus/validation/authors.txt");
		AuthorRecognizer1 exo2 = new AuthorRecognizer1("lm/small_author_corpus/fichConfig_bigram_1000sentences.txt","lm/small_author_corpus/corpus_20000.vocab", "data/author_corpus/validation/authors.txt");

		//computation of the hypothesis author file
		try {
			File sentenceFile = new File("data/small_author_corpus/validation/sentences_100sentences.txt");
			Scanner scan = new Scanner(sentenceFile);
			MiscUtils mot = new MiscUtils();
			mot.writeFile("","data/small_author_corpus/validation/authors_100sentences_hyp1.txt",false);
			String temoin = "nothing here";
			while (scan.hasNextLine())
			{
				temoin = scan.nextLine();
				mot.writeFile(exo2.recognizeAuthorSentence(temoin) + "\n", "data/small_author_corpus/validation/authors_100sentences_hyp1.txt",true);
			}
			System.out.println("FINIS !");

		} catch (IOException e) {
			e.printStackTrace();
		}

		//computation of the performance of the recognition system
		System.out.println(RecognizerPerformance.evaluateAuthors("data/small_author_corpus/validation/authors_100sentences_ref.txt","data/small_author_corpus/validation/authors_100sentences_hyp1.txt"));


		//computation of the performance of the recognition system
		
	}
}