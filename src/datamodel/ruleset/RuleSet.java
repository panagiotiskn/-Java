package datamodel.ruleset;

import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.StyleEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.rules.AbstractRule;

/**
 * Class to represent a set of rules
 * 
 * The main idea of the class is that it has a field for every rule of interest.
 * So, it has rules for headings and rules for style.
 * Headings: the class has a rule for what to omit, a rule for how to handle heading 1, a rule for handling heading 2
 * Style: the class has a rule for bold and a rule for italics.
 * 
 * All these rules dictate when sth is t obe treated as h1, or bold, etc.
 * If an item does not apply by any of the "heading" rules, it is by default NORMAL
 * If an item does not apply by any of the "style" rules, it is by default REGULAR
 * 
 * The class comes with two methods, that, given a LineBlock, decide which classification applies (a) for its heading and (b) for its style
 * 
 * @author pvassil
 * @version 0.1
 */
public class RuleSet {
	private String name;	
	private AbstractRule omitRule;
	private AbstractRule h1Rule;
	private AbstractRule h2Rule;
	private AbstractRule boldRule;
	private AbstractRule italicsRule;
	
	public RuleSet(String name, AbstractRule omitRule, AbstractRule h1Rule,
			AbstractRule h2Rule, AbstractRule boldRule, AbstractRule italicsRule) {
		this.name = name;
		this.omitRule = omitRule;
		this.h1Rule = h1Rule;
		this.h2Rule = h2Rule;
		this.boldRule = boldRule;
		this.italicsRule = italicsRule;
	}

	public StyleEnum determineHeadingStatus(LineBlock lineblock) {
		if (omitRule.isValid(lineblock))
			return StyleEnum.OMITTED;
		if (h1Rule.isValid(lineblock))
			return StyleEnum.H1;
		if (h2Rule.isValid(lineblock))
			return StyleEnum.H2;
		return StyleEnum.NORMAL;
	}

	public FormatEnum determineFormatStatus(LineBlock lineblock) {
		if (boldRule.isValid(lineblock))
			return FormatEnum.BOLD;
		if (italicsRule.isValid(lineblock))
			return FormatEnum.ITALICS;
		return FormatEnum.REGULAR;
	}
	
	public AbstractRule getBold()
	{
		return boldRule;
	}
	
	public String toString() {
		return this.name + "\nOMIT: " + omitRule.toString() + "\nH1: " + h1Rule.toString() + "\nH2: " + h2Rule.toString() +
				"\nBOLD: " + boldRule.toString() + "\nITALICS: " + italicsRule.toString() + "\n";
	}

}//end class
