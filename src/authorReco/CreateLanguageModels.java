package authorReco;

import langModel.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
		CreateLanguageModels corpus = new CreateLanguageModels();

		File balzacFile = new File("data/author_corpus/train/balzac.txt");
		File hugoFile = new File("data/author_corpus/train/hugo.txt");
		File maupassantFile = new File("data/author_corpus/train/maupassant.txt");
		File moliereFile = new File("data/author_corpus/train/moliere.txt");
		File montaigneFile = new File("data/author_corpus/train/montaigne.txt");
	}
}