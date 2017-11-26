import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class OpenNLPTester {

	public static void main(String[] args) throws Exception {

        OpenNLP ner = new OpenNLP();
		try {
			/*
			 *  Use ner.extractInputFile("path_to_input_file", "path_to_output_directory"); for tagging specific file
			 *  Use ner.ProcessAllInputFiles("path_to_input_directory", "path_to_output_directory"); for tagging all files in a 
			 *  directory
			 *  
			 *  Examples provided below:
			 */
			
//	        ner.extractInputFile("C:\\Users\\Vincent\\Research\\Scenarios\\Scenario1.txt", "C:\\Users\\Vincent\\Research");
//			ner.ProcessAllInputFiles("C:\\Users\\Vincent\\Research\\Scenarios", "C:\\Users\\Vincent\\Research\\OpenNLP");
			System.out.println("done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
