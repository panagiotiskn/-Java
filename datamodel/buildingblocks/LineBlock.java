package datamodel.buildingblocks;

import java.util.ArrayList;
import java.util.Arrays;

public class LineBlock 
{
	private String text;
	private ArrayList<String> sentences = new ArrayList<String>();
	private int numLines;
	private int words;
	private FormatEnum format;
	private StyleEnum style;
	
	
	
	public LineBlock(String text)
	{
		this.text = text;
		String[] linesArray = text.split("\r\n|\r|\n");
		String[] wordsArray = text.split(" ");
		numLines = linesArray.length;	
		words = wordsArray.length;
		
		//ArrayList<String> sentences = new ArrayList<String>(Arrays.asList(text.split("\\s+|[^.]+$")));
		
		for(String line: linesArray)
		{
			sentences.add(line);
		}
		
		
	}
	
	
	public String getStatsAsString()
	{
		return "Lines:"+numLines+"   Words:"+words;
	}
	
	
	public ArrayList<String> getLines()
	{
		return sentences;
	}
	
	public int getNumWords()
	{
		return words;
		
	}
	
	public int getNumLines()
	{
		return numLines;
	}
	

	public String getText()
	{
		return text;
	}


	public StyleEnum getStyle()
	{
		return style;
	}

	public FormatEnum getFormat()
	{
		return format;
	}

	public void setFormat(FormatEnum determineFormatStatus) {
		// TODO Auto-generated method stub
		this.format = determineFormatStatus;
	}
	
	public void setStyle(StyleEnum determineHeadingStatus) {
		// TODO Auto-generated method stub
		this.style = determineHeadingStatus;
	}
	
	
	

}
