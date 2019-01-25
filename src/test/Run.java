package test;

import authorReco.*;
import langModel.MiscUtils;

/**
 * Méthode de RUN
 * Test de l'efficience des Système de Reconnaissance d'auteurs.
 *
 */
public class Run {


    public static void main(String[] args) {

        String lienFichierConfig = "lm/small_author_corpus/fichConfig_bigram_1000sentences.txt";
        String lienFichierVocab = "lm/small_author_corpus/corpus_20000.vocab";
        String lienFichierAuteurs = "data/small_author_corpus/validation/authors.txt";

        AuthorRecognizer1 system_1 = new AuthorRecognizer1(lienFichierConfig, lienFichierVocab, lienFichierAuteurs);
        UnknownAuthorRecognizer1 system_2 = new UnknownAuthorRecognizer1(lienFichierConfig, lienFichierVocab, lienFichierAuteurs);

        MiscUtils createur = new MiscUtils();

        createur.writeFile("","data/author_corpus/test/authors-hyp1.txt",false);
        createur.writeFile("","data/author_corpus/test/authors-hyp2.txt",false);

        for(String sentence : createur.readTextFileAsStringList("data/author_corpus/test/sentencesTest.txt"))
        {
            createur.writeFile(system_1.recognizeAuthorSentence(sentence) + "\n","data/author_corpus/test/authors-hyp1.txt",true);
            createur.writeFile(system_2.recognizeAuthorSentence(sentence) + "\n","data/author_corpus/test/authors-hyp2.txt",true);
        }


    }

}
