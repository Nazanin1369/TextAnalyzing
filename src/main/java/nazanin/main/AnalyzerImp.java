package nazanin.main;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.parser.chunking.Parser;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.util.InvalidFormatException;

public class AnalyzerImp implements Analyzer {

	/**
	 * returns an array of strings as a result of tagging part of speech 
	 * of the content object {@link Content}.
	 * @param Content object 
	 * @return Array of part of speeches
	 * @author Nazanin
	 */
	@Override
	public String[] tagger(Content c) {
		String path = "/Users/nazanin/MyProjects/Maven/Text-Analyzing/src/Resources/en-pos-maxent.bin";
		File posModelFile = new File(path);
		String[] result = null;
		try{
		FileInputStream posModelStream = new FileInputStream(posModelFile);
		POSModel model = new POSModel(posModelStream);
		POSTaggerME tagger = new POSTaggerME(model);	
		String[] words = tokenizer(c.getText());
		result = tagger.tag(words);
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * return an array of strings which are stems of each word 
	 * of the content object {@link Content} text.
	 * @param Content Object
	 * @return Array of stems
	 * @author nazanin
	 */
	@Override
	public String[] stemmer(Content c) {
		EnglishStemmer english = new EnglishStemmer();
		String[] words = tokenizer(c.getText());
		for(int i = 0; i < words.length; i++){
			english.setCurrent(words[i]);
			english.stem();
			words[i] = english.getCurrent();
		}
		return words;
	}
	
	/**
	 * Tokenize the String input and return it as an array
	 * of Strings.
	 * @param text
	 * @return tokenized words as an Array
	 */
	public String[] tokenizer(String text){
		return SimpleTokenizer.INSTANCE.tokenize(text);
	}
	
	/**
	 * detect sentences in the content object {@link Content}
	 * @param content object
	 * @return All sentences in the content text as an Array
	 * @author nazanin
	 */
	@Override
	public String[] sentence_detector(Content c) {
		String path = "/Users/nazanin/MyProjects/Maven/Text-Analyzing/src/Resources/en-sent.bin";
		File senModelFile = new File(path);
		String[] result = null;
		try{
			FileInputStream modelStream = new FileInputStream(senModelFile);
			SentenceModel model = new SentenceModel(modelStream);
			SentenceDetector detector = new SentenceDetectorME(model);
			result = detector.sentDetect(c.getText());
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public Parse[] grammer_parser(Content c) {
		String path = "/Users/nazanin/MyProjects/Maven/Text-Analyzing/src/Resources/en-parser-chunking.bin";
		Parse[] result = null;
		File parserModel = new File(path);
		try{
			FileInputStream modelstream = new FileInputStream(parserModel);
			ParserModel model = new ParserModel(modelstream);
			Parser parser = (Parser) ParserFactory.create(model, 20, 0.95);
			result = ParserTool.parseLine(c.getText(), parser, 3);
			
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 */
	@Override
	public String[] tika_XMLparser() {
		String[] result = new String[3];
		File html = new File("/Users/nazanin/MyProjects/Maven/Text-Analyzing/src/Resources/html.xml");
		try {
			InputStream input = new FileInputStream(html);
			ContentHandler text = new BodyContentHandler();
			LinkContentHandler links = new LinkContentHandler();
			ContentHandler handler = new TeeContentHandler(text, links);
			Metadata metadata = new Metadata();
			HtmlParser parser = new HtmlParser();
			ParseContext context = new ParseContext();
			parser.parse(input, handler, metadata, context);
			result[0] = "Title: " + metadata.get(metadata.TITLE);
			result[1] = "Body: " + text.toString();
			result[2] = "links: " + links.getLinks().toString();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String[] tika_autoParser() {
		String[] result = new String[3];
		try {
			InputStream input = new FileInputStream(new File("/Users/nazanin/Books/Web crawler.pdf"));
			ContentHandler textHandler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			AutoDetectParser parser = new AutoDetectParser();
			ParseContext context = new ParseContext();
			parser.parse(input, textHandler, metadata, context);
			result[0] = "Title: " + metadata.get(metadata.TITLE);
			result[1] = "Body: " + textHandler.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		   
		return result;
	}
	

}
