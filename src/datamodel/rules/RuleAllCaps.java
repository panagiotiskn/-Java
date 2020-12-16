package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleAllCaps extends AbstractRule {

	
	@Override
	public boolean isValid(LineBlock paragraph) {
		// TODO Auto-generated method stub
		String str = paragraph.getText();
		if(str.equals(str.toUpperCase()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This Rule checks if a LineBlock has a text in upper case and can be used as a heading";
	}

}
