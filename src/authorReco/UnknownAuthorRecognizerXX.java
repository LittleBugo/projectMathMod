package authorReco;

import java.util.*;

import authorEval.*;
import langModel.*;

/**
 * Class UnknownAuthorRecognizer1: a first author recognition system that recognizes
 * the author of a sentence by using the language models read from a configuration system.
 * (unknown authors can be detected)
 *
 * @author N. Hernandez and S. Quiniou (2017)
 *
 */
public class UnknownAuthorRecognizerXX extends UnknownAuthorRecognizer1 {

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
    public UnknownAuthorRecognizerXX(String configFile, String vocabFile, String authorFile) {
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
    public static void main(String[] args) {
        //initialization of the recognition system


        //computation of the hypothesis author file


        //computation of the performance of the recognition system
    }
}