import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class OpenNLP {
    
    public static StringBuilder processNERFormat(File file) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int count = 1;
        ArrayList<String> people = new ArrayList<String>();
        ArrayList<String> organizations = new ArrayList<String>();
        ArrayList<String> times = new ArrayList<String>();
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<String> durations = new ArrayList<String>();
        ArrayList<String> locations = new ArrayList<String>();
        
        /*
         * Replace the model filepaths with your own!
         */
    	InputStream is = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-sent.bin");
    	SentenceModel model = new SentenceModel(is);
    	SentenceDetectorME sdetector = new SentenceDetectorME(model);
    	
    	InputStream is2 = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-token.bin");
    	TokenizerModel modelToken = new TokenizerModel(is2);
    	Tokenizer tokenizer = new TokenizerME(modelToken);
        
		InputStream inputStreamName = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-ner-person.bin");
		TokenNameFinderModel nmodel = new TokenNameFinderModel(inputStreamName);
		NameFinderME nameFinder = new NameFinderME(nmodel);
		
		InputStream is3 = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-ner-location.bin");
		TokenNameFinderModel model3 = new TokenNameFinderModel(is3);
		NameFinderME nameFinderLoc = new NameFinderME(model3);
		
		InputStream is4 = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-ner-organization.bin");
		TokenNameFinderModel model4 = new TokenNameFinderModel(is4);
		NameFinderME nameFinderOrg = new NameFinderME(model4);
		
		InputStream is5 = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-ner-date.bin");
		TokenNameFinderModel model5 = new TokenNameFinderModel(is5);
		NameFinderME nameFinderDate = new NameFinderME(model5);
		
		InputStream is6 = new FileInputStream("C:\\software\\apache-opennlp-1.8.2-bin\\apache-opennlp-1.8.2\\en-ner-time.bin");
		TokenNameFinderModel model6 = new TokenNameFinderModel(is6);
		NameFinderME nameFinderTime = new NameFinderME(model6);

        sb.append("Sentence \tLocations \tPeople \tOrganizations \tTimes \tDates \tDurations \r\n");
        
        /*
         * I use StanfordCoreNLP for parsing sentences to keep output consistent (OpenNLP breaks paragraphs into
         * different sentences than StanforeCoreNLP)
         * Most of the commented out stuff is me originally using OpenNLP's sentence parser
         * On inspection, using OpenNLP's original parser does not seem to improve OpenNLP's precision and recall
         */
        
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        props.setProperty("truecase.overwriteText", "true");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        while ((line = br.readLine()) != null) {
//        	if(count == 20) break;
            Annotation document = new Annotation(line);
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//            String[] sentences = sdetector.sentDetect(line);

            
            for(CoreMap sen: sentences) {
            	String sentence = sen.toString();
                sb.append(sentence.toString() + " " + count++ + "\t");
                people.clear();
                organizations.clear();
                times.clear();
                dates.clear();
                durations.clear();
                locations.clear();
              /* not sure why, but I need to append a long string to the end of each sentence
               * in order for OpenNLP to detect names... ignore "abcdefghijklmnopqrstuvwxyz"
               * if it shows up tagged in the output
               */
              String[] tokens = tokenizer.tokenize(sentence + "abcdefghijklmnopqrstuvwxyz");

              Span nameSpans[] = nameFinder.find(tokens);
              Span locSpans[] = nameFinderLoc.find(tokens);
              Span orgSpans[] = nameFinderOrg.find(tokens);
              Span dateSpans[] = nameFinderDate.find(tokens);
              Span timeSpans[] = nameFinderTime.find(tokens);

              for (Span token: nameSpans) {
            	String word = tokens[token.getStart()];
//                String ne = token.getType();
                people.add(word);
//                switch (ne){
//                	case "PERSON": 
//                		people.add(word);
//                		break;
//                	case "ORGANIZATION":
//                		organizations.add(word);
//                		break;
//                	case "TIME":
//                		times.add(word);
//                		break;
//                	case "DATE":
//                		dates.add(word);
//                		break;
//                	case "DURATION":
//                		durations.add(word);
//                		break;
//                	case "LOCATION":
//                		locations.add(word);
//                	default:
//                		break;
//                }
//                System.out.println(word + " " + ne);
              }
              for (Span token: locSpans) {
              	String word = tokens[token.getStart()];
                locations.add(word);
              }
              for (Span token: orgSpans) {
            	  String word = tokens[token.getStart()];
                  organizations.add(word);
              }
              for (Span token: dateSpans) {
            	  String word = tokens[token.getStart()];
                  dates.add(word);
              }
              for (Span token: timeSpans) {
            	  String word = tokens[token.getStart()];
                  times.add(word);
              }

              if(!locations.isEmpty()) sb.append(locations.get(0));
              for(int i = 1; i < locations.size(); i++){
              	sb.append("; " + locations.get(i));
              }
              sb.append(" \t");
              if(!people.isEmpty()) sb.append(people.get(0));
              for(int i = 1; i < people.size(); i++){
              	sb.append("; " + people.get(i));
              }
              sb.append(" \t");
              if(!organizations.isEmpty()) sb.append(organizations.get(0));
              for(int i = 1; i < organizations.size(); i++){
              	sb.append("; " + organizations.get(i));
              }
              sb.append(" \t");
              if(!dates.isEmpty()) sb.append(dates.get(0));
              for(int i = 1; i < dates.size(); i++){
              	sb.append("; " + dates.get(i));
              }
              sb.append(" \t");
              if(!times.isEmpty()) sb.append(times.get(0));
              for(int i = 1; i < times.size(); i++){
              	sb.append("; " + times.get(i));
              }
              sb.append(" \t");
              if(!durations.isEmpty()) sb.append(durations.get(0));
              for(int i = 1; i < durations.size(); i++){
              	sb.append("; " + durations.get(i));
              }
              sb.append("\r\n");
//              System.out.println(sb.toString());
            }
        }
        br.close();
        return sb;
    }
    
    public static void extractInputFile(String src, String destination) throws Exception{
        File file = new File(src);
        StringBuilder sb;
        PrintWriter out;
                
        sb = processNERFormat(file);
        out = new PrintWriter(destination +"//OutputOpenNLP"+ file.getName());
        out.print(sb.toString());
        out.flush();
        out.close();
    }
    
	public static void ProcessAllInputFiles(String srcFolder, String destFolder) throws Exception {
		File folder = new File(srcFolder);
		File[] files = folder.listFiles();

		File dest = new File(destFolder);
		if (!dest.exists()) {
			dest.mkdirs();
		}

		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i]);
			extractInputFile(files[i].getAbsolutePath(), destFolder);
		}
	}
}
