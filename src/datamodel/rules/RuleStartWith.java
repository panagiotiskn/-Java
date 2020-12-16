package datamodel.rules;

import datamodel.buildingblocks.LineBlock;

public class RuleStartWith extends AbstractRule {

	private String prefix;
	
	public RuleStartWith(String prefix) {
		// TODO Auto-generated constructor stub
		this.prefix = prefix;
	}

	@Override
	public boolean isValid(LineBlock paragraph) {
		
		int br = 0;
		
		for (int i=0; i < prefix.length(); i++)
		{
			
			if(prefix.charAt(i) == paragraph.getText().charAt(i))
			{
				br = 1;
			}else {
				return false;
			}
		}
		if(br == 1) return true;
		return false;
		
	}

	
	
	public void getPrefix()
	{
		System.out.println(prefix);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This Rule looks for the lineblock that starts with the prefix: " + prefix;
	}

}
