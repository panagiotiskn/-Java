package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleUndefined extends AbstractRule {
	

	@Override
	public boolean isValid(LineBlock paragraph) {
		// TODO Auto-generated method stub
		return false; //ALWAYS RETURNES FALSE
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This Rule is Undefined and it's never valid";
	}

}
