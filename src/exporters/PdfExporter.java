package exporters;

import java.awt.TextArea;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import datamodel.buildingblocks.Document;
import datamodel.buildingblocks.FormatEnum;
import datamodel.buildingblocks.LineBlock;
import datamodel.buildingblocks.StyleEnum;



public class PdfExporter {

	private Document inputDocument;
	private String outputFileName;
	
	
	
	public PdfExporter(Document document, String outputFileName) {
		// TODO Auto-generated constructor stub
		this.inputDocument = document;
		this.outputFileName = outputFileName;
		
		
	}

	public int export()  {
		// TODO Auto-generated method stub
		int count = 0;
		String dest = outputFileName;   

      // Creating a Document object       
      com.itextpdf.text.Document document = new com.itextpdf.text.Document();
   
      try {
    	  
		PdfWriter.getInstance(document, new FileOutputStream(dest));
	      // Fonts
	       Font bold = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD);
	       Font italic = new Font(FontFamily.TIMES_ROMAN,12,Font.ITALIC);
	       Font h1 = new Font(FontFamily.TIMES_ROMAN,24);
	       Font h2 = new Font(FontFamily.TIMES_ROMAN,20);
	       
	       document.open();
	       
	       for(LineBlock lb:inputDocument.getLineblocks())
			{
	    	   count ++;
	    	   
	    	   if(lb.getStyle()==StyleEnum.OMITTED)
				{
					;
				}else if(lb.getStyle()== StyleEnum.H1)
				{
					Paragraph p1 = new Paragraph();
					Chunk chunk1 = new Chunk(lb.getText(),h1);
					p1.add(chunk1);
					document.add(p1);
				}else if(lb.getStyle()== StyleEnum.H2)
				{
					Paragraph p1 = new Paragraph();
					Chunk chunk1 = new Chunk(lb.getText(),h2);
					p1.add(chunk1);
					document.add(p1);
					
				}else if(lb.getFormat()==FormatEnum.BOLD)
					{
						Paragraph p1 = new Paragraph();
						Chunk chunk1 = new Chunk(lb.getText(),bold);
						p1.add(chunk1);
						document.add(p1);
						
					}else if(lb.getFormat()==FormatEnum.ITALICS)
					{
						Paragraph p1 = new Paragraph();
						Chunk chunk1 = new Chunk(lb.getText(),italic);
						p1.add(chunk1);
						document.add(p1);
										
					}else {
						Paragraph p1 = new Paragraph();
						p1.add(lb.getText());
						document.add(p1);
					}

			}
      
      
			document.close();
      
      
      } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (DocumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return count;
	}
	
}
