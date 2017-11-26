import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

public class StanfordNER {
    
	// kind of deprecated... use processNERFormat() instead
    public static StringBuilder processNER(File file) throws Exception {
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


        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        props.setProperty("truecase.overwriteText", "true");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        
        while ((line = br.readLine()) != null) {
//        	if(count == 20) break;
            Annotation document = new Annotation(line);
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);

            for(CoreMap sentence: sentences) {
                sb.append(sentence.toString() + " " + count++ + "\t");
                people.clear();
                organizations.clear();
                times.clear();
                dates.clear();
                durations.clear();
                locations.clear();
//                System.out.println(line);
              for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            	String word = token.get(TextAnnotation.class);
                String ne = token.get(NamedEntityTagAnnotation.class);
                switch (ne){
                	case "PERSON": 
                		people.add(word);
                		break;
                	case "ORGANIZATION":
                		organizations.add(word);
                		break;
                	case "TIME":
                		times.add(word);
                		break;
                	case "DATE":
                		dates.add(word);
                		break;
                	case "DURATION":
                		durations.add(word);
                		break;
                	case "LOCATION":
                		locations.add(word);
                	default:
                		break;
                }
//                System.out.println(word + " " + ne);
              }
              sb.append("Locations: ");
              if(!locations.isEmpty()) sb.append(locations.get(0));
              for(int i = 1; i < locations.size(); i++){
              	sb.append("; " + locations.get(i));
              }
              sb.append("\tPerson names: ");
              if(!people.isEmpty()) sb.append(people.get(0));
              for(int i = 1; i < people.size(); i++){
              	sb.append("; " + people.get(i));
              }
              sb.append("\tOrganizations: ");
              if(!organizations.isEmpty()) sb.append(organizations.get(0));
              for(int i = 1; i < organizations.size(); i++){
              	sb.append("; " + organizations.get(i));
              }
              sb.append("\tDates: ");
              if(!dates.isEmpty()) sb.append(dates.get(0));
              for(int i = 1; i < dates.size(); i++){
              	sb.append("; " + dates.get(i));
              }
              sb.append("\tTimes: ");
              if(!times.isEmpty()) sb.append(times.get(0));
              for(int i = 1; i < times.size(); i++){
              	sb.append("; " + times.get(i));
              }
              sb.append("\tDurations:");
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


        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, truecase");
//        props.setProperty("truecase.overwriteText", "true"); // Use truecase annotator
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        sb.append("Sentence \tLocations \tPeople \tOrganizations \tTimes \tDates \tDurations \r\n");

        while ((line = br.readLine()) != null) {
//        	if(count == 20) break;
            Annotation document = new Annotation(line);
            pipeline.annotate(document);
            List<CoreMap> sentences = document.get(SentencesAnnotation.class);
            for(CoreMap sentence: sentences) {
                sb.append(sentence.toString() + " " + count++ + "\t");
                people.clear();
                organizations.clear();
                times.clear();
                dates.clear();
                durations.clear();
                locations.clear();
//                System.out.println(line);
              for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
            	String word = token.get(TextAnnotation.class);
                String ne = token.get(NamedEntityTagAnnotation.class);
                switch (ne){
                	case "PERSON": 
                		people.add(word);
                		break;
                	case "ORGANIZATION":
                		organizations.add(word);
                		break;
                	case "TIME":
                		times.add(word);
                		break;
                	case "DATE":
                		dates.add(word);
                		break;
                	case "DURATION":
                		durations.add(word);
                		break;
                	case "LOCATION":
                		locations.add(word);
                	default:
                		break;
                }
//                System.out.println(word + " " + ne);
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
        out = new PrintWriter(destination +"//OutputNoLabel"+ file.getName());
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
