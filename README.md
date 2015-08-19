## TextAnalyzing

Text Analyzing project using Apache Lucene, Tika, Snowball stemmer and etc.

### methods:
    * Tagging
      analyzer.tagger(content);
        
    * Stemming
      analyzer.stemmer(stems);
        
    * Senetence Detection
      analyzer.sentence_detector(sentence);
        
    * Grammar Parsing
      showParser(analyzer.grammer_parser(sentence));
        
    * Tika XML Parsing
      analyzer.tika_XMLparser();
        
    * Tika AutoDetect_Parsing
      analyzer.tika_autoParser();
