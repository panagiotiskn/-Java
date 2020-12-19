package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

/**
 * Abstract class to be concreted by specific subclasses for the handling of paragraph characterization
 * 
 * @author pvassil
 *
 */
public abstract class AbstractRule {
	/**
	 * Decides if the paragraph abides by the rule
	 * 
	 * @param paragraph the LineBlock under test
	 * @return true if the the LineBlock abides by the test of the rule
	 */
	public abstract boolean isValid(LineBlock paragraph);
	
	/**
	 * For debugging and testing purposes: outputs a string on what the rule does
	 */
	public abstract String toString();
}
