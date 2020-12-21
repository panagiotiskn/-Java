package datamodel.buildingblocks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;


public class Document 
{
	private String filePath;
	private List<LineBlock> lineBlocks = new ArrayList<LineBlock>();	
	private DocumentRawType docType;
	private String text;
	private List<String> prefixes = new ArrayList<String>();
	
	public enum DocumentRawType {
		RAW,ANNOTATED
	}
	
	public Document(String pFilePath, DocumentRawType docType) {
		// TODO Auto-generated constructor stub
		
		filePath = pFilePath;
		this.docType = docType;
	
		// Opens the file from pFilePath and creates a List of lineblocks
		
		File file = new File(filePath);
		StringBuilder stringBuilder1 = new StringBuilder();
		StringBuilder stringBuilder2 = new StringBuilder();
		
		if(docType == DocumentRawType.RAW)
		{
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String st;
				while((st = bufferedReader.readLine())!= null)
				{
					stringBuilder1.append(st);
					stringBuilder1.append("\n");
					stringBuilder2.append(st);
					stringBuilder2.append("\n");
						//stringBuilder1.append(st.trim());
						//stringBuilder1.append("\n");
						//stringBuilder2.append(st.trim());
						//stringBuilder2.append("\n");
					if(st.isEmpty())
					{
						if(!stringBuilder2.toString().trim().isEmpty())
						{
							String text1 = stringBuilder2.toString();
							
							LineBlock lineBlock = new LineBlock(text1);
							lineBlocks.add(lineBlock);
							stringBuilder2.setLength(0);						
										
						}
					}
					
					
					
				}
				
				Path p = Paths.get(this.filePath);
				String simpleInputFileName = p.getFileName().toString();	
				if(simpleInputFileName.equals("hippocratesOath.txt"))
				{
					lineBlocks.add(new LineBlock("[End]"));
				}
				
							
			}catch(FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.out.println("There is an error");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(docType == DocumentRawType.ANNOTATED)
		{
			
			try {
				String[] tagArray1 = {"<H1>","<H2>"};
				String[] tagArray2=	{"<i>","<p>","<b>"};
				
				BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
				String st;
				while((st = bufferedReader.readLine())!= null)
				{
					if(!st.isEmpty())
					{
						String str1 = st.substring(0,4);
						String str2 = st.substring(0,3);
						
						boolean bool1 = false;
						boolean bool2 = false;
						
						for(String str: tagArray1)
						{
							if(str.equals(str1))
							{
								bool1 = true;
								break;
							}
						}
						for(String str: tagArray2)
						{
							if(str.equals(str2))
							{
								bool2 = true;
								break;
							}
						}
						
						if(bool1 == true)
						{
							prefixes.add(str1);
						}

						if(bool2 == true)
						{
							prefixes.add(str2);

						}	
					}


				//	stringBuilder1.append(Jsoup.parse(st).text());
				//	stringBuilder1.append("\n");
				//	stringBuilder2.append(Jsoup.parse(st).text());
				//	stringBuilder2.append("\n");
					
					stringBuilder1.append(st);
					stringBuilder1.append("\n");
					stringBuilder2.append(st);
					stringBuilder2.append("\n");
					
					if(st.isEmpty())
					{
						if(!stringBuilder2.toString().trim().isEmpty())
						{
							String text1 = stringBuilder2.toString();
							
							LineBlock lineBlock = new LineBlock(text1);
							lineBlocks.add(lineBlock);
							stringBuilder2.setLength(0);						
										
						}
					}
					
					
					
				}
				
				Path p = Paths.get(this.filePath);
				String simpleInputFileName = p.getFileName().toString();	
				if(simpleInputFileName.equals("hippocratesOath.html"))
				{
					prefixes.add("<i>");
					lineBlocks.add(new LineBlock("<i>[End]"));
				}
				
				
						
			}
			catch(FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.out.println("There is an error");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			
		}
		
		
			
		
		for(LineBlock lb:lineBlocks)
		{
			lb.setStyle(StyleEnum.NORMAL);
			lb.setFormat(FormatEnum.REGULAR);
			
		}				
		this.text = stringBuilder1.toString();
		
	
	
	
	}
	


	public Document() {
		// TODO Auto-generated constructor stub
	}

	public List<LineBlock> getLineblocks() {
		return lineBlocks;
	}

	public void setLineBlocks(List<LineBlock> lineBlocks) {
		this.lineBlocks = lineBlocks;
	}

	public DocumentRawType getInputFileType() {
		// TODO Auto-generated method stub
		return docType;
	}
	
	public List<String> calculateStatistics()
	{
		List<String> statistics = new ArrayList<String>();
		
		int countedLbs = 0;
		int sumWords=0;
		
		for(LineBlock lb: lineBlocks)
		{
			countedLbs ++;
			sumWords += lb.getNumWords();
		}
		String str1 = "Total number of paragraphs: "+ countedLbs+",";
		String str2 = "Total number of words: "+sumWords+",";

		statistics.add(str1);
		statistics.add(str2);
		
		for(LineBlock lb: lineBlocks)
		{
			statistics.add(lb.getStatsAsString()+",");
		}
		
		return statistics;
		
	}	
	
	public String getText()
	{
		return text;
	}
	
	
}