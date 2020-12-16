package datamodel.rules;

import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.LineBlock;

public class RuleInPosition extends AbstractRule {

	private List<LineBlock> lineblocks = new ArrayList<LineBlock>();
	private List<Integer> positions = new ArrayList<Integer>();
	
	
	public RuleInPosition(List<LineBlock> pLineblocks, List<Integer> pPositions) {
		// TODO Auto-generated constructor stub
		this.positions=pPositions;
		for(Integer number: pPositions)
		{
			LineBlock lb =pLineblocks.get(number);
			lineblocks.add(lb);
		}
		
	}

	public List<LineBlock> getLineBlocks()
	{
		return lineblocks;
	}
	
	public void printer()
	{
		for(LineBlock lb: lineblocks)
		{
			System.out.println("This Rule ownes this paragraph");
			System.out.println(lb.getText());
			
		}
	}
	
	@Override
	public boolean isValid(LineBlock paragraph) {
		// TODO Auto-generated method stub
		
		for(LineBlock lb:lineblocks)
		{
			if(lb.getText().trim().equals(paragraph.getText().trim()))
			{
				return true;
			}

		}
		
		return false;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "This Rule keeps the lineblocks that are written on the integers "+positions.toString();
	}

}
