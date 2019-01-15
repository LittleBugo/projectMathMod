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
		int order = 3;
		MiscUtils mot = new MiscUtils();
		NgramUtils decoupeur = new NgramUtils();

		//Pour chaque auteur on prend son fichier de base avec les phrases
		for (String auteur : mot.readTextFileAsStringList("data/small_author_corpus/validation/authors.txt"))
		{
			mot.writeFile(" ", "lm/small_author_corpus/trigram_" + auteur + ".lm", false);

			//pour chaque phrase on les découpes en trigrams
			for(String sentence : mot.readTextFileAsStringList("data/author_corpus/train/" + auteur + ".txt"))
			{
				//pour chaque trigram on l'ajoute au fichier
				for(String trigram : decoupeur.decomposeIntoNgrams(sentence, order))
				mot.writeFile(trigram, "lm/small_author_corpus/trigram_" + auteur + ".lm", true);
			}



		}

	}
}