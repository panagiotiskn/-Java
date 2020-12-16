package exporters;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;

public class MarkdownExporter{

	private Document document;
	private String outputFileName;
	private List<LineBlock> lineblocks = new ArrayList<LineBlock>();
	
	public MarkdownExporter(Document document, String outputFileName) {
		// TODO Auto-generated constructor stub
		this.document = document;
		this.outputFileName = outputFileName;
		this.lineblocks = document.getLineblocks();

	
	}

	public int export() throws IOException {
		// TODO Auto-generated method stub
		
			int count = 0;
			File file = new File(outputFileName);
			
			 try (FileWriter fw = new FileWriter(file)) {
				 StringBuilder sb1 = new StringBuilder();
				 StringBuilder sb2 = new StringBuilder();
				 StringBuilder sb3 = new StringBuilder();
				 StringBuilder sb4 = new StringBuilder();
				 
				 for(LineBlock l: lineblocks)
				 {
					 count ++;
					if(l.getStyle()==StyleEnum.OMITTED)
					{
						fw.write("\n\n");
					}else if(l.getStyle()== StyleEnum.H1)
					{
						sb1.append("#");
						sb1.append(l.getText().trim());
						sb1.append("\n\n");
						fw.write(sb1.toString());
						sb1.setLength(0);
					}else if(l.getStyle()== StyleEnum.H2)
					{
						fw.write("##"+l.getText().trim());
						fw.write("\n\n");
						
					}else {
						if(l.getFormat()==FormatEnum.BOLD)
						{
							fw.write("**"+l.getText().trim()+"**");
							fw.write("\n\n");
						}else if(l.getFormat()==FormatEnum.ITALICS)
						{
							fw.write("*"+l.getText().trim()+"*");
							fw.write("\n\n");
						}else {
							fw.write(l.getText());
							fw.write("\n\n");
						}
					}
				 }    
			
				 
			
			 }catch(IOException e)
			{
				System.out.println("An error occured");
				e.printStackTrace();
			}
			
		
		return count;
	}

}
