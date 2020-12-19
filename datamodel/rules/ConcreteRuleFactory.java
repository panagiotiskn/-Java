package datamodel.rules;

import java.util.List;

import datamodel.buildingblocks.LineBlock;

/**
 * Creator of concrete rule objects
 * 
 * For each concrete subclass, the factory comes with one method.
 * Thus, the clients of the package need only work with
 * (a) the current factory for rule creation
 * (b) the AbstractRule abstract class for the actual work of a specific rule (rule validation)
 *  
 * @author pvassil
 * @version 0.1
 */
public class ConcreteRuleFactory {

	public AbstractRule createRuleUndefined() {
		return new RuleUndefined();	
	}
	
	public AbstractRule createRuleAllCaps() {
		return new RuleAllCaps();
	}

	public AbstractRule createRuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {
		if((pLineblocks == null) || (pPositions == null)) {
			System.err.println("[ConcreteRuleFactory] createRuleInPosition with empty parameters");
			return new RuleUndefined();
		}
		return new RuleInPosition(pLineblocks, pPositions);
	}

	public AbstractRule createRuleStartWith(String prefix) {
		if(prefix == null){
			System.err.println("[ConcreteRuleFactory] createRuleStartWith with empty parameters");
			return new RuleUndefined();
		}
		return new RuleStartWith(prefix);
	}
	
}//end class
