import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class StanfordNERTester {

	public static void main(String[] args) throws Exception {
        StanfordNER ner = new StanfordNER();
		try {
			/*
			 *  Use ner.extractInputFile("path_to_input_file", "path_to_output_directory"); for tagging specific file
			 *  Use ner.ProcessAllInputFiles("path_to_input_directory", "path_to_output_directory"); for tagging all files in a 
			 *  directory
			 *  
			 *  Examples provided below:
			 */
			
//	        ner.extractInputFile("C:\\Users\\Vincent\\Research\\Scenarios\\EMS_radio_call.txt", "C:\\Users\\Vincent\\Research\\output");
			
//			ner.ProcessAllInputFiles("C:\\Users\\vincentclin1\\Dropbox\\Research\\Scenarios", "C:\\Users\\vincentclin1\\Dropbox\\Research\\NERTest");
			System.out.println("done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
