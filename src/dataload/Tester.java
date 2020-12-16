package dataload;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.itextpdf.text.DocumentException;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.Document.DocumentRawType;
import datamodel.buildingblocks.LineBlock;
import datamodel.rules.RuleAllCaps;
import datamodel.rules.RuleInPosition;
import datamodel.rules.RuleStartWith;
import datamodel.rules.RuleUndefined;
import datamodel.ruleset.RuleSet;
import datamodel.ruleset.RuleSetCreator;
import engine.Engine;
import exporters.MarkdownExporter;
import exporters.PdfExporter;



class Tester
{
	public static void main(String[] args) throws IOException, DocumentException 
	{


		
		// 10 DECEMBER 2020 TESTING LINEBLOCK
		
		LineBlock lineBlock1 = new LineBlock("I heard his, stately tramp die away, step by step\n" + 
				"down the stairs and out into the deserted street, and\n" + 
				"felt sorry that he was gone, poor fellow -- and\n" + 
				"sorrier still that he had carried off my red blanket\n" + 
				"and my bath tub.");
		
		LineBlock lineBlock2 = new LineBlock(" \"END.\"");
		
		System.out.println("Below there is a LineBlock");
		System.out.println(lineBlock1.getText());
		System.out.println("***");

		
		System.out.println("Below there is a LineBlock");
		System.out.println(lineBlock2.getText());
		System.out.println("***");
		System.out.println("*****************************************************");		
		
		

		
		// 11 DECEMBER TESTING DOCUMENT
		
		System.out.println("Testing a Raw File");
		
		Document document1 = new Document("/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/hippocratesOath.txt",DocumentRawType.RAW);
		
		for(LineBlock paragraph: document1.getLineblocks())
		{
			System.out.println("This is a paragraph");
			System.out.println(paragraph.getText());
			System.out.println("***");
			
		}
		System.out.println("************************************************");
		
		// 13 DECEMBER TESTING DOCUMENT HTML
		
		Document document2 = new Document("/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/hippocratesOath.html",DocumentRawType.ANNOTATED);
		
		System.out.println("Testing an Annotated File");
		
		for(LineBlock paragraph: document2.getLineblocks())
		{
			System.out.println("This is a paragraph");
			System.out.println(paragraph.getText());
			System.out.println("***");
		}

		
		System.out.println("The document type of the above document is: " +document1.getInputFileType());
				
		System.out.println("*****************************************************");
		// 11 DECEMBER 2020 TESTING RAWFILELINELOADER (last tested)

		
		String path1 = "/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/ghost_mt.txt";
		RawFileLineLoader loader1 = new RawFileLineLoader(path1,DocumentRawType.RAW,"loader1");
	    System.out.println(loader1.getText());
	    System.out.println("Lets loop through the lineblocks and check out what's happening");
	    loader1.returnTextFromList();
	    
	    List<LineBlock> plineBlocks = loader1.getLineBlocks();
	    
	    for(LineBlock lb:plineBlocks)
	    {
	    	System.out.println(lb.getStyle());
	    	System.out.println(lb.getFormat());
	    }
	    
	    System.out.println("Testing if the reports Work");
	    for(LineBlock lb:plineBlocks)
	    {
	    	System.out.println(lb.getStatsAsString());
	    }
	    
	    
		System.out.println("*****************************************************");	    
	    


		
	    // 10 DECEMBER 2020 TESTING RULES
	    
	    System.out.println("Testing Rule Undefined");
	    RuleUndefined rule1 = new RuleUndefined();
	    System.out.println(rule1.isValid(lineBlock1));
	    System.out.println(rule1.isValid(lineBlock2));
	    System.out.println(rule1.toString());
	    
	    System.out.println("\n");

	    
	    System.out.println("Testing Rule all Caps");
	    RuleAllCaps rule2 = new RuleAllCaps();
	    System.out.println(rule2.isValid(lineBlock1));
	    System.out.println(rule2.isValid(lineBlock2));
	    System.out.println(rule2.toString());
        
	    System.out.println("\n");

	    
	    
	    
	    List<Integer> positions = new ArrayList<Integer>(); 
        positions.add(1); 
        positions.add(3); 
	    positions.add(15);
	    
        
        System.out.println("Testing Rule in Position");
        RuleInPosition rule3 = new RuleInPosition(plineBlocks,positions);
        System.out.println(rule3.isValid(lineBlock1));
        System.out.println(rule3.isValid(lineBlock2));
        System.out.println(rule3.toString());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
        
        
        
        System.out.println("Testing Rule Starts With");
	    
        RuleStartWith rule4 = new RuleStartWith("I heard");
        System.out.println(rule4.isValid(lineBlock1));
        System.out.println(rule4.isValid(lineBlock2));
        rule4.toString();
        
		System.out.println("*****************************************************");
        
		// 11 DECEMBER TESTING RULESET

		
		// CONSTRUCTING rules
		
		List<LineBlock> lb = document2.getLineblocks();
		List<Integer> pos = new ArrayList<Integer>();
		pos = positions;
		
		RuleUndefined ru = new RuleUndefined();
		RuleAllCaps rc = new RuleAllCaps();
		RuleInPosition rp = new RuleInPosition(lb,pos);
		RuleStartWith rs = new RuleStartWith("OATH ");
		
		// Testing for h1
		RuleSet rs1 = new RuleSet("ruleset1",ru,rc,ru,ru,ru);
		
		for(LineBlock l:lb)
		{
			System.out.println(rs1.determineHeadingStatus(l));
		}
		
		System.out.println("*****************************************************");

		
		// Testing for h2
		rs1 = new RuleSet("ruleset2",ru,ru,rc,ru,ru);
        
		for(LineBlock l:lb)
		{
			System.out.println(rs1.determineHeadingStatus(l));
		}
		
		System.out.println("*****************************************************");

		// Testing for omit
		
		rs1 = new RuleSet("ruleset3",rp,ru,ru,ru,ru);
		for(LineBlock l:lb)
		{
			System.out.println(rs1.determineHeadingStatus(l));
		}
		
		System.out.println("*****************************************************");

		// Testing for bold
		
		rs1 = new RuleSet("ruleset4",ru,ru,ru,rs,ru);
		
		for(LineBlock l:lb)
		{
			System.out.println(rs1.determineFormatStatus(l));
		}
		
		System.out.println("*****************************************************");

		// Testing for italics
		
		rs1 = new RuleSet("ruleset5",ru,ru,ru,ru,rc);
		for(LineBlock l:lb)
		{
			System.out.println(rs1.determineFormatStatus(l));
		}		
		
		System.out.println("*****************************************************");

		
        // 11 DECEMBER TESTING RULESETCREATOR
        
		
		System.out.println("Testing RuleSet Creator");

		
		String subrule1 = "<B>";
		String subrule2 = "POSITIONS";
		String subrule3 = "4,5";
		
		
		String subrule4 = "H1";
		String subrule5 = "ALL_CAPS";
		
		String subrule6 = "H2";
		String subrule7 = "STARTS_WITH";
		String subrule8 = "OATH";
		
		
		
		List<String> input1 = new ArrayList<String>();
		List<String> input2 = new ArrayList<String>();
		List<String> input3 = new ArrayList<String>();
		
		input1.add(subrule1);
		input1.add(subrule2);
		input1.add(subrule3);
	    
		input2.add(subrule4);
		input2.add(subrule5);
		
		input3.add(subrule6);
		input3.add(subrule7);
		input3.add(subrule8);
		
		List<List<String>> inputSpec = new ArrayList<List<String>>();
		List<List<String>> inputSpec1 = new ArrayList<List<String>>();
		inputSpec.add(input1);
		inputSpec.add(input2);
		inputSpec.add(input3);
		
		inputSpec1.add(input3);
		List<String> prefixes = new ArrayList<String>();
		prefixes.add(subrule8);
		
	    RuleSetCreator rulesetcreator1 = new RuleSetCreator(document1.getLineblocks(),inputSpec,"rulesetcreator1");
	    
	    RuleSet rulesset = rulesetcreator1.createRuleSet();
	    
	    for(LineBlock l: document1.getLineblocks())
	    {
	    	System.out.println("PARAGRAPH");
	    	System.out.println(rulesset.determineHeadingStatus(l));
	    	System.out.println(rulesset.determineFormatStatus(l));
	    	System.out.println(">>>");
	    }
	    
	    
		System.out.println("*****************************************************");    

		
		//14 DECEMBER TESTING ENGINE
		
		Engine engine1 = new Engine("/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/hippocratesOath.txt","RAW","engine1");
		Engine engine2 = new Engine("/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/hippocratesOath.html","ANNOTATED","engine2");
		
		engine1.registerInputRuleSetForPlainFiles(inputSpec);
		engine1.loadFileAndCharacterizeBlocks();
		String outputFile = "/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/test.md";
		engine1.exportMarkDown(outputFile);
		
		//16 DECEMBER TESTING PDFExport on Engine
		
		String outputFile1 = "/home/linux/eclipse-workspace/AM1_AM2_AM3_TextProcessor/Resources/SampleDocs/test.pdf";
		engine1.exportPdf(outputFile1);
		
		
		
		
		
		
		

	 
        
		
		
		
			
		
		
		
		
	}
	
	
	
	

	
}