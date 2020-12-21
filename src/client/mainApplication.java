package client;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import datamodel.buildingblocks.Document.DocumentRawType;
import engine.Engine;
import exporters.MarkdownExporter;
import exporters.PdfExporter;

public class mainApplication {
	private Scanner reader;
	
	public mainApplication(){
		reader = new Scanner(System.in); 
	}
	
	private Scanner getReader(){
		return reader;
	}
	
	private void printExamplesForText()
	{
		System.out.println("Those are some examples ONLY FOR TEXT");
		System.out.println(" \"OMIT\" \"POSITIONS\" \"1,2\"");
		System.out.println(" \"H1\" \"STARTS_WITH\" \"POLITICAL ECONOMY\"");

	}
	
	private void printExamplesForHTML()
	{
		System.out.println("Those are some examples ONLY FOR HTML");
		System.out.println(" \"H1\" \"STARTS_WITH\" \"<H1>\"");
		System.out.println(" \"H2\" \"STARTS_WITH\" \"<H2>\"");
		System.out.println(" \"<B>\" \"STARTS_WITH\" \"<b>\"");
	}
	
	
	private void printHowToConstructRules()
	{
		System.out.println("The format of the rules should be \"something1\" \"something2\" \"something3\"");
		System.out.println("For \"something1\" choose between \"OMIT\" \"H1\" \"H2\" \"<B>\"  \"<Ι>\"  ");
		System.out.println("For \"something2\" choose \"STARTS_WITH\" \"ALL_CAPS\" \"POSITIONS\"");
		System.out.println("For \"STARTS_WITH\" the rule looks for a paragraph that starts with the words on \"something3\" ");
		System.out.println("For \"POSITIONS\" the rule looks for all the paragraphs written in \"something3\" ");
		System.out.println("For \"ALL_CAPS\" you dont need any \"something3\" the rules contains all paragraph that written on Caps");
		
	}
	
	
	private int printMenu(){
		
	    Scanner scanner = new Scanner(System.in);  // Create a Scanner object
	    //String userName = scanner.nextLine();
		System.out.println("Select an Option\n 1. Upload File \n 2. Add Rule \n 3. Produce Report\n 4. Export\n 5. Exit");
		boolean isCorrect = false;
		while(isCorrect==false)
		{
			System.out.println("Select your option");
			int choice = scanner.nextInt();
			if(choice > 5 || choice < 1)
			{
				System.out.println("Wrong answer! Try again...");
			}else {
				return choice;
			}
		}		
		return 0;
	}


	
	public static void main(String args[]) {
		mainApplication t = new mainApplication();
		Engine engine1 = null;
		String fileType = "";
		boolean youChoseRules = false;
		boolean keepRunning = true;
		while(keepRunning ==true){
			int choice = t.printMenu();
			
			if(choice==1)
			{
				System.out.println("You chose to upload a file,write below the path for your file");
			    Scanner scanner = new Scanner(System.in);
			    String path = scanner.nextLine();
			    System.out.println("The path you chose is: "+ path);
			    
		    	System.out.println("Now choose if the file is RAW(.txt) or ANNOTATED(.html)");
			    while((!fileType.equals("RAW")) && (!fileType.equals("ANNOTATED")))
			    {
			    	System.out.println("Choose RAW or ANNOTATED");
			    	fileType = scanner.nextLine().toUpperCase();
			    	
			    }
			    System.out.println("Finally choose a name for this file (whatever you like doesn't matter)");
			    String fileName = scanner.nextLine();
			    engine1 = new Engine(path, fileType,fileName);
			}
			
			if(choice ==2)
			{
				
				if(engine1==null)
				{
					System.out.println("You cant create rules if you dont import a file first");
					
				}else {
					t.printHowToConstructRules();
					t.printExamplesForText();
					t.printExamplesForHTML();
					int keepGoing = 1;
					String writeRules = "";
					List<List<String>> rules = new ArrayList<List<String>>();
					List<String> prefixes = new ArrayList<String>();
					while(keepGoing != 0)
					{
					    Scanner scanner = new Scanner(System.in);
						System.out.println("Write a rule according to the text above");
						writeRules = scanner.nextLine();
						String [] array = writeRules.split(" "); 

						List<String> rule = new ArrayList<String>();
						
						for(String str: array)
						{
							rule.add(str);
						}
						if(rule.size()==3)
								prefixes.add(rule.get(2));
						rules.add(rule);
						youChoseRules = true;
						System.out.println("Do you want to add another rule? Press 1 or press 0 to stop adding rules");
						keepGoing = scanner.nextInt();
						
					}
					System.out.println("You have added some rules check them out!");
					if(fileType.equals("RAW"))
					{
						engine1.registerInputRuleSetForPlainFiles(rules);
						
					}else {
					
						engine1.registerInputRuleSetForAnnotatedFiles(rules, prefixes);
					}
					System.out.println("Do you wish to continue and move back to main menu? [Y/N]");
				    Scanner scanner = new Scanner(System.in);
					String choice1 = scanner.nextLine().toUpperCase();

					if(choice1.equals("N"))
					{
						keepRunning = false;
					}

				}
			}
			if(choice ==3)
			{
				if(engine1==null)
				{
					System.out.println("You cant produce a report if you dont import a file first");
					
				}else {
					System.out.println("You chose to print some more infomration about the file");
					List<String> statistics1 = engine1.reportWithStats();
					for(String statistic:statistics1)
					{
						System.out.println(statistic);

					}
					System.out.println("Do you wish to continue and move back to main menu? [Y/N]");
				    Scanner scanner = new Scanner(System.in);
					String choice1 = scanner.nextLine().toUpperCase();

					if(choice1.equals("N"))
					{
						keepRunning = false;
					}
				}

			}
			if(choice == 4)
			{
				if(engine1==null)
				{
					System.out.println("Why don't you import a file first?");
					
				}else {
					System.out.println("So you chose to export your file into a PDF or MARKDOWN file.");
					System.out.println("First give me the path where you want to save your file");
					System.out.println("YOUR PATH SHOULD CONTAIN THE NAME OF THE OUTPUT FILE");
					Scanner scanner = new Scanner(System.in);
					String pathString = scanner.nextLine(); 
					if(youChoseRules == true)
					{
						System.out.println("Seems like you have added some rules on the System");
						System.out.println("Do you want to save the the output files with the rules or without? [Y/N]");
						String choice3 = scanner.nextLine().toUpperCase(); 
						if(choice3.equals("Y"))
						{
							engine1.loadFileAndCharacterizeBlocks();
						}
						
					}
					boolean nextStep = false;
					while(nextStep ==false)
					{
						System.out.println("Nice now if you please choose [1] if you want to convert to pdf and [2] if you want to convert to markdown");
						Integer choice4 = scanner.nextInt(); 
						if(choice4==1)
						{
							System.out.println("Okay working on that");
							engine1.exportPdf(pathString);
							nextStep = true;						
						}else if(choice4==2)
						{
							System.out.println("Okay working on that");
							engine1.exportMarkDown(pathString);
							nextStep = true;
						}else {
							nextStep = false;
						}
				}
					System.out.println("Your outputfile is created");

				}

			}else if(choice == 5)
			{
				System.out.println("Are you sure you want to exit or  move back to main menu instead? [Y/N]");
			    Scanner scanner = new Scanner(System.in);
				String choice1 = scanner.nextLine().toUpperCase();

				if(choice1.equals("Y"))
				{
					System.out.println("Bye Bye");
					System.exit(1);
				}
				
			}

		}
	
	}
		  
}
		
	
		