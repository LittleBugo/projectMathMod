package authorReco;

import langModel.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Class CreateLanguageModels: a class to create the language models used in the recognition systems.
 * 
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class CreateLanguageModels {

	/* constructeur */
	//public CreateLanguageModels();

	/**
	 * Main method.
	 * 
	 * @param args arguments of the main method.
	 */
	public static void main(String[] args) {
		//create the language models from the training corpora

		MiscUtils mot = new MiscUtils();
		Map<String,List> map = new HashMap();

		for (String auteur : mot.readTextFileAsStringList("data/small_author_corpus/validation/authors.txt")) {
			NgramCounts ngram = new NgramCounts();
			List list = new ArrayList();
			list.add(new File("data/author_corpus/train/" + auteur + ".txt"));
			list.add(ngram);
			map.put(auteur,list);
		}

	}
}