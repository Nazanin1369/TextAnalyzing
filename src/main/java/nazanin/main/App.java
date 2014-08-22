package nazanin.main;

import opennlp.tools.parser.Parse;

/**
 * Main Class
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Content content = new Content("This is a sample.");
        Content stems = new Content("bank bankers banking banks.");
        Content sentence = new Content("This is a sentence. It has fruits, vegetables," +
                                       " etc. but does not have meat. Mr. Smith went to Washington.");
        Analyzer analyzer = new AnalyzerImp();
        
        System.out.println("\n\n---Tagging---");
        printResult(analyzer.tagger(content));
        
        System.out.println("\n\n---Stemming---");
        printResult(analyzer.stemmer(stems));
        
        System.out.println("\n\n---Senetence Detection---");
        printResult(analyzer.sentence_detector(sentence));
        
        System.out.println("\n\n---Grammar Parsing---");
        showParser(analyzer.grammer_parser(sentence));
        
        System.out.println("\n\n---Tika XML Parsing---");
        printResult(analyzer.tika_XMLparser());
        
        System.out.println("\n\n---Tika AutoDetect_Parsing---");
        printResult(analyzer.tika_autoParser());
        
    }
    public static void printResult(String[] arr){
    	for(String s: arr){
    		System.out.print(" " + s + " * ");
    	}
    }
    
    public static void showParser(Parse[] p){
    	for(Parse ps: p){
    	 ps.show();
    	}
    }
}
