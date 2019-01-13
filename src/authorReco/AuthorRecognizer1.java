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
	private Map<String, Map<String, LanguageModelInterface>> authorLangModelsMap;
	private Map<String, Map<String, LanguageModelInterface>> authorLangModelsMap2;

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
		this.authorLangModelsMap2 = new HashMap<String, Map<String, LanguageModelInterface>>();
	    //Initialisation du ngramCounts qui va permettre de créer le languagueModel qui est dans la MAP.
	    NgramCounts ngram = new NgramCounts();
	    //Initialisation du Vocabulary qui va de paire avec le ngram pour le languageModel. grâce à la classe supérieure
	    super.loadVocabularyFile(vocabFile);
	    //Initialisation du language (ici Naive mais on pourrait aussi avoir LaPlace
        LanguageModelInterface language = new NaiveLanguageModel();
		LanguageModelInterface language2 = new LaplaceLanguageModel();
        //Initialisation des auteurs qui seront reconnus par le système
		super.loadAuthorFile(authorFile);
        //Lecture de chaque ligne du fichier
		for(String ligne : scanConfig){

            //découper dans le tableau chaque String pour avoir des mots séparés (séparation \t est la représentation de l'espace.
            String[] contientDesMots = ligne.split("\t");

            //Vérification que la ligne où il y a écris le nom de l'auteur coordonne avec les auteurs entrés dans le système.
			if(super.authors.contains(contientDesMots[0]))
			{
				//Remplir le ngramCounts grâce au chemin qui même à l'"authorFile".
				ngram.readNgramCountsFile(contientDesMots[2]);
				//initialise l'intérieur du language avec le ngram et vocab initialisé juste avant
				language.setNgramCounts(ngram, super.vocabularyLM);
				language2.setNgramCounts(ngram, super.vocabularyLM);
				//Créé la map qui est intégrée par la suite à la map
				Map<String, LanguageModelInterface> table = new HashMap();
				//ajouter à la table, la ligne
				table.put(contientDesMots[1], language);
				//ajouter à l'atribut la table créé préalablement.
				this.authorLangModelsMap.put(contientDesMots[0], table);
				this.authorLangModelsMap2.put(contientDesMots[0], table);


			}

        }

/*
	    super();
	    loadAuthorConfigurationFile(configFile);
        loadVocabularyFile(vocabFile);
	    loadAuthorFile(authorFile);
        this.authorLangModelsMap = new HashMap<>();
        for (String auteur : super.authors)
        {
            Map tempToAdd = new HashMap();
            tempToAdd.put("", super.vocabularyLM);
        }
*/	}

	/**
	 * Method recognizing and returning the author of the given sentence 
	 * (the unknown author can also be picked up).
	 * 
	 * @param sentence the sentence whose author is to recognize.
	 * @return the author of the sentence as recognized by the recognition system.
	 */
	public String recognizeAuthorSentence(String sentence, int i) {
		double count=0.0;
		double tmp = 0.0;
		String recognizeAuthor= "Je ne sais pas";
		for(String author : super.authors)
		{
			if (i==0)
			{
				tmp = this.authorLangModelsMap.get(author).get(author+"_bi").getSentenceProb(sentence);
			}
			else
			{
				tmp = this.authorLangModelsMap2.get(author).get(author+"_bi").getSentenceProb(sentence);
			}
			if(tmp > count && tmp > 0.0)
			{
				count=tmp;
				recognizeAuthor=author;
			}

		}
		return recognizeAuthor;
	}

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//initialization of the recognition system
		AuthorRecognizer1 aut = new AuthorRecognizer1("lm/small_author_corpus/fichConfig_bigram_1000sentences.txt","lm/small_author_corpus/corpus_20000.vocab", "data/author_corpus/validation/authors.txt");
		//computation of the hypothesis author file
		String sentence = "J'en ai aucune idée, je me contente de viser dans le trou";

		for(int i=0; i<=1; i++)
		{
			String naive = aut.recognizeAuthorSentence(sentence, 0);
			String laPlace = aut.recognizeAuthorSentence(sentence, 0);
		//Beginning of the Mindblowing
			System.out.println("Qui a dit : '" + sentence + "' ?");
			System.out.println("Oui Naive ?");
			System.out.println(naive);
			System.out.println("C'est non, Monsieur Laplace une réponse ?");
			System.out.println(laPlace);

			if (naive == laPlace && laPlace != "Je ne sais pas" )
			{
				System.out.println("Mais enfin Mr Laplace c'est la même réponse que Mr Naive !");
			}
			else if(naive == "Je ne sais pas" && laPlace == "Je ne sais pas" && i<1)
			{
				System.out.println("Tant pis, QUESTION suivante");
			}
			else
			{
				System.out.println("C'est un non CA-TE-GO-RIQUE pour les deux joueurs Laplace et Naive");
			}
			System.out.println(" ");
			sentence="et je ne vois pas non plus la vie en rose. ";
		}


		//computation of the performance of the recognition system
		
	}
}