package engine;

import java.util.List;

import datamodel.ruleset.RuleSet;

public interface IPlainTextDocumentEngine {

	/**
	 * Registers a global rule set for a plain file at the main engine
	 * 
	 * Returns a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 * The inputSpec parameter representing the specification of the rules is a List of (List of String)
	 * Each category of rule (OMIT, H1, H2, <B>, <I>) has a dedicated (List of String). 
	 * Every category absent is setup to undefined status and eventually mapped to the resp. RuleUndefined object that always returns false for isValid()
	 * The 0th element is the aforementioned category representative string
	 * The 1st element is STARTS_WITH, POSITIONS, ALL_CAPS 
	 * The 3rd element is a prefix string for STARTS_WITH or a comma-separated string of positions (starting from 0) for POSITIONS
	 * 
	 * @param inputSpec a List of (List of String) with the specification of the rules on how to handle paragraphs
	 * @return a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 */
	RuleSet registerInputRuleSetForPlainFiles(List<List<String>> inputSpec);

	/**
	 * Registers a global rule set for an annotated  file at the main engine
	 * 
	 * Returns a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 * The inputSpec parameter representing the specification of the rules is a List of (List of String)
	 * Each category of rule (OMIT, H1, H2, <B>, <I>) has a dedicated (List of String). 
	 * Every category absent is setup to undefined status and eventually mapped to the resp. RuleUndefined object that always returns false for isValid()
	 * The 0th element is the aforementioned category representative string
	 * The 1st element _must_ always be STARTS_WITH  
	 * The 3rd element is a prefix string for STARTS_WITH or a comma-separated string of positions (starting from 0) for POSITIONS
	 * 
	 * The prefixes parameter represents the List<String> to report on the marks at the beginning of each paragraph.
	 * 
	 *  
	 * @param inputSpec a List of (List of String) with the specification of the rules on how to handle paragraphs
	 * @param prefixes a List of Strings as the prefixes for the annotated paragraphs of the file
	 * @return a RuleSet object as the result of the parsing and internal representation of the rules expressed as strings.
	 */
	RuleSet registerInputRuleSetForAnnotatedFiles(List<List<String>> inputSpec, List<String> prefixes);

	/**
	 * Takes the input file specified at the constructor, loads it and processes it according to the rule set specified at the constructor
	 *  
	 *  The blocks of the file are represented in a List in main memory, as the this.lineblocks attribute.
	 *   
	 * @return the number of LineBlocks that were identified and represented in-memory from the input file
	 */
	int loadFileAndCharacterizeBlocks();

	/**
	 * Exports the input file of the constructor as the MarkDown file at the path specified by outputFileName
	 * 
	 *  If the input files has not been processed, and the this.lineblocks attribute has a size of 0, the method loads and characterizes the input
	 *  	
	 * @param outputFileName the path where the exported MarkDown file will be written
	 * @return the number of LineBlocks exported in the output file
	 */
	int exportMarkDown(String outputFileName);//end exportMarkDown

	/**
	 * Exports the input file of the constructor as the pdf file at the path specified by outputFileName
	 * 
	 *  If the input files has not been processed, and the this.lineblocks attribute has a size of 0, the method loads and characterizes the input
	 *  	
	 * @param outputFileName the path where the exported pdf file will be written
	 * @return the number of LineBlocks exported in the output file
	 */
	int exportPdf(String outputFileName);//end exportPdf

	/**
	 * Outputs a List<String> to be used as a report on the number of paragraphs and words of a file.
	 * 
	 * If the input file has not been previously loaded and processed, the method does so.
	 * Then, it creates a List<String> with the following elements:
	 * the 0th element reporting on the total number of paragraphs
	 * the 1st element re porting on the total number of words
	 * each subsequent element reporting on the number of words of each paragraph
	 * 
	 * @return the List<String> with the report's elements
	 */
	List<String> reportWithStats();

}