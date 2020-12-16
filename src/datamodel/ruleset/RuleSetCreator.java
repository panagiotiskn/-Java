package datamodel.ruleset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import datamodel.buildingblocks.LineBlock;
import datamodel.rules.AbstractRule;
import datamodel.rules.ConcreteRuleFactory;

/**
 * The class is responsible for creating a RuleSet, given a set of strings as input.
 * 
 * The set of strings to specify the rules are given as input at the constructor of the class
 * The constructor also needs the set of LineBlocks of the document (for the positional rules) 
 * and a name to discriminate the new RuleSet from the others
 * 
 * The core of the processing is performed by method createRuleSet()
 * 
 * @author pvassil
 * @version 0.1
 */
public class RuleSetCreator {
	private List<LineBlock> lineblocks;
	private List<List<String>> inputSpec;
	private ConcreteRuleFactory factory;
	private String name;
	private AbstractRule omitRule;
	private AbstractRule h1Rule;
	private AbstractRule h2Rule;
	private AbstractRule boldRule;
	private AbstractRule italicsRule;
	
	/**
	 * The constructor of the class initializes the attributes of the class to the default values
	 * such that they are subsequently changed only if necessary
	 * 
	 * The most important aspect of the project is the specification of the RuleSet by the clients of this class.
	 * This is represented in the attribute this.inputSpec, which in turns is populated by the constructor's input parameter inputSpec
	 * The format is as follows: inputSpec is a List of List<String>. Each internal List<String> is a triplet of 3 strings:
	 * (a) style of heading, i.e., H1, H2, OMIT, <B>, <I>
	 * (b) when the rule is activated, i.e., STARTS_WITH, ALL_CAPS, POSITIONS
	 * (c) the value pertaining to the rule
	 * The only case where just a pair of strings is needed is ALL_CAPS
	 * POSITIONS requires a single number or a comma-separated list of numbers as 3rd argument
	 * 
	 * For example, a triplet saying "OMIT", "POSITIONS" "4,5" will omit the positions 4 and 5 in the list of paragraphs
	 * As another example, a triplet saying "H1", "ALL_CAPS" will treat all lines with all their characters as capital letters as H1
	 * 
	 * @param lineblocks a List<LineBlock> representing the paragraphs of the input (called LineBlocks in this project)
	 * @param inputSpec a List<List<String>> with the specification of behavior
	 * @param name the name of the RuleSet to be created
	 */
	public RuleSetCreator(List<LineBlock> lineblocks, List<List<String>> inputSpec, String name) {
		this.lineblocks = lineblocks;
		this.inputSpec = inputSpec;
		this.factory = new ConcreteRuleFactory();
		this.name = name;

		AbstractRule currentRule = factory.createRuleUndefined();
		omitRule=currentRule; h1Rule=currentRule; h2Rule=currentRule; boldRule=currentRule; italicsRule=currentRule;
	}
	
	/**
	 * The class populates a new RuleSet with all its necessary rules and returns it as the result of the processing
	 * 
	 * The class employs the abstract class AbstractRule (to be concreted with concrete subclasses per category of rule)
	 * and the respective Factory. Thus, a set of concrete classes to handle the rules of AllCaps, Position, StartsWith, or Undefined are needed.
	 * At the end, the different kinds of rules of the RuleSet are all populated with an object of the appropriate class.
	 * 
	 * @return the RuleSet to be generated
	 */
	public RuleSet createRuleSet() {
		String lastParameter = "";
		
		
		for(List<String> l: inputSpec) {
			AbstractRule currentRule = factory.createRuleUndefined();
			
			switch(l.get(1).strip().toUpperCase()) {
				case "STARTS_WITH":	lastParameter = l.get(2);
									currentRule = factory.createRuleStartWith(lastParameter);
									break;
				case "ALL_CAPS":	currentRule = factory.createRuleAllCaps();
									break;
				case "POSITIONS":	lastParameter = l.get(2);
									List<String> stringList = new ArrayList<String>(Arrays.asList(lastParameter.split("\\s*,\\s*")));
									List<Integer> intList = stringList.stream().map(Integer::parseInt).collect(Collectors.toList());
									currentRule = factory.createRuleInPosition(lineblocks, intList);
									break;
				default: 	;
			}
			
			if(currentRule==null) {
				System.err.println("[RuleSetCreator] null format rule; exiting");
				System.exit(-1);
			}
			
			switch(l.get(0).strip().toUpperCase()) {
			case "OMIT":	omitRule = currentRule;System.out.println("[RuleSetCreator] " + currentRule.toString()+"\n");
						break;
			case "H1":	h1Rule = currentRule;System.out.println("[RuleSetCreator] " + currentRule.toString()+"\n");
						break;
			case "H2":	h2Rule = currentRule;System.out.println("[RuleSetCreator] " + currentRule.toString()+"\n");
						break;
			case "<B>":	boldRule = currentRule;System.out.println("[RuleSetCreator] " + currentRule.toString()+"\n");
						break;
			case "<I>":	italicsRule = currentRule;System.out.println("[RuleSetCreator] " + currentRule.toString()+"\n");
						break;
			default:	System.err.println("[RuleSetCreator] Wrong rule set specification syntax. Aborting");
						System.exit(-100);
			}
			
			if(currentRule==null) {
				System.err.println("[RuleSetCreator] null style rule; exiting");
				System.exit(-1);
			}
		}//end outer for
		
		return new RuleSet(name, omitRule, h1Rule, h2Rule, boldRule, italicsRule);

	}//end method

}//end class
