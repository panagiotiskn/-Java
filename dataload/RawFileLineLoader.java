package dataload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.Document.DocumentRawType;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

public class RawFileLineLoader 
{
	private String path;
	private String text;
	private String alias;
	private DocumentRawType docType;
	private Document document;
	private List<LineBlock> lineblocks = new ArrayList<LineBlock>();
	
	
	public RawFileLineLoader()
	{}
	
	public RawFileLineLoader(String path,DocumentRawType docType,String alias) throws IOException
	{
		this.path = path;
		this.docType = docType;
		this.alias = alias;
		this.document = new Document(path,docType);
		lineblocks = document.getLineblocks();
		
	}

	public List<LineBlock> getLineBlocks()
	{
		return lineblocks;
	}

	public String getText()
	{
		return text;
	}

	 
	public void returnTextFromList()
	{
		for(LineBlock lb: lineblocks)
		{
			System.out.println("This paragraph comes from Loader objects and run smoothly\n");
			System.out.println(lb.getText());
				
		}
	}
	
	public void load(String filePath, List<LineBlock> lineblocks) {
		// TODO Auto-generated method stub
		this.path = filePath;
		this.lineblocks=lineblocks;
		
	}
	

	

}
