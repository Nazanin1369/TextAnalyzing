package nazanin.main;

import opennlp.tools.parser.Parse;

public interface Analyzer {
	public String[] tagger(Content c);
	public String[] stemmer(Content c);
	public String[] sentence_detector(Content c);
	public Parse[] grammer_parser(Content c);
	public String[] tika_XMLparser();
	public String[] tika_autoParser();

}
