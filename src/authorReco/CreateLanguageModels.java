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
		Map<String, Integer> correspondance = new HashMap(); //Map qui va permettre de compter les redondances de ngrams
		mot.writeFile("", "lm/small_author_corpus/fichConfig_trigram_1000sentences.txt", false);
		//Pour chaque auteur on prend son fichier de base avec les phrases
		for (String auteur : mot.readTextFileAsStringList("data/small_author_corpus/validation/authors.txt"))
		{
			mot.writeFile(" ", "lm/small_author_corpus/trigram_" + auteur + ".lm", false);
			//pour chaque phrase on les découpes en trigrams
			for(String sentence : mot.readTextFileAsStringList("data/small_author_corpus/train/" + auteur + ".txt"))
			{
				//Créé un filtre pour retirer tous les <s> </s> des phrases.
				char[] sent = sentence.toCharArray();
				sentence = "";
				for(int i = 4; i <sent.length-4; i++)
				{
					sentence+=sent[i];
				}

				//pour chaque trigram on l'ajoute au fichier
				for(String trigram : decoupeur.decomposeIntoNgrams(sentence, order))
				{
					if(trigram.length()>1)
					{
						//Créer la ligne BiGram si elle n'est pas déjà présente
						if(!correspondance.containsKey(trigram))
						{
							correspondance.put(trigram, 1);
						}
						else //Sinon incrémente le nombre de fois qu'il apparait.
						{
							correspondance.replace(trigram, correspondance.get(trigram) +1);
						}
					}
				}
			}
			//Enfin on écrit dans le fichier chaque ngram avec son nombre d'apparition
			for(String trigram : correspondance.keySet())
 			{
				int nombrecopies = correspondance.get(trigram);
				mot.writeFile(trigram +" " + nombrecopies+ "\n", "lm/small_author_corpus/trigram_" + auteur + ".lm", true);
			}
			System.out.println(auteur);

			mot.writeFile(auteur + "\t" + auteur + "_tri\tlm/small_author_corpus/trigram_" + auteur + ".lm \n", "lm/small_author_corpus/fichConfig_trigram_1000sentences.txt", true);
		}
	}
}